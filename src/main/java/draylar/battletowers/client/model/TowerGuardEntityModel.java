// Made with Model Converter by Globox_Z
// Generate all required imports
package draylar.battletowers.client.model;


import draylar.battletowers.entity.TowerGuardianEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;


public class TowerGuardEntityModel extends EntityModel<TowerGuardianEntity> {
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

    public TowerGuardEntityModel(ModelPart root) {
        this.torso = root.getChild("torso");
        this.leftLeg = root.getChild("leftLeg");
        this.leftLegLower = this.leftLeg.getChild("leftLegLower");
        this.rightLeg = root.getChild("rightLeg");
        this.rightLegLower = this.rightLeg.getChild("rightLegLower");
        this.leftArm = root.getChild("leftArm");
        this.leftArmLower = this.leftArm.getChild("leftArmLower");
        this.rightArm = root.getChild("rightArm");
        this.rightArmLower = this.rightArm.getChild("rightArmLower");
        this.head = root.getChild("head");
        this.rightTorch = this.head.getChild("rightTorch");
        this.leftTorch = this.head.getChild("leftTorch");

        rightArm.pitch = -.1f;
        leftArm.pitch = -.1f;
        leftTorch.roll = .58f;
        rightTorch.roll = -.58f;
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        // head, y12 -> y0
        // main body, y0 -> -y27
        // left leg, -y27 -> -y47
        ModelPartData modelPartData5 = modelPartData.addChild("head", ModelPartBuilder.create().uv(52, 0).cuboid(-6, -12, -6, 12, 12, 12), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild("torso", ModelPartBuilder.create().uv(0, 0).cuboid(-9, 0, -4, 18, 27, 8), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData modelPartData1 = modelPartData.addChild("leftLeg", ModelPartBuilder.create().uv(26, 35).cuboid(1, 0, -3f, 7f, 15f, 6f), ModelTransform.pivot(0.0F, 25F, 0.0F));
        modelPartData1.addChild("leftLegLower", ModelPartBuilder.create().uv(0, 35).cuboid(.5f, 0, -3.5f, 8f, 15f, 7f), ModelTransform.pivot(0.0F, 13F, 0.0F));
        ModelPartData modelPartData2 = modelPartData.addChild("rightLeg", ModelPartBuilder.create().uv(0, 35).cuboid(-8f, 0, -3f, 7, 15, 6), ModelTransform.pivot(0.0F, 25F, 0.0F));
        modelPartData2.addChild("rightLegLower", ModelPartBuilder.create().uv(0, 35).cuboid(-8.5f, 0, -3.5f, 8, 15, 7), ModelTransform.pivot(0.0F, 13F, 0.0F));
        ModelPartData modelPartData3 = modelPartData.addChild("leftArm", ModelPartBuilder.create().uv(56, 61).cuboid(9, 1, -3, 8, 14, 6), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData3.addChild("leftArmLower", ModelPartBuilder.create().uv(52, 35).cuboid(9, 0, -3, 8, 14, 6), ModelTransform.pivot(0.0F, 15F, 0.0F));
        ModelPartData modelPartData4 = modelPartData.addChild("rightArm", ModelPartBuilder.create().uv(0, 61).cuboid(-17, 1, -3, 8, 14, 6), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData4.addChild("rightArmLower", ModelPartBuilder.create().uv(28, 61).cuboid(-17, 0, -3, 8, 14, 6), ModelTransform.pivot(0.0F, 15F, 0.0F));
        modelPartData5.addChild("leftTorch", ModelPartBuilder.create().uv(0, 0).cuboid("left_torch", 0, 0, 0, 2, 9, 2, Dilation.NONE, 8, 81), ModelTransform.pivot(9F, 15F, 0.0F));
        modelPartData5.addChild("rightTorch", ModelPartBuilder.create().uv(0, 0).cuboid("right_torch", 0, 0, 0, 2, 9, 2, Dilation.NONE, 0, 81), ModelTransform.pivot(11F, 13F, 0.0F));
        return TexturedModelData.of(modelData, 112, 112);
        // right left, -y27 -> -y47
        // right arm
        // left arm
        // horns

    }

    @Override
    public void setAngles(TowerGuardianEntity entity, float limbAngle, float limbDistance, float customAngle, float headYaw, float headPitch) {
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