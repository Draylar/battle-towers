package draylar.battletowers.client.renderer;

import draylar.battletowers.client.model.TowerGuardEntityModel;
import draylar.battletowers.entity.TowerGuardianEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class TowerGuardEntityRenderer extends MobEntityRenderer<TowerGuardianEntity, TowerGuardEntityModel> {

    public TowerGuardEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new TowerGuardEntityModel(), 1);
    }

    @Override
    public Identifier getTexture(TowerGuardianEntity towerGuardianEntity) {
        return new Identifier("battletowers:textures/entity/tower_guard/tower_guard.png");
    }
}
