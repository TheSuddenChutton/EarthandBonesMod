package com.github.thesuddenchutton.earthandbonesmod.world.gen;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RangeDecoratorConfiguration;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import com.github.thesuddenchutton.earthandbonesmod.setup.Registration;
import com.github.thesuddenchutton.earthandbonesmod.util.AnythingTest;
import com.github.thesuddenchutton.earthandbonesmod.util.DirtOrGrassTest;
import com.github.thesuddenchutton.earthandbonesmod.util.GravelOrStoneTest;
import com.github.thesuddenchutton.earthandbonesmod.world.gen.features.CeilTrap;


public class BlockGenerator{

	public static void generate(final BiomeLoadingEvent e) {
		generateOre(e.getGeneration(), new DirtOrGrassTest(), Registration.RUBBLE.get().defaultBlockState(), 10, 50, 150, 200);
		generateOre(e.getGeneration(), new GravelOrStoneTest(), Registration.RUBBLE.get().defaultBlockState(), 5, 1, 100, 300);
		generateOre(e.getGeneration(), new GravelOrStoneTest(), Registration.RUBBLE.get().defaultBlockState(), 1, 1, 100, 500);
		generateOre(e.getGeneration(), new GravelOrStoneTest(), Registration.ARMORORE.get().defaultBlockState(), 2, 1, 30, 300);
		generateOre(e.getGeneration(), new GravelOrStoneTest(), Registration.FIERCEORE.get().defaultBlockState(), 2, 1, 30, 300);
		generateOre(e.getGeneration(), new GravelOrStoneTest(), Registration.HEALTHYORE.get().defaultBlockState(), 2, 1, 30, 300);
		generateFloorTrap(e.getGeneration(), new BlockMatchTest(Blocks.STONE), Registration.SPIKETRAP.get().defaultBlockState(), 1, 1, 20, 50);
		generateSpiders(Registration.SpiderNestDO.get(), e.getGeneration(), new AnythingTest(), Registration.SPIDERNEST.get().defaultBlockState(), 1, 1, 20, 100);
		generateSpiders(Registration.SpiderNestJU.get(), e.getGeneration(), new AnythingTest(), Registration.SPIDERNEST.get().defaultBlockState(), 4, 1, 20, 50);
		generateSpiders(Registration.SpiderNestOAK.get(), e.getGeneration(), new AnythingTest(), Registration.SPIDERNEST.get().defaultBlockState(), 1, 1, 20, 10);
		generateSpiders(Registration.SpiderNestBI.get(), e.getGeneration(), new AnythingTest(), Registration.SPIDERNEST.get().defaultBlockState(), 1, 1, 20, 15);
	}
	private static void generateOre(BiomeGenerationSettingsBuilder settings, RuleTest fillerType, BlockState state,
	          int veinSize, int minHeight, int maxHeight, int amount) {
				settings.addFeature(Decoration.UNDERGROUND_ORES,
						Feature.ORE.configured(new OreConfiguration(fillerType, state, veinSize)).range(
								new RangeDecoratorConfiguration(UniformHeight.of(VerticalAnchor.aboveBottom(minHeight),
										VerticalAnchor.aboveBottom(maxHeight)))).squared().count(amount));
	}
	private static void generateFloorTrap(BiomeGenerationSettingsBuilder settings, RuleTest fillerType, BlockState state,
	          int veinSize, int minHeight, int maxHeight, int amount) {
				settings.addFeature(Decoration.UNDERGROUND_ORES,
						Registration.FloorTrap.get().configured(new OreConfiguration(fillerType, state, veinSize)).range(
								new RangeDecoratorConfiguration(UniformHeight.of(VerticalAnchor.aboveBottom(minHeight),
										VerticalAnchor.aboveBottom(maxHeight)))).squared().count(amount));
	}
	private static void generateSpiders(CeilTrap spiders, BiomeGenerationSettingsBuilder settings, RuleTest fillerType, BlockState state,
	          int veinSize, int minHeight, int maxHeight, int amount) {
				settings.addFeature(Decoration.UNDERGROUND_ORES,
						spiders.configured(new OreConfiguration(fillerType, state, veinSize)).range(
								new RangeDecoratorConfiguration(UniformHeight.of(VerticalAnchor.aboveBottom(minHeight),
										VerticalAnchor.aboveBottom(maxHeight)))).squared().count(amount));
	}
}
