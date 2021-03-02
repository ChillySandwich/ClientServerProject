import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

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

public class Client extends Application {

	Scene sceneOne;
	Scene sceneTwo;
	private String globalUsername;

	//Items for HBox1
	Text usernameText = new Text("Username");
	Text password = new Text("Password");
	TextField usernameField = new TextField();
	TextField passwordField = new TextField();

	//Items for HBox2
	Text IPLabel = new Text("Server IP Address");
	Text portLabel = new Text("Port");
	static TextField IPField = new TextField();
	static TextField portField = new TextField();
	Button loginBtn = new Button ("Login");

	//Items for HBox3
	Text taskLabel = new Text("Enter Task");
	TextField taskField = new TextField();
	Button addTaskbtn = new Button ("Add Task");
	Button displayTaskbtn = new Button ("Display All Tasks");

	BorderPane root = new BorderPane();

	private TableView<MyRecord> table = new TableView();

	BufferedReader br;
	PrintWriter pr;

	@Override
	public void start(Stage primaryStage) {
		try {

			HBox Hbox1Sc1 = new HBox(usernameText, usernameField);
			Hbox1Sc1.setPadding(new Insets(15, 40, 15, 40));
			Hbox1Sc1.setSpacing(40);

			HBox Hbox2Sc1 = new HBox(password, passwordField);
			Hbox2Sc1.setPadding(new Insets(15, 40, 15, 40));
			Hbox2Sc1.setSpacing(40);

			//Laying out Hbox2
			HBox Hbox3Sc1 = new HBox(IPLabel, IPField, portLabel, portField, loginBtn);
			Hbox3Sc1.setPadding(new Insets(15, 40, 15, 40));
			Hbox3Sc1.setSpacing(40);

			VBox Vbox1Sc1 = new VBox(Hbox1Sc1, Hbox2Sc1, Hbox3Sc1);


			//Laying out Hbox3
			HBox Hbox3 = new HBox(taskLabel, taskField, addTaskbtn, displayTaskbtn);
			taskField.setPrefWidth(300);
			Hbox3.setPadding(new Insets(15, 40, 15, 40));
			Hbox3.setSpacing(40);


			//Table with Username and Task

			TableColumn TSColumn = new TableColumn("Task");
			TSColumn.setCellValueFactory(new PropertyValueFactory<>("task"));

			table.getColumns().addAll(TSColumn);

			//Laying out Vbox
			VBox Vbox2 = new VBox(Hbox3, table);

			//Disable connect if fields are empty
			// connectBtn.disableProperty().bind(
			//    Bindings.isEmpty(usernameField.textProperty())
			//    .or(Bindings.isEmpty(passwordField.textProperty())
			//    .or(Bindings.isEmpty(IPField.textProperty())
			//    .or(Bindings.isEmpty(portField.textProperty()))))
			// );
			//
			// IPField.disableProperty().bind(
			// Bindings.isEmpty(passwordField.textProperty())
			// .or(Bindings.isEmpty(usernameField.textProperty()))
			// );
			//
			// portField.disableProperty().bind(
			// Bindings.isEmpty(passwordField.textProperty())
			// .or(Bindings.isEmpty(usernameField.textProperty()))
			// );


			// loginBtn.setOnAction(e -> submitUser(passwordField.getText(), usernameField.getText()));

			loginBtn.setOnAction(e -> login(primaryStage, usernameField.getText(), passwordField.getText()));

			addTaskbtn.setOnAction(e -> addTask(taskField.getText(),usernameField.getText()));

			displayTaskbtn.setOnAction(e -> displayTasks());


			sceneOne = new Scene(Vbox1Sc1);
			sceneTwo = new Scene(Vbox2,800,500);


			primaryStage.setScene(sceneOne);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void connect() {
		Socket s;
		try {
			String IP = IPField.getText();
			int port = Integer.valueOf(portField.getText());
			s = new Socket(IP, port);

			pr = new PrintWriter(s.getOutputStream());


			InputStreamReader in = new InputStreamReader(s.getInputStream());
			br = new BufferedReader(in);


			if (s.isConnected()) {
				System.out.println("Connected!");
				System.out.println(s.getInetAddress());
			}
			else
				System.out.println("Not Connected!");


		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}


	public void displayTasks() {

		connect();
		try {
			String userPass = ("GETTASKS:" + globalUsername);
			
			//sends username to server
			pr.println(userPass);
			pr.flush();
			
			//reads data from server
            StringTokenizer data = new StringTokenizer(br.readLine(), "|");
            table.getItems().clear();
            while (data.hasMoreTokens()) {
                String task = data.nextToken();
				MyRecord a = new MyRecord(task);
				table.getItems().add(a);
            }

		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	public void addTask(String task, String userID) {

		connect();
		try {
			String userPass = ("ADDTASKS:" + userID + ":" + task);

			pr.println(userPass);
			pr.flush();




		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	public void login(Stage stage, String username, String password) {

		connect();
		try {
			String userPass = ("LOGIN:" + username + ":" + password);
			globalUsername = username;	
			
//			Encryption encrypt = new Encryption();
//			String encryptUserPass = encrypt.convert(userPass);
			pr.println(userPass);
			pr.flush();

			String str = br.readLine();

			System.out.println(str);
			if (str.equals("0")) {
				System.out.println("Incorrect Login");
			}
			else {
				stage.setScene(sceneTwo);
			}


		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		launch();

	}
}