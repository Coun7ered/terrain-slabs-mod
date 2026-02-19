package net.countered.terrainslabs.mixin;

import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin( FlowableFluid.class )
public abstract class FlowableFluidMixin {

    @Unique
    private static final BooleanProperty GENERATED = BooleanProperty.of("generated");

    /**
     * Fluid considers trying to flow into generated slabs
     */
    @Inject( method = "canFill", at = @At( "HEAD" ), cancellable = true )
    private void onCanFill(
            BlockView world, BlockPos pos,
            BlockState state, Fluid fluid,
            CallbackInfoReturnable<Boolean> cir
    ) {
        if ( state.getBlock() instanceof SlabBlock && state.getProperties().contains( GENERATED )
                && state.get( GENERATED ) && state.get( Properties.SLAB_TYPE ).equals( SlabType.BOTTOM )
                && state.get( Properties.WATERLOGGED ).equals( false )
        ) {
            cir.setReturnValue( true );
            cir.cancel();
        }
    }

    /**
     * Fluid flows into generated slabs if the fluid level is high enough
     * @param world world
     * @param pos pos
     * @param state blockState flowing into
     * @param direction flow direction
     * @param fluidState fluidState
     * @param ci context
     */
    @Inject( method = "flow", at = @At( "HEAD" ), cancellable = true )
    void onFlow(
            WorldAccess world, BlockPos pos,
            BlockState state, Direction direction,
            FluidState fluidState, CallbackInfo ci
    ) {
        if ( !state.getProperties().contains( GENERATED ) || !state.get( GENERATED ) ) {
            return;
        }
        if ( direction == Direction.DOWN || fluidState.getLevel() >= 4 ) {
            world.breakBlock( pos, false );
            world.setBlockState( pos, fluidState.getBlockState(), Block.NOTIFY_ALL );
            ci.cancel();
        }
    }
}
