package steamjavalibrary;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.InputStream;
import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;
import java.util.Enumeration;
import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Random;

/**
 * A class containing all data concerning the simulation.
 * User and Game data is loaded from zip files containing 
 * json files with 15000 games and 200000 users.
 * Object children tree:
 *      GamesArray
 *          SteamGame
 *              GameBehaviour
 *      UsersArray
 *          SteamUser
 *              UserBehaviour
 * 
 * @author Jonnie
 */
public class Data {
    private boolean steamsale;
    private GamesArray allgames = new GamesArray();
    private UsersArray allusers = new UsersArray();
    private String[] genreslist = {"rpg","mmo","fps","casual","adventure","arcade","rts"};
    private int gamessold = 0;
    private double steamrevenue = 0;
    private ArrayList<PurchaseHist> allhistory = new ArrayList();
    private SteamGame weeklydiscount;
    
    /**
     * Constructor for the Data class.
     * Loads content from zip files and creates allgames and allusers classes from them.
     * After creation they are sorted and an additional construtor for allgames is ran.
     */
    public Data(){
        steamsale = false;
        gamessold = 0; 
        Gson gson = new GsonBuilder().create();
        try(ZipFile zipFile = new ZipFile(new File("src/resources/allgames.zip")) ){
            System.out.println("\nLoading Users...");
            Enumeration<? extends ZipEntry> entries = zipFile.entries();         
            ZipEntry entry = entries.nextElement();
            InputStream stream = zipFile.getInputStream(entry);
            Reader read = new InputStreamReader(stream, "UTF-8");
            allgames = gson.fromJson(read, GamesArray.class);
            read.close();
            System.out.println(allgames.getApps().size()+" games loaded.");
        } catch(Exception e){
            System.out.println("error "+e);
        }
        
        try(ZipFile zipFile = new ZipFile(new File("src/resources/allusers.zip")) ){
            System.out.println("\nLoading Users...");
            Enumeration<? extends ZipEntry> entries = zipFile.entries();         
            ZipEntry entry = entries.nextElement();
            InputStream stream = zipFile.getInputStream(entry);
            Reader read = new InputStreamReader(stream, "UTF-8");
            allusers = gson.fromJson(read, UsersArray.class);
            read.close();
            stream.close();
            System.out.println(allusers.getUsers().size()+" users loaded.\n");
        } catch(Exception e){
            System.out.println("error "+e);
        }
        allgames.sortGames();
        allusers.sortUsers();
        allgames.setupLists();
        Random r = new Random();
        weeklydiscount = allgames.getApps().get(r.nextInt(allgames.getApps().size()));
        weeklydiscount.setGameonsale();
    }
    
    public void shuffleWeeklydiscount(){
        Random r = new Random();
        weeklydiscount.removeSale();
        weeklydiscount = allgames.getApps().get(r.nextInt(allgames.getApps().size()));
        weeklydiscount.setGameonsale();
    }
    
    public SteamGame getWeeklydiscount(){
        return weeklydiscount;
    }
    
    /**
     * Adds a PurchaseHist object to the allhistory ArrayList.
     * @param purchase 
     */
    public void addHistory(PurchaseHist purchase){
        allhistory.add(purchase);
    }
    
    /**
     * Adds the specified amount to steamrevenue. 
     * @param amount 
     */
    public void addSteamrevenue(double amount){
        steamrevenue += amount;
    }
    
    /**
     * Getter for the GamesArray object.
     * @return 
     */
    public GamesArray getAllgames() {
        return allgames;
    }
    
    /**
     * Getter for total steam revenue.
     * @return 
     */
    public double getSteamrevenue() {
        return steamrevenue;
    }
    
    /**
     * Getter for the Array of all purchases.
     * @return 
     */
    public ArrayList<PurchaseHist> getAllhistory() {
        return allhistory;
    }
    
    /**
     * Getter for the UsersArray object.
     * @return 
     */
    public UsersArray getAllusers() {
        return allusers;
    }
    
    /**
     * Getter for the genrelist String array.
     * @return 
     */
    public String[] getGenreslist() {
        return genreslist;
    }
    
    /**
     * Getter for total amount of games sold.
     * @return 
     */
    public int getGamessold() {
        return gamessold;
    }
    
    /**
     * Increments games sold by 1.
     */
    public void incrementSold(){
        gamessold+=1;
    }
    
    /**
     * Returns boolean of state of sale events.
     * @return 
     */
    public boolean isSteamsale() {
        return steamsale;
    }
    
    /**
     * Sets the boolean value for steamsale.
     * @param sale 
     */
    public void setSale(boolean sale) {
        steamsale = sale;
    }
}
