import java.awt.Color;
import java.awt.Font;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class MyFrame extends JFrame {
	private MyPanel panel;
  public MyPanel getPanel() {
  return panel;
	}

	public MyFrame() {
    super("mastermind");
    panel = new MyPanel();
    add(panel); 
    pack();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    addMouseListener(panel);  
    addKeyListener(panel); 
    JLabel jlabel = new JLabel("MASTERMIND");
    jlabel.setFont(new Font("Verdana",1,20));
    jlabel.setForeground(Color.WHITE);
    jlabel.setLocation(27, 20);
    panel.add(jlabel);
    jlabel.setVisible(true);
  }
	public static void main(String[] args) throws IOException {
		new MyFrame().getPanel().run();
	}
}