package draylar.battletowers.mixin;

import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.function.Supplier;

@Mixin(GenerationSettings.class)
public interface GenerationSettingsAccessor {
    @Mutable
    @Accessor
    void setStructureFeatures(List<Supplier<ConfiguredStructureFeature<?, ?>>> features);
}
