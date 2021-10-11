package com.github.thesuddenchutton.earthandbonesmod.blocks;

import java.util.Random;

import com.github.thesuddenchutton.earthandbonesmod.handlers.EventHandler;
import com.github.thesuddenchutton.earthandbonesmod.setup.Registration;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;

public class SpiderNest extends Block {
	public static final IntegerProperty SPIDERS = IntegerProperty.create("spiderspawns", 0, 3);
	public static final IntegerProperty GROWTH_ABILITY = IntegerProperty.create("growth_ability", 0, 2);
	public static final int MAX_SPIDERS = 3, MAX_GROWTH = 2;
	public static Random rand = new Random();
	
	public SpiderNest () {
		super(Properties.of(Material.CLAY).sound(SoundType.GRAVEL).strength(1.0f).requiresCorrectToolForDrops());
	    this.registerDefaultState(this.defaultBlockState().setValue(SPIDERS, 3).setValue(GROWTH_ABILITY, 2));
	}
	@Override
	public void onPlace(BlockState p_60566_, Level p_60567_, BlockPos p_60568_, BlockState p_60569_, boolean p_60570_) {
		if(!EventHandler.ActiveSpiderNests.contains(p_60568_))EventHandler.ActiveSpiderNests.add(p_60568_);
	}
	@Override
	public void tick(BlockState p_60462_, ServerLevel p_60463_, BlockPos p_60464_, Random p_60465_) {
		for (int i = 0; i < EventHandler.players.size(); i++) {
			if(!EventHandler.ActiveSpiderNests.contains(p_60464_) && EventHandler.isWithinDistance(p_60464_, EventHandler.players.get(i).blockPosition(), 50))
			{
				EventHandler.ActiveSpiderNests.add(p_60464_);
			}
		}
	}
	@Override
	public void attack(BlockState p_60499_, Level level, BlockPos nest, Player player) {
		for (int i = 0; i < EventHandler.players.size(); i++) {
			if(EventHandler.isWithinDistance(nest, EventHandler.players.get(i).blockPosition(), 50))
			{
				level.playLocalSound(nest.getX(), nest.getY(), nest.getZ(), SoundEvents.SPIDER_STEP, SoundSource.HOSTILE, 2f, 1f, false);
				level.playLocalSound(nest.getX(), nest.getY(), nest.getZ(), SoundEvents.SPIDER_HURT, SoundSource.HOSTILE, 2f, 1f, false);
				level.playLocalSound(nest.getX(), nest.getY(), nest.getZ(), SoundEvents.SPIDER_AMBIENT, SoundSource.HOSTILE, 2f, 1f, false);
			}
		}
	}
	@Override
	public boolean removedByPlayer(BlockState state, Level level, BlockPos nest, Player player, boolean willHarvest,
			FluidState fluid) {
		int s = state.getValue(SPIDERS);
		while(s > 0) {
			s--;
			System.out.println("SPIDERS");
			Spider spider = new Spider(EntityType.SPIDER, level);
			level.addFreshEntity(spider);
			BlockPos spawnspot = nest;
			boolean foundspot = false;
			int i3 = 0;
			while(!foundspot && i3 < 30) {
				i3++;
				if(!level.getBlockState(spawnspot).is(Blocks.AIR) && !level.getBlockState(spawnspot).is(Blocks.CAVE_AIR)) {
					spawnspot = spawnspot.below();
				}
				if(!level.getBlockState(spawnspot).is(Blocks.AIR) && !level.getBlockState(spawnspot).is(Blocks.CAVE_AIR)) {
					spawnspot = spawnspot.east(rand.nextInt(3) - 1);
				}if(!level.getBlockState(spawnspot).is(Blocks.AIR) && !level.getBlockState(spawnspot).is(Blocks.CAVE_AIR)) {
					spawnspot = spawnspot.south(rand.nextInt(3) - 1);
				}if(!level.getBlockState(spawnspot).is(Blocks.AIR) && !level.getBlockState(spawnspot).is(Blocks.CAVE_AIR)) {
					spawnspot = spawnspot.north(rand.nextInt(3) - 1);
				}if(!level.getBlockState(spawnspot).is(Blocks.AIR) && !level.getBlockState(spawnspot).is(Blocks.CAVE_AIR)) {
					spawnspot = spawnspot.west(rand.nextInt(3) - 1);
				}if(!level.getBlockState(spawnspot).is(Blocks.AIR) && !level.getBlockState(spawnspot).is(Blocks.CAVE_AIR)) {
					spawnspot = spawnspot.above(rand.nextInt(3));
				}
				else {
					foundspot = true;
				}
			}
			spider.setPos(spawnspot.getX(), spawnspot.getY(),spawnspot.getZ());
			spider.setTarget(player);
			spider.setAggressive(true);
		}
		return super.removedByPlayer(state, level, nest, player, willHarvest, fluid);
	}
	@Override
	public boolean canHarvestBlock(BlockState state, BlockGetter world, BlockPos pos, Player player) {
		return (player.hasCorrectToolForDrops(Blocks.COBWEB.defaultBlockState()) && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() != Items.WOODEN_SWORD);
	}
	@Override
	public boolean isRandomlyTicking(BlockState p_49921_) {
		return true;
	}
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_55484_) {
		p_55484_.add(SPIDERS);
		p_55484_.add(GROWTH_ABILITY);
	}
}
