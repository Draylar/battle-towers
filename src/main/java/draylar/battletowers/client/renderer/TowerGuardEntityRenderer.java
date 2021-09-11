package draylar.battletowers.client.renderer;

import draylar.battletowers.client.model.TowerGuardEntityModel;
import draylar.battletowers.entity.TowerGuardianEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3f;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class TowerGuardEntityRenderer extends GeoEntityRenderer<TowerGuardianEntity> {

    public TowerGuardEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new TowerGuardEntityModel());
    }

    @Override
    public void render(TowerGuardianEntity entity, float yaw, float delta, MatrixStack matrices, VertexConsumerProvider provider, int light) {
        entity.bodyYaw = entity.headYaw;

        matrices.push();
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180));
        matrices.scale(1.5f, 1.5f, 1.5f);

        super.render(entity, yaw, delta, matrices, provider, light);
        matrices.pop();
    }
}
