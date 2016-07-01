package MoseShipsBukkit.Listeners.ShipsCommands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import MoseShipsBukkit.Ships;
import MoseShipsBukkit.GUI.ShipsGUICommand;
import MoseShipsBukkit.Listeners.CommandLauncher;
import MoseShipsBukkit.ShipsTypes.VesselType;
import MoseShipsBukkit.ShipsTypes.HookTypes.Fuel;
import MoseShipsBukkit.ShipsTypes.HookTypes.RequiredMaterial;
import MoseShipsBukkit.Utils.ConfigLinks.Config;
import MoseShipsBukkit.World.Wind.Direction;

public class Info extends CommandLauncher {

	public Info() {
		super("Info", "", "Get info about the ships plugin", null, true, true, InfoGUI.class);
		new InfoGUI(this);
	}

	@Override
	public void playerCommand(Player player, String[] args) {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		player.sendMessage(getFormat("Version", Ships.getPlugin().getDescription().getVersion()));
		player.sendMessage(
				getFormat("Worlds wind direction", Direction.getDirection(player.getWorld()).getDirection().name()));
		player.sendMessage(getFormat("Ships track limit", config.getInt("Structure.StructureLimits.trackLimit")));
		player.sendMessage(
				getFormat("Submarine air check limit", config.getInt("Structure.StructureLimits.airCheckGap")));
		player.sendMessage(ChatColor.GOLD + "---[Protected Vessels]---");
		player.sendMessage(getFormat("Block Break", "" + config.getBoolean("World.ProtectedVessels.BlockBreak")));
		player.sendMessage(
				getFormat("Inventory Protect", "" + config.getBoolean("World.ProtectedVessels.InventoryOpen")));
		player.sendMessage(getFormat("Fire Protect", "" + config.getBoolean("World.ProtectedVessels.FireProtect2")));
		player.sendMessage(
				getFormat("Creeper Protect", "" + config.getBoolean("World.ProtectedVessels.ExploadeProtect.Creeper")));
		player.sendMessage(
				getFormat("TNT Protect", "" + config.getBoolean("World.ProtectedVessels.ExploadeProtect.TNT")));
		player.sendMessage(getFormat("EnderDragon Protect",
				"" + config.getBoolean("World.ProtectedVessels.EntityProtect.EnderDragon")));
		player.sendMessage(
				getFormat("Wither Protect", "" + config.getBoolean("World.ProtectedVessels.EntityProtect.Wither")));
		player.sendMessage(
				getFormat("Block Break", "" + config.getBoolean("World.ProtectedVessels.EntityProtect.EnderMan")));
	}

	@Override
	public void consoleCommand(ConsoleCommandSender sender, String[] args) {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		sender.sendMessage(getFormat("Version", Ships.getPlugin().getDescription().getVersion()));
		sender.sendMessage(getFormat("Ships track limit", config.getInt("Structure.StructureLimits.trackLimit")));
		sender.sendMessage(
				getFormat("Submarine air check limit", config.getInt("Structure.StructureLimits.airCheckGap")));
		sender.sendMessage(ChatColor.GOLD + "---[Protected Vessels]---");
		sender.sendMessage(getFormat("Block Break", "" + config.getBoolean("World.ProtectedVessels.BlockBreak")));
		sender.sendMessage(
				getFormat("Inventory Protect", "" + config.getBoolean("World.ProtectedVessels.InventoryOpen")));
		sender.sendMessage(getFormat("Fire Protect", "" + config.getBoolean("World.ProtectedVessels.FireProtect2")));
		sender.sendMessage(
				getFormat("Creeper Protect", "" + config.getBoolean("World.ProtectedVessels.ExploadeProtect.Creeper")));
		sender.sendMessage(
				getFormat("TNT Protect", "" + config.getBoolean("World.ProtectedVessels.ExploadeProtect.TNT")));
		sender.sendMessage(getFormat("EnderDragon Protect",
				"" + config.getBoolean("World.ProtectedVessels.EntityProtect.EnderDragon")));
		sender.sendMessage(
				getFormat("Wither Protect", "" + config.getBoolean("World.ProtectedVessels.EntityProtect.Wither")));
		sender.sendMessage(
				getFormat("Block Break", "" + config.getBoolean("World.ProtectedVessels.EntityProtect.EnderMan")));
		sender.sendMessage(ChatColor.GOLD + "---[World Wind Directions]---");
		for (Direction direction : Direction.getDirections()) {
			sender.sendMessage(getFormat(direction.getWorld().getName(), direction.getDirection().name()));
		}

	}

	public static String getFormat(String message, Object result) {
		String ret = ChatColor.GOLD + "[" + message + "] " + ChatColor.AQUA + result;
		return ret;
	}

	public static class InfoGUI extends ShipsGUICommand {

		public InfoGUI(CommandLauncher command) {
			super(command);
		}

		@Override
		public void onScreenClick(HumanEntity player, ItemStack item, Inventory inv, int slot, ClickType type) {
			if (item != null) {
				if (item.equals(ShipsGUICommand.FORWARD_BUTTON)) {
					onInterfaceBoot(player, getPage(inv) + 1);
				} else if (item.equals(ShipsGUICommand.BACK_BUTTON)) {
					onInterfaceBoot(player, getPage(inv) - 1);
				}
			}
		}

