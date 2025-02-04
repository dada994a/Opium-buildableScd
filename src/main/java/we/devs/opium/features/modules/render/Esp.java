package we.devs.opium.features.modules.render;

import we.devs.opium.event.impl.Render3DEvent;
import we.devs.opium.features.modules.Module;
import we.devs.opium.features.settings.Setting;
import we.devs.opium.util.render.RenderUtil;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;


import java.awt.*;

public class Esp extends Module {
    public enum Mode{
        OUTLINE, FILL
    }
    //settings
    public Setting<Mode> mode = this.register(new Setting<>("Mode", Mode.OUTLINE));
    public Setting<Double> lineThickness = this.register(new Setting<>("Line", 2.0,0.0,5.0,v -> this.mode.getValue() == Mode.OUTLINE));
//item
    public Setting<Boolean> players = this.register(new Setting<>("Players", true));
    public Setting<Boolean> crystal = this.register(new Setting<>("Crystal", false));
    public Setting<Boolean> pearl = this.register(new Setting<>("Pearl", false));
    public Setting<Boolean> items = this.register(new Setting<>("Items", true));
    public Setting<Float> range = this.register(new Setting<>("Range", 100f,10f,500f,v -> this.items.getValue()));

    public Esp(){
        super("ESP","",Category.RENDER,true,false,false);
    }
    @Override
    public void onRender3D(Render3DEvent event) {
        if (players.getValue()) {
            if (mode.getValue() == Mode.OUTLINE) {
                for (AbstractClientPlayerEntity entity : mc.world.getPlayers()) {
                    if (entity != mc.player) {
                        RenderUtil.drawBox(event.getMatrix(), entity.getBoundingBox(), Color.green, lineThickness.getValue());
                    }
                }
            }

        }
        if (players.getValue()) {
            if (mode.getValue() == Mode.FILL) {
                for (AbstractClientPlayerEntity entity : mc.world.getPlayers()) {
                    if (entity != mc.player) {
                        RenderUtil.drawBoxFilled(event.getMatrix(), entity.getBoundingBox(), Color.green);
                    }
                }
            }

        }
        if (items.getValue()){
            if (mode.getValue() == Mode.OUTLINE) {
                Vec3d playerPos = mc.player.getPos();
                for (Entity entity : mc.world.getEntities()) {
                    if (entity instanceof ItemEntity) {
                        Vec3d itemPos = entity.getPos();
                        if (playerPos.distanceTo(itemPos) <= range.getValue()) {
                            RenderUtil.drawBox(event.getMatrix(), entity.getBoundingBox(), Color.white,
                                    lineThickness.getValue().floatValue());
                        }
                    }
                }
            }
            if (mode.getValue() == Mode.FILL) {
                Vec3d playerPos = mc.player.getPos();
                for (Entity entity : mc.world.getEntities()) {
                    if (entity instanceof ItemEntity) {
                        Vec3d itemPos = entity.getPos();
                        if (playerPos.distanceTo(itemPos) <= range.getValue()) {
                            RenderUtil.drawBoxFilled(event.getMatrix(), entity.getBoundingBox(), Color.white);
                        }
                    }
                }
            }

        }
        if (pearl.getValue()){
            if (mode.getValue() == Mode.OUTLINE) {
                Vec3d playerPos = mc.player.getPos();
                for (Entity entity : mc.world.getEntities()) {
                    if (entity instanceof EnderPearlEntity) {
                        Vec3d itemPos = entity.getPos();
                        if (playerPos.distanceTo(itemPos) <= range.getValue()) {
                            RenderUtil.drawBox(event.getMatrix(), entity.getBoundingBox(), Color.pink,
                                    lineThickness.getValue().floatValue());
                        }
                    }
                }
            }
            if (mode.getValue() == Mode.FILL) {
                Vec3d playerPos = mc.player.getPos();
                for (Entity entity : mc.world.getEntities()) {
                    if (entity instanceof EnderPearlEntity) {
                        Vec3d itemPos = entity.getPos();
                        if (playerPos.distanceTo(itemPos) <= range.getValue()) {
                            RenderUtil.drawBoxFilled(event.getMatrix(), entity.getBoundingBox(), Color.pink);
                        }
                    }
                }
            }
        }
        if (crystal.getValue()) {
            if (mode.getValue() == Mode.OUTLINE) {
                Vec3d playerPos = mc.player.getPos();
                for (Entity entity : mc.world.getEntities()) {
                    if (entity instanceof EndCrystalEntity) {
                        Vec3d itemPos = entity.getPos();
                        if (playerPos.distanceTo(itemPos) <= range.getValue()) {
                            RenderUtil.drawBox(event.getMatrix(), entity.getBoundingBox(), Color.BLUE,
                                    lineThickness.getValue().floatValue());
                        }
                    }
                }
            }
            if (mode.getValue() == Mode.FILL) {
                Vec3d playerPos = mc.player.getPos();
                for (Entity entity : mc.world.getEntities()) {
                    if (entity instanceof EndCrystalEntity) {
                        Vec3d itemPos = entity.getPos();
                        if (playerPos.distanceTo(itemPos) <= range.getValue()) {
                            RenderUtil.drawBoxFilled(event.getMatrix(), entity.getBoundingBox(), Color.BLUE);
                        }
                    }
                }
            }


        }
    }
}
