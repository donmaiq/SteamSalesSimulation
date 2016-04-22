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
    
    public ArrayList<SteamUser> getUsers() {
        return users;
    }
    public void addApps(SteamUser user) {
        users.add(user);
    }
    public void sortUsers(){
        Collections.sort(users);
    }
}
