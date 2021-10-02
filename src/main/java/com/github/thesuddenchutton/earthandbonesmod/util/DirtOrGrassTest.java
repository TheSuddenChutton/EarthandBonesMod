package com.github.thesuddenchutton.earthandbonesmod.util;

import java.util.Random;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;

public class DirtOrGrassTest extends RuleTest{

	@Override
	public boolean test(BlockState p_74310_, Random p_74311_) {
		// TODO Auto-generated method stub
		return p_74310_.is(Blocks.GRASS_BLOCK) || p_74310_.is(Blocks.DIRT);
	}

	@Override
	protected RuleTestType<?> getType() {
		// TODO Auto-generated method stub
		return RuleTestType.BLOCK_TEST;
	}

}
