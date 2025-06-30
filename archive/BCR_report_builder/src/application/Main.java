package application;
	
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("JavaFX");
			
			GridPane grid = new GridPane();
			//grid.setPadding(new Insets(130, 125, 150, 125));
			grid.setPadding(new Insets(20, 20, 20, 20));
			grid.setAlignment(Pos.CENTER);
			grid.setHgap(10);
			grid.setVgap(10);
			Text scenetitle = new Text("Welcome");
			scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 25));
			
			Button createReport = new Button("Create Report");
			Button editReport = new Button("Edit Report");
			Button deleteReport = new Button("Delete Report");
			
			grid.add(scenetitle, 0, 0);
			grid.add(createReport, 1, 2, 2, 2);
			grid.add(editReport, 3, 2, 2, 2);
			grid.add(deleteReport, 5, 2, 2, 2);
			
			Scene scene = new Scene(grid, 600, 600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}


