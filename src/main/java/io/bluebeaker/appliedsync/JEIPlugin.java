package io.bluebeaker.appliedsync;

import io.bluebeaker.appliedsync.mixin.AccessorGuiMEMonitorable;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.gui.overlay.IIngredientGridSource;
import mezz.jei.ingredients.IngredientFilter;
import net.minecraft.client.Minecraft;

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin {
    public static IJeiRuntime runtime;
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        runtime=jeiRuntime;
    }

    public static boolean jeiHasKeyboardFocus(){
        return runtime.getIngredientListOverlay().hasKeyboardFocus();
    }
}
