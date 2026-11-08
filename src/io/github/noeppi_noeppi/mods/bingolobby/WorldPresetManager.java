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

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import net.neoforged.fml.loading.FMLPaths;

public class WorldPresetManager {
   public static void copyWorld(final Path var0) {
      Path var1 = FMLPaths.CONFIGDIR.get().resolve("bingolobby-preset.zip");
      if (!Files.isRegularFile(var1)) {
         try (InputStream var8 = WorldPresetManager.class.getResourceAsStream("/bingolobby-preset.zip")) {
            if (var8 != null) {
               Files.createDirectories(var1.getParent());
               Files.copy(var8, var1);
            }
         } catch (IOException var9) {
            throw new RuntimeException("Failed to install BingoLobby preset", var9);
         }
      }

      if (Files.isRegularFile(var1) && !Files.exists(var0)) {
         try (final FileSystem var2 = FileSystems.newFileSystem(URI.create("jar:" + var1.toUri()), Map.of())) {
            Files.createDirectories(var0.getParent());
            Files.walkFileTree(var2.getPath("/"), new FileVisitor<Path>() {
               public FileVisitResult preVisitDirectory(Path var1, BasicFileAttributes var2x) throws IOException {
                  Files.createDirectory(var0.resolve(var2.getPath("/").relativize(var1).toString()));
                  return FileVisitResult.CONTINUE;
               }

               public FileVisitResult visitFile(Path var1, BasicFileAttributes var2x) throws IOException {
                  Files.copy(var1, var0.resolve(var2.getPath("/").relativize(var1).toString()));
                  return FileVisitResult.CONTINUE;
               }

               public FileVisitResult visitFileFailed(Path var1, IOException var2x) throws IOException {
                  throw var2x;
               }

               public FileVisitResult postVisitDirectory(Path var1, IOException var2x) throws IOException {
                  if (var2x != null) {
                     throw var2x;
                  } else {
                     return FileVisitResult.CONTINUE;
                  }
               }
            });
         } catch (IOException var7) {
            throw new RuntimeException("Failed to copy BingoLobby preset", var7);
         }
      }
   }
}
