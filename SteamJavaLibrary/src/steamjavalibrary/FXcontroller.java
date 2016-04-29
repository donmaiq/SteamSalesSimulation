/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steamjavalibrary;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import static steamjavalibrary.SteamJavaLibrary.data;

/**
 *
 * @author anon
 */
public class FXcontroller implements Initializable {
    private KeyFrame timekeyframe = new KeyFrame(Duration.millis(1000), event -> simulationloop());
    private Timeline timeline = new Timeline(timekeyframe);
    private int discountsellamount;
    
    public static XYChart.Series soldgraph = new XYChart.Series();
    public static XYChart.Series reviewgraph = new XYChart.Series();
    
    private static int daycounter = 1;
    public static int getDay(){
        return daycounter;
    }
    
    private void simulationloop(){
        if(daycounter<366){
            if(daycounter%7==0){
                data.shuffleWeeklydiscount();
                countWeeklydiscountsold();
                simtextarea.appendText("\nNew weekly discount:\n"+data.getWeeklydiscount().getName()+
                            "\n"+data.getWeeklydiscount().getSaleprice()+"€"+"\n"+
                                data.getWeeklydiscount().getSaleamount()+"% sale\n");
            }
            //summersale
            if(daycounter==167) data.setSale(true);
            if(daycounter==174) data.setSale(false);
            //winter sale
            if(daycounter==340) data.setSale(true);
            if(daycounter==347) data.setSale(false);
            
            simtextarea.appendText("\nDay "+daycounter+"\n");
            sellGames();
            soldcount.setText(""+formatthousands(data.getGamessold(),false));
            revenue.setText(formatthousands(data.getSteamrevenue(),false)+" €");
            daycounter++;
        }else{
            startsimulation(0);
        }
    }
        
    public void startsimulation(int args){
        if(args==1){
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        }else{
            timeline.stop();
        }
    }
    public String formatthousands(double amount, boolean decimal){        
        int tmpint = (int) Math.floor(amount);
        if(!decimal) return String.format("%,d", tmpint).replace(",", " ");
        String part1 = String.format("%,d", tmpint).replace(",", " ");
        String part2 = (""+(amount-tmpint)).substring(1);
        return part1+part2;
    }
    
    /**
     * Initializes the graphs used to graph information about sales.
     */
    public static void initgraphs(){
        int[] reviews = new int[101];
        for(int i=0;i<data.getAllgames().getApps().size();i++){
            SteamGame game = data.getAllgames().getApps().get(i);
            reviews[game.getReview()] += 1;
        }
        for(int a=0;a<reviews.length;a++){
            reviewgraph.getData().add(new XYChart.Data(a, reviews[a]));
        }
    }
    
    /**
     * Updates the graphs with the data in arguments.
     * @param soldcount 
     */
    public void updategraphs(int soldcount){
        soldgraph.getData().add(new XYChart.Data(daycounter, soldcount));
    }
    
    /**
     * Counts the base of daily sales for a weekly discount game
     * depending on the nature of the sale.
     */
    public void countWeeklydiscountsold(){
        int discountreview = data.getWeeklydiscount().getReview();
        double discountprice = data.getWeeklydiscount().getSaleprice();
        int discountsize = data.getWeeklydiscount().getSaleamount();
        int selldiscountamount = discountreview*discountreview/100; //exponential depending on reviewmax 100
        
        if(discountsize > 60){
            if(discountprice<10){
                selldiscountamount=selldiscountamount*10;
            }else{
                selldiscountamount=selldiscountamount*5;
            }
        }else if(discountsize>20){
            if(discountprice<10){
                selldiscountamount=selldiscountamount*8;
            }else{
                selldiscountamount=selldiscountamount*4;
            }
        }else{
            if(discountprice<10){
                selldiscountamount=selldiscountamount*6;
            }else{
                selldiscountamount=selldiscountamount*2;
            }
        }
        discountsellamount = selldiscountamount;
    }
    
    /**
     * Sells games for one day.
     */
    public void sellGames(){
        Random r = new Random();
        int sold=0;
        int amount = (int) Math.abs(r.nextGaussian()*100)+1000; // sell about 1000-1100 games
        
        //If the steamsale is on, sell more games. If not sell the weekly discount game.
        if(data.isSteamsale()) { 
            amount=amount*5 ;
            
        }else{
            int totalweeklysold = (int) Math.abs(discountsellamount + r.nextGaussian()/3*discountsellamount)+50;
            for(int a=0;a<totalweeklysold;a++){
                SteamUser ruser = data.getAllusers().getUsers().get(data.getAllusers().getUsers().size()-1);
                ruser.buyGame(data.getWeeklydiscount());
                sold++;
            }
        }
        
        for(int i=0;i<amount;i++){
            SteamUser ruser = getRandomUser();
            String genre = ruser.getBehaviour().getFavGenre();
            SteamGame rgame;
            // 50/50 if user buys a game in his favorite genre
            if(r.nextBoolean()){
                rgame = getRandomGame(genre); 
            }else{
                rgame = getRandomGame("");
            }
            if(ruser.buyGame(rgame)) sold++;
        }
        updategraphs(sold);
        simtextarea.appendText(sold+" games sold\n");
    }
    
