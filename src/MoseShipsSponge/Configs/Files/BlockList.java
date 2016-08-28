package MoseShipsSponge.Configs.Files;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.block.trait.BlockTrait;

import MoseShipsSponge.ShipsMain;
import MoseShipsSponge.Configs.BasicConfig;

public class BlockList extends BasicConfig {

	List<BlockState> MATERIALS = new ArrayList<>();
	List<BlockState> RAM = new ArrayList<>();

	public BlockList() {
		super("/Configuration/MaterialsList");
		applyMissing();
		// code for testing purpose only
		MATERIALS.addAll(getAllPossibleStates(BlockTypes.WALL_SIGN));
		MATERIALS.addAll(getAllPossibleStates(BlockTypes.PLANKS));

	}
	
	public BlockList applyMissing(){
		getAllPossibleStates().stream().forEach(b -> {
			set(false, b.getId(), "enabled");
		});
		save();
		return this;
	}

	public List<BlockState> getMaterialsList() {
		return MATERIALS;
	}

	public List<BlockState> getRamMaterialsList() {
		return RAM;
	}

	public List<BlockState> getUnusedMaterialsList() {
		List<BlockState> states = getAllPossibleStates();
		states.removeAll(RAM);
		states.removeAll(MATERIALS);
		return states;
	}

	public boolean contains(BlockState state, ListType type) {
		switch (type) {
			case MATERIALS:
				return MATERIALS.stream().anyMatch(b -> b.equals(state));
			case NONE:
				return getUnusedMaterialsList().stream().anyMatch(s -> s.equals(state));
			case RAM:
				return RAM.stream().anyMatch(b -> b.equals(state));
		}
		return false;
	}

	public List<BlockState> getContains(BlockType block, ListType type) {
		switch (type) {
			case MATERIALS:
				return MATERIALS.stream().filter(b -> b.getType().equals(block)).collect(Collectors.toList());
			case NONE:
				return getUnusedMaterialsList().stream().filter(b -> b.getType().equals(block)).collect(Collectors.toList());
			case RAM:
				return RAM.stream().filter(b -> b.getType().equals(block)).collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

	public static List<BlockState> getAllPossibleStates(BlockType type) {
		List<BlockState> states = new ArrayList<>();
		/* type.getTraits().forEach(t -> {
		 * t.getPossibleValues().forEach(v -> {
		 * Optional<BlockState> state = type.getDefaultState().withTrait(t, v);
		 * if (state.isPresent()) {
		 * states.add(state.get());
		 * }
		 * });
		 * }); */
		for (BlockTrait<?> trait : type.getTraits()) {
			for (Object value : trait.getPossibleValues()) {
				Optional<BlockState> state = type.getDefaultState().withTrait(trait, value);
				if (state.isPresent()) {
					states.add(state.get());
				}
			}
		}
		return states;
	}

	public static List<BlockState> getAllPossibleStates() {
		List<BlockState> states = new ArrayList<>();
		Collection<BlockType> types = ShipsMain.getPlugin().getGame().getRegistry().getAllOf(BlockType.class);
		types.forEach(b -> {
			states.addAll(getAllPossibleStates(b));
		});
		return states;
	}

	public static enum ListType {
		MATERIALS,
		RAM,
		NONE;
	}

}
