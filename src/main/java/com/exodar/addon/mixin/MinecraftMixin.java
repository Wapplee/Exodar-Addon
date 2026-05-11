package com.exodar.addon.mixin;

import com.exodar.addon.modules.LegitAura;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Inject(method = "startAttack", at = @At("HEAD"), cancellable = true)
    private void exodar$cancelAttackWhenTracking(CallbackInfoReturnable<Boolean> cir) {
        Modules modules = Modules.get();
        if (modules == null) return;
        LegitAura la = modules.get(LegitAura.class);
        if (la != null && la.isActive() && la.getCurrentTarget() != null) {
            cir.setReturnValue(false);
        }
    }
}
