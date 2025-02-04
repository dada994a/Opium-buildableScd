package we.devs.opium.features.modules.render;


import we.devs.opium.features.modules.Module;
import we.devs.opium.features.settings.Setting;
import net.minecraft.client.option.SimpleOption;

public class Zoom extends Module {
    private Integer lastFov = null;
    public Setting<Float> zoomFactor = this.register(new Setting<>("Value", 2f,1f,3.0f));
    public Zoom(){
        super("Zoom","",Category.RENDER,true,false,false);
    }

    public void onDisable() {
        if (lastFov != null) {
            mc.options.getFov().setValue((int) Math.max(30, Math.min(110, lastFov)));
        }
    }

    @Override
    public void onEnable() {
        lastFov = mc.options.getFov().getValue();
    }

    @Override
    public void onToggle() {

    }


    @Override
    public void onTick() {
        SimpleOption<Integer> fov = mc.options.getFov();
        int newZoom = (int) Math.max(30, Math.min(110, lastFov / zoomFactor.getValue()));
        fov.setValue(newZoom);
    }

}
