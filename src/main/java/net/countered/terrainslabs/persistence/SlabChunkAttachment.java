package net.countered.terrainslabs.persistence;

import com.mojang.serialization.Codec;
import net.countered.terrainslabs.TerrainSlabs;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class SlabChunkAttachment {

    public static final AttachmentType<List<BlockPos>> BOT_SLAB_POSITIONS = AttachmentRegistry.createPersistent(
            Identifier.of(TerrainSlabs.MOD_ID, "bot_slab_positions"),
            Codec.list(BlockPos.CODEC)
    );

    public static final AttachmentType<List<BlockPos>> TOP_SLAB_POSITIONS = AttachmentRegistry.createPersistent(
            Identifier.of(TerrainSlabs.MOD_ID, "top_slab_positions"),
            Codec.list(BlockPos.CODEC)
    );

    public static void registerSlabAttachment() {;
    }
}