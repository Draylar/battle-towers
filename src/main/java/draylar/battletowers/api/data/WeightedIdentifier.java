package draylar.battletowers.api.data;

import net.minecraft.util.Identifier;

public record WeightedIdentifier(Identifier id, int weight) {
}
