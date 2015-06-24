package MoseShipsBukkit.StillShip;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Dropper;
import org.bukkit.block.Furnace;
import org.bukkit.block.Hopper;
import org.bukkit.block.Sign;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.cnaude.chairs.api.ChairsAPI;

import MoseShipsBukkit.Ships;
import MoseShipsBukkit.Events.ShipMovingEvent;
import MoseShipsBukkit.MovingShip.MovementMethod;
import MoseShipsBukkit.MovingShip.MovingBlock;
import MoseShipsBukkit.MovingShip.MovingStructure;
import MoseShipsBukkit.ShipTypes.VesselType;
import MoseShipsBukkit.Utils.BlockConverter;
import MoseShipsBukkit.Utils.ConfigLinks.Config;
import MoseShipsBukkit.Utils.ConfigLinks.MaterialsList;
import MoseShipsBukkit.Utils.ConfigLinks.Messages;
import MoseShipsBukkit.Utils.MoseUtils.CustomDataStore;
import MoseShipsBukkit.Utils.OtherPlugins.OtherPlugins;

public class Vessel extends CustomDataStore{
	
	String NAME;
	OfflinePlayer OWNER;
	VesselType TYPE;
	ShipsStructure STRUCTURE;
	Sign SIGN;
	Location TELEPORTLOCATION;
	BlockFace DIRECTION;
	Location AUTOPILOTTO;
	boolean PROTECTVESSEL;
	double COST;
	
	static List<Vessel> LOADEDVESSELS = new ArrayList<Vessel>();
	
	public Vessel(Sign sign, String name, VesselType type, Player player){
		NAME = name;
		TYPE = type;
		SIGN = sign;
		STRUCTURE = new ShipsStructure(Ships.getBaseStructure(sign.getBlock()));
		org.bukkit.material.Sign sign2 = (org.bukkit.material.Sign)sign.getData();
		DIRECTION = sign2.getFacing();
		OWNER = player;
		TELEPORTLOCATION = player.getLocation();
		LOADEDVESSELS.add(this);
		type.save(this);
	}
	
	public Vessel(Sign sign, String name, VesselType type, OfflinePlayer player, Location loc){
		NAME = name;
		TYPE = type;
		SIGN = sign;
		STRUCTURE = new ShipsStructure(Ships.getBaseStructure(sign.getBlock()));
		org.bukkit.material.Sign sign2 = (org.bukkit.material.Sign)sign.getData();
		DIRECTION = sign2.getFacing();
		OWNER = player;
		TELEPORTLOCATION = loc;
		LOADEDVESSELS.add(this);
		type.save(this);
	}
	
	public BlockFace getFacingDirection(){
		return DIRECTION;
	}
	
	public OfflinePlayer getOwner(){
		return OWNER;
	}
	
	public VesselType getVesselType(){
		return TYPE;
	}
	
	public String getName(){
		return NAME;
	}
	
	public Location getTeleportLocation(){
		return TELEPORTLOCATION;
	}
	
	public Location getAutoPilotTo(){
		return AUTOPILOTTO;
	}
	
	public Sign getSign(){
		return SIGN;
	}
	
	public ShipsStructure getStructure(){
		return STRUCTURE;
	}
	
	public void setOwner(OfflinePlayer user){
		OWNER = user;
	}
	
	public void setAutoPilotTo(Location moveTo){
		AUTOPILOTTO = moveTo;
	}
	
	public void setStructure(ShipsStructure blocks){
		STRUCTURE = blocks;
	}
	
	public void setTeleportLoc(Location loc){
		TELEPORTLOCATION = loc;
	}
	
	public void setFacingDirection(BlockFace facing){
		DIRECTION = facing;
	}
	
	public void setVesselType(VesselType type){
		TYPE = type;
	}
	
	public void save(){
		this.getVesselType().save(this);
	}
	
	public void remove(){
		LOADEDVESSELS.remove(this);
		File file = getFile();
		file.delete();
	}
	
	public boolean isProtected(){
		return PROTECTVESSEL;
	}
	
	public double getCost(){
		return COST;
	}
	
	public void setProtectVessel(boolean args){
		PROTECTVESSEL = args;
	}
	
	public boolean isMoving(){
		if (getStructure() instanceof MovingStructure){
			return true;
		}
		return false;
	}
	
