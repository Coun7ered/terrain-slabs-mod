package net.countered.terrainslabs;

import net.countered.datagen.*;
import net.countered.terrainslabs.worldgen.feature.ModConfiguredFeatures;
import net.countered.terrainslabs.worldgen.feature.ModPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TerrainSlabsDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
		registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
	}

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ModWorldGenerator::new);
		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModLootTableProvider::new);
		pack.addProvider(ModBlockTagsProvider::new);
		pack.addProvider(ModRecipeProvider::new);
		Runtime.getRuntime().addShutdownHook(new Thread(this::deleteGeneratedDirectory)); //deletes directory src/main/generated/assets/terrainslabs because it is being generated but redundant
	}
	private void deleteGeneratedDirectory() {
		Path generatedDir = Path.of("src", "main", "generated", "assets", "minecraft");

		if (Files.exists(generatedDir)) {
			try {
				Files.delete(generatedDir);
				System.out.println("Deleted generated directory after data generation: " + generatedDir);
			} catch (IOException e) {
				System.err.println("Error deleting directory: " + e.getMessage());
			}
		}
	}
}
