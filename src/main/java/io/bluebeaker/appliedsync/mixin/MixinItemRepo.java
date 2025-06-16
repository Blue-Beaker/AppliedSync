package io.bluebeaker.appliedsync.mixin;

import appeng.client.me.ItemRepo;
import io.bluebeaker.appliedsync.AppliedSyncConfig;
import io.bluebeaker.appliedsync.JEIPlugin;
import io.bluebeaker.appliedsync.Utils;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ItemRepo.class,remap = false)
public abstract class MixinItemRepo {

    @Shadow abstract void setSearch(String search);

    @Inject(method = "updateJEI(Ljava/lang/String;)V",at = @At("HEAD"),cancellable = true)
    public void syncJEI(String filter, CallbackInfo ci){
        if(!AppliedSyncConfig.enable) return;

        if(Utils.isMEFocused(Minecraft.getMinecraft().currentScreen)) return;

        Utils.syncJeiToME(Minecraft.getMinecraft().currentScreen,JEIPlugin.runtime);
        ci.cancel();
    }
}
