package CheckersGame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Checkers extends JFrame{
    public static boolean turn1 = true;
    JMenuBar menu;
    public static int algoNum = 1;
    
    public static void main(String[] args) {
        Runnable r = new Runnable()
                   {
                      @Override
                      public void run()
                      {
                         new Checkers();
                      }
                   };
      EventQueue.invokeLater(r);
    }
    
    public Checkers() {
        super("Checkers");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Logic logic = new Logic(algoNum); 
        logic.resetGame();
        
        setUndecorated(true);
        setContentPane(logic);
        setResizable(false);
        createMenu(logic);
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public void createMenu(Logic board) {
        menu = new JMenuBar();
        
        JMenu topic1 = new JMenu("Algorithms");
        
        ButtonGroup group1 = new ButtonGroup();
        JRadioButtonMenuItem rb1 = new JRadioButtonMenuItem("Random");
        JRadioButtonMenuItem rb2 = new JRadioButtonMenuItem("MiniMax");
        
        rb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
               algoNum = 1;
               newGame();
            }
        });
        
        rb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                algoNum = 2;
                newGame();
            }
        });
        
        group1.add(rb1);
        group1.add(rb2);
        
        if(algoNum == 1) {
            rb1.setSelected(true);
            rb2.setSelected(false);
        } else if (algoNum == 2) {
            rb1.setSelected(false);
            rb2.setSelected(true);
        }
        
        topic1.add(rb1);
        topic1.add(rb2);
        menu.add(topic1);
        
        JMenu topic2 = new JMenu("Settings");
        JRadioButtonMenuItem reset = new JRadioButtonMenuItem("Reset");
        JRadioButtonMenuItem exit = new JRadioButtonMenuItem("Exit");
        
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
               newGame();
            }
        });
        
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                System.exit(0);
            }
        });
        
        topic2.add(reset);
        topic2.add(exit);
        menu.add(topic2);
        
        this.setJMenuBar(menu);
    }
    
    public void newGame() {
        dispose();
        new Checkers();
    }
    
    public static void dialogWindow(String infoMessage, String titleBar){
        JOptionPane.showMessageDialog(null, infoMessage, "" + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
}
