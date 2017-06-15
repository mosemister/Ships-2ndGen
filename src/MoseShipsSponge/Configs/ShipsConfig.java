package MoseShipsSponge.Configs;

public class ShipsConfig extends BasicConfig {

	// STRUCTURE

	public static final Object[] PATH_STRUCTURE_STRUCTURELIMITS_TRACKLIMIT = {
			"Advanced",
			"Structure",
			"StructureLimits",
			"TrackLimits" };

	public static final Object[] PATH_STRUCTURE_STRUCTURELIMITS_TRACKREPEATE = {
			"Advanced",
			"Structure",
			"StructureLimits",
			"TrackRepeate" };

	public static final Object[] PATH_STRUCTURE_STRUCTURELIMITS_SECTIONSIZE = {
			"Advanced",
			"Structure",
			"StructureLimits",
			"SectionSize" };

	// ALGORITHMS

	public static final Object[] PATH_ALGORITHMS_BLOCKFINDER = {
			"Advanced",
			"Algorithms",
			"BlockFinder" };

	public static final Object[] PATH_ALGORITHMS_MOVEMENT = {
			"Advanced",
			"Algorithms",
			"Movement" };

	public static final Object[] PATH_ALGORITHMS_CLEARFREQUENCY = {
			"Advanced",
			"Algorithms",
			"ClearFrequency" };

	// SCHEDULER

	public static final Object[] PATH_SCHEDULER_REPEATE = {
			"Advanced",
			"Scheduler",
			"Repeate" };

	public static final Object[] PATH_SCHEDULER_AUTOPILOT = {
			"Advanced",
			"Scheduler",
			"Autopilot" };

	public static final Object[] PATH_SCHEDULER_FALL = {
			"Advanced",
			"Scheduler",
			"Fall" };

	public static final Object[] PATH_SCHEDULER_STRUCTURE = {
			"Advanced",
			"Scheduler",
			"Structure" };

	public static final Object[] PATH_ONSNEAK_REMOVE_SHIP = {
			"Configuration",
			"OnSneak",
			"RemoveShipsBlocks" };

	// COLLIDE

	public static final Object[] PATH_COLLIDE_SHOW_TIME = {
			"Configuration",
			"Collide",
			"DisplayTime" };

	public static final Object[] PATH_COLLIDE_WILL_SHOW = {
			"Configuration",
			"Collide",
			"WillDisplay" };

	public static final Object[] PATH_COLLIDE_TO_SHOW = {
			"Configuration",
			"Collide",
			"ShowMaterial" };

	// SIGN

	public static final Object[] PATH_SIGN_FORCE_USERNAME = {
			"Configuration",
			"Sign",
			"ForceUsername" };

	// MESSAGE

	public static final Object[] PATH_MESSAGE_FUEL_FAILED_COLLECT = {
			"Configuration",
			"Messages",
			"Fuel",
			"FailedToCollectFuel" };

	public static final Object[] PATH_MESSAGE_FUEL_OUT = {
			"Configuration",
			"Messages",
			"Fuel",
			"OutOfFuel" };

	public static final Object[] PATH_MESSAGE_REQUIRED_BLOCK_MISSING = {
			"Configuration",
			"Messages",
			"RequiredBlock",
			"MissingBlock" };

	public static final Object[] PATH_MESSAGE_REQUIRED_BLOCK_MISSING_BURNER = {
			"Configuration",
			"Messages",
			"RequiredBlock",
			"MissingBurner" };

	public static final Object[] PATH_MESSAGE_SIZE_NONE = {
			"Configuration",
			"Messages",
			"Size",
			"None" };

	public static final Object[] PATH_MESSAGE_SIZE_MAX = {
			"Configuration",
			"Messages",
			"Size",
			"TooLarge" };

	public static final Object[] PATH_MESSAGE_SIZE_MIN = {
			"Configuration",
			"Messages",
			"Size",
			"TooSmall" };

