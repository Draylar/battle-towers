package draylar.battletowers;

import draylar.battletowers.client.model.TowerGuardEntityModel;
import draylar.battletowers.client.renderer.TowerGuardEntityRenderer;
import draylar.battletowers.registry.BattleTowerBlocks;
import draylar.battletowers.registry.BattleTowerEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelLayer;

@Environment(EnvType.CLIENT)
public class BattleTowersClient implements ClientModInitializer {

    public static final EntityModelLayer TOWER_GUARD_LAYER = new EntityModelLayer(BattleTowers.id("tower_guard_render_layer"), "main");


    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(BattleTowerEntities.TOWER_GUARD, TowerGuardEntityRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlock(BattleTowerBlocks.BOSS_LOCK, RenderLayer.getCutout());

        EntityModelLayerRegistry.registerModelLayer(TOWER_GUARD_LAYER, TowerGuardEntityModel::getTexturedModelData);
    }
}
