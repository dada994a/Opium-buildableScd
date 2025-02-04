package we.devs.opium.features.modules.render;

import we.devs.opium.event.impl.Render3DEvent;
import we.devs.opium.features.modules.Module;
import we.devs.opium.features.settings.Setting;
import we.devs.opium.util.render.RenderUtil;
import we.devs.opium.util.world.BlockUtil;
import net.minecraft.block.entity.*;
import net.minecraft.util.math.Box;


import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class StorageEsp extends Module {
    public enum Mode{
        OUTLINE, FILL
    }
    public Setting<Mode> mode = this.register(new Setting<>("Mode", Mode.OUTLINE));
    public Setting<Double> lineThickness = this.register(new Setting<>("Line", 2.0,0.0,5.0,v -> this.mode.getValue() == Mode.OUTLINE));

    public Setting<Boolean> endportal = this.register(new Setting<>("EndPortal", false));
    //public Setting<Boolean> netherportal = this.register(new Setting<>("NetherPortal", false));
    public Setting<Boolean> chests = this.register(new Setting<>("Chests", false));
    public Setting<Boolean> echests = this.register(new Setting<>("EChests", false));
    public Setting<Boolean> shulkers = this.register(new Setting<>("Shulkers", false));

    public StorageEsp(){
        super("StorageESP","",Category.RENDER,true,false,false);
    }
    @Override
    public void onRender3D(Render3DEvent event) {

        if (chests.getValue()) {
            if (mode.getValue() == Mode.OUTLINE) {
                ArrayList<BlockEntity> blockEntities = BlockUtil.getTileEntities()
                        .collect(Collectors.toCollection(ArrayList::new));
                for (BlockEntity blockEntity : blockEntities) {
                    if (blockEntity instanceof ChestBlockEntity || blockEntity instanceof TrappedChestBlockEntity
                            || blockEntity instanceof BarrelBlockEntity) {
                        Box box = new Box(blockEntity.getPos());
                        RenderUtil.drawBox(event.getMatrix(), box, Color.ORANGE, lineThickness.getValue());
                    }
                }

            }

        }
        if (chests.getValue()){
            if (mode.getValue() == Mode.FILL) {
                ArrayList<BlockEntity> blockEntities = BlockUtil.getTileEntities()
                        .collect(Collectors.toCollection(ArrayList::new));
                for (BlockEntity blockEntity : blockEntities) {
                    if (blockEntity instanceof ChestBlockEntity || blockEntity instanceof TrappedChestBlockEntity
                            || blockEntity instanceof BarrelBlockEntity) {
                        Box box = new Box(blockEntity.getPos());
                        RenderUtil.drawBox(event.getMatrix(), box, Color.ORANGE, lineThickness.getValue());
                    }
                }

            }
        }
        if (shulkers.getValue()){
            if (mode.getValue() == Mode.OUTLINE) {
                ArrayList<BlockEntity> blockEntities = BlockUtil.getTileEntities()
                        .collect(Collectors.toCollection(ArrayList::new));
                for (BlockEntity blockEntity : blockEntities) {
                    if (blockEntity instanceof ShulkerBoxBlockEntity) {
                        Box box = new Box(blockEntity.getPos());
                        RenderUtil.drawBox(event.getMatrix(), box, Color.RED, lineThickness.getValue());
                    }
                }
            }
        }
        if (shulkers.getValue()) {
            if (mode.getValue() == Mode.FILL) {
                ArrayList<BlockEntity> blockEntities = BlockUtil.getTileEntities()
                        .collect(Collectors.toCollection(ArrayList::new));
                for (BlockEntity blockEntity : blockEntities) {
                    if (blockEntity instanceof ShulkerBoxBlockEntity) {
                        Box box = new Box(blockEntity.getPos());
                        RenderUtil.drawBoxFilled(event.getMatrix(), box, Color.RED);
                    }
                }
            }
        }
        if (echests.getValue()) {
            if (mode.getValue() == Mode.OUTLINE) {
                ArrayList<BlockEntity> blockEntities = BlockUtil.getTileEntities()
                        .collect(Collectors.toCollection(ArrayList::new));
                for (BlockEntity blockEntity : blockEntities) {
                    if (blockEntity instanceof EnderChestBlockEntity) {
                        Box box = new Box(blockEntity.getPos());
                        RenderUtil.drawBox(event.getMatrix(), box, Color.MAGENTA, lineThickness.getValue());
                    }
                }
            }
        }
        if (echests.getValue()) {
            if (mode.getValue() == Mode.FILL) {
                ArrayList<BlockEntity> blockEntities = BlockUtil.getTileEntities()
                        .collect(Collectors.toCollection(ArrayList::new));
                for (BlockEntity blockEntity : blockEntities) {
                    if (blockEntity instanceof EnderChestBlockEntity) {
                        Box box = new Box(blockEntity.getPos());
                        RenderUtil.drawBoxFilled(event.getMatrix(), box, Color.MAGENTA);
                    }
                }
            }
        }
        if (endportal.getValue()) {
            if (mode.getValue() == Mode.OUTLINE) {
                ArrayList<BlockEntity> blockEntities = BlockUtil.getTileEntities()
                        .collect(Collectors.toCollection(ArrayList::new));
                for (BlockEntity blockEntity : blockEntities) {
                    if (blockEntity instanceof EndPortalBlockEntity) {
                        Box box = new Box(blockEntity.getPos());
                        RenderUtil.drawBox(event.getMatrix(), box, Color.cyan, lineThickness.getValue());
                    }
                }
            }
        }
        if (endportal.getValue()) {
            if (mode.getValue() == Mode.FILL) {
                ArrayList<BlockEntity> blockEntities = BlockUtil.getTileEntities()
                        .collect(Collectors.toCollection(ArrayList::new));
                for (BlockEntity blockEntity : blockEntities) {
                    if (blockEntity instanceof EndPortalBlockEntity) {
                        Box box = new Box(blockEntity.getPos());
                        RenderUtil.drawBoxFilled(event.getMatrix(), box, Color.cyan);
                    }
                }
            }
        }

        /*
        if (netherportal.getValue()){
            ArrayList<BlockEntity> blockEntities = BlockUtils.getTileEntities()
                    .collect(Collectors.toCollection(ArrayList::new));
            for (BlockEntity blockEntity : blockEntities) {
                if (blockEntity instanceof Nether) {
                    Box box = new Box(blockEntity.getPos());
                    RenderUtil.drawBox(event.getMatrix(), box, Color.cyan, lineThickness.getValue());
                }
            }
        }
        */
    }
}
