package net.countered.terrainslabs.mixin;

import net.countered.terrainslabs.mixinProxy.PlantBlockProxy;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin( PlantBlock.class )
public abstract class PlantBlockMixin implements PlantBlockProxy {

    @Shadow
    protected abstract boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos);

    @Unique
    public boolean terrain_slabs_mod$canPlantOnTopProxy(BlockState state, BlockView world, BlockPos pos ) {
        return this.canPlantOnTop( state, world, pos );
    }

}
