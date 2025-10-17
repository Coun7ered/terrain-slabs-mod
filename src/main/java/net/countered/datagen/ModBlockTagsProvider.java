package net.countered.datagen;

import net.countered.terrainslabs.block.ModBlocksRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagsProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        this.getOrCreateTagBuilder(BlockTags.SLABS)
                .add(ModBlocksRegistry.DIRT_SLAB)
                .add(ModBlocksRegistry.MUD_SLAB)
                .add(ModBlocksRegistry.COARSE_SLAB)
                .add(ModBlocksRegistry.SNOW_SLAB)
                .add(ModBlocksRegistry.PACKED_ICE_SLAB)
                .add(ModBlocksRegistry.DEEPSLATE_SLAB)
                .add(ModBlocksRegistry.CLAY_SLAB)
                .add(ModBlocksRegistry.MOSS_SLAB)

                //terralith compat
                .add(ModBlocksRegistry.CALCITE_SLAB)
                .add(ModBlocksRegistry.SMOOTH_BASALT_SLAB)
                .add(ModBlocksRegistry.LIGHT_BLUE_TERRACOTTA_SLAB)
                .add(ModBlocksRegistry.CYAN_TERRACOTTA_SLAB)
                .add(ModBlocksRegistry.CUSTOM_COBBLESTONE_SLAB)
                .add(ModBlocksRegistry.CUSTOM_MOSSY_COBBLESTONE_SLAB)
                .add(ModBlocksRegistry.CUSTOM_COBBLED_DEEPSLATE_SLAB)
                .add(ModBlocksRegistry.ICE_SLAB)
                .add(ModBlocksRegistry.ROOTED_DIRT_SLAB)
                .add(ModBlocksRegistry.PACKED_MUD_SLAB)
                .add(ModBlocksRegistry.BLUE_ICE_SLAB)
                .add(ModBlocksRegistry.BLACK_TERRACOTTA_SLAB)
                .add(ModBlocksRegistry.CUSTOM_PRISMARINE_SLAB)

                .add(ModBlocksRegistry.GRASS_SLAB)
                .add(ModBlocksRegistry.MYCELIUM_SLAB)
                .add(ModBlocksRegistry.PODZOL_SLAB)
                .add(ModBlocksRegistry.PATH_SLAB)

                .add(ModBlocksRegistry.GRAVEL_SLAB)
                .add(ModBlocksRegistry.SAND_SLAB)
                .add(ModBlocksRegistry.RED_SAND_SLAB)

                .add(ModBlocksRegistry.TERRACOTTA_SLAB)
                .add(ModBlocksRegistry.RED_TERRACOTTA_SLAB)
                .add(ModBlocksRegistry.ORANGE_TERRACOTTA_SLAB)
                .add(ModBlocksRegistry.LIGHT_GRAY_TERRACOTTA_SLAB)
                .add(ModBlocksRegistry.WHITE_TERRACOTTA_SLAB)
                .add(ModBlocksRegistry.BROWN_TERRACOTTA_SLAB)
                .add(ModBlocksRegistry.YELLOW_TERRACOTTA_SLAB)

                .add(ModBlocksRegistry.CUSTOM_TUFF_SLAB)
                .add(ModBlocksRegistry.CUSTOM_STONE_SLAB)
                .add(ModBlocksRegistry.CUSTOM_SANDSTONE_SLAB)
                .add(ModBlocksRegistry.CUSTOM_RED_SANDSTONE_SLAB)
                .add(ModBlocksRegistry.CUSTOM_DIORITE_SLAB)
                .add(ModBlocksRegistry.CUSTOM_ANDESITE_SLAB)
                .add(ModBlocksRegistry.CUSTOM_GRANITE_SLAB)

                .add(ModBlocksRegistry.SOUL_SAND_SLAB)
                .add(ModBlocksRegistry.SOUL_SOIL_SLAB)
                .add(ModBlocksRegistry.NETHERRACK_SLAB)
                .add(ModBlocksRegistry.CRIMSON_NYLIUM_SLAB)
                .add(ModBlocksRegistry.WARPED_NYLIUM_SLAB)
                .add(ModBlocksRegistry.BASALT_SLAB)
                .add(ModBlocksRegistry.CUSTOM_BLACKSTONE_SLAB)
                .add(ModBlocksRegistry.ENDSTONE_SLAB);

        this.getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE)
                .add(ModBlocksRegistry.DIRT_SLAB)
                .add(ModBlocksRegistry.MUD_SLAB)
                .add(ModBlocksRegistry.COARSE_SLAB)
                .add(ModBlocksRegistry.SNOW_SLAB)
                .add(ModBlocksRegistry.CLAY_SLAB)
                .add(ModBlocksRegistry.GRASS_SLAB)
                .add(ModBlocksRegistry.MYCELIUM_SLAB)
                .add(ModBlocksRegistry.PODZOL_SLAB)
                .add(ModBlocksRegistry.PATH_SLAB)
                .add(ModBlocksRegistry.GRAVEL_SLAB)
                .add(ModBlocksRegistry.SAND_SLAB)
                .add(ModBlocksRegistry.RED_SAND_SLAB)
                .add(ModBlocksRegistry.SNOW_ON_TOP)

                .add(ModBlocksRegistry.SOUL_SAND_SLAB)
                .add(ModBlocksRegistry.SOUL_SOIL_SLAB)

                //terralith
                .add(ModBlocksRegistry.ROOTED_DIRT_SLAB);

        this.getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocksRegistry.PACKED_ICE_SLAB)
                .add(ModBlocksRegistry.DEEPSLATE_SLAB)
                .add(ModBlocksRegistry.TERRACOTTA_SLAB)
                .add(ModBlocksRegistry.RED_TERRACOTTA_SLAB)
                .add(ModBlocksRegistry.ORANGE_TERRACOTTA_SLAB)
                .add(ModBlocksRegistry.LIGHT_GRAY_TERRACOTTA_SLAB)
                .add(ModBlocksRegistry.WHITE_TERRACOTTA_SLAB)
                .add(ModBlocksRegistry.BROWN_TERRACOTTA_SLAB)
                .add(ModBlocksRegistry.YELLOW_TERRACOTTA_SLAB)
                .add(ModBlocksRegistry.CUSTOM_TUFF_SLAB)
                .add(ModBlocksRegistry.CUSTOM_GRANITE_SLAB)
                .add(ModBlocksRegistry.CUSTOM_ANDESITE_SLAB)
                .add(ModBlocksRegistry.CUSTOM_DIORITE_SLAB)
                .add(ModBlocksRegistry.CUSTOM_STONE_SLAB)
                .add(ModBlocksRegistry.CUSTOM_RED_SANDSTONE_SLAB)
                .add(ModBlocksRegistry.CUSTOM_SANDSTONE_SLAB)
                //terralith
                .add(ModBlocksRegistry.CALCITE_SLAB)
                .add(ModBlocksRegistry.SMOOTH_BASALT_SLAB)
                .add(ModBlocksRegistry.LIGHT_BLUE_TERRACOTTA_SLAB)
                .add(ModBlocksRegistry.CYAN_TERRACOTTA_SLAB)
                .add(ModBlocksRegistry.CUSTOM_COBBLESTONE_SLAB)
                .add(ModBlocksRegistry.CUSTOM_MOSSY_COBBLESTONE_SLAB)
                .add(ModBlocksRegistry.CUSTOM_COBBLED_DEEPSLATE_SLAB)
                .add(ModBlocksRegistry.ICE_SLAB)
                .add(ModBlocksRegistry.PACKED_MUD_SLAB)
                .add(ModBlocksRegistry.BLUE_ICE_SLAB)
                .add(ModBlocksRegistry.BLACK_TERRACOTTA_SLAB)
                .add(ModBlocksRegistry.CUSTOM_PRISMARINE_SLAB)

                .add(ModBlocksRegistry.NETHERRACK_SLAB)
                .add(ModBlocksRegistry.WARPED_NYLIUM_SLAB)
                .add(ModBlocksRegistry.CRIMSON_NYLIUM_SLAB)
                .add(ModBlocksRegistry.CUSTOM_BLACKSTONE_SLAB)
                .add(ModBlocksRegistry.BASALT_SLAB)
                .add(ModBlocksRegistry.ENDSTONE_SLAB);

        this.getOrCreateTagBuilder(BlockTags.HOE_MINEABLE)
                .add(ModBlocksRegistry.MOSS_SLAB);

        this.getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)
                .add(ModBlocksRegistry.DEAD_BUSH_ON_TOP)
                .add(ModBlocksRegistry.SHORT_GRASS_ON_TOP)
                .add(ModBlocksRegistry.FERN_ON_TOP)
                .add(ModBlocksRegistry.BROWN_MUSHROOM_ON_TOP)
                .add(ModBlocksRegistry.RED_MUSHROOM_ON_TOP);

        this.getOrCreateTagBuilder(BlockTags.SWORD_EFFICIENT)
                .add(ModBlocksRegistry.DEAD_BUSH_ON_TOP)
                .add(ModBlocksRegistry.BROWN_MUSHROOM_ON_TOP)
                .add(ModBlocksRegistry.RED_MUSHROOM_ON_TOP)
                .add(ModBlocksRegistry.SHORT_GRASS_ON_TOP)
                .add(ModBlocksRegistry.FERN_ON_TOP);


        this.getOrCreateTagBuilder(BlockTags.SMELTS_TO_GLASS).add(ModBlocksRegistry.SAND_SLAB, ModBlocksRegistry.RED_SAND_SLAB);

        this.getOrCreateTagBuilder(BlockTags.PARROTS_SPAWNABLE_ON).add(ModBlocksRegistry.GRASS_SLAB);

        this.getOrCreateTagBuilder(BlockTags.ANIMALS_SPAWNABLE_ON).add(ModBlocksRegistry.GRASS_SLAB);

        this.getOrCreateTagBuilder(BlockTags.VALID_SPAWN).add(ModBlocksRegistry.GRASS_SLAB, ModBlocksRegistry.PODZOL_SLAB);

        this.getOrCreateTagBuilder(BlockTags.AXOLOTLS_SPAWNABLE_ON).add(ModBlocksRegistry.CLAY_SLAB);

        this.getOrCreateTagBuilder(BlockTags.RABBITS_SPAWNABLE_ON).add(ModBlocksRegistry.GRASS_SLAB, ModBlocksRegistry.SNOW_SLAB, ModBlocksRegistry.SAND_SLAB);

        this.getOrCreateTagBuilder(BlockTags.GOATS_SPAWNABLE_ON) //needs improvement
                .add(ModBlocksRegistry.CUSTOM_STONE_SLAB, ModBlocksRegistry.SNOW_SLAB, ModBlocksRegistry.PACKED_ICE_SLAB, ModBlocksRegistry.GRAVEL_SLAB);

        this.getOrCreateTagBuilder(BlockTags.SNOW).add(ModBlocksRegistry.SNOW_SLAB).add(ModBlocksRegistry.SNOW_ON_TOP);

        this.getOrCreateTagBuilder(BlockTags.SCULK_REPLACEABLE)
                .add(ModBlocksRegistry.CUSTOM_STONE_SLAB)
                .add(ModBlocksRegistry.DIRT_SLAB)
                .add(ModBlocksRegistry.TERRACOTTA_SLAB)  //need extension
                .add(ModBlocksRegistry.SAND_SLAB, ModBlocksRegistry.RED_SAND_SLAB)
                .add(ModBlocksRegistry.GRAVEL_SLAB)
                .add(ModBlocksRegistry.CLAY_SLAB)
                .add(ModBlocksRegistry.CUSTOM_RED_SANDSTONE_SLAB)
                .add(ModBlocksRegistry.CUSTOM_SANDSTONE_SLAB);


        this.getOrCreateTagBuilder(BlockTags.AZALEA_ROOT_REPLACEABLE)
                .add(ModBlocksRegistry.CUSTOM_STONE_SLAB)
                .add(ModBlocksRegistry.DIRT_SLAB)
                .add(ModBlocksRegistry.TERRACOTTA_SLAB)  //need extension
                .add(ModBlocksRegistry.RED_SAND_SLAB)
                .add(ModBlocksRegistry.CLAY_SLAB)
                .add(ModBlocksRegistry.GRAVEL_SLAB)
                .add(ModBlocksRegistry.SAND_SLAB)
                .add(ModBlocksRegistry.SNOW_SLAB);

        this.getOrCreateTagBuilder(BlockTags.SNIFFER_DIGGABLE_BLOCK)
                .add(ModBlocksRegistry.DIRT_SLAB, ModBlocksRegistry.GRASS_SLAB, ModBlocksRegistry.PODZOL_SLAB, ModBlocksRegistry.COARSE_SLAB, ModBlocksRegistry.MOSS_SLAB, ModBlocksRegistry.MUD_SLAB);

        this.getOrCreateTagBuilder(BlockTags.WOLVES_SPAWNABLE_ON).add(ModBlocksRegistry.GRASS_SLAB, ModBlocksRegistry.SNOW_SLAB, ModBlocksRegistry.COARSE_SLAB, ModBlocksRegistry.PODZOL_SLAB);

        this.getOrCreateTagBuilder(BlockTags.FOXES_SPAWNABLE_ON).add(ModBlocksRegistry.GRASS_SLAB, ModBlocksRegistry.SNOW_SLAB, ModBlocksRegistry.PODZOL_SLAB, ModBlocksRegistry.COARSE_SLAB);

        this.getOrCreateTagBuilder(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)
                .add(ModBlocksRegistry.MUD_SLAB);

        this.getOrCreateTagBuilder(BlockTags.MANGROVE_ROOTS_CAN_GROW_THROUGH)
                .add(ModBlocksRegistry.MUD_SLAB);

        this.getOrCreateTagBuilder(BlockTags.FROGS_SPAWNABLE_ON).add(ModBlocksRegistry.GRASS_SLAB, ModBlocksRegistry.MUD_SLAB);

        this.getOrCreateTagBuilder(BlockTags.UNDERWATER_BONEMEALS).add(ModBlocksRegistry.SEAGRASS_ON_TOP);

        this.getOrCreateTagBuilder(BlockTags.SOUL_SPEED_BLOCKS).add(ModBlocksRegistry.SOUL_SAND_SLAB, ModBlocksRegistry.SOUL_SOIL_SLAB);

        this.getOrCreateTagBuilder(BlockTags.INFINIBURN_OVERWORLD).add(ModBlocksRegistry.NETHERRACK_SLAB);

        this.getOrCreateTagBuilder(BlockTags.MUSHROOM_GROW_BLOCK).add(ModBlocksRegistry.MYCELIUM_SLAB).add(ModBlocksRegistry.PODZOL_SLAB).add(ModBlocksRegistry.CRIMSON_NYLIUM_SLAB).add(ModBlocksRegistry.WARPED_NYLIUM_SLAB);

        this.getOrCreateTagBuilder(BlockTags.DRAGON_IMMUNE).add(ModBlocksRegistry.ENDSTONE_SLAB);

        this.getOrCreateTagBuilder(BlockTags.NYLIUM).add(ModBlocksRegistry.CRIMSON_NYLIUM_SLAB, ModBlocksRegistry.WARPED_NYLIUM_SLAB);

    }
}
