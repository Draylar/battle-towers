package draylar.battletowers.api.spawner;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.world.MobSpawnerEntry;

public class MobSpawnerEntryBuilder {

    private final NbtCompound spawnTag = new NbtCompound();

    public MobSpawnerEntryBuilder(Identifier entityID) {
        NbtCompound entityTag = new NbtCompound();
        entityTag.putString("id", entityID.toString());
        this.spawnTag.put("Entity", entityTag);
    }

    public MobSpawnerEntry build() {
        return new MobSpawnerEntry(spawnTag);
    }
}
