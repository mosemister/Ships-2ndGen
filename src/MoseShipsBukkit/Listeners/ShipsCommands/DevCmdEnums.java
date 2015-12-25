package MoseShipsBukkit.Listeners.ShipsCommands;

public enum DevCmdEnums{
	LOADEDVESSELS(0),
	VESSELTYPES(1),
	CVESSELTYPES(2),
	MATERIALSLIST(3),
	RAMMATERIALS(4),
	ALL(5),
	STRUCTURE(6);

	private byte id;
	DevCmdEnums(int id){
		this.id = (byte)id;
	}
	
	public int getID(){return id;}
}
