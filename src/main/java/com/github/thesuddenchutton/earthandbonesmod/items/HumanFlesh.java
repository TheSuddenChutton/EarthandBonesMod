package com.github.thesuddenchutton.earthandbonesmod.items;

import java.util.List;
import java.util.Random;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
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

public class HumanFlesh extends BasicItem{
	public static boolean willHurt;
	public static Random rand;
	
	public HumanFlesh(Properties p) {
		super(p);
	}

	@Override
	public void appendHoverText(ItemStack p_41421_, Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
		super.appendHoverText(p_41421_, p_41422_, list, p_41424_);
		if(!willHurt) list.add(new TranslatableComponent("message.humanflesh"));
		else list.add(new TranslatableComponent("message.humanflesh2"));
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
		p_41433_.startUsingItem(p_41434_);
		return super.use(p_41432_, p_41433_, p_41434_);
	}
	@Override
	public void inventoryTick(ItemStack p_41404_, Level p_41405_, Entity p_41406_, int p_41407_, boolean p_41408_) {
		if(rand.nextInt(100)==0)willHurt = !willHurt;
		super.inventoryTick(p_41404_, p_41405_, p_41406_, p_41407_, p_41408_);
	}
	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity e) {
		e.getAttribute(Attributes.MAX_HEALTH).addTransientModifier(new AttributeModifier("humanflesh", 2, AttributeModifier.Operation.ADDITION));
		if(willHurt) e.hurt(new DamageSource("humanflesh"), 5);
		System.out.println(e.getName().getContents() + " ATE HUMAN FLESH TO GAIN IT'S HUMAN POWER");
		return super.finishUsingItem(stack, level, e);
	}
}