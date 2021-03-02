import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ServerMain extends Application {

	//Items for HBox1
	Text IPLabel = new Text("Server IP Address");
	Text portLabel = new Text("Port");
	TextField IPField = new TextField();
	TextField portField = new TextField();
	Button runServer = new Button ("Run Server");

	BorderPane root = new BorderPane();

	// TableView table = new TableView();
	ServerSocket listener;

	@Override
	public void start(Stage primaryStage) {
		try {

			//Laying out Hbox1
			HBox Hbox1 = new HBox(IPLabel, portLabel);
			Hbox1.setPadding(new Insets(15, 40, 15, 40));
			Hbox1.setSpacing(40);

			//Laying out Hbox2
			HBox Hbox2 = new HBox(IPLabel, IPField, portLabel, portField, runServer);
			Hbox2.setPadding(new Insets(15, 40, 15, 40));
			Hbox2.setSpacing(40);


			//Laying out Vbox
			VBox Vbox1 = new VBox(Hbox1, Hbox2);

			runServer.setOnAction((event) -> connection());

			Scene scene = new Scene(Vbox1,800,200);
			primaryStage.setScene(scene);
			primaryStage.show();


		} catch(Exception e) {
			e.printStackTrace();
		}

	}

	public void connection() {
		//new Server(IPField.getText(), Integer.valueOf(portField.getText())).start();;
		new ServerMainThread(Integer.valueOf(portField.getText()), IPField.getText()).start();
	}
	
	public static void main(String[] args) {
		launch();
	}
}