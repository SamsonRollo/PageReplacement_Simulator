package ui;

import java.awt.*;
import javax.swing.*;

import controller.PageController;

public class MainClass extends JFrame{ //rename to PRAS
	private PageController pageController;
	private JPanel activePanel;
	private MainMenuPanel mainMenuPanel;
	private AlgorithmPanel algorithmPanel;
	private CardLayout card;
	private static final int COVER_WIDTH = 500;
	private static final int COVER_HEIGHT = 600;
	private static final int PAGE_WIDTH = 850;
	private static final int PAGE_HEIGHT = 600;
	private final String ICON_PATH = "src/tray_icon.png";

    public MainClass(){
    	setTrayIcon();
		setUndecorated(true);
		setResizable(false);
		getContentPane().setLayout(new FlowLayout());
		loadMainMenu();
		loadPages();
		pack();
		setLocationRelativeTo(null);	
		setVisible(true);
	}

	private void loadMainMenu(){
		pageController = new PageController(this);
		mainMenuPanel = new MainMenuPanel(new Dimension(COVER_WIDTH, COVER_HEIGHT), this);
		card = new CardLayout();
		activePanel = new JPanel(card);
		activePanel.setPreferredSize(mainMenuPanel.getPreferredSize());
		activePanel.add(mainMenuPanel, mainMenuPanel.getPanelName());
		getContentPane().add(activePanel);
	}

	private void loadPages(){
		algorithmPanel = new AlgorithmPanel(new Dimension(PAGE_WIDTH, PAGE_HEIGHT), getMainClass());
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
	
	private void setTrayIcon(){
		ImageLoader il = new ImageLoader(ICON_PATH, "icon");
            setIconImage(il.getBuffImage());
	}
	
	public PageController getPageController(){
		return pageController;
	}
    
    private MainClass getMainClass(){
    	return this;
    }
    
    public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				new MainClass();
			}
		});
	}
}
