package com.github.thesuddenchutton.earthandbonesmod.items;

import java.util.List;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnchantedGoldenAppleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class Cigar extends TradeGood{

	public Cigar(Properties p) {
		super(p);
	}

	@Override
	public void appendHoverText(ItemStack p_41421_, Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
		super.appendHoverText(p_41421_, p_41422_, list, p_41424_);
		list.add(new TranslatableComponent("message.cigar"));
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
		p_41433_.startUsingItem(p_41434_);
		return super.use(p_41432_, p_41433_, p_41434_);
	}
	
	@Override
	public SoundEvent getEatingSound() {
		// TODO Auto-generated method stub
		return SoundEvents.WOOL_HIT;
	}
	
	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity e) {
		if(e.getAttribute(Attributes.MOVEMENT_SPEED).getValue() < e.getAttribute(Attributes.MOVEMENT_SPEED).getBaseValue()*2)e.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(new AttributeModifier("Cigar", 1.1, AttributeModifier.Operation.MULTIPLY_TOTAL));
		if(e.getAttribute(Attributes.KNOCKBACK_RESISTANCE).getValue() < e.getAttribute(Attributes.KNOCKBACK_RESISTANCE).getBaseValue()*10)e.getAttribute(Attributes.KNOCKBACK_RESISTANCE).addTransientModifier(new AttributeModifier("Cigar", 1.2, AttributeModifier.Operation.MULTIPLY_TOTAL));
		if(e.getAttribute(Attributes.MAX_HEALTH).getValue() > 2)e.getAttribute(Attributes.MAX_HEALTH).addTransientModifier(new AttributeModifier("Cigar", -1, AttributeModifier.Operation.ADDITION));
		System.out.println(e.getName().getContents() + " SMOKED A CIGAR AND LOOKED COOL DOING IT");
		return super.finishUsingItem(stack, level, e);
	}
}
