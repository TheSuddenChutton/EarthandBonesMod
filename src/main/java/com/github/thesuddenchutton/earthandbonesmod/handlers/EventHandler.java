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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SpawnerBlock;
import net.minecraft.world.level.entity.ChunkEntities;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;


@EventBusSubscriber
public class EventHandler {

	public static Random rand = new Random();
	public static ArrayList<BlockPos> TremorPos = new ArrayList<BlockPos>();
	public static ServerLevel level;
	public static boolean ticking = false;
	public static ArrayList<BlockPos> spiderNests = new ArrayList<BlockPos>();
	public static ArrayList<Player> players = new ArrayList<Player>();

	
	
	@SubscribeEvent
	static void OnTick(TickEvent e){
		if(!ticking) {
			ticking = true;
			if(rand.nextInt(Math.floorDiv(5000, TremorPos.size()+1)) == 0 && level != null && TremorPos.size() > 0 && level != null && ticking == false) {
				BlockPos pos = TremorPos.get(0).above(rand.nextInt(3)).below(rand.nextInt(3)).north(rand.nextInt(3)).south(rand.nextInt(3)).east(rand.nextInt(3)).west(rand.nextInt(3));
				if((level.getBlockState(pos).getBlock() == Blocks.DIRT || level.getBlockState(pos).getBlock() == Blocks.STONE) && level.isLoaded(pos)) 
				{
					level.setBlock(pos, Registration.RUBBLE.get().defaultBlockState(), 0);
				}
				if(TremorPos.size() > 0)TremorPos.remove(0);
			}

			if(level != null) {
				for (int i = 0; i < players.size(); i++) {
					
					for (int i2 = 0; i2 < spiderNests.size(); i2++) {
						Player player = players.get(i);
						BlockPos nest = spiderNests.get(i2);
						if(level.getBlockState(nest).getBlock() != Registration.SPIDERNEST.get()) {
							spiderNests.remove(i2);
						}
						if(rand.nextInt(300) == 0){
							if(level.getEntities(null, new AABB(nest.below(50).north(3).east(3), nest.south(3).west(3))).contains(player) && level.getBlockState(nest).getValue(SpiderNest.SPIDERS) > 0) {
								System.out.println("SPIDERS");
								Entity spider = new Spider(EntityType.SPIDER, level);
								level.addFreshEntity(spider);
								spider.setPos(nest.below().getX(),nest.below().getY(),nest.below().getZ());
								level.setBlock(nest, level.getBlockState(nest).setValue(SpiderNest.SPIDERS, level.getBlockState(nest).getValue(SpiderNest.SPIDERS)-1), 3);
							}
						}
					}
				}
			}
			ticking=false;
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
	
	
	
}
