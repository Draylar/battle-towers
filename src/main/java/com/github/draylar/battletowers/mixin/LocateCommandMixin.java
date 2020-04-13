package com.github.draylar.battletowers.mixin;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.LocateCommand;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocateCommand.class)
public class LocateCommandMixin {

    @Shadow
    private static int execute(ServerCommandSource source, String name) throws CommandSyntaxException {
        throw new AssertionError();
    }

    @Inject(method = "register", at = @At(value = "RETURN"))
    private static void onRegister(CommandDispatcher<ServerCommandSource> dispatcher, CallbackInfo info) {
        dispatcher.register(CommandManager.literal("locate").requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.literal("Battle_Tower").executes(ctx -> execute(ctx.getSource(), "BattleTower"))));
    }

    private LocateCommandMixin() {
        // NO-OP
    }
}
