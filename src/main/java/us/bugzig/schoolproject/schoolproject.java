package us.bugzig.schoolproject;

import fr.mrmicky.fastboard.FastBoard;
import me.libraryaddict.disguise.DisguiseAPI;
import org.bukkit.Statistic;
import org.bukkit.plugin.java.JavaPlugin;
import us.bugzig.schoolproject.commands.Start;
import us.bugzig.schoolproject.events.PickupBlock;
import us.bugzig.schoolproject.events.Scoreboard;
import us.bugzig.schoolproject.events.Teleport;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class schoolproject extends JavaPlugin {

    public final Map<UUID, FastBoard> boards = new HashMap<>();

    @Override
    public void onEnable() {

        //Commands
        this.getCommand("startdisguise").setExecutor(new Start());
        getServer().getScheduler().runTaskTimer(this, () -> {
            for (FastBoard board : boards.values()) {
                updateBoard(board);
            }
        }, 0, 20);


        //Events
        getServer().getPluginManager().registerEvents(new Teleport(this), this);
        getServer().getPluginManager().registerEvents(new PickupBlock(this), this);
        getServer().getPluginManager().registerEvents(new Scoreboard(this), this);


    }
    @Override
    public void onDisable() {
    }

    private void updateBoard(FastBoard board) {
        board.updateTitle("Disguise Server");
        board.updateLines(
                "",
                "Online: " + getServer().getOnlinePlayers().size(),
                "",
                "You Are: " + DisguiseAPI.getDisguise(board.getPlayer()).getDisguiseName(),
                ""
        );
    }
}


