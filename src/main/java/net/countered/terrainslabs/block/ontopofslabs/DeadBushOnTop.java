package net.countered.terrainslabs.block.ontopofslabs;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.sound.AmbientDesertBlockSounds;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class DeadBushOnTop extends PlantBlock {
    public static final MapCodec<DeadBushOnTop> CODEC = createCodec(DeadBushOnTop::new);
    protected static final VoxelShape SHAPE = Block.createColumnShape(12.0, -8.0, 5.0);

    public DeadBushOnTop(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends PlantBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.getBlock() instanceof SlabBlock;
    }
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        AmbientDesertBlockSounds.tryPlayDeadBushSounds(world, pos, random);
    }
}
