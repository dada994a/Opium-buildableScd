package we.devs.opium.features.modules.other;


import com.google.common.eventbus.Subscribe;
import we.devs.opium.event.impl.TotemParticleEvent;
import we.devs.opium.features.modules.Module;
import we.devs.opium.features.settings.Setting;
import we.devs.opium.util.render.NewColorUtil;

import java.awt.*;
import java.util.Random;


public class TotemFish extends Module {
    public static TotemFish INSTANCE;
    public Setting<Float> velocityXZ = this.register(new Setting<>("SecondBlue", 100f, 0f, 500f));
    public Setting<Float> velocityY = this.register(new Setting<>("HoverAlpha", 100f, 0f, 500f));
    public TotemFish(){
        super("AutoFish","",Category.MISC,true,false,false);
        INSTANCE = this;
    }
    Random random = new Random();
    @Subscribe
    public void idk(TotemParticleEvent event) {
        event.cancel();
        event.velocityZ *= velocityXZ.getValue() / 100;
        event.velocityX *= velocityXZ.getValue() / 100;

        event.velocityY *= velocityY.getValue() / 100;

        event.color = NewColorUtil.fadeColor(Color.WHITE,Color.BLACK, random.nextDouble());
    }

}