package ui;

import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;


public class AlgorithmPanel extends JPanel{
    private BufferedImage page;
    private static final String COVER_PATH = "src/page.png";
    private MainClass mainClass;

    public AlgorithmPanel(Dimension d, MainClass mainClass){
    	this.mainClass = mainClass;
        setLayout(null);
        setSize((int)d.getWidth(), (int)d.getHeight());
        drawPage();
    }

    private void drawPage(){
        try{
            page = ImageIO.read(this.getClass().getClassLoader().getResource(COVER_PATH));
        }catch(IOException ioe){}
        loadPageTools();
    }

    private JPanel loadPageTools(){
        JPanel pageTools = new JPanel(null);

        PRASButton startButton = new PRASButton();
        PRASButton stopButton = new PRASButton();
        PRASButton mainMenuButton = new PRASButton();
        PRASButton saveButton = new PRASButton();

        // startButton.setIcons(,
        //                     ,
        //                     );
		// stopButton.setIcons(,
        //                     ,
        //                     );
        // mainMenuButton.setIcons(,
        //                     ,
        //                     );
        // saveButton.setIcons(,
        //                     ,
        //                     );
        startButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				mainClass.openPage();//
			}
		});
	    stopButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
			}
		});
	    mainMenuButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
                mainClass.openMenu();
			}
		});
        saveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
			}
		});

        add(startButton);
        add(stopButton);
        add(mainMenuButton);

        return pageTools;
        
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(page, 0, 0, this);
    }
}
