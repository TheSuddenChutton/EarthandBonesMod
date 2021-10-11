package com.github.thesuddenchutton.earthandbonesmod.world.gen.features;

import java.util.ArrayList;
import java.util.Random;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.biome.Biomes;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class CeilTrap extends OreFeature{

	ArrayList<Block> b;
	boolean space, swamp;
	Random rand = new Random();
	public CeilTrap(Codec<OreConfiguration> p_66531_, ArrayList<Block> block, boolean needsSpace, boolean swamponly) {
		super(p_66531_);
		b = block;
		space = needsSpace;
		swamp = swamponly;
	}
	@Override
	public boolean place(FeaturePlaceContext<OreConfiguration> p_159865_) {
		LevelAccessor levelaccessor = p_159865_.level();
	    OreConfiguration oreconfiguration = p_159865_.config();
	    BlockPos pos = p_159865_.origin();
	    for (int i = 1; i <= oreconfiguration.size; i++) {
		    for(int j = 1; j <= 30; j++) {
		    	BlockPos blockpos = pos.above(j);
			    BlockPos blockpos1 = pos.above(j+1);
			    BlockState blockstate = levelaccessor.getBlockState(blockpos);
			    BlockState blockstate1 = levelaccessor.getBlockState(blockpos1);
	
			    //System.out.println(levelaccessor.getBiome(blockpos).getRegistryName().getPath());
			    if(!swamp || levelaccessor.getBiome(blockpos).getRegistryName().getPath().contains("swamp")){
				    if (b.contains(blockstate1.getBlock()) && (blockstate.isAir() || blockstate.canBeReplaced(Fluids.FLOWING_WATER)) && !space) {
						levelaccessor.setBlock(blockpos, oreconfiguration.targetStates.get(0).state, 0);
				    
				    	return true;
				    }
				    else if(b.contains(blockstate1.getBlock()) && blockstate.isAir()){
				    	BlockState blockstateb = levelaccessor.getBlockState(blockpos.below());
				    	if (blockstateb.isAir())
						{
							levelaccessor.setBlock(blockpos, oreconfiguration.targetStates.get(0).state, 0);
							System.out.println("CREEPER COCOON");
						}
				    	return true;
				    }
			    }
		    }
		    pos = pos.below(2).north(rand.nextInt(3)-1).east(rand.nextInt(3)-1);
	    }
	    return true;
	}
}