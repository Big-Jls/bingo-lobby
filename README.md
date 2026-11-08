# BingoLobby — Bongo 的非官方 NeoForge 26.2 大厅移植

> **原项目（上游）**
> - BingoLobby — <https://github.com/noeppi-noeppi/BingoLobby>
> - Bongo — <https://github.com/noeppi-noeppi/Bongo>
> - LibX — <https://github.com/ModdingX/LibX>
>
> **非官方项目**：这是社区维护的移植版，不是官方发布，与原作者 **noeppi_noeppi** 没有隶属关系，也未获其背书或赞助。原项目为 **BingoLobby**。
>
> **许可**：Apache License 2.0，源码即本仓库。

## 简介

为 [ReBongo](https://github.com/Big-Jls/rebongo) 增加一个「大厅维度」：玩家不再在主世界重生，而是在大厅维度固定坐标（X=0、Y=65、Z=0）重生。用不同颜色地毯划分队伍区域，站上地毯即可选队。只有 OP 能在大厅维度中互动。

## 环境要求

| 依赖 | 版本 |
|---|---|
| Minecraft | 26.2 |
| NeoForge | 26.2.0.93 或更高 |
| Java | 25 |
| ReBongo（`modId = bongo`） | 26.2-1.0.0-port.1 或更高 |
| LibX | 26.2-8.0.0（官方版） |

## 安装

1. 安装 NeoForge 26.2.0.93+、官方 LibX 26.2-8.0.0、ReBongo。
2. 把 `BingoLobby-26.2-1.0.0-port.1.jar` 放入客户端与服务端的 `mods`。
3. 进入服务器后会在大厅维度重生；首次启动会把 `bingolobby-preset.zip` 复制到 `config/`。

## 命令

| 命令 | 说明 |
|---|---|
| `/bingo vip` / `/bingo unvip` | 设置/取消玩家 VIP |
| `/bingo vipteam` / `/bingo unvipteam` | 设置/取消 VIP 队伍（默认黄色队伍） |
| `/bingo maxplayers <n>` | 限制每队最大人数，`-1` 取消限制 |

`join` / `leave` 在大厅模式下被禁用，其余 Bingo 命令照常可用。

## 配置

`config/bingolobby-common.toml`：

- `auto_create`：没有对局时自动创建
- `auto_start_seconds`：自动开始倒计时
- `win_return_seconds`：胜利后回大厅等待时间
- `tasks`：自动创建用的任务集，默认 `bongo:hard`
- `settings`：自动创建用的设置，默认 `bongo:default`

**从旧版本升级**：如果你之前用的是 `bingo:hard` / `bingo:default`，请改成 `bongo:hard` / `bongo:default`，否则会提示“game definition could not be found”。

## 构建

需要 JDK 25、NeoForge 26.2.0.93 安装、官方 LibX 与已构建的 ReBongo jar：

```powershell
.\build.ps1 -Version 26.2-1.0.0-port.1 `
  -MinecraftDir "D:\minecraft\.minecraft" `
  -NeoForgeVersion "26.2-NeoForge_26.2.0.93" `
  -LibXJar "D:\minecraft\.minecraft\mods\LibX-26.2-8.0.0.jar" `
  -BongoJar ".\..\rebongo\dist\Bingo-26.2-1.0.0-port.1.jar"
```

## 许可与致谢

- 原作品 **BingoLobby**：**noeppi_noeppi**，Apache License 2.0
- 依赖 **ReBongo**（原 **Bongo**，noeppi_noeppi），Apache License 2.0
- 依赖 **LibX**：**ModdingX（MelanX、noeppi_noeppi）**，Apache License 2.0

维护：**Big_Jls**；移植协助：**DeepSeek-v4.1-Flash-1017**。

本项目为非官方移植版，未使用上游图标，也不暗示任何官方关系（Apache License 2.0 第 6 条不授予商标权）。
