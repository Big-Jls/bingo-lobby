# Changelog

## 26.2-1.0.0-port.2 — 2026-11-10

### Changed

- Winning a game no longer automatically runs `bingo stop` after a delay. The
  match stays in its won state until an operator ends it manually; the
  `win_return_seconds` config option was removed together with that behaviour.

## 26.2-1.0.0-port.1 — 2026-11-09

First public source release of the unofficial NeoForge 26.2 port.

### Port

- Ported the Forge 1.20.1 code base to Minecraft 26.2 / NeoForge 26.2.0.93:
  lobby dimension + dimension type + biome data, world preset extraction,
  payload registration, commands, events and the lobby overlay.
- Dependency switched from the old `bingo` id to the ReBongo port
  (`modId bongo`, package `io.github.noeppi_noeppi.mods.bongo`).
- Added an explicit `libx [26.2-8.0.0,)` dependency.
- Version renamed to `26.2-1.0.0-port.1` and metadata updated with the
  unofficial name, own issue tracker and non-affiliation note.

### Fixes

- Default task/settings ids changed to `bongo:hard` / `bongo:default` so the
  lobby can create games from the ReBongo data pack.
- Start-command error messages now use the `bongo.cmd.start.*` keys provided
  by ReBongo.
- `LobbyCommands` no longer uses a raw `Collection` when iterating
  `EntityArgument.getPlayers`, fixing a compile error on 26.2.

### Compatibility

- Existing `config/bingolobby-common.toml` values `bingo:hard` / `bingo:default`
  must be changed to `bongo:hard` / `bongo:default` once.
- The bundled `bingolobby-preset.zip` is copied to `config/` on first start.
