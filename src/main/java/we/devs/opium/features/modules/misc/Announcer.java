package we.devs.opium.features.modules.misc;

import com.google.common.eventbus.Subscribe;
import we.devs.opium.features.modules.Module;
import we.devs.opium.features.settings.Setting;
import we.devs.opium.util.math.MathUtil;
import we.devs.opium.util.models.Timer;
import net.minecraft.entity.player.PlayerEntity;

import java.text.DecimalFormat;
import java.util.Random;


public class Announcer extends Module {
    public Setting<Boolean> move = this.register(new Setting<>("Move", true));
    public Setting<Double> delay = this.register(new Setting<>("Delay", 10d, 1d, 30d));

    private double lastPositionX;
    private double lastPositionY;
    private double lastPositionZ;

    private int eaten;

    private int broken;

    private final Timer delayTimer = new Timer();

    public Announcer() {
        super("Announcer", "", Category.MISC, true, false, false);
    }

    @Override
    public void onEnable() {
        eaten = 0;
        broken = 0;

        delayTimer.reset();
    }

    @Override
    public void onUpdate() {
        
        double traveledX = lastPositionX - mc.player.lastRenderX;
        double traveledY = lastPositionY - mc.player.lastRenderY;
        double traveledZ = lastPositionZ - mc.player.lastRenderZ;

        double traveledDistance = Math.sqrt(traveledX * traveledX + traveledY * traveledY + traveledZ * traveledZ);

        if (move.getValue()
                && traveledDistance >= 1
                && traveledDistance <= 1000
                && delayTimer.passedS(delay.getValue())) {

            mc.player.networkHandler.sendChatMessage(getWalkMessage()
                    .replace("{blocks}", new DecimalFormat("0.00").format(traveledDistance)));

            lastPositionX = mc.player.lastRenderX;
            lastPositionY = mc.player.lastRenderY;
            lastPositionZ = mc.player.lastRenderZ;

            delayTimer.reset();
        }// poprobyi zdes
    }

    @Subscribe
    public void onUseItem(PlayerEntity event) {

        int random = MathUtil.getRandom(1, 6);

        //if (eat.getValue())
        //&& event.isPlayer() = mc.player
        // && event.getActiveItem()
        //|| event.getHandItems() instanceof Items.ENCHANTED_GOLDEN_APPLE)
        {

            ++eaten;

            if (eaten >= random && delayTimer.passedS(delay.getValue())) {

                mc.player.networkHandler.sendChatMessage(getEatMessage()
                        .replace("{amount}", "" + eaten)
                        .replace("{name}", "" + event.getActiveItem().getName()));

                eaten = 0;

                delayTimer.reset();
            }
        }
    }
/*
    @Subscribe
    public void onBreakBlock(BlockBreakingInfo event) {

        int random = MathUtil.getRandom(1, 6);

        ++broken;

        if (breakBlock.getValue()
                && broken >= random
                && delayTimer.passedS(delay.getValue())) {

            mc.player.networkHandler.sendChatMessage(getBreakMessage()
                    .replace("{amount}", "" + broken)
                    .replace("{name}", "" + event.getPos()));

            broken = 0;

            delayTimer.reset();
        }
    }

 */



        private String getWalkMessage(){
        String[] walkMessage = {
                "I just flew over {blocks} blocks thanks to Opium!",
                "Я только что пролетел над {blocks} блоками с помощью Opium!",
                "Opium sayesinde {blocks} blok u\u00E7tum!",
                "\u6211\u521A\u521A\u7528 Opium \u8D70\u4E86 {blocks} \u7C73!",
                "Dank Opium bin ich gerade über {blocks} Blöcke geflogen!",
                "Jag hoppade precis över {blocks} blocks tack vare Opium!",
                "Właśnie przeleciałem ponad {blocks} bloki dzięki Opium!",
                "Es tikko nolidoju {blocks} blokus, paldies Opium!",
                "Я щойно пролетів як моль над {blocks} блоками завдяки Opium!",
                "I just fwew ovew {blocks} bwoccs thanks to Opium",
                "Ho appena camminato per {blocks} blocchi grazie a Opium!",
                "עכשיו עפתי {blocks} הודות ל Opium!",
                "Právě jsem proletěl {blocks} bloků díky Opium!"
        };

        return walkMessage[new Random().nextInt(walkMessage.length)];
    }

    private String getBreakMessage() {

        String[] breakMessage = {
                "I just destroyed {amount} {name} with the power of Opium!",
                "Я только что разрушил {amount} {name} с помощью Opium!",
                "Az \u00F6nce {amount} tane {name} k\u0131rd\u0131m. Te\u015Eekk\u00FCrler Opium!",
                "\u6211\u521A\u521A\u7528 Opium \u7834\u574F\u4E86 {amount} {name}!",
                "Ich habe gerade {amount} {name} mit der Kraft von Opium zerstört!",
                "Jag förstörde precis {amount} {name} tack vare Opium!",
                "Właśnie zniszczyłem {amount} {name} za pomocą Opium",
                "Es tikko salauzu {amount} {name} ar spēku Opium!",
                "Я щойно знищив {amount} {name} за допомогою Opium!",
                "I just destwoyed {amount} {name} with the powew of Opium!",
                "Ho appena distrutto {amount} {name} grazie al potere di Opium!",
                "בדיוק חצבתי {amount} {name} בעזרת הכוח של Opium!",
                "Právě jsem zničil {amount} {name} s mocí Opium!"
        };

        return breakMessage[new Random().nextInt(breakMessage.length)];
    }

    private String getEatMessage() {

        String[] eatMessage = {
                "I just ate {amount} {name} thanks to Opium!",
                "Я только что съел {amount} {name} с помощью Opium!",
                "Tam olarak {amount} tane {name} yedim. Te\u015Eekk\u00FCrler Opium",
                "\u6211\u521A\u7528 Opium \u5403\u4E86 {amount} \u4E2A {name}!",
                "Ich habe gerade {amount} {name} dank Opium gegessen!",
                "Jag åt precis {amount} {name} tack vare Opium",
                "Właśnie zjadłem {amount} {name} dzięki Opium",
                "Es tikko apēdu {amount} {name} paldies Opium",
                "Я щойно з’їв {amount} {name} завдяки Opium!",
                "I just ate {amount} {name} thanks to Opium! ^-^",
                "Ho appena mangiato {amount} {name} grazie a Opium!",
                "כרגע אכלתי {amount} {name} הודות לOpium!" ,
                "Právě jsem snědl {amount} {name} díky Opium"
        };

        return eatMessage[new Random().nextInt(eatMessage.length)];
    }
}