package com.wingman.defaultplugins.entityhider;

import com.wingman.client.api.generated.GameAPI;
import com.wingman.client.api.generated.NPC;
import com.wingman.client.api.generated.Player;
import com.wingman.client.api.plugin.Plugin;
import com.wingman.client.api.plugin.PluginDependency;
import com.wingman.client.api.plugin.PluginHelper;
import com.wingman.client.api.transformer.Transformers;
import com.wingman.client.api.ui.settingscreen.SettingsItem;
import com.wingman.client.api.ui.settingscreen.SettingsSection;
import javafx.scene.control.CheckBox;

@PluginDependency(
        id = "DevUtils-defaultplugins",
        version = ">=0.0.1"
)
@Plugin(
        id = "EntityHider-defaultplugins",
        name = "Entity Hider",
        description = "Hides in-game entities such as players and NPCs.",
        version = "0.0.1"
)
public class EntityHider {

    @Plugin.Helper
    public PluginHelper helper;

    private static EntityHiderSettings settings;

    @Plugin.Setup
    public void setup() {
        settings = new EntityHiderSettings(helper);

        SettingsSection settingsSection = new SettingsSection(helper.getContainer().getInfo());

        {
            SettingsItem settingsItem = new SettingsItem("Hide other players");

            CheckBox checkBox = new CheckBox();

            checkBox.setSelected(settings.isHidingPlayers());
            checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                settings.setHidingPlayers(newValue);
                settings.saveToFile();
            });

            settingsItem.add(checkBox);
            settingsSection.add(settingsItem);
        }

        {
            SettingsItem settingsItem = new SettingsItem("Hide other players UI elements");

            CheckBox checkBox = new CheckBox();

            checkBox.setSelected(settings.isHidingPlayers2D());
            checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                settings.setHidingPlayers2D(newValue);
                settings.saveToFile();
            });

            settingsItem.add(checkBox);
            settingsSection.add(settingsItem);
        }

        {
            SettingsItem settingsItem = new SettingsItem("Hide yourself");

            CheckBox checkBox = new CheckBox();

            checkBox.setSelected(settings.isHidingLocalPlayer());
            checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                settings.setHidingLocalPlayer(newValue);
                settings.saveToFile();
            });

            settingsItem.add(checkBox);
            settingsSection.add(settingsItem);
        }

        {
            SettingsItem settingsItem = new SettingsItem("Hide your own UI elements");

            CheckBox checkBox = new CheckBox();

            checkBox.setSelected(settings.isHidingLocalPlayer2D());
            checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                settings.setHidingLocalPlayer2D(newValue);
                settings.saveToFile();
            });

            settingsItem.add(checkBox);
            settingsSection.add(settingsItem);
        }

        {
            SettingsItem settingsItem = new SettingsItem("Hide NPCs");

            CheckBox checkBox = new CheckBox();

            checkBox.setSelected(settings.isHidingNPCs());
            checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                settings.setHidingNPCs(newValue);
                settings.saveToFile();
            });

            settingsItem.add(checkBox);
            settingsSection.add(settingsItem);
        }

        {
            SettingsItem settingsItem = new SettingsItem("Hide NPC UI elements");

            CheckBox checkBox = new CheckBox();

            checkBox.setSelected(settings.isHidingNPCs2D());
            checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                settings.setHidingNPCs2D(newValue);
                settings.saveToFile();
            });

            settingsItem.add(checkBox);
            settingsSection.add(settingsItem);
        }

        helper.registerSettingsSection(settingsSection);

        Transformers.TRANSFORMERS.add(new CancelEntityRenderingTransformer());
    }

    public static boolean shouldDraw(Object entity, boolean drawingUI) {
        if (entity != null) {
            if (entity instanceof Player) {
                if (drawingUI) {
                    if ((settings.isHidingLocalPlayer2D() && entity == GameAPI.getLocalPlayer())
                            || (settings.isHidingPlayers2D() && entity != GameAPI.getLocalPlayer())) {
                        return false;
                    }
                } else {
                    if ((settings.isHidingLocalPlayer() && entity == GameAPI.getLocalPlayer())
                            || (settings.isHidingPlayers() && entity != GameAPI.getLocalPlayer())) {
                        return false;
                    }
                }
            } else if (entity instanceof NPC) {
                if (drawingUI) {
                    if (settings.isHidingNPCs2D()) {
                        return false;
                    }
                } else {
                    if (settings.isHidingNPCs()) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
