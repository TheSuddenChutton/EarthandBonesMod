package com.github.thesuddenchutton.earthandbonesmod.world.gen;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RangeDecoratorConfiguration;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import com.github.thesuddenchutton.earthandbonesmod.setup.Registration;
import com.github.thesuddenchutton.earthandbonesmod.util.AnythingTest;
import com.github.thesuddenchutton.earthandbonesmod.util.DirtOrGrassTest;
import com.github.thesuddenchutton.earthandbonesmod.util.GravelOrStoneTest;
import com.github.thesuddenchutton.earthandbonesmod.world.gen.features.AreaTrap;
import com.github.thesuddenchutton.earthandbonesmod.world.gen.features.CeilTrap;


public class BlockGenerator{

	public static void generate(final BiomeLoadingEvent e) {
		generateOre(e.getGeneration(), new DirtOrGrassTest(), Registration.RUBBLE.get().defaultBlockState(), 10, 50, 150, 100);
		generateOre(e.getGeneration(), new GravelOrStoneTest(), Registration.RUBBLE.get().defaultBlockState(), 5, 10, 100, 50);
		generateOre(e.getGeneration(), new GravelOrStoneTest(), Registration.ARMORORE.get().defaultBlockState(), 4, 10, 40, 10);
		generateOre(e.getGeneration(), new GravelOrStoneTest(), Registration.FIERCEORE.get().defaultBlockState(), 4, 10, 40, 10);
		generateOre(e.getGeneration(), new GravelOrStoneTest(), Registration.HEALTHYORE.get().defaultBlockState(), 4, 10, 40, 10);
		generateFloorTrapUnderground(e.getGeneration(), new AnythingTest(), Registration.SPIKETRAP.get().defaultBlockState(), 4, 1, 3, 20);
		generateFloorTrapUnderground(e.getGeneration(), new AnythingTest(), Registration.SPIKETRAP.get().defaultBlockState(), 1, 33, 50, 30);
		generateAreaTrap(Registration.TreeTrapAcacia.get(), e.getGeneration(), new AnythingTest(), Registration.ACACIALOGTRAP.get().defaultBlockState(), 10, 40, 70, 15);
		generateAreaTrap(Registration.TreeTrapJungle.get(), e.getGeneration(), new AnythingTest(), Registration.JUNGLELOGTRAP.get().defaultBlockState(), 10, 40, 70, 15);
		generateAreaTrap(Registration.TreeTrapSpruce.get(), e.getGeneration(), new AnythingTest(), Registration.SPRUCELOGTRAP.get().defaultBlockState(), 10, 40, 70, 15);
		generateCeilTrap(Registration.SpiderNestDarkOak.get(), e.getGeneration(), new AnythingTest(), Registration.SPIDERNEST.get().defaultBlockState(), 15, 45, 70, 1);
		generateCeilTrap(Registration.SpiderNestOakBirch.get(), e.getGeneration(), new AnythingTest(), Registration.SPIDERNEST.get().defaultBlockState(), 1, 45, 60, 10);
		generateCeilTrap(Registration.CreeperCocoonJungle.get(), e.getGeneration(), new AnythingTest(), Registration.CREEPERCOCOON_UPPER.get().defaultBlockState(), 1, 55, 70, 20);
		generateCeilTrap(Registration.CreeperCocoonSwamp.get(), e.getGeneration(), new AnythingTest(), Registration.CREEPERCOCOON_UPPER.get().defaultBlockState(), 1, 35, 45, 20);
		}
	private static void generateOre(BiomeGenerationSettingsBuilder settings, RuleTest fillerType, BlockState state,
	          int veinSize, int minHeight, int maxHeight, int amount) {
				settings.addFeature(Decoration.UNDERGROUND_ORES,
						Feature.ORE.configured(new OreConfiguration(fillerType, state, veinSize)).range(
								new RangeDecoratorConfiguration(UniformHeight.of(VerticalAnchor.aboveBottom(minHeight),
										VerticalAnchor.aboveBottom(maxHeight)))).squared().count(amount));
	}
	private static void generateFloorTrapUnderground(BiomeGenerationSettingsBuilder settings, RuleTest fillerType, BlockState state,
	          int veinSize, int minHeight, int maxHeight, int amount) {
				settings.addFeature(Decoration.UNDERGROUND_ORES,
						Registration.FloorTrapUnderground.get().configured(new OreConfiguration(fillerType, state, veinSize)).range(
								new RangeDecoratorConfiguration(UniformHeight.of(VerticalAnchor.aboveBottom(minHeight),
										VerticalAnchor.aboveBottom(maxHeight)))).squared().count(amount));
	}
	private static void generateAreaTrap(AreaTrap trap, BiomeGenerationSettingsBuilder settings, RuleTest fillerType, BlockState state,
	          int veinSize, int minHeight, int maxHeight, int amount) {
				settings.addFeature(Decoration.UNDERGROUND_ORES,
						trap.configured(new OreConfiguration(fillerType, state, veinSize)).range(
								new RangeDecoratorConfiguration(UniformHeight.of(VerticalAnchor.aboveBottom(minHeight),
										VerticalAnchor.aboveBottom(maxHeight)))).squared().count(amount));
	}
	private static void generateCeilTrap(CeilTrap trap, BiomeGenerationSettingsBuilder settings, RuleTest fillerType, BlockState state,
	          int veinSize, int minHeight, int maxHeight, int amount) {
				settings.addFeature(Decoration.UNDERGROUND_ORES,
						trap.configured(new OreConfiguration(fillerType, state, veinSize)).range(
								new RangeDecoratorConfiguration(UniformHeight.of(VerticalAnchor.aboveBottom(minHeight),
										VerticalAnchor.aboveBottom(maxHeight)))).squared().count(amount));
	}
}
