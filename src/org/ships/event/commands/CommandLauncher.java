package org.ships.event.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.ships.event.commands.gui.ShipsGUICommand;

public abstract class CommandLauncher {
	String COMMAND;
	String DESCRIPTION;
	boolean PLAYERCOMMAND;
	boolean CONSOLECOMMAND;
	String PERMISSION;
	String EXTRAARGS;
	Class<? extends ShipsGUICommand> GUI;
	static List<CommandLauncher> CLASSES = new ArrayList<CommandLauncher>();

	public abstract void playerCommand(Player var1, String[] var2);

	public abstract void consoleCommand(ConsoleCommandSender var1, String[] var2);

	public CommandLauncher(String arg, String extraArgs, String description, String permission, boolean playerCommand, boolean consoleCommand) {
		this.COMMAND = arg;
		this.DESCRIPTION = description;
		this.PLAYERCOMMAND = playerCommand;
		this.CONSOLECOMMAND = consoleCommand;
		this.PERMISSION = permission;
		this.EXTRAARGS = extraArgs;
		CLASSES.add(this);
	}

	public CommandLauncher(String arg, String extraArgs, String description, String permission, boolean playerCommand, boolean consoleCommand, Class<? extends ShipsGUICommand> gui) {
		this.COMMAND = arg;
		this.DESCRIPTION = description;
		this.PLAYERCOMMAND = playerCommand;
		this.CONSOLECOMMAND = consoleCommand;
		this.PERMISSION = permission;
		this.EXTRAARGS = extraArgs;
		this.GUI = gui;
		CLASSES.add(this);
	}

	public String getCommand() {
		return this.COMMAND;
	}

	public String getDescription() {
		return this.DESCRIPTION;
	}

	public boolean isPlayerCommand() {
		return this.PLAYERCOMMAND;
	}

	public boolean isConsoleCommand() {
		return this.CONSOLECOMMAND;
	}

	public String getPermissions() {
		return this.PERMISSION;
	}

	public String getExtraArgs() {
		return this.EXTRAARGS;
	}

	public boolean hasGUI() {
		return this.GUI != null;
	}

	public void runGUI(HumanEntity player) {
		for (ShipsGUICommand command : ShipsGUICommand.getInterfaces()) {
			if (!command.getClass().equals(this.GUI))
				continue;
			command.onInterfaceBoot(player);
			return;
		}
	}

	public static List<CommandLauncher> getCommand(String command) {
		ArrayList<CommandLauncher> commands = new ArrayList<CommandLauncher>();
		for (CommandLauncher launcher : CLASSES) {
			if (!launcher.getCommand().equals(command))
				continue;
			commands.add(launcher);
		}
		return commands;
	}

	public static List<CommandLauncher> getCommands() {
		return CLASSES;
	}
}
