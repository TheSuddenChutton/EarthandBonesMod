package com.github.thesuddenchutton.earthandbonesmod.items;

import java.util.List;

import com.github.thesuddenchutton.earthandbonesmod.setup.Registration;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
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

	public AttributeModifier charge = new AttributeModifier("whip_charge", 0.9f, Operation.MULTIPLY_TOTAL);
	public Spine() {
		super(Tiers.STONE, 2, 0.1f, new Properties().durability(20).tab(CreativeModeTab.TAB_TOOLS));
		// TODO Auto-generated constructor stub
	}
	@Override
	public void appendHoverText(ItemStack p_41421_, Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
		super.appendHoverText(p_41421_, p_41422_, list, p_41424_);
		list.add(new TranslatableComponent("message.spine"));
	}
	@Override
	public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
		super.onUsingTick(stack, player, count);
	}
	@Override
	public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
		
		player.sweepAttack();
		if(player instanceof Player)((Player)player).getAttributes().getInstance(Attributes.ATTACK_DAMAGE).addTransientModifier(new AttributeModifier("whip_charge", 1.01f, Operation.MULTIPLY_TOTAL));
		return super.onLeftClickEntity(stack, player, entity);
	}
	@Override
	public void inventoryTick(ItemStack p_41404_, Level p_41405_, Entity player, int p_41407_, boolean p_41408_) {
		if(player instanceof Player)
		{
			Player p = ((Player)player);
			if(p.getMainHandItem() == p_41404_) {
				if(p.getAttributes().getInstance(Attributes.ATTACK_DAMAGE).getValue() > p.getAttributes().getInstance(Attributes.ATTACK_DAMAGE).getBaseValue())p.getAttributes().getInstance(Attributes.ATTACK_DAMAGE).addTransientModifier((new AttributeModifier("whip_charge", 0.9f, Operation.MULTIPLY_TOTAL)));
			}
			else if (p.getAttributes().hasModifier(Attributes.ATTACK_DAMAGE, charge.getId())){
				if(p.getAttributes().getInstance(Attributes.ATTACK_DAMAGE).getValue() > p.getAttributes().getInstance(Attributes.ATTACK_DAMAGE).getBaseValue())p.getAttributes().getInstance(Attributes.ATTACK_DAMAGE).removeModifier(charge);					
			}
		}
	}
}
