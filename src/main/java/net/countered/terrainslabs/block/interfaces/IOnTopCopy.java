package net.countered.terrainslabs.block.interfaces;

public interface IOnTopCopy extends IBlockCopy {

    default BlockCopyType getCopyType() {
        return BlockCopyType.ON_TOP;
    }

    default PlacementRule getPlacementRule() {
        return PlacementRule.DEFAULT;
    }

    default boolean hasPlacementRule(PlacementRule rule ) {
        return getPlacementRule().equals( rule );
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
}
