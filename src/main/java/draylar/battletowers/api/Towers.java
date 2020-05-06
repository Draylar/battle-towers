package draylar.battletowers.api;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;

import java.util.HashMap;
import java.util.Map;

public class Towers {

    public static final Tower STONE = new Tower("stone");
    public static final Tower NETHER = new Tower("nether");

    public static final Map<BiomeConditional, Tower> BIOME_ENTRANCES = new HashMap<>();

    public static void init() {
        BIOME_ENTRANCES.put(
                biome -> biome.getCategory().equals(Biome.Category.NETHER),
                NETHER
        );
    }

    public static boolean hasCustomTower(Biome biome) {
        for(BiomeConditional conditional : BIOME_ENTRANCES.keySet()) {
            if(conditional.isValid(biome)) {
                return true;
            }
        }

        return false;
    }

    private Towers() {
        // NO-OP
    }

    public static Tower getEntranceFor(Biome biome) {
        for(Map.Entry<BiomeConditional, Tower> conditional : BIOME_ENTRANCES.entrySet()) {
            if(conditional.getKey().isValid(biome)) {
                return conditional.getValue();
            }
        }

        return STONE;
    }
}
