package MoseShipsBukkit.ShipsTypes;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.ships.configuration.Messages;

import MoseShipsBukkit.Ships;
import MoseShipsBukkit.MovingShip.MovementMethod;
import MoseShipsBukkit.MovingShip.MovingBlock;
import MoseShipsBukkit.StillShip.Vessel.MovableVessel;
import MoseShipsBukkit.StillShip.Vessel.ProtectedVessel;

public interface VesselType {
	public static List<VesselType> CUSTOMVESSELS = new ArrayList<VesselType>();

	public boolean checkRequirements(MovableVessel vessel, MovementMethod move, Collection<MovingBlock> blocks,
			 Player player);

	public boolean shouldFall(MovableVessel vessel);

	public File getTypeFile();
	
	public void setTypeFile(File file);

	public VesselType createClone();

	public void loadVesselFromFiveFile(ProtectedVessel vessel, File file);

	public void createConfig();

	public void loadDefault();

	public void save(ProtectedVessel vessel);

	public String getName();
	
	public void setName(String name);

	public int getDefaultSpeed();

	public Set<Material> getMoveInMaterials();

	public void setMoveInMaterials(Collection<Material> material);

	public void setDefaultSpeed(int A);

	public void setDefaultBoostSpeed(int A);

	public int getDefaultBoostSpeed();

	public boolean isAutoPilotAllowed();

	public int getMinBlocks();

	public int getMaxBlocks();

	public void setMinBlocks(int A);

	public void setMaxBlocks(int A);

	public default boolean attemptToMove(MovableVessel vessel, MovementMethod move, List<MovingBlock> blocks,
			OfflinePlayer player) {
		if (blocks.size() <= getMaxBlocks()) {
			if (blocks.size() >= getMinBlocks()) {
				return checkRequirements(vessel, move, blocks, player.getPlayer());
			} else {
				if (player != null) {
					if (Messages.isEnabled()) {
						if (player.isOnline()) {
							player.getPlayer().sendMessage(Ships
									.runShipsMessage(Messages.getShipTooSmall(blocks.size(), getMinBlocks()), true));
						}
					}
				}
				return false;
			}
		} else {
			if (player != null) {
				if (Messages.isEnabled()) {
					if (player.isOnline()) {
						player.getPlayer().sendMessage(
								Ships.runShipsMessage(Messages.getShipTooBig(blocks.size(), getMaxBlocks()), true));
					}
				}
			}
			return false;
		}
	}

	// adds custom vessels to Ships (newer method)
	public default void inject() {
		CUSTOMVESSELS.add(this);
	}

	// gets a vesselType by its name, returns VesselType if found, returns null
	// in non are found
	public static VesselType getTypeByName(String name) {
		for (VesselType type : VesselType.values()) {
			if (type.getName().equalsIgnoreCase(name)) {
				return type;
			}
		}
		return null;
	}

	// gets all the custom vesselTypes
	public static List<VesselType> customValues() {
		List<VesselType> types = CUSTOMVESSELS;
		return types;
	}

	// gets all vesselTypes no matter if they are custom or not
	public static List<VesselType> values() {
		List<VesselType> types = new ArrayList<VesselType>();
		types.addAll(CUSTOMVESSELS);
		for (VesselTypes type : VesselTypes.values()) {
			types.add(type.get());
		}
		return types;
	}
}