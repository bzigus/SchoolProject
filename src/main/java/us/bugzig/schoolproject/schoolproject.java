package us.bugzig.schoolproject;

import org.bukkit.plugin.java.JavaPlugin;
import us.bugzig.schoolproject.commands.Start;
import us.bugzig.schoolproject.events.PickupBlock;
import us.bugzig.schoolproject.events.Teleport;

public class schoolproject extends JavaPlugin {

    @Override
    public void onEnable() {

        //Commands
        this.getCommand("startdisguise").setExecutor(new Start());


        //Events
        getServer().getPluginManager().registerEvents(new Teleport(this), this);
        getServer().getPluginManager().registerEvents(new PickupBlock(this), this);


    }
    @Override
    public void onDisable() {
    }

}