		List<ItemStack> getVesselTypeInfo(VesselType type) {
			List<ItemStack> items = new ArrayList<ItemStack>();

			ItemStack name = new ItemStack(Material.CAULDRON_ITEM, 1);
			ItemMeta nameMeta = name.getItemMeta();
			nameMeta.setDisplayName(type.getName());
			name.setItemMeta(nameMeta);
			items.add(name);

			ItemStack speed = new ItemStack(Material.CAULDRON_ITEM, 1);
			ItemMeta speedMeta = speed.getItemMeta();
			speedMeta.setDisplayName("Speeds");
			speedMeta.setLore(
					Arrays.asList("Engine: " + type.getDefaultSpeed(), "Boost:" + type.getDefaultBoostSpeed()));
			speed.setItemMeta(speedMeta);
			items.add(speed);

			ItemStack blockLimits = new ItemStack(Material.CAULDRON_ITEM, 1);
			ItemMeta blockLimitsMeta = blockLimits.getItemMeta();
			blockLimitsMeta.setDisplayName("Block Limits");
			blockLimitsMeta.setLore(Arrays.asList("Min: " + type.getMinBlocks(), "Max:" + type.getMaxBlocks()));
			blockLimits.setItemMeta(blockLimitsMeta);
			items.add(blockLimits);

			if (type instanceof Fuel) {
				Fuel type2 = (Fuel) type;
				ItemStack fuelLimits = new ItemStack(Material.CAULDRON_ITEM, 1);
				ItemMeta fuelMeta = fuelLimits.getItemMeta();
				fuelMeta.setDisplayName("Block Limits");
				List<String> fuels = new ArrayList<String>();
				for (Entry<Material, Byte> entry : type2.getFuel().entrySet()) {
					fuels.add(entry.getKey().name() + " : " + entry.getValue());
				}
				fuelMeta.setLore(fuels);
				fuelLimits.setItemMeta(fuelMeta);
				items.add(fuelLimits);
			}
			if (type instanceof RequiredMaterial) {
				RequiredMaterial type2 = (RequiredMaterial) type;
				ItemStack matLimits = new ItemStack(Material.CAULDRON_ITEM, 1);
				ItemMeta matMeta = matLimits.getItemMeta();
				matMeta.setDisplayName("Required Blocks");
				List<String> fuels = new ArrayList<String>();
				List<Material> materials = type2.getRequiredMaterial();
				if (materials != null) {
					for (Material material : materials) {
						fuels.add(material.name());
					}
					matMeta.setLore(fuels);
					matLimits.setItemMeta(matMeta);
					items.add(matLimits);
				}

				ItemStack perLimits = new ItemStack(Material.CAULDRON_ITEM, 1);
				ItemMeta perMeta = perLimits.getItemMeta();
				perMeta.setDisplayName("Percent");
				perMeta.setLore(Arrays.asList("Percent:" + type2.getRequiredPercent()));
				perLimits.setItemMeta(perMeta);
				items.add(perLimits);
			}
			return items;
		}

		public List<ItemStack> getPage1Info(HumanEntity entity) {
			YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
			List<ItemStack> items = new ArrayList<ItemStack>();

			ItemStack versionIS = new ItemStack(Material.ARMOR_STAND, 1);
			ItemMeta versionIM = versionIS.getItemMeta();
			versionIM.setDisplayName("Version");
			versionIM.setLore(Arrays.asList(Config.getConfig().getLatestVersionString()));
			versionIS.setItemMeta(versionIM);
			items.add(versionIS);

			ItemStack limitIS = new ItemStack(Material.ANVIL, 1);
			ItemMeta limitIM = limitIS.getItemMeta();
			limitIM.setDisplayName("Limits");
			limitIM.setLore(Arrays.asList("Track: " + config.getInt("Structure.StructureLimits.trackLimit"),
					"SubHeight: " + config.getInt("Structure.StructureLimits.airCheckGap")));
			limitIS.setItemMeta(limitIM);
			items.add(limitIS);

			ItemStack worldIS = new ItemStack(Material.COMPASS, 1);
			ItemMeta worldIM = worldIS.getItemMeta();
			worldIM.setDisplayName("World");
			worldIM.setLore(
					Arrays.asList("WindDirection: " + Direction.getDirection(entity.getWorld()).getDirection().name()));
			worldIS.setItemMeta(worldIM);
			items.add(worldIS);

			return items;
		}

		@SuppressWarnings("deprecation")
		public void onInterfaceBoot(HumanEntity player, int page) {
			if (page == 1) {
				Inventory inv = createPageGUI(getPage1Info(player), getInventoryName(), page, false);
				player.openInventory(inv);
				if (player instanceof Player) {
					((Player) player).updateInventory();
				}
			} else {
				try {
					VesselType type = VesselType.values().get(page - 2);
					List<ItemStack> items = getVesselTypeInfo(type);
					Inventory inv = createPageGUI(items, getInventoryName(), page, false);
					player.openInventory(inv);
					if (player instanceof Player) {
						((Player) player).updateInventory();
					}
				} catch (IndexOutOfBoundsException e) {
					Inventory inv = createPageGUI(new ArrayList<ItemStack>(), getInventoryName(), page, false);
					player.openInventory(inv);
					if (player instanceof Player) {
						((Player) player).updateInventory();
					}
				}
			}
		}

		@Override
		public void onInterfaceBoot(HumanEntity player) {
			Inventory inv = createPageGUI(getPage1Info(player), getInventoryName(), 1, true);
			player.openInventory(inv);

		}

		@Override
		public String getInventoryName() {
			return "Info";
		}

	}
}
