package com.github.thesuddenchutton.earthandbonesmod.blocks;

import java.util.ArrayList;
import java.util.List;
import com.github.thesuddenchutton.earthandbonesmod.setup.Registration;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext.Builder;

public class ArmorOre extends Block {
	
	public ArmorOre () {
		super(Properties.of(Material.METAL).sound(SoundType.METAL).strength(7.0f).requiresCorrectToolForDrops());
	}

}
