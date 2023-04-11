package com.skeldoor;

import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.*;

import javax.inject.Inject;
import java.awt.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class AmazeingChestOverlay extends Overlay {

    private final Client client;
    private final AmazeingChestPlugin plugin;
    private static final Font FONT = FontManager.getRunescapeFont().deriveFont(Font.BOLD, 16);
    private static final int MAX_DISTANCE = 2400;
    private List<MazeChest> mazeChests = new ArrayList<>();

    @Inject
    private AmazeingChestOverlay(Client client, AmazeingChestPlugin plugin)
    {
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
        setPriority(OverlayPriority.HIGHEST);
        this.client = client;
        this.plugin = plugin;
    }

    public void addMazeChest(MazeChest mazeChest){
        mazeChests.add(mazeChest);
    }

    public void removeMazeChest(WorldPoint loc){
        mazeChests.removeIf(mazeChest -> mazeChest.getLocation().equals(loc));
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        graphics.setFont(FONT);
        graphics.setColor(plugin.overlayColour());
        for (MazeChest mazeChest : mazeChests){
            renderGameObjects(graphics, mazeChest, client);
        }
        return null;
    }

    private void renderGameObjects(Graphics2D graphics, MazeChest mazeChest, Client client)
    {
        if (mazeChest != null && mazeChest.getLocation() != null)
        {
            Player player = client.getLocalPlayer();
            LocalPoint mazeChestLocal = LocalPoint.fromWorld(client, mazeChest.getLocation());
            if (mazeChestLocal != null && player.getLocalLocation().distanceTo(mazeChestLocal) <= MAX_DISTANCE)
            {
                StringBuilder stringBuilder = new StringBuilder();
                long despawnTimeMillis = mazeChest.getDespawnTime().toEpochMilli() - Instant.now().toEpochMilli();
                String timerText = String.format("%.0fs", despawnTimeMillis / 1000f);
                stringBuilder.append("Closing in ").append(timerText);
                OverlayUtil.renderTileOverlay(graphics, mazeChest.getGameObject(), stringBuilder.toString(), plugin.overlayColour());
            }
        }
    }
}
