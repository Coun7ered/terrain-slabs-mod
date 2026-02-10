package net.countered.terrainslabs.block.ontopofslabs;

import net.countered.terrainslabs.block.interfaces.IBlockCopyFabric;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;


public class FlowerOnTop extends FlowerBlock implements IBlockCopyFabric {
    private final Block originalBlock;
    protected static final VoxelShape SHAPE = Block.createCuboidShape(5.0, -8.0, 5.0, 11.0, 2.0, 11.0);

    public FlowerOnTop(Block originalBlock ) {
        super( ((FlowerBlock) originalBlock).getEffectInStew(),
                ((FlowerBlock) originalBlock).getEffectInStewDuration(),
                AbstractBlock.Settings.copy( originalBlock ));
        this.originalBlock = originalBlock;
    }


    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Vec3d vec3d = state.getModelOffset(world, pos);
        return SHAPE.offset(vec3d.x, vec3d.y, vec3d.z);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.getBlock() instanceof SlabBlock;
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
