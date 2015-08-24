package com.bioxx.tfc.Core.Util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;

/**
 * Made for WorldGenOre.OreList, but usefull for other purposes.
 * Stores all keys in lowercase
 *
 * @author Dries007
 */
@SuppressWarnings("unchecked")
public class CaseInsensitiveHashMap<V> extends HashMap<String, V>
{
	public CaseInsensitiveHashMap(int initialCapacity, float loadFactor)
	{
		super(initialCapacity, loadFactor);
	}

	public CaseInsensitiveHashMap(int initialCapacity)
	{
		super(initialCapacity);
	}

	public CaseInsensitiveHashMap()
	{
		super();
	}

	public CaseInsensitiveHashMap(Map<? extends String, ? extends V> m)
	{
		super((Map<? extends String, ? extends V>) toLowercase(m));
	}

	// Helper methods
	private static Map<String, Object> toLowercase(Map<? extends String, ?> m)
	{
		ImmutableMap.Builder<String, Object> temp = ImmutableMap.builder();
		// Must use Map.Entry here, not (Hashmap.)Entry, as the latter is a private inner class on some JDK's.
		// (Hashmap.)Entry has been replaced by (Hashmap.)Node in Oracle's JDK, wich is why it likely won't error in a dev env.
		// Thanks Stackoverflow & Grepcode
		for (Map.Entry<? extends String, ?> entry : m.entrySet())
		{
			temp.put(toLowercase(entry.getKey()), entry.getValue());
		}
		return temp.build();
	}

	private static String toString(Object o)
	{
		if (o == null) return null;
		return o.toString();
	}

	private static String toLowercase(String key)
	{
		if (Strings.isNullOrEmpty(key)) return key;
		return key.toLowerCase(Locale.ENGLISH);
	}

	// Override java 1.7 methods involving key
	@Override
	public V get(Object key)
	{
		return this.get(toString(key));
	}

	@Override
	public V put(String key, V value)
	{
		return super.put(toLowercase(key), value);
	}

	@Override
	public void putAll(Map<? extends String, ? extends V> m)
	{
		super.putAll((Map<? extends String, ? extends V>) toLowercase(m));
	}

	@Override
	public V remove(Object key)
	{
		return this.remove(toString(key));
	}

	// Properly typed get & remove methods.
	public V get(String key)
	{
		return super.get(toLowercase(key));
	}

	public V remove(String key)
	{
		return super.remove(toLowercase(key));
	}
}
