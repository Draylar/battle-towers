package draylar.battletowers.entity;

import draylar.battletowers.BattleTowers;
import draylar.battletowers.entity.goal.TowerGuardianMeleeAttackGoal;
import draylar.battletowers.mixin.DamageTrackerAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
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
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;

public class TowerGuardianEntity extends HostileEntity {

    private final ServerBossBar bossBar;

    public TowerGuardianEntity(EntityType<TowerGuardianEntity> entityType, World world) {
        super(entityType, world);
        this.bossBar = (ServerBossBar)(new ServerBossBar(this.getDisplayName(), BossBar.Color.PURPLE, BossBar.Style.PROGRESS)).setDarkenSky(true);
    }

    @Override
    public void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new TowerGuardianMeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 32.0F));
        this.goalSelector.add(2, new GoToEntityTargetGoal(this, 0.09D, 32.0F));
        this.goalSelector.add(3, new WanderAroundGoal(this, 0.6D));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new FollowTargetGoal<>(this, PlayerEntity.class, false));
    }

    public static DefaultAttributeContainer.Builder createGuardianAttributes() {
        return DefaultAttributeContainer.builder()
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, BattleTowers.CONFIG.bossAttack)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, BattleTowers.CONFIG.bossHP)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 10D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D)
                .add(EntityAttributes.GENERIC_ARMOR, 1D)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1)
                .add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 2);
    }

//    @Override
//    public void onPlayerCollision(PlayerEntity player) {
//        this.playSound(SoundEvents.ENTITY_IRON_GOLEM_ATTACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
//        player.damage(DamageSource.mob(this), (float) this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).getValue());
//    }

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

            if(attacker instanceof PlayerEntity) {
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
        if(!world.isClient) {
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
}
