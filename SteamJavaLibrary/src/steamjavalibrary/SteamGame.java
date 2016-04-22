/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steamjavalibrary;
import java.util.ArrayList;
import java.lang.Math;
import java.util.Random;
/**
 *
 * @author Jonnie
 */
public class SteamGame implements Comparable<SteamGame>{
    private int appid;
    private String name;
    private double price;
    private final String type; //Type of release. AAA / AA / A
    private final String genre;
    private final int review;    //0-100, review score and word of mouth
    private final int marketing; //0-100, marketing amount, bigger audience
    private int soldunits;
    private double revenue; //append on sales
    private double steamcut; //steam cut from revenue, append on sales
    private GameBehaviour behaviour;
    private final ArrayList<PurchaseHist> history = new ArrayList();

    public void gameSold(){
        //calulate the 80% that will go to revenue and round it, leftover 20% to steamcut
        incrementSoldunits();
        double roundedrevenue = round2decimal(getPrice()*0.7);
        incrementRevenue(roundedrevenue);
        incrementSteamcut(getPrice()-roundedrevenue);
        SteamJavaLibrary.data.incrementSold();
    }
    public void addHistory(PurchaseHist history){
        this.history.add(history);
    }
    public SteamGame() {
        Random r = new Random();
        behaviour = new GameBehaviour();
        revenue = 0.0;
        steamcut = 0.0;
        soldunits = 0;
        double randomseed = r.nextInt(100);
        if(randomseed<15){
            type="AAA";
            price=40 + round2decimal(r.nextDouble()*20);
            marketing = r.nextInt(20)+80;
        }else if(randomseed<80){
            type="Normal";
            price=10 + round2decimal(r.nextDouble()*20);
            marketing = r.nextInt(80)+20;
        }else{
            type="Indie";
            price=3 + round2decimal(r.nextDouble()*10);
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
        genre = SteamJavaLibrary.data.genreslist[r.nextInt(SteamJavaLibrary.data.genreslist.length)];
    }
    
    @Override
    public int compareTo(SteamGame steamgame){
        int comparevariation=((SteamGame)steamgame).getReview();
        return comparevariation-this.getReview();
    }
    
    public void incrementSteamcut(double steamcut){
        this.steamcut += steamcut;
    }
    public void incrementRevenue(double revenue){
        this.revenue += revenue;
    }
    public void incrementSoldunits(){
        soldunits = soldunits + 1;
    }
    public void reducePrice() {
        double percent = 1-behaviour.getPricedropamount()/100;
        price=round2decimal(price*percent);
    }
    
    //ROUNDS DOUBLE TO 2 DECIMAL POINTS, 12.3059 -> 12.31
    //USE AFTER MULTIPLYING A DOUBLE
    public double round2decimal(double arg){
        arg=arg*100;
        return Math.round(arg)/100;       
    }
    
    //PUBLIC GETTERS
    public String getName() {
        return name;
    }
    public int getAppID() {
        return appid;
    }
    public double getPrice() {
        return price;
    }
    public String getType() {
        return type;
    }
    public String getGenre() {
        return genre;
    }
    public int getReview() {
        return review;
    }
    public int getMarketing() {
        return marketing;
    }
    public double getRevenue() {
        return revenue;
    }
    public double getSteamcut() {
        return steamcut;
    }
    public GameBehaviour getBehaviour() {
        return behaviour;
    }
    
    
    
}
