package com.github.thesuddenchutton.earthandbonesmod.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.thesuddenchutton.earthandbonesmod.handlers.EventHandler;
import com.github.thesuddenchutton.earthandbonesmod.setup.Registration;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext.Builder;

public class CreeperCocoonLower extends Block {
	public static final IntegerProperty VISUAL = CreeperCocoonUpper.VISUAL;
	public static Random rand = new Random();
	
	public CreeperCocoonLower () {
		super(Properties.of(Material.CLAY).sound(SoundType.GRAVEL).strength(1.0f).requiresCorrectToolForDrops());
	    this.registerDefaultState(this.defaultBlockState().setValue(VISUAL, 0));
	}
	@Override
	public void tick(BlockState p_60462_, ServerLevel level, BlockPos p_60464_, Random p_60465_) {
		for (int i = 0; i < EventHandler.players.size(); i++) {
			if(!level.getBlockState(p_60464_.above()).is(Registration.CREEPERCOCOON_UPPER.get())) {
				level.setBlock(p_60464_, Blocks.AIR.defaultBlockState(), 3);
			}
			else if(!EventHandler.ActiveCreeperCocoons.contains(p_60464_.above()) && EventHandler.isWithinDistance(p_60464_, EventHandler.players.get(i).blockPosition(), 100) && p_60462_.getValue(VISUAL) == 0)
			{
				EventHandler.ActiveCreeperCocoons.add(p_60464_.above());
			}
		}
	}
	@Override
	public List<ItemStack> getDrops(BlockState p_60537_, Builder p_60538_) {
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		switch(p_60537_.getValue(VISUAL)) {
			case 0: stacks.add(new ItemStack(Blocks.CREEPER_HEAD.asItem())); stacks.add(new ItemStack(Items.BONE.asItem(), 10)); break;
			case 1: stacks.add(new ItemStack(Registration.TOBACCO.get(), rand.nextInt(6)+3)); break;
			case 2: stacks.add(new ItemStack(Items.GUNPOWDER,5)); break;
			default: break;
		}
		return stacks;
	}
	@Override
	public void onRemove(BlockState p_60515_, Level p_60516_, BlockPos p_60517_, BlockState p_60518_,
			boolean p_60519_) {
		
	}
	@Override
	public void attack(BlockState p_60499_, Level level, BlockPos cocoon, Player player) {
		for (int i = 0; i < EventHandler.players.size(); i++) {
			if(EventHandler.ActiveCreeperCocoons.contains(cocoon.above()))
			{
				EventHandler.ActiveCreeperCocoons.remove(cocoon.above());
			}
			if(p_60499_.getValue(VISUAL) == 0) {
				System.out.println("Creepers");
				Creeper creeper = new Creeper(EntityType.CREEPER, level);
				level.addFreshEntity(creeper);
				BlockPos spawnspot = cocoon;
				boolean foundspot = false;
				int i3 = 0;
				while(!foundspot && i3 < 30) {
					i3++;
					if(!level.getBlockState(spawnspot).is(Blocks.AIR) && !level.getBlockState(spawnspot).is(Blocks.CAVE_AIR) && !level.getBlockState(spawnspot).is(Blocks.COBWEB)) {
						spawnspot = spawnspot.below();
					}
					if(!level.getBlockState(spawnspot).is(Blocks.AIR) && !level.getBlockState(spawnspot).is(Blocks.CAVE_AIR) && !level.getBlockState(spawnspot).is(Blocks.COBWEB)) {
						spawnspot = spawnspot.east(rand.nextInt(3) - 1);
					}if(!level.getBlockState(spawnspot).is(Blocks.AIR) && !level.getBlockState(spawnspot).is(Blocks.CAVE_AIR) && !level.getBlockState(spawnspot).is(Blocks.COBWEB)) {
						spawnspot = spawnspot.south(rand.nextInt(3) - 1);
					}if(!level.getBlockState(spawnspot).is(Blocks.AIR) && !level.getBlockState(spawnspot).is(Blocks.CAVE_AIR) && !level.getBlockState(spawnspot).is(Blocks.COBWEB)) {
						spawnspot = spawnspot.north(rand.nextInt(3) - 1);
					}if(!level.getBlockState(spawnspot).is(Blocks.AIR) && !level.getBlockState(spawnspot).is(Blocks.CAVE_AIR) && !level.getBlockState(spawnspot).is(Blocks.COBWEB)) {
						spawnspot = spawnspot.west(rand.nextInt(3) - 1);
					}if(!level.getBlockState(spawnspot).is(Blocks.AIR) && !level.getBlockState(spawnspot).is(Blocks.CAVE_AIR) && !level.getBlockState(spawnspot).is(Blocks.COBWEB)) {
						spawnspot = spawnspot.above(rand.nextInt(3));
					}
					else {
						foundspot = true;
					}
				}
				creeper.setPos(spawnspot.getX(), spawnspot.getY(),spawnspot.getZ());
				creeper.setTarget(player);
				creeper.setAggressive(true);
				creeper.getAttribute(Attributes.FOLLOW_RANGE).addTransientModifier(new AttributeModifier("Provoked", 100, Operation.ADDITION));
				creeper.getAttribute(Attributes.KNOCKBACK_RESISTANCE).addTransientModifier(new AttributeModifier("Provoked", 100, Operation.ADDITION));
				creeper.getAttribute(Attributes.ARMOR).addTransientModifier(new AttributeModifier("Provoked", 20, Operation.ADDITION));
				
				level.setBlock(cocoon, level.getBlockState(cocoon).setValue(CreeperCocoonUpper.VISUAL, 1), 3);
				level.setBlock(cocoon.above(), level.getBlockState(cocoon.above()).setValue(VISUAL, 1).setValue(CreeperCocoonUpper.GROWTH_ABILITY, 0).setValue(CreeperCocoonUpper.LEFT, true), 3);
			}
		}
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
		p_55484_.add(VISUAL);
	}
	
}
