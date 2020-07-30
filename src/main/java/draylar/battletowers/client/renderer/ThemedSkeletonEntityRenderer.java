package draylar.battletowers.client.renderer;

import draylar.battletowers.entity.themed.ThemedSkeletonEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.SkeletonEntityModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class ThemedSkeletonEntityRenderer extends BipedEntityRenderer<ThemedSkeletonEntity, SkeletonEntityModel<ThemedSkeletonEntity>> {

    private final Identifier texture;

    public ThemedSkeletonEntityRenderer(EntityRenderDispatcher entityRenderDispatcher, Identifier texture) {
        super(entityRenderDispatcher, new SkeletonEntityModel<>(), 0.5F);
        this.addFeature(new ArmorFeatureRenderer<>(this, new SkeletonEntityModel<>(0.5F, true), new SkeletonEntityModel<>(1.0F, true)));
        this.texture = texture;
    }

    @Override
    public Identifier getTexture(ThemedSkeletonEntity abstractSkeletonEntity) {
        return texture;
    }

    @Override
    public void render(ThemedSkeletonEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        float height = mobEntity.getHeight();
        matrixStack.push();
        matrixStack.translate(0, height + .1, 0);
        matrixStack.multiply(Vector3f.NEGATIVE_Y.getDegreesQuaternion(mobEntity.headYaw));

        MinecraftClient.getInstance().getItemRenderer().renderItem(
                new ItemStack(Items.RED_MUSHROOM),
                ModelTransformation.Mode.FIXED,
                i,
                OverlayTexture.DEFAULT_UV,
                matrixStack,
                vertexConsumerProvider
        );

        matrixStack.pop();
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
