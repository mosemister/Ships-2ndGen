package MoseShipsSponge.Configs.materialslist;

import org.spongepowered.api.block.BlockType;

import MoseShipsSponge.Configs.materialslist.rules.BlockRule;

public class BlockPowers {
	
	BlockType type;
	BlockRule rule;
	int limitPerShip;
	
	public BlockPowers(BlockType type, BlockRule rules, int limit) {
		this.type = type;
		this.rule = rules;
		this.limitPerShip = limit;
	}
	
	public BlockType getType() {
		return type;
	}
	
	public BlockRule getRule() {
		return rule;
	}
	
	public int getLimitPerShip() {
		return limitPerShip;
	}

}
