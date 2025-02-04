package we.devs.opium.features.modules.client;

import we.devs.opium.features.modules.Module;
import we.devs.opium.features.settings.Setting;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;


public class Debug extends Module {
    public static Debug INSTANCE;

    public enum ClientMode {
        Opium, Opiumlegacy, Gondal, Setstantium, Burger, Off
    }
    public enum NotifyMode {
        Mio, Miolegacy, Opium, Opiumlegacy, Future, SetStantium, Off
    }
    //public Setting<ClientMode> client = this.register(new Setting<>("Client", ClientMode.Meow));
    public Setting<NotifyMode> notification = this.register(new Setting<>("Notify", NotifyMode.Opium));
    public Debug(){
        super("Debug","",Category.CLIENT,true,false,false);
        INSTANCE = this;
    }
}
