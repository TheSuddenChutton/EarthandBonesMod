package com.github.thesuddenchutton.earthandbonesmod.world.gen.features;

import java.util.Random;

import com.github.thesuddenchutton.earthandbonesmod.setup.Registration;
import com.github.thesuddenchutton.earthandbonesmod.util.AnythingTest;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.GrowingPlantFeature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.configurations.GrowingPlantConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

public class FloorTrap extends OreFeature{

	public FloorTrap(Codec<OreConfiguration> p_66531_) {
	
		super(p_66531_);
	}
	
	public boolean place(FeaturePlaceContext<OreConfiguration> p_159865_) {
	      LevelAccessor levelaccessor = p_159865_.level();
	      OreConfiguration oreconfiguration = p_159865_.config();
	      Random random = p_159865_.random();
	      BlockPos.MutableBlockPos blockpos$mutableblockpos = p_159865_.origin().mutable();
	      BlockPos.MutableBlockPos blockpos$mutableblockpos1 = (MutableBlockPos) blockpos$mutableblockpos.mutable().above();
	      BlockState blockstate = levelaccessor.getBlockState(blockpos$mutableblockpos);

	      for(int j = 1; j <= 100; ++j) {
	         BlockState blockstate1 = levelaccessor.getBlockState(blockpos$mutableblockpos1);
	         if (blockstate1.isAir() && blockstate.is(Blocks.STONE)) {
	        	 //i need to spawn the block here.
	        	 return true;
	         }
	   	  }

	      return true;
	}
}