import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameofLife extends Application{
	/*
	public static void main(String [] args){
		Petridish petridish = new Petridish();
		Scanner sc = new Scanner(System.in);
		String ans = new String();

		System.out.println("Enter a number for the Seed value. (0 - 40) ");
		try {
		ans = sc.nextLine();
		petridish.startLife(Integer.parseInt(ans));
			petridish.iterateLife();
		} catch (Exception e) {
			e.printStackTrace();
		}
		sc.close();
	}
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		/* TODO:
		 *		create logic for entering and input checking seed value
		 *			- create function for incorrect values
		 *			- create function for correct value and moving on to next part
		 *		create next part representations on screen
		 *			- integrate the logic for the graphic aspect of the petridish 		
		 * 		hide most of these lines of code in functions to make it more readable
		 */
		
		BorderPane border = new BorderPane();
		Text scenetitle = new Text("Enter a number for the Seed value.");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		border.setTop(scenetitle);
		
		HBox hbox = new HBox();
		
		final Text seedValue = new Text("Seed value:");
		seedValue.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
		hbox.getChildren().add(seedValue);
		
		TextField seedTextField = new TextField();
		seedTextField.setText("0-40");
		hbox.getChildren().add(seedTextField);
		
		final Text actionText = new Text();
		actionText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		
		Button userInput = new Button("Enter Value");
		userInput.setOnAction(new EventHandler <ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				actionText.setFill(Color.FIREBRICK);
				actionText.setText(seedTextField.getText());
			}
		});
		
		hbox.getChildren().add(userInput);
		
		border.setCenter(hbox);
		border.setBottom(actionText);
		
		Scene scene = new Scene(border, 600, 600);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}