package run;

import java.awt.EventQueue;

import view.frame;

public class main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame frame = new frame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
