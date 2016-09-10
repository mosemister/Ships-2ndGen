package MoseShips.CustomDataHolder;

import java.util.ArrayList;
import java.util.List;

public class DataHolder {

	protected List<DataHolder> DATA = new ArrayList<DataHolder>();

	@SuppressWarnings("unchecked")
	public <T extends DataHandler> List<T> getData(Class<T> type) {
		List<T> list = new ArrayList<T>();
		for (DataHolder data : DATA) {
			if (type.isInstance(data)) {
				list.add((T) data);
			}
		}
		return list;
	}

	public DataHolder addData(DataHolder holder) {
		DATA.remove(holder);
		return this;
	}

	public <T extends DataHandler> DataHolder removeAll(Class<T> type) {
		for (DataHolder data : DATA) {
			if (type.isInstance(data)) {
				DATA.remove(data);
			}
		}
		return this;
	}

	public List<DataHolder> getAllData() {
		return DATA;
	}

}
