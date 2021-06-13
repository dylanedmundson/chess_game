package gui;

import entities.Entity;

import java.awt.*;

//TODO: implement poppup for pawn upgrade
public class PawnUpgradeGUI {
    private byte color;
    private boolean show = false;
    //create flag method for showing popup

    //create render method for call in paint components if upgrade is happening

    //implement either with key listeners or mouse listener and lock screen so now more clicking can happen
    //except on the popup
    public void render(Graphics g) {
        if (color == Entity.WHITE) {
            //render white
        } else {
            //render black
        }
    }

    // public Entity getChoice(byte color) {
    //     show = true;
    //     this.color = color;
    //     // while (//click hasn't happend') {
    //     //     //handle click listening in here
    //     // }
    //     show = false
    // }
}
