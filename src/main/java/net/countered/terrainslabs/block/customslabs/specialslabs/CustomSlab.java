package net.countered.terrainslabs.block.customslabs.specialslabs;

import net.countered.terrainslabs.block.interfaces.IBlockCopyFabric;
import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;

public class CustomSlab extends SlabBlock implements IBlockCopyFabric {
    public static final BooleanProperty GENERATED;
    private final Block originalBlock;

    static {
        GENERATED = BooleanProperty.of("generated");
    }

    public CustomSlab( Block originalBlock ) {
        this( originalBlock, AbstractBlock.Settings.copy( originalBlock ) );
    }
    protected CustomSlab( Block originalBlock, Settings customSettings ) {
        super( customSettings );
        this.originalBlock = originalBlock;
        this.setDefaultState(this.getDefaultState()
                .with(TYPE, SlabType.BOTTOM)
                .with(WATERLOGGED, Boolean.valueOf(false))
                .with(GENERATED, Boolean.valueOf(false)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TYPE, WATERLOGGED, GENERATED);
    }

    @Override
    public Block getOriginBlock() {
        return this.originalBlock;
    }

    @Override
    public BlockCopyType getCopyType() {
        return BlockCopyType.SLAB;
    }
}
