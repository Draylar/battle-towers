package draylar.battletowers.api;

import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class AnimationQueue {

    private final Map<String, Byte> animationByID = new HashMap<>();
    private final Map<Byte, String> idByAnimation = new HashMap<>();
    private final Queue<String> queue = new ArrayDeque<>();
    private final LivingEntity entity;
    private final World world;
    private String current = null;

    public AnimationQueue(LivingEntity entity) {
        this.entity = entity;
        this.world = entity.world;
    }

    public void registerAnimation(String animation, int id) {
        byte byteId = (byte) (id + 100);
        animationByID.put(animation, byteId);
        idByAnimation.put(byteId, animation);

        // TODO: CRASH IF DUPLICATE IDS, OR HANDLE BEHIND SCENES
    }

    public void queueAnimation(String animation) {
        if(world.isClient) {
            queue.add(animation);
        } else {
            entity.world.sendEntityStatus(entity, animationByID.get(animation));
        }
    }

    public <T extends IAnimatable> PlayState tick(AnimationEvent<T> event) {
        return tick(event, e -> PlayState.CONTINUE);
    }

    public <T extends IAnimatable> PlayState tick(AnimationEvent<T> event, AnimationController.IAnimationPredicate<T> fallback) {
        // If an animation has been queued up, run it...
        if(!queue.isEmpty()) {
            String queued = queue.poll();
            current = queued;
            event.getController().setAnimation(new AnimationBuilder().addAnimation(queued));
            event.getController().markNeedsReload();
            return PlayState.CONTINUE;
        }

        // If an animation is currently running, wait for it to finish, and stop it afterwards.
        if(current != null) {
            if (event.getController().getAnimationState().equals(AnimationState.Stopped)) {
                current = null;
                event.getController().clearAnimationCache();
                return PlayState.CONTINUE;
            }

            return PlayState.CONTINUE;
        }

        // At this point, no animations have been queued. Fallback to the default behavior.
        return fallback.test(event);
    }

    public void handleStatus(byte status) {
        @Nullable String animation = idByAnimation.get(status);
        if(animation != null) {
            queueAnimation(animation);
        }
    }
}
