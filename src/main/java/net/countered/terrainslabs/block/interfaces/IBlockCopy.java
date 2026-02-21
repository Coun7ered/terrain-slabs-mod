package net.countered.terrainslabs.block.interfaces;

import org.jetbrains.annotations.Nullable;

public interface IBlockCopy {

    String[] getOriginBlockStrings();

    BlockCopyType getCopyType();

    default boolean isSlabBlock() {
        return getCopyType() == BlockCopyType.SLAB;
    }

    default boolean isOnTopBlock() {
        return getCopyType() == BlockCopyType.ON_TOP;
    }

    @Nullable
    default PlacementRule getPlacementRule() {
        return getCopyType().equals( BlockCopyType.ON_TOP ) ? PlacementRule.DEFAULT : null;
    }

    default boolean hasPlacementRule(PlacementRule rule ) {
        return getPlacementRule() != null && getPlacementRule().equals(rule);
    }
    default boolean hasPlacementRule(String rule ) {
        return this.hasPlacementRule( PlacementRule.getRuleFromString( rule ) );
    }

    enum PlacementRule {
        DEFAULT("default"),
        CUSTOM("custom");

        final String name;

        PlacementRule( String str ) {
            name = str;
        }

        public String getName() {
            return this.name;
        }

        static PlacementRule getRuleFromString( String str ) {
            for ( PlacementRule rule : PlacementRule.values() ) {
                if ( rule.getName().equals( str ) ) {
                    return rule;
                }
            }
            return null;
        }
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
