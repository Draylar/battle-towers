package draylar.battletowers.entity;

import draylar.battletowers.BattleTowers;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TowerGuardEntity extends HostileEntity implements RangedAttackMob {

    private final ServerBossBar bossBar;
    private int currentProjectileCooldown = 0;

    public TowerGuardEntity(EntityType<TowerGuardEntity> entityType, World world) {
        super(entityType, world);
        this.bossBar = (ServerBossBar)(new ServerBossBar(this.getDisplayName(), BossBar.Color.PURPLE, BossBar.Style.PROGRESS)).setDarkenSky(true);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 9.0F));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new FollowTargetGoal<>(this, PlayerEntity.class, false));
    }

    @Override
    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(BattleTowers.CONFIG.bossHP);
        this.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getAttributeInstance(EntityAttributes.KNOCKBACK_RESISTANCE).setBaseValue(10.0D);
        this.getAttributeInstance(EntityAttributes.ATTACK_DAMAGE).setBaseValue(BattleTowers.CONFIG.bossDamageScale);
    }

    @Override
    protected void mobTick() {
        this.bossBar.setPercent(this.getHealth() / this.getMaximumHealth());

        if (getTarget() != null && !world.isClient) {
            currentProjectileCooldown++;

            int requiredProjectileCooldown = 100;
            if (currentProjectileCooldown >= requiredProjectileCooldown) {
                currentProjectileCooldown = 0;
                attack(getTarget(), 1);
            }
        } else currentProjectileCooldown = 0;
    }

    @Override
    protected Identifier getLootTableId() {
        return new Identifier(BattleTowers.CONFIG.bossLootTable);
    }

    @Override
    public boolean cannotDespawn() {
        return true;
    }

    @Override
    public int getSafeFallDistance() {
        return 1000;
    }

    @Override
    public void attack(LivingEntity livingEntity, float v) {
        Vec3d vec3d = this.getRotationVec(1.0F);
        double velocityX = livingEntity.getX() - (this.getX() + vec3d.x * 4.0D);
        double velocityY = livingEntity.getBoundingBox().y1 + (double) (livingEntity.getHeight() / 2.0F) - (0.5D + this.getY() + (double) (this.getHeight() / 2.0F));
        double velocityZ = livingEntity.getZ() - (this.getZ() + vec3d.z * 4.0D);
        FireballEntity fireballEntity = new FireballEntity(world, this, velocityX, velocityY, velocityZ);
        fireballEntity.explosionPower = 1;
        fireballEntity.updatePosition(this.getX() + vec3d.x * 4.0D, this.getY() + (double) (this.getHeight() / 2.0F) + 0.5D, this.getZ() + vec3d.z * 4.0D);
    }

    @Override
    public void onStartedTrackingBy(ServerPlayerEntity player) {
        super.onStartedTrackingBy(player);
        this.bossBar.addPlayer(player);
    }

    @Override
    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        this.bossBar.removePlayer(player);
    }
}
