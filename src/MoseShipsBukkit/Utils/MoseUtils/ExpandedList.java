package MoseShipsBukkit.Utils.MoseUtils;

import java.util.ArrayList;

public class ExpandedList<T extends Object> extends ArrayList<T> {

	private static final long serialVersionUID = 1L;

	public Integer getID(T obj) {
		for (int A = 0; A < size(); A++) {
			if (obj.equals(get(A))) {
				return A;
			}
		}
		return null;
	}
}
