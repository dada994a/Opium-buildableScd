package we.devs.opium.features.modules.movement;

import we.devs.opium.event.eventbus.EventHandler;
import we.devs.opium.event.impl.Render3DEvent;
import we.devs.opium.features.modules.Module;
import we.devs.opium.features.settings.Setting;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class NoSlow extends Module {
    public Setting<Boolean> guimove = this.register(new Setting<>("GuiMove", true));
    //public Setting<Boolean> sneak = this.register(new Setting<>("Sneak", false,v -> this.guimove.getValue()));
    public NoSlow(){
        super("NoSlow","",Category.MOVEMENT,true,false,false);
    }
    @Override
    public void onRender3D(Render3DEvent event) {
        update();
    }

    @EventHandler
    public void onUpdate() {
        update();
    }


    private void update() {
        if (guimove.getValue() == true) {
            if (!(mc.currentScreen instanceof ChatScreen)) {
                for (KeyBinding k : new KeyBinding[]{mc.options.backKey,mc.options.forwardKey, mc.options.leftKey, mc.options.rightKey, mc.options.jumpKey}) {
                    k.setPressed(InputUtil.isKeyPressed(mc.getWindow().getHandle(), InputUtil.fromTranslationKey(k.getBoundKeyTranslationKey()).getCode()));
                }

                InputUtil.isKeyPressed(mc.getWindow().getHandle(), InputUtil.fromTranslationKey(mc.options.forwardKey.getBoundKeyTranslationKey()).getCode());
                InputUtil.isKeyPressed(mc.getWindow().getHandle(), InputUtil.fromTranslationKey(mc.options.sprintKey.getBoundKeyTranslationKey()).getCode());

                //if (sneak.getValue()) {
                    //mc.options.sneakKey.setPressed(InputUtil.isKeyPressed(mc.getWindow().getHandle(), InputUtil.fromTranslationKey(mc.options.sneakKey.getBoundKeyTranslationKey()).getCode()));
                //}
            }
        }
    }

}
