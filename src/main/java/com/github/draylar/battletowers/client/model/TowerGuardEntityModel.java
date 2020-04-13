package com.github.draylar.battletowers.client.model;

import com.github.draylar.battletowers.entity.TowerGuardEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class TowerGuardEntityModel extends EntityModel<TowerGuardEntity> {
    private final ModelPart bone;
    private final ModelPart legs;
    private final ModelPart right_leg;
    private final ModelPart left_leg;
    private final ModelPart arms;
    private final ModelPart right_arm;
    private final ModelPart right_arm_lower;
    private final ModelPart left_arm;
    private final ModelPart left_arm_lower;
    private final ModelPart head;
    private final ModelPart torch3;
    private final ModelPart torch4;

    public TowerGuardEntityModel() {
        textureWidth = 112;
        textureHeight = 112;

        bone = new ModelPart(this);
        bone.setPivot(0.0F, 24.0F, 0.0F);
        bone.pivotY = -.9f;
        bone.addCuboid("base", -9, -47, -4, 18, 27, 8, 0, 0, 0);

        legs = new ModelPart(this);
        legs.setPivot(0.0F, 0.0F, 0.0F);
        bone.addChild(legs);

        right_leg = new ModelPart(this);
        right_leg.setPivot(0.0F, -4.0F, 4.5F);
        legs.addChild(right_leg);
        right_leg.addCuboid("right_leg", 1, -16, -7.5f, 7, 20, 6, 0, 26, 35);

        left_leg = new ModelPart(this);
        left_leg.setPivot(0.0F, -4.0F, -4.5F);
        legs.addChild(left_leg);
        left_leg.addCuboid("left_leg", -8, -16, 1.5f, 7, 20, 6, 0, 0, 35);

        arms = new ModelPart(this);
        arms.setPivot(0.0F, 0.0F, 0.0F);
        bone.addChild(arms);

        right_arm = new ModelPart(this);
        right_arm.setPivot(0.0F, -27.0F, 9.0F);
        arms.addChild(right_arm);
        right_arm.addCuboid("right_arm", 9, -19, -12, 8, 14, 6, 0, 56, 61);


        right_arm_lower = new ModelPart(this);
        right_arm_lower.setPivot(0.0F, 11.0F, 4.0F);
        right_arm.addChild(right_arm_lower);
        right_arm_lower.addCuboid("right_arm_lower", 9, -16, -16, 8, 14, 6, 0, 28, 61);

        left_arm = new ModelPart(this);
        left_arm.setPivot(0.0F, -27.0F, -9.0F);
        arms.addChild(left_arm);
        left_arm.addCuboid("left_arm", -17, -19, 6, 8, 14, 6, 0, 0, 61);

        left_arm_lower = new ModelPart(this);
        left_arm_lower.setPivot(0.0F, 11.0F, -4.0F);
        left_arm.addChild(left_arm_lower);
        left_arm_lower.addCuboid("left_arm_lower", -17, -16, 10, 8, 14, 6, 0, 52, 35);

        head = new ModelPart(this);
        head.setPivot(0.0F, -31.0F, 0.0F);
        bone.addChild(head);
        head.addCuboid("head", -6, -28, -6, 12, 12, 12, 0, 52, 0);

        torch3 = new ModelPart(this);
        head.addChild(torch3);
        torch3.addCuboid("torch1", 5, -35, 0, 2, 9, 2, 0, 8, 81);

        torch4 = new ModelPart(this);
        head.addChild(torch4);
        torch4.addCuboid("torch2", -7, -35, 0, 2, 9, 2, 0, 0, 81);
    }

    @Override
    public void setAngles(TowerGuardEntity entity, float limbAngle, float limbDistance, float customAngle, float headYaw, float headPitch) {
    }


    public void setRotationAngle(ModelPart cuboid, float x, float y, float z) {
        cuboid.pivotX = x;
        cuboid.pivotY = y;
        cuboid.pivotZ = z;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        matrices.scale(1.6f, 1.6f, 1.6f);
        matrices.translate(0, 1, 0);
        bone.render(matrices, vertexConsumer, light, overlay);
    }
}