package net.countered.terrainslabs.persistence;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.countered.terrainslabs.TerrainSlabs;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class SlabChunkAttachment {

    public static final AttachmentType<List<BlockPos>> BOT_SLAB_POSITIONS = AttachmentRegistry.create(
            Identifier.of(TerrainSlabs.MOD_ID, "bot_slab_positions"),
            builder -> builder
                    .persistent(Codec.list(BlockPos.CODEC))
                    .initializer(ArrayList::new)
    );

    public record AttachedSlabPos(BlockPos pos, boolean waterlogged) {

        public static final Codec<AttachedSlabPos> CODEC =
                RecordCodecBuilder.create(instance -> instance.group(
                        BlockPos.CODEC.fieldOf("pos").forGetter(AttachedSlabPos::pos),
                        Codec.BOOL.fieldOf("waterlogged").forGetter(AttachedSlabPos::waterlogged)
                ).apply(instance, AttachedSlabPos::new));
    }

    // Pair for waterlogged check necessary to fix floating water bug
    public static final AttachmentType<List<AttachedSlabPos>> TOP_SLAB_POSITIONS =
            AttachmentRegistry.create(
                    Identifier.of(TerrainSlabs.MOD_ID, "top_slab_positions"),
                    builder -> builder
                            .persistent(Codec.list(AttachedSlabPos.CODEC))
                            .initializer(ArrayList::new)
            );

    public static void init() {;
    }
}