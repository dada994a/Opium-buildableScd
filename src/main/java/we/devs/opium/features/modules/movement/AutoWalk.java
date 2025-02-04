package we.devs.opium.features.modules.movement;

import we.devs.opium.features.modules.Module;

public class AutoWalk extends Module {
    public AutoWalk(){
        super("AutoWalk","",Category.MOVEMENT,true,false,false);
    }
    public void onTick() {
        if (nullCheck())
            return;
        mc.options.forwardKey.setPressed(true);
    }
}
