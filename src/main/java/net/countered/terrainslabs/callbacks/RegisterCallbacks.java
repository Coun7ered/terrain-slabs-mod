package net.countered.terrainslabs.callbacks;

import net.countered.terrainslabs.block.ModBlockTags;
import net.countered.terrainslabs.block.ModBlocksRegistry;
import net.countered.terrainslabs.block.ModSlabsMap;
import net.countered.terrainslabs.block.customslabs.specialslabs.CustomSlab;
import net.countered.terrainslabs.block.interfaces.BlockCopyWrapper;
import net.countered.terrainslabs.block.interfaces.IBlockCopy;
import net.countered.terrainslabs.mixinProxy.PlantBlockProxy;
import net.countered.terrainslabs.config.MyModConfig;
import net.countered.terrainslabs.persistence.SlabChunkAttachment;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
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

    @SuppressWarnings("unused")
    public static void putVegetationOnTopItemFromString(String keyMod, String keyName, String valueMod, String valueName ) {
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

            // TODO: Allow plants to be placed on a double slab as normal?
            BlockPos blockPos = hitResult.getBlockPos().offset(hitResult.getSide());
            BlockState blockBelowState = world.getBlockState(blockPos.down());
            if ( !( blockBelowState.getBlock() instanceof SlabBlock
                    && blockBelowState.get(Properties.SLAB_TYPE).equals(SlabType.BOTTOM) ) )
            {
                return ActionResult.PASS;
            }

            BlockState currentBlockState = world.getBlockState(blockPos);
            boolean blockPosIsAir = currentBlockState.isAir() || currentBlockState.isIn( BlockTags.REPLACEABLE );
            if (item.getItem() == Items.SNOW) {
                // Ensure the block can be replaced and there's air at the target position
                if ((blockPosIsAir || currentBlockState.getBlock() == ModBlocksRegistry.SNOW_ON_TOP)) {
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
                return ActionResult.PASS; // Cancel if is not on top vegetation
            }

            BlockState vegetationState = VEGETATION_ON_TOP_ITEMS.get(item.getItem()).getDefaultState();
            boolean canBeWaterlogged = vegetationState.getProperties().contains( Properties.WATERLOGGED );
            boolean blockPosIsWater = currentBlockState.isOf(Blocks.WATER);
            if ( lacksValidFluidStateAndReplacement( vegetationState, canBeWaterlogged, blockPosIsWater, blockPosIsAir )
                    || !canPlaceOnTop( vegetationState, blockBelowState, world, blockPos ) )
            {
                return ActionResult.PASS; // Cancel if original vegetation is not compatible with original block
            }

            Collection<Property<?>> properties = vegetationState.getProperties();
            if ( properties.contains( Properties.HORIZONTAL_FACING ) ) {
                vegetationState = vegetationState.with( Properties.HORIZONTAL_FACING, player.getHorizontalFacing().getOpposite() );
            }

            if ( vegetationState.getBlock() instanceof TallPlantBlock ) {
                BlockPos topPos = blockPos.up();
                BlockState topState = world.getBlockState( topPos );
                boolean topBlockIsWater = topState.getBlock().equals( Blocks.WATER );
                if ( lacksValidFluidStateAndReplacement( topState, canBeWaterlogged, topBlockIsWater, topState.isAir() ) ) {
                    return ActionResult.PASS;
                }
            }

            world.setBlockState(blockPos, withWaterloggedState( vegetationState, canBeWaterlogged, blockPosIsWater ), Block.NOTIFY_NEIGHBORS);
            world.playSound(player, blockPos, vegetationState.getSoundGroup().getPlaceSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
            if ( vegetationState.getBlock() instanceof TallPlantBlock ) {
                ( vegetationState.getBlock()).onPlaced( world, blockPos, vegetationState, player, item );
            }

            if (!player.isCreative()) {
                item.decrement(1);
            }
            return ActionResult.SUCCESS;

        });
    }

    static boolean canPlaceOnTop( BlockState vegetationState, BlockState blockBelowState, World world, BlockPos pos ) {
        if ( vegetationState.getBlock() instanceof IBlockCopy copy && copy.hasPlacementRule( "custom" ) ) {
            return vegetationState.canPlaceAt( world, pos );
        }

        if ( vegetationState.getBlock() instanceof PlantBlock ) {
            if ( !( blockBelowState.getBlock() instanceof IBlockCopy ) ) {
                return false;
            }

            Block originPlant = new BlockCopyWrapper( (IBlockCopy) vegetationState.getBlock() ).getOriginBlock();
            Block originBlock = new BlockCopyWrapper( (IBlockCopy) blockBelowState.getBlock() ).getOriginBlock();
            return ((PlantBlockProxy) originPlant).terrain_slabs_mod$canPlantOnTopProxy(
                    originBlock.getStateWithProperties(blockBelowState), world, pos);
        }
        return true;
    }

    @SuppressWarnings("UnstableApiUsage")
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
                blockAboveState = Blocks.AIR.getDefaultState();
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
        boolean canBeWaterlogged = vegetationState.getProperties().contains( Properties.WATERLOGGED );
        boolean blockAboveIsWater = blockAboveState.getBlock().equals(Blocks.WATER );
        if ( lacksValidFluidStateAndReplacement( vegetationState, canBeWaterlogged, blockAboveIsWater, blockAboveState.isAir() )) {
            return;
        }

        if ( vegetationState.getBlock() instanceof TallPlantBlock ) {
            BlockPos topPos = blockAbovePos.up();
            BlockState topState = worldChunk.getBlockState( topPos );
            boolean topBlockIsWater = topState.getBlock().equals( Blocks.WATER );
            if ( lacksValidFluidStateAndReplacement( topState, canBeWaterlogged, topBlockIsWater, topState.isAir()) ) {
                return;
            }

            BlockState topVegeState = vegetationState.with( Properties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER );
            setBlockWithSection( worldChunk, topPos, withWaterloggedState( topVegeState, canBeWaterlogged, topBlockIsWater ) );
        }

        setBlockWithSection( worldChunk, blockAbovePos, withWaterloggedState( vegetationState, canBeWaterlogged, blockAboveIsWater ) );
    }

    // Checks is position is available and valid fluid state is possible
    private static boolean lacksValidFluidStateAndReplacement(BlockState vegetationState, boolean canBeWaterlogged, boolean blockIsWater, boolean blockIsAir ) {
        return !( blockIsWater || blockIsAir )
                || blockIsWater && !( canBeWaterlogged || vegetationState.isIn( ModBlockTags.REQUIRES_WATER ))
                || !blockIsWater && vegetationState.isIn( ModBlockTags.REQUIRES_WATER );
    }
    private static BlockState withWaterloggedState(BlockState vegetationState, boolean canBeWaterlogged, boolean blockIsWater ) {
        return canBeWaterlogged ? vegetationState.with( Properties.WATERLOGGED, blockIsWater ) : vegetationState;
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
