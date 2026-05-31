package com.exodar.addon.mixin;

import com.exodar.addon.modules.LegitAura;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(
        method = "jumpFromGround()V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getYRot()F")
    )
    private void exodar$silentYawForJump(CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (self == Minecraft.getInstance().player) {
            Modules modules = Modules.get();
            if (modules != null) {
                LegitAura aura = modules.get(LegitAura.class);
                if (aura != null && aura.shouldFixMovement()) {
                    // Update the rotation parameter directly right before it is checked
                    self.setYRot(aura.getSilentYaw());
                }
            }
        }
    }
}