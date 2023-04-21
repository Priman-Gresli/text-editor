package lk.ijse.dep10.app.controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditorController {

    @FXML
    private Button btnDown;

    @FXML
    private Button btnReplace;

    @FXML
    private Button btnReplaceAll;

    @FXML
    private Button btnUp;

    @FXML
    private CheckBox chkMatch;

    @FXML
    private Label lblResult;


    @FXML
    private TextField txtFind;

    @FXML
    private TextField txtReplace;
    String textArea;
    String textFind;
    String textReplaceAll;
    String textReplace;
    int count=0;

    int upCount=0;
    boolean flag=false;
    @FXML
    private MenuItem mnAbout;

    @FXML
    private MenuItem mnClose;

    @FXML
    private MenuItem mnNew;

    @FXML
    private MenuItem mnOpen;

    @FXML
    private MenuItem mnPrint;

    @FXML
    private MenuItem mnSave;
    private File file;
    @FXML
    private TextArea textEditor;
    @FXML
    private MenuItem mnSaveAs;
    private Boolean saveKey = false;

    private ArrayList<Integer> arrayList = new ArrayList<>();

    @FXML
    void btnDownOnAction(ActionEvent event) {

    }

    @FXML
    void btnReplaceAllOnAction(ActionEvent event) {

    }

    @FXML
    void btnReplaceOnAction(ActionEvent event) {

    }

    @FXML
    void btnUPOnAction(ActionEvent event) {

    }

    @FXML
    void chkMatchOnAction(ActionEvent event) {

    }

    @FXML
    void mnAboutOnAction(ActionEvent event) throws IOException {
        Stage stageAbout = new Stage();
        stageAbout.initModality(Modality.WINDOW_MODAL);
        stageAbout.initOwner(textEditor.getScene().getWindow());
        URL fxmlFile = this.getClass().getResource("/view/About.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlFile);
        AnchorPane root = fxmlLoader.load();

        Scene scene = new Scene(root);
        stageAbout.setScene(scene);

        stageAbout.setTitle("About Text Editor");
        stageAbout.show();
        stageAbout.centerOnScreen();
    }
    @FXML
    void mnCloseOnAction(ActionEvent event) throws IOException {
        Optional<ButtonType> optButton = new Alert(Alert.AlertType.CONFIRMATION,
                "Save changes to document before closing?",
                ButtonType.YES, ButtonType.NO,ButtonType.CANCEL).showAndWait();
        if (optButton.isEmpty() || optButton.get() ==  ButtonType.YES) {
            mnSaveOnAction(event);
            if (file==null) return;
            Stage stage = (Stage) textEditor.getScene().getWindow();
            stage.close();
        }
        if (optButton.isEmpty() || optButton.get() ==  ButtonType.NO) {
            Stage stage = (Stage) textEditor.getScene().getWindow();
            stage.close();
        }

    }

    @FXML
    void mnNewOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) textEditor.getScene().getWindow();
        if (stage.getTitle().contains("*")) {
            Optional<ButtonType> optButton = new Alert(Alert.AlertType.CONFIRMATION,
                    "Save changes to document before closing?",
                    ButtonType.YES, ButtonType.NO,ButtonType.CANCEL).showAndWait();
            if (optButton.isEmpty() || optButton.get() == ButtonType.YES) {
                mnSaveOnAction(event);
                stage.setTitle("Text Editor");
                file=null;
                textEditor.setText(new String());
            } else if (optButton.get() == ButtonType.NO) {
                file=null;
                textEditor.setText(new String());
                stage.setTitle("Text Editor");
            } else return;
        }

    }
    @FXML
    void mnOpenOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) textEditor.getScene().getWindow();
        if (stage.getTitle().contains("*")) {
            Optional<ButtonType> optButton = new Alert(Alert.AlertType.CONFIRMATION,
                    "Save changes to document before closing?",
                    ButtonType.YES, ButtonType.NO, ButtonType.CANCEL).showAndWait();
            if (optButton.isEmpty() || optButton.get() == ButtonType.YES) {
                mnSaveOnAction(event);
                if (file==null) return;
            }else return;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a text file");
        file = fileChooser.showOpenDialog(textEditor.getScene().getWindow());
        if (file==null) return;
        String [] array =file.getPath().split("/");
        System.out.println(array[array.length-1]);
        stage.setTitle(array[array.length-1]);

        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = fis.readAllBytes();
        fis.close();
        textEditor.setText(new String(bytes));

    }

    @FXML
    void mnPrintOnAction(ActionEvent event) {

    }

    @FXML
    void mnSaveAsOnAction(ActionEvent event) {

    }

    @FXML
    void mnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void rootOnDragDropped(DragEvent event) {

    }

    @FXML
    void rootOnDragOver(DragEvent event) {

    }

}
