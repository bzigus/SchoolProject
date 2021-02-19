package us.bugzig.schoolproject.util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {

        Player player = (Player) sender;

        player.sendMessage(command.getName());
        if (command.getName().equals("startdisguise")) {

            List<String> list = new ArrayList<String>();


            list.add("enderman");
            list.add("bat");
            list.add("villager");

            return list;

        }

        return null;
    }
}
