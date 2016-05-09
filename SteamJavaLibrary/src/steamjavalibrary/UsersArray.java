package steamjavalibrary;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Container for the users. Required because of the json structure.
 * @author Jonnie
 */
public class UsersArray {
    private ArrayList<SteamUser> users = new ArrayList();
    
    /**
     * Empty constructor for json to inject.
     */
    public UsersArray(){
    }
    /**
     * Getter for the Array of users.
     * @return users ArrayList of SteamUsers
     */
    public ArrayList<SteamUser> getUsers() {
        return users;
    }
    /**
     * Adds a game to the user's owned games.
     * @param user SteamUser
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
