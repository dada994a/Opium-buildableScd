package we.devs.opium.manager;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import we.devs.opium.event.impl.Render2DEvent;
import we.devs.opium.event.impl.Render3DEvent;
import we.devs.opium.features.Feature;
import we.devs.opium.features.modules.Module;
import we.devs.opium.features.modules.client.Debug;
import we.devs.opium.features.modules.client.UI;
import we.devs.opium.features.modules.client.HUD;
import we.devs.opium.features.modules.client.Watermark;
import we.devs.opium.features.modules.combat.AutoTotem;
import we.devs.opium.features.modules.combat.Criticals;
import we.devs.opium.features.modules.combat.ExpThrower;
import we.devs.opium.features.modules.exploit.Ghost;
import we.devs.opium.features.modules.exploit.HitboxDesync;
import we.devs.opium.features.modules.misc.*;
import we.devs.opium.features.modules.movement.*;
import we.devs.opium.features.modules.player.FakePlayer;
import we.devs.opium.features.modules.player.FastPlace;
import we.devs.opium.features.modules.player.NoInteract;
import we.devs.opium.features.modules.player.Replenish;
import we.devs.opium.features.modules.render.*;
import we.devs.opium.util.traits.Jsonable;
import we.devs.opium.util.traits.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleManager implements Jsonable, Util {
    public List<Module> modules = new ArrayList<>();
    public List<Module> sortedModules = new ArrayList<>();
    public List<String> sortedModulesABC = new ArrayList<>();

    public void init() {
        // COMBAT
        modules.add(new Criticals());
        modules.add(new ExpThrower());
        modules.add(new AutoTotem());
        // MISC
        modules.add(new MiddleClick());
        modules.add(new Announcer());
        modules.add(new AutoRespawn());
        modules.add(new AntiAFK());
        modules.add(new Notifier());
        modules.add(new Spammer());
        // RENDER
        modules.add(new BlockHighlight());
        modules.add(new Esp());
        modules.add(new StorageEsp());
        modules.add(new Zoom());
        modules.add(new FullBright());
        modules.add(new NoRender());
        modules.add(new Particles());
        modules.add(new Ambience());
        // MOVEMENT
        modules.add(new Step());
        modules.add(new FastFall());
        modules.add(new Velocity());
        modules.add(new HoleAnchor());
        modules.add(new Sprint());
        modules.add(new NoSlow());
        modules.add(new AutoWalk());
        // PLAYER
        modules.add(new FastPlace());
        modules.add(new Replenish());
        modules.add(new FakePlayer());
        modules.add(new NoInteract());
        // EXPLOIT
        modules.add(new Ghost());
        modules.add(new HitboxDesync());
        // CLIENT
        modules.add(new HUD());
        modules.add(new Debug());
        modules.add(new UI());
        modules.add(new Watermark());

    }

    public Module getModuleByName(String name) {
        for (Module module : this.modules) {
            if (!module.getName().equalsIgnoreCase(name)) continue;
            return module;
        }
        return null;
    }

    public <T extends Module> T getModuleByClass(Class<T> clazz) {
        for (Module module : this.modules) {
            if (!clazz.isInstance(module)) continue;
            return (T) module;
        }
        return null;
    }

    public void enableModule(Class<Module> clazz) {
        Module module = this.getModuleByClass(clazz);
        if (module != null) {
            module.enable();
        }
    }

    public void disableModule(Class<Module> clazz) {
        Module module = this.getModuleByClass(clazz);
        if (module != null) {
            module.disable();
        }
    }

    public void enableModule(String name) {
        Module module = this.getModuleByName(name);
        if (module != null) {
            module.enable();
        }
    }

    public void disableModule(String name) {
        Module module = this.getModuleByName(name);
        if (module != null) {
            module.disable();
        }
    }

    public boolean isModuleEnabled(String name) {
        Module module = this.getModuleByName(name);
        return module != null && module.isOn();
    }

    public boolean isModuleEnabled(Class<Module> clazz) {
        Module module = this.getModuleByClass(clazz);
        return module != null && module.isOn();
    }

    public Module getModuleByDisplayName(String displayName) {
        for (Module module : this.modules) {
            if (!module.getDisplayName().equalsIgnoreCase(displayName)) continue;
            return module;
        }
        return null;
    }

    public ArrayList<Module> getEnabledModules() {
        ArrayList<Module> enabledModules = new ArrayList<>();
        for (Module module : this.modules) {
            if (!module.isEnabled()) continue;
            enabledModules.add(module);
        }
        return enabledModules;
    }

    public ArrayList<String> getEnabledModulesName() {
        ArrayList<String> enabledModules = new ArrayList<>();
        for (Module module : this.modules) {
            if (!module.isEnabled() || !module.isDrawn()) continue;
            enabledModules.add(module.getFullArrayString());
        }
        return enabledModules;
    }

    public ArrayList<Module> getModulesByCategory(Module.Category category) {
        ArrayList<Module> modulesCategory = new ArrayList<Module>();
        this.modules.forEach(module -> {
            if (module.getCategory() == category) {
                modulesCategory.add(module);
            }
        });
        return modulesCategory;
    }

    public List<Module.Category> getCategories() {
        return Arrays.asList(Module.Category.values());
    }

    public void onLoad() {
        this.modules.stream().filter(Module::listening).forEach(EVENT_BUS::register);
        this.modules.forEach(Module::onLoad);
    }

    public void onUpdate() {
        this.modules.stream().filter(Feature::isEnabled).forEach(Module::onUpdate);
    }

    public void onTick() {
        this.modules.stream().filter(Feature::isEnabled).forEach(Module::onTick);
    }

    public void onRender2D(Render2DEvent event) {
        this.modules.stream().filter(Feature::isEnabled).forEach(module -> module.onRender2D(event));
    }

    public void onRender3D(Render3DEvent event) {
        this.modules.stream().filter(Feature::isEnabled).forEach(module -> module.onRender3D(event));
    }

    public void sortModules(boolean reverse) {
        this.sortedModules = this.getEnabledModules().stream().filter(Module::isDrawn)
                .sorted(Comparator.comparing(module -> mc.textRenderer.getWidth(module.getFullArrayString()) * (reverse ? -1 : 1)))
                .collect(Collectors.toList());
    }

    public void sortModulesABC() {
        this.sortedModulesABC = new ArrayList<>(this.getEnabledModulesName());
        this.sortedModulesABC.sort(String.CASE_INSENSITIVE_ORDER);
    }

    public void onUnload() {
        this.modules.forEach(EVENT_BUS::unregister);
        this.modules.forEach(Module::onUnload);
    }

    public void onUnloadPost() {
        for (Module module : this.modules) {
            module.enabled.setValue(false);
        }
    }

    public void onKeyPressed(int eventKey) {
        if (eventKey <= 0) return;
        this.modules.forEach(module -> {
            if (module.getBind().getKey() == eventKey) {
                module.toggle();
            }
        });
    }

    @Override public JsonElement toJson() {
        JsonObject object = new JsonObject();
        for (Module module : modules) {
            object.add(module.getName(), module.toJson());
        }
        return object;
    }

    @Override public void fromJson(JsonElement element) {
        for (Module module : modules) {
            module.fromJson(element.getAsJsonObject().get(module.getName()));
        }
    }

    @Override public String getFileName() {
        return "modules.json";
    }
}