	public boolean safelyMoveTowardsLocation(Location moveTo, int speed, Player player){
		Location loc = this.getSign().getLocation();
		BlockFace face = this.getFacingDirection();
		MovementMethod move = MovementMethod.getMovementDirection(face);
		boolean value = false;
		if (loc.getY() == moveTo.getY()){
			if (loc.getX() == moveTo.getX()){
				//try Z
				if (loc.getZ() == moveTo.getZ()){
					return false;
				}
				if (loc.getZ() > moveTo.getZ()){
					if ((loc.getZ() + speed) > moveTo.getZ()){
						value = moveVessel(MovementMethod.MOVE_NEGATIVE_Z, 1, player);
					}else{
						if (move.equals(MovementMethod.MOVE_NEGATIVE_Z)){
							value = moveVessel(MovementMethod.MOVE_NEGATIVE_Z, speed, player);
						}else{
							value = moveVessel(MovementMethod.ROTATE_RIGHT, 90, player);
						}
					}
				}else{
					if ((loc.getZ() - speed) < moveTo.getZ()){
						value = moveVessel(MovementMethod.MOVE_POSITIVE_Z, 1, player);
					}else{
						if (move.equals(MovementMethod.MOVE_POSITIVE_Z)){
							value = moveVessel(MovementMethod.MOVE_POSITIVE_Z, speed, player);
						}else{
							value = moveVessel(MovementMethod.ROTATE_RIGHT, 90, player);
						}
					}
				}
			}else{
				//try X
				if (loc.getX() > moveTo.getX()){
					if ((loc.getX() + speed) > moveTo.getX()){
						value = moveVessel(MovementMethod.MOVE_NEGATIVE_X, 1, player);
					}else{
						if (move.equals(MovementMethod.MOVE_NEGATIVE_X)){
							value = moveVessel(MovementMethod.MOVE_NEGATIVE_X, speed, player);
						}else{
							value = moveVessel(MovementMethod.ROTATE_RIGHT, 90, player);
						}
					}
				}else{
					if ((loc.getX() - speed) < moveTo.getX()){
						value = moveVessel(MovementMethod.MOVE_POSITIVE_X, 1, player);
					}else{
						if (move.equals(MovementMethod.MOVE_POSITIVE_X)){
							value = moveVessel(MovementMethod.MOVE_POSITIVE_X, speed, player);
						}else{
							value = moveVessel(MovementMethod.ROTATE_RIGHT, 90, player);
						}
					}
				}
			}
		}else{
			//try Y
			if (loc.getY() > moveTo.getY()){
				if ((loc.getY() + speed) > moveTo.getY()){
					value = moveVessel(MovementMethod.MOVE_UP, 1, player);
				}else{
					value = moveVessel(MovementMethod.MOVE_UP, speed, player);
				}
			}else{
				if ((loc.getY() - speed) > moveTo.getY()){
					value = moveVessel(MovementMethod.MOVE_DOWN, 1, player);
				}else{
					value = moveVessel(MovementMethod.MOVE_DOWN, speed, player);
				}
			}
		}
		return value;
	}
	
