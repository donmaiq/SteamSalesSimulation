package steamjavalibrary;

import java.util.ArrayList;
import java.util.Random;
/**
 * A class for an individual steam game.
 * 
 * @author Jonnie
 */
public class SteamGame implements Comparable<SteamGame>{
    private int appid;
    private String name;
    private double price;
    private double saleprice;
    private int saleamount;
    private boolean sale = false;
    private final String type; //Type of release. AAA / normal / indie
    private final String genre;
    private final int review;    //0-100, review score and word of mouth
    private final int marketing; //0-100, marketing amount, bigger audience
    private int soldunits;
    private double revenue; //append on sales
    private double steamcut; //steam cut from revenue, append on sales
    private final GameBehaviour behaviour;
    private final ArrayList<PurchaseHist> history = new ArrayList();
    
    /**
     * The constructor for a steamgame.
     * Creates random values for the game. Appid and name are created by the
     * grandparent data zip constructor.
     */
    public SteamGame() {
        Random r = new Random();
        behaviour = new GameBehaviour();
        revenue = 0.0;
        steamcut = 0.0;
        soldunits = 0;
        int randomseed = r.nextInt(101);
        if(randomseed<15){
            type="AAA";
            price=40 + r.nextInt(21);
            marketing = r.nextInt(21)+80;
        }else if(randomseed<80){
            type="Normal";
            price=10 + r.nextInt(21);
            marketing = r.nextInt(81)+20;
        }else{
            type="Indie";
            price=3 + r.nextInt(21);
            marketing = r.nextInt(20);
        }
        //75% chance for a normal distribution 50-100
        //25% to be random int 0-100
        //this creates a linear chance until 50% where it rises until 75% and back to linear one in 100%
        if(r.nextInt(100)>25){
            int x = (int) Math.round((r.nextGaussian()/2*25)+75);
            while(x>100){
                x = (int) Math.round((r.nextGaussian()/2*25)+75);
            }
            review = x;
        }else{
            review = r.nextInt(100);
        }
        int rand = r.nextInt(100);
        int index;
        if(rand<10){index=0;
        }else if(rand<14){index=1;
        }else if(rand<35){index=2;
        }else if(rand<50){index=3;
        }else if(rand<66){index=4;
        }else if(rand<88){index=5;
        }else{index=6;}
        //todo: importing genrelist from data not working?
        String[] genrelist = {"rpg","mmo","fps","casual","adventure","arcade","rts"};
        genre = genrelist[index];
        saleprice = price;
    }
    
    /**
     * Defines the custom comparison to compare SteamGame by reviews.
     * @param steamgame
     * @return 
     */
    @Override
    public int compareTo(SteamGame steamgame){
        int comparevariation=((SteamGame)steamgame).getReview();
        return comparevariation-this.getReview();
    }
    
    /**
     * Method called when a game is sold. Counts the different revenues.
     */
    public void gameSold(){
        double sellingprice = (isSale() ? getSaleprice() : getPrice());
        double roundedrevenue = round2decimal(sellingprice*0.8);
        double steamrevenue = sellingprice-roundedrevenue;
        incrementRevenue(roundedrevenue);
        incrementSteamcut(steamrevenue);
        SteamJavaLibrary.data.addSteamrevenue(steamrevenue);
        SteamJavaLibrary.data.incrementSold();
        incrementSoldunits();
    }
    
    /**
     * Adds a PurchaseHist object to the games purchase history.
     * @param history 
     */
    public void addHistory(PurchaseHist history){
        this.history.add(history);
    }
    
    /**
     * Sets the steamgame on sale. Will calculate a sale price for the game.
     */
    public void setGameonsale(){
        sale = true;
        Random r = new Random();
        saleamount = behaviour.getSalescale()[r.nextInt(3)];
        saleprice = round2decimal(price * ((100.0-saleamount)/100));
    }
     /**
     * Sets the game on sale for steamsales. Will calculate a sale price for the game.
     */
    public void setGameonsteamsale(int index){
        sale = true;
        saleamount = behaviour.getSalescale()[index];
        saleprice = round2decimal(price * ((100.0-saleamount)/100));
    }
    
    /**
     * Stops the sale of a steamgame.
     */
    public void removeSale(){
        sale=false;
    }
    
    /**
     * Gets the % how much the game is in sale.
     * @return 
     */
    public int getSaleamount() {
        return saleamount;
    }
    
    /**
     * Returns a boolean declaring state of sale.
     * @return 
     */
    public boolean isSale(){
        return sale;
    }
    
    /**
     * Gets the price the game is on sale for.
     * @return 
     */
    public double getSaleprice(){
        return saleprice;
    }
    
    /**
     * Method to return the current price of the game.
     * @return price of the game
     */
    public double getTrueprice(){
        return (isSale() ? getSaleprice() : getPrice());
    }
    
    /**
     * Increments the double value to steamcut.
     * @param value 
     */
    public void incrementSteamcut(double value){
        steamcut += value;
    }
    
    /**
     * Increments revenue.
     * @param revenue 
     */
    public void incrementRevenue(double revenue){
        this.revenue += revenue;
    }
    
    /**
     * Increments the amount of soldunits by 1.
     */
    public void incrementSoldunits(){
        soldunits = soldunits + 1;
    }
    
    /**
     * Reduces the price of the game.
     */
    public void reducePrice() {
        double percent = 1-behaviour.getPricedropamount()/100.0;
        price=round2decimal(price*percent);
    }
    
    /**
     * Rounds a double to two decimal points.
     * @param arg
     * @return 
     */
    public double round2decimal(double arg){
        arg=arg*100;
        return Math.round(arg)/100;       
    }
    /**
     * Getter for app's id.
     * @return 
     */
    public int getAppid() {
        return appid;
    }
    /**
     * Getter for amount of sold games.
     * @return 
     */
    public int getSoldunits() {
        return soldunits;
    }
    /**
     * Getter for the Array of purchases.
     * @return 
     */
    public ArrayList<PurchaseHist> getHistory() {
        return history;
    }
    /**
     * Getter for game's name.
     * @return 
     */
    public String getName() {
        return name;
    }
    /**
     * Getter for game's price.
     * @return 
     */
    public double getPrice() {
        return price;
    }
    /**
     * Getter for game's type.
     * @return 
     */
    public String getType() {
        return type;
    }
    /**
     * Getter for game's genre.
     * @return 
     */
    public String getGenre() {
        return genre;
    }
    /**
     * Getter for game's review.
     * @return 
     */
    public int getReview() {
        return review;
    }
    /**
     * Getter for game's marketing.
     * @return 
     */
    public int getMarketing() {
        return marketing;
    }
    /**
     * Getter for total revenue.
     * @return 
     */
    public double getRevenue() {
        return revenue;
    }
    /**
     * Getter for steam's cut of total revenue.
     * @return 
     */
    public double getSteamcut() {
        return steamcut;
    }
    /**
     * Getter for the GameBehaviour object.
     * @return 
     */
    public GameBehaviour getBehaviour() {
        return behaviour;
    }
}
