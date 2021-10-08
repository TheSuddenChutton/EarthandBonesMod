package com.github.thesuddenchutton.earthandbonesmod.setup;

import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.github.thesuddenchutton.earthandbonesmod.EarthandBonesMod.MODID;

import java.util.ArrayList;
import java.util.Arrays;

import com.github.thesuddenchutton.earthandbonesmod.blocks.ArmorOre;
import com.github.thesuddenchutton.earthandbonesmod.blocks.DeadLeaves;
import com.github.thesuddenchutton.earthandbonesmod.blocks.FierceOre;
import com.github.thesuddenchutton.earthandbonesmod.blocks.HealthyOre;
import com.github.thesuddenchutton.earthandbonesmod.blocks.Rubble;
import com.github.thesuddenchutton.earthandbonesmod.blocks.SpiderNest;
import com.github.thesuddenchutton.earthandbonesmod.blocks.SpikeTrap;
import com.github.thesuddenchutton.earthandbonesmod.blocks.Spikes;
import com.github.thesuddenchutton.earthandbonesmod.items.Armorin;
import com.github.thesuddenchutton.earthandbonesmod.items.Fierceion;
import com.github.thesuddenchutton.earthandbonesmod.items.Healthium;
import com.github.thesuddenchutton.earthandbonesmod.items.HumanFlesh;
import com.github.thesuddenchutton.earthandbonesmod.items.Spine;
import com.github.thesuddenchutton.earthandbonesmod.world.gen.features.CeilTrap;
import com.github.thesuddenchutton.earthandbonesmod.world.gen.features.FloorTrap;

public class Registration {
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
	private static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, MODID);
	private static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MODID);
	
	public static void init() {
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		FEATURES.register(FMLJavaModLoadingContext.get().getModEventBus());
		SOUNDS.register(FMLJavaModLoadingContext.get().getModEventBus());
			
	}
	public static final RegistryObject<SoundEvent> MENUMUSIC = SOUNDS.register("menumusic", () -> new SoundEvent(new ResourceLocation(MODID, "sounds/music/menumusic")));
	
	
	public static final RegistryObject<FloorTrap> FloorTrapUnderground = FEATURES.register("floortrap", () -> new FloorTrap(OreConfiguration.CODEC,true));
	public static final RegistryObject<CeilTrap> SpiderNestDO = FEATURES.register("spidernestdo", () -> new CeilTrap(OreConfiguration.CODEC, new ArrayList<Block>(Arrays.asList(Blocks.DARK_OAK_LEAVES, Registration.SPIDERNEST.get()))));
	public static final RegistryObject<CeilTrap> SpiderNestJU = FEATURES.register("spidernestju", () -> new CeilTrap(OreConfiguration.CODEC, new ArrayList<Block>(Arrays.asList(Blocks.JUNGLE_LEAVES, Registration.SPIDERNEST.get()))));
	public static final RegistryObject<CeilTrap> SpiderNestOAK = FEATURES.register("spidernestoak", () -> new CeilTrap(OreConfiguration.CODEC, new ArrayList<Block>(Arrays.asList(Blocks.OAK_LEAVES))));
	public static final RegistryObject<CeilTrap> SpiderNestBI = FEATURES.register("spidernestbi", () -> new CeilTrap(OreConfiguration.CODEC, new ArrayList<Block>(Arrays.asList(Blocks.BIRCH_LEAVES))));

	
	public static final RegistryObject<HumanFlesh> HUMANFLESH = ITEMS.register("humanflesh", () -> new HumanFlesh(new Item.Properties().food(new FoodProperties.Builder().meat().alwaysEat().nutrition(4).saturationMod(1).build()).tab(CreativeModeTab.TAB_FOOD)));
	public static final RegistryObject<Healthium> HEALTHIUM = ITEMS.register("healthium", () -> new Healthium(new Item.Properties().food(new FoodProperties.Builder().alwaysEat().nutrition(0).saturationMod(10).build()).tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<Armorin> ARMORIN = ITEMS.register("armorin", () -> new Armorin(new Item.Properties().food(new FoodProperties.Builder().alwaysEat().nutrition(0).saturationMod(0).build()).tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<Fierceion> FIERCEION = ITEMS.register("fiercion", () -> new Fierceion(new Item.Properties().food(new FoodProperties.Builder().alwaysEat().nutrition(0).saturationMod(0).build()).tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<Spine> SPINE = ITEMS.register("spine", () -> new Spine());

	
	public static final RegistryObject<Rubble> RUBBLE = BLOCKS.register("rubble", Rubble::new);
	public static final RegistryObject<Item> RUBBLE_ITEM = ITEMS.register("rubble", () -> new BlockItem(RUBBLE.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final RegistryObject<DeadLeaves> DEADLEAVES = BLOCKS.register("deadleaves", DeadLeaves::new);
	public static final RegistryObject<Item> DEADLEAVES_ITEM = ITEMS.register("deadleaves", () -> new BlockItem(DEADLEAVES.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final RegistryObject<Spikes> SPIKES = BLOCKS.register("spikes", Spikes::new);
	public static final RegistryObject<Item> SPIKES_ITEM = ITEMS.register("spikes", () -> new BlockItem(SPIKES.get(), new Item.Properties()));
	public static final RegistryObject<SpikeTrap> SPIKETRAP = BLOCKS.register("spiketrap", SpikeTrap::new);
	public static final RegistryObject<Item> SPIKETRAP_ITEM = ITEMS.register("spiketrap", () -> new BlockItem(SPIKETRAP.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	
	public static final RegistryObject<HealthyOre> HEALTHYORE = BLOCKS.register("healthyore", HealthyOre::new);
	public static final RegistryObject<Item> HEALTHYORE_ITEM = ITEMS.register("healthyore", () -> new BlockItem(HEALTHYORE.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final RegistryObject<ArmorOre> ARMORORE = BLOCKS.register("armorore", ArmorOre::new);
	public static final RegistryObject<Item> ARMORORE_ITEM = ITEMS.register("armorore", () -> new BlockItem(ARMORORE.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final RegistryObject<FierceOre> FIERCEORE = BLOCKS.register("fierceore", FierceOre::new);
	public static final RegistryObject<Item> FIERCEORE_ITEM = ITEMS.register("fierceore", () -> new BlockItem(FIERCEORE.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final RegistryObject<SpiderNest> SPIDERNEST = BLOCKS.register("spidernest", SpiderNest::new);
	public static final RegistryObject<Item> SPIDERNEST_ITEM = ITEMS.register("spidernest", () -> new BlockItem(SPIDERNEST.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
}