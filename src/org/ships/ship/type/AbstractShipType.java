package org.ships.ship.type;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.ships.block.MovingBlock;
import org.ships.ship.Ship;
import org.ships.ship.movement.MovementMethod;

public abstract class AbstractShipType implements VesselType {
	protected File file;
	protected String name;
	protected int speed;
	protected int boost;
	protected int max;
	protected int min;
	protected List<Material> material;
	protected boolean autoPilot;

	public AbstractShipType(File file, String name, int speed, int boost, int maxBlocks, int minBlocks, boolean autopilot, Material... movein) {
		this(file, name, speed, boost, maxBlocks, minBlocks, autopilot, Arrays.asList(movein));
	}

	public AbstractShipType(File file, String name, int speed, int boost, int maxBlocks, int minBlocks, boolean autopilot, Collection<Material> moveIn) {
		this.file = file;
		this.name = name;
		this.speed = speed;
		this.boost = boost;
		this.max = maxBlocks;
		this.min = minBlocks;
		this.material = new ArrayList<Material>(moveIn);
		this.autoPilot = autopilot;
	}

	@Override
	public boolean shouldFall(Ship vessel) {
		Set<MovingBlock> blocks = MovingBlock.convert(vessel, MovementMethod.MOVE_DOWN);
		if (this.checkRequirements(vessel, MovementMethod.MOVE_DOWN, blocks, null)) {
			return true;
		}
		return false;
	}

	@Override
	public File getTypeFile() {
		return this.file;
	}

	@Override
	public void setTypeFile(File file) {
		this.file = file;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int getDefaultSpeed() {
		return this.speed;
	}

	@Override
	public Set<Material> getMoveInMaterials() {
		return new HashSet<Material>(this.material);
	}

	@Override
	public void setMoveInMaterials(Collection<Material> material) {
		material.clear();
		material.addAll(material);
	}

	@Override
	public void setDefaultSpeed(int A) {
		this.speed = A;
	}

	@Override
	public void setDefaultBoostSpeed(int A) {
		this.boost = A;
	}

	@Override
	public int getDefaultBoostSpeed() {
		return this.boost;
	}

	@Override
	public boolean isAutoPilotAllowed() {
		return this.autoPilot;
	}

	@Override
	public int getMinBlocks() {
		return this.min;
	}

	@Override
	public int getMaxBlocks() {
		return this.max;
	}

	@Override
	public void setMinBlocks(int A) {
		this.min = A;
	}

	@Override
	public void setMaxBlocks(int A) {
		this.max = A;
	}
}
