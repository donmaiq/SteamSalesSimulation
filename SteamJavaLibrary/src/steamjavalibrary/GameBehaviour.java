package steamjavalibrary;
import java.util.Random;

/**
 * A class containing information of a games behaviour.
 * @author Jonnie
 */
public class GameBehaviour {
    private final int salerating; //0-100, chance for a sale
    private final int[] salescale = new int[3];
    private final int pricetimescale; //0-100, variation in price over time
    private final int pricedropamount; //0-50 %, how much price drops per iteration
    
    /**
     * Constructor for the class. Randomly generates behaviour values for the object.
     */
    public GameBehaviour() {
        Random r = new Random();
        salerating = r.nextInt(101);
        salescale[0] = r.nextInt(21)+5; //5-25
        salescale[1] = r.nextInt(21)+30; //30-50
        salescale[2] = r.nextInt(16)+65; //65-80
        pricetimescale = r.nextInt(101); 
        pricedropamount = r.nextInt(31)+10; //10-40
    }
    
    public int getPricedropamount() {
        return pricedropamount;
    }
    
    public int getPricetimescale(){
        return pricetimescale;
    }
    
    public int[] getSalescale(){
        return salescale;
    }
    
    public int getSalerating(){
        return salerating;
    }
    
}
