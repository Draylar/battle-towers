package com.github.draylar.battletowers;

import com.github.draylar.battletowers.client.entity.towerguard.TowerGuardEntityRenderer;
import com.github.draylar.battletowers.common.Entities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class BattleTowersClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(Entities.TOWER_GUARD, ((entityRenderDispatcher, context) -> new TowerGuardEntityRenderer(entityRenderDispatcher)));
    }
}
