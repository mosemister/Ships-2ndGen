package MoseShipsBukkit.Utils;

import org.bukkit.Material;

public class MaterialItem {
	
	Material MATERIAL;
	byte DATA;
	
	@SuppressWarnings("deprecation")
	public MaterialItem(int id, int data){
		MATERIAL = Material.getMaterial(id);
		DATA = (byte)data;
	}
	
	public Material getMaterial(){
		return MATERIAL;
	}
	
	public byte getData(){
		return DATA;
	}

}
