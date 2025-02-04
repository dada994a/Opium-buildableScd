package we.devs.opium.features.modules.other;


import we.devs.opium.Opium;
import we.devs.opium.event.eventbus.EventHandler;
import we.devs.opium.event.eventbus.EventPriority;
import we.devs.opium.event.impl.KeyboardInputEvent;
import we.devs.opium.event.impl.Render3DEvent;
import we.devs.opium.event.impl.RotateEvent;
import we.devs.opium.features.modules.Module;
import we.devs.opium.features.settings.Setting;
import we.devs.opium.util.math.MathUtil2;
import we.devs.opium.util.module.MovementUtil;

public class Freecam extends Module {
    public static Freecam INSTANCE;
    public Setting<Float> speed = this.register(new Setting<>("HSpeed", 1f, 0f, 3f));
    public Setting<Float> hspeed = this.register(new Setting<>("VSpeed", 0.42f, 0f, 3f));
    public Setting<Boolean> rotate = this.register(new Setting<>("Rotate", true));
    private float fakeYaw;
    private float fakePitch;
    private float prevFakeYaw;
    private float prevFakePitch;
    private double fakeX;
    private double fakeY;
    private double fakeZ;
    private double prevFakeX;
    private double prevFakeY;
    private double prevFakeZ;
    public Freecam(){
        super("Freecam","",Category.PLAYER,true,false,false);
        INSTANCE = this;
    }
    @Override
    public void onEnable() {
        if (nullCheck()) {
            disable();
            return;
        }
        mc.chunkCullingEnabled = false;

        preYaw = mc.player.getYaw();
        prePitch = mc.player.getPitch();

        fakePitch = mc.player.getPitch();
        fakeYaw = mc.player.getYaw();

        prevFakePitch = fakePitch;
        prevFakeYaw = fakeYaw;

        fakeX = mc.player.getX();
        fakeY = mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose());
        fakeZ = mc.player.getZ();

        prevFakeX = fakeX;
        prevFakeY = fakeY;
        prevFakeZ = fakeZ;
    }


    @Override
    public void onDisable() {
        mc.chunkCullingEnabled = true;
    }

    @Override
    public void onUpdate() {
        if (rotate.getValue() && mc.crosshairTarget != null && mc.crosshairTarget.getPos() != null) {
            float[] angle = Opium.rotationManager.getLegitRotations(mc.crosshairTarget.getPos());
            preYaw = angle[0];
            prePitch = angle[1];
        }
    }

    private float preYaw;
    private float prePitch;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRotate(RotateEvent event) {
        event.setYawNoModify(preYaw);
        event.setPitchNoModify(prePitch);
    }

    @Override
    public void onRender3D(Render3DEvent event) {
        prevFakeYaw = fakeYaw;
        prevFakePitch = fakePitch;

        fakeYaw = mc.player.getYaw();
        fakePitch = mc.player.getPitch();
    }

    @EventHandler
    public void onKeyboardInput(KeyboardInputEvent event) {
        if (mc.player == null) return;

        double[] motion = MovementUtil.directionSpeed(speed.getValue());

        prevFakeX = fakeX;
        prevFakeY = fakeY;
        prevFakeZ = fakeZ;

        fakeX += motion[0];
        fakeZ += motion[1];

        if (mc.options.jumpKey.isPressed())
            fakeY += hspeed.getValue();

        if (mc.options.sneakKey.isPressed())
            fakeY -= hspeed.getValue();

        mc.player.input.movementForward = 0;
        mc.player.input.movementSideways = 0;
        mc.player.input.jumping = false;
        mc.player.input.sneaking = false;
    }

    public float getFakeYaw() {
        return (float) MathUtil2.interpolate(prevFakeYaw, fakeYaw, mc.getRenderTickCounter().getTickDelta(true));
    }

    public float getFakePitch() {
        return (float) MathUtil2.interpolate(prevFakePitch, fakePitch, mc.getRenderTickCounter().getTickDelta(true));
    }

    public double getFakeX() {
        return MathUtil2.interpolate(prevFakeX, fakeX, mc.getRenderTickCounter().getTickDelta(true));
    }

    public double getFakeY() {
        return MathUtil2.interpolate(prevFakeY, fakeY, mc.getRenderTickCounter().getTickDelta(true));
    }

    public double getFakeZ() {
        return MathUtil2.interpolate(prevFakeZ, fakeZ, mc.getRenderTickCounter().getTickDelta(true));
    }

}
