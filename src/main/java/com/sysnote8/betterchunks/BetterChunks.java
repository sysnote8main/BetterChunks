package com.sysnote8.betterchunks;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sysnote8.betterchunks.core.CommonProxy;

@Mod(modid = Tags.MODID,
     version = Tags.VERSION,
     name = Tags.MODNAME,
     acceptedMinecraftVersions = "[1.12.2]",
     dependencies = "required-after:betterquesting;")
public class BetterChunks {

    public static final Logger logger = LogManager.getLogger(Tags.MODID);

    @SidedProxy(serverSide = "com.sysnote8.betterchunks.core.CommonProxy",
                clientSide = "com.sysnote8.betterchunks.client.ClientProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        proxy.preInit(event);
        logger.info("Running {} ({})", Tags.MODNAME, Tags.VERSION);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        // Todo: register commands
    }

    // @SubscribeEvent
    // // Register recipes here (Remove if not needed)
    // public void registerRecipes(RegistryEvent.Register<IRecipe> event) {}
    //
    // @SubscribeEvent
    // // Register items here (Remove if not needed)
    // public void registerItems(RegistryEvent.Register<Item> event) {}
    //
    // @SubscribeEvent
    // // Register blocks here (Remove if not needed)
    // public void registerBlocks(RegistryEvent.Register<Block> event) {}
}
