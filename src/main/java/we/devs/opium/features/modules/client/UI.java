package we.devs.opium.features.modules.client;

import com.google.common.eventbus.Subscribe;
import we.devs.opium.Opium;
import we.devs.opium.event.impl.ClientEvent;
import we.devs.opium.features.commands.Command;
import we.devs.opium.features.gui.OyVeyGui;
import we.devs.opium.features.modules.Module;
import we.devs.opium.features.settings.Setting;
import we.devs.opium.util.traits.Util;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

public class UI
        extends Module {
    public static UI INSTANCE;
    public Setting<String> prefix = this.register(new Setting<>("Prefix", ";"));
    public Setting<Integer> red = this.register(new Setting<>("Red", 108, 0, 255));
    public Setting<Integer> green = this.register(new Setting<>("Green", 108, 0, 255));
    public Setting<Integer> blue = this.register(new Setting<>("Blue", 255, 0, 255));
    public Setting<Integer> hoverAlpha = this.register(new Setting<>("Alpha", 255, 0, 255));
    public Setting<Integer> topRed = this.register(new Setting<>("SecondRed", 108, 0, 255));
    public Setting<Integer> topGreen = this.register(new Setting<>("SecondGreen", 108, 0, 255));
    public Setting<Integer> topBlue = this.register(new Setting<>("SecondBlue", 255, 0, 255));
    public Setting<Integer> alpha = this.register(new Setting<>("HoverAlpha", 255, 0, 255));
    public Setting<Boolean> rainbow = this.register(new Setting<>("Rainbow", false));
    //public Setting<rainbowMode> rainbowModeHud = this.register(new Setting<>("HRainbowMode", rainbowMode.Static, v -> this.rainbow.getValue()));
    //public Setting<rainbowModeArray> rainbowModeA = this.register(new Setting<>("ARainbowMode", rainbowModeArray.Static, v -> this.rainbow.getValue()));
    public Setting<Integer> rainbowHue = this.register(new Setting<>("Delay", 240, 0, 600, v -> this.rainbow.getValue()));
    public Setting<Float> rainbowBrightness = this.register(new Setting<>("Brightness ", 150.0f, 1.0f, 255.0f, v -> this.rainbow.getValue()));
    public Setting<Float> rainbowSaturation = this.register(new Setting<>("Saturation", 150.0f, 1.0f, 255.0f, v -> this.rainbow.getValue()));
    private OyVeyGui click;

    public UI() {
        super("UI", "Opens the ClickGui", Category.CLIENT, true, false, false);
        setBind(GLFW.GLFW_KEY_RIGHT_SHIFT);
        this.setInstance();
        INSTANCE = this;

    }

    public static UI getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UI();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }



    @Subscribe
    public void onSettingChange(ClientEvent event) {
        if (event.getStage() == 2 && event.getSetting().getFeature().equals(this)) {
            if (event.getSetting().equals(this.prefix)) {
                Opium.commandManager.setPrefix(this.prefix.getPlannedValue());
                Command.sendMessage("Prefix set to " + Formatting.DARK_GRAY + Opium.commandManager.getPrefix());
            }
            Opium.colorManager.setColor(this.red.getPlannedValue(), this.green.getPlannedValue(), this.blue.getPlannedValue(), this.hoverAlpha.getPlannedValue());
        }
    }

    @Override
    public void onEnable() {
        Util.mc.setScreen(OyVeyGui.getClickGui());
    }

    @Override
    public void onLoad() {
        Opium.colorManager.setColor(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.hoverAlpha.getValue());
        Opium.commandManager.setPrefix(this.prefix.getValue());
    }

    @Override
    public void onTick() {
        if (!(UI.mc.currentScreen instanceof OyVeyGui)) {
            this.disable();
        }
    }
    @Override
    public void onDisable() {
        if (!(UI.mc.currentScreen instanceof OyVeyGui)) {
            this.disable();
        }
    }

    public enum rainbowModeArray {
        Static,
        Up

    }

    public enum rainbowMode {
        Static,
        Sideway

    }
}