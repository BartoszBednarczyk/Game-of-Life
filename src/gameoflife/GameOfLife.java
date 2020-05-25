    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife;

import java.awt.Color;

/**
 * Klasa tworząca GameOfLifeJF
 * @author Bartosz Bednarczyk
 */
public class GameOfLife {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
       
        GameOfLifeJF life = new GameOfLifeJF();
        life.getContentPane().setBackground(Color.white);
        life.setLocationRelativeTo(null);
        life.show();
       
        
        
    }
    
}
