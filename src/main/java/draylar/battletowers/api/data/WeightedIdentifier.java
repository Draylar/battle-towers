package draylar.battletowers.api.data;

import net.minecraft.util.Identifier;

// Do not convert this into a record; needs to be serialized.
public class WeightedIdentifier {

    private final Identifier id;
    private final int weight;

    public WeightedIdentifier(Identifier id, int weight) {
        this.id = id;
        this.weight = weight;
    }

    public Identifier getId() {
        return id;
    }

    public int getWeight() {
        return weight;
    }
}