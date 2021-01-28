package net.dohaw.customgui;

import lombok.Getter;
import net.dohaw.corelib.helpers.MathHelper;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuiManager {

    private CustomGUIPlugin plugin;
    @Getter private Map<String, CustomGuiMenu> menus = new HashMap<>();

    public GuiManager(CustomGUIPlugin plugin){
        this.plugin = plugin;
    }

    public void loadGuis(){

        ConfigurationSection menuSection = plugin.getBaseConfig().getMenus();
        if(menuSection != null){
            List<GuiSlotInfo> guiSlotInfo = new ArrayList<>();
            for(String menuKey : menuSection.getKeys(false)){

                String rootPath = "Menus." + menuKey + ".";
                String title = menuSection.getString(rootPath + "Title");
                int numSlots = menuSection.getInt(rootPath + "Number of Slots");

                ConfigurationSection slotsSection = menuSection.getConfigurationSection(rootPath + "Slots");
                if(slotsSection != null){
                    for(String slotStr : slotsSection.getKeys(false)){
                        if(MathHelper.isInt(slotStr)){

                            int slot = Integer.parseInt(slotStr);
                            rootPath = rootPath + "Slots." + slot + ".";
                            List<String> commandsRanOnClick = menuSection.getStringList(rootPath + "Actions.Commands");
                            String guiOpenedOnClick = menuSection.getString(rootPath + "Actions.GUI");
                            Material mat = Material.valueOf(menuSection.getString(rootPath + "Material"));

                        }else{
                            throw new IllegalArgumentException("There is a slot that isn't an integer in the gui menu \"" + menuKey + "\"");
                        }
                    }

                }else{
                    throw new NullPointerException("The \"Slots\" sections in your config cannot be found!");
                }

            }
        }else{
            throw new NullPointerException("The Menus section in your config can't be found!");
        }

    }

}
