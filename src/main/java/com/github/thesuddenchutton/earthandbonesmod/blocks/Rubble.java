package com.github.thesuddenchutton.earthandbonesmod.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.thesuddenchutton.earthandbonesmod.handlers.EventHandler;
import com.github.thesuddenchutton.earthandbonesmod.setup.Registration;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
import net.minecraftforge.common.IPlantable;

public class Rubble extends FallingBlock {

	public static ServerLevel serverlevel;
	
	public Rubble () {
		super(Properties.of(Material.SAND).sound(SoundType.GRAVEL).destroyTime(1).strength(1.0f, 0.5f).requiresCorrectToolForDrops());
	}
	@Override
	public List<ItemStack> getDrops(BlockState p_60537_, Builder p_60538_) {
		ArrayList<ItemStack> l = new ArrayList<ItemStack>();
		l.add(new ItemStack(Registration.RUBBLE_ITEM.get()));
		return l;
	}
	@Override
	public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing,
			IPlantable plantable) {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean canHarvestBlock(BlockState state, BlockGetter world, BlockPos pos, Player player) {
		return true;
	}

	@Override
	public boolean isRandomlyTicking(BlockState p_49921_) {
		return true;
	}
	  
	@Override
	public void tick(BlockState st, ServerLevel level, BlockPos pos, Random rand) {
		if(serverlevel == null)serverlevel = level;
		if(EventHandler.level == null)EventHandler.level = serverlevel;
		if(!level.isWaterAt(pos.below()) && !level.isEmptyBlock(pos.below()) && rand.nextBoolean()) {
			if(level.isEmptyBlock(pos.below().east()) && rand.nextInt(5) == 0) {
				level.setBlock(pos.below().east(), st, UPDATE_ALL);
				if(!level.isEmptyBlock(pos.below().below().east()))level.setBlock(pos.below().below().east(), st, UPDATE_ALL);
			}
			if(level.isEmptyBlock(pos.below().west()) && rand.nextInt(5) == 0) {
				level.setBlock(pos.below().west(), st, UPDATE_ALL);
				if(!level.isEmptyBlock(pos.below().below().west()))level.setBlock(pos.below().below().west(), st, UPDATE_ALL);
				level.setBlock(pos, Blocks.AIR.defaultBlockState(), UPDATE_ALL);	
			}
			if(level.isEmptyBlock(pos.below().north()) && rand.nextInt(5) == 0) {
				level.setBlock(pos.below().north(), st, UPDATE_ALL);
				if(!level.isEmptyBlock(pos.below().below().north()))level.setBlock(pos.below().below().north(), st, UPDATE_ALL);
				level.setBlock(pos, Blocks.AIR.defaultBlockState(), UPDATE_ALL);
			}
			if(level.isEmptyBlock(pos.below().south()) && rand.nextInt(5) == 0) {
				level.setBlock(pos.below().south(), st, UPDATE_ALL);
				if(!level.isEmptyBlock(pos.below().below().south()))level.setBlock(pos.below().below().south(), st, UPDATE_ALL);
				level.setBlock(pos, Blocks.AIR.defaultBlockState(), UPDATE_ALL);	
			}
		}else
		{
			level.setBlock(pos.below(), st, UPDATE_ALL);
			level.setBlock(pos, Blocks.AIR.defaultBlockState(), UPDATE_ALL);

			if(level.getBlockState(pos.below()).getBlock() == Blocks.DIRT || level.getBlockState(pos.below()).getBlock() == Blocks.STONE) {
				level.setBlock(pos.below(), st, UPDATE_ALL);
			}
			if(level.getBlockState(pos.below(2)).getBlock() == Blocks.DIRT || level.getBlockState(pos.below(2)).getBlock() == Blocks.STONE) {
				level.setBlock(pos.below(2), st, UPDATE_ALL);
			}
			if(level.getBlockState(pos.below().west()).getBlock() == Blocks.DIRT || level.getBlockState(pos.below().west()).getBlock() == Blocks.STONE) {
				level.setBlock(pos.below().west(), st, UPDATE_ALL);
			}
			if(level.getBlockState(pos.below().east()).getBlock() == Blocks.DIRT || level.getBlockState(pos.below().east()).getBlock() == Blocks.STONE) {
				level.setBlock(pos.below().east(), st, UPDATE_ALL);
			}
			if(level.getBlockState(pos.below().north()).getBlock() == Blocks.DIRT || level.getBlockState(pos.below().north()).getBlock() == Blocks.STONE) {
				level.setBlock(pos.below().north(), st, UPDATE_ALL);
			}
			if(level.getBlockState(pos.below().south()).getBlock() == Blocks.DIRT || level.getBlockState(pos.below().south()).getBlock() == Blocks.STONE) {
				level.setBlock(pos.below().south(), st, UPDATE_ALL);
			}
		}
	}
	
}
