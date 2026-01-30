import javax.swing.JFrame;

public class Display {
    private JFrame display;

    Display(){
        this.display = new JFrame("Chip-8 Emulator");
        this.display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.display.setSize(640, 320);
        this.clearFrame();
        this.display.setVisible(true);
    }

    public JFrame getFrame(){
        return this.display;
    }

    public void clearFrame() {
        this.display.getContentPane().removeAll();
        this.display.getContentPane().setBackground(java.awt.Color.BLACK);
        this.display.repaint();
    }
}
