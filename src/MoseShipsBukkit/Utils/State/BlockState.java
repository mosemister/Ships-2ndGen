package MoseShipsBukkit.Utils.State;

import org.bukkit.Material;

public class BlockState {

	Material MATERIAL;
	byte DATA;

	public BlockState(Material material, byte data) {
		MATERIAL = material;
		DATA = data;
	}

	public BlockState(Material material) {
		MATERIAL = material;
		DATA = (byte) -1;
	}

	public Material getMaterial() {
		return MATERIAL;
	}

	public byte getData() {
		return DATA;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof BlockState){
			BlockState state = (BlockState)obj;
			if((state.getMaterial().equals(getMaterial())) && (state.getData() == getData())){
				return true;
			}
		}
		return false;
	}

}
