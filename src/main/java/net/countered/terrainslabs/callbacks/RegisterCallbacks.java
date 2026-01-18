package net.countered.terrainslabs.callbacks;

import net.countered.terrainslabs.block.ModBlocksRegistry;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SnowBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class RegisterCallbacks {
    private static final Map<Item, Block> VEGETATION_ON_TOP_ITEMS = new HashMap<>();

    static {
        VEGETATION_ON_TOP_ITEMS.put(Items.POPPY, ModBlocksRegistry.POPPY_ON_TOP);
        VEGETATION_ON_TOP_ITEMS.put(Items.DANDELION, ModBlocksRegistry.DANDELION_ON_TOP);
        VEGETATION_ON_TOP_ITEMS.put(Items.AZURE_BLUET, ModBlocksRegistry.AZURE_BLUET_ON_TOP);
        VEGETATION_ON_TOP_ITEMS.put(Items.CORNFLOWER, ModBlocksRegistry.CORNFLOWER_ON_TOP);
        VEGETATION_ON_TOP_ITEMS.put(Items.SHORT_GRASS, ModBlocksRegistry.SHORT_GRASS_ON_TOP);
        VEGETATION_ON_TOP_ITEMS.put(Items.FERN, ModBlocksRegistry.FERN_ON_TOP);
        VEGETATION_ON_TOP_ITEMS.put(Items.DEAD_BUSH, ModBlocksRegistry.DEAD_BUSH_ON_TOP);
        VEGETATION_ON_TOP_ITEMS.put(Items.BROWN_MUSHROOM, ModBlocksRegistry.BROWN_MUSHROOM_ON_TOP);
        VEGETATION_ON_TOP_ITEMS.put(Items.RED_MUSHROOM, ModBlocksRegistry.RED_MUSHROOM_ON_TOP);
        VEGETATION_ON_TOP_ITEMS.put(Items.SEAGRASS, ModBlocksRegistry.SEAGRASS_ON_TOP);
    }


    public static void init() {
        registerPlaceOnTopCallback();
    }

    private static void registerPlaceOnTopCallback(){
        UseBlockCallback.EVENT.register((PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) -> {
            ItemStack item = player.getStackInHand(hand);

            if (item.getItem() == Items.SNOW) {
                BlockPos blockPos = hitResult.getBlockPos().offset(hitResult.getSide());

                // Ensure the block can be replaced and there's air at the target position
                if ((world.getBlockState(blockPos).isAir() || world.getBlockState(blockPos).getBlock() == ModBlocksRegistry.SNOW_ON_TOP)
                        && world.getBlockState(blockPos.down()).getBlock() instanceof SlabBlock && world.getBlockState(blockPos.down()).get(Properties.SLAB_TYPE).equals(SlabType.BOTTOM)) {
                    // Replace with custom snow slab
                    int currentLayers = world.getBlockState(blockPos).getBlock() instanceof SnowBlock
                            ? world.getBlockState(blockPos).get(SnowBlock.LAYERS)
                            : 0;

                    // If snow layers can be increased, increase by 1 layer
                    if (currentLayers < SnowBlock.MAX_LAYERS) {
                        world.setBlockState(blockPos, ModBlocksRegistry.SNOW_ON_TOP.getDefaultState()
                                .with(SnowBlock.LAYERS, currentLayers + 1));

                        // Play placement sound and consume one snow slab
                        world.playSound(player, blockPos, SoundEvents.BLOCK_SNOW_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        if (!player.isCreative()) {
                            item.decrement(1);
                        }
                        return ActionResult.SUCCESS;
                    }
                }
            }
            else if (VEGETATION_ON_TOP_ITEMS.containsKey(item.getItem())) {
                BlockPos blockPos = hitResult.getBlockPos().offset(hitResult.getSide());
                // Ensure the block can be replaced and there's air at the target position
                if (world.getBlockState(blockPos).isAir() && world.getBlockState(blockPos.down()).getBlock() instanceof SlabBlock
                        && world.getBlockState(blockPos.down()).get(Properties.SLAB_TYPE).equals(SlabType.BOTTOM) && !item.isOf(Items.SEAGRASS)) {

                    world.setBlockState(blockPos, VEGETATION_ON_TOP_ITEMS.get(item.getItem()).getDefaultState(), 0);
                    world.playSound(player, blockPos, SoundEvents.BLOCK_GRASS_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);

                    if (!player.isCreative()) {
                        item.decrement(1);
                    }
                    return ActionResult.SUCCESS;
                }
                else if (world.getBlockState(blockPos).isOf(Blocks.WATER) && world.getBlockState(blockPos.down()).getBlock() instanceof SlabBlock && world.getBlockState(blockPos.down()).get(Properties.SLAB_TYPE).equals(SlabType.BOTTOM)) {
                    if (item.getItem().equals(Items.SEAGRASS)) {
                        world.setBlockState(blockPos, ModBlocksRegistry.SEAGRASS_ON_TOP.getDefaultState(), 0);

                        world.playSound(player, blockPos, SoundEvents.BLOCK_WET_GRASS_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        if (!player.isCreative()) {
                            item.decrement(1);
                        }
                        return ActionResult.SUCCESS;
                    }
                }
            }
            // Pass to allow normal behavior if conditions are not met
            return ActionResult.PASS;
        });
    }
}
