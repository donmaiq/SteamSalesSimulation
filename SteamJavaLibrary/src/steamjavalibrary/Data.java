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

public class Data {
    public static boolean steamsale;
    public static  GamesArray allgames = new GamesArray();
    public static  UsersArray allusers = new UsersArray();
    public static String[] genreslist = {"rpg","mmo","fps","casual","adventure","arcade","rts"};
    private static int gamessold;
    public Data(){
        this.steamsale=false;
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
        
    }

    public static int getGamessold() {
        return gamessold;
    }
    
    public static void incrementSold(){
        gamessold+=1;
    }
    public static boolean isSale() {
        return steamsale;
    }
    public static void setSale(boolean sale) {
        Data.steamsale = sale;
    }
}
