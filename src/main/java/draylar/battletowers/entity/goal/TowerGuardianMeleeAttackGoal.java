package draylar.battletowers.entity.goal;

import draylar.battletowers.entity.TowerGuardianEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.Hand;

import java.util.EnumSet;

public class TowerGuardianMeleeAttackGoal extends Goal {

    protected final TowerGuardianEntity mob;
    private final double speed;
    private final boolean pauseWhenMobIdle;
    private Path path;
    private double targetX;
    private double targetY;
    private double targetZ;
    private int updateCountdownTicks;
    private int field_24667;
    private long lastUpdateTime;

    public TowerGuardianMeleeAttackGoal(TowerGuardianEntity towerGuardian, double speed, boolean pauseWhenMobIdle) {
        this.mob = towerGuardian;
        this.speed = speed;
        this.pauseWhenMobIdle = pauseWhenMobIdle;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    @Override
    public boolean canStart() {
        long worldTime = this.mob.world.getTime();

        if (worldTime - this.lastUpdateTime < 20L) {
            return false;
        } else {
            this.lastUpdateTime = worldTime;
            LivingEntity target = this.mob.getTarget();

            if (target == null) {
                return false;
            } else if (!target.isAlive()) {
                return false;
            } else {
                this.path = this.mob.getNavigation().findPathTo(target, 0);

                if (this.path != null) {
                    return true;
                } else {
                    return this.getSquaredMaxAttackDistance() >= this.mob.squaredDistanceTo(target.getX(), target.getY(), target.getZ());
                }
            }
        }
    }

    @Override
    public boolean shouldContinue() {
        LivingEntity target = this.mob.getTarget();

        if (target == null) {
            return false;
        } else if (!target.isAlive()) {
            return false;
        } else if (!this.pauseWhenMobIdle) {
            return !this.mob.getNavigation().isIdle();
        } else if (!this.mob.isInWalkTargetRange(target.getBlockPos())) {
            return false;
        } else {
            return !(target instanceof PlayerEntity) || !target.isSpectator() && !((PlayerEntity) target).isCreative();
        }
    }

    @Override
    public void start() {
        this.mob.getNavigation().startMovingAlong(this.path, this.speed);
        this.mob.setAttacking(true);
        this.updateCountdownTicks = 0;
        this.field_24667 = 0;
    }

    @Override
    public void stop() {
        LivingEntity target = this.mob.getTarget();

        if (!EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(target)) {
            this.mob.setTarget(null);
        }

        this.mob.setAttacking(false);
        this.mob.getNavigation().stop();
    }

    @Override
    public void tick() {
        LivingEntity target = this.mob.getTarget();
        this.mob.getLookControl().lookAt(target, 30.0F, 30.0F);
        double distanceToTarget = this.mob.squaredDistanceTo(target.getX(), target.getY(), target.getZ());
        this.updateCountdownTicks = Math.max(this.updateCountdownTicks - 1, 0);

        if ((this.pauseWhenMobIdle || this.mob.getVisibilityCache().canSee(target)) && this.updateCountdownTicks <= 0 && (this.targetX == 0.0D && this.targetY == 0.0D && this.targetZ == 0.0D || target.squaredDistanceTo(this.targetX, this.targetY, this.targetZ) >= 1.0D || this.mob.getRandom().nextFloat() < 0.05F)) {
            this.targetX = target.getX();
            this.targetY = target.getY();
            this.targetZ = target.getZ();
            this.updateCountdownTicks = 4 + this.mob.getRandom().nextInt(7);

            if (distanceToTarget > 1024.0D) {
                this.updateCountdownTicks += 10;
            } else if (distanceToTarget > 256.0D) {
                this.updateCountdownTicks += 5;
            }

            if (!this.mob.getNavigation().startMovingTo(target, this.speed)) {
                this.updateCountdownTicks += 15;
            }
        }

        this.field_24667 = Math.max(this.field_24667 - 1, 0);
        this.attack(target, distanceToTarget);
    }

    private void attack(LivingEntity target, double squaredDistance) {
        double maxAttackDistance = this.getSquaredMaxAttackDistance();

        if (squaredDistance <= maxAttackDistance && this.field_24667 <= 0) {
            this.method_28346();
            this.mob.swingHand(Hand.MAIN_HAND);
            this.mob.tryAttack(target);
        }
    }

    protected void method_28346() {
        this.field_24667 = 20;
    }

    private double getSquaredMaxAttackDistance() {
        return this.mob.getWidth() * this.mob.getWidth();
    }
}

