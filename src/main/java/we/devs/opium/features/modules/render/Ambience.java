package we.devs.opium.features.modules.render;

import we.devs.opium.features.modules.Module;
import we.devs.opium.features.settings.Setting;
import net.minecraft.client.particle.ElderGuardianAppearanceParticle;
import net.minecraft.client.particle.RainSplashParticle;

public class Ambience extends Module {
   public enum Weather{
       Clear, off
   }
    //public Setting<Weather> weather = this.register(new Setting<>("Weather", Weather.Clear));
    public Setting<Boolean> customTime = this.register(new Setting<>("CustomTime", false));
    public Setting<Integer> time = this.register(new Setting<>("Time", 0,0,24000,v -> this.customTime.getValue()));

    public Ambience(){
        super("Ambience","",Category.RENDER,true,false,false);
    }
    @Override
    public void onUpdate() {
        if (customTime.getValue() == true) {
            mc.world.setTimeOfDay((long) this.time.getValue());
        }
    }
    /*
    @EventHandler
    public void onParticle(ParticleEvent.AddParticle event) {
        if (weather.getValue() == Weather.Clear && event.particle instanceof RainSplashParticle) {
            event.cancel();
        }
    }

     */
}
