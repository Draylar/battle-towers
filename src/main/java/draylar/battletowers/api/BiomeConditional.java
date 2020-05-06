package draylar.battletowers.api;

import net.minecraft.world.biome.Biome;

@FunctionalInterface
public interface BiomeConditional {
    boolean isValid(Biome biome);
}
