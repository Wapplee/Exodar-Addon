package com.exodar.addon.mixin;

import com.exodar.addon.modules.LegitAura;
import com.exodar.addon.modules.Penetration;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Inject(method = "pick(F)V", at = @At("TAIL"))
    private void exodar$pickOverride(float partialTick, CallbackInfo ci) {
        Modules modules = Modules.get();
        if (modules == null) return;

        LegitAura la = modules.get(LegitAura.class);
        if (la != null && la.isActive()) {
            la.applyOverride();
        }

        Penetration p = modules.get(Penetration.class);
        if (p != null && p.isActive()) {
            p.applyOverride();
        }
    }
}
