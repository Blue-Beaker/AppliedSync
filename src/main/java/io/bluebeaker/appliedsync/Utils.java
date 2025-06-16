package io.bluebeaker.appliedsync;

import appeng.api.config.SearchBoxMode;
import appeng.api.config.Settings;
import appeng.client.gui.implementations.GuiMEMonitorable;
import appeng.client.gui.widgets.MEGuiTextField;
import appeng.client.me.ItemRepo;
import appeng.core.AEConfig;
import io.bluebeaker.appliedsync.mixin.AccessorGuiMEMonitorable;
import mezz.jei.api.IJeiRuntime;
import net.minecraft.client.gui.GuiScreen;

import javax.annotation.Nullable;

public class Utils {
    // Utility method to sync text from JEI to ME
    public static void syncJeiToME(@Nullable GuiScreen gui, IJeiRuntime runtime) {
        if(!(gui instanceof GuiMEMonitorable) || runtime==null) return;
        if(!shouldSync()) return;
        AccessorGuiMEMonitorable accessor = (AccessorGuiMEMonitorable) gui;
        String text = runtime.getIngredientFilter().getFilterText();

        ItemRepo repo = accessor.getRepo();
        if(repo!=null) repo.setSearchString(text);

        MEGuiTextField searchField = accessor.getSearchField();
        if(searchField!=null) searchField.setText(text);
    }

    public static boolean shouldSync(){
        if(!AppliedSyncConfig.enable) return false;
        final Enum searchModeSetting = AEConfig.instance().getConfigManager().getSetting(Settings.SEARCH_MODE);
        return (SearchBoxMode.JEI_AUTOSEARCH == searchModeSetting || SearchBoxMode.JEI_MANUAL_SEARCH == searchModeSetting || SearchBoxMode.JEI_AUTOSEARCH_KEEP==searchModeSetting || SearchBoxMode.JEI_MANUAL_SEARCH_KEEP==searchModeSetting);
    }
    public static boolean isJeiNotKeep(){
        final Enum searchModeSetting = AEConfig.instance().getConfigManager().getSetting(Settings.SEARCH_MODE);
        return (SearchBoxMode.JEI_AUTOSEARCH == searchModeSetting || SearchBoxMode.JEI_MANUAL_SEARCH == searchModeSetting);
    }

    public static boolean isMEFocused(@Nullable GuiScreen gui){
        if(!(gui instanceof GuiMEMonitorable)) return false;
        AccessorGuiMEMonitorable accessor = (AccessorGuiMEMonitorable) gui;
        return accessor.getSearchField().isFocused();
    }
}
