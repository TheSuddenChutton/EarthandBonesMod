package com.github.thesuddenchutton.earthandbonesmod.handlers;

import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.ArrayList;
import java.util.Random;

import com.github.thesuddenchutton.earthandbonesmod.blocks.CreeperCocoonLower;
import com.github.thesuddenchutton.earthandbonesmod.blocks.CreeperCocoonUpper;
import com.github.thesuddenchutton.earthandbonesmod.blocks.SpiderNest;
import com.github.thesuddenchutton.earthandbonesmod.setup.Registration;
import com.github.thesuddenchutton.earthandbonesmod.world.gen.BlockGenerator;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.TickEvent.WorldTickEvent;

import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;


@EventBusSubscriber
public class EventHandler {

	public static Random rand = new Random();
	public static int ticking = 0;
	public static ArrayList<BlockPos> TremorPos = new ArrayList<BlockPos>();
	public static ArrayList<BlockPos> ActiveSpiderNests = new ArrayList<BlockPos>();
	public static ArrayList<BlockPos> ActiveCreeperCocoons = new ArrayList<BlockPos>();
	public static ArrayList<BlockPos> ActiveRubble = new ArrayList<BlockPos>();
	static int rubblenum = 0;
	public static ArrayList<Player> players = new ArrayList<Player>();
	
	//TICK FUNCTIONS
	static void handleCaveInRubble(Level level) {
		if(TremorPos.size() > 0) {
			BlockPos pos = TremorPos.get(0).above(rand.nextInt(3)).below(rand.nextInt(3)).north(rand.nextInt(3)).south(rand.nextInt(3)).east(rand.nextInt(3)).west(rand.nextInt(3));
			if((level.getBlockState(pos).getBlock() == Blocks.DIRT || level.getBlockState(pos).getBlock() == Blocks.STONE) && level.isLoaded(pos))
			{
				level.setBlock(pos, Registration.RUBBLE.get().defaultBlockState(), 0);
			}
			if(TremorPos.size() > 0)TremorPos.remove(0);
		}
	}
	
