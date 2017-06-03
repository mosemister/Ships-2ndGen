package MoseShipsBukkit.Events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import MoseShipsBukkit.Utils.SOptional;

public class ShipsCause {

	List<Object> g_causes = new ArrayList<Object>();

	public ShipsCause(Object... cause) {
		g_causes = Arrays.asList(cause);
	}

	public ShipsCause(List<Object> list) {
		g_causes = list;
	}

	public ShipsCause(ShipsCause cause, Object... tCause) {
		g_causes.addAll(cause.get());
		g_causes.addAll(Arrays.asList(tCause));
	}

	@SuppressWarnings("unchecked")
	public ShipsCause(ShipsCause cause, List<Object> tCause) {
		g_causes.addAll(cause.get());
		g_causes.addAll(Arrays.asList(tCause));
	}

	@SuppressWarnings("unchecked")
	public <T extends Object> SOptional<T> first(Class<T> type) {
		for (Object obj : g_causes) {
			if (type.isInstance(obj)) {
				return new SOptional<T>((T) obj);
			}
		}
		return new SOptional<T>();
	}

	public SOptional<Object> first() {
		if (g_causes.size() == 0) {
			return new SOptional<Object>();
		} else {
			return new SOptional<Object>(g_causes.get(0));
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends Object> SOptional<T> last(Class<T> type) {
		for (int A = g_causes.size() - 1; A > 0; A--) {
			Object obj = g_causes.get(A);
			if (type.isInstance(obj)) {
				return new SOptional<T>((T) obj);
			}
		}
		return new SOptional<T>();
	}

	public SOptional<Object> last() {
		if (g_causes.size() == 0) {
			return new SOptional<Object>();
		} else {
			return new SOptional<Object>(g_causes.get(g_causes.size() - 1));
		}
	}

	public Set<Object> get() {
		return new HashSet<Object>(g_causes);
	}

}
