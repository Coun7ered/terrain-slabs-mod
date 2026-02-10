package net.countered.terrainslabs.mixin;

import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin( FlowableFluid.class )
public abstract class FlowableFluidMixin {

    @Unique
    private static final BooleanProperty GENERATED = BooleanProperty.of("generated");

    @Redirect( method = "flow", at = @At( value = "INVOKE",
            target = "Lnet/minecraft/block/FluidFillable;tryFillWithFluid(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/fluid/FluidState;)Z"))
    private boolean tryFillWithFluidProxy(
            FluidFillable instance, WorldAccess world,
            BlockPos pos, BlockState state, FluidState fluidState
    ) {
        if ( state.getProperties().contains( GENERATED ) && state.get( GENERATED ) ) {
            world.breakBlock( pos, true );
            world.setBlockState( pos, fluidState.getBlockState(), Block.NOTIFY_ALL );
            return true;
        }
        return instance.tryFillWithFluid( world, pos, state, fluidState );
    }

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

}
