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

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

public final class LobbyNetwork {
   public static void update(Level var0) {
      if (var0 instanceof ServerLevel var1) {
         PacketDistributor.sendToPlayersInDimension(var1, new LobbyUpdatePayload(Lobby.get(var1).save(new CompoundTag())), new CustomPacketPayload[0]);
      }
   }

   public static void update(Player var0) {
      if (var0 instanceof ServerPlayer var1 && var1.level() instanceof ServerLevel var2) {
         PacketDistributor.sendToPlayer(var1, new LobbyUpdatePayload(Lobby.get(var2).save(new CompoundTag())), new CustomPacketPayload[0]);
      }
   }

   private LobbyNetwork() {
   }
}