	static void handleFallingRubble(Level level) {
		if(ActiveRubble.size()>0) {
			for (int i2 = 0; i2 < 10; i2++) {
				rubblenum++;
				boolean quedForRemoval = true;
				for (int i = 0; i < players.size(); i++) {
					if(ActiveRubble.size() > 0) {
						Player player = players.get(i);
						if(rubblenum >= ActiveRubble.size())rubblenum = 0;
						BlockPos rubble = ActiveRubble.get(rubblenum);
						
						if(level.getBlockState(rubble).getBlock() != Registration.RUBBLE.get()) {
							quedForRemoval = false;
							ActiveRubble.remove(rubblenum);
							rubblenum--;
						}
						else {
							if(isWithinDistance(rubble, player.blockPosition(), 35)) {
								UpdateRubble(rubble, level);
							}
							if(isWithinDistance(rubble, player.blockPosition(), 50)) {
								quedForRemoval = false;
							}
						}
					}
				}
				if(quedForRemoval && ActiveRubble.size()>0) {
					ActiveRubble.remove(rubblenum);
					rubblenum--;
				}
			}
		}
	}
	public static void UpdateRubble(BlockPos rubble, Level level){
		if(level!=null) {
			if(!level.isWaterAt(rubble.below()) && !level.isEmptyBlock(rubble.below())) {
				if(level.isEmptyBlock(rubble.below().east()) && rand.nextInt(3) == 0) {
					level.setBlock(rubble.below().east(), Registration.RUBBLE.get().defaultBlockState(), 3);
					if(!level.isEmptyBlock(rubble.below().below().east())) {
						level.setBlock(rubble.below().below().east(), Registration.RUBBLE.get().defaultBlockState(), 3);
						ActiveRubble.add(rubble.below().east());
					}
					level.setBlock(rubble, Blocks.AIR.defaultBlockState(), 3);	
					ActiveRubble.remove(rubble);
				}
				if(level.isEmptyBlock(rubble.below().west()) && rand.nextInt(5) == 0) {
					level.setBlock(rubble.below().west(), Registration.RUBBLE.get().defaultBlockState(), 3);
					if(!level.isEmptyBlock(rubble.below().below().west()))
					{
						level.setBlock(rubble.below().below().west(), Registration.RUBBLE.get().defaultBlockState(), 3);
						ActiveRubble.add(rubble.below().west());		
					}
					level.setBlock(rubble, Blocks.AIR.defaultBlockState(), 3);	
					ActiveRubble.remove(rubble);		
				}
				if(level.isEmptyBlock(rubble.below().north()) && rand.nextInt(5) == 0) {
					level.setBlock(rubble.below().north(), Registration.RUBBLE.get().defaultBlockState(), 3);
					if(!level.isEmptyBlock(rubble.below().below().north())) {
						level.setBlock(rubble.below().below().north(), Registration.RUBBLE.get().defaultBlockState(), 3);
						ActiveRubble.add(rubble.below().north());		
					}
					level.setBlock(rubble, Blocks.AIR.defaultBlockState(), 3);
					ActiveRubble.remove(rubble);		
				}
				if(level.isEmptyBlock(rubble.below().south()) && rand.nextInt(5) == 0) {
					level.setBlock(rubble.below().south(), Registration.RUBBLE.get().defaultBlockState(), 3);
					if(!level.isEmptyBlock(rubble.below().below().south())) {
						level.setBlock(rubble.below().below().south(), Registration.RUBBLE.get().defaultBlockState(), 3);
						ActiveRubble.add(rubble.below().south());		
					}
					level.setBlock(rubble, Blocks.AIR.defaultBlockState(), 3);	
					ActiveRubble.remove(rubble);		
					
				}
			}else
			{
				level.setBlock(rubble.below(), Registration.RUBBLE.get().defaultBlockState(), 3);
				ActiveRubble.add(rubble.below());	
				level.setBlock(rubble, Blocks.AIR.defaultBlockState(), 3);
				ActiveRubble.remove(rubble);
				if(level.getBlockState(rubble.below(2)).getBlock() == Blocks.DIRT || level.getBlockState(rubble.below(2)).getBlock() == Blocks.STONE && rand.nextBoolean()) {
					level.setBlock(rubble.below(2), Registration.RUBBLE.get().defaultBlockState(), 3);
					ActiveRubble.add(rubble.below(2));		
				}
				if(level.getBlockState(rubble.below().west()).getBlock() == Blocks.DIRT || level.getBlockState(rubble.below().west()).getBlock() == Blocks.STONE && rand.nextInt(10) == 0) {
					level.setBlock(rubble.below().west(), Registration.RUBBLE.get().defaultBlockState(), 3);
					ActiveRubble.add(rubble.below().west());		
					
				}
				if(level.getBlockState(rubble.below().east()).getBlock() == Blocks.DIRT || level.getBlockState(rubble.below().east()).getBlock() == Blocks.STONE  && rand.nextInt(10) == 0) {
					level.setBlock(rubble.below().east(), Registration.RUBBLE.get().defaultBlockState(), 3);
					ActiveRubble.add(rubble.below().east());		
					
				}
				if(level.getBlockState(rubble.below().north()).getBlock() == Blocks.DIRT || level.getBlockState(rubble.below().north()).getBlock() == Blocks.STONE && rand.nextInt(10) == 0) {
					level.setBlock(rubble.below().north(), Registration.RUBBLE.get().defaultBlockState(), 3);
					ActiveRubble.add(rubble.below().north());		
				}
				if(level.getBlockState(rubble.below().south()).getBlock() == Blocks.DIRT || level.getBlockState(rubble.below().south()).getBlock() == Blocks.STONE && rand.nextInt(10) == 0) {
					level.setBlock(rubble.below().south(), Registration.RUBBLE.get().defaultBlockState(), 3);
					ActiveRubble.add(rubble.below().south());		
				}
			}
		}
	}
	static void handleSpiderSpawns(Level level) {
		for (int i2 = 0; i2 < ActiveSpiderNests.size(); i2++) {
			if(rand.nextInt(5)==0) {
				boolean quedForRemoval = players.size()>0;
				for (int i = 0; i < players.size(); i++) {
					Player player = players.get(i);
					BlockPos nest = ActiveSpiderNests.get(i2);
					if(level.getBlockState(nest).getBlock() != Registration.SPIDERNEST.get()) {
						quedForRemoval = false;
						ActiveSpiderNests.remove(i2);
						i2--;
					}
					else{
						if(isWithinDistance(nest, player.blockPosition(), 10) && level.getBlockState(nest).getValue(SpiderNest.SPIDERS) > 0) {
							System.out.println("SPIDERS");
							Spider spider = new Spider(EntityType.SPIDER, level);
							level.addFreshEntity(spider);
							BlockPos spawnspot = nest;
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
							spider.setPos(spawnspot.getX(), spawnspot.getY(),spawnspot.getZ());
							spider.setTarget(player);
							spider.setAggressive(true);
							level.setBlock(nest, level.getBlockState(nest).setValue(SpiderNest.SPIDERS, level.getBlockState(nest).getValue(SpiderNest.SPIDERS)-1), 3);
						}
					}if(isWithinDistance(nest, player.blockPosition(), 75)) {
						quedForRemoval = false;
					}
				}
				if(quedForRemoval) {
					ActiveSpiderNests.remove(i2);
					i2--;
				}
			}
		}
	}
	
