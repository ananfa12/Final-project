package common;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("rawtypes")
public class MessageFromServer implements Serializable {
	// default errors
	public static final int LOGIN = 0;
	public static final int MSG = 1;

	public Task t;

	public Task task;
	public Screens screen;
	public int value;
	public Object other;
	
	public String s;

	public ArrayList longData;

	public MessageFromServer() {
		longData = new ArrayList<String>();
	}
	
	public void addLongData(Object...ob) {
		for (int i = 0; i < ob.length; i++)
			longData.add(ob[i]);
	}
	
	public Object get(int index) {
		return longData.get(index);
	}
}