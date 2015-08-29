package MoseShipsBukkit.ShipTypes.Types;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import MoseShipsBukkit.Ships;
import MoseShipsBukkit.MovingShip.MovementMethod;
import MoseShipsBukkit.MovingShip.MovingBlock;
import MoseShipsBukkit.ShipTypes.VesselType;
import MoseShipsBukkit.ShipTypes.NormalRequirements.ChargePlates;
import MoseShipsBukkit.StillShip.SpecialBlock;
import MoseShipsBukkit.StillShip.Vessel;
import MoseShipsBukkit.Utils.ConfigLinks.Messages;

public class SolarShip extends VesselType implements ChargePlates{

	int TAKEAMOUNT;
	int MAXAMOUNT;
	int MAXBLOCKS;
	int MINBLOCKS;
	int CHARGETAKEAMOUNT;
	int CHARGEMAXAMOUNT;
	
	
	public SolarShip() {
		super("SolarShip", 2, 3, true, true);
	}

	@Override
	public void save(Vessel vessel) {
		File file = new File("plugins/Ships/VesselData/" + vessel.getName() + ".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("ShipsData.Player.Name", vessel.getOwner().getUniqueId().toString());
		config.set("ShipsData.Type", "Solarship");
		config.set("ShipsData.Protected", vessel.isProtected());
		config.set("ShipsData.Config.Block.Max", getMaxBlocks());
		config.set("ShipsData.Config.Block.Min", getMinBlocks());
		config.set("ShipsData.Config.Speed.Engine", getDefaultSpeed());
		config.set("Fuel.Config.MaxLimitPerCell", getChargeMaxAmount());
		config.set("Fuel.Config.Consumption", getChargeTakeAmount());
		Sign sign = vessel.getSign();
		Location loc = vessel.getTeleportLocation();
		config.set("ShipsData.Location.Sign", sign.getLocation().getX() + "," + sign.getLocation().getY() + "," + sign.getLocation().getZ() + "," + sign.getLocation().getWorld().getName());
		config.set("ShipsData.Location.Teleport", loc.getX() + "," + loc.getY() + "," + loc.getZ() + "," + loc.getWorld().getName());
		try{
			config.save(file);
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}

	@Override
	public void createConfig() {
		File file = getFile();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("ShipsData.Config.Speed.Engine", 2);
		config.set("ShipsData.Config.Speed.Boost", 3);
		config.set("ShipsData.Config.Blocks.Max", 4000);
		config.set("ShipsData.Config.Blocks.Min", 200);
		config.set("ShipsData.Config.Fuel.MaxLimitPerCell", 100);
		config.set("ShipsData.Config.Fuel.Consumption", 10);
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void loadDefault() {
		File file = getFile();
		if (!file.exists()){
			createConfig();
		}
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		this.setDefaultSpeed(config.getInt("ShipsData.Config.Speed.Engine"));
		this.setDefaultBoostSpeed(config.getInt("ShipsData.Config.Speed.Boost"));
		this.setMaxBlocks(config.getInt("ShipsData.Config.Blocks.Max"));
		this.setMinBlocks(config.getInt("ShipsData.Config.Blocks.Min"));
		this.setChargeMaxAmount(config.getInt("ShipsData.Config.Fuel.MaxLimitPerCell"));
		this.setChargeTakeAmount(config.getInt("ShipsData.Config.Fuel.Consumption"));
	}

	@Override
	public File getFile() {
		File file = new File("plugins/Ships/Configuration/VesselTypes/SolarShip.yml");
		return file;
	}

	@Override
	public int getMaxBlocks() {
		return MAXBLOCKS;
	}

	@Override
	public int getMinBlocks() {
		return MINBLOCKS;
	}

	@Override
	public void setMaxBlocks(int amount) {
		MAXBLOCKS = amount;
	}

	@Override
	public void setMinBlocks(int amount) {
		MINBLOCKS = amount;
	}

	@Override
	public boolean CheckRequirements(Vessel vessel, MovementMethod move, List<MovingBlock> blocks, Player player) {
		if(blocks.size() >= getMinBlocks()){
			if (blocks.size() <= getMaxBlocks()){
				if (this.isMovingInto(blocks, getMoveInMaterials())){
					if (move.equals(MovementMethod.MOVE_DOWN)){
						return true;
					}else{
						long time = vessel.getTeleportLocation().getWorld().getTime();
						if (time > 13000){
							if (this.checkCharge(vessel)){
								takeCharge(vessel);
								return true;
							}else{
								if (player != null){
									if (Messages.isEnabled()){
										player.sendMessage(Ships.runShipsMessage(Messages.getOutOfFuel("fuel"), true));
									}
								}
								return false;
							}
						}else{
							return true;
						}
					}
				}else{
					if (player != null){
						if (Messages.isEnabled()){
							player.sendMessage(Ships.runShipsMessage(Messages.getMustBeIn("Air"), true));
						}
					}
					return false;
				}
			}else{
				if (player != null){
					if (Messages.isEnabled()){
						player.sendMessage(Ships.runShipsMessage(Messages.getShipTooBig(blocks.size(), getMaxBlocks()), true));
					}
				}
				return false;
			}
		}else{
			if (player != null){
				if (Messages.isEnabled()){
					player.sendMessage(Ships.runShipsMessage(Messages.getShipTooSmall(blocks.size(), getMinBlocks()), true));
				}
			}
			return false;
		}
	}

	@Override
	public int getChargeTakeAmount() {
		return TAKEAMOUNT;
	}

	@Override
	public int getChargeMaxAmount() {
		return MAXAMOUNT;
	}

	public boolean checkCharge(Vessel vessel){
		int count = 0;
		for(SpecialBlock block : vessel.getStructure().getSpecialBlocks()){
			if (block.getBlock().getState() instanceof Sign){
				Sign sign = (Sign)block.getBlock().getState();
				if (sign.getLine(0).equals(ChatColor.YELLOW + "[Cell]")){
					int amount = Integer.parseInt(sign.getLine(2));
					if (amount >= getChargeTakeAmount()){
						count = count + getChargeTakeAmount();
						sign.setLine(2, amount - getChargeTakeAmount() + "");
					}
				}
			}
		}
		if (count >= this.getChargeTakeAmount()){
			return true;
		}
		return false;
	}
	
	@Override
	public void takeCharge(Vessel vessel) {
		int count = 0;
		for(SpecialBlock block : vessel.getStructure().getSpecialBlocks()){
			if (block.getBlock().getState() instanceof Sign){
				Sign sign = (Sign)block.getBlock().getState();
				if (sign.getLine(0).equals(ChatColor.YELLOW + "[Cell]")){
					int amount = Integer.parseInt(sign.getLine(2));
					if (amount >= getChargeTakeAmount()){
						count = count + getChargeTakeAmount();
						sign.setLine(2, amount - getChargeTakeAmount() + "");
					}
				}
			}
			if (count == getChargeTakeAmount()){
				return;
			}
		}
	}

	@Override
	public void setChargeTakeAmount(int A) {
		CHARGETAKEAMOUNT = A;
	}

	@Override
	public void setChargeMaxAmount(int A) {
		CHARGEMAXAMOUNT = A;
		
	}

	@Deprecated
	@Override
	public Vessel loadFromClassicVesselFile(Vessel vessel, File file) {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		VesselType type = vessel.getVesselType();
		if (type instanceof SolarShip){
			SolarShip solarship = (SolarShip)type;
			int consumption = config.getInt("ShipsData.Config.Fuel.Consumption");
			int maxFuelCount = config.getInt("ShipsData.Config.Fuel.MaxLimitPerCell");
			solarship.setChargeMaxAmount(maxFuelCount);
			solarship.setChargeTakeAmount(consumption);
			vessel.setVesselType(type);
		}
		return vessel;
	}

	@Override
	public Vessel loadFromNewVesselFile(Vessel vessel, File file) {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		VesselType type = vessel.getVesselType();
		if (type instanceof SolarShip){
			SolarShip solarship = (SolarShip)type;
			int consumption = config.getInt("ShipsData.Config.Fuel.Consumption");
			int maxFuelCount = config.getInt("ShipsData.Config.Fuel.MaxLimitPerCell");
			solarship.setChargeMaxAmount(maxFuelCount);
			solarship.setChargeTakeAmount(consumption);
			vessel.setVesselType(type);
		}
		return vessel;
	}

}
