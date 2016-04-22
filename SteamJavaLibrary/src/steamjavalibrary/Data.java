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
<<<<<<< HEAD
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
=======
import java.util.Collections;
>>>>>>> e86083e3e21f5f53526ac92283dde661be5b7585

public class Data {
    public static boolean steamsale;
    public static  GamesArray allgames = new GamesArray();
    public static  UsersArray allusers = new UsersArray();
    public static String[] genreslist = {"rpg","mmo","fps","casual","adventure","arcade","rts"};
<<<<<<< HEAD
    public static List<Integer> apu=new ArrayList();
    public static int[] apu2=new int[0];
    
=======
>>>>>>> e86083e3e21f5f53526ac92283dde661be5b7585
    
    public Data(){
        this.steamsale=false;
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

    public static boolean isSale() {
        return steamsale;
    }
    public static void setSale(boolean sale) {
        Data.steamsale = sale;
    }
<<<<<<< HEAD
    public static void sortGames(){
        for(int i=0;i<allgames.getApps().size();i++){
            apu.add(allgames.getApps().get(i).getReview());
        }
        for (int i=0;i<allgames.getApps().size();i++){
        apu2[i]=apu.get(i);
        }
        järjestäTaulukko(apu2);
        tulostaTaulukko(apu2);
    }
    public static void tulostaTaulukko(int[] b) {
        for (int i = 0; i < b.length; i++) {
            System.out.print(b[i] + " ");
        }
        System.out.println();
    }

    public static int etsiMinIndex(int[] taulu, int i) {
        int minInd = i;
        for (int a = i; a < taulu.length; a++) {
            if (taulu[minInd] > taulu[a]) {
                minInd = a;
            }
        }
        return minInd;
    }

    public static void vaihda(int[] taulu, int i, int j) {
        int alt = taulu[i];
        taulu[i] = taulu[j];
        taulu[j] = alt;
    }

    public static void järjestäTaulukko(int[] taulu) {
        int minInd;
        for (int i = 0; i < taulu.length - 1; i++) {
            minInd = etsiMinIndex(taulu, i);
            vaihda(taulu, i, minInd);
        }
    }
=======
    
>>>>>>> e86083e3e21f5f53526ac92283dde661be5b7585
}
