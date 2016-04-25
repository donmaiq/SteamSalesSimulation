/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steamjavalibrary;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Jonnie
 */
public class UsersArray {
    private ArrayList<SteamUser> users = new ArrayList();
    
    public UsersArray(){
    }
    /**
     * Getter for the Array of users.
     * @return 
     */
    public ArrayList<SteamUser> getUsers() {
        return users;
    }
    /**
     * Adds a game to the user's owned games.
     * @param user 
     */
    public void addApps(SteamUser user) {
        users.add(user);
    }
    /**
     * Sorts users by their variationscale.
     */
    public void sortUsers(){
        Collections.sort(users);
    }
}
