import javax.swing.*;

public class SkyjoFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 1200;
	private static final int HEIGHT = 960;
	
	public SkyjoFrame(String title) {
		super(title);
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new SkyjoPanel());
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		
	}
}
