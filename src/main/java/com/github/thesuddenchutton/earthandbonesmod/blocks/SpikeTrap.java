package com.github.thesuddenchutton.earthandbonesmod.blocks;

import java.util.Random;

import com.github.thesuddenchutton.earthandbonesmod.setup.Registration;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BasePressurePlateBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RedStoneOreBlock;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;

public class SpikeTrap extends Block{

	public SpikeTrap() {
		super(Properties.of(Material.STONE));
	}
	
	
	
	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random rand) {
		level.setBlock(pos.above(), Blocks.AIR.defaultBlockState(), UPDATE_ALL);
	}	
	public void stepOn(Level p_154299_, BlockPos p_154300_, BlockState p_154301_, Entity p_154302_) {
	   interact(p_154301_, p_154299_, p_154300_);
	   super.stepOn(p_154299_, p_154300_, p_154301_, p_154302_);
	}
	
	public InteractionResult use(BlockState p_55472_, Level p_55473_, BlockPos p_55474_, Player p_55475_, InteractionHand p_55476_, BlockHitResult p_55477_) {
	   if (!p_55473_.isClientSide) {
	      interact(p_55472_, p_55473_, p_55474_);
	   }
	
	   ItemStack itemstack = p_55475_.getItemInHand(p_55476_);
	   return itemstack.getItem() instanceof BlockItem && (new BlockPlaceContext(p_55475_, p_55476_, itemstack, p_55477_)).canPlace() ? InteractionResult.PASS : InteractionResult.SUCCESS;
	}
	
	private static void interact(BlockState p_55493_, Level p_55494_, BlockPos p_55495_) {
	   p_55494_.setBlock(p_55495_.above(), Registration.RUBBLE.get().defaultBlockState(), 3);
	
	}
	
	public boolean isRandomlyTicking(BlockState p_55486_) {
	   return true;
	}
}
