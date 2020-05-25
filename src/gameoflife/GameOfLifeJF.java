/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife;

import java.util.Timer;
import java.util.TimerTask;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import static javax.swing.JOptionPane.showInputDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.filechooser.FileSystemView;

/**
 * Klasa główna programu obsługująca całą funkcjonalność
 * @author Bartosz Bednarczyk
 */
public class GameOfLifeJF extends javax.swing.JFrame {

    /**
     * Creates new form GameOfLifeJF
     */
    
    final int width = 100, height = 50;
    boolean[][] currentMove = new boolean[height][width], nextMove = new boolean[height][width];
    boolean play;
    Image offScrImg;
    Graphics offScrGraph;
    boolean glider;
    Color myGrey = new Color(112, 112, 112);
    
    
            
/**
 * 
 */
    public GameOfLifeJF() {
        initComponents();
        offScrImg = createImage(jPanel1.getWidth(), jPanel1.getHeight());
        offScrGraph = offScrImg.getGraphics();

        
        
        
        
        Timer time = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                if (play) {
                    for (int i = 0; i < height; i++) {
                        for (int j = 0; j < width; j++) {
                            nextMove[i][j] = decide(i, j);
                        }
                    }

                    for (int i = 0; i < height; i++) {
                        for (int j = 0; j < width; j++) {
                            currentMove[i][j] = nextMove[i][j];
                        }
                    }
                    screenReset();
                }
            }
        };
        time.scheduleAtFixedRate(task, 0, 100);
        screenReset();
    }
/**
 * decide decyduje czy komórka, której współrzędne zostały przekazane jako argumenty będzie w następnym ruchu żywa lub martwa
 * @param i współrzędna x na planszy
 * @param j współrzędna y na planszy
 * @return wartość komórki (martwa lub żywa)
 */
    public boolean decide(int i, int j) {
        int neighbours = 0;
        if (j < width - 1) {
            if (currentMove[i][j + 1]) {
                neighbours++;
            }
            if (i > 0) {
                if (currentMove[i - 1][j + 1]) {
                    neighbours++;
                }
            }
            if (i < height - 1) {
                if (currentMove[i + 1][j + 1]) {
                    neighbours++;
                }
            }
        }
        if (j > 0) {
            if (currentMove[i][j - 1]) {
                neighbours++;
            }
            if (i > 0) {
                if (currentMove[i - 1][j - 1]) {
                    neighbours++;
                }
            }
            if (i < height - 1) {
                if (currentMove[i + 1][j - 1]) {
                    neighbours++;
                }
            }
        }
        if (i > 0) {
            if (currentMove[i - 1][j]) {
                neighbours++;
            }
        }
        if (i < height - 1) {
            if (currentMove[i + 1][j]) {
                neighbours++;
            }
        }
        if (neighbours == 3) {
            return true;
        }
        if (currentMove[i][j] && neighbours == 2) {
            return true;
        }
        return false;
    }