	public static final Object[] PATH_MESSAGE_PERCENT_NOT_ENOUGH = {
			"Configuration",
			"Messages",
			"Percent",
			"NotEnoughPercent" };

	public static final Object[] PATH_MESSAGE_ENVIROMENT_NOT_IN_WATER = {
			"Configuration",
			"Messages",
			"Enviroment",
			"NotInWater" };

	public static final Object[] PATH_MESSAGE_ENVIROMENT_DETCTION_AHEAD = {
			"Configuration",
			"Messages",
			"Enviroment",
			"DetectionAhead" };

	public static final Object[] PATH_MESSAGE_AUTOPILOT_FINISHED = {
			"Configuration",
			"Messages",
			"AutoPilot",
			"Finished" };

	public static final Object[] PATH_MESSAGE_SIGN_CREATE_FAILED_NAME = {
			"Configuration",
			"Messages",
			"Sign",
			"Create",
			"Failed",
			"ByName" };

	public static final Object[] PATH_MESSAGE_SIGN_CREATE_FAILED_TYPE = {
			"Configuration",
			"Messages",
			"Sign",
			"Create",
			"Failed",
			"ByType" };

	public static final ShipsConfig CONFIG = new ShipsConfig();

	public ShipsConfig() {
		super("Configuration/Config");
		applyMissing();
	}

	public ShipsConfig applyMissing() {
		set(300, PATH_STRUCTURE_STRUCTURELIMITS_TRACKLIMIT);
		set(2, PATH_STRUCTURE_STRUCTURELIMITS_TRACKREPEATE);
		set(100, PATH_STRUCTURE_STRUCTURELIMITS_SECTIONSIZE);
		// this will be Ships6 when that algorithm is done
		set("Ships 6", PATH_ALGORITHMS_MOVEMENT);
		set("Ships 5", PATH_ALGORITHMS_BLOCKFINDER);
		set(10, PATH_ALGORITHMS_CLEARFREQUENCY);
		// SCHEDULER
		set(2, PATH_SCHEDULER_AUTOPILOT);
		set(6, PATH_SCHEDULER_FALL);
		set(1, PATH_SCHEDULER_STRUCTURE);
		set(20, PATH_SCHEDULER_REPEATE);
		// ONSNEAK stuff
		set(false, PATH_ONSNEAK_REMOVE_SHIP);
		// SIGN
		set(false, PATH_SIGN_FORCE_USERNAME);
		// COLLIDE stuff
		set(3, PATH_COLLIDE_SHOW_TIME);
		set(true, PATH_COLLIDE_WILL_SHOW);
		set("7:0", PATH_COLLIDE_TO_SHOW);
		// MESSAGE
		set("Failed to collect fuel", PATH_MESSAGE_FUEL_FAILED_COLLECT);
		set("Out of fuel", PATH_MESSAGE_FUEL_OUT);
		set("Ship is missing %block%", PATH_MESSAGE_REQUIRED_BLOCK_MISSING);
		set("Ship is missing burner", PATH_MESSAGE_REQUIRED_BLOCK_MISSING_BURNER);
		set("Ship is %over% over the limit", PATH_MESSAGE_SIZE_MAX);
		set("Ship is %under% under the limit", PATH_MESSAGE_SIZE_MIN);
		set("Ship requires %percent% percent more %blocks%", PATH_MESSAGE_PERCENT_NOT_ENOUGH);
		set("Ship is not in water", PATH_MESSAGE_ENVIROMENT_NOT_IN_WATER);
		set("Ship found detection, they are %block% for %time%", PATH_MESSAGE_ENVIROMENT_DETCTION_AHEAD);
		set("%Ship% auto-pilot finished", PATH_MESSAGE_AUTOPILOT_FINISHED);
		set("%Name% has already been taken", PATH_MESSAGE_SIGN_CREATE_FAILED_NAME);
		set("%Type% is not a ship type", PATH_MESSAGE_SIGN_CREATE_FAILED_TYPE);

		save();
		return this;
	}

}