	public boolean moveVessel(MovementMethod move, int speed, Player player){
		if ((player == null) || (player.hasPermission("ships." + getName() + ".use")) || (player.hasPermission("ships." + getVesselType().getName() + ".use")) || (player.hasPermission("ships.*.use"))){
			if (move == null){
				if (player != null){
					player.sendMessage(Ships.runShipsMessage("Can not translate block into move", false));
					return false;
				}
			}
			if (speed != 0){
				move.setSpeed(speed);
			}
			List<MovingBlock> blocks = new ArrayList<MovingBlock>();
			for (Block block : STRUCTURE.getPriorityBlocks()){
				MovingBlock block2 = new MovingBlock(block, this, move);
				if (isBlocked(block2)){
					if (player != null){
						if (Messages.isEnabled()){
							player.sendMessage(Ships.runShipsMessage(Messages.getFoundInWay(block2.getMovingTo().getBlock().getType().name()), true));
						}
					}
					return false;
				}else{
					blocks.add(block2);
				}
			}
			for (SpecialBlock block : STRUCTURE.getSpecialBlocks()){
				MovingBlock block2 = new MovingBlock(block, this, move);
				if (isBlocked(block2)){
					if (player != null){
						if (Messages.isEnabled()){
							player.sendMessage(Ships.runShipsMessage(Messages.getFoundInWay(block2.getMovingTo().getBlock().getType().name()), true));
						}
					}
					return false;
				}else{
					blocks.add(block2);
				}
			}
			for (Block block : STRUCTURE.getStandardBlocks()){
				MovingBlock block2 = new MovingBlock(block, this, move);
				if (isBlocked(block2)){
					if (player != null){
						if (Messages.isEnabled()){
							player.sendMessage(Ships.runShipsMessage(Messages.getFoundInWay(block2.getMovingTo().getBlock().getType().name()), true));
						}
					}
					return false;
				}else{
					blocks.add(block2);
				}
			}
			for (Block block : STRUCTURE.getAirBlocks()){
				MovingBlock block2 = new MovingBlock(block, this, move);
				if (isBlocked(block2)){
					if (player != null){
						if (Messages.isEnabled()){
							player.sendMessage(Ships.runShipsMessage(Messages.getFoundInWay(block2.getMovingTo().getBlock().getType().name()), true));
						}
					}
					return false;
				}else{
					blocks.add(block2);
				}
			}
			ConsoleCommandSender console = Bukkit.getConsoleSender();
			YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
			for(MovingBlock block : blocks){
				if (config.getBoolean("FactionsSupport.enabled")){
					if (OtherPlugins.isFactionsLoaded()){
						if (OtherPlugins.isLocationOnFactionsLand(block.getMovingTo())){
							if (player != null){
								if (Messages.isEnabled()){
									player.sendMessage(Ships.runShipsMessage("Faction in way.", true));
								}
							}
							return false;
						}
					}else{
						console.sendMessage(Ships.runShipsMessage("Ships has Factions enabled but can not hook into it.", true));
					}
				}
				if (config.getBoolean("WorldGuardSupport.enabled")){
					if (OtherPlugins.isWorldGuardLoaded()){
						if (OtherPlugins.isLocationInWorldGuardRegion(block.getMovingTo())){
							if (player != null){
								if (Messages.isEnabled()){
									player.sendMessage(Ships.runShipsMessage("Region in way.", true));
								}
							}
							return false;
						}
					}else{
						console.sendMessage(Ships.runShipsMessage("Ships has WorldGuard enabled but can not hook into it.", true));
					}
				}
				if (config.getBoolean("WorldBorderSupport.enabled")){
					if (OtherPlugins.isWorldBorderLoaded()){
						if (OtherPlugins.isLocationOutOfWorldBorder(block.getMovingTo())){
							if (player != null){
								if (Messages.isEnabled()){
									player.sendMessage(Ships.runShipsMessage("Edge of map.", true));
								}
							}
							return false;
						}
					}else{
						console.sendMessage(Ships.runShipsMessage("Ships has WorldBorder enabled but can not hook into it.", true));
					}
				}
				if (config.getBoolean("GriefPreventionPluginSupport.enabled")){
					if (OtherPlugins.isGriefPreventionLoaded()){
						if (OtherPlugins.isLocationInGriefPreventionClaim(block.getMovingTo(), this)){
							if (player != null){
								if (Messages.isEnabled()){
									player.sendMessage(Ships.runShipsMessage("Claim in way.", true));
								}
							}
						}
					}
				}
				if (config.getBoolean("TownySupport.enabled")){
					if (OtherPlugins.isTownyLoaded()){
						if (OtherPlugins.isTownyHookLoaded()){
							if (OtherPlugins.isLocationInTownyLand(block.getMovingTo())){
								if (player != null){
									if (Messages.isEnabled()){
										player.sendMessage(Ships.runShipsMessage("Town in way.", true));
									}
								}
							}
						}else{
							console.sendMessage(Ships.runShipsMessage("Needs ShipsTownyHook.jar for compatibility with Towny. Download at dev.bukkit.org/bukkit-plugins/ships/files/.", true));
						}
					}else{
						console.sendMessage(Ships.runShipsMessage("Ships has Towny enabled but can not hook into it.", true));
					}
				}
			}
			if (this.getVesselType().CheckRequirements(this, move, blocks, player)){
				MovingStructure structure = new MovingStructure(blocks);
				this.setStructure(structure);
				ShipMovingEvent event = new ShipMovingEvent(player, this, move, structure);
				Bukkit.getPluginManager().callEvent(event);
				if (!event.isCancelled()){
					forceMove(move, event.getStructure().getAllMovingBlocks());
					return true;
				}
			}
		}
		return false;
	}
	
