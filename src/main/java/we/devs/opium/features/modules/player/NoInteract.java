package we.devs.opium.features.modules.player;

import com.google.common.eventbus.Subscribe;
import we.devs.opium.event.impl.PacketEvent;
import we.devs.opium.features.modules.Module;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.EnderChestBlock;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;


public class NoInteract extends Module {
    public NoInteract(){
        super("NoInteract","",Category.PLAYER,true,false,false);
    }
    @Subscribe
    public void onPacket(PacketEvent.Send event) {
        if (nullCheck() || !(event.getPacket() instanceof PlayerInteractBlockC2SPacket packet)) {
            return;
        }
        Block block = mc.world.getBlockState(packet.getBlockHitResult().getBlockPos()).getBlock();
        if (!mc.player.isSneaking()) {
            if (block instanceof ChestBlock || block instanceof EnderChestBlock || block instanceof AnvilBlock) {
                event.cancel();
            }
        }
    }
}
