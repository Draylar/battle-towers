package draylar.battletowers.world;

import net.minecraft.world.gen.feature.AbstractTempleFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

public class BattleTowerFeature extends AbstractTempleFeature<DefaultFeatureConfig> {

    public BattleTowerFeature() {
        super(DefaultFeatureConfig::deserialize);
    }

    @Override
    protected int getSeedModifier() {
        return 52857294;
    }

    @Override
    public StructureStartFactory getStructureStartFactory() {
        return BattleTowerStructureStart::new;
    }

    @Override
    public String getName() {
        return "battletower";
    }

    @Override
    public int getRadius() {
        return 8;
    }
}
