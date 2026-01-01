package net.countered.terrainslabs;

import eu.midnightdust.lib.config.MidnightConfig;
import net.countered.terrainslabs.block.ModBlocksRegistry;
import net.countered.terrainslabs.callbacks.RegisterCallbacks;
import net.countered.terrainslabs.config.ModConfig;
import net.countered.terrainslabs.item.ModItemGroups;
import net.countered.terrainslabs.item.ShovelPathSlab;
import net.countered.terrainslabs.persistence.SlabChunkAttachment;
import net.countered.terrainslabs.worldgen.feature.ModAddedFeatures;
import net.countered.terrainslabs.worldgen.slabfeature.ModSlabGeneration;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TerrainSlabs implements ModInitializer {
	public static final String MOD_ID = "terrainslabs";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing TerrainSlabs");
		MidnightConfig.init(TerrainSlabs.MOD_ID, ModConfig.class);
		SlabChunkAttachment.registerSlabAttachment();
		ModBlocksRegistry.registerModBlocks();
		ModAddedFeatures.registerFeatures();
		ModSlabGeneration.generateSlabs();
		ModItemGroups.registerItemGroups();
		ShovelPathSlab.init();
		RegisterCallbacks.registerCallbacks();
	}
}