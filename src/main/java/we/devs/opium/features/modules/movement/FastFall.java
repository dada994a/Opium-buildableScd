package we.devs.opium.features.modules.movement;

import we.devs.opium.features.modules.Module;
import we.devs.opium.features.Feature;
import we.devs.opium.util.traits.Util;

public class FastFall extends Module {
    public FastFall() {
        super("FastFall", "step but reversed..", Category.MOVEMENT, true, false, false);
    }

    @Override public void onUpdate() {
        if (Feature.nullCheck()) return;
        if (Util.mc.player.isInLava() || Util.mc.player.isTouchingWater() || !Util.mc.player.isOnGround()) return;
        Util.mc.player.addVelocity(0, -1, 0);
    }
}
