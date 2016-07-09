package MoseShipsSponge.Causes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;

public enum ShipsCause {
	
	SIGN_CLICK,
	SIGN_CREATE;
	
	String NAME;
	
	private ShipsCause(){
		NAME = name();
	}
	
	private ShipsCause(String name){
		NAME = name;
	}
	
	public Cause buildCause(){
		return Cause.builder().named(NAME, this).build();
	}
	
	public Cause buildCause(Map<String, Object> causes){
		List<NamedCause> namedCauses = new ArrayList<>();
		namedCauses.add(NamedCause.of(NAME, this));
		causes.entrySet().forEach(e -> NamedCause.of(e.getKey(), e.getValue()));
		return Cause.builder().addAll(namedCauses).owner(this).build();
	}

}
