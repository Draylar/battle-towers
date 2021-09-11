package draylar.battletowers.entity.ai;

import draylar.battletowers.entity.TowerGuardianEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;

import java.util.EnumSet;
import java.util.Timer;
import java.util.TimerTask;

public class GuardianAttackGoal extends Goal {

    protected final TowerGuardianEntity guardian;
    private final double speed;
    private final boolean pauseWhenMobIdle;
    private Path path;
    private double targetX;
    private double targetY;
    private double targetZ;
    private int updateCountdownTicks;
    private int field_24667;
    private long lastUpdateTime;
    private ServerWorld world;

    public GuardianAttackGoal(TowerGuardianEntity guardian, double speed, boolean pauseWhenMobIdle) {
        this.guardian = guardian;
        this.speed = speed;
        this.pauseWhenMobIdle = pauseWhenMobIdle;
        this.world = (ServerWorld) guardian.getEntityWorld();
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    @Override
    public boolean canStart() {
        long worldTime = this.guardian.world.getTime();

        // Entity can't attack within 1 second of the world loading.
        if (worldTime - lastUpdateTime < 20L) {
            return false;
        } else {
            lastUpdateTime = worldTime;
            LivingEntity target = guardian.getTarget();

            // This attack goal can start if the Tower Guardian has a target within distance (or if the player is within the attack range of the target)..
            if (target == null) {
                return false;
            } else if (!target.isAlive()) {
                return false;
            } else {
                path = guardian.getNavigation().findPathTo(target, 0);
                if (path != null) {
                    return true;
                } else {
                    return getSquaredMaxAttackDistance() >= guardian.squaredDistanceTo(target.getX(), target.getY(), target.getZ());
                }
            }
        }
    }

    @Override
    public boolean shouldContinue() {
        LivingEntity target = this.guardian.getTarget();

        // This task can continue if the target is valid/alive and within a reasonable distance.
        if (target == null) {
            return false;
        } else if (!target.isAlive()) {
            return false;
        } else if (!pauseWhenMobIdle) {
            return !guardian.getNavigation().isIdle();
        } else if (!guardian.isInWalkTargetRange(target.getBlockPos())) {
            return false;
        } else {
            return !(target instanceof PlayerEntity) || !target.isSpectator() && !((PlayerEntity) target).isCreative();
        }
    }

    @Override
    public void start() {
        guardian.getNavigation().startMovingAlong(this.path, this.speed);
        guardian.setAttacking(true);
        updateCountdownTicks = 0;
        field_24667 = 0;
    }

    @Override
    public void stop() {
        LivingEntity target = this.guardian.getTarget();

        if (!EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(target)) {
            this.guardian.setTarget(null);
        }

        this.guardian.setAttacking(false);
        this.guardian.getNavigation().stop();
    }

    @Override
    public void tick() {
        LivingEntity target = this.guardian.getTarget();
        this.guardian.getLookControl().lookAt(target, 30.0F, 30.0F);
        double distanceToTarget = this.guardian.squaredDistanceTo(target.getX(), target.getY(), target.getZ());
        this.updateCountdownTicks = Math.max(this.updateCountdownTicks - 1, 0);

        if ((this.pauseWhenMobIdle || this.guardian.getVisibilityCache().canSee(target)) && this.updateCountdownTicks <= 0 && (this.targetX == 0.0D && this.targetY == 0.0D && this.targetZ == 0.0D || target.squaredDistanceTo(this.targetX, this.targetY, this.targetZ) >= 1.0D || this.guardian.getRandom().nextFloat() < 0.05F)) {
            this.targetX = target.getX();
            this.targetY = target.getY();
            this.targetZ = target.getZ();
            this.updateCountdownTicks = 4 + this.guardian.getRandom().nextInt(7);

            if (distanceToTarget > 1024.0D) {
                this.updateCountdownTicks += 10;
            } else if (distanceToTarget > 256.0D) {
                this.updateCountdownTicks += 5;
            }

            if (!this.guardian.getNavigation().startMovingTo(target, this.speed)) {
                this.updateCountdownTicks += 15;
            }
        }

        this.field_24667 = Math.max(this.field_24667 - 1, 0);
        this.attack(target, distanceToTarget);
    }

    private void attack(LivingEntity target, double squaredDistance) {
        double maxAttackDistance = this.getSquaredMaxAttackDistance();

        if (squaredDistance <= maxAttackDistance && this.field_24667 <= 0) {
            method_28346();
            guardian.tryAttack(target);
        }
    }

    protected void method_28346() {
        this.field_24667 = 20;
    }

    private double getSquaredMaxAttackDistance() {
        return guardian.getWidth() * guardian.getWidth() * 4;
    }
}

