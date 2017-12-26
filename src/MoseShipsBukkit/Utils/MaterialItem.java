package MoseShipsBukkit.Utils;

import org.bukkit.Material;

@Deprecated
public class MaterialItem {

	Material MATERIAL;
	byte DATA;

	public MaterialItem(int id, int data) {
		MATERIAL = Material.getMaterial(id);
		DATA = (byte) data;
	}
	
	public MaterialItem(Material material, byte data) {
		MATERIAL = material;
		DATA = data;
	}

	public Material getMaterial() {
		return MATERIAL;
	}

	public byte getData() {
		return DATA;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof MaterialItem)) {
			return false;
		}
		MaterialItem item = (MaterialItem)obj;
		if((item.getMaterial().equals(getMaterial()))) {
			if((item.getData() == -1) || (getData() == -1)) {
				return true;
			}else if(item.getData() == getData()) {
				return true;
			}
		}
		return false;
	}
}
