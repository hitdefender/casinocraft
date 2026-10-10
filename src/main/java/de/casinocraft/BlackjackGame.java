package de.casinocraft;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

final class BlackjackGame {
    private final List<Integer> player = new ArrayList<>();
    private final List<Integer> dealer = new ArrayList<>();
    private boolean over = false;
    private String result = "Karte ziehen oder stehen bleiben.";
    void reset() {
        player.clear(); dealer.clear(); over=false;
        player.add(draw()); player.add(draw());
        dealer.add(draw()); dealer.add(draw());
        result = playerScore()==21 ? "Blackjack! Dealer ist dran." : "Karte ziehen oder stehen bleiben.";
    }
    boolean hit() {
        if(over) return false;
        player.add(draw());
        if(playerScore()>21){over=true; result="Überkauft! Du verlierst diese Runde.";}
        return true;
    }
    boolean doubleDown() {
        if(over || player.size()!=2) return false;
        player.add(draw());
        if(playerScore()>21){over=true;result="Überkauft nach Verdoppeln.";}
        else dealerTurn();
        return true;
    }
    boolean split() {
        if(over || player.size()!=2 || value(player.get(0))!=value(player.get(1))) return false;
        // Lightweight split: represent the second hand as an additional card slot.
        int second=player.remove(1);
        player.add(draw());
        result="Split aktiviert (vereinfachte Ein-Hand-Darstellung); spiele diese Hand weiter.";
        return true;
    }
    void stand() { if(!over) dealerTurn(); }
    private void dealerTurn() {
        while(score(dealer)<17) dealer.add(draw());
        over=true;
        int p=playerScore(), d=score(dealer);
        if(p>21) result="Du bist überkauft. Dealer gewinnt.";
        else if(d>21 || p>d) result="Du gewinnst gegen den Dealer!";
        else if(p==d) result="Unentschieden.";
        else result="Dealer gewinnt.";
    }
    String message(){ return result; }
    String playerText(){ return cards(player); }
    String dealerText(){ return over ? cards(dealer)+" ("+score(dealer)+")" : card(dealer.get(0))+" + [verdeckt]"; }
    int playerScore(){ return score(player); }
    int coins(int current){ return current; }
    private int draw(){return ThreadLocalRandom.current().nextInt(1,14);}
    private int value(int c){return Math.min(c,10);}
    private int score(List<Integer> cards){
        int total=0, aces=0;
        for(int c:cards){total+=value(c); if(c==1) aces++;}
        while(aces>0 && total+10<=21){total+=10;aces--;}
        return total;
    }
    private String cards(List<Integer> cards){StringBuilder s=new StringBuilder();for(int i=0;i<cards.size();i++){if(i>0)s.append("  ");s.append(card(cards.get(i)));}return s.toString();}
    private String card(int c){return (c==1?"A":c==11?"J":c==12?"Q":c==13?"K":Integer.toString(c));}
}
