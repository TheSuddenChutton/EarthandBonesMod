package com.github.thesuddenchutton.earthandbonesmod.handlers;

import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.ArrayList;
import java.util.Random;

import com.github.thesuddenchutton.earthandbonesmod.blocks.SpiderNest;
import com.github.thesuddenchutton.earthandbonesmod.setup.Registration;
import com.github.thesuddenchutton.earthandbonesmod.world.gen.BlockGenerator;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.WorldTickEvent;

import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;


@EventBusSubscriber
public class EventHandler {

	public static Random rand = new Random();
	public static int ticking = 0;
	public static int ticking2 = 0;
	public static ArrayList<BlockPos> TremorPos = new ArrayList<BlockPos>();
	public static ArrayList<BlockPos> spiderNests = new ArrayList<BlockPos>();
	public static ArrayList<BlockPos> ActiveRubble = new ArrayList<BlockPos>();
	static int nestnum = 0, rubblenum = 0;
	static boolean ticknow = false;
	public static ArrayList<Player> players = new ArrayList<Player>();
	
	
	
	@SubscribeEvent
	static void OnWorldTick(WorldTickEvent e){
		Level level = e.world;
		ticknow = !ticknow;
		if(level.dimension().equals(Level.OVERWORLD) && ticknow) {
			if(ticking == ticking2) {
				ticking ++;
				if(ticking > 100) {
					ticking -= 100;
					ticking2 -= 100;
				}
				if(ticking > ticking2+1) {
					System.out.println("Cought a doubletick");
				}
				else {
					if(rand.nextInt(10) == 0 && level != null && TremorPos.size() > 0 && level != null) {
						BlockPos pos = TremorPos.get(0).above(rand.nextInt(3)).below(rand.nextInt(3)).north(rand.nextInt(3)).south(rand.nextInt(3)).east(rand.nextInt(3)).west(rand.nextInt(3));
						if((level.getBlockState(pos).getBlock() == Blocks.DIRT || level.getBlockState(pos).getBlock() == Blocks.STONE) && level.isLoaded(pos)) 
						{
							level.setBlock(pos, Registration.RUBBLE.get().defaultBlockState(), 0);
						}
						if(TremorPos.size() > 0)TremorPos.remove(0);
					}
		
					if(level != null) {
						for (int i = 0; i < players.size(); i++) {
							
							for (int i2 = 0; i2 < spiderNests.size() && i2 < 5; i2++) {
								Player player = players.get(i);
								nestnum++;
								if(nestnum >= spiderNests.size())nestnum = 0;
								BlockPos nest = spiderNests.get(nestnum);
								if(level.getBlockState(nest).getBlock() != Registration.SPIDERNEST.get()) {
									spiderNests.remove(nestnum);
									nestnum--;
								}
								else if(rand.nextInt(10) == 0){
									if(isWithinDistance(nest, player.blockPosition(), 10) && level.getBlockState(nest).getValue(SpiderNest.SPIDERS) > 0) {
										System.out.println("SPIDERS");
										Entity spider = new Spider(EntityType.SPIDER, level);
										level.addFreshEntity(spider);
										spider.setPos(nest.below().getX(),nest.below().getY(),nest.below().getZ());
										level.setBlock(nest, level.getBlockState(nest).setValue(SpiderNest.SPIDERS, level.getBlockState(nest).getValue(SpiderNest.SPIDERS)-1), 3);
									}
								}
							}
						}
						for (int i2 = 0; i2 < 10; i2++) {
							rubblenum++;
							for (int i = 0; i < players.size(); i++) {
								if(ActiveRubble.size() > 0) {
									Player player = players.get(i);
									if(rubblenum >= ActiveRubble.size())rubblenum = 0;
									BlockPos rubble = ActiveRubble.get(rubblenum);
									
									if(level.getBlockState(rubble).getBlock() != Registration.RUBBLE.get()) {
										ActiveRubble.remove(rubblenum);
										rubblenum--;
									}
									else {
										if(isWithinDistance(rubble, player.blockPosition(), 25)) {
											System.out.println("rubble " + rubblenum + " is near the player, updating...");
											UpdateRubble(rubble, level);
										}
										if(!isWithinDistance(rubble, player.blockPosition(), 50)) {
											System.out.println("rubble " + rubblenum + " is far from the player, removing...");
											ActiveRubble.remove(rubblenum);
											rubblenum--;
										}
									}
								}
							}
						}
					}
				}
				ticking2++;
			}
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

	@SubscribeEvent(priority= EventPriority.HIGH)
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
