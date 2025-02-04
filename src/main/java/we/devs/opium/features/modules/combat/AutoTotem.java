
package we.devs.opium.features.modules.combat;

import we.devs.opium.event.eventbus.EventHandler;
import we.devs.opium.features.modules.Module;
import we.devs.opium.features.settings.Setting;
import we.devs.opium.util.models.Timer;
import we.devs.opium.util.player.EntityUtil;
import we.devs.opium.util.player.InventoryUtil2;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;

public class AutoTotem extends Module {
	public Setting<Boolean> mainHand = this.register(new Setting<>("MainHand", false));
	private final Setting<Integer> health = this.register(new Setting<>("Health", 14, 1, 36));

	public AutoTotem() {
		super("AutoTotem","", Category.COMBAT,true,false,false);

	}
	int totems = 0;
	private final Timer timer = new Timer();

	@Override
	public String getInfo() {
		return String.valueOf(totems);
	}

	@EventHandler
	public void onUpdate() {
		update();
	}
	private void update() {
		if (nullCheck()) return;
		totems = InventoryUtil2.getItemCount(Items.TOTEM_OF_UNDYING);
		if (mc.currentScreen != null && !(mc.currentScreen instanceof ChatScreen) && !(mc.currentScreen instanceof InventoryScreen)) {
			return;
		}
		if (!timer.passedMs(200)) {
			return;
		}
		if (mc.player.getHealth() + mc.player.getAbsorptionAmount() > health.getValue()) {
			return;
		}
		if (mc.player.getMainHandStack().getItem() == Items.TOTEM_OF_UNDYING || mc.player.getOffHandStack().getItem() == Items.TOTEM_OF_UNDYING) {
			return;
		}
		int itemSlot = InventoryUtil2.findItemInventorySlot(Items.TOTEM_OF_UNDYING);
		if (itemSlot != -1) {
			if (mainHand.getValue()) {
				InventoryUtil2.switchToSlot(0);
				if (mc.player.getInventory().getStack(0).getItem() != Items.TOTEM_OF_UNDYING) {
					mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, itemSlot, 0, SlotActionType.PICKUP, mc.player);
					mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, 36, 0, SlotActionType.PICKUP, mc.player);
					mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, itemSlot, 0, SlotActionType.PICKUP, mc.player);
					EntityUtil.syncInventory();
				}
			} else {
				mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, itemSlot, 0, SlotActionType.PICKUP, mc.player);
				mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, 45, 0, SlotActionType.PICKUP, mc.player);
				mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, itemSlot, 0, SlotActionType.PICKUP, mc.player);
				EntityUtil.syncInventory();
			}
			timer.reset();
		}
	}
}
