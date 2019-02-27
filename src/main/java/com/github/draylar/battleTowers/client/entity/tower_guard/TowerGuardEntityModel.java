package com.github.draylar.battleTowers.client.entity.tower_guard;

import com.github.draylar.battleTowers.common.entity.tower_guard.TowerGuardEntity;
import net.minecraft.client.model.Cuboid;
import net.minecraft.client.render.entity.model.EntityModel;

public class TowerGuardEntityModel extends EntityModel<TowerGuardEntity>
{
    private final Cuboid bone;
    private final Cuboid legs;
    private final Cuboid right_leg;
    private final Cuboid left_leg;
    private final Cuboid arms;
    private final Cuboid right_arm;
    private final Cuboid right_arm_lower;
    private final Cuboid left_arm;
    private final Cuboid left_arm_lower;
    private final Cuboid head;
    private final Cuboid torch3;
    private final Cuboid torch4;

    public TowerGuardEntityModel()
    {
        textureWidth = 112;
        textureHeight = 112;

        bone = new Cuboid(this);
        bone.setRotationPoint(0.0F, 24.0F, 0.0F);
        bone.y = -.9f;
        bone.addBox("base", -9, -47, -4, 18, 27, 8, 0, 0, 0);

        legs = new Cuboid(this);
        legs.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone.addChild(legs);

        right_leg = new Cuboid(this);
        right_leg.setRotationPoint(0.0F, -4.0F, 4.5F);
        legs.addChild(right_leg);
        right_leg.addBox("right_leg", 1, -16, -7.5f, 7, 20, 6, 0, 26, 35);

        left_leg = new Cuboid(this);
        left_leg.setRotationPoint(0.0F, -4.0F, -4.5F);
        legs.addChild(left_leg);
        left_leg.addBox("left_leg", -8, -16, 1.5f, 7, 20, 6, 0, 0, 35);

        arms = new Cuboid(this);
        arms.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone.addChild(arms);

        right_arm = new Cuboid(this);
        right_arm.setRotationPoint(0.0F, -27.0F, 9.0F);
        arms.addChild(right_arm);
        right_arm.addBox("right_arm", 9, -19, -12, 8, 14, 6, 0, 56, 61);


        right_arm_lower = new Cuboid(this);
        right_arm_lower.setRotationPoint(0.0F, 11.0F, 4.0F);
        right_arm.addChild(right_arm_lower);
        right_arm_lower.addBox("right_arm_lower", 9, -16, -16, 8, 14, 6, 0, 28, 61);

        left_arm = new Cuboid(this);
        left_arm.setRotationPoint(0.0F, -27.0F, -9.0F);
        arms.addChild(left_arm);
        left_arm.addBox("left_arm", -17, -19, 6, 8, 14, 6, 0, 0, 61);

        left_arm_lower = new Cuboid(this);
        left_arm_lower.setRotationPoint(0.0F, 11.0F, -4.0F);
        left_arm.addChild(left_arm_lower);
        left_arm_lower.addBox("left_arm_lower", -17, -16, 10, 8, 14, 6, 0, 52, 35);

        head = new Cuboid(this);
        head.setRotationPoint(0.0F, -31.0F, 0.0F);
        bone.addChild(head);
        head.addBox("head", -6, -28, -6, 12, 12, 12, 0, 52, 0);

        torch3 = new Cuboid(this);
        head.addChild(torch3);
        torch3.addBox("torch1", 5, -35, 0, 2, 9, 2, 0, 8, 81);

        torch4 = new Cuboid(this);
        head.addChild(torch4);
        torch4.addBox("torch2", -7, -35, 0, 2, 9, 2, 0, 0, 81);
    }

    @Override
    public void render(TowerGuardEntity entity_1, float float_1, float float_2, float float_3, float float_4, float float_5, float float_6)
    {
        bone.render(0.1f);
    }


    public void setRotationAngle(Cuboid cuboid, float x, float y, float z) {
        cuboid.rotationPointX = x;
        cuboid.rotationPointY = y;
        cuboid.rotationPointZ = z;
    }
}