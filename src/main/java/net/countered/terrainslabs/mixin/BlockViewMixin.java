package net.countered.terrainslabs.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockState;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.RaycastContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin( BlockView.class )
public interface BlockViewMixin {

    /**
     * Injects into lambda responsible for the "blockHitFactory" in the core raytrace method of BlockView.
     * Adds on a check for whether the block above the raytrace target is seen in front of the actual voxel targeted.
     * This check allows "onTop" slab blocks to be targeted correctly.
     * @param innerContext RaycastContext
     * @param pos BlockPos
     * @param cir CallbackInfoReturnable
     * @param blockHitResult BlockHitResult
     * @param blockHitResult2 BlockHitResult for fluid
     */
    @Inject( method = "method_17743", at = @At( "RETURN" ), cancellable = true)
    private void onBlockHitFactory(
            RaycastContext innerContext,
            BlockPos pos,
            CallbackInfoReturnable<BlockHitResult> cir,
            @Local( ordinal = 0 ) BlockHitResult blockHitResult,
            @Local( ordinal = 1 ) BlockHitResult blockHitResult2
    ) {
        BlockPos abovePos = new BlockPos( pos.getX(), pos.getY() + 1, pos.getZ() );
        BlockState blockStateAbove = this.getBlockState( abovePos );
        VoxelShape voxelShape3 = innerContext.getBlockShape( blockStateAbove, (BlockView) this, abovePos );
        BlockHitResult blockHitResultAbove = this.raycastBlock( innerContext.getStart(), innerContext.getEnd(), abovePos, voxelShape3, blockStateAbove );
        double f = blockHitResultAbove == null ? Double.MAX_VALUE : innerContext.getStart().squaredDistanceTo(blockHitResultAbove.getPos());

        double d = blockHitResult == null ? Double.MAX_VALUE : innerContext.getStart().squaredDistanceTo(blockHitResult.getPos());
        double e = blockHitResult2 == null ? Double.MAX_VALUE : innerContext.getStart().squaredDistanceTo(blockHitResult2.getPos());

        if ( f <= d && f <= e ) {
            cir.setReturnValue(blockHitResultAbove);
        }
    }

    @Shadow
    BlockState getBlockState(BlockPos pos);

    @Shadow
    default BlockHitResult raycastBlock(Vec3d start, Vec3d end, BlockPos pos, VoxelShape shape, BlockState state) {
        return null;
    }

}
