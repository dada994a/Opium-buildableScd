package we.devs.opium.features.modules.client;

import we.devs.opium.Opium;
import we.devs.opium.event.impl.Render2DEvent;
import we.devs.opium.features.modules.Module;
import we.devs.opium.features.settings.Setting;
import we.devs.opium.util.traits.Util;

public class Watermark extends Module {
    public static Watermark INSTANCE;
    public enum Mode {
        Normal, Client, CC
    }

    public Setting<Mode> mode = this.register(new Setting<>("Mode", Mode.Normal));
    public Watermark() {
        super("Watermark", "kool klient watermarkz", Category.CLIENT, true, false, false);
        INSTANCE = this;
    }

    @Override public void onRender2D(Render2DEvent event) {
        switch (Watermark.INSTANCE.mode.getValue()) {

            case Normal -> {
                event.getContext().drawTextWithShadow(
                        Util.mc.textRenderer,
                        Opium.NAME + Opium.VERSION,
                        1, 1,
                        -1
                );
            }
            case CC -> {
                event.getContext().drawTextWithShadow(
                        Util.mc.textRenderer,
                        "0piumh4ck.cc" + Opium.VERSION,
                        1, 1,
                        -1
                );
            }
            case Client -> {
                event.getContext().drawTextWithShadow(
                        Util.mc.textRenderer,
                        "Opium Client" + Opium.VERSION,
                        1, 1,
                        -1
                );
            }
        }
    }
}
