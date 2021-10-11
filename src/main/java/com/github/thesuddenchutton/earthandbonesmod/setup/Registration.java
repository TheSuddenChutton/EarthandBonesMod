package com.github.thesuddenchutton.earthandbonesmod.setup;

import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
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
import com.github.thesuddenchutton.earthandbonesmod.blocks.CreeperCocoonLower;
import com.github.thesuddenchutton.earthandbonesmod.blocks.CreeperCocoonUpper;
import com.github.thesuddenchutton.earthandbonesmod.blocks.DeadLeaves;
import com.github.thesuddenchutton.earthandbonesmod.blocks.FierceOre;
import com.github.thesuddenchutton.earthandbonesmod.blocks.HealthyOre;
import com.github.thesuddenchutton.earthandbonesmod.blocks.MultidirectionalSpikeTrap;
import com.github.thesuddenchutton.earthandbonesmod.blocks.Rubble;
import com.github.thesuddenchutton.earthandbonesmod.blocks.SpiderNest;
import com.github.thesuddenchutton.earthandbonesmod.blocks.SpikeTrap;
import com.github.thesuddenchutton.earthandbonesmod.blocks.Spikes;
import com.github.thesuddenchutton.earthandbonesmod.blocks.TrapAlertAir;
import com.github.thesuddenchutton.earthandbonesmod.items.Armorin;
import com.github.thesuddenchutton.earthandbonesmod.items.Cigar;
import com.github.thesuddenchutton.earthandbonesmod.items.Fierceion;
import com.github.thesuddenchutton.earthandbonesmod.items.Healthium;
import com.github.thesuddenchutton.earthandbonesmod.items.HumanFlesh;
import com.github.thesuddenchutton.earthandbonesmod.items.Spine;
import com.github.thesuddenchutton.earthandbonesmod.items.Tobbaco;
import com.github.thesuddenchutton.earthandbonesmod.world.gen.features.AreaTrap;
import com.github.thesuddenchutton.earthandbonesmod.world.gen.features.CeilTrap;
import com.github.thesuddenchutton.earthandbonesmod.world.gen.features.FloorTrap;

