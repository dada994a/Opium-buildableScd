package we.devs.opium.manager;

import com.google.common.eventbus.Subscribe;
import we.devs.opium.Opium;
import we.devs.opium.event.Stage;

import we.devs.opium.event.impl.*;
import we.devs.opium.features.Feature;
import we.devs.opium.features.commands.Command;
import we.devs.opium.util.models.Timer;
import we.devs.opium.util.traits.Util;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import net.minecraft.util.Formatting;

public class EventManager extends Feature {
    private final Timer logoutTimer = new Timer();

    public void init() {
        Util.EVENT_BUS.register(this);
    }

    public void onUnload() {
        Util.EVENT_BUS.unregister(this);
    }

    @Subscribe
    public void onUpdate(UpdateEvent event) {
        Util.mc.getWindow().setTitle("Opium Client | 1.21.1");
        if (!fullNullCheck()) {
//            OyVey.inventoryManager.update();
            Opium.moduleManager.onUpdate();
            Opium.moduleManager.sortModules(true);
            onTick();
//            if ((HUD.getInstance()).renderingMode.getValue() == HUD.RenderingMode.Length) {
//                OyVey.moduleManager.sortModules(true);
//            } else {
//                OyVey.moduleManager.sortModulesABC();
//            }
        }
    }

    public void onTick() {
        if (fullNullCheck())
            return;
        Opium.moduleManager.onTick();
        for (PlayerEntity player : Util.mc.world.getPlayers()) {
            if (player == null || player.getHealth() > 0.0F)
                continue;
            Util.EVENT_BUS.post(new DeathEvent(player));
//            PopCounter.getInstance().onDeath(player);
        }
    }

    @Subscribe
    public void onUpdateWalkingPlayer(UpdateWalkingPlayerEvent event) {
        if (fullNullCheck())
            return;
        if (event.getStage() == Stage.PRE) {
            Opium.speedManager.updateValues();
            Opium.rotationManager.updateRotations();
            Opium.positionManager.updatePosition();
        }
        if (event.getStage() == Stage.POST) {
            Opium.rotationManager.restoreRotations();
            Opium.positionManager.restorePosition();
        }
    }

    @Subscribe
    public void onPacketReceive(PacketEvent.Receive event) {
        Opium.serverManager.onPacketReceived();
        if (event.getPacket() instanceof WorldTimeUpdateS2CPacket)
            Opium.serverManager.update();
    }

    @Subscribe
    public void onWorldRender(Render3DEvent event) {
        Opium.moduleManager.onRender3D(event);
    }

    @Subscribe public void onRenderGameOverlayEvent(Render2DEvent event) {
        Opium.moduleManager.onRender2D(event);
    }

    @Subscribe public void onKeyInput(KeyEvent event) {
        Opium.moduleManager.onKeyPressed(event.getKey());
    }

    @Subscribe public void onChatSent(ChatEvent event) {
        if (event.getMessage().startsWith(Command.getCommandPrefix())) {
            event.cancel();
            try {
                if (event.getMessage().length() > 1) {
                    Opium.commandManager.executeCommand(event.getMessage().substring(Command.getCommandPrefix().length() - 1));
                } else {
                    Command.sendMessage("Please enter a command.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Command.sendMessage(Formatting.RED + "An error occurred while running this command. Check the log!");
            }
        }
    }
}