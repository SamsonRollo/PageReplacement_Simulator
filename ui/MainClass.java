package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import controller.MenuController;

public class MainClass extends JFrame implements ActionListener{
	private MenuController menuController;
	//private PageController pageController;
	private JPanel activePanel; //either main menu or ative algorithm
	private MainMenuPanel mainMenuPanel;
	private AlgorithmPanel algorithmPanel;
	private CardLayout card;
	private static final int COVER_WIDTH = 500;
	private static final int COVER_HEIGHT = 600;
	private static final int PAGE_WIDTH = 800;
	private static final int PAGE_HEIGHT = 600;

    public MainClass(){
		setSize(COVER_WIDTH, COVER_HEIGHT);
		setTitle("PRAS");
		setLocationRelativeTo(null);
		setUndecorated(true);
		setResizable(false);
		getContentPane().setLayout(null);
		loadMainMenu();
		setVisible(true);
		loadPages();		
	}

	private void loadMainMenu(){
		menuController = new MenuController();
		mainMenuPanel = new MainMenuPanel(new Dimension(COVER_WIDTH, COVER_HEIGHT), this);
		card = new CardLayout();
		activePanel = new JPanel(card);
		activePanel.setSize(COVER_WIDTH, COVER_HEIGHT);
		activePanel.add(mainMenuPanel, "mmPanel");
		getContentPane().add(activePanel);
	}

	private void loadPages(){
		algorithmPanel = new AlgorithmPanel(new Dimension(PAGE_WIDTH, PAGE_HEIGHT), this);
		algorithmPanel.setSize(PAGE_WIDTH, PAGE_HEIGHT);
		activePanel.add(algorithmPanel, "alPanel");
	}

	public void openPage(){
		loadActive("alPanel", PAGE_WIDTH, PAGE_HEIGHT);
	}

	public void openMenu(){
		loadActive("mmPanel", COVER_WIDTH, COVER_HEIGHT);
	}
	
	public void loadActive(String active, int width, int height){
		setSize(width, height);
		setLocationRelativeTo(null);
		activePanel.setSize(width, height);
		card.show(activePanel, active);
		revalidate();
		repaint();
		activePanel.updateUI();
	}

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
    }
    
    public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				new MainClass();
			}
		});
	}
}
