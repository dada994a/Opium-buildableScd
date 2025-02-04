package we.devs.opium.features.modules.render;


import we.devs.opium.features.modules.Module;
import we.devs.opium.features.settings.Setting;
import we.devs.opium.mixin.StatusEffectInstanceAccessor;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;
import net.minecraft.world.LightType;

public class FullBright extends Module {

    public Setting<Mode> mode = this.register(new Setting<>("Mode",Mode.Potion));
    public FullBright(){
        super("FullBright","",Category.RENDER,true,false,false);
    }
/*
    @Override
    public void onEnable() {
        if (mode.getValue() == Mode.Luminance) mc.worldRenderer.reload();
    }
    */

    @Override
    public void onDisable() {
        if (mode.getValue() == Mode.Potion) disableNightVision();
    }
 /*

    public int getLuminance(LightType type) {
        if (!isEnabled() || mode.getValue() != Mode.Luminance || type != lightType.getValue()) return 0;
        return minimumLightLevel.getValue();
    }

    @Override
    public void onUpdate() {
        if (mode.getValue() == Mode.Gamma)
        mc.options.getGamma().setValue(9999.0);
    }



 */

        @Override
        public void onTick () {
            if (mc.player == null || !mode.getValue().equals(Mode.Potion)) return;
            if (mc.player.hasStatusEffect(Registries.STATUS_EFFECT.getEntry(StatusEffects.NIGHT_VISION.value()))) {
                StatusEffectInstance instance = mc.player.getStatusEffect(Registries.STATUS_EFFECT.getEntry(StatusEffects.NIGHT_VISION.value()));
                if (instance != null && instance.getDuration() < 99999999)
                    ((StatusEffectInstanceAccessor) instance).setDuration(999999999);
            } else {
                mc.player.addStatusEffect(new StatusEffectInstance(Registries.STATUS_EFFECT.getEntry(StatusEffects.NIGHT_VISION.value()), 999999999, 0));
            }
        }

        private void disableNightVision () {
            if (mc.player == null) return;
            if (mc.player.hasStatusEffect(Registries.STATUS_EFFECT.getEntry(StatusEffects.NIGHT_VISION.value()))) {
                mc.player.removeStatusEffect(Registries.STATUS_EFFECT.getEntry(StatusEffects.NIGHT_VISION.value()));
            }
        }

        public enum Mode {

            Potion,

        }

}
