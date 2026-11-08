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
import io.github.noeppi_noeppi.mods.bongo.data.Team;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.WoolCarpetBlock;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

public class EventListener {
   @SubscribeEvent
   public void playerTick(Post var1) {
      if (var1.getEntity() instanceof ServerPlayer var2) {
         if (var2.level().dimension().equals(ModDimensions.LOBBY_DIMENSION)) {
            if (var2.tickCount % 20 == 0) {
               var2.setHealth(var2.getMaxHealth());
               var2.getFoodData().setFoodLevel(20);
               var2.setAirSupply(var2.getMaxAirSupply());
               var2.setRemainingFireTicks(0);
               if (var2.getY() < -3.0) {
                  ModDimensions.teleportToLobby(var2, false);
               }
            }

            if (var2.tickCount % 2 == 1) {
               Bingo var9 = Bingo.get(var2.level());
               DyeColor var4 = null;
               if (!var2.isSpectator()) {
                  var4 = var2.level().getBlockState(var2.blockPosition()).getBlock() instanceof WoolCarpetBlock var6 ? var6.getColor() : null;
                  if (var4 == null) {
                     var4 = var2.level().getBlockState(var2.blockPosition().below()).getBlock() instanceof WoolCarpetBlock var12 ? var12.getColor() : null;
                  }
               }

               if (var9.active() && !var9.running() && !var9.won()) {
                  Team var11 = var4 == null ? null : var9.getTeam(var4);
                  if (var11 == null || !var11.hasPlayer(var2)) {
                     Lobby var13 = Lobby.get(var2.level());
                     MutableComponent var7 = var13.canAccess(var2, var11);
                     if (var7 != null) {
                        var2.sendSystemMessage(var7.withStyle(ChatFormatting.AQUA));
                        ModDimensions.teleportToLobby(var2, false);
                     } else if (var11 != null) {
                        var11.addPlayer(var2);
                     }
                  }
               } else if (var4 != null) {
                  var2.sendSystemMessage(Component.translatable("bingolobby.nojoin.noactive").withStyle(ChatFormatting.AQUA));
                  ModDimensions.teleportToLobby(var2, false);
               }
            }
         }
      }
   }
}
