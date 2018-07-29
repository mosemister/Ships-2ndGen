package org.ships.configuration.parsers;

import java.util.Optional;

public class DoubleParser implements Parser<Double, String> {
	@Override
	public Optional<Double> parse(String parse) {
		try {
			double value = Double.parseDouble(parse);
			return Optional.of(value);
		} catch (NumberFormatException e) {
			try {
				double value = Integer.parseInt(parse);
				return Optional.of(value);
			} catch (NumberFormatException value) {
				return Optional.empty();
			}
		}
	}

	@Override
	public String toString(Double string) {
		double value = string;
		if (((int) value) == value) {
			return "" + (int) value + "";
		}
		return "" + value + "";
	}
}
