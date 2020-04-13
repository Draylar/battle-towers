package com.github.draylar.battletowers;

import com.github.draylar.battletowers.client.renderer.TowerGuardEntityRenderer;
import com.github.draylar.battletowers.registry.BattleTowerBlocks;
import com.github.draylar.battletowers.registry.BattleTowerEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class BattleTowersClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(BattleTowerEntities.TOWER_GUARD, ((entityRenderDispatcher, context) -> new TowerGuardEntityRenderer(entityRenderDispatcher)));
        BlockRenderLayerMap.INSTANCE.putBlock(BattleTowerBlocks.BOSS_LOCK, RenderLayer.getCutout());
    }
}
