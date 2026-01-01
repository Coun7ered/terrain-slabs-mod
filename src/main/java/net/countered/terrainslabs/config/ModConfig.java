package net.countered.terrainslabs.config;


import eu.midnightdust.lib.config.MidnightConfig;

public class ModConfig extends MidnightConfig {
    // Define categories and entries
    public static final String GENERATION = "generation";  // category for world generation

    @Entry(category = GENERATION)
    public static boolean enableSlabGeneration = true;

    @Entry(category = GENERATION)
    public static boolean enableVegetationOnSlabs = true;

    @Entry(category = GENERATION)
    public static boolean enableSnowOnSlabs = true;
}