package org.ships.block.sign;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

public interface ShipSign {
	public String[] getLines();

	public String[] getAliases();

	public String getName();

	public boolean isSign(Sign var1);

	public boolean onCreate(SignChangeEvent var1);

	public static interface OnLeftClick extends ShipSign {

		public static interface OnStand extends OnLeftClick {
			public void onLeftClick(Player var1, Sign var2);
		}

		public static interface OnShift extends OnLeftClick {
			public void onShiftLeftClick(Player var1, Sign var2);
		}

	}

	public static interface OnRightClick extends ShipSign {

		public static interface OnStand extends OnRightClick {
			public void onRightClick(Player var1, Sign var2);
		}

		public static interface OnShift extends OnRightClick {
			public void onShiftRightClick(Player var1, Sign var2);
		}

	}

}
