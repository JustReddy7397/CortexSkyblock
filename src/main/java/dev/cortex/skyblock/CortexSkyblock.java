package dev.cortex.skyblock;

import com.infernalsuite.aswm.api.AdvancedSlimePaperAPI;
import com.infernalsuite.aswm.api.loaders.SlimeLoader;
import com.infernalsuite.aswm.loaders.file.FileLoader;
import dev.cortex.skyblock.command.admin.IslandCommand;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.Lamp;
import revxrsal.commands.bukkit.BukkitLamp;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;

import java.io.File;

@Getter
public final class CortexSkyblock extends JavaPlugin {
    public static CortexSkyblock instance;
    private SlimeLoader islandLoader;
    private AdvancedSlimePaperAPI asp;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    @SuppressWarnings("UnstableApiUsage")
    public void onEnable() {
        this.islandLoader = new FileLoader(new File("player_islands"));
        this.asp = AdvancedSlimePaperAPI.instance();
        Lamp<BukkitCommandActor> lamp = BukkitLamp.builder(this)
                .build();
        lamp.register(new IslandCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
