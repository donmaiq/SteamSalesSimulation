package steamjavalibrary;
import java.util.Random;

/**
 * A class containing information of a games behaviour.
 * @author Jonnie
 */
public class GameBehaviour {
    private final int[] salescale = new int[3];
    private final int pricedropamount; //0-50 %, how much price drops per iteration
    
    /**
     * Constructor for the class. Randomly generates behaviour values for the object.
     */
    public GameBehaviour() {
        Random r = new Random();
        salescale[0] = r.nextInt(21)+5; //5-25
        salescale[1] = r.nextInt(21)+30; //30-50
        salescale[2] = r.nextInt(16)+65; //65-80
        pricedropamount = r.nextInt(31)+10; //10-40
    }
    /**
    * Getter for the amount a game reduces in price.
    * @return value of price drop
    */
    public int getPricedropamount() {
        return pricedropamount;
    }
    /**
    * Getter for the 3 different sales a game can have.
    * @return int[3] with 3 sale values in size order.
    */
    public int[] getSalescale(){
        return salescale;
    }
    
}
