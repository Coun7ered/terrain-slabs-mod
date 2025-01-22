package net.countered.terrainslabs.mixin;

import net.fabricmc.fabric.mixin.content.registry.ShovelItemAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ShovelItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.block.SlabBlock.TYPE;
import static net.minecraft.block.SlabBlock.WATERLOGGED;

@Mixin(ShovelItem.class)
public abstract class ShovelItemMixin {

    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    private void useOnCustomSlab(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);

        if (context.getSide() == Direction.DOWN) {
            return;
        }

        BlockState pathState = ShovelItemAccessor.getPathStates().get(blockState.getBlock());

        // Check if it's a slab block registered for path conversion
        if (pathState != null && blockState.getBlock() instanceof SlabBlock && world.getBlockState(blockPos.up()).isAir()) {
            PlayerEntity playerEntity = context.getPlayer();
            SlabType slabType = blockState.get(TYPE);
            pathState = pathState.with(TYPE, slabType).with(WATERLOGGED, blockState.get(WATERLOGGED));

            world.playSound(playerEntity, blockPos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (!world.isClient) {
                world.setBlockState(blockPos, pathState, Block.NOTIFY_ALL_AND_REDRAW);
                world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(playerEntity, pathState));
                if (playerEntity != null) {
                    context.getStack().damage(1, playerEntity, LivingEntity.getSlotForHand(context.getHand()));
                }
            }

            cir.setReturnValue(ActionResult.SUCCESS);
        }
    }
}
