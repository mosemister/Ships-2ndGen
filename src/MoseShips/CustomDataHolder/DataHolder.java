package MoseShips.CustomDataHolder;

import java.util.ArrayList;
import java.util.List;

public class DataHolder {
	
	protected List<DataHolder> DATA = new ArrayList<>();
	
	@SuppressWarnings("unchecked")
	public <T extends DataHandler> List<T> getData(Class<T> type){
		List<T> list = new ArrayList<>();
		DATA.stream().filter(d -> type.isInstance(d)).forEach(d -> list.add((T)d));
		return list;
	}
	
	public DataHolder addData(DataHolder holder){
		DATA.remove(holder);
		return this;
	}
	
	public <T extends DataHandler> DataHolder removeAll(Class<T> type){
		DATA.stream().filter(d -> type.isInstance(d)).forEach(d -> DATA.remove(d));
		return this;
	}
	
	public List<DataHolder> getAllData(){
		return DATA;
	}

}
