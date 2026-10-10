package de.casinocraft;
import java.util.concurrent.ThreadLocalRandom;
final class SlotGame {
    int next(){ return ThreadLocalRandom.current().nextInt(5); }
}
