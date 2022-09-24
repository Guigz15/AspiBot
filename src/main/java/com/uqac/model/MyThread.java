package com.uqac.model;

import com.uqac.AspiBotApplication;
import com.uqac.controller.MainWindowController;
import javafx.scene.paint.Color;
import lombok.Getter;

import java.util.Random;

public abstract class MyThread implements Runnable {
    private Thread thread;
    private boolean running;
    @Getter
    private final String name;

    public void stop() {
        running = false;
        System.out.println("Thread " + name + " stopped");
    }

    public MyThread(String name) {
        running = true;
        thread = new Thread(this);
        this.name = name;
        thread.start();
    }

    //This function synchronize the vacuum's board and the board where new items (dust and gem) are generated
    public void runVacuumBoardUpdate(AspiBot aspibot, Board board) {
        running = true;
        while (running) {
            try {
                //This time needs to be changed with the learning
                Thread.sleep(1000);
                aspibot.getSensor().updateBoard(board);
            } catch (InterruptedException | NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
    public void runTest(AspiBot aspiBot, MainWindowController mainWindowController, Board board) {
        running = true;
        while(running) {
            try {
                Thread.sleep(1000);
                Random random = new Random();
                aspiBot.getEffector().move(aspiBot, Effector.Direction.values()[random.nextInt(Effector.Direction.values().length)]);
                mainWindowController.drawVacuum(aspiBot.getSensor().getXPosition(), aspiBot.getSensor().getYPosition());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    //This function is used to update the visual board
    public void runUpdateBoard(MainWindowController mainWindowController, AspiBot aspiBot) {
        running = true;
        while (running) {
            try {
                Thread.sleep(1);
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        Tile tile = aspiBot.getSensor().getBoard().getTile(i, j);
                        if(aspiBot.getSensor().getXPosition() != i || aspiBot.getSensor().getYPosition() != j) {
                            tile.setVacuum(false);
                        }else {
                            tile.setVacuum(true);
                        }

                        if (tile.isDust() && tile.isGem() && tile.isVacuum()) {
                            tile.setFill(null);
                            mainWindowController.drawVacuumDustGem(i, j);
                        } else if (tile.isDust() && tile.isVacuum() && !tile.isGem()) {
                            tile.setFill(null);
                            mainWindowController.drawVacuumDust(i, j);
                        } else if (tile.isGem() && tile.isVacuum() && !tile.isDust()) {
                            tile.setFill(null);
                            mainWindowController.drawVacuumGem(i, j);
                        } else if (tile.isDust() && tile.isGem() && !tile.isVacuum()) {
                            tile.setFill(null);
                            mainWindowController.drawDustGem(i, j);
                        } else if (tile.isDust() && !tile.isGem() && !tile.isVacuum()) {
                            tile.setFill(null);
                            mainWindowController.drawDust(i, j);
                        } else if (tile.isGem() && !tile.isDust() && !tile.isVacuum()) {
                            tile.setFill(null);
                            mainWindowController.drawGem(i, j);
                        } else if (tile.isVacuum() && !tile.isGem() && !tile.isDust()) {
                            tile.setFill(null);
                            mainWindowController.drawVacuum(i, j);
                        } else if (!tile.isVacuum() && !tile.isGem() && !tile.isDust()) {
                            tile.setFill(null);
                            mainWindowController.drawEmpty(i, j);
                        }
                    }
                }

            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //This function allow to move the vacuum and decide what's the next move. It updates the visual board and the board of the vacuum.
    public void runAspibot(AspiBot aspiBot, MainWindowController mainWindowController) {
        running = true;
        while(running) {
            try {
                Thread.sleep(1000);
                Random random = new Random();
                aspiBot.getEffector().move(aspiBot, Effector.Direction.values()[random.nextInt(Effector.Direction.values().length)]);
                mainWindowController.drawVacuum(aspiBot.getSensor().getXPosition(), aspiBot.getSensor().getYPosition());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //This function is used to generate new items (dust and gem) on the board
    public void runItemsGeneration(Board board)
    {
        running = true;
        while (running) {
            try {
                Random r = new Random();
                //The time between the items generation is not definitive, I think we have to discuss it. Now the max time is 10 seconds.
                int time =  r.nextInt(11);
                Thread.sleep(time * 1000);
                System.out.println("Generate items");
                board.generateItems();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
}
