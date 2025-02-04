package we.devs.opium.features.modules.misc;



import we.devs.opium.features.modules.Module;
import we.devs.opium.features.modules.exploit.Ghost;
import we.devs.opium.features.settings.Setting;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.util.Formatting;

import static we.devs.opium.features.commands.Command.sendMessage;

public class AutoRespawn extends Module {
    public static AutoRespawn INSTANCE;
    public Setting<Boolean> deathCoords = this.register(new Setting<>("Coords", true));
    public AutoRespawn(){
        super("AutoRespawn","",Category.MISC,true,false,false);
        INSTANCE = this;
    }
    @Override
    public void onTick() {
        if (Ghost.INSTANCE.isDisabled()) {
            if (mc.currentScreen instanceof DeathScreen) {
                mc.player.requestRespawn();
                mc.setScreen(null);
                if (deathCoords.getValue())
                    sendMessage(Formatting.WHITE + "You died at " + "X:" + (int) mc.player.getX() + " " + "Y:" + (int) mc.player.getY() + " " + "Z:" + (int) mc.player.getZ());
            }
        }

    }


}




