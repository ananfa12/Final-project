package common;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JComboBox;

public class ListCombo implements Serializable {
	public ArrayList<String> names;
	public ArrayList<Integer> ids;
	public JComboBox<String> list;
	
	@SuppressWarnings("unchecked")
	public ListCombo(ArrayList<String> names, ArrayList<Integer> ids) {
		this.names = (ArrayList<String>)names.clone();
		this.ids = (ArrayList<Integer>)ids.clone();
		
		makeComboBox();
	}
	
	public ListCombo() {
		names = new ArrayList<String>();
		ids = new ArrayList<Integer>();
		
		makeComboBox();
	}
	
	private void makeComboBox() {
		list = new JComboBox<String>();
		for(String name: names)
			list.addItem(name);
	}
	
	public void updateComboBox() {
		list.removeAllItems();
		for(String name: names)
			list.addItem(name);
	}
	
	public ArrayList<String> getNames() {
		return names;
	}
	
	public ArrayList<Integer> getIds() {
		return ids;
	}
	
	public JComboBox getComboBox() {
		return list;
	}
}
