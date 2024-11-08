package net.countered.terrainslabs;

import eu.midnightdust.lib.config.MidnightConfig;
import net.countered.terrainslabs.block.ModBlocksRegistry;
import net.countered.terrainslabs.config.MyModConfig;
import net.countered.terrainslabs.item.ModItemGroups;
import net.countered.terrainslabs.item.ShovelPathSlab;
import net.countered.terrainslabs.worldgen.feature.ModAddedFeatures;
import net.countered.terrainslabs.worldgen.slabfeature.ModSlabGeneration;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SnowBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TerrainSlabs implements ModInitializer {
	public static final String MOD_ID = "terrainslabs";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing TerrainSlabs");
		MidnightConfig.init(TerrainSlabs.MOD_ID, MyModConfig.class);
		ModBlocksRegistry.registerModBlocks();
		ModAddedFeatures.registerFeatures();
		ModSlabGeneration.generateSlabs();
		ModItemGroups.registerItemGroups();
		ShovelPathSlab.init();
		registerCallbacks();
	}

	public static void registerCallbacks() {
		UseBlockCallback.EVENT.register((PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) -> {
			ItemStack item = player.getStackInHand(hand);

			if (item.getItem() == Items.SNOW) {
				BlockPos blockPos = hitResult.getBlockPos().offset(hitResult.getSide());

				// Ensure the block can be replaced and there's air at the target position
				if ((world.getBlockState(blockPos).isAir() || world.getBlockState(blockPos).getBlock() == ModBlocksRegistry.SNOW_ON_TOP)
					&& world.getBlockState(blockPos.down()).getBlock() instanceof SlabBlock) {
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
			// Pass to allow normal behavior if conditions are not met
			return ActionResult.PASS;
		});
	}
}