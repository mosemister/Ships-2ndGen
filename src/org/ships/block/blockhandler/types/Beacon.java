package org.ships.block.blockhandler.types;

import org.bukkit.potion.PotionEffectType;
import org.ships.block.blockhandler.Container;
import org.ships.plugin.BlockHandlerNotReady;

public class Beacon extends Container<org.bukkit.block.Beacon> {

	PotionEffectType type;
	PotionEffectType type2;

	@Override
	public void apply(org.bukkit.block.Beacon state) {
		super.apply(state);
		this.applyPrimaryPotionEffect(state);
		this.applySecondaryPotionEffect(state);
	}

	@Override
	public boolean isReady() {
		return this.getBlock().getState() instanceof org.bukkit.block.Beacon;
	}

	@Override
	public void save(boolean forRemoval) throws BlockHandlerNotReady {
		this.savePrimaryPotionEffect();
		this.saveSecondaryPotionEffect();
		super.save(forRemoval);
	}

	public void applyPrimaryPotionEffect(org.bukkit.block.Beacon state) {
		state.setPrimaryEffect(this.type);
	}

	public void savePrimaryPotionEffect() {
		this.type = this.getState().getPrimaryEffect().getType();
	}

	public void applySecondaryPotionEffect(org.bukkit.block.Beacon state) {
		state.setSecondaryEffect(this.type2);
	}

	public void saveSecondaryPotionEffect() {
		this.type2 = this.getState().getSecondaryEffect().getType();
	}
}
