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
            if (checks(event, player)) {

                Disguise disguise = DisguiseAPI.getDisguise(player);
                EndermanWatcher watcher = (EndermanWatcher) disguise.getWatcher();

                if (watcher.getItemInMainHand() == null) {
                    setHand(watcher, player, disguise);
                } else if (watcher.getItemInMainHand() != null) {

                    setBlock(watcher, player, disguise);

                }

                setTimer(player);
            }


        } else {
            player.sendMessage(ChatColor.RED.toString() + timeLeft + " seconds before you can use this feature again.");
        }


    }

    //Checks for player info
    private boolean checks (PlayerInteractEvent event, Player player) {
        Disguise disguise = DisguiseAPI.getDisguise(player);
        if (player.getItemInHand().getType() == Material.STICK) {
            if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (DisguiseAPI.isDisguised(player)) {
                    if (disguise.getDisguiseName().equals("Enderman")) {
                        return true;
                    } else { return false; }
                } else { return false; }
            } else { return false; }
        } else { return false; }

    }

    private void setHand (EndermanWatcher watcher, Player player, Disguise disguise) {

        watcher.setItemInMainHand(player.getTargetBlock(null, 5).getType());
        player.getTargetBlock(null, 5).setType(Material.AIR);
        player.sendMessage("Picked up " + player.getTargetBlock(null, 5).getType());
        disguise.setWatcher(watcher);

    }
    private void setBlock (EndermanWatcher watcher, Player player, Disguise disguise) {

        player.getTargetBlock(null, 5).getLocation().add(0, 1, 0).getBlock().setType(watcher.getItemInMainHand().getType());

        watcher.setItemInMainHand((Material) null);
        disguise.setWatcher(watcher);

    }

    //Sets Timer
    private void setTimer (Player player) {

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

    }

}
