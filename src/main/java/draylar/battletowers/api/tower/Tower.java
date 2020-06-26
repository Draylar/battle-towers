package draylar.battletowers.api.tower;

import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.util.List;
import java.util.Map;

/**
 * Stores information about a themed Battle Tower.
 *
 * <p>A Battle Tower has a name (converted to an {@link net.minecraft.util.Identifier} under the namespace "battletowers",
 * a {@link BiomeConditional} for deciding whether a {@link net.minecraft.world.biome.Biome} is a valid place to spawn,
 * and an entrance, layer, roof, and bottom element list for jigsaw use.
 */
public class Tower {

    private transient String name;
    private final Map<Identifier, List<WeightedIdentifier>> processors;
    private final BiomeConditional biomeConditional;
    private final List<ElementTemplate> entrances;
    private final List<ElementTemplate> layers;
    private final List<ElementTemplate> roofs;
    private final List<ElementTemplate> bottoms;

    public Tower(Map<Identifier, List<WeightedIdentifier>> processors, BiomeConditional biomeConditional, List<ElementTemplate> entrances, List<ElementTemplate> layers, List<ElementTemplate> roofs, List<ElementTemplate> bottoms) {
        this.processors = processors;
        this.biomeConditional = biomeConditional;
        this.entrances = entrances;
        this.layers = layers;
        this.roofs = roofs;
        this.bottoms = bottoms;
    }

    public Map<Identifier, List<WeightedIdentifier>> getProcessors() {
        return processors;
    }

    public List<ElementTemplate> getEntrances() {
        return entrances;
    }

    public List<ElementTemplate> getLayers() {
        return layers;
    }

    public List<ElementTemplate> getRoofs() {
        return roofs;
    }

    public List<ElementTemplate> getBottoms() {
        return bottoms;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public BiomeConditional getBiomeConditional() {
        return biomeConditional;
    }
}
