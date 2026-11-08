/*
 * Original work: BingoLobby - Copyright (c) noeppi_noeppi.
 * Licensed under the Apache License, Version 2.0.
 *
 * MODIFIED for Minecraft 26.2 / NeoForge by Big_Jls (with DeepSeek-v4.1-Flash-1017) on 2026-11-08.
 * Changes: Forge 1.20.1 to NeoForge 26.2 port; payloads, commands, lobby dimension
 * and rendering updated; depends on the ReBongo port (modId bongo).
 * See LICENSE and NOTICE.
 */
package io.github.noeppi_noeppi.mods.bingolobby;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import org.slf4j.Logger;

@Mod("bingolobby")
public final class BingoLobby {
   public static final String MODID = "bingolobby";
   public static final Logger LOGGER = LogUtils.getLogger();
   private static BingoLobby instance;

   public BingoLobby(ModContainer var1) {
      instance = this;
      IEventBus var2 = var1.getEventBus();
      var1.registerConfig(Type.COMMON, LobbyConfig.SPEC);
      var2.addListener(this::registerPayloads);
      NeoForge.EVENT_BUS.addListener(LobbyCommands::register);
      NeoForge.EVENT_BUS.register(new DestinationControlEvents());
      NeoForge.EVENT_BUS.register(new LobbyEvents());
      NeoForge.EVENT_BUS.register(new BingoEvents());
      NeoForge.EVENT_BUS.register(new EventListener());
      NeoForge.EVENT_BUS.addListener(LobbyManager::onAboutToStart);
      NeoForge.EVENT_BUS.addListener(LobbyManager::onServerStarted);
      NeoForge.EVENT_BUS.addListener(LobbyManager::onServerTick);
      if (FMLEnvironment.getDist() == Dist.CLIENT) {
         var2.addListener(this::registerGuiLayers);
      }
   }

   public static BingoLobby getInstance() {
      return instance;
   }

   private void registerPayloads(RegisterPayloadHandlersEvent var1) {
      var1.registrar("1")
         .playToClient(LobbyUpdatePayload.TYPE, LobbyUpdatePayload.STREAM_CODEC, (var0, var1x) -> var1x.enqueueWork(() -> LobbyClient.update(var0.tag())));
   }

   @OnlyIn(Dist.CLIENT)
   private void registerGuiLayers(RegisterGuiLayersEvent var1) {
      var1.registerAbove(VanillaGuiLayers.CHAT, Identifier.fromNamespaceAndPath("bingolobby", "lobby"), new RenderOverlay());
   }
}
