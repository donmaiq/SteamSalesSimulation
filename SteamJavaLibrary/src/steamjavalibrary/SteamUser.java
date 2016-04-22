/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steamjavalibrary;
import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author Jonnie
 */
public class SteamUser implements Comparable<SteamUser>{
    private long steamid;
    private String personaname;
    private String avatar;
    private UserBehaviour behaviour = new UserBehaviour();
    private ArrayList<SteamGame> ownedgames = new ArrayList();
    
    //Constructor
    public SteamUser() {
        PopulateGames();
    }    
    public SteamUser(long steamid, String personaname) {
        this.steamid = steamid;
        this.personaname = personaname;
        PopulateGames();
    }
    //Populate ownedgames with random games
    private void PopulateGames(){
        Random r = new Random();
        for(int i=0;i<r.nextInt(4)+1;i++){
            int index = r.nextInt(SteamJavaLibrary.data.allgames.getApps().size());
            ownedgames.add(SteamJavaLibrary.data.allgames.getApps().get(index));
        }
    }
    @Override
    public int compareTo(SteamUser steamuser){
        int comparevariation=((SteamUser)steamuser).getBehaviour().getVariationscale();
        return comparevariation-this.getBehaviour().getVariationscale();
    }
    
    public ArrayList<SteamGame> getOwnedgames() {
        return ownedgames;
    }
    
    //ADD GAME TO USERS LIBRARY, RETURNS TRUE WHEN ADDED, FALSE IF GAME WAS ALREADY IN
    //CALL THIS AFTER BUYING GAME
    public boolean addToOwnedgames(SteamGame newgame) {
        if(ownedgames.contains(newgame)) return false;
        ownedgames.add(newgame);
        return true;
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
