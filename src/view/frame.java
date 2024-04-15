package view;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.event.KeyListener;
public class frame {


	private JFrame jframe;

	
	public frame() {
		frame_tetris();
        jframe.setVisible(true);
		
	}
	
	private void frame_tetris() {
		jframe = new JFrame("Game Tetris");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setLayout(null);
        jframe.setResizable(false);
        jframe.setSize(418, 561);
        KeyListener keyListener = null;
		jframe.addKeyListener(keyListener);
		
	}


}
