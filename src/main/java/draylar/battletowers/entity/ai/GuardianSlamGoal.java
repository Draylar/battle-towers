package draylar.battletowers.entity.ai;

import draylar.battletowers.entity.TowerGuardianEntity;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ShieldItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class GuardianSlamGoal extends BaseGuardianGoal {

    public GuardianSlamGoal(TowerGuardianEntity guardian) {
        super(guardian, 80, 100, 5.0f);
        setControls(EnumSet.of(Control.MOVE, Control.LOOK));
    }

    @Override
    public void start() {
        guardian.getQueue().queueAnimation(TowerGuardianEntity.ANIMATION_SLAM);
    }

    @Override
    public void tick() {
        super.tick();

        guardian.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, guardian.getTarget().getPos());

        if(ticks == 40) {
            Vec3d outPos = guardian.getPos().add(guardian.getRotationVector().multiply(5, 0, 5));
            BlockPos out = new BlockPos(outPos);

            world.getEntitiesByClass(LivingEntity.class, new Box(out).expand(4, 2, 4), entity -> entity != guardian).forEach(entity -> {
                Vec3d difference = entity.getPos().subtract(outPos).normalize();
                difference = new Vec3d(difference.getX(), Math.max(0.2, difference.getY()), difference.getZ());

                // If the player has a shield, they take less knockback.
                boolean hasShield = false;
                if(entity instanceof PlayerEntity player) {
                    hasShield = player.getActiveItem().getItem() instanceof ShieldItem;
                }

                double distanceToGuardian = entity.squaredDistanceTo(outPos);
                float scalar = (float) Math.max(1.0f, 10f - distanceToGuardian);
                entity.damage(DamageSource.GENERIC, scalar);
                entity.setVelocity(difference.multiply(hasShield ? 0.1 : 0.35).multiply(scalar));
                entity.velocityModified = true;
                entity.velocityDirty = true;
            });

            world.spawnParticles(ParticleTypes.EXPLOSION, out.getX(), out.getY(), out.getZ(), 5, 3, 1, 3, 0);
        }
    }
}
