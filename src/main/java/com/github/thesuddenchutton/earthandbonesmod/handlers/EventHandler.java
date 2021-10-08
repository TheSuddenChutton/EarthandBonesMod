package com.github.thesuddenchutton.earthandbonesmod.handlers;

import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.ArrayList;
import java.util.Random;

import com.github.thesuddenchutton.earthandbonesmod.blocks.SpiderNest;
import com.github.thesuddenchutton.earthandbonesmod.setup.Registration;
import com.github.thesuddenchutton.earthandbonesmod.sounds.MenuMusic;
import com.github.thesuddenchutton.earthandbonesmod.world.gen.BlockGenerator;

import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.AbstractIllager;
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
import net.minecraft.world.level.block.SpawnerBlock;
import net.minecraft.world.level.entity.ChunkEntities;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
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
	public static ArrayList<BlockPos> ActiveRubble = new ArrayList<BlockPos>();
	static int nestnum = 0, rubblenum = 0;
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
			
	static void handleSpiderSpawns(Level level) {
		for (int i2 = 0; i2 < ActiveSpiderNests.size(); i2++) {
			if(rand.nextInt(25)==0) {
				boolean quedForRemoval = true;
				for (int i = 0; i < players.size(); i++) {
					Player player = players.get(i);
					nestnum++;
					if(nestnum >= ActiveSpiderNests.size())nestnum = 0;
					BlockPos nest = ActiveSpiderNests.get(nestnum);
					if(level.getBlockState(nest).getBlock() != Registration.SPIDERNEST.get()) {
						quedForRemoval = false;
						ActiveSpiderNests.remove(nestnum);
						nestnum--;
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
							level.setBlock(nest, level.getBlockState(nest).setValue(SpiderNest.SPIDERS, level.getBlockState(nest).getValue(SpiderNest.SPIDERS)-1), 3);
						}
						else growSpiderNest(nest, level);
					}if(isWithinDistance(nest, player.blockPosition(), 50)) {
						quedForRemoval = false;
					}
				}
				if(quedForRemoval) {
					ActiveSpiderNests.remove(nestnum);
					nestnum--;
				}
			}
		}
	}
	
	static void handleNestGrowth(Level level) {
		for (int i = 0; i < ActiveSpiderNests.size(); i++) {
			if(level.getBlockState(ActiveSpiderNests.get(i)).getBlock() == Registration.SPIDERNEST.get()) {
				if(rand.nextInt(750) == 0) {
					growSpiderNest(ActiveSpiderNests.get(i), level); 
				}
			}
			else {
				ActiveSpiderNests.remove(i);
			}
		}
	}

		
	
	static void growSpiderNest(BlockPos nest, Level level) {
		switch (rand.nextInt(3)){
			case 0:	if(level.getBlockState(nest).getValue(SpiderNest.SPIDERS) == 3 && level.getBlockState(nest).getValue(SpiderNest.GROWTH_ABILITY) >= 3)addNewNest(nest, level);
					else if(level.getBlockState(nest).getValue(SpiderNest.SPIDERS) < 3) level.setBlock(nest, level.getBlockState(nest).setValue(SpiderNest.SPIDERS, level.getBlockState(nest).getValue(SpiderNest.SPIDERS)+1), 3);
				break;
			case 1: int r = rand.nextInt(2)+2;
				for(int i = 0; i < r; i++) {
					if(level.getBlockState(nest).getValue(SpiderNest.GROWTH_ABILITY) > i)growWebs(nest, level);
				}
				break;
			case 2: 
				int r2 = rand.nextInt(5);
				for(int i = 0; i < r2; i++) {
					if(level.getBlockState(nest).getValue(SpiderNest.GROWTH_ABILITY) > 0)killLeaves(nest, level);
				}
				break;
		}
	}
	static void addNewNest(BlockPos nest, Level level) {
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
				default: if(ticking%3 == 0)handleFallingRubble(level);
						break;
			}
			ticking ++;
			if(ticking >= 30)ticking = 0;
		}
	}
	@SubscribeEvent
	static void PlaySound(PlaySoundEvent e) {
		System.out.println(e.getName());
		if(e.getName() == "music.menu") {
			e.setResultSound(new MenuMusic());
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
	static void OnDeath(LivingDeathEvent e){
		if(e.getEntity() instanceof Player || e.getEntity() instanceof Villager || e.getEntity() instanceof AbstractIllager) {
			System.out.println("HUMAN FLESH");
			e.getEntity().spawnAtLocation(new ItemStack(Registration.HUMANFLESH.get(), 10));
		}
		if(e.getEntity() instanceof Player || e.getEntity() instanceof Villager || e.getEntity() instanceof AbstractIllager || e.getEntity() instanceof Skeleton || e.getEntity() instanceof Zombie || e.getEntity() instanceof ZombieVillager) {
			if(rand.nextInt(10) == 0)e.getEntity().spawnAtLocation(new ItemStack(Registration.SPINE.get(), 1));
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
	public static void UpdateRubble(BlockPos rubble, Level level){
		if(level!=null) {
			if(!level.isWaterAt(rubble.below()) && !level.isEmptyBlock(rubble.below())) {
				if(level.isEmptyBlock(rubble.below().east()) && rand.nextInt(5) == 0) {
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
	
}
