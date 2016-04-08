/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steamjavalibrary;
import java.util.ArrayList;
/**
 *
 * @author Jonnie
 */
public class SteamUser {
    private int userID;
    private String name;
    private UserBehaviour behaviour = new UserBehaviour();
    private ArrayList<SteamGame> ownedgames = new ArrayList();
    
    //Constructor
    public SteamUser(int userID, String name) {
        this.userID = userID;
        this.name = name;
        PopulateGames();
    }
    //Populate ownedgames with random games
    public void PopulateGames(){
        SteamGame test = new SteamGame();
        ownedgames.add(test);
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
    
    
}
