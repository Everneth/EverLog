package com.everneth.everlog;

import co.aikar.commands.BukkitCommandManager;
import co.aikar.idb.DB;
import co.aikar.idb.Database;
import co.aikar.idb.DatabaseOptions;
import co.aikar.idb.PooledDatabaseOptions;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class EverLog extends JavaPlugin {

    private static EverLog plugin;
    private static BukkitCommandManager commandManager;
    FileConfiguration config = getConfig();
    String configPath = getDataFolder() + System.getProperty("file.separator") + "config.yml";
    File configFile = new File(configPath);

    @Override
    public void onEnable()
    {
        plugin = this;

        getLogger().info("EverLog has started its shenanigans...");
        if(!configFile.exists())
        {
            this.saveDefaultConfig();
        }

        DatabaseOptions options = DatabaseOptions.builder().mysql(config.getString("dbuser"), config.getString("dbpass"), config.getString("dbname"), config.getString("dbhost")).build();
        Database db = PooledDatabaseOptions.builder().options(options).createHikariDatabase();
        DB.setGlobalDatabase(db);

        registerCommands();
    }
    @Override
    public void onDisable()
    {
        getLogger().info("EverLog shenanigans have ended...");
        DB.close();
    }

    private void registerCommands()
    {
        commandManager = new BukkitCommandManager(this);
        commandManager.registerCommand(null);
    }
}
