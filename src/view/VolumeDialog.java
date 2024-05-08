package view;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
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
        getContentPane().setLayout(new BorderLayout());

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
        
        JPanel sliderPanel = new JPanel(new GridLayout(2, 1, 10, 10)); 
        sliderPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel volumeRow = new JPanel(new BorderLayout(5, 0)); 
        volumeRow.add(volumeSlider, BorderLayout.EAST);
        JLabel lblNewLabel = new JLabel("Volume:");
        volumeRow.add(lblNewLabel);
        volumeRow.add(lblNewLabel, BorderLayout.WEST);

        volumeRow.add(volumeSlider);
        sliderPanel.add(volumeRow);
        getContentPane().add(sliderPanel, BorderLayout.CENTER);
        

        rowSoundSlider = new JSlider(0, 100, (int) (previousRowSoundVolume * 100));
        rowSoundSlider.setMajorTickSpacing(25);
        rowSoundSlider.setPaintTicks(true);
        rowSoundSlider.setPaintLabels(true);
        rowSoundSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {Sound.setRowSoundVolume(rowSoundSlider.getValue() / 100.0f);}
        });
        
        JPanel rowSoundPanel = new JPanel();
        rowSoundPanel.setLayout(new BoxLayout(rowSoundPanel, BoxLayout.X_AXIS));
        rowSoundPanel.add(Box.createHorizontalGlue());
        rowSoundPanel.add(rowSoundSlider);
        JPanel rowSoundRow = new JPanel(new BorderLayout(0, 5));
        JLabel lblRowSound = new JLabel("Row:");
        rowSoundRow.add(lblRowSound, BorderLayout.WEST);
        rowSoundRow.add(rowSoundPanel, BorderLayout.CENTER);
        sliderPanel.add(rowSoundRow);
        
        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dispose());
        buttonPanel.add(okButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

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