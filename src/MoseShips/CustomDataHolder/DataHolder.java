package MoseShips.CustomDataHolder;

import java.util.ArrayList;
import java.util.List;

public class DataHolder {

	protected List<DataHandler> DATA = new ArrayList<DataHandler>();

	@SuppressWarnings("unchecked")
	public <T extends DataHandler> List<T> getData(Class<T> type) {
		List<T> list = new ArrayList<T>();
		for (DataHandler data : DATA) {
			if (type.isInstance(data)) {
				list.add((T) data);
			}
		}
		return list;
	}

	public DataHolder addData(DataHandler holder) {
		DATA.remove(holder);
		return this;
	}

	public <T extends DataHandler> DataHolder removeAll(Class<T> type) {
		for (DataHandler data : DATA) {
			if (type.isInstance(data)) {
				DATA.remove(data);
			}
		}
		return this;
	}

	public List<DataHandler> getAllData() {
		return DATA;
	}

}
