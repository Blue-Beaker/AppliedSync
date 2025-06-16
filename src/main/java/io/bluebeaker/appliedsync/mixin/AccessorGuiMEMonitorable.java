package io.bluebeaker.appliedsync.mixin;

import appeng.client.gui.implementations.GuiMEMonitorable;
import appeng.client.gui.widgets.MEGuiTextField;
import appeng.client.me.ItemRepo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = GuiMEMonitorable.class,remap = false)
public interface AccessorGuiMEMonitorable {
    @Accessor
    MEGuiTextField getSearchField();

    @Accessor
    ItemRepo getRepo();
}
