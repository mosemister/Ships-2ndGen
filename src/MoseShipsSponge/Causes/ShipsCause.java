package MoseShipsSponge.Causes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;

import MoseShipsSponge.Plugin.ShipsMain;

public enum ShipsCause {

	SIGN_CLICK,
	SIGN_CREATE,
	BLOCK_MOVING;

	String NAME;

	private ShipsCause() {
		NAME = name();
	}

	private ShipsCause(String name) {
		NAME = name;
	}

	public Cause buildCause() {
		Cause.Builder builder = Cause.builder();
		builder.owner(ShipsMain.getPlugin().getContainer());
		builder.named(NAME, this);
		return builder.build();
	}

	public Cause buildCause(Map<String, Object> causes) {
		List<NamedCause> namedCauses = new ArrayList<>();
		namedCauses.add(NamedCause.of(NAME, this));
		causes.entrySet().stream().forEach(e -> namedCauses.add(NamedCause.of(e.getKey(), e.getValue())));
		return Cause.builder().addAll(namedCauses).owner(ShipsMain.getPlugin().getContainer()).build();
	}

}
