package com.github.thesuddenchutton.earthandbonesmod.items;

import java.util.List;

import com.github.thesuddenchutton.earthandbonesmod.setup.Registration;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class Spine extends SwordItem{

	public Spine() {
		super(Tiers.STONE, 10, 0.1f, new Properties().durability(20).tab(CreativeModeTab.TAB_TOOLS));
		// TODO Auto-generated constructor stub
	}
	@Override
	public void appendHoverText(ItemStack p_41421_, Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
		super.appendHoverText(p_41421_, p_41422_, list, p_41424_);
		list.add(new TranslatableComponent("message.spine"));
	}
}
