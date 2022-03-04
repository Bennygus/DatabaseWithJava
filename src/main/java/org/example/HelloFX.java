package org.example;

import javafx.application.Application;


import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javafx.stage.Stage;
import org.example.entity.Song;
import org.example.service.SongService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class HelloFX extends Application {

    private ObservableList<Song> observableList;
    private ListView<Song> listView;

    ExecutorService executors = Executors.newSingleThreadExecutor();
    private SongService songService;
    private TextField songName;
    private TextField artistName;
    private Button addButton;
    private Button deleteButton;

    @Override
    public void stop() throws Exception {
        super.stop();
        executors.shutdown();
    }


    @Override
    public void start(Stage stage) throws Exception {
        songService = new SongService();

        Label labelForSongName = new Label("_Name:");
        songName = new TextField();
        labelForSongName.setMnemonicParsing(true);
        labelForSongName.setLabelFor(songName);

         Label labelForArtistName = new Label("_Artist:");
        artistName = new TextField();
        labelForArtistName.setMnemonicParsing(true);
        labelForSongName.setLabelFor(artistName);


        addButton = new Button();
        addButton.setText("Add");
        addButton.setDisable(true);
        addButton.setOnAction(actionEvent -> createNewSong());

        //Connect status of text in textfields to add button disable.
        BooleanBinding booleanBinding = Bindings.or(songName.textProperty().isEmpty(),
                                                    artistName.textProperty().isEmpty());


        addButton.disableProperty().bind(booleanBinding);


        deleteButton = new Button();
        deleteButton.setText("Delete");
        deleteButton.setOnAction(actionEvent -> deleteSelectedSong());

        listView = new ListView<>();
        var songs = songService.queryForSongs();
        observableList = FXCollections.observableList(songs);
        listView.setItems(observableList);

        Slider slider = new Slider(0, 360, 10.0);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(1.0f);
        slider.setBlockIncrement(1.0f);

        deleteButton.rotateProperty().bind(slider.valueProperty());


        GridPane gridPane = new GridPane();

        HBox root = new HBox();
        root.getChildren().add(gridPane);
        root.getChildren().add(listView);

        gridPane.setVgap(10.0);
        gridPane.setHgap(5.0);


        gridPane.addRow(0,labelForSongName,songName);
        gridPane.addRow(1, labelForArtistName, artistName);
        gridPane.addRow(2, addButton, deleteButton);
        gridPane.add(slider,0,3,2,1);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void deleteSelectedSong() {
        var song = listView.getSelectionModel().getSelectedItem();
        if( song != null) {
            songService.removeSong(song);
            observableList.clear();
            observableList.addAll(songService.queryForSongs());
        }
    }

    private void createNewSong() {
        //skapa nytt Song objekt med data från textfälten
        Song song = new Song();
        song.setName(songName.getText());
        song.setArtist(artistName.getText());
        // gor textfaltet tomma efter inlagg
        songName.setText("");
        artistName.setText("");


        executors.submit(() -> {

            try {
                //long running task, network not responding
                Thread.sleep(5000);
                //Spara i databasen
                songService.saveSong(song);

            } catch (InterruptedException e) {
                e.printStackTrace();

            }
            Platform.runLater(() -> {

                // kör med platform för att uppdatera
                //Uppdatera listView från Databasen
                observableList.clear();
                observableList.addAll(songService.queryForSongs());

            });




            });





    }



    }
