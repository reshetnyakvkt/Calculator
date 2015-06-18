package week8.lesson2.Task1;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CalcInterface extends Application {
	private static ArrayList<Button> buttons = new ArrayList<Button>();
	private static Character[] charArray = new Character[] { '0', '1', '2',
	        '3', '4', '5', '6', '7', '8', '9', '+', '-', '*', '/', '=' };
	private static TextField textField = new TextField();
	

	private AbstractProcessor processor = new MyProcessor();
	private Calc calc = new Calc(processor);

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		stage.setTitle("Калькулятор");
		stage.setScene(createScene());
		setEvents();
		stage.show();
	}

	public void initButtons() {
		for (int i = 0; i < charArray.length; i++) {
			buttons.add(new Button(Character.toString(charArray[i])));
		}
	}

	public Scene createScene() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_LEFT);
		grid.setHgap(20);
		grid.setVgap(20);
		grid.setPadding(new Insets(10, 10, 10, 10));

		initButtons();
		
		grid.add((Button) buttons.get(0), 0, 5);
		grid.add((Button) buttons.get(1), 0, 2);
		grid.add((Button) buttons.get(2), 1, 2);
		grid.add((Button) buttons.get(3), 2, 2);
		grid.add((Button) buttons.get(4), 0, 3);
		grid.add((Button) buttons.get(5), 1, 3);
		grid.add((Button) buttons.get(6), 2, 3);
		grid.add((Button) buttons.get(7), 0, 4);
		grid.add((Button) buttons.get(8), 1, 4);
		grid.add((Button) buttons.get(9), 2, 4);
		grid.add((Button) buttons.get(10), 3, 2);
		grid.add((Button) buttons.get(11), 3, 3);
		grid.add((Button) buttons.get(12), 3, 4);
		grid.add((Button) buttons.get(13), 3, 5);		
		grid.add((Button) buttons.get(14), 1, 5, 2, 1);
		
		((Button)buttons.get(14)).setMinWidth(65);
		
		grid.add(textField, 0, 0, 4, 1);
		textField.setEditable(false);

		Scene scene = new Scene(grid, 250, 250);
		return scene;
	}

	class MyEvent<T> implements EventHandler {
		Character charBtn;

		@Override
		public void handle(Event arg0) {
			textField.setText(calc.calcSymv(charBtn));
			//System.out.println(calc.calcSymv(charBtn));
		}

		public MyEvent(Character charBtn) {
			this.charBtn = charBtn;
		}
	}

	public void setEvents() {
		for (int i = 0; i < buttons.size(); i++) {
			((Button) buttons.get(i)).setOnAction(new MyEvent<ActionEvent>(charArray[i]));

		}
	}

}