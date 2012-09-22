package TFC.Containers;

import java.util.LinkedList;
import java.util.Queue;

public class MessageQue 
{
	public static MessageQue instance = new MessageQue();
	private Queue que;
	
	public MessageQue()
	{
		que = new LinkedList<String>();
	}
	
	public void addMessage(String s)
	{
		que.add(s);
	}
	
	public String getNextMessage()
	{
		if(!que.isEmpty())
			return (String) que.poll();
		else
			return null;
	}
}
