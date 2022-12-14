package com.uqac.controller;

import com.uqac.model.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * This controller is used to manage the main view and all threads
 */
public class MainWindowController implements Initializable {
    @FXML @Getter
    private GridPane gridPane;
    @FXML
    private Text evaluationText;
    @Getter @Setter
    private Board board;
    private AspiBot aspiBot;
    static volatile boolean exit = false;

    /**
     * This method is called by default when the view is loaded
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.board = new Board(5, 5);

        for(int i = 0; i < board.getWidth(); i++) {
            for(int j = 0; j < board.getHeight(); j++) {
                Tile tile = board.getTile(i, j);
                tile.setHeight(120);
                tile.setWidth(120);
                gridPane.add(board.getTile(i, j), i, j);
            }
        }

        this.aspiBot = new AspiBot(board);

        new Thread()
        {
            public void run()
            {
                super.run();
                while (!exit)
                {
                    try
                    {
                        if(aspiBot.getEffector().getIntentions().isEmpty())
                        {
                            aspiBot.getEffector().setIntentions(aspiBot.getDecision().chooseAlgorithm(aspiBot));
                        }
                        Thread.sleep(800);

                        aspiBot.getEffector().move(aspiBot);
                        aspiBot.getSensor().updateState();
                        if(aspiBot.getSensor().isClean())
                        {
                            aspiBot.getDecision().updateEvaluation(5);
                        }
                        evaluationText.setText(String.valueOf(aspiBot.getDecision().getEvaluation()));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }.start();
        //Create a new thread to generate dust and gems on the board
        new Thread() {
            @Override
            public void run() {
                super.run();
                while(!exit) {
                    Random r = new Random();
                    //The time between the items generation is not definitive, I think we have to discuss it. Now the max time is 10 seconds.
                    int time =  r.nextInt(11);
                    try {
                        Thread.sleep(time * 1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    aspiBot.getDecision().updateEvaluation(board.generateItems());
                }
            }
        }.start();

       /*new Thread() {
            @Override
            public void run() {
                super.run();
                while (!exit)
                {
                    try {
                        if (aspiBot.getEffector().getIntentions().isEmpty()) {
                            aspiBot.getDecision().setSensor(aspiBot.getSensor());
                            aspiBot.getEffector().setIntentions(aspiBot.getDecision().bidirectionnal_search(aspiBot));
                        }

                        aspiBot.getSensor().getTile().display();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
       */
    }


    /**
     * This function stops all threads
     */
    public void stop() {
        exit = true;
    }
}
