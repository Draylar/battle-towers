package draylar.battletowers;

import draylar.battletowers.client.renderer.*;
import draylar.battletowers.registry.BattleTowerBlocks;
import draylar.battletowers.registry.BattleTowerEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class BattleTowersClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(BattleTowerEntities.TOWER_GUARD, ((entityRenderDispatcher, context) -> new TowerGuardEntityRenderer(entityRenderDispatcher)));

        // themed entities
//        EntityRendererRegistry.INSTANCE.register(BattleTowerEntities.MUSHROOM_ZOMBIE, ((dispatcher, context) -> new ThemedZombieEntityRenderer(dispatcher, BattleTowers.id("textures/entity/zombie/mushroom_zombie.png"))));
//        EntityRendererRegistry.INSTANCE.register(BattleTowerEntities.MUSHROOM_CREEPER, ((dispatcher, context) -> new ThemedCreeperEntityRenderer(dispatcher, BattleTowers.id("textures/entity/creeper/mushroom_creeper.png"))));
//        EntityRendererRegistry.INSTANCE.register(BattleTowerEntities.MUSHROOM_SKELETON, ((dispatcher, context) -> new ThemedSkeletonEntityRenderer(dispatcher, BattleTowers.id("textures/entity/skeleton/mushroom_skeleton.png"))));
//        EntityRendererRegistry.INSTANCE.register(BattleTowerEntities.MUSHROOM_SPIDER, ((dispatcher, context) -> new ThemedSpiderEntityRenderer(dispatcher, BattleTowers.id("textures/entity/spider/mushroom_spider.png"))));

        BlockRenderLayerMap.INSTANCE.putBlock(BattleTowerBlocks.BOSS_LOCK, RenderLayer.getCutout());
    }
}
