package MoseShipsBukkit.StillShip.Vessel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import MoseShipsBukkit.Ships;
import MoseShipsBukkit.Events.ShipsWriteEvent;
import MoseShipsBukkit.MovingShip.AutoPilotData;
import MoseShipsBukkit.MovingShip.MovingStructure;
import MoseShipsBukkit.ShipsTypes.VesselType;
import MoseShipsBukkit.ShipsTypes.HookTypes.Fuel;
import MoseShipsBukkit.StillShip.ShipsStructure;
import MoseShipsBukkit.StillShip.Vectors.BlockVector;
import MoseShipsBukkit.Utils.Exceptions.InvalidSignException;
import MoseShipsBukkit.Utils.MoseUtils.CustomDataStore;

public class BaseVessel extends CustomDataStore {

	String NAME;
	OfflinePlayer OWNER;
	VesselType TYPE;
	ShipsStructure STRUCTURE;
	Block SIGN;
	Location TELEPORTLOCATION;
	BlockFace DIRECTION;
	AutoPilotData AUTOPILOTDATA;
	Map<OfflinePlayer, BlockVector> PLAYER_LOCATION = new HashMap<OfflinePlayer, BlockVector>();

	BaseVessel(Sign sign, OfflinePlayer owner, Location teleport) throws InvalidSignException {
		OWNER = owner;
		TELEPORTLOCATION = teleport;
		SIGN = sign.getBlock();
		NAME = sign.getLine(2).replace(ChatColor.GREEN + "", "");
		DIRECTION = ((org.bukkit.material.Sign) sign.getData()).getFacing();
		STRUCTURE = new ShipsStructure(Ships.getBaseStructure(sign.getBlock()));
		TYPE = VesselType.getTypeByName(sign.getLine(1).replace(ChatColor.BLUE + "", ""));
		if (TYPE == null) {
			throw new InvalidSignException(1, sign);
		}
	}

	BaseVessel(Sign sign, String name, VesselType type, Player player) {
		NAME = name;
		TYPE = type;
		SIGN = sign.getBlock();
		STRUCTURE = new ShipsStructure(Ships.getBaseStructure(sign.getBlock()));
		org.bukkit.material.Sign sign2 = (org.bukkit.material.Sign) sign.getData();
		DIRECTION = sign2.getFacing();
		OWNER = player;
		TELEPORTLOCATION = player.getLocation();
	}

	BaseVessel(Sign sign, String name, VesselType type, OfflinePlayer player, Location loc) {
		NAME = name;
		TYPE = type;
		SIGN = sign.getBlock();
		STRUCTURE = new ShipsStructure(Ships.getBaseStructure(sign.getBlock()));
		org.bukkit.material.Sign sign2 = (org.bukkit.material.Sign) sign.getData();
		DIRECTION = sign2.getFacing();
		OWNER = player;
		TELEPORTLOCATION = loc;
		/*
		 * LOADEDVESSELS.add(this) type.save(this)
		 */
	}

	public BlockFace getFacingDirection() {
		return DIRECTION;
	}

	public OfflinePlayer getOwner() {
		return OWNER;
	}

	public VesselType getVesselType() {
		return TYPE;
	}

	public String getName() {
		return NAME;
	}

	public Location getTeleportLocation() {
		return TELEPORTLOCATION;
	}

	public AutoPilotData getAutoPilotData() {
		return AUTOPILOTDATA;
	}

	public Sign getSign() {
		return (Sign) SIGN.getState();
	}

	public ShipsStructure getStructure() {
		return STRUCTURE;
	}

	public Location getLocation() {
		return getSign().getLocation();
	}

	public File getFile() {
		File file = new File("plugins/Ships/VesselData/" + this.getName() + ".yml");
		return file;
	}

	public Map<OfflinePlayer, BlockVector> getBlockLocation() {
		return PLAYER_LOCATION;
	}

	public void setBlockLocation(Map<OfflinePlayer, BlockVector> vector) {
		PLAYER_LOCATION = vector;
	}

	public void setOwner(OfflinePlayer user) {
		OWNER = user;
	}

	public void setAutoPilotTo(AutoPilotData moveTo) {
		AUTOPILOTDATA = moveTo;
	}

	public void setStructure(ShipsStructure blocks) {
		STRUCTURE = blocks;
	}

	public void setTeleportLoc(Location loc) {
		TELEPORTLOCATION = loc;
	}

	public void setFacingDirection(BlockFace facing) {
		DIRECTION = facing;
	}

	public void setVesselType(VesselType type) {
		TYPE = type;
	}

	public boolean isMoving() {
		if (getStructure() instanceof MovingStructure) {
			return true;
		}
		return false;
	}

