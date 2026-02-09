package net.countered.terrainslabs.mixin;

import net.countered.terrainslabs.block.ModSlabsMap;
import net.countered.terrainslabs.block.customslabs.specialslabs.CustomSlab;
import net.countered.terrainslabs.block.interfaces.IBlockCopy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin( FallingBlockEntity.class )
public abstract class FallingBlockEntityMixin {

    @Redirect( method = "tick", at = @At( value = "INVOKE",
            target = "Lnet/minecraft/entity/FallingBlockEntity;dropItem(Lnet/minecraft/item/ItemConvertible;)Lnet/minecraft/entity/ItemEntity;" )
    )
    private ItemEntity dropItemProxy(FallingBlockEntity instance, ItemConvertible itemConvertible) {
        if ( instance.getBlockState().getBlock() instanceof IBlockCopy ) {
            return instance.dropItem( Blocks.AIR, 1 );
        }

        Block slab = ModSlabsMap.SLAB_MAP.getOrDefault( instance.getBlockState().getBlock(), Blocks.AIR );
        if ( slab.equals( Blocks.AIR ) ) {
            return instance.dropItem( itemConvertible );
        }

        World world = instance.getWorld();
        BlockPos belowPos = instance.getBlockPos();
        BlockState belowState = world.getBlockState( belowPos );
        BlockState currentStateAtPos = world.getBlockState( belowPos.up() );
        if ( !( belowState.isOf( slab ) && belowState.get( CustomSlab.GENERATED ) )
                || !( currentStateAtPos.isIn( BlockTags.REPLACEABLE ) || currentStateAtPos.isAir()
                        || currentStateAtPos.isOf( Blocks.WATER ) )
        ) {
            return instance.dropItem( itemConvertible );
        }

        BlockState state = instance.getBlockState();
        world.setBlockState( belowPos, state.getBlock().getStateWithProperties( belowState ) );
        world.setBlockState( instance.getBlockPos().up(), state );
        return instance.dropItem( Blocks.AIR, 1 );
    }
}
