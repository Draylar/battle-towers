package com.github.draylar.battletowers;

import com.github.draylar.battletowers.client.renderer.TowerGuardEntityRenderer;
import com.github.draylar.battletowers.entity.TowerGuardEntity;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.render.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class BattleTowersClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(TowerGuardEntity.class, ((entityRenderDispatcher, context) -> new TowerGuardEntityRenderer(entityRenderDispatcher)));
    }
}
