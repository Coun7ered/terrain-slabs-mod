package net.countered.terrainslabs.callbacks;

import net.countered.terrainslabs.block.ModBlockTags;
import net.countered.terrainslabs.block.ModBlocksRegistry;
import net.countered.terrainslabs.block.ModSlabsMap;
import net.countered.terrainslabs.block.customslabs.specialslabs.CustomSlab;
import net.countered.terrainslabs.config.MyModConfig;
import net.countered.terrainslabs.persistence.SlabChunkAttachment;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
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
        VEGETATION_ON_TOP_ITEMS.put(Items.GRASS, ModBlocksRegistry.SHORT_GRASS_ON_TOP);
        VEGETATION_ON_TOP_ITEMS.put(Items.FERN, ModBlocksRegistry.FERN_ON_TOP);
        VEGETATION_ON_TOP_ITEMS.put(Items.DEAD_BUSH, ModBlocksRegistry.DEAD_BUSH_ON_TOP);
        VEGETATION_ON_TOP_ITEMS.put(Items.BROWN_MUSHROOM, ModBlocksRegistry.BROWN_MUSHROOM_ON_TOP);
        VEGETATION_ON_TOP_ITEMS.put(Items.RED_MUSHROOM, ModBlocksRegistry.RED_MUSHROOM_ON_TOP);
        VEGETATION_ON_TOP_ITEMS.put(Items.SEAGRASS, ModBlocksRegistry.SEAGRASS_ON_TOP);
    }

    public static void putVegetaitonOnTopItemFromString( String keyMod, String keyName, String valueMod, String valueName ) {
        Item item = Registries.ITEM.get( Identifier.of( keyMod, keyName ) );
        Block onTopBlock = Registries.BLOCK.get( Identifier.of( valueMod, valueName ) );
        VEGETATION_ON_TOP_ITEMS.put( item, onTopBlock );
    }

    public static void registerCallbacks() {
        registerPlaceOnTopCallback();
        registerSlabPlacementCallback();
    }

    private static void registerPlaceOnTopCallback(){
        UseBlockCallback.EVENT.register((PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) -> {
            ItemStack item = player.getStackInHand(hand);

            BlockPos blockPos = hitResult.getBlockPos().offset(hitResult.getSide());
            if ( !( world.getBlockState(blockPos.down()).getBlock() instanceof SlabBlock
                    && world.getBlockState(blockPos.down()).get(Properties.SLAB_TYPE).equals(SlabType.BOTTOM) ) )
            {
                return ActionResult.PASS;
            }

            if (item.getItem() == Items.SNOW) {
                // Ensure the block can be replaced and there's air at the target position
                if ((world.getBlockState(blockPos).isAir() || world.getBlockState(blockPos).getBlock() == ModBlocksRegistry.SNOW_ON_TOP)) {
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
                return ActionResult.PASS;
            }

            if ( !VEGETATION_ON_TOP_ITEMS.containsKey(item.getItem()) ) {
                return ActionResult.PASS;
            }

            BlockState vegetationState = VEGETATION_ON_TOP_ITEMS.get(item.getItem()).getDefaultState();
            // Ensure the block can be replaced and there's air at the target position
            if (world.getBlockState(blockPos).isAir() && !vegetationState.isIn( ModBlockTags.REQUIRES_WATER ) ) {

                Collection<Property<?>> properties = vegetationState.getProperties();
                if ( properties.contains( Properties.HORIZONTAL_FACING ) ) {
                    vegetationState = vegetationState.with( Properties.HORIZONTAL_FACING, player.getHorizontalFacing().getOpposite() );
                }

                world.setBlockState(blockPos, vegetationState, 0);
                world.playSound(player, blockPos, vegetationState.getSoundGroup().getPlaceSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);

                if (!player.isCreative()) {
                    item.decrement(1);
                }
                return ActionResult.SUCCESS;
            }
            else if (world.getBlockState(blockPos).isOf(Blocks.WATER) ) {
                boolean canBeWaterlogged = vegetationState.getProperties().contains( Properties.WATERLOGGED );
                if ( !( canBeWaterlogged || vegetationState.isIn( ModBlockTags.REQUIRES_WATER )) ) {
                    return ActionResult.PASS;
                }

                world.setBlockState(blockPos, canBeWaterlogged ? vegetationState.with( Properties.WATERLOGGED, true ) : vegetationState, 0);
                world.playSound(player, blockPos, vegetationState.getSoundGroup().getPlaceSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
                if (!player.isCreative()) {
                    item.decrement(1);
                }
                return ActionResult.SUCCESS;
            }

            // Pass to allow normal behavior if conditions are not met
            return ActionResult.PASS;
        });
    }

    private static void registerSlabPlacementCallback() {
        ServerChunkEvents.CHUNK_LOAD.register((serverWorld, worldChunk) -> {
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

    private static void placeBottomSlab(WorldChunk worldChunk, BlockPos placePos) {
        BlockPos blockBelowPos = placePos.down();
        BlockPos blockAbovePos = placePos.up();
        BlockState blockAboveState = worldChunk.getBlockState(blockAbovePos);
        BlockState currentBlockState = worldChunk.getBlockState(placePos);
        BlockState blockBelowState = worldChunk.getBlockState(blockBelowPos);

        if ( bottomSlabForbidden( currentBlockState ) ) {
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

        // check if section is too high
        int sectionIndex = worldChunk.getSectionIndex(blockAbovePos.getY());
        if (sectionIndex < 0 || sectionIndex >= worldChunk.getSectionArray().length) {
            return;
        }

        // remove double tall plants
        if (currentBlockState.isIn(ModBlockTags.DOUBLE_TALL_PLANTS) || currentBlockState.isIn(BlockTags.TALL_FLOWERS)) {
            if (currentBlockState.isOf(Blocks.TALL_SEAGRASS)) {
                setBlockWithoutUpdates(worldChunk, blockAbovePos, Blocks.WATER.getDefaultState());
                blockAboveState = Blocks.WATER.getDefaultState();
            }
            else {
                setBlockWithSection( worldChunk, blockAbovePos, Blocks.AIR.getDefaultState() );
                if (MyModConfig.enableVegetationOnSlabs) {
                    placeVegetationOnTop(worldChunk, currentBlockState, blockAboveState, blockAbovePos);
                }
            }
        }
        // Handle grass slab special case by converting grass to dirt before placing the slab
        if (ModSlabsMap.BLOCK_BELOW_REPLACEMENT_MAP.containsKey(slabState.getBlock())) {
            setBlockWithSection( worldChunk, blockBelowPos, ModSlabsMap.BLOCK_BELOW_REPLACEMENT_MAP.get(slabState.getBlock()).getDefaultState() );
        }
        slabState = updateBottomWaterloggedState(currentBlockState, blockAboveState, slabState);

        // place vegetation / snow on top
        if (MyModConfig.enableVegetationOnSlabs) {
            placeVegetationOnTop(worldChunk, currentBlockState, blockAboveState, blockAbovePos);
        }
        if (currentBlockState.isOf(Blocks.SNOW)) {
            if (MyModConfig.enableSnowOnSlabs) {
                BlockState snowState = ModBlocksRegistry.SNOW_ON_TOP.getDefaultState();
                setBlockWithoutUpdates(worldChunk, blockAbovePos, snowState);
                if (slabState.getProperties().contains(Properties.SNOWY)) {
                    slabState = slabState.with(Properties.SNOWY, true);
                }
            } else {
                slabState = ModBlocksRegistry.SNOW_SLAB.getDefaultState();
            }
        }
        setBlockWithoutUpdates(worldChunk, placePos,  slabState.with(CustomSlab.GENERATED, true));
    }

    private static boolean bottomSlabForbidden( BlockState currentBlockState ) {
        return !(currentBlockState.isOf(Blocks.AIR) || currentBlockState.isOf(Blocks.WATER) || currentBlockState.isOf(Blocks.CAVE_AIR) || currentBlockState.isOf(Blocks.VOID_AIR)  || currentBlockState.isOf(Blocks.LAVA))
                && !currentBlockState.isIn(ModBlockTags.DOUBLE_TALL_PLANTS) && !currentBlockState.isIn(BlockTags.TALL_FLOWERS)
                && !ModSlabsMap.ON_TOP_VEGETATION_BLOCKS_MAP.containsKey(currentBlockState.getBlock()) && !currentBlockState.isOf(Blocks.SNOW)
                && !currentBlockState.isIn(BlockTags.REPLACEABLE) && !currentBlockState.isIn(BlockTags.REPLACEABLE_BY_TREES);
    }

    static void setBlockWithoutUpdates(WorldChunk chunk, BlockPos pos, BlockState state) {
        int y = pos.getY();
        int lx = pos.getX() & 15;
        int lz = pos.getZ() & 15;
        int ly = y & 15;

        ChunkSection section = chunk.getSection(chunk.getSectionIndex(y));

        BlockState previous = section.setBlockState(lx, ly, lz, state);
        if (previous == state) {
            return;
        }
        for (Heightmap.Type type : Heightmap.Type.values()) {
            Heightmap hm = chunk.getHeightmap(type);
            if (hm != null) {
                hm.trackUpdate(lx, y, lz, state);
            }
        }
        chunk.setNeedsSaving(true);
    }

    static void setBlockWithSection( WorldChunk chunk, BlockPos pos, BlockState state ) {
        int y = pos.getY();
        int lx = pos.getX() & 15;
        int lz = pos.getZ() & 15;
        int ly = y & 15;

        ChunkSection section = chunk.getSection(chunk.getSectionIndex(y));

        section.setBlockState(lx, ly, lz, state);
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
        if (ModSlabsMap.TOP_SLAB_REPLACEMENT_MAP.containsKey((slabState.getBlock()))) {
            slabState = ModSlabsMap.TOP_SLAB_REPLACEMENT_MAP.get(slabState.getBlock()).getDefaultState();
        }
        slabState = updateTopWaterloggedState(worldChunk, placePos, slabState);
        setBlockWithSection(worldChunk, placePos, slabState.with(CustomSlab.GENERATED, true).with(Properties.SLAB_TYPE, SlabType.TOP));
    }

    private static void placeVegetationOnTop(WorldChunk worldChunk, BlockState currentBlockState, BlockState blockAboveState, BlockPos blockAbovePos) {
        if ( !ModSlabsMap.ON_TOP_VEGETATION_BLOCKS_MAP.containsKey(currentBlockState.getBlock()) ) {
            return;
        }

        BlockState vegetationState = ModSlabsMap.ON_TOP_VEGETATION_BLOCKS_MAP.get(currentBlockState.getBlock()).getStateWithProperties(currentBlockState);
        // Handle Water
        boolean canBeWaterlogged = vegetationState.getProperties().contains( Properties.WATERLOGGED );
        boolean blockAboveIsWater = blockAboveState.getBlock().equals(Blocks.WATER );
        if ( blockAboveIsWater && !( canBeWaterlogged || vegetationState.isIn( ModBlockTags.REQUIRES_WATER ))
            || !blockAboveIsWater && vegetationState.isIn( ModBlockTags.REQUIRES_WATER ))
        {
            return;
        }


        vegetationState = canBeWaterlogged ? vegetationState.with( Properties.WATERLOGGED, blockAboveIsWater ) : vegetationState;
        setBlockWithSection( worldChunk, blockAbovePos, vegetationState );
    }

    private static BlockState updateBottomWaterloggedState(BlockState currentBlockState, BlockState blockAboveState, BlockState slabState) {
        if (slabState.contains(Properties.WATERLOGGED)) {
            if (currentBlockState.isOf(Blocks.WATER) || blockAboveState.isOf(Blocks.WATER)
                    || currentBlockState.getFluidState() == Fluids.WATER.getStill(false) )
            {
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
