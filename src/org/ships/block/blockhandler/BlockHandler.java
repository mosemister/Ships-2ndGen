package org.ships.block.blockhandler;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Rotatable;
import org.ships.block.blockhandler.types.DefaultBlockHandler;
import org.ships.plugin.Ships;
import org.ships.ship.movement.MovementMethod;

public interface BlockHandler <T extends BlockState> {
	
	public static final Map<Material, Class<? extends BlockHandler<? extends BlockState>>> HANDLER = new HashMap<>(); 
	
	public Block getBlock();
	public void setBlock(Block block);
	public void remove(Material material);
	public BlockPriority getPriority();
	public void apply();
	
	@SuppressWarnings("unchecked")
	public default T getState() {
		return (T)getBlock().getState();
	}
	
	public default void rotate(MovementMethod method) {
		if(!(getBlock().getBlockData() instanceof Rotatable)) {
			return;
		}
		Rotatable rotate = (Rotatable)getBlock().getBlockData();
		switch(method) {
		case ROTATE_RIGHT:
			rotate.setRotation(Ships.getSideFace(rotate.getRotation(), false));
			break;
		case ROTATE_LEFT:
			rotate.setRotation(Ships.getSideFace(rotate.getRotation(), true));
			break;
		default: break;
		}
	}
	
	public static void register(Class<? extends BlockHandler<? extends BlockState>> class1, Material material) {
		HANDLER.put(material, class1);
	}
	
	public static void register(Class<? extends BlockHandler<? extends BlockState>> class1, Material... materials) {
		for(Material material : materials) {
			register(class1, material);
		}
	}
	
	public static BlockHandler<? extends BlockState> getBlockHandler(Block block) {
		Optional<Entry<Material, Class<? extends BlockHandler<? extends BlockState>>>> opBlockHandler = HANDLER.entrySet().stream().filter(e -> e.getKey().equals(block.getType())).findFirst();
		BlockHandler<? extends BlockState> handler = null;
		if(!opBlockHandler.isPresent()) {
			handler = new DefaultBlockHandler();
			handler.setBlock(block);
			return handler;
		}
		Class<? extends BlockHandler<? extends BlockState>> classHandler = opBlockHandler.get().getValue();
		try {
			handler = classHandler.getConstructor().newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		}
		handler.setBlock(block);
		return handler;
	}
	
	public static Set<BlockHandler<? extends BlockState>> convert(Collection<Block> blocks){
		List<BlockHandler<? extends BlockState>> list = new ArrayList<>();
		blocks.stream().forEach(b -> list.add(getBlockHandler(b)));
		return new HashSet<>(list);
	}

}
