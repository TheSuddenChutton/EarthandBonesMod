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
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext.Builder;

public class CreeperCocoonUpper extends Block {
	public static final BooleanProperty LEFT = BooleanProperty.create("left");
	public static final BooleanProperty FULLSIZE = BooleanProperty.create("fullsize");
	public static final IntegerProperty MATURITY = IntegerProperty.create("maturity", 0, 5);
	public static final IntegerProperty GROWTH_ABILITY = IntegerProperty.create("growth_ability", 0, 32);
	public static final IntegerProperty VISUAL = IntegerProperty.create("visual", 0, 2);

	public static Random rand = new Random();
	
	public CreeperCocoonUpper () {
		super(Properties.of(Material.CLAY).sound(SoundType.GRAVEL).strength(1.0f).requiresCorrectToolForDrops());
	    this.registerDefaultState(this.defaultBlockState().setValue(LEFT, false).setValue(FULLSIZE, false).setValue(MATURITY, 0).setValue(GROWTH_ABILITY, 32).setValue(VISUAL, 0));
	}
	@Override
	public void onPlace(BlockState p_60566_, Level p_60567_, BlockPos p_60568_, BlockState p_60569_, boolean p_60570_) {
		if(!EventHandler.ActiveCreeperCocoons.contains(p_60568_))EventHandler.ActiveCreeperCocoons.add(p_60568_);
	}
	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, Random p_60465_) {
		for (int i = 0; i < EventHandler.players.size(); i++) {
			if(!level.getBlockState(pos.above()).is(Blocks.JUNGLE_LEAVES) && !level.getBlockState(pos.above()).is(Blocks.OAK_LEAVES)) {
				if(!state.getValue(LEFT) && state.getValue(MATURITY) >= 3 && state.getValue(MATURITY) < 5) {
					System.out.println("Creepers");
					Creeper creeper = new Creeper(EntityType.CREEPER, level);
					level.addFreshEntity(creeper);
					BlockPos spawnspot = pos.north().below();
					
					creeper.setPos(spawnspot.getX(), spawnspot.getY(),spawnspot.getZ());
					creeper.setAggressive(true);
					creeper.getAttribute(Attributes.FOLLOW_RANGE).addTransientModifier(new AttributeModifier("Provoked", 100, Operation.ADDITION));
					creeper.getAttribute(Attributes.KNOCKBACK_RESISTANCE).addTransientModifier(new AttributeModifier("Provoked", 100, Operation.ADDITION));
					creeper.getAttribute(Attributes.JUMP_STRENGTH).addTransientModifier(new AttributeModifier("Provoked", 3, Operation.ADDITION));
					creeper.getAttribute(Attributes.ARMOR).addTransientModifier(new AttributeModifier("Provoked", 20, Operation.ADDITION));
					
					level.setBlock(pos, level.getBlockState(pos).setValue(CreeperCocoonUpper.LEFT, true).setValue(CreeperCocoonUpper.GROWTH_ABILITY, 0), 3);
					level.setBlock(pos.below(), level.getBlockState(pos.below()).setValue(CreeperCocoonUpper.LEFT, true).setValue(CreeperCocoonUpper.GROWTH_ABILITY, 0), 3);
				}
				level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
			}
			if(level.getBlockState(pos.below()).is(Registration.CREEPERCOCOON_LOWER.get()) && state.getValue(FULLSIZE)) {
				level.setBlock(pos, state.setValue(FULLSIZE, true), 3);
			}
			else if(state.getValue(FULLSIZE)) {
				level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
			}
			if(!EventHandler.ActiveCreeperCocoons.contains(pos) && EventHandler.isWithinDistance(pos, EventHandler.players.get(i).blockPosition(), 100) && !state.getValue(LEFT) && state.getValue(MATURITY) != 5)
			{
				EventHandler.ActiveCreeperCocoons.add(pos);
			}
		}
	}
	@Override
	public void attack(BlockState p_60499_, Level level, BlockPos cocoon, Player player) {
		if(EventHandler.ActiveCreeperCocoons.contains(cocoon))
		{
			EventHandler.ActiveCreeperCocoons.remove(cocoon);
		}
		if(!p_60499_.getValue(LEFT) && p_60499_.getValue(MATURITY) >= 3 && p_60499_.getValue(MATURITY) < 5) {
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
			
			level.setBlock(cocoon, level.getBlockState(cocoon).setValue(LEFT, true).setValue(GROWTH_ABILITY, 0).setValue(VISUAL, 1), 3);
			level.setBlock(cocoon.below(), level.getBlockState(cocoon.below()).setValue(VISUAL, 1), 3);
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
		p_55484_.add(LEFT);
		p_55484_.add(FULLSIZE);
		p_55484_.add(MATURITY);
		p_55484_.add(GROWTH_ABILITY);
		p_55484_.add(VISUAL);
	}

	@Override
	public List<ItemStack> getDrops(BlockState p_60537_, Builder p_60538_) {
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		switch(p_60537_.getValue(VISUAL)) {
			case 0: stacks.add(new ItemStack(Blocks.CREEPER_HEAD.asItem())); stacks.add(new ItemStack(Items.BONE.asItem(), 10)); break;
			case 1: stacks.add(new ItemStack(Registration.TOBACCO.get(), rand.nextInt(6)+3)); break;
			case 2: stacks.add(new ItemStack(Items.GUNPOWDER,15)); stacks.add(new ItemStack(Items.BONE_MEAL,15)); stacks.add(new ItemStack(Items.CREEPER_HEAD,rand.nextInt(3))); break;
			default: break;
		}
		return stacks;
	}
}
