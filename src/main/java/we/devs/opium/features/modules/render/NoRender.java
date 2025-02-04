package we.devs.opium.features.modules.render;



import we.devs.opium.event.eventbus.EventHandler;
import we.devs.opium.event.impl.PacketEvent;
import we.devs.opium.event.impl.Render3DEvent;
import we.devs.opium.features.modules.Module;
import we.devs.opium.features.settings.Setting;
import net.minecraft.client.particle.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.entity.projectile.thrown.ExperienceBottleEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;

public class NoRender extends Module {
    private int itemsCounter;
    public Setting<Boolean> antiTitle = this.register(new Setting<>("AntiTitle", false));
    public Setting<Boolean> potions = this.register(new Setting<>("Potions", false));
    public Setting<Boolean> xp = this.register(new Setting<>("EXP", false));
    public Setting<Boolean> arrows = this.register(new Setting<>("Arrows", false));
    public Setting<Boolean> eggs = this.register(new Setting<>("Egg", false));
    public Setting<Boolean> noArmorStand = this.register(new Setting<>("ArmourStands", false));
    public Setting<Boolean> crystals = this.register(new Setting<>("Crystals", false));
    public Setting<Boolean> items = this.register(new Setting<>("Items", false));

    public NoRender() {
        super("NoRender", "Allows you to stop rendering stuff", Category.RENDER, true, false, false);
    }
    @EventHandler
    public void onPacketReceive(PacketEvent.Receive event){
        if(event.getPacket() instanceof TitleS2CPacket && antiTitle.getValue()){
            event.setCancelled(true);
        }
    }

    @Override
    public void onRender3D(Render3DEvent event) {
        for(Entity ent : mc.world.getEntities()){
            if(ent instanceof PotionEntity){
                if(potions.getValue())
                    mc.world.removeEntity(ent.getId(), Entity.RemovalReason.KILLED);
            }
            if(ent instanceof ExperienceBottleEntity){
                if(xp.getValue())
                    mc.world.removeEntity(ent.getId(), Entity.RemovalReason.KILLED);
            }
            if(ent instanceof ArrowEntity){
                if(arrows.getValue())
                    mc.world.removeEntity(ent.getId(), Entity.RemovalReason.KILLED);
            }
            if(ent instanceof EggEntity){
                if(eggs.getValue())
                    mc.world.removeEntity(ent.getId(), Entity.RemovalReason.KILLED);
            }
            if(ent instanceof ArmorStandEntity){
                if(noArmorStand.getValue())
                    mc.world.removeEntity(ent.getId(), Entity.RemovalReason.KILLED);
            }
            if(ent instanceof EndCrystalEntity){
                if(crystals.getValue())
                    mc.world.removeEntity(ent.getId(), Entity.RemovalReason.KILLED);
            }
            if (ent instanceof ItemEntity) {
                itemsCounter++;
                if (items.getValue()) mc.world.removeEntity(ent.getId(), Entity.RemovalReason.KILLED);
            }
        }
    }

}
