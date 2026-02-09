package net.countered.terrainslabs.mixin;

import net.countered.terrainslabs.block.ModSlabsMap;
import net.countered.terrainslabs.block.interfaces.BlockCopyWrapper;
import net.countered.terrainslabs.block.interfaces.IBlockCopy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.AutomaticItemPlacementContext;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin( FallingBlockEntity.class )
public abstract class FallingBlockEntityMixin {

    @Redirect( method = "tick", at = @At( value = "INVOKE",
            target = "Lnet/minecraft/entity/FallingBlockEntity;dropItem(Lnet/minecraft/item/ItemConvertible;)Lnet/minecraft/entity/ItemEntity;" )
    )
    private ItemEntity dropItemProxy(FallingBlockEntity instance, ItemConvertible itemConvertible) {
        if ( instance.getBlockState().getBlock() instanceof IBlockCopy blockCopy ) {
            return instance.dropItem( Blocks.AIR, 1 );
        }

        Block slab = ModSlabsMap.SLAB_MAP.getOrDefault( instance.getBlockState().getBlock(), Blocks.AIR );
        if ( slab.equals( Blocks.AIR ) ) {
            return instance.dropItem( itemConvertible );
        }

        World world = instance.getWorld();
        BlockPos blockBelowPos = instance.getBlockPos();
        BlockState state = instance.getBlockState();
        BlockState blockBelowState = world.getBlockState( blockBelowPos );
        if ( blockBelowState.isOf( slab ) ) { //No need to check state, would only fail on bottom slab.
            FluidState fluidState = blockBelowState.getFluidState();
            if ( fluidState.getFluid().equals( Fluids.WATER ) ) {
                world.setBlockState( blockBelowPos, Blocks.WATER.getDefaultState() );
            } else {
                world.setBlockState( blockBelowPos, Blocks.AIR.getDefaultState() );
            }

            world.setBlockState( blockBelowPos, state.getBlock().getPlacementState(
                    new AutomaticItemPlacementContext(
                            world, blockBelowPos, Direction.DOWN,
                            new ItemStack( state.getBlock().asItem() ),
                            getDirectionFromState( state )
                    )
            ), 0 );
            world.setBlockState( instance.getBlockPos().up(), instance.getBlockState() );
            return instance.dropItem( Blocks.AIR, 1 );
        }
        return instance.dropItem( itemConvertible );
    }

    @Unique
    private Direction getDirectionFromState(BlockState state ) {
        if ( state.getProperties().contains( Properties.HORIZONTAL_FACING ) ) {
            return state.get( Properties.HORIZONTAL_FACING );
        }
        if ( state.getProperties().contains( Properties.HORIZONTAL_AXIS )
                && state.get( Properties.HORIZONTAL_AXIS ).equals( Direction.Axis.X )
        ) {
            return Direction.WEST;
        }
        return Direction.NORTH;
    }
}
