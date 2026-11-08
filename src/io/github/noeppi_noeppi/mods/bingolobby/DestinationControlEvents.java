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

import io.github.noeppi_noeppi.mods.bongo.Bingo;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;

public class DestinationControlEvents {
   @SubscribeEvent
   public void playerJoin(PlayerLoggedInEvent var1) {
      if (var1.getEntity() instanceof ServerPlayer var2) {
         ServerLevel var5 = var2.level();
         Bingo var4 = Bingo.get(var5);
         if (!var4.running() && !var4.won() || var4.getTeams().stream().noneMatch(var1x -> var1x.hasPlayer(var2))) {
            ModDimensions.teleportToLobby(var2, true);
         }

         LobbyNetwork.update(var2);
      }
   }

   @SubscribeEvent
   public void playerRespawn(PlayerRespawnEvent var1) {
      if (var1.getEntity() instanceof ServerPlayer var2) {
         ServerLevel var5 = var2.level();
         Bingo var4 = Bingo.get(var5);
         if (!var4.running() && !var4.won() || var4.getTeams().stream().noneMatch(var1x -> var1x.hasPlayer(var2))) {
            ModDimensions.teleportToLobby(var2, false);
         }
      }
   }

   @SubscribeEvent
   public void playerChangeDimension(PlayerChangedDimensionEvent var1) {
      if (var1.getEntity() instanceof ServerPlayer var2) {
         LobbyNetwork.update(var2);
      }
   }
}
