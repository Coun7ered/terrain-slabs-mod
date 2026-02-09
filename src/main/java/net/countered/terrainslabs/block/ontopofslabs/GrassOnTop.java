package net.countered.terrainslabs.block.ontopofslabs;

import net.countered.terrainslabs.block.interfaces.IBlockCopyFabric;
import net.countered.terrainslabs.block.interfaces.IOnTopCopy;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class GrassOnTop extends FernBlock implements IBlockCopyFabric, IOnTopCopy {
    private final Block originalBlock;
    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0, -8.0, 2.0, 14.0, 5.0, 14.0);

    public GrassOnTop(Block originalBlock ) {
        super( AbstractBlock.Settings.copy( originalBlock ) );
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
    public Block getOriginBlock() {
        return originalBlock;
    }
}
