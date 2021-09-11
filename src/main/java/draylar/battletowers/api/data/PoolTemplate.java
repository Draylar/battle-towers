package draylar.battletowers.api.data;

import net.minecraft.util.Identifier;

import java.util.Map;

public record PoolTemplate(Identifier id, Map<Identifier, Integer> elements, Identifier terminator) {
}
