package we.devs.opium.features.modules.other;

import we.devs.opium.features.modules.Module;
import we.devs.opium.features.settings.Setting;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.SwordItem;

public class NoEntityTrace extends Module {
    public static NoEntityTrace INSTANCE;
    private final Setting<Boolean> onlyPickaxe = this.register(new Setting<>("OnlyPickaxe ", true));

    public NoEntityTrace() {
        super("NoEntityTrace", "", Category.PLAYER, true, false, false);
        INSTANCE = this;
    }

    public boolean isActive;

    @Override
    public void onDisable() {
        isActive = false;
    }

    public boolean noEntityTrace() {
        if (onlyPickaxe.getValue()) {
            return mc.player.getMainHandStack().getItem() instanceof PickaxeItem || mc.player.isUsingItem() && !(mc.player.getMainHandStack().getItem() instanceof SwordItem);
        }
        return true;
    }
}


