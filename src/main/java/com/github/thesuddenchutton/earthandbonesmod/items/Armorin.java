package com.github.thesuddenchutton.earthandbonesmod.items;

import java.util.List;
import java.util.Random;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class Armorin extends BasicItem{

	Random rand = new Random();
	public Armorin(Properties p) {
		super(p);
	}

	@Override
	public void appendHoverText(ItemStack p_41421_, Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
		super.appendHoverText(p_41421_, p_41422_, list, p_41424_);
		list.add(new TranslatableComponent("message.armorin"));
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
		p_41433_.startUsingItem(p_41434_);
		return super.use(p_41432_, p_41433_, p_41434_);
	}
	
	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity e) {
		e.getAttribute(Attributes.ARMOR).addTransientModifier(new AttributeModifier("Armorin", rand.nextInt(10), AttributeModifier.Operation.ADDITION));
		System.out.println(e.getName().getContents() + " ATE ARMORIN TO GAIN IT'S DEFENSIVE POWER");
		return super.finishUsingItem(stack, level, e);
	}
}
