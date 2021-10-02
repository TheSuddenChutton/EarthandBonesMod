package com.github.thesuddenchutton.earthandbonesmod.util;

import java.util.Random;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;

public class AnythingTest extends RuleTest{

	@Override
	public boolean test(BlockState p_74310_, Random p_74311_) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected RuleTestType<?> getType() {
		// TODO Auto-generated method stub
		return RuleTestType.ALWAYS_TRUE_TEST;
	}

}
