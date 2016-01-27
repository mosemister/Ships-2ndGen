package MoseShipsBukkit.Utils.MoseUtils;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class LimitedList<T extends Object> implements Iterator<T>, Iterable<T> {

	List<T> LIST;
	int CURRENT;

	public LimitedList(List<T> list) {
		LIST = list;
	}

	@Override
	public boolean hasNext() {
		return CURRENT < (LIST.size() - 1);
	}

	@Override
	public T next() {
		if (!hasNext()) {
			CURRENT = 0;
			throw new NoSuchElementException();
		}
		CURRENT++;
		return LIST.get(CURRENT);
	}

	@Override
	public Iterator<T> iterator() {
		CURRENT = 0;
		return this;
	}

	public T getCurrent() {
		return LIST.get(CURRENT);
	}

	public int size() {
		return LIST.size();
	}

	public T get(int arg) {
		return LIST.get(arg);
	}

}