/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steamjavalibrary;

/**
 * A class that works as a receipt. Contains info of a game purchase and is 
 * stored in three locations.
 * @author joona
 */
public class PurchaseHist {
    private final SteamGame game;
    private final SteamUser user;
    private final double price;
    private final int[] timestamp = new int[2];
    
    /**
     * Constructor for the PurchaseHist class. User and Game are passed as
     * parameters, price is taken from the game and timestamp from the simulation.
     * @param game SteamGame that was sold
     * @param user SteamUser that bought the game
     */
    public PurchaseHist(SteamGame game, SteamUser user){
        this.game = game;
        this.user = user;
        price = game.getTrueprice();
        timestamp[0] = FXcontroller.getYear();
        timestamp[1] = FXcontroller.getDay();
    }
    /**
    * Getter for a game.
    * @return SteamGame
    */
    public SteamGame getGame() {
        return game;
    }
    /**
    * Getter for a user.
    * @return SteamUser
    */
    public SteamUser getUser() {
        return user;
    }
    /**
     * Getter for games price.
     * @return double price
     */
    public double getPrice() {
        return price;
    }
    /**
     * Getter for timestamp.
     * @return int[2] {year,day}
     */
    public int[] getTimestamp() {
        return timestamp;
    }
    
}
