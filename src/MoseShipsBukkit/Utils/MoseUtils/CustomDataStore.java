package MoseShipsBukkit.Utils.MoseUtils;

import java.util.ArrayList;
import java.util.List;

public class CustomDataStore {
	
	List<DataStore> DATASTORE = new ArrayList<DataStore>();
	
	public CustomDataStore(){
		
	}
	
	public DataStore getData(Class<? extends DataStore> class1){
		for(DataStore data2 : DATASTORE){
			if (data2.getClass().equals(class1)){
				return data2;
			}
		}
		return null;
	}
	
	public void inject(DataStore data){
		if (getData(data.getClass()) == null){
			DATASTORE.add(data);
		}
	}
	
	public List<DataStore> getDataStores(){
		return DATASTORE;
	}

}
