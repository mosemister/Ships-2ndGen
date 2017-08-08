package MoseShipsBukkit.Utils;

public class SOptional <T extends Object> {
	
	T g_obj;
	
	public SOptional() {
		this(null);
	}
	
	public SOptional(T obj) {
		g_obj = obj;
	}

	public T get() {
		return g_obj;
	}
	
	public boolean isPresent() {
		return (g_obj != null);
	}

}
