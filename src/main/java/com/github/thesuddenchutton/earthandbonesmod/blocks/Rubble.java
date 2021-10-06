package com.github.thesuddenchutton.earthandbonesmod.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.thesuddenchutton.earthandbonesmod.handlers.EventHandler;
import com.github.thesuddenchutton.earthandbonesmod.setup.Registration;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
import net.minecraftforge.common.IPlantable;

public class Rubble extends FallingBlock {

	public static ServerLevel serverlevel;
	
	public Rubble () {
		super(Properties.of(Material.SAND).sound(SoundType.GRAVEL).destroyTime(1).strength(1.0f, 0.5f).requiresCorrectToolForDrops());
	}
	@Override
	public List<ItemStack> getDrops(BlockState p_60537_, Builder p_60538_) {
		ArrayList<ItemStack> l = new ArrayList<ItemStack>();
		l.add(new ItemStack(Registration.RUBBLE_ITEM.get()));
		return l;
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
	public void onPlace(BlockState p_53233_, Level p_53234_, BlockPos p_53235_, BlockState p_53236_, boolean p_53237_) {
		if(!EventHandler.ActiveRubble.contains(p_53235_))EventHandler.ActiveRubble.add(p_53235_);
		super.onPlace(p_53233_, p_53234_, p_53235_, p_53236_, p_53237_);
	}
	@Override
	public void tick(BlockState p_53216_, ServerLevel p_53217_, BlockPos p_53218_, Random p_53219_) {
		for (int i = 0; i < EventHandler.players.size(); i++) {
			if(!EventHandler.ActiveRubble.contains(p_53218_) && EventHandler.isWithinDistance(p_53218_, EventHandler.players.get(i).blockPosition(), 50))
				{
					System.out.println("Added random rubble");
					EventHandler.ActiveRubble.add(p_53218_);
				}
			
		}
	}
}
