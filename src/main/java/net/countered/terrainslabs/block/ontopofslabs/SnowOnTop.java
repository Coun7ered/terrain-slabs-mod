package net.countered.terrainslabs.block.ontopofslabs;

import net.countered.terrainslabs.block.interfaces.IBlockCopyFabric;
import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

import static net.minecraft.block.SlabBlock.TYPE;

public class SnowOnTop extends SnowBlock implements IBlockCopyFabric {
    private final Block originalBlock;

    public SnowOnTop(Block originalBlock ) {
        super( AbstractBlock.Settings.copy( originalBlock ) );
        this.originalBlock = originalBlock;
    }

    protected static final VoxelShape[] LAYERS_TO_SHAPE = new VoxelShape[]{
            VoxelShapes.empty(),
            Block.createCuboidShape(0.0, -8.0, 0.0, 16.0, -6.0,  16.0),
            Block.createCuboidShape(0.0, -8.0, 0.0, 16.0, -4.0,  16.0),
            Block.createCuboidShape(0.0, -8.0, 0.0, 16.0, -2.0,  16.0),
            Block.createCuboidShape(0.0, -8.0, 0.0, 16.0, 0.0,  16.0),
            Block.createCuboidShape(0.0, -8.0, 0.0, 16.0, 2.0, 16.0),
            Block.createCuboidShape(0.0, -8.0, 0.0, 16.0, 4.0, 16.0),
            Block.createCuboidShape(0.0, -8.0, 0.0, 16.0, 6.0, 16.0),
            Block.createCuboidShape(0.0, -8.0, 0.0, 16.0, 8.0, 16.0)
    };
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return LAYERS_TO_SHAPE[state.get(LAYERS)];
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return LAYERS_TO_SHAPE[state.get(LAYERS)-1];
    }

    @Override
    public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
        return LAYERS_TO_SHAPE[state.get(LAYERS)];
    }

    @Override
    public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return LAYERS_TO_SHAPE[state.get(LAYERS)];
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.down());
        if (blockState.getBlock() instanceof SlabBlock) {
            if (blockState.get(TYPE).equals(SlabType.BOTTOM)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0F;
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
