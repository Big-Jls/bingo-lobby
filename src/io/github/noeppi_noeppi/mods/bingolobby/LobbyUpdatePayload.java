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

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.Identifier;

public record LobbyUpdatePayload(CompoundTag tag) implements CustomPacketPayload {
   public static final Type<LobbyUpdatePayload> TYPE = new Type(Identifier.fromNamespaceAndPath("bingolobby", "lobby_update"));
   public static final StreamCodec<ByteBuf, LobbyUpdatePayload> STREAM_CODEC = ByteBufCodecs.COMPOUND_TAG.map(LobbyUpdatePayload::new, LobbyUpdatePayload::tag);

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
