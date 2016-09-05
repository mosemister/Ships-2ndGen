package MoseShips.Maps;

import java.util.HashMap;
import java.util.Map;

public class OrderedMap<A extends Object, B extends Object> extends HashMap<A, B> {

	private static final long serialVersionUID = 1L;

	public Entry<A, B> getEntry(int C) {
		int D = 0;
		for (Entry<A, B> entry : entrySet()) {
			if (D == C) {
				return entry;
			} else {
				D++;
			}
		}
		return null;
	}

	public Entry<A, B> getEntry(A key) {
		for (Entry<A, B> entry : entrySet()) {
			if (entry.getKey().equals(key)) {
				return entry;
			}
		}
		return null;
	}

	public int getID(A key) {
		int D = 0;
		for (Entry<A, B> entry : entrySet()) {
			if (key.equals(entry.getKey())) {
				return D;
			}
		}
		return -1;
	}

	public static <Y extends Object, Z extends Object> OrderedMap<Y, Z> orderByKey(Map<Y, Z> map) {
		OrderedMap<Y, Z> ret = new OrderedMap<Y, Z>();
		while (map.size() > 0) {
			Entry<Y, Z> first = map.entrySet().iterator().next();
			Z value = first.getValue();
			Y key = first.getKey();
			for (Entry<Y, Z> entry : map.entrySet()) {
				if (((Double) entry.getKey()) > ((Double) key)) {
					value = entry.getValue();
					key = entry.getKey();
				}
			}
			ret.put(key, value);
			map.remove(key, value);
		}
		return ret;
	}

	public static <Y extends Object, Z extends Object> OrderedMap<Y, Z> orderByValue(Map<Y, Z> map) {
		OrderedMap<Y, Z> ret = new OrderedMap<Y, Z>();
		while (map.size() > 0) {
			Entry<Y, Z> first = map.entrySet().iterator().next();
			Z value = first.getValue();
			Y key = first.getKey();
			for (Entry<Y, Z> entry : map.entrySet()) {
				if (((Double) entry.getValue()) > ((Double) value)) {
					value = entry.getValue();
					key = entry.getKey();
				}
			}
			ret.put(key, value);
			map.remove(key, value);
		}
		return ret;
	}

}