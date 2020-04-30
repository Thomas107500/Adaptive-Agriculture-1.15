package com.Thomas107500.adaptiveagriculture;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.Thomas107500.adaptiveagriculture.config.Config;
import com.Thomas107500.adaptiveagriculture.events.StaticEventHandler;
import com.Thomas107500.adaptiveagriculture.init.BlockInit;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(AdaptiveAgriculture.MOD_ID)
public class AdaptiveAgriculture
{
    
    public static final Logger LOGGER = LogManager.getLogger();
    public static AdaptiveAgriculture instance;
    public static final String MOD_ID = "adaptiveagriculture";
    
    public static final ItemGroup CREATIVE_TAB = new ItemGroup("adaptive_agriculture_tab") 
    {
    	@Override
    	public ItemStack createIcon() 
    	{
    		return BlockInit.infertile_dirt.asItem().getDefaultInstance();
    	}
    };
    
    public AdaptiveAgriculture() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        ModLoadingContext.get().registerConfig(Type.COMMON, Config.COMMON_SPEC, "AdaptiveAgriculture.toml");
        
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        instance = this;
    }

    private void setup(final FMLCommonSetupEvent event)
    {
    	MinecraftForge.EVENT_BUS.register(StaticEventHandler.class);
    	
    }

    private void doClientStuff(final FMLClientSetupEvent event) 
    {
       RenderTypeLookup.setRenderLayer(BlockInit.cover_crop, RenderType.getCutoutMipped());
      
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
         
    }

    private void processIMC(final InterModProcessEvent event)
    {
       
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) 
    {
       
    }

   
}
