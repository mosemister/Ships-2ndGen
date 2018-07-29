package org.ships.ship;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;
import org.ships.configuration.BlockList;

public interface ShipBlockListable extends Ship {
	public YamlConfiguration getBlockListConfiguration();

	public File getBlockListFile();

	public void setBlockListFile(File var1);

	public BlockList getBlockList();

	public void setBlockList(BlockList var1);
}
