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
		generateOre(e.getGeneration(), new DirtOrGrassTest(), Registration.RUBBLE.get().defaultBlockState(), 10, 50, 150, 100);
		generateOre(e.getGeneration(), new GravelOrStoneTest(), Registration.RUBBLE.get().defaultBlockState(), 5, 10, 100, 50);
		generateOre(e.getGeneration(), new GravelOrStoneTest(), Registration.ARMORORE.get().defaultBlockState(), 4, 10, 40, 10);
		generateOre(e.getGeneration(), new GravelOrStoneTest(), Registration.FIERCEORE.get().defaultBlockState(), 4, 10, 40, 10);
		generateOre(e.getGeneration(), new GravelOrStoneTest(), Registration.HEALTHYORE.get().defaultBlockState(), 4, 10, 40, 10);
		generateFloorTrapUnderground(e.getGeneration(), new AnythingTest(), Registration.SPIKETRAP.get().defaultBlockState(), 1, 1, 20, 150);
		generateSpiders(Registration.SpiderNestDO.get(), e.getGeneration(), new AnythingTest(), Registration.SPIDERNEST.get().defaultBlockState(), 1, 1, 20, 50);
		generateSpiders(Registration.SpiderNestJU.get(), e.getGeneration(), new AnythingTest(), Registration.SPIDERNEST.get().defaultBlockState(), 5, 1, 20, 20);
		generateSpiders(Registration.SpiderNestOAK.get(), e.getGeneration(), new AnythingTest(), Registration.SPIDERNEST.get().defaultBlockState(), 1, 1, 20, 10);
		generateSpiders(Registration.SpiderNestBI.get(), e.getGeneration(), new AnythingTest(), Registration.SPIDERNEST.get().defaultBlockState(), 1, 1, 20, 15);
	}
	private static void generateOre(BiomeGenerationSettingsBuilder settings, RuleTest fillerType, BlockState state,
	          int veinSize, int minHeight, int maxHeight, int amount) {
				settings.addFeature(Decoration.UNDERGROUND_ORES,
						Feature.ORE.configured(new OreConfiguration(fillerType, state, veinSize)).range(
								new RangeDecoratorConfiguration(UniformHeight.of(VerticalAnchor.aboveBottom(minHeight),
										VerticalAnchor.aboveBottom(maxHeight)))).count(amount));
	}
	private static void generateFloorTrapUnderground(BiomeGenerationSettingsBuilder settings, RuleTest fillerType, BlockState state,
	          int veinSize, int minHeight, int maxHeight, int amount) {
				settings.addFeature(Decoration.UNDERGROUND_ORES,
						Registration.FloorTrapUnderground.get().configured(new OreConfiguration(fillerType, state, veinSize)).range(
								new RangeDecoratorConfiguration(UniformHeight.of(VerticalAnchor.aboveBottom(minHeight),
										VerticalAnchor.aboveBottom(maxHeight)))).squared().count(amount));
	}
	private static void generateSpiders(CeilTrap spiders, BiomeGenerationSettingsBuilder settings, RuleTest fillerType, BlockState state,
	          int veinSize, int minHeight, int maxHeight, int amount) {
				settings.addFeature(Decoration.UNDERGROUND_ORES,
						spiders.configured(new OreConfiguration(fillerType, state, veinSize)).range(
								new RangeDecoratorConfiguration(UniformHeight.of(VerticalAnchor.aboveBottom(minHeight),
										VerticalAnchor.aboveBottom(maxHeight)))).count(amount));
	}
}
