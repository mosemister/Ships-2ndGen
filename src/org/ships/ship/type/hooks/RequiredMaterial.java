package org.ships.ship.type.hooks;

import java.util.Collection;
import java.util.Set;

import org.bukkit.Material;

public interface RequiredMaterial {
	public Set<Material> getRequiredMaterials();

	public float getRequiredPercent();

	public void setRequiredMaterials(Collection<Material> var1);

	public void setRequiredPercent(float var1);
}
