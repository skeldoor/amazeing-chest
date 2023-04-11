package com.skeldoor;

import javax.inject.Inject;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.GameObjectDespawned;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.client.Notifier;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import java.awt.*;
import java.time.Instant;

@Slf4j
@PluginDescriptor(
	name = "Amazeing Chest",
	description = "Shows how long until a maze random chest closes"
)
public class AmazeingChestPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private AmazeingChestOverlay overlay;

	@Inject
	private AmazeingChestConfig config;

	@Inject
	private Notifier notifier;

	private final int openedChestId = 14988;

	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
	}

	@Subscribe
	public void onGameObjectSpawned(GameObjectSpawned gameObjectSpawned){
		if (gameObjectSpawned.getGameObject().getId() == openedChestId){
			MazeChest mazeChest = new MazeChest(gameObjectSpawned.getGameObject(), gameObjectSpawned.getGameObject().getWorldLocation(), Instant.now());
			overlay.addMazeChest(mazeChest);
		}
	}

	@Subscribe
	public void onGameObjectDespawned(GameObjectDespawned gameObjectDespawned){
		if (gameObjectDespawned.getGameObject().getId() == openedChestId){
			overlay.removeMazeChest(gameObjectDespawned.getGameObject().getWorldLocation());
			if (config.sendNotification()) notifier.notify("Maze random chest closed");
		}
	}

	public Color overlayColour(){
		return config.overlayColour();
	}

	@Provides
	AmazeingChestConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(AmazeingChestConfig.class);
	}
}
