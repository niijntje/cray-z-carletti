package gui;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;

public class MainFrame extends JFrame implements Subject {
	private ArrayList<Observer> observers;

	public MainFrame() {

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setTitle("");
		this.setLocation(20, 20);
		this.setSize(400, 300);
	}

	@Override
	public void registerObserver(Observer o) {
		observers.add(o);

	}

	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);

	}

	@Override
	public void notifyObserver() {
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).update();
		}
	}

}
