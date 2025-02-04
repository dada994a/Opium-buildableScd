package we.devs.opium.features.modules.combat;

import com.google.common.eventbus.Subscribe;
import we.devs.opium.event.impl.PacketEvent;
import we.devs.opium.features.modules.Module;
import we.devs.opium.util.models.Timer;
import we.devs.opium.util.traits.Util;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class Criticals extends Module {
    private final Timer timer = new Timer();
    public Criticals() {
        super("Criticals", "", Category.COMBAT, true, false, false);
    }
    @Subscribe private void onPacketSend(PacketEvent.Send event) {
        if (event.getPacket() instanceof PlayerInteractEntityC2SPacket packet && packet.type.getType() == PlayerInteractEntityC2SPacket.InteractType.ATTACK) {
            Entity entity = Util.mc.world.getEntityById(packet.entityId);
            if (entity == null
                    || entity instanceof EndCrystalEntity
                    || !Util.mc.player.isOnGround()
                    || !(entity instanceof LivingEntity)
                    || !timer.passedMs(0)) return;

            Util.mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(Util.mc.player.getX(), Util.mc.player.getY() + (double) 0.1f, Util.mc.player.getZ(), false));
            Util.mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(Util.mc.player.getX(), Util.mc.player.getY(), Util.mc.player.getZ(), false));
            Util.mc.player.addCritParticles(entity);
            timer.reset();
        }
    }

    @Override public String getDisplayInfo() {
        return "Packet";
    }
}
