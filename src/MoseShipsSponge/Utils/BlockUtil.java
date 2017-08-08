package MoseShipsSponge.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;

public class BlockUtil {
	
	public static BlockState[] deserialBlockState(List<String> list){
		List<BlockState> list2 = new ArrayList<>();
		Collection<BlockState> states = Sponge.getRegistry().getAllOf(BlockState.class);
		list.stream().forEach(b -> {
			Optional<BlockState> opBlock = states.stream().filter(s -> s.getId().equals(b)).findAny();
			if(opBlock.isPresent()){
				list2.add(opBlock.get());
			}
		});
		return list2.toArray(new BlockState[list2.size()]);
	}
	
	public static ItemStackSnapshot[] deseriaItemSnapshot(List<String> list){
		List<ItemStackSnapshot> list2 = new ArrayList<>();
		Collection<ItemType> states = Sponge.getRegistry().getAllOf(ItemType.class);
		list.stream().forEach(b -> {
			Optional<ItemType> opBlock = states.stream().filter(s -> s.getId().equals(b)).findAny();
			if(opBlock.isPresent()){
				list2.add(opBlock.get().getTemplate());
			}
		});
		return list2.toArray(new ItemStackSnapshot[list2.size()]);
	}
	
	public static List<String> serial(BlockState... states){
		List<String> list = new ArrayList<>();
		for(BlockState state : states){
			list.add(state.getId());
		}
		return list;
	}
	
	public static List<String> serial(ItemStackSnapshot... states){
		List<String> list = new ArrayList<>();
		for(ItemStackSnapshot state : states){
			String serial = state.getType().getId();
			list.add(serial);
		}
		return list;
	}

}
