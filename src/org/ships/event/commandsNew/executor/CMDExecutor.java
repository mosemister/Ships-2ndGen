package org.ships.event.commandsNew.executor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.bukkit.command.CommandSender;
import org.ships.event.commandsNew.argument.CommandArgument;
import org.ships.event.commandsNew.argument.OptionalCommandArgument;
import org.ships.utils.ListUtil;

public abstract class CMDExecutor {
	static final Set<CMDExecutor> executors = new HashSet<CMDExecutor>();
	private List<CommandArgument<? extends Object>> arguments = new ArrayList<CommandArgument<? extends Object>>();

	@SafeVarargs
	public CMDExecutor(CommandArgument<? extends Object>... arguments) {
		this(Arrays.asList(arguments));
	}

	public CMDExecutor(Collection<CommandArgument<? extends Object>> arguments) {
		this.arguments.addAll(arguments);
	}

	public List<CommandArgument<? extends Object>> getCommandArguments() {
		return this.arguments;
	}

	public Optional<CommandArgument<? extends Object>> getCommandArgument(String key) {
		return this.arguments.stream().filter(i -> i.getAssignedKey().equalsIgnoreCase(key)).findAny();
	}

	public String getUsage(CommandSender sender) {
		return ListUtil.asStringList(this.arguments, " ", a -> {
			if (a instanceof OptionalCommandArgument && ((OptionalCommandArgument<? extends Object>) a).getDefaultValue(sender).isPresent()) {
				return "[" + a.getAssignedKey() + "]";
			}
			return "<" + a.getAssignedKey() + ">";
		});
	}

	public abstract boolean onExecute(CommandSender var1, CMDArgumentResults var2);

	public static void register(CMDExecutor executor) {
		executors.add(executor);
	}
}
