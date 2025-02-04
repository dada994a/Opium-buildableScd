package we.devs.opium.features.modules.other;

import we.devs.opium.features.modules.Module;
import we.devs.opium.features.settings.Setting;

public class BetterChat extends Module {
    public Setting<Boolean> timestamp = this.register(new Setting<>("Timestamp", true));

    public BetterChat(){
        super("BetterChat","",Category.MISC,true,false,false);
    }




}
