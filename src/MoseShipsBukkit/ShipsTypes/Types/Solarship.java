package MoseShipsBukkit.ShipsTypes.Types;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import MoseShipsBukkit.Ships;
import MoseShipsBukkit.MovingShip.MovementMethod;
import MoseShipsBukkit.MovingShip.MovingBlock;
import MoseShipsBukkit.ShipsTypes.VesselType;
import MoseShipsBukkit.ShipsTypes.VesselTypeUtils;
import MoseShipsBukkit.ShipsTypes.HookTypes.Cell;
import MoseShipsBukkit.StillShip.Vessel;
import MoseShipsBukkit.Utils.ConfigLinks.Messages;

public class Solarship extends VesselType implements Cell{
	
	int TAKE;
	int MAX;
	
	public Solarship() {
		super("Solarship", new ArrayList<Material>(Arrays.asList(Material.AIR)), 2, 3, true);
	}

	@Override
	public boolean removeCellPower(Vessel vessel) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getTotalCellPower(Vessel vessel) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addCellPower() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean checkRequirements(Vessel vessel, MovementMethod move, List<MovingBlock> blocks, @Nullable Player player) {
		VesselTypeUtils utils = new VesselTypeUtils();
		if(blocks.size() >= getMinBlocks()){
			if (blocks.size() <= getMaxBlocks()){
				if (utils.isMovingInto(blocks, getMoveInMaterials())){
					if (move.equals(MovementMethod.MOVE_DOWN)){
						return true;
					}else{
						long time = vessel.getTeleportLocation().getWorld().getTime();
						if (time > 13000){
							if (getTotalCellPower(vessel) >= TAKE){
								removeCellPower(vessel);
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
	public boolean shouldFall(Vessel vessel) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public File getTypeFile() {
		File file = new File("plugins/Ships/Configuration/VesselTypes/SolarShip.yml");
		return file;
	}

	@Override
	public VesselType clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadVesselFromFiveFile(Vessel vessel, File file) {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		VesselType type = vessel.getVesselType();
		if (type instanceof Solarship){
			Solarship solarship = (Solarship)type;
			int consumption = config.getInt("ShipsData.Config.Fuel.Consumption");
			int maxFuelCount = config.getInt("ShipsData.Config.Fuel.MaxLimitPerCell");
			solarship.MAX = maxFuelCount;
			solarship.TAKE = consumption;
			vessel.setVesselType(type);
		}
	}

	@Override
	public void createConfig() {
		File file = getTypeFile();
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
		File file = getTypeFile();
		if (!file.exists()){
			createConfig();
		}
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		this.setDefaultSpeed(config.getInt("ShipsData.Config.Speed.Engine"));
		this.setDefaultBoostSpeed(config.getInt("ShipsData.Config.Speed.Boost"));
		this.setMaxBlocks(config.getInt("ShipsData.Config.Blocks.Max"));
		this.setMinBlocks(config.getInt("ShipsData.Config.Blocks.Min"));
		this.MAX = config.getInt("ShipsData.Config.Fuel.MaxLimitPerCell");
		this.TAKE = config.getInt("ShipsData.Config.Fuel.Consumption");
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
		config.set("Fuel.Config.MaxLimitPerCell", MAX);
		config.set("Fuel.Config.Consumption", TAKE);
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

}
