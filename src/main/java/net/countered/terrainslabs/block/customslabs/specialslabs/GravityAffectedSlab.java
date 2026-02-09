package net.countered.terrainslabs.block.customslabs.specialslabs;

import net.countered.terrainslabs.block.interfaces.BlockCopyWrapper;
import net.countered.terrainslabs.block.interfaces.IBlockCopy;
import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.client.util.ParticleUtil;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class GravityAffectedSlab extends CustomSlab implements LandingBlock {

    public GravityAffectedSlab(Block originalBlock ) {
        super( originalBlock );
        this.setDefaultState(this.getDefaultState()
                .with(TYPE, SlabType.BOTTOM)
                .with(WATERLOGGED, false)
                .with(GENERATED, false));
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        world.scheduleBlockTick(pos, this, this.getFallDelay());
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if ( canFallThrough( world.getBlockState( pos.down() ) ) && pos.getY() >= world.getBottomY() ) {
            // FallingBlockEntity fallingBlockEntity =
            FallingBlockEntity.spawnFromBlock( world, pos, state );
            // this.configureFallingBlockEntity(fallingBlockEntity);
        }
    }
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        world.scheduleBlockTick(pos, this, this.getFallDelay());
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    private boolean canFallThrough( BlockState state ) {
        return FallingBlock.canFallThrough( state )
                || ( state.isOf( this ) && state.get( TYPE ).equals( SlabType.BOTTOM ) );
    }

//    protected void configureFallingBlockEntity(FallingBlockEntity entity) {
//    }

    /**
     * Gets the amount of time in ticks this block will wait before attempting to start falling.
     */
    protected int getFallDelay() {
        return 2;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(16) == 0) {
            BlockPos blockPos = pos.down();
            if ( canFallThrough(world.getBlockState(blockPos))) {
                ParticleUtil.spawnParticle(world, pos, random, new BlockStateParticleEffect(ParticleTypes.FALLING_DUST, state));
            }
        }
    }

//    public int getColor(BlockState state, BlockView world, BlockPos pos) {
//        return -16777216;
//    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        BlockState blockState = ctx.getWorld().getBlockState(blockPos);
        if (blockState.isOf(this)) {
            return blockState.with(TYPE, SlabType.DOUBLE).with(WATERLOGGED, false );
        } else {
            FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
            BlockState blockState2 = this.getDefaultState().with(TYPE, SlabType.BOTTOM).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
            Direction direction = ctx.getSide();
            return direction != Direction.DOWN && (direction == Direction.UP || !(ctx.getHitPos().y - (double)blockPos.getY() > 0.5))
                    ? blockState2
                    : blockState2.with(TYPE, SlabType.BOTTOM);
        }
    }

    @Override
    public void onDestroyedOnLanding(World world, BlockPos pos, FallingBlockEntity fallingBlockEntity) {
        BlockState fallingBlockState = fallingBlockEntity.getBlockState();
        BlockState landedOnBlockState = world.getBlockState( pos );
        if ( landedOnBlockState.isOf( this ) ) { //No need to check state, would only trigger on bottom slab
            if ( landedOnBlockState.get( GENERATED ) ) {
                world.setBlockState( pos, new BlockCopyWrapper(
                        (IBlockCopy) fallingBlockState.getBlock() ).getOriginBlock().getDefaultState());
            } else {
                world.setBlockState( pos, this.getDefaultState().with( TYPE, SlabType.DOUBLE ));
            }
            if ( !fallingBlockState.get( TYPE ).equals( SlabType.DOUBLE ) ) {
                return;
            }

            BlockState aboveState = world.getBlockState( pos.up() );
            if ( !( aboveState.isIn( BlockTags.REPLACEABLE ) || aboveState.isAir() || aboveState.isOf( Blocks.WATER ) ) ) {
                dropStack(world, pos, new ItemStack(this.getOriginItem()));
                return;
            }

            world.setBlockState( pos.up(), landedOnBlockState
                    .with( TYPE, SlabType.BOTTOM ) );
            return;
        }

        if ( fallingBlockState.get(TYPE).equals( SlabType.DOUBLE) ) {
            dropStack(world, pos, new ItemStack(this.getOriginItem(), 2));
        } else {
            dropStack(world, pos, new ItemStack(this.getOriginItem()));
        }
    }

    @Override
    public void onLanding(World world, BlockPos pos, BlockState fallingBlockState, BlockState currentStateInPos, FallingBlockEntity fallingBlockEntity) {
        if ( fallingBlockState.get( TYPE ) == SlabType.TOP ) {
            world.setBlockState( pos, fallingBlockState.with( TYPE, SlabType.BOTTOM ) );
        }
    }
}

