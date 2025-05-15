package com.sysnote8.betterchunks.api.db;

import static com.sysnote8.betterchunks.BetterChunks.logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

import org.jetbrains.annotations.NotNull;

public class NBTDataStorage {

    private static final String fileSuffix = ".dat";
    private final File targetFile;
    private NBTTagCompound compound;

    public NBTDataStorage(@NotNull File parentFolder, @NotNull String fileName) {
        targetFile = new File(parentFolder, fileName + fileSuffix);
        compound = load();
    }

    public File getFile() {
        return targetFile;
    }

    public NBTTagCompound getTag() {
        return compound;
    }

    public void setTag(NBTTagCompound tagCompound) {
        compound = tagCompound;
    }

    private @NotNull NBTTagCompound load() {
        logger.debug("Reading data from {}", targetFile.getName());
        if (!targetFile.exists()) {
            return new NBTTagCompound();
        }
        try (InputStream inStream = new FileInputStream(targetFile)) {
            return CompressedStreamTools.readCompressed(inStream);
        } catch (IOException e) {
            logger.error("Failed to load", e);
            return new NBTTagCompound();
        }
    }

    public void reloadFile() {
        compound = load();
    }

    public synchronized void save() {
        if (compound != null) { // Null check for safety
            write();
        } else {
            logger.error("Target data is null");
        }
    }

    private void write() {
        logger.debug("Writing data to {}", targetFile.getName());
        if (compound.isEmpty()) {
            logger.debug("No data has wrote because this tag has no data");
            return;
        }

        if (!targetFile.exists()) {
            logger.debug("Creating parent folders...");
            try {
                Files.createDirectories(targetFile.getParentFile().toPath());
            } catch (IOException e) {
                logger.error("Couldn't create parent folders", e);
                return;
            }
        }

        try (OutputStream outStream = new FileOutputStream(targetFile)) {
            CompressedStreamTools.writeCompressed(compound, outStream);
            logger.debug("Successfully to write data ({})", targetFile.getName());
        } catch (IOException e) {
            logger.error("Failed to write data", e);
        }
    }
}
