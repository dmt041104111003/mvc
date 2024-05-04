package view;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import controller.Sound;
public class VolumeDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	 	private JSlider volumeSlider;
	    private JSlider rowSoundSlider; 
	    private int previousVolume;
	    private float previousRowSoundVolume; 

    public VolumeDialog(Frame owner) {
        super(owner, "Adjust Volume", true);
        setSize(300, 230);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        previousVolume = Sound.getVolume();
        previousRowSoundVolume = Sound.getRowSoundVolume(); 
        
        volumeSlider = new JSlider(0, 100, previousVolume);
        volumeSlider.setMajorTickSpacing(25);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);
        volumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {Sound.setVolume(volumeSlider.getValue());}
        });
        rowSoundSlider = new JSlider(0, 100, (int) (previousRowSoundVolume * 100));
        rowSoundSlider.setMajorTickSpacing(25);
        rowSoundSlider.setPaintTicks(true);
        rowSoundSlider.setPaintLabels(true);
        rowSoundSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {Sound.setRowSoundVolume(rowSoundSlider.getValue() / 100.0f);}
        });
        
        JPanel sliderPanel = new JPanel();
        sliderPanel.add(volumeSlider);
        sliderPanel.add(rowSoundSlider);
        add(sliderPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dispose());
        buttonPanel.add(okButton);
        add(buttonPanel, BorderLayout.SOUTH);

        addWindowListener(new WindowListener() {
            @Override
            public void windowClosing(WindowEvent e) {
                Sound.setVolume(previousVolume); 
                Sound.setRowSoundVolume(previousRowSoundVolume);
                dispose(); 
            }
            @Override public void windowOpened(WindowEvent e) {}
            @Override public void windowClosed(WindowEvent e) {}
            @Override public void windowIconified(WindowEvent e) {}
            @Override public void windowDeiconified(WindowEvent e) {}
            @Override public void windowActivated(WindowEvent e) {}
            @Override public void windowDeactivated(WindowEvent e) {}
        });
    }
}