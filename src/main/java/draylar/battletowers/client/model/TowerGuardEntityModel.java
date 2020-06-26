package draylar.battletowers.client.model;

import draylar.battletowers.entity.TowerGuardEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class TowerGuardEntityModel extends EntityModel<TowerGuardEntity> {

    private final ModelPart torso;
    private final ModelPart leftLeg;
    private final ModelPart leftLegLower;
    private final ModelPart rightLeg;
    private final ModelPart rightLegLower;
    private final ModelPart leftArm;
    private final ModelPart rightArmLower;
    private final ModelPart rightArm;
    private final ModelPart leftArmLower;
    private final ModelPart head;
    private final ModelPart leftTorch;
    private final ModelPart rightTorch;

    public TowerGuardEntityModel() {
        textureWidth = 112;
        textureHeight = 112;

        // head, y12 -> y0
        head = new ModelPart(this, 52, 0);
        head.addCuboid( -6, -12, -6, 12, 12, 12, 0);
        head.setPivot(0.0F, 0.0F, 0.0F);

        // main body, y0 -> -y27
        torso = new ModelPart(this, 0, 0);
        torso.addCuboid(-9, 0, -4, 18, 27, 8, 0);
        torso.setPivot(0.0F, 0.0F, 0.0F);

        // left leg, -y27 -> -y47
        leftLeg = new ModelPart(this, 26, 35);
        leftLeg.addCuboid(1, 0, -3f, 7f, 15f, 6f);
        leftLeg.setPivot(0.0F, 25f, 0.0F);

        leftLegLower = new ModelPart(this, 0, 35);
        leftLegLower.addCuboid(.5f, 0, -3.5f, 8f, 15f, 7f);
        leftLegLower.setPivot(0.0F, 13, 0.0F);
        leftLeg.addChild(leftLegLower);

        // right left, -y27 -> -y47
        rightLeg = new ModelPart(this, 0, 35);
        rightLeg.addCuboid(-8f, 0, -3f, 7, 15, 6, 0);
        rightLeg.setPivot(0.0F, 25f, 0.0F);

        rightLegLower = new ModelPart(this, 0, 35);
        rightLegLower.addCuboid(-8.5f, 0, -3.5f, 8, 15, 7, 0);
        rightLegLower.setPivot(0.0F, 13, 0.0F);
        rightLeg.addChild(rightLegLower);

        // right arm
        leftArm = new ModelPart(this, 56, 61);
        leftArm.addCuboid(9, 1, -3, 8, 14, 6, 0);
        leftArm.setPivot(0.0F, 0.0F, 0.0F);

        leftArmLower = new ModelPart(this, 52, 35);
        leftArmLower.addCuboid(9, 0, -3, 8, 14, 6, 0);
        leftArmLower.setPivot(0.0F, 15, 0.0F);
        leftArm.addChild(leftArmLower);

        // left arm
        rightArm = new ModelPart(this, 0, 61);
        rightArm.addCuboid(-17, 1, -3, 8, 14, 6, 0);
        rightArm.setPivot(0.0F, 0.0F, 0.0F);

        rightArmLower = new ModelPart(this, 28, 61);
        rightArmLower.addCuboid(-17, 0, -3, 8, 14, 6, 0);
        rightArmLower.setPivot(0.0F, 15, 0.0F);
        rightArm.addChild(rightArmLower);

        // horns
        leftTorch = new ModelPart(this);
        leftTorch.addCuboid("left_torch", 0, 0, 0, 2, 9, 2, 0, 8, 81);
        leftTorch.setPivot(9, -15, 0.0f);
        head.addChild(leftTorch);

        rightTorch = new ModelPart(this);
        rightTorch.addCuboid("right_torch", 0, 0, 0, 2, 9, 2, 0, 0, 81);
        rightTorch.setPivot(-11, -13, 0.0f);
        head.addChild(rightTorch);

        rightArm.pitch = -.1f;
        leftArm.pitch = -.1f;

        leftTorch.roll = .58f;
        rightTorch.roll = -.58f;
    }

    @Override
    public void setAngles(TowerGuardEntity entity, float limbAngle, float limbDistance, float customAngle, float headYaw, float headPitch) {
        head.yaw = headYaw * 0.017453292F;
        head.pitch = headPitch * 0.017453292F;

        rightArm.pitch = -.1f - (float) Math.sin(this.handSwingProgress * 3.1415927F);
        leftArm.pitch = -.1f - (float) Math.sin(this.handSwingProgress * 3.1415927F);

        rightArmLower.pitch = rightArm.pitch * 1.5f;
        leftArmLower.pitch = leftArm.pitch * 1.5f;

        rightLeg.pitch = (float) Math.sin(limbAngle) * .3f;
        leftLeg.pitch = -(float) Math.sin(limbAngle) * .3f;

        rightLegLower.pitch = -rightLeg.pitch / 2f;
        leftLegLower.pitch = -leftLeg.pitch / 2f;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
//        matrices.scale(1.6f, 1.6f, 1.6f);
        matrices.translate(0, -1.8, 0);

        torso.render(matrices, vertexConsumer, light, overlay);
        leftLeg.render(matrices, vertexConsumer, light, overlay);
        rightLeg.render(matrices, vertexConsumer, light, overlay);
        leftArm.render(matrices, vertexConsumer, light, overlay);
        rightArm.render(matrices, vertexConsumer, light, overlay);
        head.render(matrices, vertexConsumer, light, overlay);
    }
}