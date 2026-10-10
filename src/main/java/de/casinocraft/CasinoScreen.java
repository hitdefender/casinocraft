package de.casinocraft;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class CasinoScreen extends Screen {
    private enum Page { MENU, SLOTS, BLACKJACK }
    private Page page = Page.MENU;
    private int coins = 1000;
    private int bet = 50;
    private String message = "Willkommen! Du spielst mit virtuellem Spielgeld.";
    private final SlotGame slots = new SlotGame();
    private final BlackjackGame blackjack = new BlackjackGame();
    private int slotA = 7, slotB = 7, slotC = 7;

    public CasinoScreen() { super(Text.literal("CasinoCraft")); }
    @Override protected void init() { rebuild(); }
    private void rebuild() {
        clearChildren();
        int cx = width / 2, top = height / 2 - 85;
        if (page == Page.MENU) {
            addDrawableChild(ButtonWidget.builder(Text.literal("🎰 Slotmaschine"), b -> { page=Page.SLOTS; rebuild(); })
                .dimensions(cx-100, top, 200, 24).build());
            addDrawableChild(ButtonWidget.builder(Text.literal("🂡 Blackjack gegen Dealer"), b -> { page=Page.BLACKJACK; blackjack.reset(); message="Neue Runde gestartet."; rebuild(); })
                .dimensions(cx-100, top+32, 200, 24).build());
        } else if (page == Page.SLOTS) {
            addDrawableChild(ButtonWidget.builder(Text.literal("Drehen ("+bet+")"), b -> spin()).dimensions(cx-100, top+55, 200, 24).build());
            addDrawableChild(ButtonWidget.builder(Text.literal("Einsatz -"), b -> { bet=Math.max(10,bet-10); rebuild(); }).dimensions(cx-100, top+85, 95, 20).build());
            addDrawableChild(ButtonWidget.builder(Text.literal("Einsatz +"), b -> { bet=Math.min(500,bet+10); rebuild(); }).dimensions(cx+5, top+85, 95, 20).build());
            addDrawableChild(ButtonWidget.builder(Text.literal("Zurück"), b -> {page=Page.MENU; rebuild();}).dimensions(cx-50, top+120, 100, 20).build());
        } else {
            addDrawableChild(ButtonWidget.builder(Text.literal("Karte (Hit)"), b -> { blackjack.hit(); syncMessage(); rebuild(); })
                .dimensions(cx-155, top+115, 100, 22).build());
            addDrawableChild(ButtonWidget.builder(Text.literal("Rest (Stand)"), b -> { blackjack.stand(); syncMessage(); rebuild(); })
                .dimensions(cx-50, top+115, 100, 22).build());
            addDrawableChild(ButtonWidget.builder(Text.literal("Verdoppeln"), b -> { if(coins>=bet && blackjack.doubleDown()) {coins-=bet; bet*=2;} syncMessage(); rebuild(); })
                .dimensions(cx+55, top+115, 100, 22).build());
            addDrawableChild(ButtonWidget.builder(Text.literal("Teilen (Split)"), b -> { if(coins>=bet && blackjack.split()) {coins-=bet;} syncMessage(); rebuild(); })
                .dimensions(cx-50, top+143, 100, 20).build());
            addDrawableChild(ButtonWidget.builder(Text.literal("Neue Runde"), b -> { blackjack.reset(); message="Neue Runde gestartet."; rebuild(); })
                .dimensions(cx-155, top+143, 100, 20).build());
            addDrawableChild(ButtonWidget.builder(Text.literal("Zurück"), b -> {page=Page.MENU; rebuild();}).dimensions(cx+55, top+143, 100, 20).build());
        }
    }
    private void spin() {
        if (coins < bet) { message="Nicht genug Spielgeld."; rebuild(); return; }
        coins -= bet;
        String[] symbols={"🍒","🍋","🔔","💎","7"};
        slotA=slots.next(); slotB=slots.next(); slotC=slots.next();
        if (slotA==slotB && slotB==slotC) { int win=bet*(slotA==4?10:5); coins+=win; message="Dreier! Gewinn: "+win+" Coins."; }
        else if(slotA==slotB || slotB==slotC || slotA==slotC) { int win=bet*2; coins+=win; message="Zwei gleiche! Gewinn: "+win+" Coins."; }
        else message="Leider kein Gewinn. Viel Glück beim nächsten Dreh!";
        rebuild();
    }
    private void syncMessage() { message=blackjack.message(); coins=blackjack.coins(coins); }
    @Override public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        renderBackground(ctx, mouseX, mouseY, delta);
        int cx=width/2, left=cx-180, top=height/2-115;
        ctx.fill(left,top,left+360,top+235,0xF20D0B18);
        ctx.fill(left,top,left+360,top+2,0xFFB04CFF);
        ctx.drawCenteredTextWithShadow(textRenderer,"✦ CASINOCRAFT ✦",cx,top+12,0xFFE6B8FF);
        ctx.drawCenteredTextWithShadow(textRenderer,"Guthaben: "+coins+" Coins",cx,top+31,0xFFFFD76A);
        if(page==Page.MENU) {
            ctx.drawCenteredTextWithShadow(textRenderer,"Wähle dein Spiel",cx,top+58,0xFFFFFFFF);
            ctx.drawCenteredTextWithShadow(textRenderer,"Hotkey: K  •  Nur virtuelles Spielgeld",cx,top+145,0xFFBFB5D2);
        } else if(page==Page.SLOTS) {
            ctx.drawCenteredTextWithShadow(textRenderer,"SLOT-MASCHINE",cx,top+40,0xFFFFFFFF);
            String[] sym={"🍒","🍋","🔔","💎","7"};
            ctx.drawCenteredTextWithShadow(textRenderer,sym[slotA]+"     "+sym[slotB]+"     "+sym[slotC],cx,top+68,0xFFFFD76A);
            ctx.drawCenteredTextWithShadow(textRenderer,"Drei gleiche = 5x Einsatz (7 = 10x)",cx,top+92,0xFFDBD0E9);
        } else {
            ctx.drawCenteredTextWithShadow(textRenderer,"BLACKJACK  •  Dealer-Bot",cx,top+42,0xFFFFFFFF);
            ctx.drawCenteredTextWithShadow(textRenderer,"Dealer: "+blackjack.dealerText(),cx,top+61,0xFFFFD76A);
            ctx.drawCenteredTextWithShadow(textRenderer,"Deine Karten: "+blackjack.playerText(),cx,top+82,0xFFFFFFFF);
            ctx.drawCenteredTextWithShadow(textRenderer,"Punkte: "+blackjack.playerScore()+"    Einsatz: "+bet,cx,top+101,0xFFBFB5D2);
        }
        ctx.drawCenteredTextWithShadow(textRenderer,message,cx,top+192,0xFFE6B8FF);
        super.render(ctx,mouseX,mouseY,delta);
    }
    @Override public boolean shouldPause(){return false;}
}