	static void handleNestGrowth(Level level) {
		for (int i = 0; i < ActiveSpiderNests.size(); i++) {
			if(level.getBlockState(ActiveSpiderNests.get(i)).getBlock() == Registration.SPIDERNEST.get() && rand.nextInt(3)==0) {
				growSpiderNest(ActiveSpiderNests.get(i), level);
			}
			else {
				ActiveSpiderNests.remove(i);
			}
		}
	}

	
	
	static void growSpiderNest(BlockPos nest, Level level) {
		switch (rand.nextInt(3)){
			case 0:	if(level.getBlockState(nest).getValue(SpiderNest.SPIDERS) > 3 && level.getBlockState(nest).getValue(SpiderNest.GROWTH_ABILITY) > 1 && rand.nextInt(36) == 0)addNewNest(nest, level);
					else if(level.getBlockState(nest).getValue(SpiderNest.SPIDERS) < 6 && rand.nextInt(5) == 0) level.setBlock(nest, level.getBlockState(nest).setValue(SpiderNest.SPIDERS, level.getBlockState(nest).getValue(SpiderNest.SPIDERS)+1), 3);
				break;
			case 1: int r = rand.nextInt(2)+2;
				for(int i = 0; i < r; i++) {
					if(level.getBlockState(nest).getValue(SpiderNest.GROWTH_ABILITY)+1 >= i && rand.nextInt(6) == 0)growWebs(nest, level);
				}
				break;
			case 2: 
				int r2 = rand.nextInt(4)+2;
				for(int i = 0; i < r2; i++) {
					if(rand.nextBoolean())killLeaves(nest, level);
				}
				break;
		}
	}
	static void addNewNest(BlockPos nest, Level level) {
		BlockPos spawnspot = nest;
		boolean foundspot = false;
		int i3 = 0;
		while(!foundspot && i3 < 20) {
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
			}else {
				foundspot = true;
			}
		}
		level.setBlock(nest, level.getBlockState(nest).setValue(SpiderNest.SPIDERS, 1).setValue(SpiderNest.GROWTH_ABILITY, Math.floorDiv(level.getBlockState(nest).getValue(SpiderNest.GROWTH_ABILITY),2)), 3);
		level.setBlock(spawnspot, level.getBlockState(nest), 3);
		System.out.println("SPIDER NEST GREW");
	}
	static void growWebs(BlockPos nest, Level level) {
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
			}else {
				foundspot = true;
			}
		}
		level.setBlock(spawnspot, Blocks.COBWEB.defaultBlockState(), 3);
	}
	static void killLeaves(BlockPos nest, Level level) {
		BlockPos spawnspot = nest;
		boolean foundspot = false;
		while(!foundspot) {
			if(!level.getBlockState(spawnspot).is(Blocks.OAK_LEAVES) && !level.getBlockState(spawnspot).is(Blocks.DARK_OAK_LEAVES) 
					&& !level.getBlockState(spawnspot).is(Blocks.JUNGLE_LEAVES) && !level.getBlockState(spawnspot).is(Blocks.BIRCH_LEAVES)) {
				spawnspot = spawnspot.below();
			}if(!level.getBlockState(spawnspot).is(Blocks.OAK_LEAVES) && !level.getBlockState(spawnspot).is(Blocks.DARK_OAK_LEAVES) 
					&& !level.getBlockState(spawnspot).is(Blocks.JUNGLE_LEAVES) && !level.getBlockState(spawnspot).is(Blocks.BIRCH_LEAVES)) {
				spawnspot = spawnspot.east(rand.nextInt(3) - 1);
			}if(!level.getBlockState(spawnspot).is(Blocks.OAK_LEAVES) && !level.getBlockState(spawnspot).is(Blocks.DARK_OAK_LEAVES) 
						&& !level.getBlockState(spawnspot).is(Blocks.JUNGLE_LEAVES) && !level.getBlockState(spawnspot).is(Blocks.BIRCH_LEAVES)) {
				spawnspot = spawnspot.south(rand.nextInt(3) - 1);
			}if(!level.getBlockState(spawnspot).is(Blocks.OAK_LEAVES) && !level.getBlockState(spawnspot).is(Blocks.DARK_OAK_LEAVES) 
					&& !level.getBlockState(spawnspot).is(Blocks.JUNGLE_LEAVES) && !level.getBlockState(spawnspot).is(Blocks.BIRCH_LEAVES)) {
				spawnspot = spawnspot.north(rand.nextInt(3) - 1);
			}if(!level.getBlockState(spawnspot).is(Blocks.OAK_LEAVES) && !level.getBlockState(spawnspot).is(Blocks.DARK_OAK_LEAVES) 
					&& !level.getBlockState(spawnspot).is(Blocks.JUNGLE_LEAVES) && !level.getBlockState(spawnspot).is(Blocks.BIRCH_LEAVES)) {
				spawnspot = spawnspot.west(rand.nextInt(3) - 1);
			}if(!level.getBlockState(spawnspot).is(Blocks.OAK_LEAVES) && !level.getBlockState(spawnspot).is(Blocks.DARK_OAK_LEAVES) 
					&& !level.getBlockState(spawnspot).is(Blocks.JUNGLE_LEAVES) && !level.getBlockState(spawnspot).is(Blocks.BIRCH_LEAVES)) {
				spawnspot = spawnspot.above(rand.nextInt(3));
			}else {
				foundspot = true;
			}
		}
		level.setBlock(spawnspot, Registration.DEADLEAVES.get().defaultBlockState(), 3);
	}
	public static void handleCreeperSpawns(Level level) {
		for (int i2 = 0; i2 < ActiveCreeperCocoons.size(); i2++) {
			if(rand.nextInt(5)==0) {
				boolean quedForRemoval = players.size()>0;
				boolean spawned = false;
				int i = EventHandler.players.size()-1;
				if(i < 0)return;
				else if (i != 0) {
					i-=rand.nextInt(EventHandler.players.size());
				}
				Player player = players.get(i);
				BlockPos nest = ActiveCreeperCocoons.get(i2);
				if(level.getBlockState(nest).getBlock() != Registration.CREEPERCOCOON_UPPER.get()) {
					quedForRemoval = false;
					ActiveCreeperCocoons.remove(i2);
					i2--;
				}
				else{
					if(isWithinDistance(nest, player.blockPosition(), 10) && level.getBlockState(nest).getValue(CreeperCocoonUpper.FULLSIZE) && level.getBlockState(nest).getValue(CreeperCocoonUpper.MATURITY) >= 3) {
						System.out.println("CREEPER");
						Creeper creeper = new Creeper(EntityType.CREEPER, level);
						level.addFreshEntity(creeper);
						BlockPos spawnspot = nest.below().north();
						creeper.setPos(spawnspot.getX(), spawnspot.getY(),spawnspot.getZ());
						creeper.setTarget(player);
						creeper.setAggressive(true);
						creeper.setSilent(true);
						creeper.getAttribute(Attributes.FOLLOW_RANGE).addTransientModifier(new AttributeModifier("Provoked", 100, Operation.ADDITION));
						creeper.getAttribute(Attributes.KNOCKBACK_RESISTANCE).addTransientModifier(new AttributeModifier("Provoked", 100, Operation.ADDITION));
						creeper.getAttribute(Attributes.ARMOR).addTransientModifier(new AttributeModifier("Provoked", 20, Operation.ADDITION));
						creeper.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(new AttributeModifier("Provoked", 0.2f, Operation.MULTIPLY_BASE));
						level.setBlock(nest, level.getBlockState(nest).setValue(CreeperCocoonUpper.LEFT, true), 3);
						level.setBlock(nest.below(), level.getBlockState(nest.below()).setValue(CreeperCocoonLower.VISUAL, 1), 3);
						spawned = true;
						quedForRemoval = false;
						ActiveCreeperCocoons.remove(i2);
						i2--;
					}
				}if(isWithinDistance(nest, player.blockPosition(), 75)) {
					quedForRemoval = false;
				}
				if(quedForRemoval) {
					ActiveCreeperCocoons.remove(i2);
					i2--;
				}
			}
		}
	}
	static void handleCocoonGrowth(Level level) {
		for (int i = 0; i < ActiveCreeperCocoons.size(); i++) {
			if(level.getBlockState(ActiveCreeperCocoons.get(i)).getBlock() == Registration.CREEPERCOCOON_UPPER.get()) {
				if(rand.nextInt(10) == 0) {
					BlockPos cocoon = ActiveCreeperCocoons.get(i);
					if(level.getBlockState(cocoon).is(Registration.CREEPERCOCOON_UPPER.get())) {
						if(level.getBlockState(cocoon).getValue(CreeperCocoonUpper.MATURITY) >= 3
								&& level.getBlockState(cocoon).getValue(CreeperCocoonUpper.MATURITY) < 5
								&& !level.getBlockState(cocoon).getValue(CreeperCocoonUpper.FULLSIZE) 
								&& !level.getBlockState(cocoon).getValue(CreeperCocoonUpper.LEFT)
								&& rand.nextBoolean()) {
							level.setBlock(cocoon.below(), Registration.CREEPERCOCOON_LOWER.get().defaultBlockState(), 3);
							level.setBlock(cocoon, level.getBlockState(cocoon)
									.setValue(CreeperCocoonUpper.LEFT, false)
									.setValue(CreeperCocoonUpper.FULLSIZE, true), 3);
						}
						else if(level.getBlockState(cocoon).getValue(CreeperCocoonUpper.MATURITY) < 5 && rand.nextInt(10) == 0) {
							
							level.setBlock(cocoon, level.getBlockState(cocoon).setValue(CreeperCocoonUpper.MATURITY, level.getBlockState(cocoon).getValue(CreeperCocoonUpper.MATURITY)+1), 3);
							if(level.getBlockState(cocoon).getValue(CreeperCocoonUpper.MATURITY) == 5) {
								if(level.getBlockState(cocoon).getValue(CreeperCocoonUpper.FULLSIZE))level.setBlock(cocoon.below(), Registration.CREEPERCOCOON_LOWER.get().defaultBlockState().setValue(CreeperCocoonLower.VISUAL, 2), 3);
								level.setBlock(cocoon.below(), level.getBlockState(cocoon).setValue(CreeperCocoonLower.VISUAL, 2), 3);
							}
						}
					}
				}
			}
			else {
				ActiveCreeperCocoons.remove(i);
			}
		}
	}
	static void GrowCocoon(BlockPos cocoon, Level level) {
		BlockPos spawnspot = cocoon;
		boolean foundspot = false;
		int i3 = 0;
		while(!foundspot && i3 < 20) {
			i3++;
			if(!level.getBlockState(spawnspot).is(Blocks.JUNGLE_LEAVES) && !level.getBlockState(spawnspot).is(Blocks.OAK_LEAVES)) {
				spawnspot = spawnspot.below();
			}if(!level.getBlockState(spawnspot).is(Blocks.JUNGLE_LEAVES) && !level.getBlockState(spawnspot).is(Blocks.OAK_LEAVES)) {
				spawnspot = spawnspot.east(rand.nextInt(3) - 1);
			}if(!level.getBlockState(spawnspot).is(Blocks.JUNGLE_LEAVES) && !level.getBlockState(spawnspot).is(Blocks.OAK_LEAVES)) {
				spawnspot = spawnspot.south(rand.nextInt(3) - 1);
			}if(!level.getBlockState(spawnspot).is(Blocks.JUNGLE_LEAVES) && !level.getBlockState(spawnspot).is(Blocks.OAK_LEAVES)) {
				spawnspot = spawnspot.north(rand.nextInt(3) - 1);
			}if(!level.getBlockState(spawnspot).is(Blocks.JUNGLE_LEAVES) && !level.getBlockState(spawnspot).is(Blocks.OAK_LEAVES)) {
				spawnspot = spawnspot.west(rand.nextInt(3) - 1);
			}if(!level.getBlockState(spawnspot).is(Blocks.JUNGLE_LEAVES) && !level.getBlockState(spawnspot).is(Blocks.OAK_LEAVES)) {
				spawnspot = spawnspot.above(rand.nextInt(3));
			}else {
				if(!level.getBlockState(spawnspot).is(Blocks.AIR)) {
					spawnspot = spawnspot.below();
				}
				else {
					foundspot = true;
				}
			}
		}
		level.setBlock(cocoon, Registration.CREEPERCOCOON_UPPER.get().defaultBlockState() , 3);
		if(foundspot) {
			level.setBlock(spawnspot, Registration.CREEPERCOCOON_UPPER.get().defaultBlockState().setValue(CreeperCocoonUpper.GROWTH_ABILITY, Math.floorDiv(level.getBlockState(cocoon).getValue(SpiderNest.GROWTH_ABILITY),2)), 3);
			System.out.println("CREEPER COCOON CREATED");
		}
	}
	
	@SubscribeEvent
	static void OnWorldTick(WorldTickEvent e){
		Level level = e.world;
		if(level.dimension().equals(Level.OVERWORLD)) {
			switch (ticking){
				case 0:	handleCaveInRubble(level);
						break;
				case 10: handleSpiderSpawns(level);
						break;
				case 20: handleNestGrowth(level);
						break;
				case 30: handleCreeperSpawns(level);
						break;
				case 40: handleCocoonGrowth(level);
						break;
				default: if(ticking%5 == 0)handleFallingRubble(level);
						break;
			}
			ticking ++;
			if(ticking >= 50)ticking = 0;
		}
	}
	@SubscribeEvent
	static void OnBreak(BreakEvent e){
		if(e.getState() != Registration.RUBBLE.get().defaultBlockState() && rand.nextBoolean())TremorPos.add(e.getPos());
	}
	
	@SubscribeEvent
	static void OnAttack(AttackEntityEvent e){
		if(e.getPlayer().getMainHandItem().getItem() == Registration.SPINE.get() && rand.nextBoolean()) e.getEntity().spawnAtLocation(new ItemStack(Items.BONE, 1));
	}
	

	@SubscribeEvent
	static void OnDeath(LivingDeathEvent event){
	    Entity e = event.getEntity();
		if(e instanceof Player || e instanceof Villager || e instanceof AbstractIllager || e instanceof Skeleton || e instanceof Zombie || e instanceof ZombieVillager || e instanceof Creeper) {
			if(e instanceof Player || e instanceof Villager || e instanceof AbstractIllager) {
				System.out.println("HUMAN FLESH");
				e.spawnAtLocation(new ItemStack(Registration.HUMANFLESH.get(), 5 + rand.nextInt(6)));
				if(e instanceof Villager || e instanceof AbstractIllager && rand.nextBoolean()) {
					e.spawnAtLocation(new ItemStack(Registration.CIGAR.get(), rand.nextInt(8)+1));	
				}
			}
			if(e instanceof Skeleton) {
				e.spawnAtLocation(new ItemStack(Registration.SPINE.get()));	
			}
		}
		
	}

	@SubscribeEvent(priority=EventPriority.HIGH)
	public static void onBiomeLoading(BiomeLoadingEvent evt)
	{
		BlockGenerator.generate(evt);
	}
	
	@SubscribeEvent
	public static void onPlayerLogIn(PlayerLoggedInEvent e)
	{
		if(!players.contains(e.getPlayer()))players.add(e.getPlayer());
	}
	@SubscribeEvent
	public static void onPlayerLogOut(PlayerLoggedOutEvent e)
	{
		if(players.contains(e.getPlayer()))players.remove(e.getPlayer());
	}
	
	
	public static boolean isWithinDistance(BlockPos a, BlockPos b, int d) {
		int x = Math.abs(a.getX()) - Math.abs(b.getX());
		int y = Math.abs(a.getY()) - Math.abs(b.getY());
		int z = Math.abs(a.getZ()) - Math.abs(b.getZ());
		
		int d2 = x*x + y*y + z*z;
		return d*d > d2;
	}
	
	public static boolean isWithinFlatDistance(BlockPos a, BlockPos b, int d) {
		int x = Math.abs(a.getX()) - Math.abs(b.getX());
		int y = Math.abs(a.getY()) - Math.abs(b.getY());
		
		int d2 = x*x + y*y;
		return d*d > d2;
	}
}
