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

    /**
     * This method allows the correct item to be dropped by IBlockCopy falling slabs, as well as includes
     * special behaviour for blocks falling on a generated copy of themselves (places on top if possible
     * and converts the slab to a block)
     * @param instance falling block entity which is being removed
     * @param itemConvertible object that will convert to the item dropped by default
     * @return Item entity that will be spawned
     */
    @Redirect( method = "tick", at = @At( value = "INVOKE",
            target = "Lnet/minecraft/entity/FallingBlockEntity;dropItem(Lnet/minecraft/item/ItemConvertible;)Lnet/minecraft/entity/ItemEntity;" )
    )
    private ItemEntity dropItemProxy(FallingBlockEntity instance, ItemConvertible itemConvertible) {
        // block copies have their own listener to handle this.
        if ( instance.getBlockState().getBlock() instanceof IBlockCopy ) {
            return instance.dropItem( Blocks.AIR, 1 );
        }

        Block slab = ModSlabsMap.SLAB_MAP.getOrDefault( instance.getBlockState().getBlock(), Blocks.AIR );
        Block slabReplace = ModSlabsMap.BLOCK_BELOW_REPLACEMENT_MAP.getOrDefault( slab, Blocks.AIR );
        if ( slab.equals( Blocks.AIR ) ) {
            return instance.dropItem( itemConvertible );
        }

        World world = instance.getWorld();
        BlockPos belowPos = instance.getBlockPos();
        BlockState fallingBlockState = instance.getBlockState();

        // If block types match and slab is generated, place on top instead of breaking for better natural behaviour
        BlockState belowState = world.getBlockState( belowPos );
        BlockState currentStateAtPos = world.getBlockState( belowPos.up() );
        if ( !( ( belowState.isOf( slab ) || fallingBlockState.isOf( slabReplace ) )
                        && belowState.get( CustomSlab.GENERATED ) )
                || !( currentStateAtPos.isIn( BlockTags.REPLACEABLE ) || currentStateAtPos.isAir()
                        || currentStateAtPos.isOf( Blocks.WATER ) )
        ) {
            return instance.dropItem( itemConvertible );
        }

        Block belowBlock = slabReplace.getDefaultState().isAir() ? fallingBlockState.getBlock() : slabReplace;
        world.setBlockState( belowPos, belowBlock.getStateWithProperties( belowState ) );
        world.setBlockState( instance.getBlockPos().up(), fallingBlockState );
        return instance.dropItem( Blocks.AIR, 1 );
    }
}
