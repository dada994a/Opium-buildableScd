package we.devs.opium.features.modules.player;


import com.mojang.authlib.GameProfile;
import we.devs.opium.features.modules.Module;
import we.devs.opium.features.settings.Setting;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.entity.Entity;
import java.util.UUID;

public class FakePlayer extends Module {
    private int id = -1;

    public Setting<String> name = this.register(new Setting<>("Name", "FakePlayer"));
    public Setting<Boolean> copyInventory = this.register(new Setting<>("CopyInventory", false));
    public FakePlayer(){
        super("FakePlayer","",Category.PLAYER,true,false,false);
    }
    @Override
    public void onEnable() {
        if (mc.world == null || mc.player == null) {
            this.setEnabled(false);
            return;
        }
        Entity entity = new OtherClientPlayerEntity(mc.world,
                new GameProfile(UUID.randomUUID(), name.getValue())) {{
            this.setHealth((20f));
            this.setPosition(mc.player.getPos());
            this.setYaw(mc.player.getYaw());
            this.setPitch(mc.player.getPitch());
            this.bodyYaw = mc.player.bodyYaw;
            this.prevBodyYaw = this.bodyYaw;
            this.headYaw = mc.player.headYaw;
            this.prevHeadYaw = this.headYaw;
            this.setPose(mc.player.getPose());
            if (copyInventory.getValue()) {
                this.getInventory().clone(mc.player.getInventory());
            }
        }};

        mc.world.addEntity(entity);
        this.id = entity.getId();
    }

    @Override
    public void onDisable() {
        if (mc.world == null || mc.player == null) return;
        mc.world.removeEntity(id, Entity.RemovalReason.DISCARDED);
        this.id = -1;
    }
}