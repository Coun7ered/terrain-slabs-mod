package net.countered.terrainslabs.block.interfaces;

public interface IBlockCopy {

    String[] getOriginBlockStrings();

    BlockCopyType getCopyType();

    default boolean isSlabBlock() {
        return getCopyType() == BlockCopyType.SLAB;
    }

    default boolean isOnTopBlock() {
        return getCopyType() == BlockCopyType.ON_TOP;
    }

    enum BlockCopyType {
        ON_TOP("on_top"),
        SLAB("slab");

        private final String name;

        BlockCopyType( String string ) {
            this.name = string;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
}
