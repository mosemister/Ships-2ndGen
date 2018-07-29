package org.ships.ship.type;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.ships.block.MovingBlock;
import org.ships.configuration.Messages;
import org.ships.plugin.Ships;
import org.ships.ship.Ship;
import org.ships.ship.movement.MovementMethod;
import org.ships.ship.type.types.VesselTypes;

public interface VesselType {
	public static final List<VesselType> CUSTOMVESSELS = new ArrayList<VesselType>();

	public boolean checkRequirements(Ship var1, MovementMethod var2, Collection<MovingBlock> var3, Player var4);

	public boolean shouldFall(Ship var1);

	public File getTypeFile();

	public void setTypeFile(File var1);

	public VesselType createClone();

	public void loadVesselFromFiveFile(Ship var1, File var2);

	public void createConfig();

	public void loadDefault();

	public void save(Ship var1);

	public String getName();

	public void setName(String var1);

	public int getDefaultSpeed();

	public Set<Material> getMoveInMaterials();

	public void setMoveInMaterials(Collection<Material> var1);

	public void setDefaultSpeed(int var1);

	public void setDefaultBoostSpeed(int var1);

	public int getDefaultBoostSpeed();

	public boolean isAutoPilotAllowed();

	public int getMinBlocks();

	public int getMaxBlocks();

	public void setMinBlocks(int var1);

	public void setMaxBlocks(int var1);

	default public boolean attemptToMove(Ship vessel, MovementMethod move, Collection<MovingBlock> blocks, OfflinePlayer player) {
		if (blocks.size() <= this.getMaxBlocks()) {
			if (blocks.size() >= this.getMinBlocks()) {
				return this.checkRequirements(vessel, move, blocks, player.getPlayer());
			}
			if (player != null && Messages.isEnabled() && player.isOnline()) {
				player.getPlayer().sendMessage(Ships.runShipsMessage(Messages.getShipTooSmall(blocks.size(), this.getMinBlocks()), true));
			}
			return false;
		}
		if (player != null && Messages.isEnabled() && player.isOnline()) {
			player.getPlayer().sendMessage(Ships.runShipsMessage(Messages.getShipTooBig(blocks.size(), this.getMaxBlocks()), true));
		}
		return false;
	}

	default public void inject() {
		CUSTOMVESSELS.add(this);
	}

	public static VesselType getTypeByName(String name) {
		for (VesselType type : VesselType.values()) {
			if (!type.getName().equalsIgnoreCase(name))
				continue;
			return type;
		}
		return null;
	}

	public static List<VesselType> customValues() {
		List<VesselType> types = CUSTOMVESSELS;
		return types;
	}

	public static List<VesselType> values() {
		ArrayList<VesselType> types = new ArrayList<VesselType>();
		types.addAll(CUSTOMVESSELS);
		for (VesselType type : VesselTypes.values()) {
			types.add(type);
		}
		return types;
	}
}
