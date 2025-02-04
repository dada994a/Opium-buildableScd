package we.devs.opium.features.modules.movement;


import we.devs.opium.features.modules.Module;
import we.devs.opium.features.settings.Setting;
import we.devs.opium.util.world.HoleUtil;
import net.minecraft.entity.attribute.EntityAttributes;

public class Step extends Module {
    public static Step INSTANCE;

    private final Setting<Float> height = this.register(new Setting<>("Height", 2f, 1f, 3f, v -> true));
    private final Setting<Boolean> holeDisable = this.register(new Setting<>("HoleDisable", false));
    public Step() {
        super("Step", "step..", Category.MOVEMENT, true, false, false);
         INSTANCE = this;
    }

    private float prev;
    private boolean alreadyInHole;

    @Override
    public void onEnable() {
        if (nullCheck()) {
            prev = 0.6f;
            return;
        }
        prev = mc.player.getStepHeight();
        alreadyInHole = mc.player != null && HoleUtil.isHole(mc.player.getBlockPos());
    }

    @Override public void onDisable() {
        if (nullCheck()) return;
        mc.player.getAttributeInstance(EntityAttributes.GENERIC_STEP_HEIGHT).setBaseValue(prev);
    }

    @Override public void onUpdate() {
        if (nullCheck()) return;
        mc.player.getAttributeInstance(EntityAttributes.GENERIC_STEP_HEIGHT).setBaseValue(height.getValue());

        if (holeDisable.getValue() && HoleUtil.isHole(mc.player.getBlockPos()) && !alreadyInHole) {
            disable();
            return;
        }
        alreadyInHole = mc.player != null && HoleUtil.isHole(mc.player.getBlockPos());
    }

}
