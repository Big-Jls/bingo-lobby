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

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityMobGriefingEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.LeftClickEmpty;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickEmpty;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.neoforged.neoforge.event.level.BlockEvent.BlockToolModificationEvent;
import net.neoforged.neoforge.event.level.BlockEvent.EntityMultiPlaceEvent;
import net.neoforged.neoforge.event.level.BlockEvent.EntityPlaceEvent;
import net.neoforged.neoforge.event.level.BlockEvent.FarmlandTrampleEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent.Start;

public class LobbyEvents {
   private static boolean inLobby(Level var0) {
      return var0 != null && var0.dimension().equals(ModDimensions.LOBBY_DIMENSION);
   }

   private static boolean inLobby(LevelAccessor var0) {
      return var0 instanceof Level var1 && inLobby(var1);
   }

   @SubscribeEvent
   public void rightClickBlock(RightClickBlock var1) {
      cancelInteract(var1);
   }

   @SubscribeEvent
   public void rightClickItem(RightClickItem var1) {
      cancelInteract(var1);
   }

   @SubscribeEvent
   public void rightClickEmpty(RightClickEmpty var1) {
      cancelInteract(var1);
   }

   @SubscribeEvent
   public void leftClickBlock(LeftClickBlock var1) {
      cancelInteract(var1);
   }

   @SubscribeEvent
   public void leftClickEmpty(LeftClickEmpty var1) {
      cancelInteract(var1);
   }

   @SubscribeEvent
   public void entityInteract(EntityInteract var1) {
      cancelInteract(var1);
   }

   private static void cancelInteract(PlayerInteractEvent var0) {
      if (inLobby(var0.getEntity().level()) && var0 instanceof ICancellableEvent var1) {
         var1.setCanceled(true);
      }
   }

   @SubscribeEvent
   public void blockPlace(EntityPlaceEvent var1) {
      if (inLobby(var1.getLevel()) && var1 instanceof ICancellableEvent) {
         EntityPlaceEvent var2 = var1;
         var2.setCanceled(true);
      }
   }

   @SubscribeEvent
   public void blockMultiPlace(EntityMultiPlaceEvent var1) {
      if (inLobby(var1.getLevel()) && var1 instanceof ICancellableEvent) {
         EntityMultiPlaceEvent var2 = var1;
         var2.setCanceled(true);
      }
   }

   @SubscribeEvent
   public void farmlandTrample(FarmlandTrampleEvent var1) {
      if (inLobby(var1.getEntity().level())) {
         var1.setCanceled(true);
      }
   }

   @SubscribeEvent
   public void blockToolModification(BlockToolModificationEvent var1) {
      if (inLobby(var1.getLevel())) {
         var1.setCanceled(true);
      }
   }

   @SubscribeEvent
   public void explode(Start var1) {
      if (inLobby(var1.getLevel())) {
         var1.setCanceled(true);
      }
   }

   @SubscribeEvent
   public void mobSpawn(FinalizeSpawnEvent var1) {
      if (inLobby(var1.getEntity().level())) {
         var1.setSpawnCancelled(true);
      }
   }

   @SubscribeEvent
   public void mobGriefing(EntityMobGriefingEvent var1) {
      Entity var2 = var1.getEntity();
      if (var2 != null && inLobby(var2.level())) {
         var1.setCanGrief(false);
      }
   }

   @SubscribeEvent
   public void livingDamage(LivingIncomingDamageEvent var1) {
      if (var1.getEntity() instanceof Player && inLobby(var1.getEntity().level())) {
         var1.setCanceled(true);
      }
   }
}
