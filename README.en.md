# BingoLobby — Unofficial NeoForge 26.2 Port of BingoLobby

> **Upstream projects**
> - BingoLobby — <https://github.com/noeppi-noeppi/BingoLobby>
> - Bongo — <https://github.com/noeppi-noeppi/Bongo>
> - LibX — <https://github.com/ModdingX/LibX>
>
> **Unofficial project**: this is a community-maintained port, not an official release. It is not affiliated with, endorsed by, or sponsored by the original author **noeppi_noeppi**. The original project is **BingoLobby**.
>
> **License**: Apache License 2.0. The complete source is this repository.

## What is this?

A lobby dimension add-on for [ReBongo](https://github.com/Big-Jls/rebongo): players no longer respawn in the overworld, but at a fixed spot in the lobby dimension (X=0, Y=65, Z=0). Team areas are marked with colored carpets — stand on a carpet to pick that team. Only operators can interact inside the lobby dimension.

## Requirements

| Dependency | Version |
|---|---|
| Minecraft | 26.2 |
| NeoForge | 26.2.0.93 or newer |
| Java | 25 |
| ReBongo (`modId = bongo`) | 26.2-1.0.0-port.1 or newer |
| LibX | 26.2-8.0.0 (official release) |

Use the official LibX release (CurseForge file ID 9409485). This repository does not bundle or redistribute LibX.

## Installation

1. Install NeoForge 26.2.0.93+, the official LibX 26.2-8.0.0 and ReBongo.
2. Put `BingoLobby-26.2-1.0.0-port.2.jar` into the `mods` folder of **both** client and server.
3. After joining a server you respawn in the lobby dimension. On first start `bingolobby-preset.zip` is copied into `config/`.

## Commands

| Command | Description |
|---|---|
| `/bingo vip` / `/bingo unvip` | Grant / revoke VIP status for players |
| `/bingo vipteam` / `/bingo unvipteam` | Mark / unmark a team as VIP-only (yellow team by default) |
| `/bingo maxplayers <n>` | Limit the number of players per team; `-1` removes the limit |

`join` / `leave` are disabled in lobby mode; all other Bingo commands keep working as usual.

## Configuration

`config/bingolobby-common.toml`:

- `auto_create`: automatically create a game when none is active
- `auto_start_seconds`: countdown before the automatic start
- `tasks`: task set used for automatic creation, defaults to `bongo:hard`
- `settings`: game settings used for automatic creation, defaults to `bongo:default`

Once a winner is decided the lobby no longer runs `bingo stop`. The match stays in its won state until an operator ends it manually (the `win_return_seconds` option was removed).

**Upgrading from older versions**: if your config still uses `bingo:hard` / `bingo:default`, change them to `bongo:hard` / `bongo:default`, otherwise you will get "game definition could not be found".

## Building

Requires JDK 25, a NeoForge 26.2.0.93 installation, the official LibX jar and a built ReBongo jar:

```powershell
.\build.ps1 -Version 26.2-1.0.0-port.2 `
  -MinecraftDir "D:\minecraft\.minecraft" `
  -NeoForgeVersion "26.2-NeoForge_26.2.0.93" `
  -LibXJar "D:\minecraft\.minecraft\versions\26.2 bingo\mods\LibX-26.2-8.0.0.jar" `
  -BongoJar ".\..\rebongo\dist\ReBongo-26.2-1.0.0-port.13.jar"
```

The script compiles `src/` and packages `res/` plus `LICENSE` / `NOTICE` into `dist/BingoLobby-<version>.jar`.

## License and credits

- Original work **BingoLobby**: author **noeppi_noeppi**, Apache License 2.0
- Dependency **ReBongo** (originally **Bongo**, noeppi_noeppi), Apache License 2.0
- Dependency **LibX**: **ModdingX (MelanX, noeppi_noeppi)**, Apache License 2.0
- Dependency **NeoForge**: NeoForged, LGPL-2.1 (not bundled)
- Minecraft: Mojang, all rights reserved (not bundled)

Maintained by **Big_Jls**; porting assistance by **DeepSeek-v4.1-Flash-1017**.

This project is an unofficial port. It does not use the upstream icon or author avatar and does not imply any official relationship (Apache License 2.0, section 6, does not grant trademark rights).
