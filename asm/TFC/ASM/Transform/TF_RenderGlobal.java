package TFC.ASM.Transform;

/*
 * Invaluable help from AtomicStryker's MultiMine coremod code <3
 */

import java.lang.reflect.Field;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.world.World;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodInsnNode;

import TFC.TFCASMLoadingPlugin;
import TFC.API.Util.Helper;
import TFC.ASM.ClassTransformer;

public class TF_RenderGlobal extends ClassTransformer
{

	public TF_RenderGlobal()
	{
		mcpClassName = "net.minecraft.client.renderer.RenderGlobal";
		obfClassName = "bls";

		List nodes = new ArrayList<InstrSet>();
		InsnList list = new InsnList();
		list.add(new LineNumberNode(435, new LabelNode()));
		list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "TFC/ASM/Transform/TF_RenderGlobal","loadRenderers","()V"));
		nodes.add(new InstrSet(list, 453, InstrOpType.InsertBefore));
		mcpMethodNodes.put("loadRenderers | ()V", new Patch(nodes, PatchOpType.Modify));
		obfMethodNodes.put("a | ()V", new Patch(nodes, PatchOpType.Modify));
	}

	public static void loadRenderers()
	{
		RenderGlobal renderG = Minecraft.getMinecraft().renderGlobal;
		int k = 0, j = 0;
		int renderChunksWide = Helper.getInteger(renderG, "w", "renderChunksWide", TFCASMLoadingPlugin.runtimeDeobf);
		int renderChunksTall = Helper.getInteger(renderG, "x", "renderChunksTall", TFCASMLoadingPlugin.runtimeDeobf);
		int renderChunksDeep = Helper.getInteger(renderG, "y", "renderChunksDeep", TFCASMLoadingPlugin.runtimeDeobf);
		int glRenderListBase = Helper.getInteger(renderG, "z", "glRenderListBase", TFCASMLoadingPlugin.runtimeDeobf);
		WorldRenderer[] worldRenderers = (WorldRenderer[])(Helper.getObject(renderG, "v", "worldRenderers", TFCASMLoadingPlugin.runtimeDeobf));
		WorldRenderer[] sortedWorldRenderers = (WorldRenderer[])(Helper.getObject(renderG, "u", "sortedWorldRenderers", TFCASMLoadingPlugin.runtimeDeobf));
		List worldRenderersToUpdate = (ArrayList)(Helper.getObject(renderG, "t", "worldRenderersToUpdate", TFCASMLoadingPlugin.runtimeDeobf));
		World world = (World)(Helper.getObject(renderG, "r", "theWorld", TFCASMLoadingPlugin.runtimeDeobf));

		worldRenderersToUpdate.clear();
		renderG.tileEntities.clear();

		for (int l = 0; l < renderChunksWide; ++l)
		{
			for (int i1 = renderChunksTall-1; i1 >= 0; --i1)
			{
				for (int j1 = 0; j1 < renderChunksDeep; ++j1)
				{
					worldRenderers[(j1 * renderChunksTall + i1) * renderChunksWide + l] = new WorldRenderer(world, renderG.tileEntities, l * 16, i1 * 16, j1 * 16, glRenderListBase + j);

					if (Helper.getBoolean(renderG, "D", "occlusionEnabled", TFCASMLoadingPlugin.runtimeDeobf))
					{
						worldRenderers[(j1 * renderChunksTall + i1) * renderChunksWide + l].glOcclusionQuery = ((IntBuffer)(Helper.getObject(renderG, "C", "glOcclusionQueryBase", TFCASMLoadingPlugin.runtimeDeobf))).get(k);
					}

					worldRenderers[(j1 * renderChunksTall + i1) * renderChunksWide + l].isWaitingOnOcclusionQuery = false;
					worldRenderers[(j1 * renderChunksTall + i1) * renderChunksWide + l].isVisible = true;
					worldRenderers[(j1 * renderChunksTall + i1) * renderChunksWide + l].isInFrustum = true;
					worldRenderers[(j1 * renderChunksTall + i1) * renderChunksWide + l].chunkIndex = k++;
					worldRenderers[(j1 * renderChunksTall + i1) * renderChunksWide + l].markDirty();
					sortedWorldRenderers[(j1 * renderChunksTall + i1) * renderChunksWide + l] = worldRenderers[(j1 * renderChunksTall + i1) * renderChunksWide + l];
					worldRenderersToUpdate.add(worldRenderers[(j1 * renderChunksTall + i1) * renderChunksWide + l]);
					j += 3;
				}
			}
		}
	}

	public int getRenderChunksWide(Object obj)
	{
		Field f = null;
		try 
		{
			f = obj.getClass().getDeclaredField("renderChunksWide");
			f.setAccessible(true);
			return (Integer) f.get(obj); //IllegalAccessException
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return 0; 		
	}
}