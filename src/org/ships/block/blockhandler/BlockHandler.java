package org.ships.block.blockhandler;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Rotatable;
import org.ships.block.blockhandler.types.DefaultBlockHandler;
import org.ships.plugin.BlockHandlerNotReady;
import org.ships.plugin.Ships;
import org.ships.ship.movement.MovementMethod;

public interface BlockHandler<T extends BlockState> {
	public static final Map<Material, Class<? extends BlockHandler<? extends BlockState>>> HANDLER = new HashMap<>();

	public Block getBlock();

	public void setBlock(Block var1);

	public void saveBlockData();

	public void applyBlockData();

	public BlockPriority getPriority();

	public boolean isReady();

	public default void save(boolean forRemoval) throws BlockHandlerNotReady {
		this.saveBlockData();
	}

	public default void apply(boolean force) {
		T state = this.getState();
		this.apply(state);
		state.update(force, false);
	}

	public default void apply() {
		this.apply(false);
	}

	public default void apply(T state) {
	}

	public default void remove(Material material) {
		this.getBlock().setType(material);
	}

	@SuppressWarnings("unchecked")
	public default T getState() {
		return (T) this.getBlock().getState();
	}

	public default void rotate(MovementMethod method) {
		if (!(this.getBlock().getBlockData() instanceof Rotatable)) {
			return;
		}
		Rotatable rotate = (Rotatable) this.getBlock().getBlockData();
		switch (method) {
			case ROTATE_RIGHT: {
				rotate.setRotation(Ships.getSideFace(rotate.getRotation(), false));
				break;
			}
			case ROTATE_LEFT: {
				rotate.setRotation(Ships.getSideFace(rotate.getRotation(), true));
				break;
			}
			default:
				return;
		}
	}

	public static void register(Class<? extends BlockHandler<? extends BlockState>> class1, Material material) {
		HANDLER.put(material, class1);
	}

	public static void register(Class<? extends BlockHandler<? extends BlockState>> class1, Material... materials) {
		for (Material material : materials) {
			BlockHandler.register(class1, material);
		}
	}

	public static BlockHandler<? extends BlockState> getBlockHandler(Block block) {
		Optional<Map.Entry<Material, Class<? extends BlockHandler<? extends BlockState>>>> opBlockHandler = HANDLER.entrySet().stream().filter(e -> e.getKey().equals(block.getType())).findFirst();
		BlockHandler<? extends BlockState> handler = null;
		if (!opBlockHandler.isPresent()) {
			handler = new DefaultBlockHandler();
			handler.setBlock(block);
			return handler;
		}
		Class<? extends BlockHandler<? extends BlockState>> classHandler = opBlockHandler.get().getValue();
		try {
			handler = classHandler.getConstructor(new Class[0]).newInstance(new Object[0]);
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		}
		handler.setBlock(block);
		return handler;
	}

	public static Set<BlockHandler<? extends BlockState>> convert(Collection<Block> blocks) {
		Set<BlockHandler<? extends BlockState>> list = new HashSet<>();
		blocks.stream().forEach(b -> list.add(BlockHandler.getBlockHandler(b)));
		return list;
	}

}
