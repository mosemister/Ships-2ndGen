package MoseShips.Stores;

public class TwoStore<A extends Object, B extends Object> extends OneStore<A> {

	B TWO_STORE;

	public TwoStore(A one, B two) {
		super(one);
		TWO_STORE = two;
	}

	public B getSecond() {
		return TWO_STORE;
	}

	public TwoStore<A, B> setSecond(B second) {
		TWO_STORE = second;
		return this;
	}

}
