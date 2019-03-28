package com.github.draylar.battleTowers.config;

import com.google.gson.Gson;
import net.minecraft.util.Identifier;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BattleTowersConfig
{
    /**
     * Checks to see if the config folder exists.
     * If the config folder does not exist, it creates it.
     * It then moves on to checking the config file.
     */
    public void checkConfigFolder()
    {
        Path configPath = Paths.get(System.getProperty("user.dir") + "/config/battle-towers");

        if (configPath.toFile().isDirectory()) checkConfigFile(configPath);

        else
        {
            configPath.toFile().mkdirs();
            checkConfigFile(configPath);
        }
    }


    /**
     * Checks the config file.
     * If the config file does not exist, it is created and filled with default values.
     * If the config file does exist, the values are transferred to the config holder.
     *
     * @param path
     */
    public void checkConfigFile(Path path)
    {
        Path jsonPath = Paths.get(path + "/battle-towers.json");

        if (!jsonPath.toFile().exists())
        {
            createNewFile(jsonPath);
            setBlankConfig(jsonPath, true);
        }

        try {
            // read config
            String input = new String(Files.readAllBytes(jsonPath));

            // save to current config holder
            Gson gson = new Gson();
            ConfigHolder.configInstance = gson.fromJson(input, ConfigGson.class);

            // check for null json reading
            if(ConfigHolder.configInstance.requiredItem == null)
            {
                System.out.println("[Battle Towers] Json is invalid! Try to fix from the error, or remove config file. Rolling with defaults!");
                setBlankConfig(jsonPath, false);
            }
        }

        catch (Exception e)
        {
            System.out.println("[Battle Towers] Json is invalid! Try to fix from the error, or remove config file. Rolling with defaults!");
            e.printStackTrace();

            // to prevent future errors, just set filler values in current config.
            // we leave the config file alone and assume the user will see the errors in console
            // and either delete or fix it.
            setBlankConfig(jsonPath, false);
        }
    }


    /**
     * Sets the current config instance to blank values.
     * If writeConfig is true, we also write the config to the config file.
     * @param jsonPath
     * @param writeConfig
     */
    private void setBlankConfig(Path jsonPath, boolean writeConfig)
    {
        // create blank config
        ConfigGson config = new ConfigGson();

        config.requiredKeys = 10;
        config.requiredItem = "battle-towers:boss_key";
        config.bossLootTable = new Identifier("battle-towers:boss_loot");
        config.blacksmithLootTable = new Identifier("battle-towers:blacksmith_loot");
        config.jungleLootTable = new Identifier("battle-towers:jungle_loot");
        config.layerLootTable = new Identifier("battle-towers:layer_loot");
        config.libraryLootTable = new Identifier("battle-towers:library_loot");
        config.mineLootTable = new Identifier("battle-towers:mine_loot");
        config.entranceLootTable = new Identifier("battle-towers:base_loot");
        config.bossHP = 300;
        config.bossDamageScale = 5;
        config.floorAmount = 10;
        config.floorRandomAddition = 3;
        config.structureRarity = 500;

        // write to config holder
        ConfigHolder.configInstance = config;

        if(writeConfig) writeConfigToPath(jsonPath, config);
    }


    /**
     * Writes the input config to our config file.
     * @param jsonPath
     * @param config
     */
    private void writeConfigToPath(Path jsonPath, ConfigGson config)
    {
        try
        {
            Gson gson = new Gson();
            Files.write(jsonPath, gson.toJson(config).getBytes());
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Attempts to create a new file.
     * @param path
     */
    private void createNewFile(Path path)
    {
        try
        {
            path.toFile().createNewFile();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
