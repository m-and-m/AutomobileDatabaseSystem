import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;


public class SelectionComboModel implements ComboBoxModel {

	private List<String> data = new ArrayList<String>();
	private int selected = 0;
	
	public SelectionComboModel(List<String> list) {
		data = list;
	} //SelectionComboModel
	
	@Override
	public Object getElementAt(int i) {
		return data.get(i);
	}

	@Override
	public int getSize() {
		return data.size();
	}

	@Override
	public Object getSelectedItem() {
		return data.get(selected);
	}

	@Override
	public void setSelectedItem(Object o) {
		selected = data.indexOf(o);
	}

	@Override
	public void removeListDataListener(ListDataListener arg0) {
	}

	@Override
	public void addListDataListener(ListDataListener arg0) {
	}
} /// SelectionComboModel
