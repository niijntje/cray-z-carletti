package gui;

public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MainFrame mainFrame = new MainFrame();
		mainFrame.setVisible(true);
		SubFrameTilfoejMellemvarer subframeMellemvarer = new SubFrameTilfoejMellemvarer(mainFrame);
		subframeMellemvarer.setVisible(true);
		
	}

}
