package we.devs.opium.features.modules.movement;



import we.devs.opium.features.modules.Module;

import we.devs.opium.features.settings.Setting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import static we.devs.opium.util.world.HoleUtil.validIndestructible;
import static we.devs.opium.util.world.HoleUtil.validTwoBlockIndestructible;


public class HoleAnchor extends Module {
    public HoleAnchor() {
        super("HoleAnchor","", Category.MOVEMENT,true,false,false);
    }

    private final Setting<Integer> pitch = this.register(new Setting<>("Pitch", 60, 0, 90));
    private final Setting<Boolean> pull = this.register(new Setting<>("Pull", true));


    @Override
    public void onUpdate() {
        if (mc.player.getPitch() > pitch.getValue()) {
            if (
                    validIndestructible(BlockPos.ofFloored(mc.player.getPos()).down(1))
                            || validIndestructible(BlockPos.ofFloored(mc.player.getPos()).down(2))
                            || validIndestructible(BlockPos.ofFloored(mc.player.getPos()).down(3))
                            || validTwoBlockIndestructible(BlockPos.ofFloored(mc.player.getPos()).down(1))
                            || validTwoBlockIndestructible(BlockPos.ofFloored(mc.player.getPos()).down(2))
                            || validTwoBlockIndestructible(BlockPos.ofFloored(mc.player.getPos()).down(3))
            ) {
                if (!pull.getValue()) {
                    mc.player.setVelocity(0, mc.player.getVelocity().getY(), 0);
                } else {
                    Vec3d center = new Vec3d(Math.floor(mc.player.getX()) + 0.5, Math.floor(mc.player.getY()), Math.floor(mc.player.getZ()) + 0.5);

                    if (Math.abs(center.x - mc.player.getX()) > 0.1 || Math.abs(center.z - mc.player.getZ()) > 0.1) {
                        double d3 = center.x - mc.player.getX();
                        double d4 = center.z - mc.player.getZ();
                        mc.player.setVelocity(Math.min(d3 / 2.0, 0.2), mc.player.getVelocity().getY(), Math.min(d4 / 2.0, 0.2));
                    }
                }
            }
        }
    }
}
