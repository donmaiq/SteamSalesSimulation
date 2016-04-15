/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steamjavalibrary;



//testing utils
import java.util.Scanner;
import java.util.Random;

/**
 *
 * @author joona
 */
public class SteamJavaLibrary {
    public static Data data = new Data();
    
    public static void main(String[] args){
        //TEST LOOP press enter to load new random user
        Random r = new Random();
        Scanner lukija = new Scanner(System.in);
        while(true){
            SteamUser user1 = data.allusers.getUsers().get(r.nextInt(data.allusers.getUsers().size()));
            System.out.println(user1.getPersonaname()+" owns "+user1.getOwnedgames().size()+" games:");
            for(int i=0;i<user1.getOwnedgames().size();i++){
                System.out.println(user1.getOwnedgames().get(i).getName());               
                System.out.print("\tReview:"+user1.getOwnedgames().get(i).getReview()+"\n\tGenres:");
                for(int a=0;a<user1.getOwnedgames().get(i).getGenres().size();a++){
                    System.out.print(user1.getOwnedgames().get(i).getGenres().get(a));
                    if(a+1!=user1.getOwnedgames().get(i).getGenres().size()) System.out.print(",");
                }
                System.out.print("\n");
            }
            System.out.println("His favorite genre is "+user1.getBehaviour().getFavGenre()+"\n");
            String x = lukija.nextLine();
            if(x.equals("stop")) break;
        }
    }
    
}
