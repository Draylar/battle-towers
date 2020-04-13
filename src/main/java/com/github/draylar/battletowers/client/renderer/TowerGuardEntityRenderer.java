package com.github.draylar.battletowers.client.renderer;

import com.github.draylar.battletowers.client.model.TowerGuardEntityModel;
import com.github.draylar.battletowers.entity.TowerGuardEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class TowerGuardEntityRenderer extends MobEntityRenderer<TowerGuardEntity, TowerGuardEntityModel> {

    public TowerGuardEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new TowerGuardEntityModel(), 1);
    }

    @Override
    public Identifier getTexture(TowerGuardEntity towerGuardEntity) {
        return new Identifier("battletowers:textures/entity/tower_guard/tower_guard.png");
    }
}
