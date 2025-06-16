package io.bluebeaker.appliedsync;

import appeng.api.config.SearchBoxMode;
import appeng.api.config.Settings;
import appeng.client.gui.implementations.GuiMEMonitorable;
import appeng.core.AEConfig;
import appeng.integration.Integrations;
import io.bluebeaker.appliedsync.mixin.AccessorGuiMEMonitorable;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.apache.logging.log4j.Logger;

import io.bluebeaker.appliedsync.Tags;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION,clientSideOnly = true,acceptableRemoteVersions = "*")
public class AppliedSync
{
    public static final String MODID = Tags.MOD_ID;
    public static final String NAME = Tags.MOD_NAME;
    public static final String VERSION = Tags.VERSION;
    
    public MinecraftServer server;

    private static Logger logger;
    
    public AppliedSync() {
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
    }
    @EventHandler
    public void onServerStart(FMLServerStartingEvent event){
        this.server=event.getServer();
    }

    @SubscribeEvent
    public void onInput(GuiScreenEvent.DrawScreenEvent event){
        GuiScreen gui = event.getGui();
        if(gui instanceof GuiMEMonitorable && shouldSync()){

            AccessorGuiMEMonitorable accessor = (AccessorGuiMEMonitorable) gui;
            String searchText = Integrations.jei().getSearchText();
            accessor.getSearchField().setText(searchText);
            accessor.getRepo().setSearchString(searchText);
        }
    }

    public boolean shouldSync(){
        if(!AppliedSyncConfig.enable) return false;
        final Enum searchModeSetting = AEConfig.instance().getConfigManager().getSetting(Settings.SEARCH_MODE);
        return (SearchBoxMode.JEI_AUTOSEARCH == searchModeSetting || SearchBoxMode.JEI_MANUAL_SEARCH == searchModeSetting || SearchBoxMode.JEI_AUTOSEARCH_KEEP==searchModeSetting || SearchBoxMode.JEI_MANUAL_SEARCH_KEEP==searchModeSetting);
    }

    @SubscribeEvent
    public void onConfigChangedEvent(OnConfigChangedEvent event) {
        if (event.getModID().equals(MODID)) {
            ConfigManager.sync(MODID, Type.INSTANCE);
        }
    }

    public static Logger getLogger(){
        return logger;
    }
}
