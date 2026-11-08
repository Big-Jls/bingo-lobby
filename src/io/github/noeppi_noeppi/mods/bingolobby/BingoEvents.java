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

import io.github.noeppi_noeppi.mods.bongo.event.BingoChangeTeamEvent;
import io.github.noeppi_noeppi.mods.bongo.event.BingoPickLevelEvent;
import io.github.noeppi_noeppi.mods.bongo.event.BingoStopEvent.Player;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;

public class BingoEvents {
   @SubscribeEvent
   public void changeTeam(BingoChangeTeamEvent var1) {
      var1.setCanceled(true);
      var1.setFailureMessage(Component.translatable("bingolobby.command.disabled"));
   }

   @SubscribeEvent
   public void bingoStop(Player var1) {
      ModDimensions.teleportToLobby(var1.getPlayer(), true);
   }

   @SubscribeEvent
   public void pickBingoWorld(BingoPickLevelEvent var1) {
      if (var1.getLevel().dimension().equals(ModDimensions.LOBBY_DIMENSION)) {
         ServerLevel var2 = var1.getServer().getLevel(Level.OVERWORLD);
         if (var2 != null) {
            var1.setLevel(var2);
         }
      }
   }
}
