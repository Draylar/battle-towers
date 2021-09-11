package draylar.battletowers.entity.ai;

import draylar.battletowers.entity.TowerGuardianEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class GuardianSpinGoal extends BaseGuardianGoal{

    public GuardianSpinGoal(TowerGuardianEntity guardian) {
        super(guardian, 15 * 20, 20 * 20, 15.0f);
        setControls(EnumSet.of(Control.MOVE, Control.LOOK, Control.TARGET, Control.JUMP));
    }

    @Override
    public void start() {
        super.start();
        guardian.getQueue().queueAnimation(TowerGuardianEntity.ANIMATION_SPIN);
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity target = guardian.getTarget();

        if(ticks >= 40) {
            for (LivingEntity entity : world.getEntitiesByClass(LivingEntity.class, guardian.getBoundingBox().expand(2, 1, 2), it -> it != guardian)) {
                entity.damage(DamageSource.GENERIC, 4.0f);
            }

            if(target != null) {
                Vec3d targetPos = target.getPos();
                guardian.getMoveControl().moveTo(targetPos.getX(), targetPos.getY(), targetPos.getZ(), 1.0f);
            }
        }
    }
}
