package net.countered.terrainslabs.mixinProxy;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public interface PlantBlockProxy {

    boolean terrain_slabs_mod$canPlantOnTopProxy(BlockState state, BlockView world, BlockPos pos );

}
