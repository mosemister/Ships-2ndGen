package MoseShipsSponge.AutoPilot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.flowpowered.math.vector.Vector3i;

import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;

public class BasicShipPath {
	
	protected Vector3i g_pos1;
	protected Vector3i g_pos2;
	protected List<LinePath> g_paths;
	protected int g_speed;
	protected int g_current_path;
	protected int g_current_stage_in_path;
	
	public BasicShipPath(LiveShip ship, Vector3i pos2, boolean upAndOver) {
		this(ship.getLocation().getBlockPosition(), pos2, ship.getEngineSpeed(), upAndOver);
	}
	
	public BasicShipPath(Vector3i pos1, Vector3i pos2, int speed, boolean upAndOver) {
		g_pos1 = pos1;
		g_pos2 = pos2;
		g_speed = speed;
		g_paths = updatePath(upAndOver);
	}
	
	public List<LinePath> updatePath(boolean upAndOver){
		g_paths = new ArrayList<>();
		Vector3i previous = g_pos1;
		Vector3i next;
		if(upAndOver) {
			if(!(previous.getY() >= 300)) {
				next = new Vector3i(previous.getX(), 300, previous.getZ());
				g_paths.add(new LinePath(previous, next));
				previous = next;
			}
		}
		//match Z
		next = new Vector3i(previous.getX(), previous.getY(), g_pos2.getZ());
		g_paths.add(new LinePath(previous, next));
		previous = next;
		
		//match X
		next = new Vector3i(g_pos1.getX(), previous.getY(), previous.getZ());
		g_paths.add(new LinePath(previous, next));
		previous = next;
		
		//match Y
		next = new Vector3i(previous.getX(), g_pos2.getY(), previous.getZ());
		g_paths.add(new LinePath(previous, next));
		previous = next;
		return g_paths;
	}
	
	public Optional<LinePath> getCurrentPath() {
		if(g_paths.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(g_paths.get(g_current_path));
	}
	
	public Optional<Vector3i> getCurrentPos() {
		Optional<LinePath> opPath = getCurrentPath();
		if(!opPath.isPresent()) {
			return Optional.empty();
		}
		return opPath.get().get(g_speed, g_current_stage_in_path);
	}
	
	public Optional<Vector3i> getNextPosition(){
		System.out.print("getNextPos:");
		Optional<LinePath> opPath = getCurrentPath();
		g_current_path = g_current_path + 1;
		if(!opPath.isPresent()) {
			System.out.println(" exit 1");
			return Optional.empty();
		}
		LinePath path = opPath.get();
		System.out.println("Info: CurrentPath: " + g_current_path + " CurrentStage: " + g_current_stage_in_path + " SizeOfPath: " + path.createLine(g_speed).size());
		Optional<Vector3i> next = path.get(g_speed, g_current_stage_in_path);
		if(next.isPresent()) {
			System.out.println(" exit 2");
			return next;
		}
		if(g_paths.size() != g_current_path) {
			System.out.println(" exit 3");
			return Optional.empty();
		}
		g_current_stage_in_path = 0;
		path = g_paths.get(g_current_path);
		System.out.println(" exit 4");
		return path.get(g_speed, g_current_stage_in_path);
	}
	
	public boolean hasNext() {
		return g_current_path != g_paths.size();
	}
	
	public static void main(String[] args) {
		BasicShipPath path = new BasicShipPath(new Vector3i(0, 0, 0), new Vector3i(0, 6, 0), 2, false);
		while(path.hasNext()) {
			Optional<Vector3i> opVector = path.getNextPosition();
			if(opVector.isPresent()) {
				Vector3i vector = opVector.get();
				System.out.println("Pos: " + vector.getX() + "," + vector.getY() + "," + vector.getZ());
			}
			System.out.println("Pos: Unknown");
		}
	}

}
