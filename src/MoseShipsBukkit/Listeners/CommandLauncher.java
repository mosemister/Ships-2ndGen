package MoseShipsBukkit.Listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public abstract class CommandLauncher {
	
	String COMMAND;
	String DESCRIPTION;
	boolean PLAYERCOMMAND;
	boolean CONSOLECOMMAND;
	String PERMISSION;
	String EXTRAARGS;
	
	static List<CommandLauncher> CLASSES = new ArrayList<CommandLauncher>();

	public abstract void playerCommand(Player player, String[] args);
	public abstract void consoleCommand(ConsoleCommandSender sender, String[] args);
	
	public CommandLauncher(String arg, String extraArgs, String description, String permission, boolean playerCommand, boolean consoleCommand){
		COMMAND = arg;
		DESCRIPTION = description;
		PLAYERCOMMAND = playerCommand;
		CONSOLECOMMAND = consoleCommand;
		PERMISSION = permission;
		EXTRAARGS = extraArgs;
		CLASSES.add(this);
	}
	
	public String getCommand(){
		return COMMAND;
	}
	
	public String getDescription(){
		return DESCRIPTION;
	}
	
	public boolean isPlayerCommand(){
		return PLAYERCOMMAND;
	}
	
	public boolean isConsoleCommand(){
		return CONSOLECOMMAND;
	}
	
	public String getPermissions(){
		return PERMISSION;
	}
	
	public String getExtraArgs(){
		return EXTRAARGS;
	}
	
	public static List<CommandLauncher> getCommands(){
		return CLASSES;
	}
}
