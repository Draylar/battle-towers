package com.github.draylar.battleTowers.entity.tower_guard;

import com.github.draylar.battleTowers.common.Entities;
import com.github.draylar.battleTowers.config.ConfigHolder;
import net.minecraft.class_1399;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttacker;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class TowerGuardEntity extends HostileEntity implements RangedAttacker
{
    private int currentProjectileCooldown = 0;
    private final int requiredProjectileCooldown = 100;

    public TowerGuardEntity(World world)
    {
        super(Entities.towerGuard, world);
        setSize(2, 6);
    }

    @Override
    protected void initGoals()
    {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 9.0F));
        this.targetSelector.add(1, new class_1399(this, new Class[0]));
        this.targetSelector.add(2, new FollowTargetGoal(this, PlayerEntity.class, false));
    }

    @Override
    protected void initAttributes()
    {
        super.initAttributes();
        this.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(ConfigHolder.configInstance.bossHP);
        this.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getAttributeInstance(EntityAttributes.KNOCKBACK_RESISTANCE).setBaseValue(10.0D);
        this.getAttributeInstance(EntityAttributes.ATTACK_DAMAGE).setBaseValue(ConfigHolder.configInstance.bossDamageScale);
    }

    @Override
    protected void mobTick()
    {
        if(getTarget() != null && !world.isClient)
        {
            currentProjectileCooldown++;

            if (currentProjectileCooldown >= requiredProjectileCooldown)
            {
                currentProjectileCooldown = 0;
                attack(getTarget(), 1);
            }
        }

        else currentProjectileCooldown = 0;
    }

    @Override
    protected Identifier getLootTableId()
    {
        return ConfigHolder.configInstance.bossLootTable;
    }

    @Override
    protected boolean cannotDespawn()
    {
        return true;
    }

    @Override
    public int getSafeFallDistance()
    {
        return 1000;
    }


    @Override
    public void attack(LivingEntity livingEntity, float v)
    {
        ProjectileEntity projectileEntity_1 = new ArrowEntity(world);

        double double_1 = livingEntity.x - this.x;
        double double_2 = livingEntity.getBoundingBox().minY + (double)(livingEntity.getHeight() / 3.0F) - projectileEntity_1.y;
        double double_3 = livingEntity.z - this.z;
        double double_4 = (double) MathHelper.sqrt(double_1 * double_1 + double_3 * double_3);

        projectileEntity_1.setVelocity(double_1, double_2 + double_4 * 0.20000000298023224D, double_3, 1.6F, (float)(14 - this.world.getDifficulty().getId() * 4));
        this.playSound(SoundEvents.ENTITY_WITHER_SHOOT, 1.0F, 1.0F / (this.getRand().nextFloat() * 0.4F + 0.8F));

        this.world.spawnEntity(projectileEntity_1);
    }

    @Override
    public boolean hasArmsRaised()
    {
        return false;
    }

    @Override
    public void setArmsRaised(boolean b)
    {

    }
}
