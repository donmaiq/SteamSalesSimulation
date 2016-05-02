package steamjavalibrary;
import java.util.ArrayList;
/**
 * A class for an Individual steam user.
 * 
 * @author Jonnie
 */
public class SteamUser implements Comparable<SteamUser>{
    private long steamid;
    private String personaname;
    private String avatar;
    private final UserBehaviour behaviour = new UserBehaviour();
    private final ArrayList<SteamGame> ownedgames = new ArrayList();
    private final ArrayList<PurchaseHist> history = new ArrayList();
    private double moneyspent;
    
    /**
     * Empty constructor for Gson to create user from json.
     */
    public SteamUser() {
    } 
    
    /**
     * Defines the custom comparison to compare steamuser by variationscale.
     * @param steamuser
     * @return 
     */
    @Override
    public int compareTo(SteamUser steamuser){
        int comparevariation=((SteamUser)steamuser).getBehaviour().getVariationscale();
        return comparevariation-this.getBehaviour().getVariationscale();
    }
    
    /**
     * Method for purchasing a newgame. If the newgame is owned, return false,
     * else will return true after creating a PurchaseHist object.
     * @param newgame
     * @return 
     */
    public boolean buyGame(SteamGame newgame) {
        if(ownedgames.contains(newgame)) return false;
        moneyspent += newgame.getTrueprice();
        ownedgames.add(newgame);
        PurchaseHist temphist = new PurchaseHist(newgame,this);
        history.add(temphist);
        newgame.addHistory(temphist);
        SteamJavaLibrary.data.addHistory(temphist);
        newgame.gameSold();
        return true;
    }
    /**
     * Getter for a user's owned games.
     * @return 
     */
    public ArrayList<SteamGame> getOwnedgames() {
        return ownedgames;
    }
    /**
     * Getter for a user's purchase history.
     * @return 
     */
    public ArrayList<PurchaseHist> getHistory(){
        return history;
    }
    /**
     * Getter for a user's steamid.
     * @return 
     */
    public long getSteamid() {
        return steamid;
    }

    public double getMoneyspent() {
        return moneyspent;
    }
    
    
    /**
     * Getter for a user's name.
     * @return 
     */
    public String getPersonaname() {
        return personaname;
    }
    /**
     * Getter for a user's avatar.
     * @return 
     */
    public String getAvatar() {
        return avatar;
    }
    /**
     * Getter for a user's behaviour.
     * @return 
     */
    public UserBehaviour getBehaviour() {
        return behaviour;
    }
    
}