	@SuppressWarnings("deprecation")
	boolean isBlocked(MovingBlock block){
		Location loc = block.getMovingTo();
		Block block2 = loc.getBlock();
		if (!isPartOfVessel(loc)){
			MaterialsList matList = MaterialsList.getMaterialsList();
			if (matList.contains(block2.getType(), block2.getData(), false)){
				return false;
			}else if (isMoveInBlock(loc)){
				return false;
			}
		}else{
			return false;
		}
		return true;
	}
	
	boolean isMoveInBlock(Location loc){
		for(Material material : this.getVesselType().getMoveInMaterials()){
			if (material.equals(loc.getBlock().getType())){
				return true;
			}
		}
		return false;
	}
	
	boolean isPartOfVessel(Location loc){
		ShipsStructure str = STRUCTURE;
		for (Block block : str.getAllBlocks()){
			if (block.getLocation().equals(loc)){
				return true;
			}
		}
		return false;
	}
	
	List<Entity> getEntitys(){
		List<Entity> entitys = this.getTeleportLocation().getWorld().getEntities();
		List<Entity> rEntitys = new ArrayList<Entity>();
		List<Block> blocks = getStructure().getAllBlocks();
		for (int A = 0; A < entitys.size(); A++){
			Entity entity = entitys.get(A);
			Block block = entity.getLocation().getBlock().getRelative(0, -1, 0);
			if (blocks.contains(block)){
				rEntitys.add(entity);
			}
		}
		return rEntitys;
	}
	
	public void forceTeleport(Location loc){
		List<MovingBlock> blocks = getTeleportStructure(loc);
		forceMove(MovementMethod.TELEPORT, blocks);
	}
	
	public void forceMove(MovementMethod move, int speed, Player player){
		List<MovingBlock> blocks = new ArrayList<MovingBlock>();
		for (Block block : STRUCTURE.getPriorityBlocks()){
			MovingBlock block2 = new MovingBlock(block, this, move);
			blocks.add(block2);
		}
		for (SpecialBlock block : STRUCTURE.getSpecialBlocks()){
			MovingBlock block2 = new MovingBlock(block, this, move);
			blocks.add(block2);
		}
		for (Block block : STRUCTURE.getStandardBlocks()){
			MovingBlock block2 = new MovingBlock(block, this, move);
			blocks.add(block2);
		}
		for (Block block : STRUCTURE.getAirBlocks()){
			MovingBlock block2 = new MovingBlock(block, this, move);
			blocks.add(block2);
		}
		forceMove(move, blocks);
	}
	
	@SuppressWarnings("deprecation")
	void forceMove(MovementMethod move, List<MovingBlock> blocks){
		//remove all blocks (priority first)
		Config configuration = Config.getConfig();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(configuration.getFile());
		for(int A = 0; A < blocks.size(); A++){
			MovingBlock block = blocks.get(A);
			if (block.getSpecialBlock() != null){
				clearInventory(block);
			}
			if (block.getBlock().getLocation().getY() > config.getInt("World.defaultWaterLevel")){
				block.getBlock().setType(Material.AIR);
			}else{
				block.getBlock().setType(Material.STATIONARY_WATER);
			}
		}
		//place all blocks (priority last)
		for(int A = blocks.size() - 1;A >= 0; A--){
			MovingBlock block = blocks.get(A);
			Block bBlock = block.getMovingTo().getBlock();
			if ((move.equals(MovementMethod.ROTATE_LEFT) || (move.equals(MovementMethod.ROTATE_RIGHT)))){
				byte data = BlockConverter.convertRotation(move, block, block.getData());
				bBlock.setTypeIdAndData(block.getId(), data, false);
			}else{
				bBlock.setTypeIdAndData(block.getId(), block.getData(), false);
			}
			if (block.getSpecialBlock() != null){
				setInventory(block);
			}
		}
		MovingBlock mBlock = new MovingBlock(getTeleportLocation().getBlock(), this, move);
		TELEPORTLOCATION = mBlock.getMovingTo();
		updateLocation(mBlock.getMovingTo(), getSign());
		moveEntitys(move);
		updateStructure();
	}

