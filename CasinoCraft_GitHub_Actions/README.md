# CasinoCraft — Fabric 1.21.11

Client-side Minecraft casino minigame overlay using **virtual coins only**.

## Features
- Press **K** to open the menu; press K again to close.
- Slot machine with virtual coin bets and simple payouts.
- Blackjack dealer bot; Hit, Stand, Double Down and a basic Split action.
- Java 21 / Fabric / Minecraft 1.21.11.

## Build on Windows
1. Install a Java 21 JDK.
2. Open this folder in a terminal.
3. Run `gradle build` (or add a Gradle wrapper in your IDE).
4. The mod JAR should appear in `build\libs`.
5. Put the JAR into `.minecraft\mods` alongside Fabric API for 1.21.11.

## Notes
This is an initial source project. The build has not been verified in this environment. Blackjack split is a simplified prototype, and this version keeps the coin balance in memory (it resets when the game is restarted). The GUI uses vanilla widgets and text for compatibility.
