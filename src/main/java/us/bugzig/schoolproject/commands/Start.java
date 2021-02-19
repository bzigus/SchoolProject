package us.bugzig.schoolproject.commands;

import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Start implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        if (args[0].equals("bat")) {
            dBat(player);
        } else if (args[0].equals("enderman")) {
            dEnderman(player);
        } else if (args[0].equals("villager")) {
            dVillager(player);
        }

        else {

            player.sendMessage("Please use a name of an animal: Bat, Enderman, Villager");

        }

        return true;
    }

    public void dBat (Player player) {

        MobDisguise mobDisguise = new MobDisguise(DisguiseType.BAT);
        mobDisguise.setEntity(player);
        mobDisguise.startDisguise();

        player.setAllowFlight(true);
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 6000000, 1));

        player.sendMessage("Disguise has been started successfully");

    }

    public void dEnderman (Player player) {

        MobDisguise mobDisguise = new MobDisguise(DisguiseType.ENDERMAN);
        mobDisguise.setEntity(player);
        mobDisguise.startDisguise();
        player.sendMessage("Disguise has been started successfully");

    }


    public void dVillager (Player player) {

        MobDisguise mobDisguise = new MobDisguise(DisguiseType.VILLAGER);
        mobDisguise.setEntity(player);
        mobDisguise.startDisguise();
        player.sendMessage("Disguise has been started successfully");

    }
}