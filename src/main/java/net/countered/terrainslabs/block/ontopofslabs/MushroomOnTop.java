package net.countered.terrainslabs.block.ontopofslabs;

import net.countered.terrainslabs.block.interfaces.IBlockCopyFabric;
import net.minecraft.block.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class MushroomOnTop extends MushroomPlantBlock implements IBlockCopyFabric {
    private final Block originalBlock;
    protected static final VoxelShape SHAPE = Block.createCuboidShape(5.0, -8.0, 5.0, 11.0, -2.0, 11.0);

    public MushroomOnTop(Block originalBlock, RegistryKey<ConfiguredFeature<?, ?>> featureKey) {
        super( AbstractBlock.Settings.copy( originalBlock ), featureKey );
        this.originalBlock = originalBlock;
    }


    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.getBlock() instanceof SlabBlock;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        BlockState blockState = world.getBlockState(blockPos);
        return this.canPlantOnTop(blockState, world, blockPos);
    }

    @Override
    public Block getOriginBlock() {
        return originalBlock;
    }

    @Override
    public BlockCopyType getCopyType() {
        return BlockCopyType.ON_TOP;
    }
}
