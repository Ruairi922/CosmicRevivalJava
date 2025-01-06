package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    //DEBUGGING
    boolean checkDrawTime = false;

    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }
    @Override
    public void keyTyped(KeyEvent e) {
        // we do not use this method at all we only use the KeyPressed
        // and KeyReleased methods
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        // title state
        if(gp.gameState == gp.titleState){
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0){
                    gp.ui.commandNum = 2;
                }
            }
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 2){
                    gp.ui.commandNum = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER){
                if(gp.ui.commandNum == 0){
                    gp.gameState = gp.playState;
                }
                if(gp.ui.commandNum == 1){
                    gp.gameState = gp.controlsState;
                }
                if(gp.ui.commandNum == 2){
                    System.exit(0);
                }
            }
        }
        //play state
        else if(gp.gameState == gp.playState) {
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
                upPressed = true;
            }
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
                downPressed = true;
            }
            if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
                leftPressed = true;
            }
            if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
                rightPressed = true;
            }
            if(code == KeyEvent.VK_P){
                gp.gameState = gp.pauseState;

            }
            if(code == KeyEvent.VK_C){
                gp.gameState = gp.characterState;
            }
            if(code == KeyEvent.VK_ENTER){
                enterPressed = true;
            }
            //DEUBUGGING
            if(code == KeyEvent.VK_T){
                if(checkDrawTime == false){
                    checkDrawTime = true;
                }
                else if(checkDrawTime == true){
                    checkDrawTime = false;
                }
            }
        }
        //Pause state
        else if(gp.gameState == gp.pauseState){
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0){
                    gp.ui.commandNum = 2;
                }
            }
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 2){
                    gp.ui.commandNum = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER){
                if(gp.ui.commandNum == 0){
                    gp.gameState = gp.playState;
                }
                if(gp.ui.commandNum == 1){
                    gp.gameState = gp.controlsState;
                }
                if(gp.ui.commandNum == 2){
                    gp.gameState = gp.titleState;
                }
            }

        }
        //Dialogue state
        else if(gp.gameState == gp.dialogueSate){
            if(code == KeyEvent.VK_ENTER){
                gp.gameState = gp.playState;

            }
        }

        //Character state
        else if(gp.gameState == gp.characterState){
            if(code == KeyEvent.VK_C){
                gp.gameState = gp.playState;
            }
        }

        //Game over state
        else if(gp.gameState == gp.gameOverState){
            gameOverState(code);
        }

        //Controller state
        else if(gp.gameState == gp.controlsState){
            if(code == KeyEvent.VK_ENTER){
                gp.gameState = gp.pauseState;
            }
        }

        //win state
        else if(gp.gameState == gp.winState){
            if(code == KeyEvent.VK_ENTER){
                gp.gameState = gp.titleState;
                //gp.restart();
            }
        }



    }
    public void gameOverState(int code){
        if(code == KeyEvent.VK_W){
            gp.ui.commandNum--;
            if(gp.ui.commandNum <0){
                gp.ui.commandNum = 1;
            }
            gp.playSE(9);
        }

        if(code == KeyEvent.VK_S){
            gp.ui.commandNum++;
            if(gp.ui.commandNum > 1){
                gp.ui.commandNum = 0;
            }
            gp.playSE(9);
        }
        if(code == KeyEvent.VK_ENTER){
            if(gp.ui.commandNum == 0){
                gp.gameState = gp.playState;
                gp.retry();
            }
            else if(gp.ui.commandNum == 1){
                gp.gameState = gp.titleState;
                gp.restart();
            }
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
            upPressed = false;
        }
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
            downPressed = false;
        }
        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
            rightPressed = false;
        }
        if(code == KeyEvent.VK_ENTER){
            enterPressed = false;
        }


    }
}

