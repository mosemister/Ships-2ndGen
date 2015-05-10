package MoseShipsBukkit.StillShip;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

import MoseShipsBukkit.Ships;
import MoseShipsBukkit.Events.ShipMoveEvent;
import MoseShipsBukkit.MovingShip.MovementMethod;
import MoseShipsBukkit.MovingShip.MovingBlock;
import MoseShipsBukkit.ShipTypes.VesselType;
import MoseShipsBukkit.ShipTypes.Types.AirShip;
import MoseShipsBukkit.ShipTypes.Types.SolarShip;
import MoseShipsBukkit.ShipTypes.Types.Submarine;
import MoseShipsBukkit.ShipTypes.Types.WaterShip;
import MoseShipsBukkit.Utils.BlockConverter;
import MoseShipsBukkit.Utils.ConfigLinks.Config;
import MoseShipsBukkit.Utils.ConfigLinks.MaterialsList;
import MoseShipsBukkit.Utils.OtherPlugins.OtherPlugins;

public class Vessel {
	
	String NAME;
	OfflinePlayer OWNER;
	VesselType TYPE;
	ShipsStructure STRUCTURE;
	Sign SIGN;
	Location TELEPORTLOCATION;
	BlockFace DIRECTION;
	Location AUTOPILOTTO;
	
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
	
