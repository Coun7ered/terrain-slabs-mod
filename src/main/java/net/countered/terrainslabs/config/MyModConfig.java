package net.countered.terrainslabs.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class MyModConfig extends MidnightConfig {
    // Define categories and entries
    public static final String GENERATION = "generation";  // category for world generation

    @Entry(category = GENERATION)
    public static boolean enableSlabGeneration = true;

    @Entry(category = GENERATION)
    public static boolean enableVegetationOnSlabs = true;

    @Entry(category = GENERATION)
    public static boolean enableSnowOnSlabs = true;

    public static final String TEXTURES = "textures";
    public enum TextureConnection {
        CONNECTED, VANILLA
    }
    @Entry(category = TEXTURES)
    public static TextureConnection textureConnectionStyle = TextureConnection.CONNECTED;
}