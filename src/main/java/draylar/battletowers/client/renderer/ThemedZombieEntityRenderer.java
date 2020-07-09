package draylar.battletowers.client.renderer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.ZombieEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class ThemedZombieEntityRenderer extends ZombieEntityRenderer {

    private final Identifier texture;

    public ThemedZombieEntityRenderer(EntityRenderDispatcher entityRenderDispatcher, Identifier texture) {
        super(entityRenderDispatcher);
        this.texture = texture;
    }

    @Override
    public Identifier getTexture(ZombieEntity zombieEntity) {
        return texture;
    }

    @Override
    public void render(ZombieEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        float height = mobEntity.getHeight();
        matrixStack.push();
        matrixStack.translate(0, height + .4, 0);
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
