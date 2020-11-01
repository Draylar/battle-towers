package draylar.battletowers.mixin;

import com.google.common.collect.ImmutableList;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.datafixers.DataFixer;
import draylar.battletowers.api.Towers;
import draylar.battletowers.registry.BattleTowerStructures;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ServerResourceManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListenerFactory;
import net.minecraft.util.UserCache;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

    @Inject(
            method = "<init>",
            at = @At("RETURN")
    )
    private void addTowers(Thread thread, DynamicRegistryManager.Impl impl, LevelStorage.Session session, SaveProperties saveProperties, ResourcePackManager resourcePackManager, Proxy proxy, DataFixer dataFixer, ServerResourceManager serverResourceManager, MinecraftSessionService minecraftSessionService, GameProfileRepository gameProfileRepository, UserCache userCache, WorldGenerationProgressListenerFactory worldGenerationProgressListenerFactory, CallbackInfo ci) {
        impl.get(Registry.BIOME_KEY).forEach(MinecraftServerMixin::initializeBiome);
        RegistryEntryAddedCallback.event(impl.get(Registry.BIOME_KEY)).register((i, identifier, biome) -> initializeBiome(biome));
    }

    private static void initializeBiome(Biome biome) {
        if (biome.getCategory() != Biome.Category.RIVER && biome.getCategory() != Biome.Category.THEEND && biome.getCategory() != Biome.Category.NONE && biome.getCategory() != Biome.Category.NETHER) {
            System.out.println("added towers to " + biome);

            List<Supplier<ConfiguredStructureFeature<?, ?>>> structureFeatures = new ArrayList<>(biome.getGenerationSettings().getStructureFeatures());
            structureFeatures.add(() -> BattleTowerStructures.CONFIGURED);
            ((GenerationSettingsAccessor) biome.getGenerationSettings()).setStructureFeatures(structureFeatures);
        }
    }
}
