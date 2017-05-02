package com.feldim2425.optgen.ui2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

import com.feldim2425.optgen.OtpGen;
import com.feldim2425.optgen.logger.LoggerManager;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainUI {

	private static MainUI instance;

	private Stage stage;

	public static void init() {
		if (instance == null) {
			new MainUI();
		}
		else {
			throw new IllegalStateException("MainUI already initialized");
		}
	}

	private MainUI() {

		instance = this;
		try {
			// Stage
			URL fxml = this.getClass().getClassLoader()
					.getResource(OtpGen.RESOURCE_PATH + "/window/InstanceViewWindow.fxml");
			Parent root = FXMLLoader.load(fxml);
			stage = new Stage();
			// stage.getIcons().add(new Image(this.getClass()
			// .getResourceAsStream("/" + StreamChatRoBot.RESOURCE_PATH +
			// "/icons/windowicon.png")));
			stage.setOnCloseRequest((event) -> {
				event.consume();
				OtpGen.getInstance().exit(true);
			});
			stage.setTitle("StreamChat RoBot [Instances]");
			stage.setScene(new Scene(root, 480, 300));

		}
		catch (IOException e) {
			LoggerManager.getLogger().log(Level.SEVERE, "Couldn't load Window from fxml", e);
		}
	}

	public static class WindowController implements Initializable {

		@Override
		public void initialize(URL location, ResourceBundle resources) {

		}

	}
}
