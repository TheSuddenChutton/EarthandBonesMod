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
import com.github.thesuddenchutton.earthandbonesmod.util.DirtOrGrassTest;
import com.github.thesuddenchutton.earthandbonesmod.util.GravelOrStoneTest;


public class Ores{

	public static void generateOres(final BiomeLoadingEvent e) {
		generateOre(e.getGeneration(), new DirtOrGrassTest(), Registration.RUBBLE.get().defaultBlockState(), 15, 50, 150, 150);
		generateOre(e.getGeneration(), new GravelOrStoneTest(), Registration.RUBBLE.get().defaultBlockState(), 5, 0, 100, 400);
		generateOre(e.getGeneration(), new BlockMatchTest(Blocks.STONE), Registration.SPIKETRAP.get().defaultBlockState(), 1, 0, 100, 400);
	}
	private static void generateOre(BiomeGenerationSettingsBuilder settings, RuleTest fillerType, BlockState state,
	          int veinSize, int minHeight, int maxHeight, int amount) {
				settings.addFeature(Decoration.UNDERGROUND_ORES,
						Feature.ORE.configured(new OreConfiguration(fillerType, state, veinSize)).range(
								new RangeDecoratorConfiguration(UniformHeight.of(VerticalAnchor.aboveBottom(minHeight),
										VerticalAnchor.aboveBottom(maxHeight)))).squared().count(amount));
	}
}
