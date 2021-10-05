package com.github.thesuddenchutton.earthandbonesmod.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class Spikes extends Block{

	public Spikes() {
		super(Properties.of(Material.STONE).noCollission().air());
	}
	
	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		entity.hurt(new DamageSource("spike trap"), 4);
	}

}
