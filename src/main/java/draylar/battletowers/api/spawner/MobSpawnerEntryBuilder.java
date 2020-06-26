package draylar.battletowers.api.spawner;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.world.MobSpawnerEntry;

public class MobSpawnerEntryBuilder {

    private final CompoundTag spawnTag = new CompoundTag();

    public MobSpawnerEntryBuilder(Identifier entityID) {
        CompoundTag entityTag = new CompoundTag();
        entityTag.putString("id", entityID.toString());
        this.spawnTag.put("Entity", entityTag);
    }

    public MobSpawnerEntry build() {
        return new MobSpawnerEntry(spawnTag);
    }
}
