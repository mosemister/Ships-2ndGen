package MoseShipsBukkit.ShipTypes.NormalRequirements;

import java.util.List;

import org.bukkit.Material;

public interface RequiredBlock {

	List<Material> getRequiredBlock();
	void setRequiredBlock(List<Material> material);
	
}
