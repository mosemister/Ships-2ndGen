package org.ships.configuration.parsers;

import java.util.Optional;

public interface Parser<T, S> {
	public Optional<T> parse(S var1);

	public S toString(T var1);
}
