package org.ships.event.commandsNew;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.ships.event.commandsNew.argument.CommandArgument;
import org.ships.event.commandsNew.executor.CMDArgumentResults;
import org.ships.event.commandsNew.executor.CMDExecutor;
import org.ships.plugin.Ships;
import org.ships.utils.ListUtil;

public class CMDEmulator implements TabExecutor {
	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
		CMDArgumentResults results = null;
		try {
			results = new CMDArgumentResults(commandSender, strings);
		} catch (IOException e) {
			commandSender.sendMessage(Ships.runShipsMessage("Unknown command", true));
			return false;
		}
		Optional<CMDExecutor> opExe = results.getFinalCommand();
		if (!opExe.isPresent()) {
			commandSender.sendMessage(Ships.runShipsMessage("Please provide the next part of the argument. The following are valid. " + ListUtil.asStringList(results.findPotentionalNextArgument(commandSender, ""), ", ", a -> a.getAssignedKey()), true));
			return true;
		}
		CMDExecutor exe = opExe.get();
		if (!exe.onExecute(commandSender, results)) {
			commandSender.sendMessage(Ships.runShipsMessage("Usage: \n" + exe.getUsage(commandSender), true));
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
		String[] rawArgs = new String[strings.length - 1];
		for (int A = 0; A < rawArgs.length; ++A) {
			rawArgs[A] = strings[A];
		}
		CMDArgumentResults results = null;
		try {
			results = new CMDArgumentResults(commandSender, rawArgs);
		} catch (IOException e) {
			commandSender.sendMessage(Ships.runShipsMessage("Unknown command", true));
			return new ArrayList<String>();
		}
		CMDArgumentResults finalResult = results;
		Set<CommandArgument<? extends Object>> set = results.findPotentionalNextArgument(commandSender, strings[strings.length - 1]);
		System.out.println("Command Arguments on Emulator: " + set.size());
		ArrayList<String> ret = new ArrayList<String>();
		set.stream().forEach(a -> {
			ret.addAll(a.onTab(commandSender, finalResult, strings[strings.length - 1]));
		});
		Collections.sort(ret);
		return ret;
	}
}
