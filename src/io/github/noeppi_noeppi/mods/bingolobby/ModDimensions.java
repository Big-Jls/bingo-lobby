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

import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayer.RespawnConfig;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelData.RespawnData;

public final class ModDimensions {
   public static final ResourceKey<Level> LOBBY_DIMENSION = ResourceKey.create(Registries.DIMENSION, Identifier.fromNamespaceAndPath("bingolobby", "lobby"));

   public static void teleportToLobby(ServerPlayer var0, boolean var1) {
      var0.getInventory().clearContent();
      if (var0.level().dimension().equals(LOBBY_DIMENSION)) {
         if (var1) {
            var0.setYRot(0.0F);
            var0.setXRot(0.0F);
         }

         var0.teleportTo(0.5, 65.0, 0.5);
      } else {
         ServerLevel var2 = var0.level().getServer().getLevel(LOBBY_DIMENSION);
         if (var2 == null) {
            BingoLobby.LOGGER.error("The bingolobby:lobby dimension is missing. Please check the datapack and restart the server.");
            var0.sendSystemMessage(Component.literal("BingoLobby failed to load the lobby dimension. Please restart the server."));
            return;
         }

         float var3 = var1 ? 0.0F : var0.getYRot();
         float var4 = var1 ? 0.0F : var0.getXRot();
         var0.teleportTo(var2, 0.5, 65.0, 0.5, Set.of(), var3, var4, false);
      }

      var0.setRespawnPosition(new RespawnConfig(RespawnData.of(LOBBY_DIMENSION, new BlockPos(0, 65, 0), 0.0F, 0.0F), true), false);
   }

   private ModDimensions() {
   }
}
