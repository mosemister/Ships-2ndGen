package MoseShips.Bypasses;

public class FinalBypass<T extends Object> {

	T OBJECT;

	public FinalBypass(T object) {
		OBJECT = object;
	}

	public T get() {
		return OBJECT;
	}

	public FinalBypass<T> set(T object) {
		OBJECT = object;
		return this;
	}

}
