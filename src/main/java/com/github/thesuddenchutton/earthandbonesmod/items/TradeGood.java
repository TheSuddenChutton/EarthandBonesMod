package com.github.thesuddenchutton.earthandbonesmod.items;

import net.minecraft.world.item.Rarity;

public class TradeGood extends BasicItem{

	public TradeGood(Properties p) {
		super(p.stacksTo(16).rarity(Rarity.RARE));
	}

}
