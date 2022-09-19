package com.uqac.model;

import com.uqac.AspiBotApplication;
import lombok.Getter;

import java.util.Random;

public abstract class MyThread implements Runnable{
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
                aspibot.updateBoard(board);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //This function is used to update the visual board
    public void runUpdateBoard(AspiBotApplication aspiBotApplication, AspiBot aspiBot) {
        running = true;
        while (running) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    Tile tile = aspiBot.getBoard().getTile(i, j);
                    if(aspiBot.getXPosition() != i || aspiBot.getYPosition() != j) {
                        tile.setVacuum(false);
                    }else {
                        tile.setVacuum(true);
                    }

                    if (tile.isDust() && tile.isGem() && tile.isVacuum()) {
                        aspiBotApplication.drawVacuumDustGem(i, j);
                    } else if (tile.isDust() && tile.isVacuum() && !tile.isGem()) {
                        aspiBotApplication.drawVacuumDust(i, j);
                    } else if (tile.isGem() && tile.isVacuum() && !tile.isDust()) {
                        aspiBotApplication.drawVacuumGem(i, j);
                    } else if (tile.isDust() && tile.isGem() && !tile.isVacuum()) {
                        aspiBotApplication.drawDustGem(i, j);
                    } else if (tile.isDust() && !tile.isGem() && !tile.isVacuum()) {
                        aspiBotApplication.drawDust(i, j);
                    } else if (tile.isGem() && !tile.isDust() && !tile.isVacuum()) {
                        aspiBotApplication.drawGem(i, j);
                    } else if (tile.isVacuum() && !tile.isGem() && !tile.isDust()) {
                        aspiBotApplication.drawVacuum(i, j);
                    } else if (!tile.isVacuum() && !tile.isGem() && !tile.isDust()) {
                        aspiBotApplication.drawEmpty(i, j);
                    }
                }
            }
        }
    }

    //This function allow to move the vacuum and decide what's the next move. It updates the visual board and the board of the vacuum.
    public void runAspibot(AspiBot aspiBot, Board board) {
        running = true;
        while (running) {
            try {
                Thread.sleep(500);
                Random r = new Random();
                //Here is the line to choose the direction. It's not a definitive version, but it's a start.
                int g = r.nextInt(4);
                aspiBot.move(AspiBot.Direction.values()[g]);
                Tile tile = aspiBot.getBoard().getTile(aspiBot.getXPosition(), aspiBot.getYPosition());
                if (tile.isDust() && tile.isGem() && tile.isVacuum()) {
                    aspiBot.pickUpGem();
                    aspiBot.vacuumize();
                    board.getTile(aspiBot.getXPosition(), aspiBot.getYPosition()).setDust(false);
                    board.getTile(aspiBot.getXPosition(), aspiBot.getYPosition()).setGem(false);
                } else if (tile.isDust() && tile.isVacuum() && !tile.isGem()) {
                    aspiBot.vacuumize();
                    board.getTile(aspiBot.getXPosition(), aspiBot.getYPosition()).setDust(false);
                } else if (tile.isGem() && tile.isVacuum() && !tile.isDust()) {
                    aspiBot.pickUpGem();
                    board.getTile(aspiBot.getXPosition(), aspiBot.getYPosition()).setGem(false);
                }
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
                //The time between the items generation is not definitive, I think we have to discuss about it. Now the max time is 10 seconds.
                int time =  r.nextInt(11);
                Thread.sleep(time * 1000);
                System.out.println("Generate items");
                for(int i = 0; i < 5; i++) {
                    for(int j = 0; j < 5; j++) {
                        if(new Random().nextDouble() < 0.1) {
                            board.getTile(i, j).setDust(true);
                        } else if(new Random().nextDouble() < 0.05){
                            board.getTile(i, j).setGem(true);
                        }

                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
