package draylar.battletowers.entity;

import draylar.battletowers.BattleTowers;
import draylar.battletowers.api.AnimationQueue;
import draylar.battletowers.entity.ai.GuardianAttackGoal;
import draylar.battletowers.entity.ai.GuardianLeapGoal;
import draylar.battletowers.entity.ai.GuardianSlamGoal;
import draylar.battletowers.entity.ai.GuardianSpinGoal;
import draylar.battletowers.mixin.DamageTrackerAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.HashSet;
import java.util.Set;

public class TowerGuardianEntity extends HostileEntity implements IAnimatable {

    public static final String ANIMATION_IDLE = "animation.tower_guardian.idle";
    public static final String ANIMATION_LEAP = "animation.tower_guardian.leap";
    public static final String ANIMATION_SLAM = "animation.tower_guardian.slam";
    public static final String ANIMATION_SLAP = "animation.tower_guardian.slap";
    public static final String ANIMATION_SPIN = "animation.tower_guardian.spin";
    public static final String ANIMATION_WALK = "animation.tower_guardian.walk";

    private final ServerBossBar bossBar;
    private final AnimationFactory factory = new AnimationFactory(this);
    private final AnimationQueue queue = new AnimationQueue(this);

    public TowerGuardianEntity(EntityType<TowerGuardianEntity> entityType, World world) {
        super(entityType, world);
        this.bossBar = (ServerBossBar) (new ServerBossBar(this.getDisplayName(), BossBar.Color.PURPLE, BossBar.Style.PROGRESS)).setDarkenSky(true);
        queue.registerAnimation(ANIMATION_LEAP, 0);
        queue.registerAnimation(ANIMATION_SLAM, 1);
        queue.registerAnimation(ANIMATION_SLAP, 2);
        queue.registerAnimation(ANIMATION_SPIN, 2);
    }

    public static DefaultAttributeContainer.Builder createGuardianAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, BattleTowers.CONFIG.bossAttack)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, BattleTowers.CONFIG.bossHP)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 10D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D)
                .add(EntityAttributes.GENERIC_ARMOR, 1D)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1)
                .add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 2);
    }

    @Override
    public void initGoals() {
        goalSelector.add(0, new SwimGoal(this));

        // Attacking Goals
        goalSelector.add(1, new GuardianLeapGoal(this));
        goalSelector.add(2, new GuardianAttackGoal(this, 1.0D, false));
        goalSelector.add(2, new GuardianSlamGoal(this));
        goalSelector.add(3, new GuardianSpinGoal(this));

        // Misc Goals
        goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 32.0F));
        goalSelector.add(4, new WanderNearTargetGoal(this, 0.09D, 32.0F));
        goalSelector.add(5, new WanderAroundGoal(this, 0.6D));
        goalSelector.add(6, new LookAroundGoal(this));

        // Targeting
        targetSelector.add(1, new RevengeGoal(this));
        targetSelector.add(2, new FollowTargetGoal<>(this, PlayerEntity.class, false));
    }

    @Override
    public boolean tryAttack(Entity target) {
        boolean success = super.tryAttack(target);

        if(success) {
            queue.queueAnimation(ANIMATION_SLAP);
        }

        return success;
    }

    @Override
    public void mobTick() {
        this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());
    }

    @Override
    public void dropLoot(DamageSource source, boolean causedByPlayer) {
        // collect set of players that have damaged this tower guardian
        Set<PlayerEntity> players = new HashSet<>();
        ((DamageTrackerAccessor) this.getDamageTracker()).getRecentDamage().forEach(damageSource -> {
            Entity attacker = damageSource.getDamageSource().getAttacker();

            if (attacker instanceof PlayerEntity) {
                players.add((PlayerEntity) attacker);
            }
        });

        // drop private loot for each player
        players.forEach(player -> {
            Identifier identifier = this.getLootTable();
            LootTable lootTable = this.world.getServer().getLootManager().getTable(identifier);
            LootContext.Builder builder = this.getLootContextBuilder(causedByPlayer, source);
            lootTable.generateLoot(builder.build(LootContextTypes.ENTITY), stack -> dropPrivateStack(stack, player));
        });
    }

    public void dropPrivateStack(ItemStack stack, PlayerEntity player) {
        if (!world.isClient) {
            ItemEntity itemEntity = new ItemEntity(this.world, this.getX(), this.getY(), this.getZ(), stack);
            itemEntity.setToDefaultPickupDelay();
            itemEntity.setOwner(player.getUuid());
            this.world.spawnEntity(itemEntity);
        }
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
    public void onStartedTrackingBy(ServerPlayerEntity player) {
        super.onStartedTrackingBy(player);
        this.bossBar.addPlayer(player);
    }

    @Override
    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        this.bossBar.removePlayer(player);
    }

    @Override
    public void handleStatus(byte status) {
        super.handleStatus(status);
        queue.handleStatus(status);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "queue", 5, event -> queue.tick(event, this::processAnimations)));
    }

    private PlayState processAnimations(AnimationEvent<TowerGuardianEntity> event) {
        if(event.isMoving() || new Vec3d(getVelocity().getX(), 0, getVelocity().getZ()).length() > 0) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.tower_guardian.walk"));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.tower_guardian.idle"));
        }

        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    public AnimationQueue getQueue() {
        return queue;
    }
}
