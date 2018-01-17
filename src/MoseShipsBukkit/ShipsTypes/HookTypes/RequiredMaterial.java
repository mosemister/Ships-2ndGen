package MoseShipsBukkit.ShipsTypes.HookTypes;

import java.util.Collection;
import java.util.Set;

import org.bukkit.Material;

public interface RequiredMaterial {

	/**
	 * gets the requiredBlock
	 */

	public Set<Material> getRequiredMaterials();

	/**
	 * gets the required percent needed
	 */
	public float getRequiredPercent();
	
	/**
	 * sets the required blocks
	 */
	public void setRequiredMaterials(Collection<Material> collection);
	
	/**
	 * sets the required percent needed
	 */
	public void setRequiredPercent(float percent);

}
