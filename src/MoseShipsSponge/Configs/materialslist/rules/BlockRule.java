package MoseShipsSponge.Configs.materialslist.rules;

import java.util.Optional;

import MoseShipsSponge.Movement.MovingBlock;

public interface BlockRule {
	
	public String getDisplayName();
	
	public Optional<String> onBlockDetect(MovingBlock block);

}
