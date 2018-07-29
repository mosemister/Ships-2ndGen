package org.ships.event.commandsNew.executor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.command.CommandSender;
import org.ships.event.commandsNew.argument.CommandArgument;
import org.ships.event.commandsNew.argument.OptionalCommandArgument;

public class CMDArgumentResults {
	List<Map.Entry<CommandArgument<? extends Object>, Object>> used = new ArrayList<Map.Entry<CommandArgument<? extends Object>, Object>>();
	String[] unparsedArguments;
	CommandSender sender;

	public CMDArgumentResults(CommandSender sender, String... args) throws IOException {
		this.unparsedArguments = args;
		this.sender = sender;
		this.used = this.findCurrentArguments();
		if (this.used == null) {
			throw new IOException("No command executor found with arguments");
		}
	}

	public CMDArgumentResults(CommandSender sender, Collection<Map.Entry<CommandArgument<? extends Object>, Object>> arguments, String... rawArgs) {
		this.used.addAll(arguments);
		this.unparsedArguments = rawArgs;
		this.sender = sender;
	}

	@SuppressWarnings("unchecked")
	public <T> Optional<T> getArgumentResult(String key) {
		Optional<Entry<CommandArgument<? extends Object>, Object>> opEntry = this.used.stream().filter(e -> e.getKey().getAssignedKey().equals(key)).findAny();
		if (!opEntry.isPresent()) {
			return Optional.empty();
		}
		return Optional.of((T)opEntry.get().getValue());
	}

	public <T> CMDArgumentResults addArgumentResult(String rawArg, CommandArgument<T> argument, T value) {
		ArrayList<Map.Entry<CommandArgument<? extends Object>, Object>> list = new ArrayList<Map.Entry<CommandArgument<? extends Object>, Object>>(this.used);
		String[] unparsedArguments = new String[this.unparsedArguments.length];
		for (int A = 0; A < this.unparsedArguments.length; ++A) {
			unparsedArguments[A] = this.unparsedArguments[A];
		}
		unparsedArguments[this.unparsedArguments.length] = rawArg;
		list.add(new CustomEntry<>(argument, value));
		return new CMDArgumentResults(this.sender, list, unparsedArguments);
	}

	public Optional<CMDExecutor> getFinalCommand() {
		Set<CMDExecutor> set = this.getCurrentCommands();
		System.out.println("getting final cmd from: " + set.size());
		return set.stream().filter(e -> {
			int argsSize = e.getCommandArguments().size();
			int usedSize = this.used.size();
			System.out.println("\tSize: " + argsSize + " Used: " + usedSize);
			return argsSize == usedSize;
		}).findAny();
	}

	public Set<CMDExecutor> getCurrentCommands() {
		System.out.println("Getting current commands");
		return CMDExecutor.executors.stream().filter(c -> {
			System.out.println("\tFound Exe");
			List<CommandArgument<? extends Object>> list = c.getCommandArguments();
			System.out.println("\t\tExe has arguments: " + list.size());
			int forTarget = 0;
			int check = 0;
			for (Map.Entry<CommandArgument<? extends Object>, Object> entry : this.used) {
				System.out.println("\t\tFound used entry: " + entry.getKey().getAssignedKey());
				check = forTarget;
				for (int A = forTarget; A < list.size(); ++A) {
					CommandArgument<? extends Object> targetArg = list.get(A);
					System.out.println("\t\t\tFound target argument: " + entry.getKey().getAssignedKey());
					if (!entry.getKey().getAssignedKey().equals(targetArg.getAssignedKey()))
						continue;
					++forTarget;
					System.out.println("\t\t\tadded argument");
					break;
				}
				if (check != forTarget)
					continue;
				System.out.println("\t\tIgnore exe");
				return false;
			}
			System.out.println("\tall arguments matched. Done with exe");
			return true;
		}).collect(Collectors.toSet());
	}

