package com.github.thesuddenchutton.earthandbonesmod.handlers;

import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.ArrayList;
import java.util.Random;

import com.github.thesuddenchutton.earthandbonesmod.setup.Registration;
import com.github.thesuddenchutton.earthandbonesmod.world.gen.Ores;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;


@EventBusSubscriber
public class EventHandler {

	public static Random rand = new Random();
	public static ArrayList<BlockPos> TremorPos = new ArrayList<BlockPos>();
	public static ServerLevel level;

	
	
	@SubscribeEvent
	static void OnTick(TickEvent e){
		if(rand.nextInt(Math.floorDiv(5000, TremorPos.size()+1)) == 0 && level != null && TremorPos.size() > 0) {
			BlockPos pos = TremorPos.get(0).above(rand.nextInt(3)).below(rand.nextInt(3)).north(rand.nextInt(3)).south(rand.nextInt(3)).east(rand.nextInt(3)).west(rand.nextInt(3));
			if(level.getBlockState(pos).getBlock() == Blocks.DIRT || level.getBlockState(pos).getBlock() == Blocks.STONE) level.setBlock(pos, Registration.RUBBLE.get().defaultBlockState(), 3);
			if(TremorPos.size() > 0)TremorPos.remove(0);
			System.out.println("TREMOR");
		}
	}
	
	
	@SubscribeEvent
	static void OnBreak(BreakEvent e){
		if((e.getState() != Registration.RUBBLE.get().defaultBlockState() && rand.nextBoolean()) || rand.nextInt(3) == 0)TremorPos.add(e.getPos());
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
		Ores.generateOres(evt);
	}
	
}
