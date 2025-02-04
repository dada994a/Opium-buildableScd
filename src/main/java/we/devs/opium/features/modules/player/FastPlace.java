package we.devs.opium.features.modules.player;

import we.devs.opium.features.modules.Module;
import we.devs.opium.features.settings.Setting;
import net.minecraft.item.Items;

public class FastPlace extends Module {
    public Setting<Boolean> XP = this.register(new Setting<>("Exp", false));
    public Setting<Boolean> CRYSTAL = this.register(new Setting<>("Crystal", false));
    public Setting<Boolean> BLOCK = this.register(new Setting<>("Obsidian", false));

    public FastPlace() {
        super("FastPlace", "", Category.PLAYER, true, false, false);
    }

    @Override public void onUpdate() {
        if (nullCheck()) return;

        if(XP.getValue()== true) {
            if (mc.player.isHolding(Items.EXPERIENCE_BOTTLE)) {
                mc.itemUseCooldown = 0;
            }
        }
        if(CRYSTAL.getValue()== true) {
            if (mc.player.isHolding(Items.END_CRYSTAL)) {
                mc.itemUseCooldown = 0;
            }
        }
        if(BLOCK.getValue()== true) {
            if (mc.player.isHolding(Items.OBSIDIAN)) {
                mc.itemUseCooldown = 0;
            }
        }
    }


}
