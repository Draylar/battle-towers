package draylar.battletowers.api.tower;

import net.minecraft.util.Identifier;

import java.util.List;

/**
 * Represents a template for a {@link net.minecraft.structure.pool.StructurePoolElement}, or a "piece" of a Battle Tower.
 */
public class ElementTemplate {

    private final Identifier id;
    private final List<Identifier> lootTables;
    private final List<Identifier> entities;

    public ElementTemplate(Identifier id, List<Identifier> lootTables, List<Identifier> entities) {
        this.id = id;
        this.lootTables = lootTables;
        this.entities = entities;
    }

    public Identifier getId() {
        return id;
    }

    public List<Identifier> getLootTables() {
        return lootTables;
    }

    public List<Identifier> getEntities() {
        return entities;
    }
}
