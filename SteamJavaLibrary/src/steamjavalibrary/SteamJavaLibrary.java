/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steamjavalibrary;
import java.util.ArrayList;
/**
 *
 * @author joona
 */
public class SteamJavaLibrary {

    public static ArrayList<SteamGame> gameslist = new ArrayList();
    public static String[] genreslist = {"rpg","mmo","fps","casual","adventure","arcade","rts"};
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        //running some tests
        
        SteamGame game1 = new SteamGame(1,"Dota 2");
        SteamGame game2 = new SteamGame(2,"Call of Duty");
        SteamGame game3 = new SteamGame(3,"Skyrim");
        gameslist.add(game1);
        gameslist.add(game2);
        
        SteamUser user1 = new SteamUser(1,"Jonnie");
        SteamUser user2 = new SteamUser(2,"Joona");
        
        System.out.println(user1.getName()+" omistaa "+user1.getOwnedgames().get(0).getName()+" pelin");
        System.out.println("Sillä on arvostelu: "+gameslist.get(gameslist.indexOf(user1.getOwnedgames().get(0))).getReview());
        System.out.print("Sen genre on: ");
        for(int a=0;a<user1.getOwnedgames().get(0).getGenres().size();a++){
            System.out.print(" "+user1.getOwnedgames().get(0).getGenres().get(a)+" ");
        }System.out.print("\n");
        System.out.println("Hänen lempigenre on "+user1.getBehaviour().getFavGenre()+"\n");
        System.out.println(user2.getName()+" omistaa "+user2.getOwnedgames().get(0).getName()+" pelin");
        System.out.println("Sillä on arvostelu: "+gameslist.get(gameslist.indexOf(user2.getOwnedgames().get(0))).getReview());
        System.out.print("Sen genre on: ");
        for(int a=0;a<user1.getOwnedgames().get(0).getGenres().size();a++){
            System.out.print(" "+user2.getOwnedgames().get(0).getGenres().get(a)+" ");
        }System.out.print("\n");
        System.out.println("Hänen lempigenre on "+user2.getBehaviour().getFavGenre());
        
        
        
    }
    
}
