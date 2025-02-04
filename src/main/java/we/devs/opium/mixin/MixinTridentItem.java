package we.devs.opium.mixin;

import we.devs.opium.Opium;
import we.devs.opium.event.impl.UseTridentEvent;
import we.devs.opium.features.modules.other.TridentBoost;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import static we.devs.opium.util.world.Wrapper.mc;


@Mixin(TridentItem.class)
public abstract class MixinTridentItem {

    @Inject(method = "onStoppedUsing", at = @At(value = "HEAD"), cancellable = true)
    public void onStoppedUsingHook(ItemStack stack, World world, LivingEntity user, int remainingUseTicks, CallbackInfo ci) {
        if (user == mc.player && EnchantmentHelper.getTridentSpinAttackStrength(stack, mc.player) > 0) {
            UseTridentEvent e = new UseTridentEvent();
            Opium.EVENT_BUS.post(e);
            if (e.isCancelled())
                ci.cancel();
        }
    }

    @Inject(method = "use", at = @At(value = "HEAD"), cancellable = true)
    public void useHook(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (EnchantmentHelper.getTridentSpinAttackStrength(itemStack, user) > 0 && !user.isTouchingWaterOrRain() && TridentBoost.INSTANCE.isEnabled() && TridentBoost.INSTANCE.anyWeather.getValue()) {
            user.setCurrentHand(hand);
            cir.setReturnValue(TypedActionResult.consume(itemStack));
        }
    }
}
