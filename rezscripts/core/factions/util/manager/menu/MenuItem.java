package rezscripts.core.factions.util.manager.menu;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class MenuItem {
    protected ItemStack item;
    protected int row, col;
    protected Runnable runnable;

    public MenuItem(int row, int col, ItemStack item, String displayName, String[] lore, Runnable runnable) {
        this(row, col, item, displayName, Arrays.asList(lore), runnable);
    }

    public MenuItem(int row, int col, ItemStack item, Runnable runnable) {
        this.row = row;
        this.col = col;
        this.item = item;
        if (runnable == null)
            this.runnable = () -> {
            };
        else
            this.runnable = runnable;
    }
    
    public MenuItem(int row, int col, ItemStack item, String displayName, List<String> lore, Runnable runnable) {
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(displayName);
        ArrayList<String> temp = new ArrayList<String>();
        for (String s : lore)
            temp.add(s);
        im.setLore(temp);
        item.setItemMeta(im);
        this.row = row;
        this.col = col;
        this.item = item;
        if (runnable == null)
            this.runnable = () -> {
            };
        else
            this.runnable = runnable;
    }

    @Override
    public String toString() {
        return item.toString();
    }

}
