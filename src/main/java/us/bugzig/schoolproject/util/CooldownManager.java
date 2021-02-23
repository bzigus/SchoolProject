package us.bugzig.schoolproject.util;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import us.bugzig.schoolproject.schoolproject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {

    private final Map<UUID, Integer> cooldowns = new HashMap<>();

    public static final int DEFAULT_COOLDOWN = 1;

    public void setCooldown(UUID player, int time){
        if(time < 1) {
            cooldowns.remove(player);
        } else {
            cooldowns.put(player, time);
        }
    }

    public int getCooldown(UUID player){
        return cooldowns.getOrDefault(player, 0);
    }

    public void setTimer (Player player, Plugin plugin, int time) {

        setCooldown(player.getUniqueId(), time);
        new BukkitRunnable() {
            @Override
            public void run() {
                int timeLeft = getCooldown(player.getUniqueId());
                setCooldown(player.getUniqueId(), --timeLeft);
                if (timeLeft == 0) {
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin, 20, 20);
    }
    public void setTimer (Player player, Plugin plugin) {

        setCooldown(player.getUniqueId(), DEFAULT_COOLDOWN);
        new BukkitRunnable() {
            @Override
            public void run() {
                int timeLeft = getCooldown(player.getUniqueId());
                setCooldown(player.getUniqueId(), --timeLeft);
                if (timeLeft == 0) {
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin, 20, 20);

    }


}
