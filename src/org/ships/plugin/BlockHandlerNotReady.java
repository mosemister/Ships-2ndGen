package org.ships.plugin;

import java.io.IOException;

import org.bukkit.block.BlockState;
import org.ships.block.blockhandler.BlockHandler;

public class BlockHandlerNotReady extends IOException {
	private static final long serialVersionUID = 1L;
	public static final String INCORRECT_MATERIAL = "the target block is not the correct material";

	public BlockHandlerNotReady(BlockHandler<? extends BlockState> handler) {
		this(handler, "an unspecified reason");
	}

	public BlockHandlerNotReady(BlockHandler<? extends BlockState> handler, String reason) {
		super("Failed to save block data for block X:" + handler.getBlock().getX() + ", Y:" + handler.getBlock().getY() + ", Z:" + handler.getBlock().getZ() + " due to block handler (" + handler.getClass().getName() + ") is not ready. Due to " + reason);
	}
}
