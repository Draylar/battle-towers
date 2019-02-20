package com.github.draylar.battleTowers;

import com.github.draylar.battleTowers.common.Blocks;
import com.github.draylar.battleTowers.common.Entities;
import com.github.draylar.battleTowers.common.Items;
import com.github.draylar.battleTowers.common.Structures;
import com.github.draylar.battleTowers.config.BattleTowersConfig;
import net.fabricmc.api.ModInitializer;

public class BattleTowers implements ModInitializer
{
	@Override
	public void onInitialize() {
		new BattleTowersConfig().checkConfigFolder();

		Structures.registerStructures();
		Blocks.registerBlocks();
		Items.registerItems();
		Entities.registerEntities();
	}
}
