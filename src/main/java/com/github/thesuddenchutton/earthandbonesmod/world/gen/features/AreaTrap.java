package com.github.thesuddenchutton.earthandbonesmod.world.gen.features;

import java.util.ArrayList;

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

public class AreaTrap extends OreFeature{

	ArrayList<Block> b;
	boolean a;
	public AreaTrap(Codec<OreConfiguration> p_66531_, ArrayList<Block> block, boolean aboveground) {
		super(p_66531_);
		b = block;
		a = aboveground;
	}
	@Override
	public boolean place(FeaturePlaceContext<OreConfiguration> p_159865_) {
	      
		LevelAccessor levelaccessor = p_159865_.level();
	    OreConfiguration oreconfiguration = p_159865_.config();
	    BlockPos blockpos = p_159865_.origin();
	    BlockState blockstate;
    	int air = 0;
    	if(a) {
	    	while(!levelaccessor.getBlockState(blockpos.above(air)).isAir() && air < 50)air++;
	    }
    	int amountspawned = 0;
	    if(air < 50) {
		    for(int y = -2; y <= 2; y++) {
		    	for(int x = -2; x <= 2; x++) {
		    		for(int z = -2; z <= 2; z++) {
		    		    blockstate = levelaccessor.getBlockState(blockpos.east(x).above(y).south(z));
		    		    if (b.contains(blockstate.getBlock())) {
							levelaccessor.setBlock(blockpos.east(x).above(y).south(z), oreconfiguration.targetStates.get(0).state, 0);
							if(amountspawned < oreconfiguration.size) {
								amountspawned++;
							}
							else {
								return true;
							}
					    }
				    }
			    }
			}
	    }
		return true;	
	}
}