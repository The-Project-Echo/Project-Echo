package net.projectecho.addon.impl;

import lombok.var;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.projectecho.addon.api.Addon;
import net.projectecho.gui.toggles.Option;
import net.projectecho.manager.FontManager;
import net.projectecho.utils.RenderingUtils;

import java.util.Arrays;
import java.util.List;

public class BedwarsHud extends Addon {

    public static Option ironOpt = new Option("Iron", true), goldOpt = new Option("Gold", true),
            diamondOpt = new Option("Diamond", true), emeraldOpt = new Option("Emerald", true), ArrowOpt = new Option("Arrows", false);
    public int iron, gold, diamond, emerald, arrow;
    public Item item;

    public BedwarsHud() {
        super("bw", "Bedwars Hud");
    }

    @Override
    public void entityHook() {
        int iron = 0, gold = 0, diamond = 0, emerald = 0, arrow = 0;
        if (mc.theWorld != null) {
            for (int i = 9; i < 45; i++) {
                ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (stack != null) {
                    Item item = stack.getItem();
                    int stackSize = stack.stackSize;

                    switch (item.getUnlocalizedName()) {
                        case "item.ingotIron":
                            iron += stackSize;
                            break;
                        case "item.ingotGold":
                            gold += stackSize;
                            break;
                        case "item.diamond":
                            diamond += stackSize;
                            break;
                        case "item.emerald":
                            emerald += stackSize;
                            break;
                        case "item.arrow":
                            arrow += stackSize;
                            break;
                    }
                }
            }
            // Update values outside the loop to save on performance
            this.iron = iron;
            this.gold = gold;
            this.diamond = diamond;
            this.emerald = emerald;
            this.arrow = arrow;
        }
        super.entityHook();
    }

    @Override
    public void displayHook() {
        drawBedwarsOverlay();
        super.displayHook();
    }

    public void drawBedwarsOverlay() {
        // List of items to render

        List<ItemInfo> items = Arrays.asList(
                new ItemInfo(ironOpt.val, String.valueOf(iron), "textures/items/iron_ingot.png", 21, 209, 2, 205),
                new ItemInfo(goldOpt.val, String.valueOf(gold), "textures/items/gold_ingot.png", 21, 224, 2, 220),
                new ItemInfo(diamondOpt.val, String.valueOf(diamond), "textures/items/diamond.png", 21, 241, 2, 235),
                new ItemInfo(emeraldOpt.val, String.valueOf(emerald), "textures/items/emerald.png", 21, 258, 2, 254),
                new ItemInfo(ArrowOpt.val, String.valueOf(arrow), "textures/items/arrow.png", 21, 275, 2, 269));

        var text_offset = 210;
        var image_offset = 205;
        // Iterate over the list and render each item
        for (ItemInfo item : items) {
            if (item.option) {
                FontManager.getFont(14).drawStringWithShadow(item.text, item.xText, text_offset, -1);
                RenderingUtils.drawImg(new ResourceLocation(item.imagePath), item.xImg, image_offset, 16, 16);
                text_offset += 19;
                image_offset += 19;
            }
        }
    }

    // Creating a class to make the code more clean
    static class ItemInfo {
        int xText, yText, xImg, yImg;
        String text, imagePath;
        boolean option;

        ItemInfo(boolean option, String text, String imagePath, int xText, int yText, int xImg, int yImg) {
            this.option = option;
            this.text = text;
            this.imagePath = imagePath;
            this.xText = xText;
            this.yText = yText;
            this.xImg = xImg;
            this.yImg = yImg;
        }
    }
}
