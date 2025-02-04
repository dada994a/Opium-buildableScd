package we.devs.opium.features.modules.misc;

import we.devs.opium.Opium;
import we.devs.opium.features.commands.Command;
import we.devs.opium.features.modules.Module;
import we.devs.opium.features.settings.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class Notifier extends Module {
    private List<PlayerEntity> knownPlayers;

    public Setting<Boolean> visualRange = this.register(new Setting<>("VisualRange", false));
    public Notifier(){
        super("Notifier","",Category.MISC,true,false,false);
    }
    @Override
    public void onEnable() {
        this.knownPlayers = new ArrayList<PlayerEntity>();
    }

    @Override
    public void onTick() {
        if (nullCheck()) {
            return;
        }
        if (visualRange.getValue() == true) {
            final List<PlayerEntity> tickPlayerList = new ArrayList<PlayerEntity>();

            for (final Entity entity : mc.world.getEntities()) {
                if (entity instanceof PlayerEntity) {
                    tickPlayerList.add((PlayerEntity) entity);
                }
            }
            if (tickPlayerList.size() > 0) {
                for (final PlayerEntity player : tickPlayerList) {
                    if (player == mc.player) {
                        continue;
                    }
                    if (!this.knownPlayers.contains(player)) {
                        this.knownPlayers.add(player);

                        if (Opium.friendManager.isFriend(player)) {

                            Command.sendMessage("Friend " + player.getName().getString() + Formatting.GREEN + " has entered the render distance!");
                        } else if (!Opium.friendManager.isFriend(player)) {

                            Command.sendMessage(player.getName().getString() + Formatting.RED + " has entered the render distance!");
                        }

                        return;
                    }
                }
            }
            if (this.knownPlayers.size() > 0) {
                knownPlayers.removeIf(player -> !tickPlayerList.contains(player));
            }
        }
    }



}
