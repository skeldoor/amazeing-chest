package com.skeldoor;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

import java.awt.*;

@ConfigGroup("AmazeingChest")
public interface AmazeingChestConfig extends Config
{
    @ConfigItem(
            keyName = "sendNotification",
            name = "Send Notification",
            description = "Sends you a notification a the chest closes"
    )
    default boolean sendNotification()
    {
        return true;
    }

    @ConfigItem(
            keyName = "overlayColour",
            name = "Overlay Colour",
            description = "Change the colour of the overlay displayed"
    )
    default Color overlayColour()
    {
        return Color.green;
    }
}
