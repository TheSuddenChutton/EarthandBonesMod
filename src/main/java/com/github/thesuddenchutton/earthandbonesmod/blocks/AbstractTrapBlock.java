package com.github.thesuddenchutton.earthandbonesmod.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class AbstractTrapBlock extends Block{

	public AbstractTrapBlock(Properties p_49795_) {
		super(p_49795_);
	}
	public void alerted(BlockPos pos, Level level) {}
}