	void moveEntitys(MovementMethod move){
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		for	(Entity entity : getEntitys()){
			Inventory inv = null;
			boolean isSitting = false;
			if (entity instanceof Player){
				Player player = (Player)entity;
				if (config.getBoolean("Inventory.keepInventorysOpen")){
					Inventory inv2 = player.getOpenInventory().getTopInventory();
					if (!inv2.getTitle().equals("container.crafting")){
						inv = inv2;
						player.closeInventory();
					}
				}
				if (config.getBoolean("ChairsReloadedSupport.enabled")){
					if (OtherPlugins.isChairsLoaded()){
						isSitting = ChairsAPI.isSitting(player);
					}else{
						Bukkit.getConsoleSender().sendMessage(Ships.runShipsMessage("Chairs support has been enabled but can not find ChairsReloaded", true));
					}
				}
			}
			Location loc = entity.getLocation();
			MovingBlock block = new MovingBlock(loc.getBlock(), this, move);
			Location loc2 = block.getMovingTo();
			double X = loc.getX() - Math.floor(loc.getX());
			double Y = loc.getY() - Math.floor(loc.getY());
			double Z = loc.getZ() - Math.floor(loc.getZ());
			loc2.add(X, Y, Z);
			loc2.setPitch(loc.getPitch());
			loc2.setYaw(loc.getYaw());
			entity.teleport(loc2);
			if (entity instanceof Player){
				Player player = (Player)entity;
				if (config.getBoolean("Inventory.keepInventorysOpen")){
					if (inv != null){
						player.openInventory(inv);
					}
				}
				if (isSitting){
					OtherPlugins.sit(player, loc2);
				}
			}
		}
	}
	
	void clearInventory(MovingBlock block){
		Block bBlock = block.getSpecialBlock().getBlock();
		if (bBlock.getState() instanceof Furnace){
			Furnace furn = (Furnace) bBlock.getState();
			furn.getInventory().clear();
		}
		if (bBlock.getState() instanceof Chest){
			Chest chest = (Chest) bBlock.getState();
			chest.getInventory().clear();
		}
		if (bBlock.getState() instanceof Dispenser){
			Dispenser disp = (Dispenser)bBlock.getState();
			disp.getInventory().clear();
		}
		if (bBlock.getState() instanceof Hopper){
			Hopper hop = (Hopper)bBlock.getState();
			hop.getInventory().clear();
		}
		if (bBlock.getState() instanceof Dropper){
			Dropper drop = (Dropper) bBlock.getState();
			drop.getInventory().clear();
		}
	}
	
	void setInventory(MovingBlock block){
		SpecialBlock sBlock = block.getSpecialBlock();
		Block nBlock = block.getMovingTo().getBlock();
		if (nBlock.getState() instanceof Furnace){
			Furnace furn = (Furnace)nBlock.getState();
			FurnaceInventory inv = furn.getInventory();
			Map<Integer, ItemStack> stack = sBlock.getItems();
			for(Entry<Integer, ItemStack> entry : stack.entrySet()){
				if (entry.getKey() == 1){
					inv.setFuel(entry.getValue());
				}
				if (entry.getKey() == 2){
					inv.setResult(entry.getValue());
				}
				if (entry.getKey() == 3){
					inv.setSmelting(entry.getValue());
				}
			}
		}
		if (nBlock.getState() instanceof Chest){
			Chest chest = (Chest) nBlock.getState();
			Inventory inv = chest.getInventory();
			for(Entry<Integer, ItemStack> entry : sBlock.getItems().entrySet()){
				if (sBlock.isRightChest()){
					int A = entry.getKey();
					if ((A > 26) && (A <= 64)){
						inv.setItem(A - 27, entry.getValue());
					}
				}else{
					int A = entry.getKey(); 
					if (A <= 26){
						inv.setItem(A, entry.getValue());
					}
				}
			}
		}
		if (nBlock.getState() instanceof Hopper){
			Hopper hop = (Hopper)nBlock.getState();
			Inventory inv = hop.getInventory();
			for(Entry<Integer, ItemStack> entry : sBlock.getItems().entrySet()){
				inv.setItem(entry.getKey(), entry.getValue());
			}
		}
		if (nBlock.getState() instanceof Dropper){
			Dropper drop = (Dropper)nBlock.getState();
			Inventory inv = drop.getInventory();
			for(Entry<Integer, ItemStack> entry : sBlock.getItems().entrySet()){
				inv.setItem(entry.getKey(), entry.getValue());
			}
		}
		if (nBlock.getState() instanceof Dispenser){
			Dispenser disp = (Dispenser)nBlock.getState();
			Inventory inv = disp.getInventory();
			for(Entry<Integer, ItemStack> entry : sBlock.getItems().entrySet()){
				inv.setItem(entry.getKey(), entry.getValue());
			}
		}
		if (nBlock.getState() instanceof Sign){
			Sign sign = (Sign) nBlock.getState();
			sign.setLine(0, sBlock.getLine(1));
			sign.setLine(1, sBlock.getLine(2));
			sign.setLine(2, sBlock.getLine(3));
			sign.setLine(3, sBlock.getLine(4));
			sign.update();
			if (sign.getLine(0).equals(ChatColor.YELLOW + "[Ships]")){
				SIGN = sign;
				updateLocation(getTeleportLocation(), sign);
				YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
				if (config.getBoolean("Signs.ForceUsernameOnLicenceSign")){
					sign.setLine(3, ChatColor.GREEN + this.getOwner().getName());
				}
			}
		}
	}
	
