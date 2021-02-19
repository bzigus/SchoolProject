package us.bugzig.schoolproject.events;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.FlagWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.EndermanWatcher;
import org.bukkit.Bukkit;
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

public class PickupBlock implements Listener {
    private final CooldownManager cooldownManager = new CooldownManager();
    private final schoolproject plugin;

    public PickupBlock(schoolproject plugin) {

        this.plugin = plugin;

    }

    @EventHandler
    public void onRightClick (PlayerInteractEvent event) {

        Player player = event.getPlayer();
        int timeLeft = cooldownManager.getCooldown(player.getUniqueId());
        if (timeLeft == 0) {

            if (player.getItemInHand().getType() != Material.STICK) {
                return;
            }
            if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                return;
            }
            if (!DisguiseAPI.isDisguised(player)) {
                return;
            }
            Disguise disguise = DisguiseAPI.getDisguise(player);
            if (!disguise.getDisguiseName().equals("Enderman")) {
                return;
            }
            EndermanWatcher watcher = (EndermanWatcher) disguise.getWatcher();
            player.sendMessage("Set " + watcher.getItemInMainHand());

            if (watcher.getItemInMainHand() == null) {
                watcher.setItemInMainHand(player.getTargetBlock(null, 5).getType());
                player.getTargetBlock(null, 5).setType(Material.AIR);
                player.sendMessage("Pickup " + player.getTargetBlock(null, 5).getType());
                disguise.setWatcher(watcher);
            } else if (watcher.getItemInMainHand() != null) {
                player.getTargetBlock(null, 5).getLocation().add(0,1,0).getBlock().setType(watcher.getItemInMainHand().getType());

                watcher.setItemInMainHand((Material) null);
                disguise.setWatcher(watcher);

            }

            cooldownManager.setCooldown(player.getUniqueId(), CooldownManager.DEFAULT_COOLDOWN);
            new BukkitRunnable() {
                @Override
                public void run() {
                    int timeLeft = cooldownManager.getCooldown(player.getUniqueId());
                    cooldownManager.setCooldown(player.getUniqueId(), --timeLeft);
                    if (timeLeft == 0) {
                        this.cancel();
                    }
                }
            }.runTaskTimer(plugin, 20, 20);
        } else {
            player.sendMessage(ChatColor.RED.toString() + timeLeft + " seconds before you can use this feature again.");
        }


    }

}
