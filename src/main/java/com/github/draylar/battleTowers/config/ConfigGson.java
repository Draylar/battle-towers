package com.github.draylar.battleTowers.config;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class ConfigGson
{
    public int requiredKeys;
    public String requiredItem;

    public Identifier bossLootTable;
    public Identifier blacksmithLootTable;
    public Identifier entranceLootTable;
    public Identifier jungleLootTable;
    public Identifier layerLootTable;
    public Identifier libraryLootTable;
    public Identifier mineLootTable;

    public int bossHP;
    public int bossDamageScale;

    public int floorAmount;
    public int floorRandomAddition;
    public int structureRarity;
}
