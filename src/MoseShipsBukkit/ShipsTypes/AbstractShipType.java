package MoseShipsBukkit.ShipsTypes;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;

import MoseShipsBukkit.MovingShip.MovementMethod;
import MoseShipsBukkit.MovingShip.MovingBlock;
import MoseShipsBukkit.StillShip.Vessel.MovableVessel;

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
		this.material = new ArrayList<>(moveIn);
		this.autoPilot = autopilot;
	}
	
	@Override
	public boolean shouldFall(MovableVessel vessel) {
		Set<MovingBlock> blocks = MovingBlock.convert(vessel, MovementMethod.MOVE_DOWN);
		if(checkRequirements(vessel, MovementMethod.MOVE_DOWN, blocks, null)) {
			return true;
		}
		return false;
	}

	@Override
	public File getTypeFile() {
		return file;
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
		return speed;
	}

	@Override
	public Set<Material> getMoveInMaterials() {
		return new HashSet<>(material);
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
		return autoPilot;
	}

	@Override
	public int getMinBlocks() {
		return min;
	}

	@Override
	public int getMaxBlocks() {
		return max;
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
