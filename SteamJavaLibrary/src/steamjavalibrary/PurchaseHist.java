/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steamjavalibrary;

/**
 *
 * @author joona
 */
public class PurchaseHist {
    private final SteamGame game;
    private final SteamUser user;
    private final double price;
    private final int timestamp;
    
    public PurchaseHist(SteamGame game, SteamUser user){
        this.game = game;
        this.user = user;
        price = game.getPrice();
        timestamp = SteamJavaLibrary.simCount;
    }
}
