package draylar.battletowers.world;

import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class BattleTowerFeature extends StructureFeature<DefaultFeatureConfig> {

    public BattleTowerFeature() {
        super(DefaultFeatureConfig.CODEC);
    }

    @Override
    public StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
        return BattleTowerStructureStart::new;
    }
}
