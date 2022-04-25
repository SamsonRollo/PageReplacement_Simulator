package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import controller.PageController;

public class MainClass extends JFrame implements ActionListener{
	private PageController pageController;
	private JPanel activePanel; //either main menu or ative algorithm
	private MainMenuPanel mainMenuPanel;
	private AlgorithmPanel algorithmPanel;
	private CardLayout card;
	private static final int COVER_WIDTH = 500;
	private static final int COVER_HEIGHT = 600;
	private static final int PAGE_WIDTH = 800;
	private static final int PAGE_HEIGHT = 600;

	public MainClass(){
		setTitle("PRAS");
		setUndecorated(true);
		setResizable(false);
		getContentPane().setLayout(new FlowLayout());
		loadMainMenu();
		pack();
		setLocationRelativeTo(null);		
		setVisible(true);
		loadPages();	
	}

	private void loadMainMenu(){
		mainMenuPanel = new MainMenuPanel(new Dimension(COVER_WIDTH, COVER_HEIGHT), this);
		card = new CardLayout();
		activePanel = new JPanel(card);
		activePanel.setPreferredSize(mainMenuPanel.getPreferredSize());
		activePanel.add(mainMenuPanel, mainMenuPanel.getPanelName());
		getContentPane().add(activePanel);
	}

	private void loadPages(){
		algorithmPanel = new AlgorithmPanel(new Dimension(PAGE_WIDTH, PAGE_HEIGHT), this);
		activePanel.add(algorithmPanel, algorithmPanel.getPanelName());
	}

	public void openPage(){
		loadActive(algorithmPanel.getPanelName(), algorithmPanel);
	}

	public void openMenu(){
		loadActive(mainMenuPanel.getPanelName(), mainMenuPanel);
	}
	
	public void loadActive(String active, JPanel panel){
		activePanel.setPreferredSize(panel.getPreferredSize());
		card.show(activePanel, active);
		pack();
		setLocationRelativeTo(null);
	}

	public PageController getPageController(){
		return pageController;
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
