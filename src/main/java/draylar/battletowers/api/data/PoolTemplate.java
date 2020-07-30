package draylar.battletowers.api.data;

import net.minecraft.util.Identifier;

import java.util.Map;

public class PoolTemplate {

    private final Identifier  id;
    private final Map<Identifier, Integer> elements;
    private final Identifier terminator;

    public PoolTemplate(Identifier id, Map<Identifier, Integer> elements, Identifier terminator) {
        this.id = id;
        this.elements = elements;
        this.terminator = terminator;
    }

    public Map<Identifier, Integer> getElements() {
        return elements;
    }

    public Identifier getId() {
        return id;
    }

    public Identifier getTerminator() {
        return terminator;
    }
}
