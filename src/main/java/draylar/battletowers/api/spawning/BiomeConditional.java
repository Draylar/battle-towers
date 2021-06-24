package draylar.battletowers.api.spawning;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.biome.Biome;

import java.util.List;

/**
 * Stores a collection of conditions related to whether a Battle Tower can spawn in a biome.
 *
 * <p>A Battle Tower can spawn based on biome IDs, biome categories ({@link Biome.Category}, or a collection of both.
 * Note that biome IDs take priority over categories.
 * If a biome ID is valid but not attached to a registered biome, only ??? will accept the conditional.
 */
public record BiomeConditional(List<Identifier> biomeIDs, List<String> biomeCategories) {


    public boolean isValid(Biome biome) {
        if (biomeIDs != null) {
            Identifier testBiomeID = BuiltinRegistries.BIOME.getId(biome);

            for (Identifier biomeID : biomeIDs) {
                if (testBiomeID != null && testBiomeID.equals(biomeID)) {
                    return true;
                }
            }
        }

        if (biomeCategories != null) {
            for (String category : biomeCategories) {
                if (biome.getCategory().getName().equals(category)) {
                    return true;
                }
            }
        }

        return false;
    }
}
