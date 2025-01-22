package net.countered.terrainslabs.block.customslabs.specialslabs;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;

public class CustomSlab extends SlabBlock {
    public static final BooleanProperty GENERATED;

    static {
        GENERATED = BooleanProperty.of("generated");
    }

    public CustomSlab(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState()
                .with(TYPE, SlabType.BOTTOM)
                .with(WATERLOGGED, Boolean.FALSE)
                .with(GENERATED, Boolean.FALSE));
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TYPE, WATERLOGGED, GENERATED);
    }
}
