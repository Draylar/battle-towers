package draylar.battletowers.world;

import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class BattleTowerStructure extends StructureFeature<DefaultFeatureConfig> {

    public BattleTowerStructure() {
        super(DefaultFeatureConfig.CODEC);
    }

    @Override
    public StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
        return BattleTowerStructureStart::new;
    }
}
