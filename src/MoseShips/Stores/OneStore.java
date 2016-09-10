package MoseShips.Stores;

public class OneStore<A extends Object> {

	A ONE_STORE;

	public OneStore(A one) {
		ONE_STORE = one;
	}

	public A getFirst() {
		return ONE_STORE;
	}

	public OneStore<A> setFirst(A first) {
		ONE_STORE = first;
		return this;
	}

}
