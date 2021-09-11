// Made with Model Converter by Globox_Z
// Generate all required imports
package draylar.battletowers.client.model;


import draylar.battletowers.BattleTowers;
import draylar.battletowers.entity.TowerGuardianEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;


public class TowerGuardEntityModel extends AnimatedGeoModel<TowerGuardianEntity> {

    private static final Identifier MODEL_LOCATION = BattleTowers.id("geo/tower_guardian.geo.json");
    private static final Identifier TEXTURE_LOCATION = BattleTowers.id("textures/entity/tower_guardian.png");
    private static final Identifier ANIMATION_LOCATION = BattleTowers.id("animations/tower_guardian.animation.json");

    @Override
    public Identifier getModelLocation(TowerGuardianEntity object) {
        return MODEL_LOCATION;
    }

    @Override
    public Identifier getTextureLocation(TowerGuardianEntity object) {
        return TEXTURE_LOCATION;
    }

    @Override
    public Identifier getAnimationFileLocation(TowerGuardianEntity animatable) {
        return ANIMATION_LOCATION;
    }
}