package com.github.thesuddenchutton.earthandbonesmod.world.gen.features;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.phys.AABB;

public class FloorTrap extends OreFeature{

	boolean undergroundOnly;
	public FloorTrap(Codec<OreConfiguration> p_66531_, boolean undergroundOnly) {
		super(p_66531_);
		this.undergroundOnly = undergroundOnly;
	}
	
	@Override
	public boolean place(FeaturePlaceContext<OreConfiguration> p_159865_) {
	    LevelAccessor levelaccessor = p_159865_.level();
	    OreConfiguration oreconfiguration = p_159865_.config();
		boolean undergroundPassed = !undergroundOnly;
		for(int j = 1; j <= 200; j++) {
	    	BlockPos blockpos = p_159865_.origin().above(200 - j);
		    BlockPos blockpos1 = p_159865_.origin().above(201 - j);
		    BlockState blockstate = levelaccessor.getBlockState(blockpos);
		    BlockState blockstate1 = levelaccessor.getBlockState(blockpos1);
	    	if(!blockstate1.isAir() && !undergroundPassed) undergroundPassed = true;
		    if ((blockstate1.isAir() || blockstate1.is(Blocks.CAVE_AIR)) && undergroundPassed && blockstate.getBlock() == Blocks.STONE)
		    {
	    		levelaccessor.setBlock(blockpos, oreconfiguration.targetStates.get(0).state, 0);
			}
	    	if(!blockstate.isAir()&&!blockstate.is(Blocks.CAVE_AIR)) {
	    		j++;
	    	}
	    }
	    return true;
	}
}