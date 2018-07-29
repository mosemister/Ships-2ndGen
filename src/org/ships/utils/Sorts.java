package org.ships.utils;

import java.util.Comparator;

import org.bukkit.block.BlockState;
import org.ships.block.MovingBlock;
import org.ships.block.blockhandler.BlockHandler;
import org.ships.block.blockhandler.BlockPriority;
import org.ships.block.configuration.MaterialConfiguration;

public class Sorts {
	public static final Comparator<BlockPriority> SORT_BLOCK_PRIORITY = (blockPriority, t1) -> {
		switch (blockPriority) {
			case ATTACHABLE: {
				switch (t1) {
					case ATTACHABLE: {
						return 0;
					}
					case SPECIAL:
					case DEFAULT: {
						return -1;
					}
				}
			}
			case SPECIAL: {
				switch (t1) {
					case ATTACHABLE: {
						return 1;
					}
					case SPECIAL: {
						return 0;
					}
					case DEFAULT: {
						return -1;
					}
				}
			}
			case DEFAULT: {
				switch (t1) {
					case DEFAULT: {
						return 0;
					}
					case ATTACHABLE:
					case SPECIAL: {
						return 1;
					}
				}
			}
		}
		return 0;
	};
	public static final Comparator<BlockHandler<? extends BlockState>> SORT_BLOCK_PRIORITY_ON_BLOCK_HANDLER = (bh, bh1) -> SORT_BLOCK_PRIORITY.compare(bh.getPriority(), bh1.getPriority());
	public static final Comparator<MovingBlock> SORT_BLOCK_PRIORITY_ON_MOVING_BLOCK = (mb, mb1) -> SORT_BLOCK_PRIORITY.compare(mb.getHandle().getPriority(), mb1.getHandle().getPriority());
	public static final Comparator<Enum<? extends Object>> SORT_ENUM_BY_NAME = (e1, e2) -> e1.name().compareToIgnoreCase(e2.name());
	public static final Comparator<MaterialConfiguration> SORT_MATERIAL_CONFIGURATION_BY_MATERIAL_NAME = (m1, m2) -> SORT_ENUM_BY_NAME.compare(m1.getMaterial(), m2.getMaterial());

}
