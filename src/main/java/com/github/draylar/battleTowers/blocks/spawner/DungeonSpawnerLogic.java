package com.github.draylar.battleTowers.blocks.spawner;

import com.google.common.collect.Lists;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sortme.MobSpawnerEntry;
import net.minecraft.util.ChatUtil;
import net.minecraft.util.Identifier;
import net.minecraft.util.InvalidIdentifierException;
import net.minecraft.util.WeightedPicker;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BoundingBox;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public abstract class DungeonSpawnerLogic
{
    private static final Logger LOGGER = LogManager.getLogger();

    public int spawnDelay = 20;

    private final List<MobSpawnerEntry> spawnPotentials = Lists.newArrayList();
    private MobSpawnerEntry spawnEntry = new MobSpawnerEntry();

    private double field_9161;
    private double field_9159;

    public int minSpawnDelay = 200;
    public int maxSpawnDelay = 800;
    public int spawnCount = 4;

    private Entity renderedEntity;

    public int maxNearbyEntities = 6;
    public int requiredPlayerRange = 16;
    public int spawnRange = 4;

    public DefaultParticleType particleBackground = ParticleTypes.SMOKE;
    public DefaultParticleType particleImportant = ParticleTypes.FLAME;

    public DungeonSpawnerLogic()
    {

    }

    private Identifier method_8281() {
        String string_1 = this.spawnEntry.getEntityTag().getString("id");

        try {
            return ChatUtil.isEmpty(string_1) ? null : new Identifier(string_1);
        } catch (InvalidIdentifierException var4) {
            BlockPos blockPos_1 = this.getPos();
            LOGGER.warn("Invalid entity id '{}' at spawner {}:[{},{},{}]", string_1, this.getWorld().dimension.getType(), blockPos_1.getX(), blockPos_1.getY(), blockPos_1.getZ());
            return null;
        }
    }


    public void setEntityType(EntityType<?> entityType_1) {
        this.spawnEntry.getEntityTag().putString("id", Registry.ENTITY_TYPE.getId(entityType_1).toString());
    }


    private boolean isPlayerInRange() {
        BlockPos blockPos_1 = this.getPos();
        return this.getWorld().findVisiblePlayer((double)blockPos_1.getX() + 0.5D, (double)blockPos_1.getY() + 0.5D, (double)blockPos_1.getZ() + 0.5D, (double) this.requiredPlayerRange);
    }


    public void update()
    {

        if (!this.isPlayerInRange())
        {
            this.field_9159 = this.field_9161;
        }

        else
        {
            // we get here
            World world = this.getWorld();
            BlockPos blockPos = this.getPos();

            if (world.isClient)
            {
                double double_1 = (double) ((float) blockPos.getX() + world.random.nextFloat());
                double double_2 = (double) ((float) blockPos.getY() + world.random.nextFloat());
                double double_3 = (double) ((float) blockPos.getZ() + world.random.nextFloat());

                world.addParticle(particleBackground, double_1, double_2, double_3, 0.0D, 0.0D, 0.0D);
                world.addParticle(particleImportant, double_1, double_2, double_3, 0.0D, 0.0D, 0.0D);

                if (this.spawnDelay > 0) --this.spawnDelay;

                this.field_9159 = this.field_9161;
                this.field_9161 = (this.field_9161 + (double) (1000.0F / ((float) this.spawnDelay + 200.0F))) % 360.0D;
            }

            else
            {
                if (this.spawnDelay == -1) this.method_8282();

                if (this.spawnDelay > 0)
                {
                    --this.spawnDelay;
                    return;
                }

                boolean boolean_1 = false;
                int int_1 = 0;

                while (true)
                {
                    if (int_1 >= this.spawnCount)
                    {
                        if (boolean_1)
                        {
                            this.method_8282();
                        }
                        break;
                    }

                    CompoundTag compoundTag_1 = this.spawnEntry.getEntityTag();
                    Optional<EntityType<?>> optional_1 = EntityType.fromTag(compoundTag_1);

                    if (!optional_1.isPresent())
                    {
                        this.method_8282();
                        return;
                    }

                    ListTag listTag_1 = compoundTag_1.getList("Pos", 6);
                    int int_2 = listTag_1.size();

                    double double_4 = int_2 >= 1 ? listTag_1.getDouble(0) : (double) blockPos.getX() + (world.random.nextDouble() - world.random.nextDouble()) * (double) this.spawnRange + 0.5D;
                    double double_5 = int_2 >= 2 ? listTag_1.getDouble(1) : (double) (blockPos.getY() + world.random.nextInt(3) - 1);
                    double double_6 = int_2 >= 3 ? listTag_1.getDouble(2) : (double) blockPos.getZ() + (world.random.nextDouble() - world.random.nextDouble()) * (double) this.spawnRange + 0.5D;

                    if (world.method_18026(((EntityType) optional_1.get()).createSimpleBoundingBox(double_4, double_5, double_6)))
                    {
                        label94:
                        {
                            Entity entity_1 = EntityType.loadEntityWithPassengers(compoundTag_1, world, (entity_1x) ->
                            {
                                entity_1x.setPositionAndAngles(double_4, double_5, double_6, entity_1x.yaw, entity_1x.pitch);
                                return entity_1x;
                            });

                            if (entity_1 == null)
                            {
                                this.method_8282();
                                return;
                            }

                            int int_3 = world.getVisibleEntities(entity_1.getClass(), (new BoundingBox((double) blockPos.getX(), (double) blockPos.getY(), (double) blockPos.getZ(), (double) (blockPos.getX() + 1), (double) (blockPos.getY() + 1), (double) (blockPos.getZ() + 1))).expand((double) this.spawnRange)).size();
                            if (int_3 >= this.maxNearbyEntities)
                            {
                                this.method_8282();
                                return;
                            }

                            entity_1.setPositionAndAngles(entity_1.x, entity_1.y, entity_1.z, world.random.nextFloat() * 360.0F, 0.0F);
                            if (entity_1 instanceof MobEntity)
                            {
                                MobEntity mobEntity_1 = (MobEntity) entity_1;
                                if (!mobEntity_1.canSpawn(world, SpawnType.SPAWNER) || !mobEntity_1.method_5957(world))
                                {
                                    break label94;
                                }

                                if (this.spawnEntry.getEntityTag().getSize() == 1 && this.spawnEntry.getEntityTag().containsKey("id", 8))
                                {
                                    ((MobEntity) entity_1).prepareEntityData(world, world.getLocalDifficulty(new BlockPos(entity_1)), SpawnType.SPAWNER, (EntityData) null, (CompoundTag) null);
                                }
                            }



                            this.spawnEntity(entity_1);
                            if (entity_1 instanceof MobEntity)
                            {
                                ((MobEntity) entity_1).method_5990();
                            }

                            boolean_1 = true;
                        }
                    }

                    ++int_1;
                }
            }

        }
    }


    private void spawnEntity(Entity entity_1) {
        if (this.getWorld().spawnEntity(entity_1)) {
            Iterator var2 = entity_1.getPassengerList().iterator();

            while(var2.hasNext()) {
                Entity entity_2 = (Entity)var2.next();
                this.spawnEntity(entity_2);
            }

        }
    }


    private void method_8282()
    {
        if (this.maxSpawnDelay <= this.minSpawnDelay) this.spawnDelay = this.minSpawnDelay;

        else
        {
            int delayDifference = this.maxSpawnDelay - this.minSpawnDelay;
            this.spawnDelay = this.minSpawnDelay + this.getWorld().random.nextInt(delayDifference);
        }

        if (!this.spawnPotentials.isEmpty())
            this.setSpawnEntry(WeightedPicker.getRandom(this.getWorld().random, this.spawnPotentials));

        this.method_8273(1);
    }


    public void deserialize(CompoundTag compoundTag_1)
    {
        this.spawnDelay = compoundTag_1.getShort("Delay");
        this.spawnPotentials.clear();
        if (compoundTag_1.containsKey("SpawnPotentials", 9))
        {
            ListTag listTag_1 = compoundTag_1.getList("SpawnPotentials", 10);

            for (int int_1 = 0; int_1 < listTag_1.size(); ++int_1)
            {
                this.spawnPotentials.add(new MobSpawnerEntry(listTag_1.getCompoundTag(int_1)));
            }
        }

        if (compoundTag_1.containsKey("SpawnData", 10))
            this.setSpawnEntry(new MobSpawnerEntry(1, compoundTag_1.getCompound("SpawnData")));

         else if (!this.spawnPotentials.isEmpty())
            this.setSpawnEntry((MobSpawnerEntry) WeightedPicker.getRandom(this.getWorld().random, this.spawnPotentials));

        if (compoundTag_1.containsKey("MinSpawnDelay", 99))
        {
            this.minSpawnDelay = compoundTag_1.getShort("MinSpawnDelay");
            this.maxSpawnDelay = compoundTag_1.getShort("MaxSpawnDelay");
            this.spawnCount = compoundTag_1.getShort("SpawnCount");
        }

        if (compoundTag_1.containsKey("MaxNearbyEntities", 99))
        {
            this.maxNearbyEntities = compoundTag_1.getShort("MaxNearbyEntities");
            this.requiredPlayerRange = compoundTag_1.getShort("RequiredPlayerRange");
        }

        if (compoundTag_1.containsKey("SpawnRange", 99))
            this.spawnRange = compoundTag_1.getShort("SpawnRange");

        if(compoundTag_1.containsKey("Particle1"))
            this.particleImportant = (DefaultParticleType) Registry.PARTICLE_TYPE.get(new Identifier(compoundTag_1.getString("Particle1")));

        if(compoundTag_1.containsKey("Particle2"))
            this.particleBackground = (DefaultParticleType) Registry.PARTICLE_TYPE.get(new Identifier(compoundTag_1.getString("Particle2")));

        if (this.getWorld() != null)
            this.renderedEntity = null;
    }


    public CompoundTag serialize(CompoundTag compoundTag_1) {
        Identifier identifier_1 = this.method_8281();
        if (identifier_1 == null) {
            return compoundTag_1;
        } else {
            compoundTag_1.putShort("Delay", (short)this.spawnDelay);
            compoundTag_1.putShort("MinSpawnDelay", (short)this.minSpawnDelay);
            compoundTag_1.putShort("MaxSpawnDelay", (short)this.maxSpawnDelay);
            compoundTag_1.putShort("SpawnCount", (short)this.spawnCount);
            compoundTag_1.putShort("MaxNearbyEntities", (short)this.maxNearbyEntities);
            compoundTag_1.putShort("RequiredPlayerRange", (short)this.requiredPlayerRange);
            compoundTag_1.putShort("SpawnRange", (short)this.spawnRange);
            compoundTag_1.put("SpawnData", this.spawnEntry.getEntityTag().copy());

            compoundTag_1.putString("Particle1", particleImportant.asString());
            compoundTag_1.putString("Particle2", particleBackground.asString());

            ListTag listTag_1 = new ListTag();
            if (this.spawnPotentials.isEmpty()) {
                listTag_1.add(this.spawnEntry.serialize());
            } else {
                Iterator var4 = this.spawnPotentials.iterator();

                while(var4.hasNext()) {
                    MobSpawnerEntry mobSpawnerEntry_1 = (MobSpawnerEntry)var4.next();
                    listTag_1.add(mobSpawnerEntry_1.serialize());
                }
            }

            compoundTag_1.put("SpawnPotentials", listTag_1);
            return compoundTag_1;
        }
    }


    @Environment(EnvType.CLIENT)
    public Entity getRenderedEntity() {
        if (this.renderedEntity == null) {
            this.renderedEntity = EntityType.loadEntityWithPassengers(this.spawnEntry.getEntityTag(), this.getWorld(), Function.identity());
            if (this.spawnEntry.getEntityTag().getSize() == 1 && this.spawnEntry.getEntityTag().containsKey("id", 8) && this.renderedEntity instanceof MobEntity) {
                ((MobEntity)this.renderedEntity).prepareEntityData(this.getWorld(), this.getWorld().getLocalDifficulty(new BlockPos(this.renderedEntity)), SpawnType.SPAWNER, (EntityData)null, (CompoundTag)null);
            }
        }

        return this.renderedEntity;
    }


    public boolean method_8275(int int_1) {
        if (int_1 == 1 && this.getWorld().isClient) {
            this.spawnDelay = this.minSpawnDelay;
            return true;
        } else {
            return false;
        }
    }


    public void setSpawnEntry(MobSpawnerEntry mobSpawnerEntry_1) {
        this.spawnEntry = mobSpawnerEntry_1;
    }


    public abstract void method_8273(int var1);

    public abstract World getWorld();

    public abstract BlockPos getPos();

    @Environment(EnvType.CLIENT)
    public double method_8278() {
        return this.field_9161;
    }

    @Environment(EnvType.CLIENT)
    public double method_8279() {
        return this.field_9159;
    }
}
