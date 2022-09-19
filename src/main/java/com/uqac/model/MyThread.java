package com.uqac.model;

import com.uqac.AspiBotApplication;
import lombok.Getter;

import java.util.Random;

public abstract class MyThread implements Runnable{
    private Thread thread;
    private boolean running;
    @Getter
    private String name;

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

    public void runVacuumBoardUpdate(AspiBot aspibot, Board board) {
        running = true;
        while (running) {
            try {
                Thread.sleep(1000);
                aspibot.updateBoard(board);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

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

    public void runAspibot(AspiBot aspiBot, Board board) {
        running = true;
        while (running) {
            try {
                Thread.sleep(500);
                Random r = new Random();
                //Here is the line to choose the direction
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

    public void runItemsGeneration(Board board)
    {
        running = true;
        while (running) {
            try {
                Random r = new Random();
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
