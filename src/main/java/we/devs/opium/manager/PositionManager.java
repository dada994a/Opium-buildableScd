package we.devs.opium.manager;

import we.devs.opium.features.Feature;
import we.devs.opium.util.traits.Util;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class PositionManager
        extends Feature {
    private double x;
    private double y;
    private double z;
    private boolean onground;

    public void updatePosition() {
        this.x = Util.mc.player.getX();
        this.y = Util.mc.player.getY();
        this.z = Util.mc.player.getZ();
        this.onground = Util.mc.player.isOnGround();
    }

    public void restorePosition() {
        Util.mc.player.setPosition(x, y, z);
        Util.mc.player.setOnGround(onground);
    }

    public void setPlayerPosition(double x, double y, double z) {
        Util.mc.player.setPosition(x, y, z);
    }

    public void setPlayerPosition(double x, double y, double z, boolean onground) {
        Util.mc.player.setPosition(x, y, z);
        Util.mc.player.setOnGround(onground);
    }

    public void setPositionPacket(double x, double y, double z, boolean onGround, boolean setPos, boolean noLagBack) {
        Util.mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y, z, onGround));
        if (setPos) {
            Util.mc.player.setPosition(x, y, z);
            if (noLagBack) {
                this.updatePosition();
            }
        }
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return this.z;
    }

    public void setZ(double z) {
        this.z = z;
    }
}