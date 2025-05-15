package com.sysnote8.betterchunks.api.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.chunk.Chunk;

import org.jetbrains.annotations.Nullable;

/**
 * Because Chunk is too big for serialize to JSON.
 */
public class ChunkData {

    public static final String TAG_DIM = "dim";
    public static final String TAG_CHUNK_X = "chunk_x";
    public static final String TAG_CHUNK_Z = "chunk_z";
    public final int x;
    public final int z;
    public final int dimId;

    public ChunkData(int dimId, int chunkX, int chunkZ) {
        this.dimId = dimId;
        this.x = chunkX;
        this.z = chunkZ;
    }

    public boolean equals(ChunkData data) {
        return this.x == data.x && this.z == data.z && this.dimId == data.dimId;
    }

    public boolean equals(Chunk chunk, int dimId) {
        return this.x == chunk.x && this.z == chunk.z && this.dimId == dimId;
    }

    public NBTTagCompound toNBTTag() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger(TAG_DIM, dimId);
        compound.setInteger(TAG_CHUNK_X, x);
        compound.setInteger(TAG_CHUNK_Z, z);
        return compound;
    }

    @Override
    public String toString() {
        return "ChunkData{x=" + x + ", z=" + z + ", dimId=" + dimId + "}";
    }

    @Nullable
    public static ChunkData fromNBT(NBTTagCompound compound) {
        if (!compound.hasKey(TAG_DIM) || !compound.hasKey(TAG_CHUNK_X) || !compound.hasKey(TAG_CHUNK_Z))
            return null;
        return new ChunkData(compound.getInteger(TAG_DIM), compound.getInteger(TAG_CHUNK_X),
                compound.getInteger(TAG_CHUNK_Z));
    }

    public static ChunkData fromChunk(Chunk chunk) {
        return new ChunkData(chunk.getWorld().provider.getDimension(), chunk.x, chunk.z);
    }
}
