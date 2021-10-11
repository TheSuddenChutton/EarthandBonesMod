package com.github.thesuddenchutton.earthandbonesmod.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BuddingAmethystBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;

public class Spikes extends Block{
	public static DirectionProperty POINTING = BlockStateProperties.FACING;
	public static IntegerProperty DAMAGE = IntegerProperty.create("damage", 0, 10);
	
	public Spikes() {
		super(Properties.of(Material.STONE).noCollission().air());
		this.registerDefaultState(this.defaultBlockState().setValue(POINTING, Direction.UP).setValue(DAMAGE, 5));
		
	}
	
	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if(entity instanceof Player)entity.hurt(new DamageSource("spiketrap"), state.getValue(DAMAGE)*(entity.fallDistance+1));
		else entity.hurt(new DamageSource("spike trap"), state.getValue(DAMAGE)-2);
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_55484_) {
		p_55484_.add(POINTING);
		p_55484_.add(DAMAGE);
	}
	
}
