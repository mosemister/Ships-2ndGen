package MoseShipsBukkit.StillShip.Vessel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import MoseShipsBukkit.ShipsTypes.VesselType;
import MoseShipsBukkit.Utils.Exceptions.InvalidSignException;

public class ProtectedVessel extends BaseVessel {

	List<UUID> SUBPILOTS = new ArrayList<UUID>();
	boolean INVINCIBLE;

	ProtectedVessel(Sign sign, OfflinePlayer owner, Location teleport) throws InvalidSignException {
		super(sign, owner, teleport);
	}

	ProtectedVessel(Sign sign, String name, VesselType type, Player player) {
		super(sign, name, type, player);
	}

	ProtectedVessel(Sign sign, String name, VesselType type, OfflinePlayer player, Location loc) {
		super(sign, name, type, player, loc);
	}

	public List<UUID> getSubPilots() {
		return SUBPILOTS;
	}

	@Deprecated
	public boolean isProtected() {
		return true;
	}

	public boolean isInvincible() {
		return INVINCIBLE;
	}

	@Deprecated
	public void setProtectVessel(boolean args) {
	}

	public void setInvincible(boolean args) {
		INVINCIBLE = args;
	}

	public void save() {
		this.getVesselType().save(this);
	}

}