    /**
     * Returns a random user with a normal distribution based on users variationscale(0-100).
     * The higher the variation the bigger the chance to return that user.
     * @return 
     */
    public SteamUser getRandomUser(){
        Random r = new Random();
        int usergaussian = (int) Math.abs(r.nextGaussian()/2*data.getAllusers().getUsers().size());
        while(usergaussian+1>data.getAllusers().getUsers().size()){
           usergaussian = (int) Math.abs(r.nextGaussian()/2*data.getAllusers().getUsers().size());
        }
        return data.getAllusers().getUsers().get(usergaussian);
    }
    
    /**
     * Returns a SteamGame with a normal distribution based on review(0-100).
     * The higher the review, the bigger the chance for returning that game.
     * 
     * @param genre String of genre to buy, empty string means any genre.
     * @return a random SteamGame object
     */
    public SteamGame getRandomGame(String genre){
        Random r = new Random();
        if(genre.equals("")){
            int gamegaussian = (int) Math.abs(r.nextGaussian()/2*data.getAllgames().getApps().size());
            while(gamegaussian+1>data.getAllgames().getApps().size()){
                gamegaussian = (int) Math.abs(r.nextGaussian()/2*data.getAllgames().getApps().size());
            }
            return data.getAllgames().getApps().get(gamegaussian);
        }else{
            int gamegaussian = (int) Math.abs(r.nextGaussian()/2*data.getAllgames().getGenreArray(genre).size());
            while(gamegaussian+1>data.getAllgames().getGenreArray(genre).size()){
                gamegaussian = (int) Math.abs(r.nextGaussian()/2*data.getAllgames().getGenreArray(genre).size());
            }
            return data.getAllgames().getGenreArray(genre).get(gamegaussian);
        }
    }
    
    
    //TAB 1
    @FXML
    private Label revenue;
    @FXML
    private Slider simspeed;
    @FXML
    private Label soldcount;
    @FXML
    private Button togglesim;
    @FXML
    private Button skip;
    @FXML
    private Button fastforward;
    @FXML
    private TextArea simtextarea;
    
    @FXML
    private void changespeed(ActionEvent event){
        Duration tickspeed;
        if(fastforward.getText().equals("Slower")){
            tickspeed = Duration.millis(1500);
            fastforward.setText("Slow");
        }else{
            tickspeed = Duration.millis(50);
            fastforward.setText("Fast");
        }
        boolean restart = false;
        if(timeline.getStatus().toString().equals("RUNNING")) restart=true;
        KeyFrame newframe = new KeyFrame(tickspeed,new EventHandler<ActionEvent>() {
                                                @Override
                                                public void handle(ActionEvent event) {
                                                    simulationloop();  
                                                }
                                            });
        timeline.stop();
        timeline.getKeyFrames().setAll(newframe);
        if(restart) timeline.play();
    }
    @FXML
    private void togglesim(ActionEvent event){
        if(togglesim.getText().equals("Start")){
            startsimulation(1);
            togglesim.setText("Pause");
        }else{
            startsimulation(0);
            togglesim.setText("Start");
        }
    }
    @FXML
    private void skiptoend(ActionEvent event){
        timeline.stop();
        togglesim.setText("Start");
        while(daycounter<366){
            simulationloop();
        }
        
    }
    
    //TAB 3 
    @FXML
    private TextArea usertextarea;
    @FXML
    private ImageView profilepic;
    @FXML
    private Label username;
    @FXML
    private Label usergamecount;
    @FXML
    private Label steamid;
   
    @FXML
    private void randomuser(ActionEvent event) {
        Random r = new Random();
        SteamUser user1 = data.getAllusers().getUsers().get(r.nextInt(data.getAllusers().getUsers().size()));
        String url = user1.getAvatar().substring(0, user1.getAvatar().length()-4) +"_medium.jpg";
        Image avatar = new Image(url, 60, 60, false, false);
        profilepic.setImage(avatar);
        username.setText(user1.getPersonaname());
        steamid.setText(""+user1.getSteamid());
        usergamecount.setText(""+user1.getOwnedgames().size());
        
        usertextarea.clear();
        String tmpstring = "";
        for(int i=0; i<user1.getHistory().size();i++){
            PurchaseHist tmphist = user1.getHistory().get(i);
            tmpstring +=        "\n"+
                                tmphist.getGame().getName()+"\n"+ 
                                tmphist.getPrice()+"€\t"+
                                "purchased on day: "+tmphist.getTimestamp()+"\n";
        }
        usertextarea.appendText(tmpstring);
    }
    
    @FXML
    LineChart<Number,Number> linechart;
    
    @FXML
    private void salesgraph(ActionEvent event) {

    }
    @FXML
    private void reviewgraph(ActionEvent event) {
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
