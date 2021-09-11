package draylar.battletowers.api.tower;

import com.google.common.collect.ImmutableList;
import draylar.battletowers.BattleTowers;
import draylar.battletowers.api.Towers;
import draylar.battletowers.api.data.PoolTemplate;
import draylar.battletowers.api.data.WeightedIdentifier;
import draylar.battletowers.api.spawning.BiomeConditional;
import draylar.staticcontent.api.ContentData;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.util.Identifier;
import robosky.structurehelpers.structure.pool.ElementRange;

import java.util.List;
import java.util.Map;

/**
 * Stores information about a themed Battle Tower.
 *
 * <p>A Battle Tower has a name (converted to an {@link net.minecraft.util.Identifier} under the namespace "battletowers",
 * a {@link BiomeConditional} for deciding whether a {@link net.minecraft.world.biome.Biome} is a valid place to spawn,
 * and an entrance, layer, roof, and bottom element list for jigsaw use.
 */
public class Tower implements ContentData {

    private final Map<Identifier, List<WeightedIdentifier>> processors;
    private final BiomeConditional biomeConditional;
    private final FloorCollection entrances;
    private final FloorCollection layers;
    private final FloorCollection roofs;
    private final FloorCollection bottoms;
    private final List<PoolTemplate> extraPools;
    private transient String name;
    private transient ImmutableList.Builder<ElementRange> limits = new ImmutableList.Builder<>();
    private transient StructurePool startPool = null;

    // this constructor only exists to stop inlining of the above fields
    public Tower(Map<Identifier, List<WeightedIdentifier>> processors, BiomeConditional biomeConditional, FloorCollection entrances, FloorCollection layers, FloorCollection roofs, FloorCollection bottoms, List<PoolTemplate> extraPools) {
        this.processors = processors;
        this.biomeConditional = biomeConditional;
        this.entrances = entrances;
        this.layers = layers;
        this.roofs = roofs;
        this.bottoms = bottoms;
        this.extraPools = extraPools;
    }

    @Override
    public void register(Identifier identifier) {
        // battletowers:battletowers/towers/frosted -> battletowers:frosted
        identifier = new Identifier(identifier.getNamespace(), identifier.getPath().replace("battletowers/towers/", "").replace(".json", ""));

        // save file name as tower name
        name = identifier.getPath();
        Towers.register(identifier, this);

        // Stone ID should be default tower
        // todo: we can do this much better
        if (Towers.DEFAULT_TOWER == null && identifier.equals(BattleTowers.id("stone"))) {
            Towers.DEFAULT_TOWER = this;
        }
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImmutableList<ElementRange> getLimits() {
        if (limits == null) {
            limits = new ImmutableList.Builder<>();
        }

        return limits.build();
    }

    public void addLimit(ElementRange range) {
        if (limits == null) {
            limits = new ImmutableList.Builder<>();
        }

        limits.add(range);
    }

    public BiomeConditional getBiomeConditional() {
        return biomeConditional;
    }

    public List<PoolTemplate> getExtraPools() {
        return extraPools;
    }

    public StructurePool getStartPool() {
        return startPool;
    }

    public void setStartPool(StructurePool startPool) {
        this.startPool = startPool;
    }
}
