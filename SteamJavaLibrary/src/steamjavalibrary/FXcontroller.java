/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steamjavalibrary;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    
    private final XYChart.Series soldgraph = new XYChart.Series();
    private final XYChart.Series reviewgraph = new XYChart.Series();
    private final ArrayList<XYChart.Series> monthgraphlist = new ArrayList();
    
    private int[] daysinmonth = {31,28,31,30,31,30,31,31,30,31,30,31};
    private String[] monthstrings = {"January","February","March","April","May","June",
                                    "July","August","September","October","November","December"};
    private int monthcounter = 0;
    private int monthdaycounter = 1;
    private static int yearcounter = 1;
    private static int daycounter = 1;
    public static int getYear(){
        return yearcounter;
    }
    public static int getDay(){
        return daycounter;
    }
    
    private int currentmonthgraph;
    private boolean monthgraphvisible = false;
    
    private void simulationloop(){
        if(daycounter==0) {
            resetgraphs();
            yearcounter++;
            monthcounter=0;
            monthdaycounter=1;
            year.setText("Year "+yearcounter);
            daycounter=1;
            return;
        }
        if(daycounter<366){
            if(monthdaycounter>daysinmonth[monthcounter]){
                if(monthgraphvisible && currentmonthgraph==monthcounter) monthgraphforward(new ActionEvent());
                monthcounter++;
                monthdaycounter=1;
            }
            if(daycounter%7==0){
                data.shuffleWeeklydiscount();
                discountsellamount = countdiscountsold(data.getWeeklydiscount());
                simtextarea.appendText("\nNew weekly discount:\n"+data.getWeeklydiscount().getName()+
                            "\n"+data.getWeeklydiscount().getSaleprice()+"€"+"\n"+
                                data.getWeeklydiscount().getSaleamount()+"% sale\n");
            }
            //summersale
            if(daycounter==167) data.setSale(true);
            if(daycounter>167 && daycounter<177) data.setsalegames();
            if(daycounter==177) data.setSale(false);
            //winter sale
            if(daycounter==340) data.setSale(true);
            if(daycounter>340 && daycounter<350) data.setsalegames();
            if(daycounter==350) data.setSale(false);
            
            simtextarea.appendText("\nDay "+daycounter+"\n");
            sellGames();
            soldcount.setText(""+formatthousands(data.getGamessold(),false));
            revenue.setText(formatthousands(data.getSteamrevenue(),false)+" €");
            day.setText("Day "+daycounter);
            daycounter++;
            monthdaycounter++;
        }else{
            daycounter=0;
            togglesim.setText("Start");
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
    public void initgraphs(){
        int[] reviews = new int[101];
        for(int i=0;i<data.getAllgames().getApps().size();i++){
            SteamGame game = data.getAllgames().getApps().get(i);
            reviews[game.getReview()] += 1;
        }
        for(int a=0;a<reviews.length;a++){
            reviewgraph.getData().add(new XYChart.Data(a, reviews[a]));
        }
        for(int i=0;i<12;i++){
            monthgraphlist.add(new XYChart.Series());
        }
        linechart.setLegendVisible(false);
        currentmonthgraph = 0;
    }
    public void resetgraphs(){
        soldgraph.getData().clear();
        for(int i=0;i<12;i++){
            monthgraphlist.get(i).getData().clear();
        }
    }
    /**
     * Updates the graphs with the data in arguments.
     * @param soldcount 
     */
    public void updategraphs(int soldcount){
        soldgraph.getData().add(new XYChart.Data(daycounter, soldcount));
        monthgraphlist.get(monthcounter).getData().add(new XYChart.Data(monthdaycounter, soldcount));

    }
    
    /**
     * Counts the base of sales for a game on sale
     * depending on the nature of the sale.
     * @param game SteamGame to calculate from
     * @return int returns amount of games to be sold
     */
    public int countdiscountsold(SteamGame game){
        int discountreview = game.getReview();
        double discountprice = game.getSaleprice();
        int discountsize = game.getSaleamount();
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
        return selldiscountamount;
    }
    
    /**
     * Sells games for one day.
     */
    public void sellGames(){
        Random r = new Random();
        int sold=0;
        int amount = (int) Math.abs(r.nextGaussian()*200)+2000; // sell about 1000-1100 games
        
        //If the steamsale is on, sell more games. If not sell the weekly discount game.
        if(data.isSteamsale()) {
            for(int i=0;i<data.getSteamsalelist().size();i++){
                int multip = r.nextInt(4)*r.nextInt(4)+1;
                int soldcount = multip * countdiscountsold(data.getSteamsalelist().get(i));
                for(int a=0;a<soldcount;a++){
                    if(data.getAllusers().getUsers().get(r.nextInt(data.getAllusers()
                            .getUsers().size())).buyGame(data.getSteamsalelist().get(i))) sold++;
                }
            }
            amount=amount*3;
            
        }else{
            int totalweeklysold = (int) Math.abs(discountsellamount + r.nextGaussian()/3*discountsellamount)+50;
            for(int a=0;a<totalweeklysold;a++){
                SteamUser ruser = data.getAllusers().getUsers().get(r.nextInt(data.getAllusers().getUsers().size()));
                if(ruser.buyGame(data.getWeeklydiscount())){
                    sold++;
                }
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
    private Label soldcount;
    @FXML
    private Label day;
    @FXML
    private Label year;
    @FXML
    private ImageView loading1;
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
        if(fastforward.getText().equals("Fast")){
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
        simulationloop();
        while(daycounter<366){
            simulationloop();
        }
        simulationloop();
    }
    //TAB 2
    @FXML
    private LineChart linechart;
    @FXML
    private NumberAxis xAxis;
    @FXML
    private ImageView loading2;

    @FXML
    private NumberAxis yAxis;
    @FXML
    private void salesgraph(ActionEvent event) {
        linechart.getData().clear();
        monthgraphvisible = false;
        linechart.setTitle("SteamGame Sales");
        linechart.getYAxis().setLabel("Sales/day");
        linechart.getXAxis().setLabel("Day");
        linechart.getData().add(soldgraph);
    }
    @FXML
    private void reviewgraph(ActionEvent event) {
        linechart.getData().clear();
        monthgraphvisible = false;
        linechart.setTitle("SteamGame Reviews");
        linechart.getYAxis().setLabel("Score count");
        linechart.getXAxis().setLabel("Review Score");
        linechart.getData().add(reviewgraph);
    }
    private void setmonthgraph(int month){
        linechart.getData().clear();
        if(linechart.getData().contains(monthgraphlist.get(month))) return;
        linechart.getData().add(monthgraphlist.get(month));
        
    }
    @FXML
    private void monthgraph(ActionEvent event) {
        if(!monthgraphvisible){
            monthgraphvisible = true;
            currentmonthgraph = monthcounter;
            linechart.setTitle("Sales for "+monthstrings[currentmonthgraph] + " year "+yearcounter);
            linechart.getYAxis().setLabel("Sales/day");
            linechart.getXAxis().setLabel("Day");
            setmonthgraph(currentmonthgraph);
        }
    }
    @FXML
    private void monthgraphback(ActionEvent event) {
        if(monthgraphvisible && currentmonthgraph!=0 && daycounter==0){
            currentmonthgraph = currentmonthgraph-1;
            linechart.setTitle("Sales for "+monthstrings[currentmonthgraph] + " year "+yearcounter);
            setmonthgraph(currentmonthgraph);
        }
    }
    @FXML
    private void monthgraphforward(ActionEvent event) {
        if(monthgraphvisible && currentmonthgraph!=11 && daycounter==0){
            currentmonthgraph = currentmonthgraph+1;
            linechart.setTitle("Sales for "+monthstrings[currentmonthgraph] + " year "+yearcounter);
            setmonthgraph(currentmonthgraph);
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
    private ImageView loading3;
    @FXML
    private Label steamid;
    @FXML
    private Label usermoneyspent;
   
    private void setuser(SteamUser user){
        String url = user.getAvatar().substring(0, user.getAvatar().length()-4) +"_medium.jpg";
        Image avatar = new Image(url, 60, 60, false, false);
        profilepic.setImage(avatar);
        username.setText(user.getPersonaname());
        steamid.setText(""+user.getSteamid());
        usergamecount.setText(""+user.getOwnedgames().size());
        usermoneyspent.setText(""+formatthousands(user.getMoneyspent(),true)+"€");
        usertextarea.clear();
        String tmpstring = "";
        for(int i=0; i<user.getHistory().size();i++){
            PurchaseHist tmphist = user.getHistory().get(i);
            tmpstring +=        "\n"+
                                tmphist.getGame().getName()+"\n"+ 
                                tmphist.getPrice()+"€\t"+
                                "purchased on year: "+tmphist.getTimestamp()[0]+" Day: "+tmphist.getTimestamp()[1]+"\n";
        }
        usertextarea.appendText(tmpstring);
    }
    @FXML
    private void randomuser(ActionEvent event) {
        Random r = new Random();
        loading3.setVisible(true);
        SteamUser user = data.getAllusers().getUsers().get(r.nextInt(data.getAllusers().getUsers().size()));
        setuser(user);
        loading3.setVisible(false);
    }
    @FXML
    private void topuser(ActionEvent event) {
        loading3.setVisible(true);
        SteamUser user = data.getAllusers().getUsers().get(0);
        for(int i=0;i<data.getAllusers().getUsers().size();i++){
            if(user.getOwnedgames().size()<data.getAllusers().getUsers().get(i).getOwnedgames().size()){
                user = data.getAllusers().getUsers().get(i);
            }
        }
        setuser(user);
        loading3.setVisible(false);
    }
    
    //TAB 4
    @FXML
    private TextArea gametextarea;
    @FXML
    private Label gamename;
    @FXML
    private Label gamegamecount;
    @FXML
    private Label gamegenre;
    @FXML
    private ImageView loading4;
    @FXML
    private Label gametotalrevenue;
    @FXML
    private Label gamesteamrevenue;
    private void setgame(SteamGame game){
        gamename.setText(game.getName());
        gamegenre.setText(game.getGenre());
        gamegamecount.setText(""+game.getSoldunits());
        gametotalrevenue.setText(""+formatthousands(game.getRevenue(), false)+"€");
        gamesteamrevenue.setText(""+formatthousands(game.getSteamcut(),false)+"€");
        
        gametextarea.clear();
        String tmpstring = "";
        int a = (game.getHistory().size() < 100 ? 0: game.getHistory().size()-99);
        for(int i=a; i<game.getHistory().size();i++){
            PurchaseHist tmphist = game.getHistory().get(i);
            tmpstring +=        "\n"+
                                tmphist.getUser().getPersonaname()+"\n"+ 
                                tmphist.getPrice()+"€\t"+
                                "purchased on year: "+tmphist.getTimestamp()[0]+" Day: "+tmphist.getTimestamp()[1]+"\n";
        }
        gametextarea.appendText(tmpstring);
    }
    
    @FXML
    private void randomgame(ActionEvent event) {
        Random r = new Random();
        loading4.setVisible(true);
        SteamGame game = data.getAllgames().getApps().get(r.nextInt(data.getAllgames().getApps().size()));
        setgame(game);
        loading4.setVisible(false);
    }
    @FXML
    private void topgame(ActionEvent event){
        loading4.setVisible(true);
        SteamGame game = data.getAllgames().getApps().get(0);
        for(int i=0;i<data.getAllgames().getApps().size();i++){
            if(game.getSoldunits()<data.getAllgames().getApps().get(i).getSoldunits()){
                game = data.getAllgames().getApps().get(i);
            }
        }
        setgame(game);
        loading4.setVisible(false);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initgraphs();
    }    
    
}
