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

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import io.github.noeppi_noeppi.mods.bongo.Bingo;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.DyeColor;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

public final class LobbyCommands {
   private static final SimpleCommandExceptionType UNKNOWN_TEAM = new SimpleCommandExceptionType(Component.literal("Unknown team"));

   public static void register(RegisterCommandsEvent var0) {
      var0.getDispatcher()
         .register(
            (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal(
                                 "bingo"
                              )
                              .then(
                                 ((LiteralArgumentBuilder)Commands.literal("vip").requires(var0x -> Commands.LEVEL_GAMEMASTERS.check(var0x.permissions())))
                                    .then(
                                       Commands.argument("players", EntityArgument.players())
                                          .executes(
                                             var0x -> {
                                                Collection<ServerPlayer> var1 = EntityArgument.getPlayers(var0x, "players");
                                                Lobby var2 = lobby(var0x);

                                                for (ServerPlayer var4 : var1) {
                                                   var2.setVip(var4, true);
                                                }

                                                ((CommandSourceStack)var0x.getSource())
                                                   .sendSuccess(
                                                      () -> Component.translatable(
                                                         "bingolobby.command.vipadd", new Object[]{Component.literal(Integer.toString(var1.size()))}
                                                      ),
                                                      true
                                                   );
                                                return 0;
                                             }
                                          )
                                    )
                              ))
                           .then(
                              ((LiteralArgumentBuilder)Commands.literal("unvip").requires(var0x -> Commands.LEVEL_GAMEMASTERS.check(var0x.permissions())))
                                 .then(
                                    Commands.argument("players", EntityArgument.players())
                                       .executes(
                                          var0x -> {
                                             Collection<ServerPlayer> var1 = EntityArgument.getPlayers(var0x, "players");
                                             Lobby var2 = lobby(var0x);

                                             for (ServerPlayer var4 : var1) {
                                                var2.setVip(var4, false);
                                             }

                                             ((CommandSourceStack)var0x.getSource())
                                                .sendSuccess(
                                                   () -> Component.translatable(
                                                      "bingolobby.command.vipremove", new Object[]{Component.literal(Integer.toString(var1.size()))}
                                                   ),
                                                   true
                                                );
                                             return 0;
                                          }
                                       )
                                 )
                           ))
                        .then(
                           ((LiteralArgumentBuilder)Commands.literal("vipteam").requires(var0x -> Commands.LEVEL_GAMEMASTERS.check(var0x.permissions())))
                              .then(
                                 Commands.argument("team", StringArgumentType.word())
                                    .suggests((var0x, var1) -> SharedSuggestionProvider.suggest(teamNames(), var1))
                                    .executes(
                                       var0x -> {
                                          DyeColor var1 = team(var0x);
                                          lobby(var0x).setVipTeam(var1, true);
                                          ((CommandSourceStack)var0x.getSource())
                                             .sendSuccess(
                                                () -> Component.translatable("bingolobby.command.vipteamadd", new Object[]{Component.literal(var1.getName())}),
                                                true
                                             );
                                          return 0;
                                       }
                                    )
                              )
                        ))
                     .then(
                        ((LiteralArgumentBuilder)Commands.literal("unvipteam").requires(var0x -> Commands.LEVEL_GAMEMASTERS.check(var0x.permissions())))
                           .then(
                              Commands.argument("team", StringArgumentType.word())
                                 .suggests((var0x, var1) -> SharedSuggestionProvider.suggest(teamNames(), var1))
                                 .executes(
                                    var0x -> {
                                       DyeColor var1 = team(var0x);
                                       lobby(var0x).setVipTeam(var1, false);
                                       ((CommandSourceStack)var0x.getSource())
                                          .sendSuccess(
                                             () -> Component.translatable("bingolobby.command.vipteamremove", new Object[]{Component.literal(var1.getName())}),
                                             true
                                          );
                                       return 0;
                                    }
                                 )
                           )
                     ))
                  .then(
                     ((LiteralArgumentBuilder)Commands.literal("maxplayers").requires(var0x -> Commands.LEVEL_GAMEMASTERS.check(var0x.permissions())))
                        .then(
                           Commands.argument("maxPlayers", IntegerArgumentType.integer(-1))
                              .executes(
                                 var0x -> {
                                    int var1 = IntegerArgumentType.getInteger(var0x, "maxPlayers");
                                    lobby(var0x).setMaxPlayers(var1);
                                    if (var1 < 0) {
                                       ((CommandSourceStack)var0x.getSource())
                                          .sendSuccess(() -> Component.translatable("bingolobby.command.maxplayers.unlimited"), true);
                                    } else {
                                       ((CommandSourceStack)var0x.getSource())
                                          .sendSuccess(
                                             () -> Component.translatable(
                                                "bingolobby.command.maxplayers.limited", new Object[]{Component.literal(Integer.toString(var1))}
                                             ),
                                             true
                                          );
                                    }

                                    return 0;
                                 }
                              )
                        )
                  ))
               .then(
                  ((LiteralArgumentBuilder)Commands.literal("countdown").requires(var0x -> Commands.LEVEL_GAMEMASTERS.check(var0x.permissions())))
                     .then(
                        Commands.argument("countdown", IntegerArgumentType.integer(-1))
                           .executes(
                              var0x -> {
                                 ServerPlayer var1 = ((CommandSourceStack)var0x.getSource()).getPlayerOrException();
                                 Bingo var2 = Bingo.get(var1.level());
                                 if (!var2.active()) {
                                    throw new SimpleCommandExceptionType(Component.translatable("bongo.cmd.start.notcreated")).create();
                                 } else if (!var2.running() && !var2.won()) {
                                    int var3 = IntegerArgumentType.getInteger(var0x, "countdown");
                                    lobby(var0x).setCountdown(var3);
                                    ((CommandSourceStack)var0x.getSource())
                                       .sendSuccess(
                                          () -> Component.translatable(
                                             "bingolobby.countdown.set",
                                             new Object[]{
                                                Component.literal(Integer.toString(Math.max(0, var3) / 60)),
                                                Component.literal(Integer.toString(Math.max(0, var3) % 60))
                                             }
                                          ),
                                          true
                                       );
                                    return 0;
                                 } else {
                                    throw new SimpleCommandExceptionType(Component.translatable("bongo.cmd.start.alreadyrunning")).create();
                                 }
                              }
                           )
                     )
               )
         );
   }

   private static Lobby lobby(CommandContext<CommandSourceStack> var0) {
      return Lobby.get(((CommandSourceStack)var0.getSource()).getServer().overworld());
   }

   private static DyeColor team(CommandContext<CommandSourceStack> var0) throws CommandSyntaxException {
      DyeColor var1 = DyeColor.byName(StringArgumentType.getString(var0, "team"), null);
      if (var1 == null) {
         throw UNKNOWN_TEAM.create();
      } else {
         return var1;
      }
   }

   private static List<String> teamNames() {
      return Arrays.stream(DyeColor.values()).<String>map(DyeColor::getName).toList();
   }

   private LobbyCommands() {
   }
}