/**
 * writeToFile zapisuje planszę do pliku .gof o nazwie podanej jako argument name
 * @param name nazwa pliku, w którym zapisana zostanie plansza
 */
    public void writeToFile(String name) {

        try {
            FileWriter writer = new FileWriter(name+".gof", true);
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    writer.write(currentMove[i][j] + "\r\n");
                    System.lineSeparator();
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
/**
 * readFromFile wczytuje zapisaną planszę z pliku, którego adres jest podawany jako argument do tablicy currentMove
 * @param name nazwa wczytywanego 
 */
    public void readFromFile(File name) {
        try {
            Scanner scanner = new Scanner(name);
            System.out.println("Read text file using Scanner");
            int i = 0;
            int j = 0;
            while (scanner.hasNextLine()) {
                currentMove[i][j] = Boolean.parseBoolean(scanner.nextLine());
                j++;
                if (j == width) {
                    j = 0;
                    i++;
                }
            }
            scanner.close();
            screenReset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
/**
 * screenReset odświeża całą planszę jPanel1
 */
    public void screenReset() {
        offScrGraph.setColor(jPanel1.getBackground());
        offScrGraph.fillRect(0, 0, jPanel1.getWidth(), jPanel1.getHeight());

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (currentMove[i][j]) {
                    offScrGraph.setColor(myGrey);
                    int y = i * jPanel1.getHeight() / height;
                    int x = j * jPanel1.getWidth() / width;
                    offScrGraph.fillRect(x, y, (jPanel1.getWidth() / width) + 1, (jPanel1.getHeight() / height) + 1);
                }

            }
        }

        offScrGraph.setColor(myGrey);

        for (int i = 0; i <= height; i++) {
            int y = i * jPanel1.getHeight() / height;
            offScrGraph.drawLine(0, y, jPanel1.getWidth(), y);
        }
        for (int j = 0; j <= width; j++) {
            int x = j * jPanel1.getWidth() / width;
            offScrGraph.drawLine(x, 0, x, jPanel1.getHeight());
        }
        int y = jPanel1.getHeight() - 1;
        offScrGraph.drawLine(0, y, jPanel1.getWidth(), y);
        int x = jPanel1.getWidth() - 1;
        offScrGraph.drawLine(x, 0, x, jPanel1.getHeight());

        jPanel1.getGraphics().drawImage(offScrImg, 0, 0, jPanel1);
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 51, 153));
        setForeground(new java.awt.Color(204, 0, 255));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });
        jPanel1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jPanel1ComponentResized(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1507, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 799, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(112, 112, 112)));
        jPanel2.setForeground(new java.awt.Color(112, 112, 112));
        jPanel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel2.setPreferredSize(new java.awt.Dimension(1131, 70));

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gameoflife/images/info.jpg"))); // NOI18N
        jButton4.setBorder(null);
        jButton4.setContentAreaFilled(false);
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gameoflife/images/save.jpg"))); // NOI18N
        jButton5.setBorder(null);
        jButton5.setBorderPainted(false);
        jButton5.setContentAreaFilled(false);
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gameoflife/images/load.jpg"))); // NOI18N
        jButton6.setBorder(null);
        jButton6.setContentAreaFilled(false);
        jButton6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gameoflife/images/logo.jpg"))); // NOI18N
        jButton7.setBorder(null);
        jButton7.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jButton7.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        jButton7.setOpaque(false);

        jButton1.setBackground(new java.awt.Color(255, 0, 51));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gameoflife/images/play.jpg"))); // NOI18N
        jButton1.setToolTipText("");
        jButton1.setBorder(null);
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setForeground(new java.awt.Color(240, 240, 240));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gameoflife/images/reset.jpg"))); // NOI18N
        jButton2.setText("Reset");
        jButton2.setAlignmentY(32.0F);
        jButton2.setBorder(null);
        jButton2.setContentAreaFilled(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gameoflife/images/onestep.jpg"))); // NOI18N
        jButton3.setBorder(null);
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 479, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 420, Short.MAX_VALUE)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton7)
                .addGap(21, 21, 21))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton4)
                        .addComponent(jButton5)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1))
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1507, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        play = !play;
        if (play) {
            ImageIcon img1 = new ImageIcon(getClass().getResource("/gameoflife/images/pause.jpg"));
            jButton1.setIcon(img1);
        } else {
            ImageIcon img1 = new ImageIcon(getClass().getResource("/gameoflife/images/play.jpg"));
            jButton1.setIcon(img1);
           
            
        }
        screenReset();
    }//GEN-LAST:event_jButton1ActionPerformed
/**
 * Przycisk czyszczący planszę
 * @param evt 
 */
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        currentMove = new boolean[height][width];
        screenReset();

    }//GEN-LAST:event_jButton2ActionPerformed
    /*
    * Główny przycisk uruchamiający algorytm
    * @param evt 
    */
    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        int j = width * evt.getX() / jPanel1.getWidth();
        int i = height * evt.getY() / jPanel1.getHeight();
        currentMove[i][j] = !currentMove[i][j];
