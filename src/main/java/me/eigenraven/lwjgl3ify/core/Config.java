package me.eigenraven.lwjgl3ify.core;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import me.eigenraven.lwjgl3ify.Tags;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.config.Configuration;

public class Config {
    public static final String[] DEFAULT_EXTENSIBLE_ENUMS = new String[] {
        // From EnumHelper
        "net.minecraft.item.EnumAction",
        "net.minecraft.item.ItemArmor$ArmorMaterial",
        "net.minecraft.entity.item.EntityPainting$EnumArt",
        "net.minecraft.entity.EnumCreatureAttribute",
        "net.minecraft.entity.EnumCreatureType",
        "net.minecraft.world.gen.structure.StructureStrongholdPieces$Stronghold$Door",
        "net.minecraft.enchantment.EnumEnchantmentType",
        "net.minecraft.entity.Entity$EnumEntitySize",
        "net.minecraft.block.BlockPressurePlate$Sensitivity",
        "net.minecraft.util.MovingObjectPosition$MovingObjectType",
        "net.minecraft.world.EnumSkyBlock",
        "net.minecraft.entity.player.EntityPlayer$EnumStatus",
        "net.minecraft.item.Item$ToolMaterial",
        "net.minecraft.item.EnumRarity",
        //
        "net.minecraftforge.event.terraingen.PopulateChunkEvent$Populate$EventType",
        "net.minecraftforge.event.terraingen.InitMapGenEvent$EventType",
        "net.minecraftforge.event.terraingen.OreGenEvent$GenerateMinable$EventType",
        "net.minecraftforge.event.terraingen.DecorateBiomeEvent$Decorate$EventType",
        // From GTNH crashes
        "vswe.stevesfactory.Localization",
        "vswe.stevesfactory.blocks.ClusterMethodRegistration",
        "vswe.stevesfactory.blocks.ConnectionBlockType",
        "vswe.stevesfactory.components.ComponentType",
        "vswe.stevesfactory.components.ConnectionSet",
        "vswe.stevesfactory.components.ConnectionOption",
        "ic2.core.init.InternalName",
        "gregtech.api.enums.Element",
        "gregtech.api.enums.OrePrefixes",
        "net.minecraft.client.audio.MusicTicker$MusicType",
        "org.bukkit.Material",
        "buildcraft.api.transport.IPipeTile.PipeType",
        "thaumcraft.common.entities.golems.EnumGolemType",
    };

    private static final Set<String> EXTENSIBLE_ENUMS = new HashSet<>(Arrays.asList(DEFAULT_EXTENSIBLE_ENUMS));
    private static boolean configLoaded = false;

    public static boolean SHOW_JAVA_VERSION = true;
    public static boolean SHOW_LWJGL_VERSION = true;

    public static String LWJGL3IFY_VERSION = Tags.VERSION;

    static void loadConfig() {
        if (configLoaded) {
            return;
        }
        configLoaded = true;
        final File configDir = new File(Launch.minecraftHome, "config");
        if (!configDir.isDirectory()) {
            configDir.mkdirs();
        }
        final File configFile = new File(configDir, "lwjgl3ify.cfg");
        final Configuration config = new Configuration(configFile);
        final String CATEGORY_CORE = "core";
        EXTENSIBLE_ENUMS.addAll(Arrays.asList(config.get(
                        CATEGORY_CORE,
                        "extensibleEnums",
                        EXTENSIBLE_ENUMS.toArray(new String[0]),
                        "Enums to make extensible at runtime")
                .getStringList()));
        SHOW_JAVA_VERSION = config.getBoolean(
                "showJavaVersion", CATEGORY_CORE, SHOW_JAVA_VERSION, "Show java version in the debug hud");
        SHOW_LWJGL_VERSION = config.getBoolean(
                "showLwjglVersion", CATEGORY_CORE, SHOW_LWJGL_VERSION, "Show lwjgl version in the debug hud");
        if (config.hasChanged()) {
            config.save();
        }
    }

    public static Set<String> getExtensibleEnums() {
        return EXTENSIBLE_ENUMS;
    }

    public static void addExtensibleEnum(String className) {
        EXTENSIBLE_ENUMS.add(className);
    }

    public static boolean isConfigLoaded() {
        return configLoaded;
    }
}
