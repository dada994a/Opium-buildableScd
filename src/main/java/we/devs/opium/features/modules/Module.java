package we.devs.opium.features.modules;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import we.devs.opium.Opium;
import we.devs.opium.event.impl.ClientEvent;
import we.devs.opium.event.impl.Render2DEvent;
import we.devs.opium.event.impl.Render3DEvent;
import we.devs.opium.features.Feature;
import we.devs.opium.features.commands.Command;
import we.devs.opium.features.modules.client.Debug;
import we.devs.opium.features.settings.Bind;
import we.devs.opium.features.settings.Setting;
import we.devs.opium.manager.ConfigManager;
import we.devs.opium.util.traits.Jsonable;
import we.devs.opium.util.traits.Util;
import net.minecraft.util.Formatting;

public class Module extends Feature implements Jsonable {
    private final String description;
    private final Category category;
    public Setting<Boolean> enabled = this.register(new Setting<>("Enabled", false));
    public Setting<Boolean> drawn = this.register(new Setting<>("Drawn", true));
    public Setting<Bind> bind = this.register(new Setting<>("Keybind", new Bind(-1)));
    public Setting<String> displayName;
    public boolean hasListener;
    public boolean alwaysListening;
    public boolean hidden;
    public boolean state;

    public Module(String name, String description, Category category, boolean hasListener, boolean hidden, boolean alwaysListening) {
        super(name);
        this.displayName = this.register(new Setting<>("DisplayName", name));
        this.description = description;
        this.category = category;
        this.hasListener = hasListener;
        this.hidden = hidden;
        this.alwaysListening = alwaysListening;
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public void onToggle() {
    }

    public void onLoad() {
    }

    public void onTick() {
    }

    public void onUpdate() {
    }

    public void onRender2D(Render2DEvent event) {
    }

    public void onRender3D(Render3DEvent event) {
    }

    public void onUnload() {
    }

    public String getDisplayInfo() {
        return null;
    }

    public boolean isOn() {
        return this.enabled.getValue();
    }

    public boolean isOff() {
        return !this.enabled.getValue();
    }

    public void setEnabled(boolean enabled) {
        if (enabled) {
            this.enable();
        } else {
            this.disable();
        }
    }

    public void enable() {
        this.enabled.setValue(true);
        this.onToggle();
        this.onEnable();
        if (this.isOn() && this.hasListener && !this.alwaysListening) {
            Util.EVENT_BUS.register(this);
        }
        switch (Debug.INSTANCE.notification.getValue()) {
            case Mio -> Command.sendMessage("§2[+] §f" + getName());
            case Miolegacy -> Command.sendMessage(Formatting.DARK_AQUA + getName() +Formatting.WHITE + ".enable = " + Formatting.GREEN +"true.");
            case Opium -> Command.sendMessage("§2[:^)] §f" + getName());
            case Opiumlegacy -> Command.sendMessage(Formatting.GREEN+getName()+" toggled on");
            case Future -> Command.sendMessage("§7" + getName() + " toggled §aon");
            case SetStantium -> Command.sendMessage(Formatting.BLUE + getName() +Formatting.WHITE +" == "+Formatting.GREEN +"on"+ Formatting.WHITE +";");
        }
    }

    public void disable() {
        if (this.hasListener && !this.alwaysListening) {
            Util.EVENT_BUS.unregister(this);
        }
        this.enabled.setValue(false);
        this.onToggle();
        this.onDisable();
        switch (Debug.INSTANCE.notification.getValue()) {
            case Mio -> Command.sendMessage(Formatting.RED +"[-] "+ Formatting.WHITE+getName());
            case Miolegacy -> Command.sendMessage(Formatting.DARK_AQUA + getName() +Formatting.WHITE + ".enable = " + Formatting.RED +"false.");
            case Opium -> Command.sendMessage(Formatting.RED +"[:^)] "+ Formatting.WHITE+getName());
            case Opiumlegacy -> Command.sendMessage(Formatting.RED+getName()+" toggled on");
            case Future -> Command.sendMessage("§7" + getName() + " toggled §coff");
            case SetStantium -> Command.sendMessage(Formatting.BLUE + getName() +Formatting.WHITE +" == "+Formatting.RED+"off"+ Formatting.WHITE +";");
        }
    }

    public void toggle() {
        ClientEvent event = new ClientEvent(!this.isEnabled() ? 1 : 0, this);
        Util.EVENT_BUS.post(event);
        if (!event.isCancelled()) {
            this.setEnabled(!this.isEnabled());
        }
    }

    public String getDisplayName() {
        return this.displayName.getValue();
    }

    public void setDisplayName(String name) {
        Module module = Opium.moduleManager.getModuleByDisplayName(name);
        Module originalModule = Opium.moduleManager.getModuleByName(name);
        if (module == null && originalModule == null) {
            Command.sendMessage(this.getDisplayName() + ", name: " + this.getName() + ", has been renamed to: " + name);
            this.displayName.setValue(name);
            return;
        }
        Command.sendMessage(Formatting.RED + "A module of this name already exists.");
    }

    @Override public boolean isEnabled() {
        return isOn();
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isDrawn() {
        return this.drawn.getValue();
    }

    public void setDrawn(boolean drawn) {
        this.drawn.setValue(drawn);
    }

    public Category getCategory() {
        return this.category;
    }

    public String getInfo() {
        return null;
    }

    public Bind getBind() {
        return this.bind.getValue();
    }

    public void setBind(int key) {
        this.bind.setValue(new Bind(key));
    }

    public boolean listening() {
        return this.hasListener && this.isOn() || this.alwaysListening;
    }

    public String getFullArrayString() {
        return this.getDisplayName() + Formatting.GRAY + (this.getDisplayInfo() != null ? " [" + Formatting.WHITE + this.getDisplayInfo() + Formatting.GRAY + "]" : "");
    }

    @Override public JsonElement toJson() {
        JsonObject object = new JsonObject();
        for (Setting<?> setting : getSettings()) {
            try {
                if (setting.getValue() instanceof Bind bind) {
                    object.addProperty(setting.getName(), bind.getKey());
                } else {
                    object.addProperty(setting.getName(), setting.getValueAsString());
                }
            } catch (Throwable e) {
            }
        }
        return object;
    }

    @Override public void fromJson(JsonElement element) {
        JsonObject object = element.getAsJsonObject();
        String enabled = object.get("Enabled").getAsString();
        if (Boolean.parseBoolean(enabled)) toggle();
        for (Setting<?> setting : getSettings()) {
            try {
                ConfigManager.setValueFromJson(this, setting, object.get(setting.getName()));
            } catch (Throwable throwable) {
            }
        }
    }

    public enum Category {
        COMBAT("Combat"),
        MISC("Misc"),
        RENDER("Render"),
        MOVEMENT("Movement"),
        PLAYER("Player"),
        EXPLOIT("Exploit"),
        CLIENT("Client");

        private final String name;

        Category(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
}
