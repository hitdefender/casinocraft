# CasinoCraft — Fabric 1.21.11

Client-side Minecraft casino minigame overlay using **virtual coins only**.

## Features in this prototype
- Press **K** to open the menu; press K again to close.
- Slot machine with virtual coin bets and basic payouts.
- Blackjack dealer bot; Hit, Stand, Double Down and a prototype Split action.
- Java 21 / Fabric / Minecraft 1.21.11.

## Build on Windows
1. Install a Java 21 JDK.
2. Install Gradle, or open the project in IntelliJ IDEA with Gradle support.
3. Double-click `build.bat` or run `gradle build` in this folder.
4. The JAR should appear in `build\\libs`.
5. Put the JAR into `.minecraft\\mods` and install Fabric API for Minecraft 1.21.11.

## Prototype limitations
- The project has **not been compiled or tested** in this environment because Gradle is not installed and external downloads are unavailable here.
- Blackjack Split is only a simplified placeholder and does not yet manage two independent hands correctly.
- Coin balance is in memory and resets when Minecraft restarts.
- GUI uses vanilla widgets and text; visual polish can be improved after the first successful build.
