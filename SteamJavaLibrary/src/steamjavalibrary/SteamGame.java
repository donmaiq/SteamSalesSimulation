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
public class SteamGame {
    private int appid;
    private String name;
    private double price;
    private String type; //Type of release. AAA / AA / A
    private ArrayList<String> genres = new ArrayList();
    private int review;    //0-100, review score and word of mouth
    private int marketing; //0-100, marketing amount, bigger audience
    private double revenue; //append on sales
    private double steamcut; //steam cut from revenue, append on sales
    private GameBehaviour behaviour = new GameBehaviour();

    public void gameSold(){
        //calulate the 80% that will go to revenue and round it, leftover 20% to steamcut
        double roundedrevenue = round2decimal(price*0.8);
        revenue += roundedrevenue;
        steamcut += price-roundedrevenue;
    }
    
    public SteamGame() {
        Random r = new Random();
        revenue = 0.0;
        steamcut = 0.0;
        double randomseed = r.nextInt(100);
        if(randomseed<33){
            type="AAA";
            price=40 + round2decimal(r.nextDouble()*20);
        }else if(randomseed<66){
            type="AA";
            price=10 + round2decimal(r.nextDouble()*20);
        }else{
            type="A";
            price=3 + round2decimal(r.nextDouble()*10);
        }
        //75% chance for a normal distribution 50-100
        //25% to be random int 0-100
        //this creates a linear chance until 50% where it rises until 75% and back to linear one in 100%
        if(r.nextInt(100)>25){
            review = (int) (r.nextGaussian()*25+75);
        }else{
            review = r.nextInt(100);
        }
        marketing = r.nextInt(100);
        
        //CREATE 1-3 GENRES FOR GAME
        String[] list = SteamJavaLibrary.genreslist;
        ArrayList<Integer> usedindex = new ArrayList();
        for(int i=0;i<r.nextInt(2)+1;i++){
            int rnd = -1;
            do{
                rnd = r.nextInt(list.length);
            }while(usedindex.contains(rnd));
            
            String genretoadd = list[rnd];
            genres.add(genretoadd);
            usedindex.add(rnd);
        }
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
    public ArrayList<String> getGenres() {
        return genres;
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
