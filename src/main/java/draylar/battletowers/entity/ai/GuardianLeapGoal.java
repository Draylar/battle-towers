package draylar.battletowers.entity.ai;

import draylar.battletowers.entity.TowerGuardianEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ShieldItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class GuardianLeapGoal extends Goal {

    private final TowerGuardianEntity guardian;
    private final ServerWorld world;
    private int ticks = 0;
    private int cooldown = 0;

    public GuardianLeapGoal(TowerGuardianEntity guardian) {
        this.guardian = guardian;
        this.world = (ServerWorld) guardian.getEntityWorld();
        setControls(EnumSet.of(Control.MOVE, Control.JUMP, Control.LOOK));
    }

    @Override
    public boolean canStart() {
        // tick cooldown
        if(cooldown > 0) {
            cooldown--;
            return false;
        }

        boolean validTarget = guardian.getTarget() != null;
        if(validTarget) {
            float distanceToTarget = guardian.distanceTo(guardian.getTarget());
            return distanceToTarget <= 4.0f;
        }

        return false;
    }

    @Override
    public void tick() {
        if(ticks == 0) {
            guardian.getQueue().queueAnimation(TowerGuardianEntity.ANIMATION_LEAP);
        }

        // Once the tower guardian lands, deal a shockwave effect.
        if(ticks == 40) {
            world.getEntitiesByClass(LivingEntity.class, new Box(guardian.getBlockPos()).expand(6, 2, 6), entity -> entity != guardian).forEach(entity -> {
                Vec3d difference = entity.getPos().subtract(guardian.getPos()).normalize();
                difference = new Vec3d(difference.getX(), Math.max(0.2, difference.getY()), difference.getZ());

                // If the player has a shield, they take less knockback.
                boolean hasShield = false;
                if(entity instanceof PlayerEntity player) {
                    hasShield = player.getActiveItem().getItem() instanceof ShieldItem;
                }

                double distanceToGuardian = entity.distanceTo(guardian);
                float scalar = (float) Math.max(1.0f, 10f - distanceToGuardian);
                entity.damage(DamageSource.GENERIC, scalar);
                entity.setVelocity(difference.multiply(hasShield ? 0.1 : 0.35).multiply(scalar));
                entity.velocityModified = true;
                entity.velocityDirty = true;
            });

            world.spawnParticles(ParticleTypes.EXPLOSION, guardian.getX(), guardian.getY(), guardian.getZ(), 5, 3, 1, 3, 0);
        }

        ticks++;
    }

    @Override
    public boolean shouldContinue() {
        return ticks < 100;
    }

    @Override
    public void stop() {
        ticks = 0;
        cooldown = 20 * 5;
    }
}