	public boolean moveVessel(MovementMethod move, int speed, Player player){
		if ((player.hasPermission("ships." + getName() + ".use")) || (player.hasPermission("ships." + getVesselType().getName() + ".use")) || (player.hasPermission("ships.*.use"))){
			if (speed != 0){
				move.setSpeed(speed);
			}
			List<MovingBlock> blocks = new ArrayList<MovingBlock>();
			for (Block block : STRUCTURE.getPriorityBlocks()){
				MovingBlock block2 = new MovingBlock(block, this, move);
				if (isBlocked(block2)){
					if (player != null){
						player.sendMessage(Ships.runShipsMessage("Found " + block2.getMovingTo().getBlock().getType().name() + " in way", true));
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
						player.sendMessage(Ships.runShipsMessage("Found " + block2.getMovingTo().getBlock().getType().name() + " in way", true));
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
						player.sendMessage(Ships.runShipsMessage("Found " + block2.getMovingTo().getBlock().getType().name() + " in way", true));
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
							player.sendMessage(Ships.runShipsMessage("Faction in way", true));
							return false;
						}
					}else{
						console.sendMessage(Ships.runShipsMessage("Ships has Factions enabled but can not hook into it", true));
					}
				}
				if (config.getBoolean("WorldGuardSupport.enabled")){
					if (OtherPlugins.isWorldGuardLoaded()){
						if (OtherPlugins.isLocationInWorldGuardRegion(block.getMovingTo())){
							player.sendMessage(Ships.runShipsMessage("Region in way", true));
							return false;
						}
					}else{
						console.sendMessage(Ships.runShipsMessage("Ships has WorldGuard enabled but can not hook into it", true));
					}
				}
				if (config.getBoolean("WorldBorderSupport.enabled")){
					if (OtherPlugins.isWorldBorderLoaded()){
						if (OtherPlugins.isLocationOutOfWorldBorder(block.getMovingTo())){
							player.sendMessage(Ships.runShipsMessage("Edge of map", true));
							return false;
						}
					}else{
						console.sendMessage(Ships.runShipsMessage("Ships has WorldBorder enabled but can not hook into it", true));
					}
				}
				if (config.getBoolean("TownySupport.enabled")){
					if (OtherPlugins.isTownyLoaded()){
						if (OtherPlugins.isTownyHookLoaded()){
							if (OtherPlugins.isLocationInTownyLand(block.getMovingTo())){
								player.sendMessage(Ships.runShipsMessage("Town in way", true));
							}
						}else{
							console.sendMessage(Ships.runShipsMessage("Needs ShipsTownyHook.jar for compatibility with Towny. Download at dev.bukkit.org/bukkit-plugins/ships/files/68", true));
						}
					}else{
						console.sendMessage(Ships.runShipsMessage("Ships has Towny enabled but can not hook into it", true));
					}
				}
			}
			if (this.getVesselType().CheckRequirements(this, move, blocks, player)){
				ShipMoveEvent event = new ShipMoveEvent(player, this, move, blocks);
				Bukkit.getPluginManager().callEvent(event);
				if (!event.isCancelled()){
					forceMove(move, event.getMovingBlocks());
					return true;
				}
			}
		}
		return false;
	}
	
	@SuppressWarnings("deprecation")
	boolean isBlocked(MovingBlock block){
		Location loc = block.getMovingTo();
		if (!isPartOfVessel(loc)){
			if (MaterialsList.getMaterialsList().contains(loc.getBlock().getType(), loc.getBlock().getData(), false)){
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
	
	@SuppressWarnings("deprecation")
	void forceMove(MovementMethod move, List<MovingBlock> blocks){
		//remove all blocks (priority first)
		Config configuration = Config.getConfig();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(configuration.getFile());
		for(int A = 0; A < blocks.size(); A++){
			MovingBlock block = blocks.get(A);
			if (block.getSpecialBlock() != null){
				clearInventory(block);
				if (block.getBlock().getLocation().getY() > config.getInt("World.defaultWaterLevel")){
					block.getBlock().setType(Material.AIR);
				}else{
					block.getBlock().setType(Material.STATIONARY_WATER);
				}
			}else{
				if (block.getBlock().getLocation().getY() > config.getInt("World.defaultWaterLevel")){
					block.getBlock().setType(Material.AIR);
				}else{
					block.getBlock().setType(Material.STATIONARY_WATER);
				}
			}
		}
		//place all blocks (priority last)
		for(int A = blocks.size() - 1;A >= 0; A--){
			MovingBlock block = blocks.get(A);
			Block bBlock = block.getMovingTo().getBlock();
			if ((move.equals(MovementMethod.ROTATE_LEFT) || (move.equals(MovementMethod.ROTATE_RIGHT)))){
				byte data = BlockConverter.convertRotation(move, block.getId(), block.getData());
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
		for	(Entity entity : getEntitys()){
			Inventory inv = null;
			if (entity instanceof Player){
				Player player = (Player)entity;
				Inventory inv2 = player.getOpenInventory().getTopInventory();
				if (!inv2.getTitle().equals("container.crafting")){
					inv = inv2;
					player.closeInventory();
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
				if (inv != null){
					Player player = (Player)entity;
					player.openInventory(inv);
				}
			}
		}
		updateStructure();
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
		setFacingDirection(sign.getAttachedFace());
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
	
	@SuppressWarnings("deprecation")
	public void reload(){
		File file = getFile();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		OfflinePlayer owner =  Bukkit.getOfflinePlayer(UUID.fromString(config.getString("ShipsData.Player.Name")));
		String vesselTypeS = config.getString("ShipsData.Type");
		int percent = config.getInt("ShipsData.Config.Block.Percent");
		int max = config.getInt("ShipsData.Config.Block.Max");
		int min = config.getInt("ShipsData.Config.Block.Min");
		int consumption = config.getInt("ShipsData.Config.Fuel.Consumption");
		List<String> fuelsL = config.getStringList("ShipsData.Config.Fuel.Fuels");
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
							if (type instanceof AirShip){
								AirShip air = (AirShip)type;
								air.setPercent(percent);
								air.setFuelTakeAmount(consumption);
								if (fuelsL.size() != 0){
									Map<Material, Byte> fuels = new HashMap<Material, Byte>();
									for (String fuelS : fuelsL){
										String[] fuelM = fuelS.split(",");
										fuels.put(Material.getMaterial(Integer.parseInt(fuelM[0])), Byte.parseByte(fuelM[1]));
									}
									air.setFuel(fuels);
								}
							}else
							//SOLARSHIP
							if(type instanceof SolarShip){
								//TODO solarShip loaded objects
							}else
							//SUBMARINE
							if (type instanceof Submarine){
								Submarine sub = (Submarine)type;
								sub.setPercent(percent);
								sub.setFuelTakeAmount(consumption);
								if (fuelsL.size() != 0){
									Map<Material, Byte> fuels = new HashMap<Material, Byte>();
									for (String fuelS : fuelsL){
										String[] fuelM = fuelS.split(",");
										fuels.put(Material.getMaterial(Integer.parseInt(fuelM[0])), Byte.parseByte(fuelM[1]));
									}
									sub.setFuel(fuels);
								}
							}else
							//WATERSHIP
							if (type instanceof WaterShip){
								//TODO Watership loaded objects
							}
						}
					}
				}
			}
		}
	}
	
	public static List<Vessel> getVessels(){
		return LOADEDVESSELS;
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
	
	public static Vessel getVessel(Block block){
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
		return null;
	}
}
