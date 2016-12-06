package ua.nastka_khmelovska;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.text.ParseException;
import java.util.Arrays;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application{
	
	//String keys = "Bar12345Bar12345"; // 128 bit key
    String initVector = "RandomInitVector"; // 16 bytes IV
	
	private String encrypted;
	
	GridPane root = new GridPane();
	Scene scene = new Scene(root, 600, 350);
	Stage primaryStage = new Stage();

	

	public static void main(String[] args) throws Exception {

		
		launch(args);
		
		
		  }
		
		
	

	@SuppressWarnings("restriction")
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		
		
		primaryStage.setTitle("Protection");
		primaryStage.setScene(scene);
		root.setStyle("-fx-background-color: lightgray;");
		primaryStage.show();
		
		//настройки корня
		root.setAlignment(Pos.TOP_LEFT);
		root.setHgap(1);
		root.setVgap(1);
		root.setPadding(new Insets (10,10,10,10));
		
		ColumnConstraints first = new ColumnConstraints();
		first.setPercentWidth(0);
		ColumnConstraints second = new ColumnConstraints();
		second.setPercentWidth(100);
		root.getColumnConstraints().addAll(first, second);
		
		TextField text = new TextField();
		text.setPromptText("Enter text for encode");
		text.setFont(Font.font("Century Gothic", FontWeight.NORMAL, 14));
		root.add(text, 1, 1);
		String forEncrypt = text.getText();
		
		
		
		
		
		final ChoiceBox method = new ChoiceBox(FXCollections.observableArrayList(
				"AES", "Code chars"));
		method.setTooltip(new Tooltip("Select method"));
		root.add(method, 1, 4);
		
		method.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){

			public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
				System.out.println(newValue);
				if(newValue.intValue() == 0){
					// AES
					TextField titleKey = new TextField();
					titleKey.setPromptText("Enter the key (16 symbols):");
					titleKey.setFont(Font.font("Century Gothic", FontWeight.NORMAL, 14));
					root.add(titleKey, 1, 2);
					
					
					Button encode = new Button();
					encode.setText("Encode");
					root.add(encode, 1, 5);
					
					encode.setOnMouseClicked(event -> {
						try {
							String keys = new String(titleKey.getText());
							String forEncrypt = text.getText();
							Ecryptor e = new Ecryptor();
							String encrypted = new String(e.encrypt(keys, initVector, forEncrypt));
							TextField encoded = new TextField(String.valueOf(encrypted));
							encoded.setFont(Font.font("Century Gothic", FontWeight.NORMAL, 14));
							root.add(encoded, 1, 6);
						} catch (Exception e) {
							
							e.printStackTrace();
						}
						   
						
					});
				
					
					
				}
				if (newValue.intValue() == 1){
					//CODE CHARS
					Button encode = new Button();
					encode.setText("Encode");
					root.add(encode, 1, 5);
					encode.setOnMouseClicked(event -> {
					CodeChars c = new CodeChars();
					
					try {
						System.out.println("For encrypt:" + text.getText());
						String enc = CodeChars.encode(text.getText());
						System.out.println("Encrypted by rsa:" + enc);
						TextField encoded = new TextField(enc);
						encoded.setFont(Font.font("Century Gothic", FontWeight.NORMAL, 14));
						root.add(encoded, 1, 6); 
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}});
				}
			}
		});
		
		
		
		
		Separator sr = new Separator();
		sr.setPrefHeight(10);
		sr.setMinHeight(10);
		root.add(sr, 1, 7);
		
		final TextField textD = new TextField();
		textD.setPromptText("Enter text for decode");
		root.add(textD, 1, 8);
		
		
		
		
		
		final ChoiceBox methodD = new ChoiceBox(FXCollections.observableArrayList(
				"AES", "Code chars"));
		methodD.setTooltip(new Tooltip("Select method"));
		root.add(methodD, 1, 10);

		methodD.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){

			public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
				System.out.println(newValue);
				if(newValue.intValue() == 0){
					// AES
					TextField keyD = new TextField();
					keyD.setPromptText("Enter key (16 symbols):");
					root.add(keyD, 1, 9);
					Button decode = new Button();
					decode.setText("Decode");
					root.add(decode, 1, 11);
					
					decode.setOnMouseClicked(event -> {
						
						try {
						
							String forDecrypt = textD.getText();
							String keys = new String(keyD.getText());
							Ecryptor e = new Ecryptor();
							String decrypted = new String(e.decrypt(keys, initVector, forDecrypt));
							
							TextField decoded = new TextField(decrypted);
							root.add(decoded, 1, 13);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					});
				
					
					
					
					
				}
				if (newValue.intValue() == 1){
					// Code chars
					Button decode = new Button();
					decode.setText("Decode");
					root.add(decode, 1, 12);
					decode.setOnMouseClicked(event -> {
					CodeChars c = new CodeChars();
					String forDecrypt = textD.getText();
					String dec;
					try {
						dec = c.decode(forDecrypt);
						TextField decoded = new TextField(dec);
						decoded.setFont(Font.font("Century Gothic", FontWeight.NORMAL, 14));
						root.add(decoded, 1, 13); 
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}});
					
					
				}
			}
		});
		
		
		
		
			
		
	}

}
