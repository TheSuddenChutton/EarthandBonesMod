package com.github.thesuddenchutton.earthandbonesmod.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.thesuddenchutton.earthandbonesmod.handlers.EventHandler;
import com.github.thesuddenchutton.earthandbonesmod.setup.Registration;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
import net.minecraftforge.common.IPlantable;

public class DeadLeaves extends FallingBlock {

	public static ServerLevel serverlevel;
	
	public DeadLeaves () {
		super(Properties.of(Material.LEAVES).sound(SoundType.CAVE_VINES).destroyTime(0.1f).strength(1.0f, 0.5f));
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
	public void tick(BlockState p_53216_, ServerLevel level, BlockPos p, Random rand) {
		
		if(rand.nextInt(3) == 0 && level.getBlockState(p.below()).is(Blocks.AIR)) {
			super.tick(p_53216_, level, p, rand);	
		}
		else if(rand.nextInt(3) == 0 && level.getBlockState(p.below()).is(Blocks.AIR)) {
			ItemEntity stack = new ItemEntity(level, p.getX(), p.getY()-0.5f, p.getZ(), new ItemStack(Items.STICK, rand.nextInt(2)+1));
			level.addFreshEntity(stack);
		}
		else if(rand.nextInt(3) == 0) {
			level.setBlock(p, Blocks.AIR.defaultBlockState(), 0);
		}
	}
}
