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

public final class LobbyClient {
   private static Lobby lobby = new Lobby();

   public static Lobby get() {
      return lobby;
   }

   public static void update(CompoundTag var0) {
      Lobby var1 = new Lobby();
      var1.load(var0);
      lobby = var1;
   }

   private LobbyClient() {
   }
}
