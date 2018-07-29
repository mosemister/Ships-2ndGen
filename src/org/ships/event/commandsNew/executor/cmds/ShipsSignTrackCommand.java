package org.ships.event.commandsNew.executor.cmds;

import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.ships.block.structure.ShipsStructure;
import org.ships.event.commandsNew.argument.SingleCatagoryArgument;
import org.ships.event.commandsNew.argument.number.OptionalIntergerArgument;
import org.ships.event.commandsNew.argument.ship.LoadableShipArgument;
import org.ships.event.commandsNew.executor.CMDArgumentResults;
import org.ships.event.commandsNew.executor.CMDExecutor;
import org.ships.plugin.BlockHandlerNotReady;
import org.ships.plugin.Ships;
import org.ships.ship.LoadableShip;

public class ShipsSignTrackCommand extends CMDExecutor {
	
	public ShipsSignTrackCommand() {
		super(new SingleCatagoryArgument("sign"), new SingleCatagoryArgument("track"), new LoadableShipArgument("ship"), new OptionalIntergerArgument("seconds", 3));
	}

	@Override
	public boolean onExecute(CommandSender sender, CMDArgumentResults results) {
		Optional<LoadableShip> opShip = results.getArgumentResult("ship");
		Optional<Integer> opSeconds = results.getArgumentResult("seconds");
		if (!opShip.isPresent()) {
			sender.sendMessage(Ships.runShipsMessage("You need to look at a ships sign or specify a ship name", true));
			return false;
		}
		int seconds = 3;
		if (opSeconds.isPresent()) {
			seconds = opSeconds.get();
		}
		LoadableShip ship = opShip.get();
		ship.updateStructure();
		ShipsStructure structure = ship.getStructure();
		structure.getAllBlocks().stream().forEach(h -> {
			try {
				h.save(true);
			} catch (BlockHandlerNotReady e) {
				e.printStackTrace();
				return;
			}
			h.getBlock().setType(Material.BEDROCK);
		});
		Bukkit.getScheduler().scheduleSyncDelayedTask(Ships.getPlugin(), () -> {
			structure.getAllBlocks().stream().forEach(h -> {
				h.apply();
			});
		}, 20 * seconds);
		return true;
	}
}
