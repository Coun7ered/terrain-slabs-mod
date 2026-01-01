package net.countered.terrainslabs.block.customslabs.soilslabs;

import com.mojang.serialization.MapCodec;
import net.countered.terrainslabs.block.customslabs.specialslabs.CustomSlab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;

public class PodzolSlab extends CustomSlab {

    public PodzolSlab(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(SlabBlock.TYPE, SlabType.BOTTOM)
                .with(SNOWY, false)
                .with(WATERLOGGED, Boolean.valueOf(false))
                .with(GENERATED, Boolean.valueOf(false)));
    }

    public static final MapCodec<PodzolSlab> CODEC = createCodec(PodzolSlab::new);
    public static final BooleanProperty SNOWY = Properties.SNOWY;

    @Override
    public MapCodec<PodzolSlab> getCodec() {
        return CODEC;
    }

    @Override
    protected BlockState getStateForNeighborUpdate(
            BlockState state,
            WorldView world,
            ScheduledTickView tickView,
            BlockPos pos,
            Direction direction,
            BlockPos neighborPos,
            BlockState neighborState,
            Random random
    ) {
        // Handle snowy logic
        if (direction == Direction.UP) {
            state = state.with(SNOWY, isSnow(neighborState));
        }

        // Handle waterlogging logic
        if (state.get(WATERLOGGED)) {
            tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        // Return the modified state based on the slab's existing logic
        return state;
    }


    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        BlockState currentState = ctx.getWorld().getBlockState(blockPos);

        // Check if the block at the position is already a slab (and it's not a double slab yet)
        if (currentState.isOf(this) && currentState.get(TYPE) != SlabType.DOUBLE) {
            // Convert to a double slab if a slab is placed on an existing slab
            return currentState.with(TYPE, SlabType.DOUBLE).with(WATERLOGGED, false);
        }

        // Determine the snowy state based on the block above
        BlockState blockAbove = ctx.getWorld().getBlockState(blockPos.up());
        BlockState defaultState = this.getDefaultState().with(SNOWY, isSnow(blockAbove));

        // Handle slab type and waterlogging for single slab placement
        FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
        BlockState slabState = defaultState.with(TYPE, SlabType.BOTTOM)
                .with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);

        Direction direction = ctx.getSide();

        // Adjust the slab type based on the placement context (whether it's a top or bottom slab)
        if (direction != Direction.DOWN && (direction == Direction.UP || !(ctx.getHitPos().y - (double) blockPos.getY() > 0.5))) {
            return slabState;
        } else {
            return slabState.with(TYPE, SlabType.TOP);
        }
    }

    private static boolean isSnow(BlockState state) {
        return state.isIn(BlockTags.SNOW);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SNOWY, TYPE, WATERLOGGED, GENERATED);
    }
}
