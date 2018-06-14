package org.ships.block.blockhandler.types;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.ships.block.blockhandler.BlockHandler;
import org.ships.block.blockhandler.BlockPriority;

public class MonsterSpawner implements BlockHandler<org.bukkit.block.CreatureSpawner> {
	
		Block block;
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
		public void remove(Material material) {
			this.saveDelay();
			this.saveMaxDelay();
			this.saveMaxEntities();
			this.saveMinDelay();
			this.savePlayerRange();
			this.saveSpawnCount();
			this.saveType();
			this.block.setType(material);
		}

		@Override
		public BlockPriority getPriority() {
			return BlockPriority.SPECIAL;
		}

		@Override
		public void apply() {
			this.applyDelay();
			this.applyMaxDelay();
			this.applyMaxEntities();
			this.applyMinDelay();
			this.applyPlayerRange();
			this.applySpawnCount();
			this.applyType();
		}
		
		public void applyType() {
			getState().setSpawnedType(this.type);
		}
		
		public void saveType() {
			this.type = getState().getSpawnedType();
		}
		
		public void applyDelay() {
			getState().setDelay(this.spawnDelay);
		}
		
		public void saveDelay() {
			this.spawnDelay = getState().getDelay();
		}
		
		public void applyMinDelay() {
			getState().setMinSpawnDelay(this.minDelay);
		}
		
		public void saveMinDelay() {
			this.minDelay = getState().getMinSpawnDelay();
		}
		
		public void applyMaxDelay() {
			getState().setMaxSpawnDelay(this.maxDelay);
		}
		
		public void saveMaxDelay() {
			this.maxDelay = getState().getMaxSpawnDelay();
		}
		
		public void applySpawnCount() {
			getState().setSpawnCount(this.spawnCount);
		}
		
		public void saveSpawnCount() {
			this.spawnCount = getState().getSpawnCount();
		}
		
		public void applyPlayerRange() {
			getState().setRequiredPlayerRange(this.requiredPlayerRange);
		}
		
		public void savePlayerRange() {
			this.requiredPlayerRange = getState().getRequiredPlayerRange();
		}
		
		public void applyMaxEntities() {
			getState().setMaxNearbyEntities(this.maxNearEntities);
		}
		
		public void saveMaxEntities() {
			this.maxNearEntities = getState().getMaxNearbyEntities();
		}

}
