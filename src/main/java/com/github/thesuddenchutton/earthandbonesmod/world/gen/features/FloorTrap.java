package com.github.thesuddenchutton.earthandbonesmod.world.gen.features;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

public class FloorTrap extends OreFeature{

	public FloorTrap(Codec<OreConfiguration> p_66531_) {
	
		super(p_66531_);
	}
	@Override
	public boolean place(FeaturePlaceContext<OreConfiguration> p_159865_) {
	      
		LevelAccessor levelaccessor = p_159865_.level();
	    OreConfiguration oreconfiguration = p_159865_.config();
	    for(int j = 1; j <= 150; ++j) {
	    	BlockPos blockpos = p_159865_.origin().above(j);
		    BlockPos blockpos1 = p_159865_.origin().above(j+1);
		    BlockState blockstate = levelaccessor.getBlockState(blockpos);
		    BlockState blockstate1 = levelaccessor.getBlockState(blockpos1);

		    if (blockstate1.isAir() && blockstate.is(Blocks.STONE)) {
				levelaccessor.setBlock(blockpos, oreconfiguration.targetStates.get(0).state, 0);
		    	return true;
		    }
	    }
	    return true;
	}
}