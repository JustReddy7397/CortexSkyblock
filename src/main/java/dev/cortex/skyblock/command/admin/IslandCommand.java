package dev.cortex.skyblock.command.admin;

import com.infernalsuite.aswm.api.AdvancedSlimePaperAPI;
import com.infernalsuite.aswm.api.world.SlimeWorld;
import dev.cortex.skyblock.CortexSkyblock;
import dev.cortex.skyblock.world.PlaceholderCreateIsland;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;

/**
 * @author JustReddy
 */
@Command("island")
public class IslandCommand {

    @Subcommand("create")
    public void createIsland(BukkitCommandActor actor) {
        if (!actor.isPlayer()) return;
        PlaceholderCreateIsland.createIsland(actor.asPlayer());
    }

    @Subcommand("save")
    public void saveIsland(BukkitCommandActor actor, String savedWorld) {
        AdvancedSlimePaperAPI api = CortexSkyblock.instance.getAsp();
        try {
            api.saveWorld(api.getLoadedWorld(savedWorld));
            actor.sender().sendMessage("World saved!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Subcommand("tp")
    public void teleportIsland(BukkitCommandActor actor, String island) {

        if (!actor.isPlayer()) return;

        actor.asPlayer().teleport(new Location(Bukkit.getWorld(island), 50, 60, 100));

    }

    @Subcommand("load")
    public void loadIsland(BukkitCommandActor actor, String island) {
        if (!actor.isPlayer()) return;
        onLoadWorld(actor.asPlayer(), island);

    }


    void onLoadWorld(@NotNull Player player, @NotNull String island) {
        AdvancedSlimePaperAPI api = CortexSkyblock.instance.getAsp();
        try {
            SlimeWorld world = api.readWorld(CortexSkyblock.instance.getIslandLoader(), island, false, null);
            api.loadWorld(world, true);
            player.sendMessage("World loaded!");
        } catch (Exception e) {
            player.sendMessage("An error occurred while loading the world. Please contact an administrator.");
        }
    }

}
