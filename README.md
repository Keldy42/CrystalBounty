# Crystal Bounty

Client-side Fabric mod for Minecraft 1.21.1.

## Build

Requires Java 21. Run `gradle build` (or use the GitHub Actions workflow). The JAR is produced in `build/libs/`.

## Controls
- **B** — open bounty board
- **V** — dash (3 second cooldown)

The bounty target is selected from the client player list, excluding yourself. Progress and points are client-local; the server does not verify or grant rewards. Kill detection reads ordinary game chat and is necessarily heuristic on a client-only mod.

## Important
The dash changes local player velocity only. A multiplayer server may correct or reject movement; this mod does not bypass server movement checks.
