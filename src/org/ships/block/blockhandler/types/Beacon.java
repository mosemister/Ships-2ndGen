package org.ships.block.blockhandler.types;

import org.bukkit.Material;
import org.bukkit.potion.PotionEffectType;
import org.ships.block.blockhandler.Container;

public class Beacon extends Container<org.bukkit.block.Beacon> {
	
	PotionEffectType type;
	PotionEffectType type2;
	
	@Override
	public void apply() {
		applyPrimaryPotionEffect();
		applySecondaryPotionEffect();
		super.apply();
	}
	
	@Override
	public void remove(Material material) {
		savePrimaryPotionEffect();
		saveSecondaryPotionEffect();
		super.remove(material);
	}
	
	public void applyPrimaryPotionEffect() {
		getState().setPrimaryEffect(this.type);
	}
	
	public void savePrimaryPotionEffect() {
		this.type = getState().getPrimaryEffect().getType();
	}
	
	public void applySecondaryPotionEffect() {
		getState().setSecondaryEffect(this.type2);
	}
	
	public void saveSecondaryPotionEffect() {
		this.type2 = getState().getSecondaryEffect().getType();
	}
	

}
