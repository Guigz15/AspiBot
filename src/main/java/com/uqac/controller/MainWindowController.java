package com.uqac.controller;

import com.uqac.model.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    @FXML @Getter
    private GridPane gridPane;
    @Getter @Setter
    private Board board;
    private AspiBot aspiBot;
    static volatile boolean exit = false;

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

        //Create a new thread to generate dust and gems on the board
        new Thread() {
            @Override
            public void run() {
                super.run();
                while(!exit)
                    generateItems(board);
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                super.run();
                int i = 10;
                while(!exit) {
                    try {
                        if (i==20)
                        {
                            i=0;
                            aspiBot.getDecision().setSensor(aspiBot.getSensor());
                            aspiBot.getEffector().setIntentions(aspiBot.getDecision().bidirectionnal_search());
                        }
                        System.out.println(i);

                        Thread.sleep(1000);

                        aspiBot.getEffector().move(aspiBot);
                        System.out.println("position :");
                        aspiBot.getSensor().getTile().display();
                        i++;
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    /**
     * This function is used to generate new items (dust and gem) on the board
     * @param board where items are generated
     */
    private void generateItems(Board board) {
        try {
            Random r = new Random();
            //The time between the items generation is not definitive, I think we have to discuss it. Now the max time is 10 seconds.
            int time =  r.nextInt(11);
            Thread.sleep(time * 1000);
            board.generateItems();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function stops all threads
     */
    public void stop() {
        exit = true;
    }
}
