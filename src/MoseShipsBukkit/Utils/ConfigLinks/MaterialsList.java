package MoseShipsBukkit.Utils.ConfigLinks;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.material.MaterialData;

import MoseShipsBukkit.Ships;
import MoseShipsBukkit.Utils.MaterialAndData;
import MoseShipsBukkit.Utils.MaterialItem;

@SuppressWarnings("deprecation")
public class MaterialsList {

	List<MaterialItem> MATERIALIDLIST = new ArrayList<MaterialItem>();
	List<MaterialItem> RAMIDLIST = new ArrayList<MaterialItem>();
	static MaterialsList LIST;

	public MaterialsList() {
		File file = new File("plugins/Ships/Configuration/Materials.yml");
		if (!file.exists()) {
			// default
			// TODO
			MATERIALIDLIST.add(new MaterialItem(5, -1));
			MATERIALIDLIST.add(new MaterialItem(14, 0));
			MATERIALIDLIST.add(new MaterialItem(15, (byte) 0));
			MATERIALIDLIST.add(new MaterialItem(16, (byte) 0));
			MATERIALIDLIST.add(new MaterialItem(17, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(20, (byte) 0));
			MATERIALIDLIST.add(new MaterialItem(21, (byte) 0));
			MATERIALIDLIST.add(new MaterialItem(22, (byte) 0));
			MATERIALIDLIST.add(new MaterialItem(23, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(25, (byte) 0));
			MATERIALIDLIST.add(new MaterialItem(29, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(33, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(34, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(35, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(41, (byte) 0));
			MATERIALIDLIST.add(new MaterialItem(42, (byte) 0));
			MATERIALIDLIST.add(new MaterialItem(43, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(44, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(45, (byte) 0));
			MATERIALIDLIST.add(new MaterialItem(46, (byte) 0));
			MATERIALIDLIST.add(new MaterialItem(47, (byte) 0));
			MATERIALIDLIST.add(new MaterialItem(48, (byte) 0));
			MATERIALIDLIST.add(new MaterialItem(49, (byte) 0));
			MATERIALIDLIST.add(new MaterialItem(50, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(51, (byte) 0));
			MATERIALIDLIST.add(new MaterialItem(52, (byte) 0));
			MATERIALIDLIST.add(new MaterialItem(53, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(54, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(55, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(56, (byte) 0));
			MATERIALIDLIST.add(new MaterialItem(57, (byte) 0));
			MATERIALIDLIST.add(new MaterialItem(58, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(61, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(62, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(63, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(64, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(65, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(68, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(69, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(70, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(71, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(72, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(75, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(76, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(77, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(84, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(85, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(87, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(92, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(93, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(94, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(95, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(96, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(97, (byte) 5));
			MATERIALIDLIST.add(new MaterialItem(98, (byte) 3));
			MATERIALIDLIST.add(new MaterialItem(101, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(102, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(107, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(108, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(109, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(116, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(117, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(118, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(123, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(124, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(125, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(126, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(128, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(130, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(131, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(132, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(133, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(134, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(135, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(136, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(138, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(139, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(140, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(143, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(144, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(145, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(146, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(147, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(148, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(149, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(150, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(151, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(152, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(154, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(155, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(156, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(158, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(159, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(160, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(162, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(163, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(164, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(165, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(166, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(167, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(168, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(169, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(170, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(171, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(172, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(173, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(176, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(177, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(178, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(183, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(184, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(185, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(186, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(187, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(188, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(189, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(190, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(191, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(192, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(193, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(184, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(185, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(186, (byte) -1));
			MATERIALIDLIST.add(new MaterialItem(187, (byte) -1));

			RAMIDLIST.add(new MaterialItem(6, (byte) -1));
			RAMIDLIST.add(new MaterialItem(18, (byte) -1));
			RAMIDLIST.add(new MaterialItem(30, (byte) -1));
			RAMIDLIST.add(new MaterialItem(31, (byte) -1));
			RAMIDLIST.add(new MaterialItem(32, (byte) -1));
			RAMIDLIST.add(new MaterialItem(37, (byte) -1));
			RAMIDLIST.add(new MaterialItem(38, (byte) -1));
			RAMIDLIST.add(new MaterialItem(39, (byte) -1));
			RAMIDLIST.add(new MaterialItem(40, (byte) -1));
			RAMIDLIST.add(new MaterialItem(59, (byte) -1));
			RAMIDLIST.add(new MaterialItem(83, (byte) -1));
			RAMIDLIST.add(new MaterialItem(104, (byte) -1));
			RAMIDLIST.add(new MaterialItem(105, (byte) -1));
			RAMIDLIST.add(new MaterialItem(106, (byte) -1));
			RAMIDLIST.add(new MaterialItem(111, (byte) -1));
			RAMIDLIST.add(new MaterialItem(115, (byte) -1));
			RAMIDLIST.add(new MaterialItem(141, (byte) -1));
			RAMIDLIST.add(new MaterialItem(142, (byte) -1));
			RAMIDLIST.add(new MaterialItem(161, (byte) -1));
			RAMIDLIST.add(new MaterialItem(175, (byte) -1));

			LIST = this;
		} else {
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			for (Material material : Material.values()) {
				Map<Byte, Integer> values = getValues(config, material);
				for (Entry<Byte, Integer> entry : values.entrySet()) {
					if (entry.getValue() == 1) {
						int id = material.getId();
						byte data = entry.getKey();
						MATERIALIDLIST.add(new MaterialItem(id, data));
					} else if (entry.getValue() == 2) {
						int id = material.getId();
						byte data = entry.getKey();
						RAMIDLIST.add(new MaterialItem(id, data));
					}
				}
			}
		}
		LIST = this;
	}

	Map<Byte, Integer> getValues(YamlConfiguration config, Material material) {
		Map<Byte, Integer> dataValues = new HashMap<Byte, Integer>();
		for (int A = -1; A < 25; A++) {
			int check = config.getInt("Materials." + material.name() + ".dataValue_" + A);
			if (check != 0) {
				dataValues.put((byte) A, check);
			}
		}
		return dataValues;
	}

	private boolean needsUpdating() {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		String mcVersion = config.getString("MCVersion");
		if (mcVersion == null) {
			Config.getConfig().updateCheck();
			return true;
		}
		File file = new File("plugins/Ships/Configuration/Materials.yml");
		if(file.length() == 0) {
			return true;
		}
		int[] knownVersion = Ships.convertVersion(mcVersion);
		int[] latest = Ships.convertVersion(Ships.getMinecraftVersion());	
		if (Ships.compare(knownVersion, latest) == Ships.COMPARE_SECOND_VALUE_IS_GREATER) {
			return true;
		}
		return false;
	}

	public void save() {
		File file = new File("plugins/Ships/Configuration/Materials.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		List<Material> failedMaterials = new ArrayList<Material>();
		boolean check = false;
		for (Material material : Material.values()) {
			if(!material.isBlock()) {
				continue;
			}
			Set<MaterialItem> materials = MaterialAndData.getAllBlocks(material);
			if (materials != null) {
				if (materials.size() == 0) {
					failedMaterials.add(material);
					if (needsUpdating()) {
						check = true;
						config.set("Materials." + material.name() + ".dataValue_-1", 0);
						Bukkit.getConsoleSender().sendMessage(
								Ships.runShipsMessage(material.name() + " has been updated in materials list", false));
					}
				} else {
					if (needsUpdating()) {
						check = true;
						for (MaterialItem data : materials) {
							if (contains(material, true)) {
								if (config
										.getInt("Materials." + material.name() + ".dataValue_" + data.getData()) == 0) {
									config.set("Materials." + material.name() + ".dataValue_" + data.getData(), 1);
								}
							} else if (contains(material, false)) {
								if (config
										.getInt("Materials." + material.name() + ".dataValue_" + data.getData()) == 0) {
									config.set("Materials." + material.name() + ".dataValue_" + data.getData(), 2);
								}
							} else {
								config.set("Materials." + material.name() + ".dataValue_" + data.getData(), 0);
							}
						}
					}
				}
			}
		}
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (check) {
			Bukkit.getConsoleSender()
					.sendMessage(Ships.runShipsMessage(
							"A new minecraft version found. \n Attempting to update the materials list with the new blocks",
							false));
			YamlConfiguration config2 = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
			config2.set("MCVersion", Ships.getMinecraftVersion());
			try {
				config2.save(Config.getConfig().getFile());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (failedMaterials.size() != 0) {
			Bukkit.getConsoleSender()
					.sendMessage(Ships.runShipsMessage(
							"Ships currently does not support " + failedMaterials + "(found " + failedMaterials.size()
									+ " blocks). \n These blocks are in the materials list however they maynot work correctly",
							true));
			Bukkit.getConsoleSender()
					.sendMessage(Ships.runShipsMessage(
							"check http://dev.bukkit.org/bukkit-plugins/ships to see if you are using the latest version. This version is "
									+ Config.getConfig().getLatestVersionString(),
							false));
		}
	}
	
	public boolean contains(Material material, MaterialData data, boolean materials) {
		List<MaterialItem> list = MATERIALIDLIST;
		if(!materials) {
			list = RAMIDLIST;
		}
		for(MaterialItem item : list) {
			if(item.getMaterial().equals(material)) {
				if(item.getData() == data.getData()) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean contains(Material material, boolean materials) {
		if (materials) {
			for (MaterialItem item : MATERIALIDLIST) {
				if (item.getMaterial().equals(material)) {
					return true;
				}
			}
		} else {
			for (MaterialItem item : RAMIDLIST) {
				if (item.getMaterial().equals(material)) {
					return true;
				}
			}
		}
		return false;
	}

	@Deprecated
	public boolean contains(Material material, byte data, boolean materials) {
		if (materials) {
			for (MaterialItem item : MATERIALIDLIST) {
				if (item.getMaterial().equals(material)) {
					if (item.getData() != -1) {
						if (item.getData() == data) {
							return true;
						}
					} else {
						return true;
					}
				}
			}
		} else {
			for (MaterialItem item : RAMIDLIST) {
				if (item.getMaterial().equals(material)) {
					if (item.getData() != -1) {
						if (item.getData() == data) {
							return true;
						}
					} else {
						return true;
					}
				}
			}
		}
		return false;
	}

	public List<MaterialItem> getMaterials() {
		return MATERIALIDLIST;
	}

	public List<MaterialItem> getRamMaterials() {
		return RAMIDLIST;
	}

	public static MaterialsList getMaterialsList() {
		return LIST;
	}

}
