//package view;
//
//import java.awt.BorderLayout;
//import java.awt.Component;
//import javax.swing.ImageIcon;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//
//class BackgroundUpdater extends Thread {
//    private final JPanel panel;
//    private final String backgroundPath;
//    private final Component componentToAdd;
//
//    public BackgroundUpdater(JPanel panel, String backgroundPath, Component componentToAdd) {
//        this.panel = panel;
//        this.backgroundPath = backgroundPath;
//        this.componentToAdd = componentToAdd;
//    }
//
//    @Override
//    public void run() {
//        ImageIcon backgroundImage = new ImageIcon(backgroundPath);
//        JLabel backgroundLabel = new JLabel(backgroundImage);
//        panel.removeAll();
//        panel.setLayout(new BorderLayout());
//        panel.add(backgroundLabel, BorderLayout.CENTER);
//        if (componentToAdd != null) {
//            panel.add(componentToAdd, BorderLayout.CENTER);
//        }
//        panel.revalidate();
//        panel.repaint();
//    }
//}