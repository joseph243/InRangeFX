package application;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.animation.FadeTransition;

public class Main extends Application {

	public static Group root = new Group();
	public static Scene scene = new Scene(root, Color.GREEN);
	public static int lineY = 202;
	public static int lineXStart = 45;
	public static int lineXEnd = 1045;
	Game game = new Game();
	Text message = new Text("Guessing Game!  Enter a guess and click Submit.");
	TextField textField = new TextField();
	Button submit = new Button();
	Button reset = new Button("Reset");

	static void main(String[] args) {
		launch(args);
	}

	public static double toNumLine(int inInt)

	{
		return inInt * 10 + lineXStart;
	}

	public void addBox(int inCount) {
		inCount--;
		Rectangle rectangle = new Rectangle();
		rectangle.setX(lineXStart + (100 * inCount));
		rectangle.setY(lineY - 5);
		rectangle.setHeight(10);
		rectangle.setWidth(100);
		rectangle.setFill(Color.GOLD);
		root.getChildren().add(rectangle);
	}

	public void gameLoss() {
		Rectangle rectangle = new Rectangle();
		rectangle.setX(lineXStart);
		rectangle.setY(lineY - 10);
		rectangle.setHeight(20);
		rectangle.setWidth(1000);
		rectangle.setFill(Color.RED);
		message.setText("You lost!! Restart and try to guess in 10 or fewer tries!");
		root.getChildren().add(rectangle);
		root.getChildren().remove(textField);
		root.getChildren().remove(submit);
		showReset();
	}

	public void showReset() { // doesnt work
//		reset.setTranslateX(450);
//		reset.setTranslateY(250);
//		root.getChildren().add(reset);
	}
	
	public void gameReset() {
		root.getChildren().remove(reset);
		root.getChildren().add(textField);
		root.getChildren().add(submit);
	}

	public void celebrate() {
		for (int i = 1; i < 5; i++) {
			for (int j = 5; j > 4; j++) {
				Rectangle rectangle = new Rectangle();
				rectangle.setX(lineXStart + (j * i));
				rectangle.setY(lineY + (j * i));
				rectangle.setHeight(3);
				rectangle.setWidth(3);
				rectangle.setFill(Color.ROYALBLUE);
				root.getChildren().add(rectangle);
			}
		}
	}

	public void gameWin() {
		Rectangle rectangle = new Rectangle();
		rectangle.setX(lineXStart);
		rectangle.setY(lineY - 10);
		rectangle.setHeight(20);
		rectangle.setWidth(1000);
		rectangle.setFill(Color.YELLOW);
		FadeTransition anim = new FadeTransition();
		anim.setNode(rectangle);
		anim.setDuration(Duration.millis(300));
		anim.setFromValue(10);
		anim.setToValue(0.1);
		anim.setCycleCount(1000);
		anim.setAutoReverse(true);
		message.setText("YOU WON IN " + game.getTries() + " TRIES!!  GOOD JOB !!");
		root.getChildren().add(rectangle);
		root.getChildren().remove(textField);
		root.getChildren().remove(submit);
		anim.play();
		showReset();
	}

	public void gameTurn() {
		int value = -1;
		if (game.getTries() < 9) {
			try {
				value = Integer.parseInt(textField.getText());
				game.tryGuess(value);
				message.setText(game.getClue(value));
				addBox(game.getTries());
			} catch (Exception e) {
				message.setText("ERROR; YOU MUST TYPE NUMBERS ONLY!!!  GUESS AGAIN PLEASE.");
			}
		} else if (game.getTries() == 9) {
			try {
				value = Integer.parseInt(textField.getText());
				if (game.tryGuess(value)) {
					message.setText(game.getClue(value));
					addBox(game.getTries());
				} else {
					gameLoss();
				}

			} catch (Exception e) {
				message.setText("ERROR; YOU MUST TYPE NUMBERS ONLY!!!  GUESS AGAIN PLEASE.");
			}

		} else {
			gameLoss();
		}
		if (game.getWinner()) {
			gameWin();
		}
	}

	public void drawLine() {
		Line line = new Line();

		line.setStrokeWidth(5);
		line.setStartY(lineY);
		line.setEndY(lineY);
		line.setStartX(lineXStart);
		line.setEndX(lineXEnd);

		root.getChildren().add(line);

		for (int i = 0; i < 11; i++) {
			Line line2 = new Line();
			line2.setStrokeWidth(5);
			line2.setStartY(lineY - 5);
			line2.setEndY(lineY + 5);
			line2.setStartX(i * 100 + lineXStart);
			line2.setEndX(i * 100 + lineXStart);
			root.getChildren().add(line2);
		}

	}

	@Override
	public void start(Stage stage) throws Exception {
		Image icon = new Image("fox2.jpg");
		Text title = new Text("InRange II");

		drawLine();

		message.setY(150);
		message.setX(350);
		message.setFont(Font.font("Arial", 20));

		submit.setText("Submit");
		submit.setTranslateX(150);

		title.setY(50);
		title.setX(450);
		title.setFont(Font.font("ComicSans", 50));

		EventHandler<KeyEvent> enterHandler = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().toString().equals("ENTER")) {
					gameTurn();
					textField.clear();
				}
				event.consume();
			}
		};

		EventHandler<MouseEvent> clickHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getSource() == submit) {
					gameTurn();
					textField.clear();
				}
				if (event.getSource() == reset) {
					gameReset();
				}

				event.consume();
			}

		};

		submit.setOnMouseClicked(clickHandler);
		textField.setOnKeyPressed(enterHandler);

		root.getChildren().add(title);
		root.getChildren().add(textField);
		root.getChildren().add(submit);
		root.getChildren().add(message);

		stage.getIcons().add(icon);
		stage.setTitle("InRange II");
		stage.setHeight(400);
		stage.setWidth(1100);
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}

}
