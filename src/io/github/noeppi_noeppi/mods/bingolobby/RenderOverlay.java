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
import io.github.noeppi_noeppi.mods.bongo.Keybinds;
import io.github.noeppi_noeppi.mods.bongo.data.Team;
import net.minecraft.ChatFormatting;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.neoforged.neoforge.client.gui.GuiLayer;

public class RenderOverlay implements GuiLayer {
   private static final int WIDTH = 150;
   private static final int PADDING = 5;

   public void render(GuiGraphicsExtractor var1, DeltaTracker var2) {
      Minecraft var3 = Minecraft.getInstance();
      if (var3.level != null && var3.player != null) {
         if (ModDimensions.LOBBY_DIMENSION.equals(var3.player.level().dimension())) {
            if (!Keybinds.BIG_OVERLAY.isDown()) {
               Bingo var4 = Bingo.get(var3.level);
               Lobby var5 = LobbyClient.get();
               Font var6 = var3.font;
               MutableComponent var7 = null;
               if (var5.getCountdown() >= 0) {
                  var7 = Component.translatable(
                        "bingolobby.scoreboard.countdown",
                        new Object[]{
                           Component.literal(Integer.toString(var5.getCountdown() / 60)).withStyle(ChatFormatting.GOLD),
                           Component.literal(String.format("%02d", var5.getCountdown() % 60)).withStyle(ChatFormatting.GOLD)
                        }
                     )
                     .withStyle(ChatFormatting.WHITE);
               }

               int var8 = var3.getConnection() == null ? 1 : var3.getConnection().getOnlinePlayers().size();
               MutableComponent var9 = Component.translatable(
                     "bingolobby.scoreboard.online", new Object[]{Component.literal(Integer.toString(var8)).withStyle(ChatFormatting.GOLD)}
                  )
                  .withStyle(ChatFormatting.WHITE);
               MutableComponent var10;
               if (var5.getMaxPlayers() == 0) {
                  var10 = Component.translatable("bingolobby.scoreboard.no_joining").withStyle(ChatFormatting.WHITE);
               } else if (var5.getMaxPlayers() < 0) {
                  var10 = Component.translatable("bingolobby.scoreboard.players_per_team", new Object[]{Component.literal("∞").withStyle(ChatFormatting.GOLD)})
                     .withStyle(ChatFormatting.WHITE);
               } else {
                  var10 = Component.translatable(
                        "bingolobby.scoreboard.players_per_team",
                        new Object[]{Component.literal(Integer.toString(var5.getMaxPlayers())).withStyle(ChatFormatting.GOLD)}
                     )
                     .withStyle(ChatFormatting.WHITE);
               }

               MutableComponent var11 = Component.translatable(
                     var5.vip(var3.player) ? "bingolobby.scoreboard.status.vip" : "bingolobby.scoreboard.status.player"
                  )
                  .withStyle(var5.vip(var3.player) ? ChatFormatting.LIGHT_PURPLE : ChatFormatting.GOLD);
               MutableComponent var12 = Component.translatable("bingolobby.scoreboard.status.info", new Object[]{var11}).withStyle(ChatFormatting.WHITE);
               MutableComponent var13;
               if (var4.active()) {
                  Team var14 = var4.getTeam(var3.player);
                  var13 = var14 == null ? Component.translatable("bingolobby.scoreboard.noteam") : var14.getName().withStyle(ChatFormatting.BOLD);
               } else {
                  var13 = Component.translatable("bingolobby.scoreboard.nogame");
               }

               MutableComponent var25 = Component.literal("B")
                  .withStyle(new ChatFormatting[]{ChatFormatting.RED, ChatFormatting.BOLD})
                  .append(Component.literal("I").withStyle(new ChatFormatting[]{ChatFormatting.BLUE, ChatFormatting.BOLD}))
                  .append(Component.literal("N").withStyle(new ChatFormatting[]{ChatFormatting.GREEN, ChatFormatting.BOLD}))
                  .append(Component.literal("G").withStyle(new ChatFormatting[]{ChatFormatting.YELLOW, ChatFormatting.BOLD}))
                  .append(Component.literal("O").withStyle(new ChatFormatting[]{ChatFormatting.LIGHT_PURPLE, ChatFormatting.BOLD}));
               MutableComponent var15 = Component.translatable("bingolobby.scoreboard.hint").withStyle(ChatFormatting.GREEN);
               int var16 = 9 + 3;
               int var17 = 5 + var16 * 7 + 5;
               int var18 = 150;

               for (Component var22 : new Component[]{var25, var15, var9, var10, var12, var13}) {
                  var18 = Math.max(var18, var6.width(var22) + 10);
               }

               if (var7 != null) {
                  var18 = Math.max(var18, var6.width(var7) + 10);
               }

               int var26 = var1.guiWidth();
               int var27 = var1.guiHeight();
               double var28 = Math.min(var26 * 0.3 / var18, var27 * 0.3 / var17);
               var1.pose().pushMatrix();
               var1.pose().translate((float)(var26 - var18 * var28 - 4.0), (float)(var27 / 2.0 - var17 * var28 / 2.0));
               var1.pose().scale((float)var28, (float)var28);
               var1.fill(0, 0, var18, var17, -1728053248);
               var1.fill(5, 5, var18 - 5, 6, -16711845);
               byte var23 = 5;
               int var24 = 8;
               var1.text(var6, var25, var23, var24, -1, true);
               var24 += var16;
               var1.text(var6, var15, var23, var24, -1, true);
               var24 += var16;
               if (var7 != null) {
                  var1.text(var6, var7, var23, var24, -1, true);
               }

               var24 += var16;
               var1.text(var6, var9, var23, var24, -1, true);
               var24 += var16;
               var1.text(var6, var10, var23, var24, -1, true);
               var24 += var16;
               var1.text(var6, var12, var23, var24, -1, true);
               var24 += var16;
               var1.text(var6, var13, var23, var24, -1, true);
               var1.pose().popMatrix();
            }
         }
      }
   }
}
