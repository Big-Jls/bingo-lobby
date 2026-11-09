/*
 * Contains code derived from BingoLobby - Copyright (c) noeppi_noeppi.
 * Licensed under the Apache License, Version 2.0.
 *
 * MODIFIED for Minecraft 26.2 / NeoForge by Big_Jls (with DeepSeek-v4.1-Flash-1017) on 2026-11-08.
 * This file may merge or move code from one or more upstream files
 * (for example commands/*, config/*, network/*, render/*, core/*, dimension/*).
 * See LICENSE, NOTICE and CHANGES.md.
 */
package io.github.noeppi_noeppi.mods.bingolobby;

import io.github.noeppi_noeppi.mods.bongo.Bingo;
import io.github.noeppi_noeppi.mods.bongo.data.Team;
import java.nio.file.Path;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.LevelResource;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent.Post;

public final class LobbyManager {
   @SubscribeEvent
   public static void onAboutToStart(ServerAboutToStartEvent var0) {
      Path var1 = var0.getServer().getWorldPath(LevelResource.ROOT).resolve("dimensions").resolve("bingolobby").resolve("lobby");
      WorldPresetManager.copyWorld(var1);
   }

   @SubscribeEvent
   public static void onServerStarted(ServerStartedEvent var0) {
      MinecraftServer var1 = var0.getServer();
      ServerLevel var2 = var1.getLevel(ModDimensions.LOBBY_DIMENSION);
      if (var2 == null) {
         BingoLobby.LOGGER.error("The bingolobby:lobby dimension was not loaded. BingoLobby will not work.");
      } else {
         Lobby.get(var2);
         if ((Boolean)LobbyConfig.AUTO_CREATE.get()) {
            createGame(var1);
         }
      }
   }

   @SubscribeEvent
   public static void onServerTick(Post var0) {
      MinecraftServer var1 = var0.getServer();
      if (var1.getTickCount() % 20 == 0) {
         ServerLevel var2 = var1.getLevel(ModDimensions.LOBBY_DIMENSION);
         if (var2 != null) {
            ServerLevel var3 = var1.overworld();
            Bingo var4 = Bingo.get(var3);
            Lobby var5 = Lobby.get(var2);
            if (var4.active() && !var4.running() && !var4.won()) {
               boolean var6 = var4.getTeams().stream().anyMatch(var0x -> !var0x.getPlayers().isEmpty());
               if (!var6 && var5.getCountdown() >= 0) {
                  var5.setCountdown(-1);
               }
            }

            var5.tickCountdown();
         }
      }
   }

   private static void createGame(MinecraftServer var0) {
      String var1 = (String)LobbyConfig.TASKS.get();
      String var2 = (String)LobbyConfig.SETTINGS.get();
      execute(var0, "bingo create " + var1 + " " + var2);
      Bingo var3 = Bingo.get(var0.overworld());
      if (!var3.active()) {
         BingoLobby.LOGGER.warn("Failed to automatically create a bingo game with tasks '{}' and settings '{}'.", var1, var2);
      } else {
         for (Team var5 : var3.getTeams()) {
            var5.clearPlayers();
         }
      }
   }

   private static void execute(MinecraftServer var0, String var1) {
      var0.getCommands().performPrefixedCommand(var0.createCommandSourceStack(), var1);
   }

   private LobbyManager() {
   }
}
