package common;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("rawtypes")
public class MessageFromClient implements Serializable{

	public Task type; 
	public ArrayList longData;

	public MessageFromClient() {
		longData = new ArrayList();
	}
	
	// adding data to longData
	public void addLongData(Object...ob) {
		for(int i = 0; i < ob.length; i++)
			longData.add(ob[i]);
	}
	
	
	
	public Object get(int index) {
		return longData.get(index);
	}
	
}