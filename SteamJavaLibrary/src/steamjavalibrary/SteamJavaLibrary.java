package steamjavalibrary;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author joona
 */
public class SteamJavaLibrary extends Application{
    public static Data data;
    /**
     * Runs the UI for the application.
     * @param stage
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception {
        
        Parent loadroot = FXMLLoader.load(getClass().getResource("loading.fxml"));
        Scene loadingscene = new Scene(loadroot);
        
        stage.setScene(loadingscene);
        stage.show();
        
        Task<Integer> task = new Task<Integer>() {
            @Override
            public Integer call() {
                data = new Data();
                return 1;
            }
        };
        task.setOnSucceeded(e -> { 
            try{
                Parent root = FXMLLoader.load(getClass().getResource("ui.fxml"));
                Scene mainscene = new Scene(root);
                stage.setScene(mainscene);
                stage.show();
            }catch(Exception a){
            };
        });
        new Thread(task).start();
        
        
        
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