	public File getFile(){
		File file = new File("plugins/Ships/VesselData/" + this.getName() + ".yml");
		return file;
	}
	
	public void updateStructure(){
		ShipsStructure structure = new ShipsStructure(Ships.getBaseStructure(getSign().getBlock()));
		setStructure(structure);
		org.bukkit.material.Sign sign = (org.bukkit.material.Sign)getSign().getData();
		BlockFace face;
		if (sign.isWallSign()){
			face = sign.getAttachedFace();
		}else{
			face = sign.getFacing().getOppositeFace();
		}
		setFacingDirection(face);
	}
	
	public void updateLocation(Location loc, Sign sign){
		File file = getFile();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("ShipsData.Location.Sign", sign.getLocation().getX() + "," + sign.getLocation().getY() + "," + sign.getLocation().getZ() + "," + sign.getLocation().getWorld().getName());
		config.set("ShipsData.Location.Teleport", loc.getX() + "," + loc.getY() + "," + loc.getZ() + "," + loc.getWorld().getName());
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Location getLocation(){
		return getSign().getLocation();
	}
	
	List<MovingBlock> getTeleportStructure(Location loc){
		List<MovingBlock> blocks = new ArrayList<MovingBlock>();
		List<Block> structure = getStructure().getAllBlocks();
		Location loc2 = getLocation();
		for(Block block : structure){
			int X = (int)(block.getX() - loc2.getX());
			int Y = (int)(block.getY() - loc2.getY());
			int Z = (int)(block.getZ() - loc2.getZ());
			Block teleport = loc.getBlock().getRelative(X, Y, Z);
			MovingBlock block2 = new MovingBlock(teleport, this, MovementMethod.TELEPORT);
			blocks.add(block2);
		}
		return blocks;
	}
	
	public int getLength(boolean updateStructure){
		if (updateStructure){
			updateStructure();
		}
		List<Block> blocks = getStructure().getAllBlocks();
		if ((getFacingDirection().equals(BlockFace.NORTH) || (getFacingDirection().equals(BlockFace.SOUTH)))){
			int maxHeight = blocks.get(0).getX();
			int minHeight = blocks.get(0).getX();
			for(Block block : blocks){
				if (block.getX() > maxHeight){
					maxHeight = block.getX();
				}
				if (block.getX() < minHeight){
					minHeight = block.getX();
				}
			}
			int result = maxHeight - minHeight;
			return result;
		}else{
			int maxHeight = blocks.get(0).getZ();
			int minHeight = blocks.get(0).getZ();
			for(Block block : blocks){
				if (block.getZ() > maxHeight){
					maxHeight = block.getZ();
				}
				if (block.getZ() < minHeight){
					minHeight = block.getZ();
				}
			}
			int result = maxHeight - minHeight;
			return result;
		}
	}
	
	public int getHeight(boolean updateStructure){
		if (updateStructure){
			updateStructure();
		}
		List<Block> blocks = getStructure().getAllBlocks();
		int maxHeight = blocks.get(0).getY();
		int minHeight = blocks.get(0).getY();
		for(Block block : blocks){
			if (block.getY() > maxHeight){
				maxHeight = block.getY();
			}
			if (block.getY() < minHeight){
				minHeight = block.getY();
			}
		}
		int result = maxHeight - minHeight;
		return result;
	}
	
	public int getWidth(boolean updateStructure){
		if (updateStructure){
			updateStructure();
		}
		List<Block> blocks = getStructure().getAllBlocks();
		if ((getFacingDirection().equals(BlockFace.WEST) || (getFacingDirection().equals(BlockFace.EAST)))){
			int maxHeight = blocks.get(0).getX();
			int minHeight = blocks.get(0).getX();
			for(Block block : blocks){
				if (block.getX() > maxHeight){
					maxHeight = block.getX();
				}
				if (block.getX() < minHeight){
					minHeight = block.getX();
				}
			}
			int result = maxHeight - minHeight;
			return result;
		}else{
			int maxHeight = blocks.get(0).getZ();
			int minHeight = blocks.get(0).getZ();
			for(Block block : blocks){
				if (block.getZ() > maxHeight){
					maxHeight = block.getZ();
				}
				if (block.getZ() < minHeight){
					minHeight = block.getZ();
				}
			}
			int result = maxHeight - minHeight;
			return result;
		}
	}
	
	public void reload(){
		File file = getFile();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		Messages.refreshMessages();
		OfflinePlayer owner =  Bukkit.getOfflinePlayer(UUID.fromString(config.getString("ShipsData.Player.Name")));
		String vesselTypeS = config.getString("ShipsData.Type");
		int max = config.getInt("ShipsData.Config.Block.Max");
		int min = config.getInt("ShipsData.Config.Block.Min");
		int engine = config.getInt("ShipsData.Config.Speed.Engine");
		String locS = config.getString("ShipsData.Location.Sign");
		String teleportS = config.getString("ShipsData.Location.Teleport");
		String name = file.getName().replace(".yml", "");
		if (vesselTypeS != null){
			if (locS != null){
				if (teleportS != null){
					String[] locM = locS.split(",");
					Location loc = new Location(Bukkit.getWorld(locM[3]), Integer.parseInt(locM[0]), Integer.parseInt(locM[1]), Integer.parseInt(locM[2]));
					if (loc.getBlock().getState() instanceof Sign){
						Sign sign = (Sign)loc.getBlock().getState();
						String[] teleportM = teleportS.split(",");
						Location teleport = new Location(Bukkit.getWorld(teleportM[3]), Integer.parseInt(teleportM[0]), Integer.parseInt(teleportM[1]), Integer.parseInt(teleportM[2]));
						VesselType vesselType = VesselType.getTypeByName(vesselTypeS);
						if (vesselType != null){
							this.TELEPORTLOCATION = teleport;
							this.SIGN = sign;
							this.NAME = name;
							this.OWNER = owner; 
							VesselType type = this.getVesselType();
							type.setDefaultSpeed(engine);
							type.setMaxBlocks(max);
							type.setMinBlocks(min);
							type.loadFromNewVesselFile(this, file);
						}
					}
				}
			}
		}
	}
	
	public static List<Vessel> getVessels(){
		return LOADEDVESSELS;
	}
	
	public static List<Vessel> getVessels(OfflinePlayer player){
		List<Vessel> vessels = new ArrayList<Vessel>();
		for (Vessel vessel : getVessels()){
			if (vessel.getOwner().equals(player)){
				vessels.add(vessel);
			}
		}
		return vessels;
	}
	
	public static Vessel getVessel(String name){
		for(Vessel vessel : getVessels()){
			if (vessel.getName().equalsIgnoreCase(name)){
				return vessel;
			}
		}
		return null;
	}
	
	public static Vessel getVessel(Sign sign){
		for (Vessel vessel : getVessels()){
			if (sign.equals(vessel.getSign())){
				return vessel;
			}
		}
		return null;
	}
	
	public static Vessel getVessel(Block block, boolean newStructure){
		if (newStructure){
			List<Block> blocks = Ships.getBaseStructure(block);
			for(Block block2 : blocks){
				if (block2.getState() instanceof Sign){
					Sign sign = (Sign)block2.getState();
					if (sign.getLine(0).equals(ChatColor.YELLOW + "[Ships]")){
						Vessel vessel = getVessel(sign);
						if (vessel == null){
							return null;
						}else{
							ShipsStructure structure = new ShipsStructure(blocks);
							vessel.setStructure(structure);
							return vessel;
						}
					}
				}
			}
		}else{
			for (Vessel vessel : LOADEDVESSELS){
				for (Block block2 : vessel.getStructure().getAllBlocks()){
					if (block2.equals(block)){
						return vessel;
					}
				}
			}
		}
		return null;
	}
}
