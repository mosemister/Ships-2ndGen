package org.ships.block.configuration;

import org.bukkit.Material;

public class MaterialConfiguration {
	Material material;
	MovementInstruction instruction;

	public MaterialConfiguration(Material material) {
		this(material, MovementInstruction.OBSTACLE);
	}

	public MaterialConfiguration(Material material, MovementInstruction instruction) {
		this.material = material;
		this.instruction = instruction;
	}

	public Material getMaterial() {
		return this.material;
	}

	public MovementInstruction getInstruction() {
		return this.instruction;
	}

	public MaterialConfiguration setInstruction(MovementInstruction instruction) {
		this.instruction = instruction;
		return this;
	}
}
