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

    public void initialize() {

        txtFind.textProperty().addListener((ov,previous,current)->{
            findResults();

        });
        textEditor.textProperty().addListener((ov,previous,current)->{
            findResults();
            textChange();
        });
        txtReplace.textProperty().addListener((ov,previous,current)->{
            findResults();
        });
        Platform.runLater(() ->{
            textEditor.getScene().getWindow().setOnCloseRequest(windowEvent ->{
                mnClose.fire();
            });
        });
    }
    private void findResults(){
        textArea= textEditor.getText();
        textFind= txtFind.getText();
        count=0;

        arrayList.clear();
        if (textFind.isEmpty()){
            lblResult.setText("0 Results");
            textEditor.selectRange(0,0);
            return;
        }
        Pattern pattern = Pattern.compile(textFind);
        Matcher matcher = pattern.matcher(textArea);
        if (flag) {
            pattern = Pattern.compile(textFind.toLowerCase());
            matcher = pattern.matcher(textArea.toLowerCase());
        }
        while (matcher.find()){
            count++;
            arrayList.add(matcher.start());
            arrayList.add(matcher.end());
        }
        if (arrayList.isEmpty()) return;
        textEditor.selectRange(arrayList.get(0), arrayList.get(1));
        upCount=0;
        lblResult.setText((upCount/2+1)+"/"+count+" Results");
    }
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
    void mnSaveOnAction(ActionEvent event) throws IOException {
        if (file==null){
            mnSaveAsOnAction(event);
        }else {
            saveFile();
        }
        if (file!=null) {
            saveKey=true;
            mnSave.setDisable(saveKey);
        }
    }
    @FXML
    public void mnSaveAsOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) textEditor.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save As a text file");
        file = fileChooser.showSaveDialog(textEditor.getScene().getWindow());
        if (file==null) return;
        FileOutputStream fos = new FileOutputStream(file);
        String text = textEditor.getText();
        byte[] bytes= text.getBytes();
        fos.write(bytes);
        fos.close();
        textEditor.setText(new String(bytes));
        String [] array =file.getPath().split("/");
        stage.setTitle(array[array.length-1]);

    }
    void saveFile() throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        String text = textEditor.getText();
        byte[] bytes= text.getBytes();
        fos.write(bytes);
        fos.close();
        textEditor.setText(new String(bytes));
        Stage stage = (Stage) textEditor.getScene().getWindow();
        stage.setTitle(stage.getTitle().substring(1,stage.getTitle().length()));
    }
    @FXML
    public void rootOnDragOver(DragEvent dragEvent) {
        dragEvent.acceptTransferModes(TransferMode.ANY);
    }
    @FXML
    public void rootOnDragDropped(DragEvent dragEvent) throws IOException {
        File droppedFile = dragEvent.getDragboard().getFiles().get(0);
        FileInputStream fis = new FileInputStream(droppedFile);
        byte[] bytes =fis.readAllBytes();
        fis.close();
        textEditor.setText(new String(bytes));
        Stage stage = (Stage) textEditor.getScene().getWindow();
        stage.setTitle(droppedFile.getName());
        System.out.println( droppedFile.getName());
    }
    @FXML
    public void textChange(){
        Stage stage = (Stage) textEditor.getScene().getWindow();
        if (!stage.getTitle().contains("*")) stage.setTitle("*"+ stage.getTitle());
        saveKey=false;
        mnSave.setDisable(saveKey);
        if (!stage.getTitle().contains("*")) stage.setTitle("*"+ stage.getTitle());
        mnSave.setDisable(saveKey);
    }

}
