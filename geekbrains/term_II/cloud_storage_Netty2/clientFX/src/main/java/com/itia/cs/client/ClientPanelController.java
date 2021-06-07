package com.itia.cs.client;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ClientPanelController implements Initializable {

    @FXML
    TableView<FileListTable> filesListArea;

    @FXML
    TextField devPathField;

    @FXML
    ComboBox<String> devBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //  строим таблицу со списком файлов
        TableColumn<FileListTable, String> fileTypeColumn = new TableColumn<>("Тип");
        fileTypeColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFileType().getName()));
        fileTypeColumn.setPrefWidth(24);

        TableColumn<FileListTable, String> fileNameColumn = new TableColumn<>("Наименование");
        fileNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFileName()));
        fileNameColumn.setPrefWidth(300);

        TableColumn<FileListTable, Long> fileSizeColumn = new TableColumn<>("Размер");
        fileSizeColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getFileSize()));
        fileSizeColumn.setPrefWidth(150);
        fileSizeColumn.setCellFactory(c -> {
            return new TableCell<FileListTable, Long>(){
                @Override
                protected void updateItem(Long item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
//                        String text = item.toString();
                        String text = String.format("%,d byte", item); // разделяет строку по 3 знака и ставит пробел
                        if (item == -1L){
                            text = "[DIR]";
                        }
                        setText(text);
                    }
                }
            };
        });

        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"); // форматируем дату для читаемого вывода перед выводом в таблицу
        TableColumn<FileListTable, String> fileDateColumn = new TableColumn<>("Дата изменения");
        fileDateColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFileLastModified().format(formatDate)));
        fileDateColumn.setPrefWidth(130);

        filesListArea.getColumns().addAll(fileTypeColumn, fileNameColumn, fileSizeColumn, fileDateColumn);
        filesListArea.getSortOrder().add(fileTypeColumn);

        // получение списка устройств для ComboBox
        devBox.getItems().clear();
        for (Path path : FileSystems.getDefault().getRootDirectories()){
            devBox.getItems().add(path.toString());
        }
        devBox.getSelectionModel().select(0); // выводим девайс компьютера

        filesListArea.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2){
                    Path path = Paths.get(devPathField.getText()).resolve(filesListArea.getSelectionModel().getSelectedItem().getFileName());
                    if (Files.isDirectory(path)){
                        updateFileList(path);
                    }
                }
            }
        });

        updateFileList(Paths.get(".","clientFX/test_folder"));
    }

    public void updateFileList(Path path){
        try {
            devPathField.setText(path.normalize().toAbsolutePath().toString());
//            devPathField.setText(path.normalize().toString());
            filesListArea.getItems().clear();
            filesListArea.getItems().addAll(Files.list(path).map(FileListTable::new).collect(Collectors.toList()));
            filesListArea.sort();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Не удалось обновить список файлов и папок.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void goUpPath(ActionEvent actionEvent) {
        Path upperPath = Paths.get(devPathField.getText()).getParent();
        if (upperPath != null){
            updateFileList(upperPath);
        }
    }

    public void goDev(ActionEvent actionEvent) {
        ComboBox<String> select = (ComboBox<String>) actionEvent.getSource();
        updateFileList(Paths.get(select.getSelectionModel().getSelectedItem()));
    }

    public String getSelectedFileName(){
        if (!filesListArea.isFocused()){
            return null;
        }
        return filesListArea.getSelectionModel().getSelectedItem().getFileName();
    }

    public String getCurrentPath(){
        return devPathField.getText();
    }

}