	public void updateLocation(Location loc, Sign sign) {
		File file = getFile();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		String signS = sign.getLocation().getX() + "," + sign.getLocation().getY() + "," + sign.getLocation().getZ()
				+ "," + sign.getLocation().getWorld().getName();
		String teleportS = loc.getX() + "," + loc.getY() + "," + loc.getZ() + "," + loc.getWorld().getName();
		ShipsWriteEvent event = new ShipsWriteEvent(file, signS, teleportS);
		if (!event.isCancelled()) {
			config.set("ShipsData.Location.Sign", signS);
			this.SIGN = sign.getBlock();
			config.set("ShipsData.Location.Teleport", teleportS);
			this.TELEPORTLOCATION = loc;
			try {
				config.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void updateStructure() {
		ShipsStructure structure = new ShipsStructure(Ships.getBaseStructure(getSign().getBlock()));
		setStructure(structure);
		org.bukkit.material.Sign sign = (org.bukkit.material.Sign) getSign().getData();
		BlockFace face;
		if (sign.isWallSign()) {
			face = sign.getAttachedFace();
		} else {
			face = sign.getFacing().getOppositeFace();
		}
		setFacingDirection(face);
	}

	public void displayInfo(Player player) {
		player.sendMessage(ChatColor.YELLOW + "[Type]" + ChatColor.AQUA + TYPE.getName());
		player.sendMessage(ChatColor.YELLOW + "[BlockCount]" + ChatColor.AQUA + STRUCTURE.getAllBlocks().size());
		player.sendMessage(ChatColor.YELLOW + "[Max/Min BlockCount]" + ChatColor.AQUA + TYPE.getMaxBlocks() + "/"
				+ TYPE.getMinBlocks());
		player.sendMessage(ChatColor.YELLOW + "[Location]" + ChatColor.AQUA + "X:" + SIGN.getX() + " Y:" + SIGN.getY()
				+ " Z:" + SIGN.getZ());
		if (TYPE instanceof Fuel) {
			for (Entry<Material, Byte> entry : ((Fuel) TYPE).getFuel().entrySet()) {
				if (entry.getValue() == -1) {
					player.sendMessage(ChatColor.YELLOW + "[FuelType]" + ChatColor.AQUA + entry.getKey().name() + ":"
							+ entry.getValue());
				} else {
					player.sendMessage(ChatColor.YELLOW + "[FuelType]" + ChatColor.AQUA + entry.getKey().name());
				}
			}
			player.sendMessage(ChatColor.YELLOW + "[Fuel Total]" + ChatColor.AQUA + ((Fuel) TYPE).getTotalFuel(this));
		}
	}

	public int getLength(boolean updateStructure) throws IOException {
		if (updateStructure) {
			updateStructure();
		}
		List<Block> blocks = getStructure().getAllBlocks();
		if ((getFacingDirection().equals(BlockFace.NORTH) || (getFacingDirection().equals(BlockFace.SOUTH)))) {
			int maxHeight = blocks.get(0).getX();
			int minHeight = blocks.get(0).getX();
			try {
				for (Block block : blocks) {
					if (block.getX() > maxHeight) {
						maxHeight = block.getX();
					}
					if (block.getX() < minHeight) {
						minHeight = block.getX();
					}
				}
			} catch (IndexOutOfBoundsException e) {
				throw new IOException("Need to update structure");
			}
			int result = maxHeight - minHeight;
			return result;
		} else {
			int maxHeight = blocks.get(0).getZ();
			int minHeight = blocks.get(0).getZ();
			try {
				for (Block block : blocks) {
					if (block.getZ() > maxHeight) {
						maxHeight = block.getZ();
					}
					if (block.getZ() < minHeight) {
						minHeight = block.getZ();
					}
				}
			} catch (IndexOutOfBoundsException e) {
				throw new IOException("Need to update structure");
			}
			int result = maxHeight - minHeight;
			return result;
		}
	}

	public int getHeight(boolean updateStructure) throws IOException {
		if (updateStructure) {
			updateStructure();
		}
		List<Block> blocks = getStructure().getAllBlocks();
		int maxHeight = blocks.get(0).getY();
		int minHeight = blocks.get(0).getY();
		try {
			for (Block block : blocks) {
				if (block.getY() > maxHeight) {
					maxHeight = block.getY();
				}
				if (block.getY() < minHeight) {
					minHeight = block.getY();
				}
			}
		} catch (IndexOutOfBoundsException e) {
			throw new IOException("Need to update structure");
		}
		int result = maxHeight - minHeight;
		return result;
	}

	public int getWidth(boolean updateStructure) throws IOException {
		if (updateStructure) {
			updateStructure();
		}
		List<Block> blocks = getStructure().getAllBlocks();
		if ((getFacingDirection().equals(BlockFace.WEST) || (getFacingDirection().equals(BlockFace.EAST)))) {
			int maxHeight = blocks.get(0).getX();
			int minHeight = blocks.get(0).getX();
			try {
				for (Block block : blocks) {
					if (block.getX() > maxHeight) {
						maxHeight = block.getX();
					}
					if (block.getX() < minHeight) {
						minHeight = block.getX();
					}
				}
			} catch (IndexOutOfBoundsException e) {
				throw new IOException("Need to update structure");
			}
			int result = maxHeight - minHeight;
			return result;
		} else {
			int maxHeight = blocks.get(0).getZ();
			int minHeight = blocks.get(0).getZ();
			try {
				for (Block block : blocks) {
					if (block.getZ() > maxHeight) {
						maxHeight = block.getZ();
					}
					if (block.getZ() < minHeight) {
						minHeight = block.getZ();
					}
				}
			} catch (IndexOutOfBoundsException e) {
				throw new IOException("Need to update structure");
			}
			int result = maxHeight - minHeight;
			return result;
		}
	}

	public List<Entity> getEntities() {
		List<Entity> entitys = this.getTeleportLocation().getWorld().getEntities();
		List<Entity> rEntitys = new ArrayList<Entity>();
		List<Block> blocks = getStructure().getAllBlocks();
		for (int A = 0; A < entitys.size(); A++) {
			Entity entity = entitys.get(A);
			Block block = entity.getLocation().getBlock().getRelative(0, -1, 0);
			if (blocks.contains(block)) {
				rEntitys.add(entity);
			}
		}
		return rEntitys;
	}

}