	private List<Map.Entry<CommandArgument<? extends Object>, Object>> findCurrentArguments() {
		System.out.println("Finding current arguments.");
		ArrayList<Map.Entry<CommandArgument<? extends Object>, Object>> ret = new ArrayList<Map.Entry<CommandArgument<? extends Object>, Object>>();
		for (int A = 0; A < this.unparsedArguments.length; ++A) {
			System.out.println("\tnew unparsed Argument");
			int check = ret.size();
			Set<CommandArgument<? extends Object>> set = this.findNextArgument(A);
			System.out.println("\t\tFindNextArgument: " + set.size());
			for (CommandArgument<? extends Object> argument : set) {
				System.out.println("\t\t\tKey: " + argument.getAssignedKey());
				Optional<? extends Object> opResult = argument.onParse(this.sender, this, this.unparsedArguments[A]);
				if (opResult.isPresent()) {
					System.out.println("\t\t\t\tParsed value");
					ret.add(new CustomEntry<CommandArgument<? extends Object>, Object>(argument, opResult.get()));
					break;
				}
				System.out.println("\t\t\t\tCould not parse value");
			}
			if (check != ret.size())
				continue;
			System.out.println("\tCheck == return size (" + ret.size() + "). returning null");
			for (Map.Entry<CommandArgument<? extends Object>, Object> entry : ret) {
				System.out.println("\t\tEntry: " + entry.getKey().getAssignedKey());
			}
			return null;
		}
		return ret;
	}

	/*private Set<CommandArgument<? extends Object>> findNextArgument() {
		int size = this.used.size();
		if (this.unparsedArguments.length <= size) {
			return new HashSet<>();
		}
		return this.findNextArgument(size);
	}*/

	private Set<CommandArgument<? extends Object>> findNextArgument(int target) {
		String toParse = this.unparsedArguments[target];
		Set<CMDExecutor> currentPossibleCommands = this.getCurrentCommands();
		Set<CommandArgument<? extends Object>> next = new HashSet<>();
		currentPossibleCommands.stream().filter(e -> e.getCommandArguments().size() > this.used.size()).forEach(e -> {
			next.add(e.getCommandArguments().get(this.used.size()));
		});
		return next.stream().filter(a -> a.onParse(this.sender, this, toParse).isPresent()).collect(Collectors.toSet());
	}

	public Set<CommandArgument<? extends Object>> findNextArgument(CommandSender sender, String toParse) {
		Set<CMDExecutor> currentPossibleCommands = this.getCurrentCommands();
		System.out.println("Current Possible commands: " + currentPossibleCommands.size());
		Set<CommandArgument<? extends Object>> next = new HashSet<>();
		currentPossibleCommands.stream().filter(e -> e.getCommandArguments().size() > this.used.size()).forEach(e -> {
			next.add(e.getCommandArguments().get(this.used.size()));
		});
		System.out.println("Every possible argument: " + next.size());
		return next.stream().filter(a -> {
			if (a.onParse(sender, this, toParse).isPresent()) {
				return true;
			}
			if (a instanceof OptionalCommandArgument && ((OptionalCommandArgument<? extends Object>) a).getDefaultValue(sender).isPresent()) {
				return true;
			}
			return false;
		}).collect(Collectors.toSet());
	}

	public Set<CommandArgument<? extends Object>> findPotentionalNextArgument(CommandSender sender, String toParse) {
		Set<CMDExecutor> currentPossibleCommands = this.getCurrentCommands();
		System.out.println("Current Possible commands: " + currentPossibleCommands.size());
		Set<CommandArgument<? extends Object>> next = new HashSet<>();
		currentPossibleCommands.stream().filter(e -> e.getCommandArguments().size() > this.used.size()).forEach(e -> {
			next.add(e.getCommandArguments().get(this.used.size()));
		});
		System.out.println("Every possible argument: " + next.size());
		return next.stream().filter(a -> a.canParse(sender, this, toParse)).collect(Collectors.toSet());
	}

	private class CustomEntry<A, B> implements Map.Entry<A, B> {
		A key;
		B value;

		public CustomEntry(A key, B value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public A getKey() {
			return this.key;
		}

		@Override
		public B getValue() {
			return this.value;
		}

		@Override
		public B setValue(B b) {
			this.value = b;
			return this.value;
		}
	}

}
