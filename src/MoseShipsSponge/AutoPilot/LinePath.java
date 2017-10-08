package MoseShipsSponge.AutoPilot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.util.Direction;

import com.flowpowered.math.vector.Vector3i;

public class LinePath {
	
	Vector3i g_pos_1;
	Vector3i g_pos_2;
	Direction g_direction = Direction.NONE;
	
	public LinePath(Vector3i pos1, Vector3i pos2) {
		g_pos_1 = pos1;
		g_pos_2 = pos2;
	}
	
	public List<Vector3i> createLine(int speed){
		if(g_pos_1.getX() > g_pos_2.getX()) {
			g_direction = Direction.NORTH;
			return createNorth(speed);
		}else if(g_pos_1.getX() < g_pos_2.getX()) {
			g_direction = Direction.SOUTH;
			return createSouth(speed);
		}else if(g_pos_1.getZ() > g_pos_2.getZ()) {
			g_direction = Direction.WEST;
			return createWest(speed);
		}else if(g_pos_1.getZ() < g_pos_2.getZ()) {
			g_direction = Direction.EAST;
			return createEast(speed);
		}else if(g_pos_1.getY() > g_pos_2.getY()) {
			g_direction = Direction.DOWN;
			return createDown(speed);
		}else if(g_pos_1.getY() < g_pos_2.getY()) {
			g_direction = Direction.UP;
			return createUp(speed);
		}
		return new ArrayList<>();
	}
	
	public Direction getDirection() {
		return g_direction;
	}
	
	public Optional<Vector3i> get(int speed, int A){
		List<Vector3i> list = createLine(speed);
		if(list.size() >= A) {
			return Optional.empty();
		}else if(list.size() <= 0) {
			return Optional.empty();
		}
		return Optional.of(list.get(A));
	}
	
	private List<Vector3i> createUp(int speed){
		List<Vector3i> list = new ArrayList<>();
		int totalDistance = g_pos_2.getY() - g_pos_1.getY();
		System.out.println("Total Distance: " + totalDistance);
		int moves = totalDistance/speed;
		for(int Y = 0; Y < moves; Y++) {
			Vector3i base = new Vector3i(0, ((1+Y)*speed), 0);
			list.add(g_pos_1.clone().add(base));
		}
		return list;
	}
	
	private List<Vector3i> createDown(int speed){
		List<Vector3i> list = new ArrayList<>();
		int totalDistance = g_pos_1.getX() - g_pos_2.getX();
		System.out.println("Total Distance: " + totalDistance);
		int moves = totalDistance/speed;
		for(int Y = moves; Y > 0; Y--) {
			Vector3i base = new Vector3i(0, (-(1-Y)*speed), 0);
			list.add(g_pos_1.clone().min(base));
		}
		return list;
	}
	
	private List<Vector3i> createSouth(int speed){
		List<Vector3i> list = new ArrayList<>();
		int totalDistance = g_pos_2.getX() - g_pos_1.getX();
		System.out.println("Total Distance: " + totalDistance);
		int moves = totalDistance/speed;
		for(int X = 0; X < moves; X++) {
			Vector3i base = new Vector3i(((1+X)*speed), 0, 0);
			list.add(g_pos_1.clone().add(base));
		}
		return list;
	}
	
	private List<Vector3i> createNorth(int speed){
		List<Vector3i> list = new ArrayList<>();
		int totalDistance = g_pos_1.getX() - g_pos_2.getX();
		System.out.println("Total Distance: " + totalDistance);
		int moves = totalDistance/speed;
		for(int X = moves; X > 0; X--) {
			Vector3i base = new Vector3i((-(1-X)*speed), 0, 0);
			list.add(g_pos_1.clone().min(base));
		}
		return list;
	}
	
	private List<Vector3i> createEast(int speed){
		List<Vector3i> list = new ArrayList<>();
		int totalDistance = g_pos_2.getZ() - g_pos_1.getZ();
		System.out.println("Total Distance: " + totalDistance);
		int moves = totalDistance/speed;
		for(int Z = 0; Z < moves; Z++) {
			Vector3i base = new Vector3i(0, 0, ((1+Z)*speed));
			list.add(g_pos_1.clone().add(base));
		}
		return list;
	}
	
	private List<Vector3i> createWest(int speed){
		List<Vector3i> list = new ArrayList<>();
		int totalDistance = g_pos_1.getZ() - g_pos_2.getZ();
		System.out.println("Total Distance: " + totalDistance);
		int moves = totalDistance/speed;
		for(int Z = moves; Z > 0; Z--) {
			Vector3i base = new Vector3i(0, 0, (-(1-Z)*speed));
			list.add(g_pos_1.clone().min(base));
		}
		return list;
	}

}
