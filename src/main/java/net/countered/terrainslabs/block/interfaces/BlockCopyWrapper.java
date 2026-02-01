package net.countered.terrainslabs.block.interfaces;

import net.minecraft.block.Block;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public record BlockCopyWrapper(IBlockCopy blockCopy) implements IBlockCopyFabric {
    @Override
    public Block getOriginBlock() {
        if (blockCopy instanceof IBlockCopyFabric blockCopyFabric) {
            return blockCopyFabric.getOriginBlock();
        }

        String[] identifierParts = blockCopy.getOriginBlockStrings();
        return Registries.BLOCK.get(Identifier.of(identifierParts[0], identifierParts[1]));
    }

    @Override
    public BlockCopyType getCopyType() {
        return blockCopy.getCopyType();
    }
}
