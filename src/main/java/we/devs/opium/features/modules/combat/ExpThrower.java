package we.devs.opium.features.modules.combat;


import we.devs.opium.Opium;
import we.devs.opium.features.modules.Module;
import we.devs.opium.features.settings.Setting;
import we.devs.opium.util.player.FindItemResult;
import we.devs.opium.util.player.InventoryUtil;
import net.minecraft.item.Items;

public class ExpThrower extends Module {
    public Setting<Boolean> rotate = this.register(new Setting<>("Down", false));
    public ExpThrower(){
        super("ExpThrower","",Category.COMBAT,true,false,false);
    }

    @Override
    public void onTick() {
        FindItemResult exp = InventoryUtil.findInHotbar(Items.EXPERIENCE_BOTTLE);
        if (!exp.found()) return;
        if (rotate.getValue()) {
            Opium.rotationManager.setPlayerRotations(mc.player.getYaw(), 90);
        }
        if (exp.getHand() != null) {
            mc.interactionManager.interactItem(mc.player, exp.getHand());
        }
        else {
            InventoryUtil.swap(exp.slot(), true);
            mc.interactionManager.interactItem(mc.player, exp.getHand());
            InventoryUtil.swapBack();
        }
    }
}
