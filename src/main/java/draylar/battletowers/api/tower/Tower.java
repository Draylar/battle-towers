package draylar.battletowers.api.tower;

import draylar.battletowers.api.data.PoolTemplate;
import draylar.battletowers.api.spawning.BiomeConditional;
import draylar.battletowers.api.data.WeightedIdentifier;
import net.minecraft.util.Identifier;
import robosky.structurehelpers.structure.pool.ElementRange;

import java.util.ArrayList;
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
    private final FloorCollection entrances;
    private final FloorCollection layers;
    private final FloorCollection roofs;
    private final FloorCollection bottoms;
    private transient List<ElementRange> limits = new ArrayList<>();

    // this constructor only exists to stop inlining of the above fields
    public Tower(Map<Identifier, List<WeightedIdentifier>> processors, BiomeConditional biomeConditional, FloorCollection entrances, FloorCollection layers, FloorCollection roofs, FloorCollection bottoms) {
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

    public FloorCollection getEntrances() {
        return entrances;
    }

    public FloorCollection getLayers() {
        return layers;
    }

    public FloorCollection getRoofs() {
        return roofs;
    }

    public FloorCollection getBottoms() {
        return bottoms;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<ElementRange> getLimits() {
        if(limits == null) {
            limits = new ArrayList<>();
        }

        return limits;
    }

    public void addLimit(ElementRange range) {
        limits.add(range);
    }

    public BiomeConditional getBiomeConditional() {
        return biomeConditional;
    }
}
