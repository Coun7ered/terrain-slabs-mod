package net.countered.datagen;

import net.countered.terrainslabs.block.ModBlocksRegistry;
import net.countered.terrainslabs.block.customslabs.specialslabs.CustomSlab;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SnowBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.condition.TableBonusLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.AlternativeEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        RegistryWrapper.Impl<Enchantment> impl = this.registries.getOrThrow(RegistryKeys.ENCHANTMENT);

        this.addDrop(ModBlocksRegistry.DIRT_SLAB, block -> silkSlabDrops(block, Blocks.DIRT));
        this.addDrop(ModBlocksRegistry.MUD_SLAB, block -> silkSlabDrops(block, Blocks.MUD));
        this.addDrop(ModBlocksRegistry.COARSE_SLAB, block -> silkSlabDrops(block, Blocks.COARSE_DIRT));
        this.addDrop(ModBlocksRegistry.DEEPSLATE_SLAB, block -> silkSlabDrops(block, Blocks.COBBLED_DEEPSLATE));
        this.addDrop(ModBlocksRegistry.MOSS_SLAB, block -> silkSlabDrops(block, Blocks.MOSS_BLOCK));
        //terralith
        this.addDrop(ModBlocksRegistry.CALCITE_SLAB, block -> silkSlabDrops(block, Blocks.CALCITE));
        this.addDrop(ModBlocksRegistry.SMOOTH_BASALT_SLAB, block -> silkSlabDrops(block, Blocks.SMOOTH_BASALT));
        this.addDrop(ModBlocksRegistry.LIGHT_BLUE_TERRACOTTA_SLAB, block -> silkSlabDrops(block, Blocks.LIGHT_BLUE_TERRACOTTA));
        this.addDrop(ModBlocksRegistry.CYAN_TERRACOTTA_SLAB, block -> silkSlabDrops(block, Blocks.CYAN_TERRACOTTA));
        this.addDrop(ModBlocksRegistry.CUSTOM_COBBLESTONE_SLAB, block -> silkSlabDrops(block, Blocks.COBBLESTONE));
        this.addDrop(ModBlocksRegistry.CUSTOM_MOSSY_COBBLESTONE_SLAB, block -> silkSlabDrops(block, Blocks.MOSSY_COBBLESTONE));
        this.addDrop(ModBlocksRegistry.CUSTOM_COBBLED_DEEPSLATE_SLAB, block -> silkSlabDrops(block, Blocks.COBBLED_DEEPSLATE));
        this.addDrop(ModBlocksRegistry.ICE_SLAB, block -> silkSlabDrops(block, Blocks.ICE));
        this.addDrop(ModBlocksRegistry.ROOTED_DIRT_SLAB, block -> silkSlabDrops(block, Blocks.ROOTED_DIRT));
        this.addDrop(ModBlocksRegistry.PACKED_MUD_SLAB, block -> silkSlabDrops(block, Blocks.PACKED_MUD));
        this.addDrop(ModBlocksRegistry.BLUE_ICE_SLAB, block -> silkSlabDrops(block, Blocks.BLUE_ICE));
        this.addDrop(ModBlocksRegistry.BLACK_TERRACOTTA_SLAB, block -> silkSlabDrops(block, Blocks.BLACK_TERRACOTTA));
        this.addDrop(ModBlocksRegistry.CUSTOM_PRISMARINE_SLAB, block -> silkSlabDrops(block, Blocks.PRISMARINE));

        this.addDrop(ModBlocksRegistry.SAND_SLAB, block -> silkSlabDrops(block, Blocks.SAND));
        this.addDrop(ModBlocksRegistry.RED_SAND_SLAB, block -> silkSlabDrops(block, Blocks.RED_SAND));

        this.addDrop(ModBlocksRegistry.TERRACOTTA_SLAB, block -> silkSlabDrops(block, Blocks.TERRACOTTA));
        this.addDrop(ModBlocksRegistry.RED_TERRACOTTA_SLAB, block -> silkSlabDrops(block, Blocks.RED_TERRACOTTA));
        this.addDrop(ModBlocksRegistry.ORANGE_TERRACOTTA_SLAB, block -> silkSlabDrops(block, Blocks.ORANGE_TERRACOTTA));
        this.addDrop(ModBlocksRegistry.LIGHT_GRAY_TERRACOTTA_SLAB, block -> silkSlabDrops(block, Blocks.LIGHT_GRAY_TERRACOTTA));
        this.addDrop(ModBlocksRegistry.WHITE_TERRACOTTA_SLAB, block -> silkSlabDrops(block, Blocks.WHITE_TERRACOTTA));
        this.addDrop(ModBlocksRegistry.BROWN_TERRACOTTA_SLAB, block -> silkSlabDrops(block, Blocks.BROWN_TERRACOTTA));
        this.addDrop(ModBlocksRegistry.YELLOW_TERRACOTTA_SLAB, block -> silkSlabDrops(block, Blocks.YELLOW_TERRACOTTA));

        this.addDrop(ModBlocksRegistry.CUSTOM_STONE_SLAB, block -> silkSlabDrops(block, Blocks.COBBLESTONE));
        this.addDrop(ModBlocksRegistry.CUSTOM_ANDESITE_SLAB, block -> silkSlabDrops(block, Blocks.ANDESITE));
        this.addDrop(ModBlocksRegistry.CUSTOM_DIORITE_SLAB, block -> silkSlabDrops(block, Blocks.DIORITE));
        this.addDrop(ModBlocksRegistry.CUSTOM_GRANITE_SLAB, block -> silkSlabDrops(block, Blocks.GRANITE));
        this.addDrop(ModBlocksRegistry.CUSTOM_TUFF_SLAB, block -> silkSlabDrops(block, Blocks.TUFF));
        this.addDrop(ModBlocksRegistry.CUSTOM_SANDSTONE_SLAB, block -> silkSlabDrops(block, Blocks.SANDSTONE));
        this.addDrop(ModBlocksRegistry.CUSTOM_RED_SANDSTONE_SLAB, block -> silkSlabDrops(block, Blocks.RED_SANDSTONE));

        this.addDrop(ModBlocksRegistry.MYCELIUM_SLAB, block -> silkSlabDrops(block, Blocks.DIRT));
        this.addDrop(ModBlocksRegistry.PODZOL_SLAB, block -> silkSlabDrops(block, Blocks.DIRT));
        this.addDrop(ModBlocksRegistry.GRASS_SLAB, block -> silkSlabDrops(block, Blocks.DIRT));
        this.addDrop(ModBlocksRegistry.PATH_SLAB, block -> silkSlabDrops(block, Blocks.DIRT));

        this.addDrop(ModBlocksRegistry.SOUL_SAND_SLAB, block -> silkSlabDrops(block, Blocks.SOUL_SAND));
        this.addDrop(ModBlocksRegistry.SOUL_SOIL_SLAB, block -> silkSlabDrops(block, Blocks.SOUL_SOIL));
        this.addDrop(ModBlocksRegistry.NETHERRACK_SLAB, block -> silkSlabDrops(block, Blocks.NETHERRACK));
        this.addDrop(ModBlocksRegistry.WARPED_NYLIUM_SLAB, block -> silkSlabDrops(block, Blocks.NETHERRACK));
        this.addDrop(ModBlocksRegistry.CRIMSON_NYLIUM_SLAB, block -> silkSlabDrops(block, Blocks.NETHERRACK));
        this.addDrop(ModBlocksRegistry.BASALT_SLAB, block -> silkSlabDrops(block, Blocks.BASALT));
        this.addDrop(ModBlocksRegistry.CUSTOM_BLACKSTONE_SLAB, block -> silkSlabDrops(block, Blocks.BLACKSTONE));
        this.addDrop(ModBlocksRegistry.ENDSTONE_SLAB, block -> silkSlabDrops(block, Blocks.END_STONE));

        this.addDrop(ModBlocksRegistry.PACKED_ICE_SLAB, this::onlySilkSlabDrops);

        this.addDrop(ModBlocksRegistry.SNOW_SLAB, block -> silkSlabDropsParts(block, Items.SNOWBALL));
        this.addDrop(ModBlocksRegistry.CLAY_SLAB, block -> silkSlabDropsParts(block, Items.CLAY_BALL));
        this.addDrop(
                ModBlocksRegistry.GRAVEL_SLAB,
                block -> gravelSlabDrops(block, Blocks.GRAVEL, Items.FLINT)
        );

        this.addDrop(
                ModBlocksRegistry.SNOW_ON_TOP,
                block -> LootTable.builder()
                        .pool(
                                LootPool.builder()
                                        .conditionally(EntityPropertiesLootCondition.create(LootContext.EntityReference.THIS))
                                        .with(
                                                AlternativeEntry.builder(
                                                        AlternativeEntry.builder(
                                                                        SnowBlock.LAYERS.getValues(),
                                                                        integer -> ItemEntry.builder(Items.SNOWBALL)
                                                                                .conditionally(BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(SnowBlock.LAYERS, integer)))
                                                                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create((float)integer.intValue())))
                                                                )
                                                                .conditionally(this.createWithoutSilkTouchCondition()),
                                                        AlternativeEntry.builder(
                                                                SnowBlock.LAYERS.getValues(),
                                                                integer -> integer == 8
                                                                        ? ItemEntry.builder(Blocks.SNOW_BLOCK)
                                                                        : ItemEntry.builder(Blocks.SNOW)
                                                                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create((float)integer.intValue())))
                                                                        .conditionally(BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(SnowBlock.LAYERS, integer)))
                                                        )
                                                )
                                        )
                        )
        );
        this.addDrop(ModBlocksRegistry.POPPY_ON_TOP, block -> this.drops(block, Blocks.POPPY));
        this.addDrop(ModBlocksRegistry.DANDELION_ON_TOP, block -> this.drops(block, Blocks.DANDELION));
        this.addDrop(ModBlocksRegistry.AZURE_BLUET_ON_TOP, block -> this.drops(block, Blocks.AZURE_BLUET));
        this.addDrop(ModBlocksRegistry.CORNFLOWER_ON_TOP, block -> this.drops(block, Blocks.CORNFLOWER));
        this.addDrop(ModBlocksRegistry.BROWN_MUSHROOM_ON_TOP, block -> this.drops(block, Blocks.BROWN_MUSHROOM));
        this.addDrop(ModBlocksRegistry.RED_MUSHROOM_ON_TOP, block -> this.drops(block, Blocks.RED_MUSHROOM));
        this.addDrop(ModBlocksRegistry.SHORT_GRASS_ON_TOP, (block) -> {
            return this.shortPlantDrops(Blocks.SHORT_GRASS);
        });
        this.addDrop(ModBlocksRegistry.FERN_ON_TOP, (block) -> {
            return this.shortPlantDrops(Blocks.FERN);
        });
        this.addDrop(
                ModBlocksRegistry.DEAD_BUSH_ON_TOP,
                block -> this.dropsWithShears(
                        Blocks.DEAD_BUSH,
                        (LootPoolEntry.Builder<?>)this.applyExplosionDecay(
                                block, ItemEntry.builder(Items.STICK).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0F, 2.0F)))
                        )
                )
        );
        this.addDrop(ModBlocksRegistry.SEAGRASS_ON_TOP, block -> this.dropsWithShears(Blocks.SEAGRASS));

    }

    /**
     * Adds a loot table entry that makes the slab drop its base block instead of itself.
     */
    public LootTable.Builder gravelSlabDrops(Block slab, Block gravelDrop, Item flintDrop) {
        RegistryWrapper.Impl<Enchantment> impl = this.registries.getOrThrow(RegistryKeys.ENCHANTMENT);

        return LootTable.builder()
                .pool(LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0F))
                        .with(
                                ItemEntry.builder(slab)
                                        .conditionally(this.createSilkTouchCondition())  // Drops the slab if Silk Touch is used
                                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0F))
                                                .conditionally(BlockStatePropertyLootCondition.builder(slab)
                                                        .properties(StatePredicate.Builder.create().exactMatch(SlabBlock.TYPE, SlabType.DOUBLE))
                                                )
                                        )
                                        .alternatively(
                                                // Drops gravel or flint without Silk Touch
                                                ItemEntry.builder(flintDrop)
                                                        .conditionally(BlockStatePropertyLootCondition.builder(slab).properties(StatePredicate.Builder.create().exactMatch(CustomSlab.GENERATED, true)))
                                                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0F))
                                                                .conditionally(BlockStatePropertyLootCondition.builder(slab)
                                                                        .properties(StatePredicate.Builder.create().exactMatch(SlabBlock.TYPE, SlabType.DOUBLE))
                                                                )
                                                        )
                                                        .conditionally(TableBonusLootCondition.builder(
                                                                impl.getOrThrow(Enchantments.FORTUNE),
                                                                0.1F, 0.14285715F, 0.25F, 1.0F // Fortune levels for flint drops
                                                        ))
                                                        .alternatively(
                                                                ItemEntry.builder(gravelDrop) // Drop gravel if no Fortune for flint
                                                                        .conditionally(BlockStatePropertyLootCondition.builder(slab).properties(StatePredicate.Builder.create().exactMatch(CustomSlab.GENERATED, true)))
                                                                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0F))
                                                                                .conditionally(BlockStatePropertyLootCondition.builder(slab)
                                                                                        .properties(StatePredicate.Builder.create().exactMatch(SlabBlock.TYPE, SlabType.DOUBLE))
                                                                                )
                                                                        )
                                                        )

                                        )
                                        .alternatively(
                                                ItemEntry.builder(slab)
                                                        .conditionally(BlockStatePropertyLootCondition.builder(slab).properties(StatePredicate.Builder.create().exactMatch(CustomSlab.GENERATED, false)))
                                                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0F))
                                                                .conditionally(BlockStatePropertyLootCondition.builder(slab)
                                                                        .properties(StatePredicate.Builder.create().exactMatch(SlabBlock.TYPE, SlabType.DOUBLE))
                                                                )
                                                        )
                                        )
                        )

                );
    }

    public LootTable.Builder silkSlabDrops(Block slab, Block drop) {
        return LootTable.builder()
                .pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1.0F))
                                .with(
                                        ItemEntry.builder(slab)
                                                .conditionally(this.createSilkTouchCondition())  // Silk Touch condition
                                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0F))
                                                        .conditionally(BlockStatePropertyLootCondition.builder(slab)
                                                                .properties(StatePredicate.Builder.create().exactMatch(SlabBlock.TYPE, SlabType.DOUBLE))
                                                        )
                                                )
                                                .alternatively(
                                                        ItemEntry.builder(drop)
                                                                .conditionally(BlockStatePropertyLootCondition.builder(slab).properties(StatePredicate.Builder.create().exactMatch(CustomSlab.GENERATED, true)))
                                                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0F))
                                                                        .conditionally(BlockStatePropertyLootCondition.builder(slab)
                                                                                .properties(StatePredicate.Builder.create().exactMatch(SlabBlock.TYPE, SlabType.DOUBLE))
                                                                        )
                                                                )
                                                )
                                                .alternatively(
                                                        ItemEntry.builder(slab)
                                                                .conditionally(BlockStatePropertyLootCondition.builder(slab).properties(StatePredicate.Builder.create().exactMatch(CustomSlab.GENERATED, false)))
                                                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0F))
                                                                        .conditionally(BlockStatePropertyLootCondition.builder(slab)
                                                                                .properties(StatePredicate.Builder.create().exactMatch(SlabBlock.TYPE, SlabType.DOUBLE))
                                                                        )
                                                                )
                                                )
                                )
                );
    }

    public LootTable.Builder silkSlabDropsParts(Block slab, Item drop) {
        return LootTable.builder()
                .pool(LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0F))
                        .with(
                                ItemEntry.builder(slab)
                                        .conditionally(this.createSilkTouchCondition())  // Silk Touch condition
                                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0F))
                                                .conditionally(BlockStatePropertyLootCondition.builder(slab)
                                                        .properties(StatePredicate.Builder.create().exactMatch(SlabBlock.TYPE, SlabType.DOUBLE))
                                                )
                                        )
                                        .alternatively(
                                                ItemEntry.builder(drop)
                                                        .conditionally(BlockStatePropertyLootCondition.builder(slab).properties(StatePredicate.Builder.create().exactMatch(CustomSlab.GENERATED, true)))
                                                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(8.0F))
                                                                .conditionally(BlockStatePropertyLootCondition.builder(slab)
                                                                        .properties(StatePredicate.Builder.create().exactMatch(SlabBlock.TYPE, SlabType.DOUBLE))
                                                                )
                                                        )
                                                        .conditionally(BlockStatePropertyLootCondition.builder(slab).properties(StatePredicate.Builder.create().exactMatch(CustomSlab.GENERATED, true)))
                                                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(4.0F))
                                                                .conditionally(BlockStatePropertyLootCondition.builder(slab)
                                                                        .properties(StatePredicate.Builder.create().exactMatch(SlabBlock.TYPE, SlabType.BOTTOM))
                                                                )
                                                        )
                                                        .conditionally(BlockStatePropertyLootCondition.builder(slab).properties(StatePredicate.Builder.create().exactMatch(CustomSlab.GENERATED, true)))
                                                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(4.0F))
                                                                .conditionally(BlockStatePropertyLootCondition.builder(slab)
                                                                        .properties(StatePredicate.Builder.create().exactMatch(SlabBlock.TYPE, SlabType.TOP))
                                                                )
                                                        )
                                        )
                                        .alternatively(
                                                ItemEntry.builder(slab)
                                                        .conditionally(BlockStatePropertyLootCondition.builder(slab).properties(StatePredicate.Builder.create().exactMatch(CustomSlab.GENERATED, false)))
                                                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0F))
                                                                .conditionally(BlockStatePropertyLootCondition.builder(slab)
                                                                        .properties(StatePredicate.Builder.create().exactMatch(SlabBlock.TYPE, SlabType.DOUBLE))
                                                                )
                                                        )
                                        )
                        )
                );
    }

    public LootTable.Builder onlySilkSlabDrops(Block slab) {
        return LootTable.builder()
                .pool(LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0F))
                        .with(
                                ItemEntry.builder(slab)
                                        .conditionally(this.createSilkTouchCondition())  // Silk Touch condition
                                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0F))
                                                .conditionally(BlockStatePropertyLootCondition.builder(slab)
                                                        .properties(StatePredicate.Builder.create().exactMatch(SlabBlock.TYPE, SlabType.DOUBLE))
                                                )
                                        )
                                        .alternatively(
                                                ItemEntry.builder(slab)
                                                        .conditionally(BlockStatePropertyLootCondition.builder(slab).properties(StatePredicate.Builder.create().exactMatch(CustomSlab.GENERATED, false)))
                                                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0F))
                                                                .conditionally(BlockStatePropertyLootCondition.builder(slab)
                                                                        .properties(StatePredicate.Builder.create().exactMatch(SlabBlock.TYPE, SlabType.DOUBLE))
                                                                )
                                                        )
                                        )

                        )
                );
    }
}
