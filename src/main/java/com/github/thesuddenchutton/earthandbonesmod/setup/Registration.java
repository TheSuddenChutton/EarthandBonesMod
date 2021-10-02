package com.github.thesuddenchutton.earthandbonesmod.setup;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.github.thesuddenchutton.earthandbonesmod.EarthandBonesMod.MODID;

import com.github.thesuddenchutton.earthandbonesmod.blocks.Rubble;
import com.github.thesuddenchutton.earthandbonesmod.blocks.SpikeTrap;
import com.github.thesuddenchutton.earthandbonesmod.blocks.Spikes;
import com.github.thesuddenchutton.earthandbonesmod.items.HumanFlesh;
import com.github.thesuddenchutton.earthandbonesmod.items.Spine;

public class Registration {
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
	
	public static void init() {
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		
	}
	public static final RegistryObject<HumanFlesh> HUMANFLESH = ITEMS.register("humanflesh", () -> new HumanFlesh(new Item.Properties().food(new FoodProperties.Builder().meat().alwaysEat().nutrition(4).saturationMod(1).build()).tab(CreativeModeTab.TAB_FOOD)));
	public static final RegistryObject<Spine> SPINE = ITEMS.register("spine", () -> new Spine());

	
	public static final RegistryObject<Rubble> RUBBLE = BLOCKS.register("rubble", Rubble::new);
	public static final RegistryObject<Item> RUBBLE_ITEM = ITEMS.register("rubble", () -> new BlockItem(RUBBLE.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final RegistryObject<Spikes> SPIKES = BLOCKS.register("spikes", Spikes::new);
	public static final RegistryObject<Item> SPIKES_ITEM = ITEMS.register("spikes", () -> new BlockItem(SPIKES.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<SpikeTrap> SPIKETRAP = BLOCKS.register("spiketrap", SpikeTrap::new);
	public static final RegistryObject<Item> SPIKETRAP_ITEM = ITEMS.register("spiketrap", () -> new BlockItem(SPIKETRAP.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
}