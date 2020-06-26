package draylar.battletowers.api.tower;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

import java.util.List;

public class BiomeConditional {

    private final List<Identifier> biomeIDs;
    private final List<String> biomeCategories;

    public BiomeConditional(List<Identifier> biomeIDs, List<String> biomeCategories) {
        this.biomeIDs = biomeIDs;
        this.biomeCategories = biomeCategories;
    }

    public boolean isValid(Biome biome) {
        if(biomeIDs != null) {
            Identifier testBiomeID = Registry.BIOME.getId(biome);

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
