package steamjavalibrary;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import java.util.Random;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author joona
 */
public class SteamJavaLibrary extends Application{
    public static Data data;
    public static int simCount=1;
    public static TextArea textField = new TextArea();
    public static XYChart.Series series = new XYChart.Series();
    public static XYChart.Series series2 = new XYChart.Series();
    public static Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), 
            event -> mainLoop()));
    
    /**
     * The main loop for the simulation. 
     * Will simulate up to 365 days of sales.
     */
    public static void mainLoop(){
        if(simCount<366){
            Random r = new Random();
            appendText("\nDay "+simCount+"\n");
            sellGames((int) Math.abs(r.nextGaussian()*1000)+300);
            appendText("Total games sold "+formatthousands(data.getGamessold(),false)+"\n");
            
            appendText("Total revenue: "+formatthousands(data.getSteamrevenue(),false)+"€\n");
            simCount++;
        }else{
            startsimulation(0);
        }
    }
    /**
     * Formats a double with a space between thousands.
     * Amount is the double to be formatted, decimal is a boolean
     * for preserving decimals. Returns the formatted double as a string.
     * @param amount
     * @param decimal
     * @return 
     */
    public static String formatthousands(double amount, boolean decimal){        
        int tmpint = (int) Math.floor(amount);
        if(!decimal) return String.format("%,d", tmpint).replace(",", " ");
        String part1 = String.format("%,d", tmpint).replace(",", " ");
        String part2 = (""+(amount-tmpint)).substring(1);
        return part1+part2;
    }
    
    /**
     * Starts or stops the simulation based on the args.
     * 1 will play and 0 will stop.
     * @param args 
     */
    public static void startsimulation(int args){
        if(args==1){
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        }else{
            timeline.stop();
        }
    }
    
    /**
     * Outputs a string to the simulations textarea.
     * @param string
     */
    public static void appendText(String string) {
        Platform.runLater(() -> textField.appendText(string));
    }
    
    /**
     * Sells an amount of games specified in the argument.
     * @param amount 
     */
    public static void sellGames(int amount){
        int sold=0;
        Random r = new Random();
        for(int i=0;i<amount;i++){
            SteamUser ruser = getRandomUser();
            String genre = ruser.getBehaviour().getFavGenre();
            SteamGame rgame;
            if(r.nextBoolean()){
                rgame = getRandomGame(genre);
            }else{
                rgame = getRandomGame("");
            }
            if(ruser.buyGame(rgame)) sold++;
        }
        updategraphs(sold);
        appendText(sold+" games sold\n");
    }
    
    /**
     * Returns a random user with a normal distribution based on users variationscale(0-100).
     * The higher the variation the bigger the chance to return that user.
     * @return 
     */
    public static SteamUser getRandomUser(){
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
     * The argument specifies which genre the game should be. Null means any genre.
     * @param genre
     * @return 
     */
    public static SteamGame getRandomGame(String genre){
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
    public static void initgraphs(){
        int[] reviews = new int[101];
        for(int i=0;i<data.getAllgames().getApps().size();i++){
            SteamGame game = data.getAllgames().getApps().get(i);
            reviews[game.getReview()] += 1;
        }
        for(int a=0;a<reviews.length;a++){
            series2.getData().add(new XYChart.Data(a, reviews[a]));
        }
    }
    public static void updategraphs(int soldcount){
        series.getData().add(new XYChart.Data(simCount, soldcount));
    }
    
    /**
     * Runs the UI for the application.
     * @param primaryStage
     * @throws Exception 
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        root.getStyleClass().add("root");
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        Scene scene = new Scene(root, 800, 500,Color.rgb(28, 28, 28));
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        tabPane.setTabMinWidth(240);
        tabPane.getStyleClass().add("tabbar");
        
        BorderPane borderPane = new BorderPane();
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());
        borderPane.setCenter(tabPane);
        borderPane.opacityProperty().set(0.0);
        
        FadeTransition ftB = new FadeTransition(Duration.millis(1000), borderPane);
        ftB.setFromValue(0.0);
        ftB.setToValue(1.0);
        ftB.setDelay(Duration.millis(1000));
        
        scene.getStylesheets().add(SteamJavaLibrary.class.getResource("../resources/style.css").toExternalForm());
        primaryStage.setTitle("Steam Sales Simulator");
        primaryStage.setScene(scene);
        
        //LOADING SCREEN
            Image image = new Image("resources/steamlogo-white-full.gif", 600, 203, false, false);
            final ImageView imv = new ImageView();
            imv.setImage(image);
            Image image2 = new Image("resources/steamlogo-white-full-done.gif", 600, 203, false, false);
            final ImageView imv2 = new ImageView();
            imv2.setImage(image2);
            final StackPane pictureRegion = new StackPane();
            pictureRegion.prefWidthProperty().bind(scene.widthProperty());
            pictureRegion.prefHeightProperty().bind(scene.heightProperty());
            pictureRegion.getChildren().add(imv);
            pictureRegion.setAlignment(imv,Pos.CENTER);
            pictureRegion.opacityProperty().set(0.0);
            pictureRegion.getStyleClass().add("loading");
            root.getChildren().add(pictureRegion);
            FadeTransition ft = new FadeTransition(Duration.millis(1000), pictureRegion);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.setDelay(Duration.millis(2000));
            FadeTransition ft2 = new FadeTransition(Duration.millis(500), pictureRegion);
            ft2.setFromValue(1.0);
            ft2.setToValue(0.0);
            SequentialTransition seqT = new SequentialTransition (pictureRegion,ft);
            SequentialTransition seqT2 = new SequentialTransition (pictureRegion,ft2);
             
            seqT2.onFinishedProperty().set(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event){
                    root.getChildren().remove(pictureRegion);
                    root.getChildren().add(borderPane);
                    ftB.play();
                }
            });
       
        //FIRST TAB
            Tab tab1 = new Tab();
            tab1.setText("Simulation");
            tab1.getStyleClass().add("tab");
                GridPane grid1 = new GridPane();
                grid1.setAlignment(Pos.CENTER);
                grid1.setHgap(10);
                grid1.setVgap(10);
                grid1.setPadding(new Insets(5, 5, 5, 5));
                
                //TESTBUTTON
                        Button simbut2 = new Button();
                        simbut2.setText("TEST");
                        simbut2.getStyleClass().add("butt");
                        HBox hbsimbut2 = new HBox(10);  
                        hbsimbut2.setAlignment(Pos.CENTER);
                        simbut2.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                System.out.println("The last 10 receipts.");
                                ArrayList<PurchaseHist> hist = data.getAllhistory();
                                for(int a=10;a>0;a--){
                                    String game = hist.get(hist.size()-a).getGame().getName();
                                    String user = hist.get(hist.size()-a).getUser().getPersonaname();
                                    double price = hist.get(hist.size()-a).getPrice();
                                    appendText("\n"+user+"\n"+game+"\n"+price+"\n");
                                }
                            }
                        });
                        hbsimbut2.getChildren().add(simbut2);
                        grid1.add(hbsimbut2, 5,10,2,1);
                //TESTBUTTON
                
                
                Button simbut = new Button();
                simbut.setText("Start");
                simbut.getStyleClass().add("butt");
                HBox hbsimbut = new HBox(10);  
                hbsimbut.setAlignment(Pos.CENTER);
                simbut.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if(simbut.getText().equals("Start")){
                            simbut.setText("Stop");
                            startsimulation(1);
                        }else{
                            simbut.setText("Start");                            
                            startsimulation(0);
                        }
                    }
                });
                hbsimbut.getChildren().add(simbut);
                grid1.add(hbsimbut, 4,9,2,1);
                textField.getStyleClass().add("textfield");
                textField.wrapTextProperty().set(true);
                textField.editableProperty().set(true);
                grid1.add(textField, 0,1,8,5);
            tab1.setContent(grid1);
            tabPane.getTabs().add(tab1);
        //END OF FIRST TAB
        
        //Second TAB
            Tab tab2 = new Tab();
            tab2.setText("Statistics");
            tab2.getStyleClass().add("tab");
                GridPane grid2 = new GridPane();
                grid2.setAlignment(Pos.CENTER);
                grid2.setHgap(10);
                grid2.setVgap(10);
                grid2.setPadding(new Insets(5, 5, 5, 5));
                final NumberAxis xAxis = new NumberAxis();
                final NumberAxis yAxis = new NumberAxis();
                xAxis.setLabel("Day number");
                yAxis.setLabel("units sold");
                final LineChart<Number,Number> lineChart = 
                new LineChart<Number,Number>(xAxis,yAxis);
                lineChart.setTitle("Sale Monitoring");
                lineChart.getData().add(series);
                
                final NumberAxis xAxis2 = new NumberAxis();
                final NumberAxis yAxis2 = new NumberAxis();
                xAxis2.setLowerBound(0);
                xAxis2.setUpperBound(100);
                xAxis2.setLabel("Review value");
                yAxis2.setLabel("review amounts");
                final LineChart<Number,Number> lineChart2 = 
                new LineChart<Number,Number>(xAxis2,yAxis2);
                lineChart2.setTitle("Review Monitoring");
                lineChart2.getData().add(series2);
                
            
            tab2.setContent(lineChart);
            tabPane.getTabs().add(tab2);
        //END OF SECOND TAB
        
        //THIRD TAB
            Tab tab3 = new Tab();
            tab3.setText("Users");
            tab3.getStyleClass().add("tab");
                GridPane grid3 = new GridPane();
                grid3.setAlignment(Pos.CENTER);
                grid3.setHgap(10);
                grid3.setVgap(10);
                grid3.setPadding(new Insets(5, 5, 5, 5));

                final ImageView ava = new ImageView();
                final HBox avaRegion = new HBox(10);
                avaRegion.getChildren().add(ava);
                avaRegion.setId("profileimg");
                grid3.add(avaRegion, 0, 2);

                Text username = new Text("");
                username.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
                username.setId("text");
                grid3.add(username, 1, 2);

                Button btn = new Button();
                btn.setText("print random user in console");
                btn.getStyleClass().add("butt");
                HBox hbBtn = new HBox(10);  
                hbBtn.setAlignment(Pos.CENTER);
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String[] userl = Printrandom();
                        username.setText(userl[0]);
                        Image newavatar = new Image(userl[1], 0, 40, false, false);
                        ava.setImage(newavatar);
                    }
                });
                hbBtn.getChildren().add(btn);
                grid3.add(hbBtn, 0,3,2,1);
            tab3.setContent(grid3);
            tabPane.getTabs().add(tab3);
        //END OF THIRD TAB
        
        //grid3.setGridLinesVisible(true);
        primaryStage.show();
        
        seqT.play();
        Task<Integer> task = new Task<Integer>() {
            @Override
            public Integer call() {
                data = new Data();
                return 1;
            }
        };
        task.setOnSucceeded(e -> {
                    initgraphs();
                    pictureRegion.getChildren().clear();
                    pictureRegion.getChildren().add(imv2);
                    pictureRegion.setOnMousePressed(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle (MouseEvent mouseEvent) {  
                            seqT2.play();
                        }
                    });
        });
        new Thread(task).start();
       
    }

    //placeholder for printing a random user
    public static String[] Printrandom(){
        Random r = new Random();
        SteamUser user1 = data.getAllusers().getUsers().get(r.nextInt(data.getAllusers().getUsers().size()));
        String[] returni = new String[2];
        returni[0] = user1.getPersonaname();
        returni[1] = user1.getAvatar();
        return returni;
    }
    /**
     * The main for the project.
     * Launches the UI.
     * @param args 
     */
    public static void main(String[] args){
        launch(args);
    }
}
