package net.countered.terrainslabs.callbacks;

import net.countered.terrainslabs.block.ModBlocksRegistry;
import net.countered.terrainslabs.block.ModSlabsMap;
import net.countered.terrainslabs.block.customslabs.specialslabs.CustomSlab;
import net.countered.terrainslabs.worldgen.slabfeature.SlabFeatureLogic;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Pair;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.WorldChunk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterCallbacks {
    private static final Map<Item, Block> VEGETATION_ON_TOP_ITEMS = new HashMap<>();

    static {
        VEGETATION_ON_TOP_ITEMS.put(Items.POPPY, ModBlocksRegistry.POPPY_ON_TOP);
        VEGETATION_ON_TOP_ITEMS.put(Items.DANDELION, ModBlocksRegistry.DANDELION_ON_TOP);
        VEGETATION_ON_TOP_ITEMS.put(Items.AZURE_BLUET, ModBlocksRegistry.AZURE_BLUET_ON_TOP);
        VEGETATION_ON_TOP_ITEMS.put(Items.CORNFLOWER, ModBlocksRegistry.CORNFLOWER_ON_TOP);
        VEGETATION_ON_TOP_ITEMS.put(Items.SHORT_GRASS, ModBlocksRegistry.SHORT_GRASS_ON_TOP);
        VEGETATION_ON_TOP_ITEMS.put(Items.FERN, ModBlocksRegistry.FERN_ON_TOP);
        VEGETATION_ON_TOP_ITEMS.put(Items.DEAD_BUSH, ModBlocksRegistry.DEAD_BUSH_ON_TOP);
        VEGETATION_ON_TOP_ITEMS.put(Items.BROWN_MUSHROOM, ModBlocksRegistry.BROWN_MUSHROOM_ON_TOP);
        VEGETATION_ON_TOP_ITEMS.put(Items.RED_MUSHROOM, ModBlocksRegistry.RED_MUSHROOM_ON_TOP);
        VEGETATION_ON_TOP_ITEMS.put(Items.SEAGRASS, ModBlocksRegistry.SEAGRASS_ON_TOP);
    }


    public static void registerCallbacks() {
        registerPlaceOnTopCallback();
        registerSlabPlacementCallback();
    }

    private static void registerPlaceOnTopCallback(){
        UseBlockCallback.EVENT.register((PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) -> {
            ItemStack item = player.getStackInHand(hand);

            if (item.getItem() == Items.SNOW) {
                BlockPos blockPos = hitResult.getBlockPos().offset(hitResult.getSide());

                // Ensure the block can be replaced and there's air at the target position
                if ((world.getBlockState(blockPos).isAir() || world.getBlockState(blockPos).getBlock() == ModBlocksRegistry.SNOW_ON_TOP)
                        && world.getBlockState(blockPos.down()).getBlock() instanceof SlabBlock && world.getBlockState(blockPos.down()).get(Properties.SLAB_TYPE).equals(SlabType.BOTTOM)) {
                    // Replace with custom snow slab
                    int currentLayers = world.getBlockState(blockPos).getBlock() instanceof SnowBlock
                            ? world.getBlockState(blockPos).get(SnowBlock.LAYERS)
                            : 0;

                    // If snow layers can be increased, increase by 1 layer
                    if (currentLayers < SnowBlock.MAX_LAYERS) {
                        world.setBlockState(blockPos, ModBlocksRegistry.SNOW_ON_TOP.getDefaultState()
                                .with(SnowBlock.LAYERS, currentLayers + 1));

                        // Play placement sound and consume one snow slab
                        world.playSound(player, blockPos, SoundEvents.BLOCK_SNOW_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        if (!player.isCreative()) {
                            item.decrement(1);
                        }
                        return ActionResult.SUCCESS;
                    }
                }
            }
            else if (VEGETATION_ON_TOP_ITEMS.containsKey(item.getItem())) {
                BlockPos blockPos = hitResult.getBlockPos().offset(hitResult.getSide());
                // Ensure the block can be replaced and there's air at the target position
                if (world.getBlockState(blockPos).isAir() && world.getBlockState(blockPos.down()).getBlock() instanceof SlabBlock
                        && world.getBlockState(blockPos.down()).get(Properties.SLAB_TYPE).equals(SlabType.BOTTOM) && !item.isOf(Items.SEAGRASS)) {

                    world.setBlockState(blockPos, VEGETATION_ON_TOP_ITEMS.get(item.getItem()).getDefaultState(), 0);
                    world.playSound(player, blockPos, SoundEvents.BLOCK_GRASS_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);

                    if (!player.isCreative()) {
                        item.decrement(1);
                    }
                    return ActionResult.SUCCESS;
                }
                else if (world.getBlockState(blockPos).isOf(Blocks.WATER) && world.getBlockState(blockPos.down()).getBlock() instanceof SlabBlock && world.getBlockState(blockPos.down()).get(Properties.SLAB_TYPE).equals(SlabType.BOTTOM)) {
                    if (item.getItem().equals(Items.SEAGRASS)) {
                        world.setBlockState(blockPos, ModBlocksRegistry.SEAGRASS_ON_TOP.getDefaultState(), 0);

                        world.playSound(player, blockPos, SoundEvents.BLOCK_WET_GRASS_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        if (!player.isCreative()) {
                            item.decrement(1);
                        }
                        return ActionResult.SUCCESS;
                    }
                }
            }
            // Pass to allow normal behavior if conditions are not met
            return ActionResult.PASS;
        });
    }

    private static void registerSlabPlacementCallback() {
        ServerChunkEvents.CHUNK_GENERATE.register((serverWorld, worldChunk) -> {
            Map<ChunkPos, Pair<List<BlockPos>, List<BlockPos>>> chunkSlabPlacementPositions = SlabFeatureLogic.chunkSlabPlacementPositions;
            if (chunkSlabPlacementPositions == null) return;
            ChunkPos chunkPos = worldChunk.getPos();
            Pair<List<BlockPos>, List<BlockPos>> placementPositions = SlabFeatureLogic.chunkSlabPlacementPositions.get(chunkPos);
            if (placementPositions == null) return;
            for (BlockPos placePos : placementPositions.getLeft()) {
                placeBottomSlab(worldChunk, placePos);
            }
            for (BlockPos placePos : placementPositions.getRight()) {
                placeTopSlab(worldChunk, placePos);
            }
            chunkSlabPlacementPositions.remove(chunkPos);
        });
    }

    private static void placeTopSlab(WorldChunk worldChunk, BlockPos placePos) {
        BlockState currentBlockState = worldChunk.getBlockState(placePos);

        // Retrieve the slab type based on the block below the current position
        BlockState slabState = ModSlabsMap.getSlabForBlock(currentBlockState.getBlock()).getDefaultState();

        if (slabState.getBlock().equals(Blocks.AIR)) {
            /* DEBUG
            System.out.println(blockBelowState);
            System.out.println(currentBlockState);
             */
            return;
        }

        slabState = updateTopWaterloggedState(worldChunk, placePos, slabState);
        ChunkSection section = worldChunk.getSection(worldChunk.getSectionIndex(placePos.getY()));
        section.setBlockState(placePos.getX() & 15, placePos.getY() & 15, placePos.getZ() & 15,  slabState.with(CustomSlab.GENERATED, true).with(Properties.SLAB_TYPE, SlabType.TOP));
    }

    private static void placeBottomSlab(WorldChunk worldChunk, BlockPos placePos) {
        BlockPos blockBelowPos = placePos.down();
        BlockPos blockAbovePos = placePos.up();
        BlockState blockAboveState = worldChunk.getBlockState(blockAbovePos);
        BlockState currentBlockState = worldChunk.getBlockState(placePos);
        BlockState blockBelowState = worldChunk.getBlockState(blockBelowPos);

        if (!(currentBlockState.isOf(Blocks.AIR) || currentBlockState.isOf(Blocks.WATER) || currentBlockState.isOf(Blocks.LAVA)) && !ModSlabsMap.ON_TOP_SLAB_BLOCKS_MAP.containsKey(currentBlockState.getBlock())) {
            return;
        }
        // Retrieve the slab type based on the block below the current position
        BlockState slabState = ModSlabsMap.getSlabForBlock(blockBelowState.getBlock()).getDefaultState();

        if (slabState.getBlock().equals(Blocks.AIR)) {
            /* DEBUG
            System.out.println(blockBelowState);
            System.out.println(currentBlockState);
             */
            return;
        }
        // Handle grass slab special case by converting grass to dirt before placing the slab
        if (SlabFeatureLogic.SOIL_SLAB_BLOCKS.contains(slabState.getBlock())) {
            worldChunk.setBlockState(blockBelowPos, Blocks.DIRT.getDefaultState(), false);
        }
        if (slabState.isOf(ModBlocksRegistry.WARPED_NYLIUM_SLAB) || slabState.isOf(ModBlocksRegistry.CRIMSON_NYLIUM_SLAB)) {
            worldChunk.setBlockState(blockBelowPos, Blocks.NETHERRACK.getDefaultState(), false);
        }
        slabState = updateBottomWaterloggedState(currentBlockState, blockAboveState, slabState);

        if (ModSlabsMap.ON_TOP_SLAB_BLOCKS_MAP.containsKey(currentBlockState.getBlock())){
            if (!(currentBlockState.isOf(Blocks.SEAGRASS) && blockAboveState.isOf(Blocks.AIR))) {
                worldChunk.setBlockState(blockAbovePos, ModSlabsMap.ON_TOP_SLAB_BLOCKS_MAP.get(currentBlockState.getBlock()).getDefaultState(), false);
            }
            if (currentBlockState.isOf(Blocks.SNOW)){
                if (SlabFeatureLogic.SOIL_SLAB_BLOCKS.contains(slabState.getBlock())){
                    worldChunk.setBlockState(placePos, slabState.with(CustomSlab.GENERATED, true).with(Properties.SNOWY, true), false);
                    return;
                }
            }
        }
        ChunkSection section = worldChunk.getSection(worldChunk.getSectionIndex(placePos.getY()));
        section.setBlockState(placePos.getX() & 15, placePos.getY() & 15, placePos.getZ() & 15,  slabState.with(CustomSlab.GENERATED, true));
    }

    private static BlockState updateBottomWaterloggedState(BlockState currentBlockState, BlockState blockAboveState, BlockState slabState) {
        if (slabState.contains(Properties.WATERLOGGED)) {
            if (currentBlockState.isOf(Blocks.WATER) || blockAboveState.isOf(Blocks.WATER) || currentBlockState.isOf(Blocks.SEAGRASS)) {
                return slabState.with(Properties.WATERLOGGED, true);
            }
        }
        return slabState;
    }

    private static BlockState updateTopWaterloggedState(WorldChunk worldChunk, BlockPos currentPos, BlockState slabState) {
        for (Direction direction : Direction.Type.HORIZONTAL) {
            // Check if the neighbor or the block above contains water to set the waterlogged property
            if (worldChunk.getBlockState(currentPos.offset(direction)).isOf(Blocks.WATER)) {
                return slabState.with(Properties.WATERLOGGED, true);
            }
        }
        return slabState;
    }


}
