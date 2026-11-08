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

import com.mojang.serialization.Codec;
import io.github.noeppi_noeppi.mods.bongo.Bingo;
import io.github.noeppi_noeppi.mods.bongo.data.Team;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import net.minecraft.world.level.storage.SavedDataStorage;

public class Lobby extends SavedData {
   public static final Codec<Lobby> CODEC = CompoundTag.CODEC.xmap(Lobby::new, var0 -> var0.save(new CompoundTag()));
   public static final SavedDataType<Lobby> DATA_TYPE = new SavedDataType(Identifier.fromNamespaceAndPath("bingolobby", "lobby"), Lobby::new, CODEC, null);
   private ServerLevel level;
   private final Set<UUID> vips = new HashSet<>();
   private final Set<DyeColor> vipTeams = new HashSet<>();
   private int maxPlayers;
   private int countdown;

   public static Lobby get(Level var0) {
      ServerLevel var1 = (ServerLevel)var0;
      SavedDataStorage var2 = var1.getServer().overworld().getDataStorage();
      Lobby var3 = (Lobby)var2.computeIfAbsent(DATA_TYPE);
      var3.level = var1;
      return var3;
   }

   public Lobby() {
      this.vipTeams.add(DyeColor.YELLOW);
      this.maxPlayers = -1;
      this.countdown = -1;
   }

   public Lobby(CompoundTag var1) {
      this();
      this.load(var1);
   }

   public boolean vip(Player var1) {
      return this.vips.contains(var1.getUUID());
   }

   public boolean vip(UUID var1) {
      return this.vips.contains(var1);
   }

   public void setVip(Player var1, boolean var2) {
      this.setVip(var1.getUUID(), var2);
   }

   public void setVip(UUID var1, boolean var2) {
      if (var2) {
         this.vips.add(var1);
      } else {
         this.vips.remove(var1);
      }

      this.setDirty();
   }

   public boolean vipTeam(DyeColor var1) {
      return this.vipTeams.contains(var1);
   }

   public void setVipTeam(DyeColor var1, boolean var2) {
      if (var2) {
         this.vipTeams.add(var1);
      } else {
         this.vipTeams.remove(var1);
      }

      this.setDirty();
   }

   public void setMaxPlayers(int var1) {
      this.maxPlayers = var1;
      this.setDirty();
   }

   public MutableComponent canAccess(Player var1, Team var2) {
      if (var2 == null) {
         return null;
      } else if (this.vipTeam(var2.color) && !this.vip(var1)) {
         return Component.translatable("bingolobby.nojoin.novip");
      } else {
         return this.maxPlayers >= 0 && var2.getPlayers().size() >= this.maxPlayers ? Component.translatable("bingolobby.nojoin.full") : null;
      }
   }

   public CompoundTag save(CompoundTag var1) {
      ListTag var2 = new ListTag();

      for (UUID var4 : this.vips) {
         CompoundTag var5 = new CompoundTag();
         var5.putString("player", var4.toString());
         var2.add(var5);
      }

      var1.put("vips", var2);
      var1.putIntArray("vipTeams", this.vipTeams.stream().mapToInt(DyeColor::getId).toArray());
      var1.putInt("maxPlayers", this.maxPlayers);
      var1.putInt("countdown", this.countdown);
      return var1;
   }

   public void load(CompoundTag var1) {
      this.vips.clear();
      ListTag var2 = var1.getListOrEmpty("vips");

      for (int var3 = 0; var3 < var2.size(); var3++) {
         CompoundTag var4 = var2.getCompoundOrEmpty(var3);
         String var5 = var4.getStringOr("player", "");
         if (!var5.isEmpty()) {
            try {
               this.vips.add(UUID.fromString(var5));
            } catch (IllegalArgumentException var8) {
            }
         }
      }

      this.vipTeams.clear();

      for (int var6 : var1.getIntArray("vipTeams").orElse(new int[0])) {
         DyeColor var7 = DyeColor.byId(var6);
         if (var7 != null) {
            this.vipTeams.add(var7);
         }
      }

      if (this.vipTeams.isEmpty()) {
         this.vipTeams.add(DyeColor.YELLOW);
      }

      this.maxPlayers = var1.getIntOr("maxPlayers", -1);
      this.countdown = var1.getIntOr("countdown", -1);
   }

   public void setCountdown(int var1) {
      int var2 = var1 < 0 ? -1 : var1;
      if (var2 != this.countdown) {
         this.countdown = var2;
         this.setDirty();
      }
   }

   public void tickCountdown() {
      if (this.level != null) {
         Bingo var1 = Bingo.get(this.level);
         boolean var2 = false;
         if ((!var1.active() || var1.running() || var1.won()) && this.countdown != -1) {
            this.countdown = -1;
            var2 = true;
         }

         if (this.countdown > 0) {
            this.countdown--;
            if ((this.countdown > 10 || this.countdown < 1) && (this.countdown >= 60 || this.countdown <= 10 || this.countdown % 10 != 0)) {
               if (this.countdown >= 60 && this.countdown % 60 == 0) {
                  this.broadcast(
                     Component.translatable(
                           "bingolobby.countdown.minutes",
                           new Object[]{Component.literal(Integer.toString(this.countdown / 60)).withStyle(ChatFormatting.DARK_PURPLE)}
                        )
                        .withStyle(ChatFormatting.DARK_AQUA)
                  );
               } else if (this.countdown == 0) {
                  this.countdown = -1;
                  var1.start();
               }
            } else {
               this.broadcast(
                  Component.translatable(
                        "bingolobby.countdown.seconds", new Object[]{Component.literal(Integer.toString(this.countdown)).withStyle(ChatFormatting.DARK_PURPLE)}
                     )
                     .withStyle(ChatFormatting.DARK_AQUA)
               );
            }

            var2 = true;
         }

         if (var2) {
            this.setDirty();
         }
      }
   }

   private void broadcast(Component var1) {
      if (this.level != null) {
         for (ServerPlayer var3 : this.level.players()) {
            var3.sendSystemMessage(var1);
         }
      }
   }

   public int getMaxPlayers() {
      return this.maxPlayers;
   }

   public int getCountdown() {
      return this.countdown;
   }

   public void setDirty() {
      this.setChanged();
   }

   public void setChanged() {
      super.setDirty();
      if (this.level != null) {
         LobbyNetwork.update(this.level);
      }
   }
}
