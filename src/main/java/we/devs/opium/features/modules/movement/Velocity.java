package we.devs.opium.features.modules.movement;

import com.google.common.eventbus.Subscribe;
import we.devs.opium.event.impl.PacketEvent;
import we.devs.opium.features.modules.Module;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;

public class Velocity extends Module {
    public Velocity() {
        super("Velocity", "", Category.MOVEMENT, true, false, false);
    }

    @Subscribe private void onPacketReceive(PacketEvent.Receive event) {
        if (event.getPacket() instanceof EntityVelocityUpdateS2CPacket || event.getPacket() instanceof ExplosionS2CPacket) event.cancel();
    }
}