//        if (glider) {
//            currentMove[i + 1][j] = !currentMove[i + 1][j];
//            currentMove[i + 2][j] = !currentMove[i + 2][j];
//            currentMove[i + 2][j + 1] = !currentMove[i + 2][j + 1];
//            currentMove[i + 1][j + 2] = !currentMove[i + 1][j + 2];
//        }

        screenReset();
    }//GEN-LAST:event_jPanel1MouseClicked
    /*
    * Reset ustawień graficznych po zmianie rozmiaru okna
    * @param evt 
    */
    private void jPanel1ComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel1ComponentResized
        // TODO add your handling code here:
        offScrImg = createImage(jPanel1.getWidth(), jPanel1.getHeight());
        offScrGraph = offScrImg.getGraphics();
        screenReset();


    }//GEN-LAST:event_jPanel1ComponentResized
    /*
    * Przycisk przechodzący do następnego dnia
    * @param evt 
    */
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        
        play = !play;
        screenReset();
        try {
            Thread.sleep(99);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        play = !play;
        

    }//GEN-LAST:event_jButton3ActionPerformed
    /*
    * Przycisk wyświetlający okienko z informacjami
    * @param evt 
    */
    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        //glider = !glider;
        showMessageDialog(null,"Game of Life (Gra w Życie) to automat komórkowy wymyślony w 1970 roku przez\n" +
"matematyka Johna Conwaya. Pomimo swoich prostych zasad, do dziś wzbudza\n" +
"zainteresowanie wielu naukowców przez sposób, w jaki proste struktury mogą\n" +
"ewoluować do niezwykle skomplikowanych tworów.\n\n" +
"Gra w Życie to po prostu zwykła plansza złożona z komórek, które mogą mieć wartość\n" +
"0 (martwa) i 1 (żywa), a także 2 reguły, które kontrolują cały mechanizm:\n\n" +
"1. Martwa komórka, która ma dokładnie 3 żywych sąsiadów, staje się żywa (rodzi się)\n" +
"w następnej jednostce czasu (kolejnym dniu).\n" +
"2. Żywa komórka z 2 albo 3 żywymi sąsiadami pozostaje nadal żywa; przy innej liczbie\n" +
"sąsiadów umiera.");
        
    }//GEN-LAST:event_jButton4MouseClicked
    /*
    * Po otwarciu programu tworzy grafikę planszy
    * @param evt 
    */
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        offScrImg = createImage(jPanel1.getWidth(), jPanel1.getHeight());
        offScrGraph = offScrImg.getGraphics();
        screenReset();        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened
    /*
    * Przycisk zapisujący aktualny stan planszy do pliku o podanej nazwie
    * @param evt 
    */
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        String returnValue = showInputDialog(null, "Podaj nazwe pliku");
        showMessageDialog(null, "Zapisano do pliku " + returnValue + ".gof w katalogu gry");
        writeToFile(returnValue);
    }//GEN-LAST:event_jButton5ActionPerformed
    /*
    * Przycisk wczytujący planszę z pliku
    * @param evt 
    */
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
       // String name = showInputDialog(null, "Podaj nazwe pliku");
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setDialogTitle("Wybierz plik: ");
		int returnValue = jfc.showOpenDialog(null);             
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File name = jfc.getSelectedFile();
			if (name.getName().endsWith(".gof")) {
                                readFromFile(name);
			}
                        else
                {
                    showMessageDialog(null,"Wybrales zly plik");
                }
		}

    }//GEN-LAST:event_jButton6ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GameOfLifeJF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameOfLifeJF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameOfLifeJF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameOfLifeJF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameOfLifeJF().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables

    void setColor(Color WHITE) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
