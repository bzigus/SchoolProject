package us.bugzig.schoolproject.events;

import me.libraryaddict.disguise.DisguiseAPI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import us.bugzig.schoolproject.schoolproject;
import us.bugzig.schoolproject.util.CooldownManager;

import java.util.concurrent.TimeUnit;


public class Teleport implements Listener {

    private final CooldownManager cooldownManager = new CooldownManager();
    private final schoolproject plugin;

    public Teleport(schoolproject plugin) {

        this.plugin = plugin;

    }

    @EventHandler
    public void onPlayerRightClick(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        int timeLeft = cooldownManager.getCooldown(player.getUniqueId());
        if (timeLeft == 0) {

            //Checks of Enderpearl, rightclick event & isdisguised
            if (player.getItemInHand().getType() != Material.ENDER_PEARL) {
                player.sendMessage("nope");
                return;
            }
            if (!event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                player.sendMessage("ya no");
                return;
            }
            if (!DisguiseAPI.isDisguised(player)) {
                return;
            }

            if (!DisguiseAPI.getDisguise(player).getDisguiseName().equals("Enderman")) {
                return;
            }


            //Stops Enderpearl from throwing
            event.setCancelled(true);
            //Sends message that you are teleporting
            player.sendMessage("Teleporting");
            cooldownManager.setCooldown(player.getUniqueId(), CooldownManager.DEFAULT_COOLDOWN);
            new BukkitRunnable() {
                @Override
                public void run() {
                    int timeLeft = cooldownManager.getCooldown(player.getUniqueId());
                    cooldownManager.setCooldown(player.getUniqueId(), --timeLeft);
                    if(timeLeft == 0){
                        this.cancel();
                    }
                }
            }.runTaskTimer(plugin, 20, 20);


            //Teleports player to block
            player.teleport(player.getTargetBlock(null, 25).getLocation().setDirection(player.getLocation().getDirection()).add(0, 1, 0));

        } else {
            player.sendMessage(ChatColor.RED.toString() + timeLeft + " seconds before you can use this feature again.");
        }
    }

}