public class Registration {
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
	private static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, MODID);
	private static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MODID);
	private static final DeferredRegister<BlockEntityType<?>> BLOCKEENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MODID);
	
	public static void init() {
		BLOCKEENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		FEATURES.register(FMLJavaModLoadingContext.get().getModEventBus());
		SOUNDS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
	
	
	public static final RegistryObject<AreaTrap> TreeTrapSpruce = FEATURES.register("treetrapspruce", () -> new AreaTrap(OreConfiguration.CODEC, new ArrayList<Block>(Arrays.asList(Blocks.SPRUCE_LOG)),true));
	public static final RegistryObject<AreaTrap> TreeTrapAcacia = FEATURES.register("treetrapacacia", () -> new AreaTrap(OreConfiguration.CODEC, new ArrayList<Block>(Arrays.asList(Blocks.ACACIA_LOG)),true));
	public static final RegistryObject<AreaTrap> TreeTrapJungle = FEATURES.register("treetrapjungle", () -> new AreaTrap(OreConfiguration.CODEC, new ArrayList<Block>(Arrays.asList(Blocks.JUNGLE_LOG)),true));
	
	public static final RegistryObject<FloorTrap> FloorTrapUnderground = FEATURES.register("floortrapunderground", () -> new FloorTrap(OreConfiguration.CODEC,true));
	
	public static final RegistryObject<CeilTrap> SpiderNestDarkOak = FEATURES.register("spidernestdo", () -> new CeilTrap(OreConfiguration.CODEC, new ArrayList<Block>(Arrays.asList(Blocks.DARK_OAK_LEAVES, Registration.SPIDERNEST.get())), false, false));
	public static final RegistryObject<CeilTrap> SpiderNestOakBirch = FEATURES.register("spidernestoak", () -> new CeilTrap(OreConfiguration.CODEC, new ArrayList<Block>(Arrays.asList(Blocks.OAK_LEAVES,Blocks.BIRCH_LEAVES)), false, false));
	
	public static final RegistryObject<CeilTrap> CreeperCocoonJungle = FEATURES.register("creepercocoonju", () -> new CeilTrap(OreConfiguration.CODEC, new ArrayList<Block>(Arrays.asList(Blocks.JUNGLE_LEAVES)),true, false));
	public static final RegistryObject<CeilTrap> CreeperCocoonSwamp = FEATURES.register("creepercocoonsw", () -> new CeilTrap(OreConfiguration.CODEC, new ArrayList<Block>(Arrays.asList(Blocks.OAK_LEAVES)),false, true));
	
	
	public static final RegistryObject<HumanFlesh> HUMANFLESH = ITEMS.register("humanflesh", () -> new HumanFlesh(new Item.Properties().food(new FoodProperties.Builder().meat().alwaysEat().nutrition(5).saturationMod(-4f).build()).tab(CreativeModeTab.TAB_FOOD)));
	public static final RegistryObject<Healthium> HEALTHIUM = ITEMS.register("healthium", () -> new Healthium(new Item.Properties().food(new FoodProperties.Builder().alwaysEat().nutrition(0).saturationMod(10).build()).tab(CreativeModeTab.TAB_FOOD)));
	public static final RegistryObject<Armorin> ARMORIN = ITEMS.register("armorin", () -> new Armorin(new Item.Properties().food(new FoodProperties.Builder().alwaysEat().nutrition(0).saturationMod(0).build()).tab(CreativeModeTab.TAB_FOOD)));
	public static final RegistryObject<Fierceion> FIERCEION = ITEMS.register("fiercion", () -> new Fierceion(new Item.Properties().food(new FoodProperties.Builder().alwaysEat().nutrition(0).saturationMod(0).build()).tab(CreativeModeTab.TAB_FOOD)));
	public static final RegistryObject<Spine> SPINE = ITEMS.register("spine", () -> new Spine());
	public static final RegistryObject<Tobbaco> TOBACCO = ITEMS.register("tobacco", () -> new Tobbaco(new Item.Properties().food(new FoodProperties.Builder().alwaysEat().nutrition(-3).saturationMod(4).build()).tab(CreativeModeTab.TAB_FOOD)));
	public static final RegistryObject<Cigar> CIGAR = ITEMS.register("cigar", () -> new Cigar(new Item.Properties().food(new FoodProperties.Builder().alwaysEat().nutrition(-1).saturationMod(4).build()).tab(CreativeModeTab.TAB_FOOD)));

	
	public static final RegistryObject<Rubble> RUBBLE = BLOCKS.register("rubble", Rubble::new);
	public static final RegistryObject<Item> RUBBLE_ITEM = ITEMS.register("rubble", () -> new BlockItem(RUBBLE.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final RegistryObject<DeadLeaves> DEADLEAVES = BLOCKS.register("deadleaves", DeadLeaves::new);
	public static final RegistryObject<Item> DEADLEAVES_ITEM = ITEMS.register("deadleaves", () -> new BlockItem(DEADLEAVES.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	
	public static final RegistryObject<Spikes> SPIKES = BLOCKS.register("spikes", Spikes::new);
	public static final RegistryObject<Item> SPIKES_ITEM = ITEMS.register("spikes", () -> new BlockItem(SPIKES.get(), new Item.Properties()));
	public static final RegistryObject<TrapAlertAir> TRAP_ALERT_AIR = BLOCKS.register("trapalertair", TrapAlertAir::new);
	public static final RegistryObject<Item> TRAP_ALERT_AIR_ITEM = ITEMS.register("trapalertair", () -> new BlockItem(TRAP_ALERT_AIR.get(), new Item.Properties()));
	public static final RegistryObject<SpikeTrap> SPIKETRAP = BLOCKS.register("spiketrap", SpikeTrap::new);
	public static final RegistryObject<Item> SPIKETRAP_ITEM = ITEMS.register("spiketrap", () -> new BlockItem(SPIKETRAP.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final RegistryObject<MultidirectionalSpikeTrap> ACACIALOGTRAP = BLOCKS.register("acacialogtrap", MultidirectionalSpikeTrap::new);
	public static final RegistryObject<Item> ACACIALOGTRAP_ITEM = ITEMS.register("acacialogtrap", () -> new BlockItem(ACACIALOGTRAP.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final RegistryObject<MultidirectionalSpikeTrap> SPRUCELOGTRAP = BLOCKS.register("sprucelogtrap", MultidirectionalSpikeTrap::new);
	public static final RegistryObject<Item> SPRUCELOGTRAP_ITEM = ITEMS.register("sprucelogtrap", () -> new BlockItem(SPRUCELOGTRAP.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final RegistryObject<MultidirectionalSpikeTrap> JUNGLELOGTRAP = BLOCKS.register("junglelogtrap", MultidirectionalSpikeTrap::new);
	public static final RegistryObject<Item> JUNGLELOGTRAP_ITEM = ITEMS.register("junglelogtrap", () -> new BlockItem(JUNGLELOGTRAP.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	
	public static final RegistryObject<HealthyOre> HEALTHYORE = BLOCKS.register("healthyore", HealthyOre::new);
	public static final RegistryObject<Item> HEALTHYORE_ITEM = ITEMS.register("healthyore", () -> new BlockItem(HEALTHYORE.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final RegistryObject<ArmorOre> ARMORORE = BLOCKS.register("armorore", ArmorOre::new);
	public static final RegistryObject<Item> ARMORORE_ITEM = ITEMS.register("armorore", () -> new BlockItem(ARMORORE.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final RegistryObject<FierceOre> FIERCEORE = BLOCKS.register("fierceore", FierceOre::new);
	public static final RegistryObject<Item> FIERCEORE_ITEM = ITEMS.register("fierceore", () -> new BlockItem(FIERCEORE.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final RegistryObject<SpiderNest> SPIDERNEST = BLOCKS.register("spidernest", SpiderNest::new);
	public static final RegistryObject<Item> SPIDERNEST_ITEM = ITEMS.register("spidernest", () -> new BlockItem(SPIDERNEST.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final RegistryObject<CreeperCocoonUpper> CREEPERCOCOON_UPPER = BLOCKS.register("creepercocoon_upper", CreeperCocoonUpper::new);
	public static final RegistryObject<Item> CREEPERCOCOON_UPPER_ITEM = ITEMS.register("creepercocoon_upper", () -> new BlockItem(CREEPERCOCOON_UPPER.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final RegistryObject<CreeperCocoonLower> CREEPERCOCOON_LOWER = BLOCKS.register("creepercocoon_lower", CreeperCocoonLower::new);
	public static final RegistryObject<Item> CREEPERCOCOON_LOWER_ITEM = ITEMS.register("creepercocoon_lower", () -> new BlockItem(CREEPERCOCOON_LOWER.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
}