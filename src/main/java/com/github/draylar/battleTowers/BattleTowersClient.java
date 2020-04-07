package com.github.draylar.battleTowers;

import com.github.draylar.battleTowers.client.entity.towerguard.TowerGuardEntityRenderer;
import com.github.draylar.battleTowers.common.Entities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;


public class BattleTowersClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(Entities.TOWER_GUARD, ((entityRenderDispatcher, context) -> new TowerGuardEntityRenderer(entityRenderDispatcher)));
    }
}
