package net.countered.terrainslabs.block.interfaces;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public interface IBlockCopyFabric extends IBlockCopy {

    default String[] getOriginBlockStrings() {
        String[] strings = getOriginBlock().getTranslationKey().split("\\.");
        return new String[] { strings[1], strings[2] };
    }

    Block getOriginBlock();

    default Item getOriginItem() {
        return getOriginBlock().asItem();
    }
}
