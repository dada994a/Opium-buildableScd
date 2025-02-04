package we.devs.opium.features.modules.movement;


import we.devs.opium.features.modules.Module;
import we.devs.opium.features.modules.client.Watermark;
import we.devs.opium.features.settings.Setting;

public class Sprint extends Module {
    public static Sprint INSTANCE;
    public enum Mode {
        Legit, Rage
    }
    public Setting<Mode> mode = this.register(new Setting<>("Mode", Mode.Legit));

    public Sprint(){
        super("Sprint","", Category.MOVEMENT, true, false, false);
        INSTANCE = this;
    }

    @Override
    public void onTick() {
        switch (Sprint.INSTANCE.mode.getValue()) {

            case Legit -> {
            }

            case Rage -> {
                if (nullCheck())
                    return;
                mc.player.setSprinting(true);
            }
        }
    }
}
