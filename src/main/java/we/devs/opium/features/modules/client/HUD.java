package we.devs.opium.features.modules.client;

import we.devs.opium.Opium;
import we.devs.opium.event.impl.Render2DEvent;
import we.devs.opium.features.modules.Module;
import we.devs.opium.features.settings.Setting;
import we.devs.opium.util.traits.Util;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;
import java.util.Arrays;
import java.util.Calendar;
import java.util.stream.Collectors;


public class HUD extends Module {
    public DrawContext context = null;
    public Setting<Boolean> welcomer = this.register(new Setting<>("Welcomer", false));
    public Setting<Boolean> biome = this.register(new Setting<>("Biome", false));
    public HUD() {
        super("HUD", "hud", Category.CLIENT, true, false, false);
    }

    @Override public void onRender2D(Render2DEvent event) {
        if(biome.getValue() == true) {
            event.getContext().drawTextWithShadow(
                    Util.mc.textRenderer,
                    "Biome: "+ biome(),
                    1, 9,
                    -1
            );
        }
        if(welcomer.getValue() == true) {
            event.getContext().drawTextWithShadow(
                    Util.mc.textRenderer,
                    "Good " + getTimeOfDay() + mc.player.getName().getString(),
                    420, 1,
                    -1
            );
        }
    }
    private static String biome() {
        if (mc.player == null || mc.world == null) return null;
        Identifier id = mc.world.getRegistryManager().get(RegistryKeys.BIOME).getId(mc.world.getBiome(mc.player.getBlockPos()).value());
        if (id == null) return ("Unknown");

        return (Arrays.stream(id.getPath().split("_")).map(StringUtils::capitalize).collect(Collectors.joining(" ")));
    }
    public static String getTimeOfDay() {
        int timeOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (timeOfDay < 12) return "Morning ";
        if (timeOfDay < 16) return "Afternoon ";
        if (timeOfDay < 21) return "Evening ";
        return "Night ";
    }

}
