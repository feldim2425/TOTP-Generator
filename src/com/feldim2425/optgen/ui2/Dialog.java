package com.feldim2425.optgen.ui2;

import java.util.concurrent.atomic.AtomicBoolean;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


public class Dialog {

	public static boolean showYesNo(String title, String question) {
		AtomicBoolean result = new AtomicBoolean(false);
		Stage st = new Stage();
		st.setTitle(title);

		Label lbl = new Label(question);
		lbl.setTextAlignment(TextAlignment.CENTER);
		lbl.setFont(Font.font("Verdana", 14));
		Button btnYes = new Button("Yes");
		Button btnNo = new Button("No");

		btnYes.setOnAction((event) -> {
			result.set(true);
			st.close();
		});
		btnYes.setMinWidth(100);
		btnYes.defaultButtonProperty().bind(btnYes.focusedProperty());
		btnNo.setOnAction((event) -> {
			result.set(false);
			st.close();
		});
		btnNo.setMinWidth(100);

		VBox vbox = new VBox();
		vbox.setPadding(new Insets(15, 12, 15, 12));
		vbox.getChildren().add(lbl);
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(20);

		HBox hbox = new HBox();
		hbox.setCenterShape(true);
		hbox.getChildren().add(btnYes);
		hbox.getChildren().add(btnNo);
		hbox.setSpacing(5);

		hbox.setAlignment(Pos.CENTER);
		vbox.getChildren().add(hbox);

		Scene sc = new Scene(vbox);
		st.setScene(sc);
		st.setResizable(false);
		st.showAndWait();
		return result.get();
	}
}
