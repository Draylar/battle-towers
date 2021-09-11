package draylar.battletowers.entity.ai;

import draylar.battletowers.entity.TowerGuardianEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.server.world.ServerWorld;

public abstract class BaseGuardianGoal extends Goal {

    protected final TowerGuardianEntity guardian;
    protected final ServerWorld world;
    private final int totalGoalTime;
    private final int maxCooldown;
    private final float requiredDistance;
    protected int ticks = 0;
    private int cooldown = 0;

    public BaseGuardianGoal(TowerGuardianEntity guardian, int totalGoalTime, int maxCooldown, float requiredDistance) {
        this.guardian = guardian;
        this.world = (ServerWorld) guardian.getEntityWorld();
        this.totalGoalTime = totalGoalTime;
        this.maxCooldown = maxCooldown;
        this.requiredDistance = requiredDistance;
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
            return distanceToTarget <= requiredDistance;
        }

        return false;
    }

    @Override
    public void tick() {
        ticks++;
    }

    @Override
    public boolean shouldContinue() {
        return ticks < totalGoalTime;
    }

    @Override
    public void stop() {
        ticks = 0;
        cooldown = maxCooldown;
    }
}
