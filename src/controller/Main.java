package controller;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.beeger.squareness.SquarenessLookAndFeel;

public class Main {

	public static Controller m_Controller;

	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel( new SquarenessLookAndFeel() );
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		m_Controller = new Controller();
		
	}

}
