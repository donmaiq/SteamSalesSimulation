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
    private final int timestamp;
    
    /**
     * Constructor for the PurchaseHist class. User and Game are passed as
     * parameters, price is taken from the game and timestamp from the simulation.
     * @param game
     * @param user 
     */
    public PurchaseHist(SteamGame game, SteamUser user){
        this.game = game;
        this.user = user;
        price = game.getPrice();
        timestamp = FXcontroller.getDay();
    }
    /**
    * Getter for a game.
    * @return 
    */
    public SteamGame getGame() {
        return game;
    }
    /**
    * Getter for a user.
    * @return 
    */
    public SteamUser getUser() {
        return user;
    }
    /**
     * Getter for game's price.
     * @return 
     */
    public double getPrice() {
        return price;
    }
    /**
     * Getter for timestamp.
     * @return 
     */
    public int getTimestamp() {
        return timestamp;
    }
    
}
