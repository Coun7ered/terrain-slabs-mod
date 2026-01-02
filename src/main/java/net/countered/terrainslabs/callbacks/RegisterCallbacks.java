package net.countered.terrainslabs.callbacks;

import net.countered.terrainslabs.block.ModBlocksRegistry;
import net.countered.terrainslabs.block.ModSlabsMap;
import net.countered.terrainslabs.block.customslabs.specialslabs.CustomSlab;
import net.countered.terrainslabs.config.ModConfig;
import net.countered.terrainslabs.persistence.SlabChunkAttachment;
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
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.WorldChunk;

import java.util.*;

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


    public static void init() {
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
            List<BlockPos> botAttachedSlabPositions = worldChunk.getAttached(SlabChunkAttachment.BOT_SLAB_POSITIONS);
            if (botAttachedSlabPositions != null && !botAttachedSlabPositions.isEmpty()) {
                for (BlockPos pos : botAttachedSlabPositions) {
                    placeBottomSlab(worldChunk, pos);
                }
                worldChunk.setAttached(SlabChunkAttachment.BOT_SLAB_POSITIONS, new ArrayList<>());
            }
            List<BlockPos> topAttachedSlabPositions = worldChunk.getAttached(SlabChunkAttachment.TOP_SLAB_POSITIONS);
            if (topAttachedSlabPositions != null && !topAttachedSlabPositions.isEmpty()) {
                for (BlockPos pos : topAttachedSlabPositions) {
                    placeTopSlab(worldChunk, pos);
                }
                worldChunk.setAttached(SlabChunkAttachment.TOP_SLAB_POSITIONS, new ArrayList<>());
            }
        });
    }

    private static final Set<Block> doubleTallPlants = new HashSet<>();
    static {
        doubleTallPlants.add(Blocks.TALL_GRASS);
        doubleTallPlants.add(Blocks.LARGE_FERN);
        doubleTallPlants.add(Blocks.TALL_SEAGRASS);
    }
    private static void placeBottomSlab(WorldChunk worldChunk, BlockPos placePos) {
        BlockPos blockBelowPos = placePos.down();
        BlockPos blockAbovePos = placePos.up();
        BlockState blockAboveState = worldChunk.getBlockState(blockAbovePos);
        BlockState currentBlockState = worldChunk.getBlockState(placePos);
        BlockState blockBelowState = worldChunk.getBlockState(blockBelowPos);

        if (!(currentBlockState.isOf(Blocks.AIR) || currentBlockState.isOf(Blocks.WATER) || currentBlockState.isOf(Blocks.CAVE_AIR) || currentBlockState.isOf(Blocks.VOID_AIR)  || currentBlockState.isOf(Blocks.LAVA))
                && !doubleTallPlants.contains(currentBlockState.getBlock())
                && !ModSlabsMap.ON_TOP_VEGETATION_BLOCKS_MAP.containsKey(currentBlockState.getBlock()) && !currentBlockState.isOf(Blocks.SNOW)) {
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
        int blockBelowY = blockBelowPos.getY();
        int blockAboveY = blockAbovePos.getY();
        int placeY = placePos.getY();

        // check if section is too high
        int sectionIndex = worldChunk.getSectionIndex(blockAboveY);
        if (sectionIndex < 0 || sectionIndex >= worldChunk.getSectionArray().length) {
            return;
        }
        ChunkSection placePosSection = worldChunk.getSection(worldChunk.getSectionIndex(placeY));
        ChunkSection belowPosSection = worldChunk.getSection(worldChunk.getSectionIndex(blockBelowY));
        ChunkSection abovePosSection = worldChunk.getSection(sectionIndex);

        // remove double tall plants
        if (doubleTallPlants.contains(currentBlockState.getBlock())) {
            if (currentBlockState.isOf(Blocks.TALL_SEAGRASS)) {
                abovePosSection.setBlockState(blockAbovePos.getX() & 15, blockAbovePos.getY() & 15, blockAbovePos.getZ() & 15,  Blocks.WATER.getDefaultState());
                blockAboveState = Blocks.WATER.getDefaultState();
            }
            else {
                abovePosSection.setBlockState(blockAbovePos.getX() & 15, blockAbovePos.getY() & 15, blockAbovePos.getZ() & 15,  Blocks.AIR.getDefaultState());
            }
        }
        // Handle grass slab special case by converting grass to dirt before placing the slab
        if (SlabFeatureLogic.SOIL_SLAB_BLOCKS.contains(slabState.getBlock())) {
            belowPosSection.setBlockState(blockBelowPos.getX() & 15, blockBelowPos.getY() & 15, blockBelowPos.getZ() & 15,  Blocks.DIRT.getDefaultState());
        }
        if (slabState.isOf(ModBlocksRegistry.WARPED_NYLIUM_SLAB) || slabState.isOf(ModBlocksRegistry.CRIMSON_NYLIUM_SLAB)) {
            belowPosSection.setBlockState(blockBelowPos.getX() & 15, blockBelowPos.getY() & 15, blockBelowPos.getZ() & 15,  Blocks.NETHERRACK.getDefaultState());
        }
        slabState = updateBottomWaterloggedState(currentBlockState, blockAboveState, slabState);

        // place vegetation / snow on top
        if (ModConfig.enableVegetationOnSlabs) {
            placeVegetationOnTop(abovePosSection, currentBlockState, blockAboveState, blockAbovePos);
        }
        if (currentBlockState.isOf(Blocks.SNOW)) {
            if (ModConfig.enableSnowOnSlabs) {
                worldChunk.setBlockState(blockAbovePos, ModBlocksRegistry.SNOW_ON_TOP.getDefaultState(), 0);
                if (SlabFeatureLogic.SOIL_SLAB_BLOCKS.contains(slabState.getBlock()) && !slabState.isOf(ModBlocksRegistry.PATH_SLAB)) {
                    slabState = slabState.with(Properties.SNOWY, true);
                }
            } else {
                slabState = ModBlocksRegistry.SNOW_SLAB.getDefaultState();
            }
        }
        worldChunk.setBlockState(placePos,  slabState.with(CustomSlab.GENERATED, true), 0);
    }

    private static void placeTopSlab(WorldChunk worldChunk, BlockPos placePos) {
        BlockState blockAboveState = worldChunk.getBlockState(placePos.up());

        // Retrieve the slab type based on the block below the current position
        BlockState slabState = ModSlabsMap.getSlabForBlock(blockAboveState.getBlock()).getDefaultState();

        if (slabState.getBlock().equals(Blocks.AIR)) {
            /* DEBUG
            System.out.println(blockBelowState);
            System.out.println(currentBlockState);
             */
            return;
        }
        if (SlabFeatureLogic.SOIL_SLAB_BLOCKS.contains(slabState.getBlock())) {
            slabState = ModBlocksRegistry.DIRT_SLAB.getDefaultState();
        }
        if (slabState.isOf(ModBlocksRegistry.WARPED_NYLIUM_SLAB) || slabState.isOf(ModBlocksRegistry.CRIMSON_NYLIUM_SLAB)) {
            slabState = ModBlocksRegistry.NETHERRACK_SLAB.getDefaultState();
        }
        slabState = updateTopWaterloggedState(worldChunk, placePos, slabState);
        ChunkSection section = worldChunk.getSection(worldChunk.getSectionIndex(placePos.getY()));
        section.setBlockState(placePos.getX() & 15, placePos.getY() & 15, placePos.getZ() & 15,  slabState.with(CustomSlab.GENERATED, true).with(Properties.SLAB_TYPE, SlabType.TOP));
    }

    private static void placeVegetationOnTop(ChunkSection abovePosSection, BlockState currentBlockState, BlockState blockAboveState, BlockPos blockAbovePos) {
        if (ModSlabsMap.ON_TOP_VEGETATION_BLOCKS_MAP.containsKey(currentBlockState.getBlock())) {
            if (!(currentBlockState.getBlock().equals(Blocks.SEAGRASS) && !blockAboveState.getBlock().equals(Blocks.WATER))) {
                abovePosSection.setBlockState(blockAbovePos.getX() & 15, blockAbovePos.getY() & 15, blockAbovePos.getZ() & 15, ModSlabsMap.ON_TOP_VEGETATION_BLOCKS_MAP.get(currentBlockState.getBlock()).getDefaultState());
            }
        }
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
            BlockPos checkPos = currentPos.offset(direction);
            ChunkPos chunkPos = new ChunkPos(checkPos);

            // Check if the neighbor or the block above contains water to set the waterlogged property
            if (worldChunk.getBlockState(currentPos.offset(direction)).isOf(Blocks.WATER) && worldChunk.getPos().equals(chunkPos)) {
                return slabState.with(Properties.WATERLOGGED, true);
            }
        }
        return slabState;
    }
}
