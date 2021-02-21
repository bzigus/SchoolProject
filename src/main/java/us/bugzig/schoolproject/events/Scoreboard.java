package us.bugzig.schoolproject.events;

import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import us.bugzig.schoolproject.schoolproject;

public class Scoreboard implements Listener {

    private final schoolproject plugin;

    public Scoreboard(schoolproject plugin) {
        this.plugin = plugin;
    }



    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        FastBoard board = new FastBoard(player);

        board.updateTitle(ChatColor.RED + "FastBoard");

        plugin.boards.put(player.getUniqueId(), board);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        FastBoard board = plugin.boards.remove(player.getUniqueId());

        if (board != null) {
            board.delete();
        }
    }


}
