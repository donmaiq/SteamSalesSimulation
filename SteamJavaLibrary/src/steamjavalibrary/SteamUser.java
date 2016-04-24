package steamjavalibrary;
import java.util.ArrayList;
/**
 * A class for an Individual steam user.
 * @author Jonnie
 */
public class SteamUser implements Comparable<SteamUser>{
    private long steamid;
    private String personaname;
    private String avatar;
    private final UserBehaviour behaviour = new UserBehaviour();
    private final ArrayList<SteamGame> ownedgames = new ArrayList();
    private final ArrayList<PurchaseHist> history = new ArrayList();
    
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
        ownedgames.add(newgame);
        PurchaseHist temphist = new PurchaseHist(newgame,this);
        history.add(temphist);
        newgame.addHistory(temphist);
        SteamJavaLibrary.data.addHistory(temphist);
        newgame.gameSold();
        return true;
    }
    
    public ArrayList<SteamGame> getOwnedgames() {
        return ownedgames;
    }
    
    public ArrayList<PurchaseHist> getHistory(){
        return history;
    }
    
    public long getSteamid() {
        return steamid;
    }
    
    public String getPersonaname() {
        return personaname;
    }
    
    public String getAvatar() {
        return avatar;
    }
    
    public UserBehaviour getBehaviour() {
        return behaviour;
    }
    
}
