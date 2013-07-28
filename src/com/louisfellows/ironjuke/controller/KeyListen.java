package com.louisfellows.ironjuke.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class KeyListen implements KeyListener {

	Controller m_Controller;
	ArrayList<Integer> m_Keys;
	
	public KeyListen( Controller p_Controller ) {
		m_Controller = p_Controller;
		m_Keys = new ArrayList<Integer>();
	}
	
	@Override
	public void keyPressed(KeyEvent key) {
		if (key.getKeyCode() == KeyEvent.VK_LEFT) {
			m_Controller.pageLeft();
		}
		else if (key.getKeyCode() == KeyEvent.VK_RIGHT) {
			m_Controller.pageRight();
		}
		else if (key.getKeyCode() == KeyEvent.VK_0) {
			m_Keys.add(0);
			checkForCompletedKeys();
		}
		else if (key.getKeyCode() == KeyEvent.VK_1) {
			m_Keys.add(1);
			checkForCompletedKeys();			
		}
		else if (key.getKeyCode() == KeyEvent.VK_2) {
			m_Keys.add(2);
			checkForCompletedKeys();
		}
		else if (key.getKeyCode() == KeyEvent.VK_3) {
			m_Keys.add(3);
			checkForCompletedKeys();
		}
		else if (key.getKeyCode() == KeyEvent.VK_4) {
			m_Keys.add(4);
			checkForCompletedKeys();
		}
		else if (key.getKeyCode() == KeyEvent.VK_5) {
			m_Keys.add(5);
			checkForCompletedKeys();
		}
		else if (key.getKeyCode() == KeyEvent.VK_6) {
			m_Keys.add(6);
			checkForCompletedKeys();
		}
		else if (key.getKeyCode() == KeyEvent.VK_7) {
			m_Keys.add(7);
			checkForCompletedKeys();
		}
		else if (key.getKeyCode() == KeyEvent.VK_8) {
			m_Keys.add(8);
			checkForCompletedKeys();
		}
		else if (key.getKeyCode() == KeyEvent.VK_9) {
			m_Keys.add(9);
			checkForCompletedKeys();
		}
		else if (key.getKeyCode() == KeyEvent.VK_R) {
			m_Controller.addRandom();
		}
		else if (key.getKeyCode() == KeyEvent.VK_C && key.isAltDown() == true) {
			m_Controller.cancelCurrent();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	private void checkForCompletedKeys() {
		if (m_Keys.size() == 0) {
			String s = "   :  ";
			m_Controller.selUpdated(s);
		}
		else if (m_Keys.size() == 1) {
			String s = m_Keys.get(0).toString()
					   + "  :  ";
			m_Controller.selUpdated(s);
		}
		else if (m_Keys.size() == 2) {
			String s = m_Keys.get(0).toString()
					   + m_Keys.get(1).toString()
					   + " :  ";
			m_Controller.selUpdated(s);
		}
		else if (m_Keys.size() == 3) {
			String s = m_Keys.get(0).toString()
					   + m_Keys.get(1).toString()
					   + m_Keys.get(2).toString()
					   + ":  ";
			m_Controller.selUpdated(s);
		}
		else if (m_Keys.size() == 4) {
			String s = m_Keys.get(0).toString()
					   + m_Keys.get(1).toString()
					   + m_Keys.get(2).toString()
					   + ":"
					   + m_Keys.get(3).toString()
					   + " ";
			m_Controller.selUpdated(s);
		}
		else if (m_Keys.size() == 5) {
			String s = m_Keys.get(0).toString()
			  		   + m_Keys.get(1).toString()
			  		   + m_Keys.get(2).toString()
			  		   + ":"
			  		   + m_Keys.get(3).toString()
			  		   + m_Keys.get(4).toString();
			m_Controller.selUpdated(s);
		}
		
		
		if (m_Keys.size() == 5) {
			int album = Integer.parseInt( "" + m_Keys.get(0) + m_Keys.get(1) + m_Keys.get(2) );
			int track = Integer.parseInt( "" + m_Keys.get(3) + m_Keys.get(4) );
			
			m_Controller.TrackNumberEntered(album - 1, track - 1);
			m_Keys.clear();
		}
	}

}
