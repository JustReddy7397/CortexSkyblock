package dev.cortex.skyblock.listeners;

import com.infernalsuite.aswm.api.AdvancedSlimePaperAPI;
import com.infernalsuite.aswm.api.world.SlimeWorld;
import dev.cortex.skyblock.CortexSkyblock;
import dev.cortex.skyblock.util.Tasks;
import dev.cortex.skyblock.world.PlaceholderCreateIsland;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;

public class JoinListener implements Listener {

    // → Java 22 allows unnamed variables in lambda expressions
    // → As such, we can use _ as a variable name to indicate that we are not using the variable

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        AdvancedSlimePaperAPI api = CortexSkyblock.instance.getAsp();
        Tasks.runAsync(_ -> {
            SlimeWorld world = null;

            try {
                world = api.readWorld(CortexSkyblock.instance.getIslandLoader(), "island_" + player.getUniqueId(), false, PlaceholderCreateIsland.defaultProperties());
            } catch (Exception e) {
                CortexSkyblock.instance.getLogger().severe("An error occurred while loading the world for player " + player.getName());
                e.printStackTrace();
            }

            if (world == null) return;

            SlimeWorld finalWorld = world;
            Tasks.runSync(_ -> {
                SlimeWorld loadedWorld = api.loadWorld(finalWorld, true);
                player.teleport(Bukkit.getWorld(loadedWorld.getName()).getSpawnLocation());
            });

        });
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        AdvancedSlimePaperAPI api = CortexSkyblock.instance.getAsp();
        Tasks.runAsync(_ -> {
            SlimeWorld world = api.getLoadedWorld("island_" + player.getUniqueId());
            try {
                api.saveWorld(world);
            } catch (IOException e) {
                CortexSkyblock.instance.getLogger()
                        .severe("An error occurred while saving the world for player " + player.getName());
                e.printStackTrace();
            }
            Tasks.runSync(_ -> {
                Bukkit.unloadWorld("island_" + player.getUniqueId(), false);
            });
        });
    }
}
