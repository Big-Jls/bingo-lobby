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

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;

public final class LobbyConfig {
   public static final ModConfigSpec SPEC;
   public static final BooleanValue AUTO_CREATE;
   public static final IntValue AUTO_START_SECONDS;
   public static final ConfigValue<String> TASKS;
   public static final ConfigValue<String> SETTINGS;

   private LobbyConfig() {
   }

   static {
      Builder var0 = new Builder();
      var0.comment("BingoLobby settings");
      var0.push("lobby");
      AUTO_CREATE = var0.comment("Automatically create a new bingo game when no game is active.").define("auto_create", true);
      AUTO_START_SECONDS = var0.comment("Countdown in seconds before a game starts once at least one team has players.")
         .defineInRange("auto_start_seconds", 30, 0, 3600);
      TASKS = var0.comment("Task set used for automatically created games.").define("tasks", "bongo:hard");
      SETTINGS = var0.comment("Settings used for automatically created games.").define("settings", "bongo:default");
      var0.pop();
      SPEC = var0.build();
   }
}
