package io.bluebeaker.appliedsync;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Type;

@Config(modid = AppliedSync.MODID,type = Type.INSTANCE,category = "general")
public class AppliedSyncConfig {
    @Comment("Example")
    @LangKey("config.appliedsync.example.name")
    public static boolean example = true;
}