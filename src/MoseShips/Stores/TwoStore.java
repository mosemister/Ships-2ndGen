package MoseShips.Stores;

public class TwoStore <A extends Object, B extends Object>{
	
	A ONE_STORE;
	B TWO_STORE;
	
	public TwoStore(A one, B two){
		ONE_STORE = one;
		TWO_STORE = two;
	}

	public A getFirst(){
		return ONE_STORE;
	}
	
	public TwoStore<A, B> setFirst(A first){
		ONE_STORE = first;
		return this;
	}
	
	public B getSecond(){
		return TWO_STORE;
	}
	
	public TwoStore<A, B> setSecond(B second){
		TWO_STORE = second;
		return this;
	}

}
