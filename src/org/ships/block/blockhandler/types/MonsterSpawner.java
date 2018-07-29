package org.ships.block.blockhandler.types;

import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.EntityType;
import org.ships.block.blockhandler.BlockHandler;
import org.ships.block.blockhandler.BlockPriority;

public class MonsterSpawner implements BlockHandler<CreatureSpawner> {
	Block block;
	BlockData data;
	EntityType type;
	int spawnDelay;
	int minDelay;
	int maxDelay;
	int spawnCount;
	int maxNearEntities;
	int requiredPlayerRange;

	@Override
	public Block getBlock() {
		return this.block;
	}

	@Override
	public void setBlock(Block block) {
		this.block = block;
	}

	@Override
	public boolean isReady() {
		return this.getBlock().getState() instanceof CreatureSpawner;
	}

	@Override
	public void save(boolean forRemoval) {
		this.saveBlockData();
		this.saveDelay();
		this.saveMaxDelay();
		this.saveMaxEntities();
		this.saveMinDelay();
		this.savePlayerRange();
		this.saveSpawnCount();
		this.saveType();
	}

	@Override
	public void saveBlockData() {
		this.data = this.getBlock().getBlockData();
	}

	@Override
	public void applyBlockData() {
		this.getBlock().setBlockData(this.data);
	}

	@Override
	public BlockPriority getPriority() {
		return BlockPriority.SPECIAL;
	}

	@Override
	public void apply(CreatureSpawner blockstate) {
		this.applyDelay(blockstate);
		this.applyMaxDelay(blockstate);
		this.applyMaxEntities(blockstate);
		this.applyMinDelay(blockstate);
		this.applyPlayerRange(blockstate);
		this.applySpawnCount(blockstate);
		this.applyType(blockstate);
	}

	public void applyType(CreatureSpawner blockstate) {
		blockstate.setSpawnedType(this.type);
	}

	public void saveType() {
		this.type = this.getState().getSpawnedType();
	}

	public void applyDelay(CreatureSpawner blockstate) {
		blockstate.setDelay(this.spawnDelay);
	}

	public void saveDelay() {
		this.spawnDelay = this.getState().getDelay();
	}

	public void applyMinDelay(CreatureSpawner blockstate) {
		blockstate.setMinSpawnDelay(this.minDelay);
	}

	public void saveMinDelay() {
		this.minDelay = this.getState().getMinSpawnDelay();
	}

	public void applyMaxDelay(CreatureSpawner blockstate) {
		blockstate.setMaxSpawnDelay(this.maxDelay);
	}

	public void saveMaxDelay() {
		this.maxDelay = this.getState().getMaxSpawnDelay();
	}

	public void applySpawnCount(CreatureSpawner blockstate) {
		blockstate.setSpawnCount(this.spawnCount);
	}

	public void saveSpawnCount() {
		this.spawnCount = this.getState().getSpawnCount();
	}

	public void applyPlayerRange(CreatureSpawner blockstate) {
		blockstate.setRequiredPlayerRange(this.requiredPlayerRange);
	}

	public void savePlayerRange() {
		this.requiredPlayerRange = this.getState().getRequiredPlayerRange();
	}

	public void applyMaxEntities(CreatureSpawner blockstate) {
		blockstate.setMaxNearbyEntities(this.maxNearEntities);
	}

	public void saveMaxEntities() {
		this.maxNearEntities = this.getState().getMaxNearbyEntities();
	}
}
