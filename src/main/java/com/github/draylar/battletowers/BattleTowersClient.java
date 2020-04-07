package com.github.draylar.battletowers;

import com.github.draylar.battletowers.client.entity.towerguard.TowerGuardEntityRenderer;
import com.github.draylar.battletowers.common.Entities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;


public class BattleTowersClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(Entities.TOWER_GUARD, ((entityRenderDispatcher, context) -> new TowerGuardEntityRenderer(entityRenderDispatcher)));
    }
}
