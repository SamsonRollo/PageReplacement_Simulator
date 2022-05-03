    package ui;

    import javax.swing.JPanel;
    import javax.swing.JScrollPane;
    import java.awt.Graphics;
    import java.awt.image.BufferedImage;
    import java.awt.Dimension;
    import java.awt.Font;
    import java.awt.Color;

    import controller.PageController;
    import model.PageInput;
    
    public class VisualPanel extends JPanel{
        private PageController controller;
        public JPanel outputPanel;
        public ValuesPanel valPanel;
        private Font font;
        private BufferedImage f1 = null, f2 =null;
        private final int DRAW_WIDTH = 385;
        private final int DRAW_HEIGHT = 386;

        public VisualPanel(PageController controller, int x, int y, int w, int h, Font font){
            this.controller = controller;
            this.font  = font;
            setLayout(null);
            setBounds(x, y, w, h);
            setOpaque(false);
            add(loadValuePanel());
            add(loadFramePanel());
        }

        public JPanel loadValuePanel(){
            valPanel = new ValuesPanel(0,0,395,160, controller, font);
            return valPanel;    
        }
        
        public JPanel loadFramePanel(){
            ImageLoader imgLoader = new ImageLoader("src/frame1.png","frame1");
            f1 = imgLoader.getBuffImage();
            imgLoader.reloadImage("src/frame2.png", "frame2");
            f2 = imgLoader.getBuffImage();
            imgLoader = null;
            outputPanel = new JPanel(null){
                @Override
                public void paintComponent(Graphics g){
                    super.paintComponent(g);
                    
                    g.setFont(font);
                    PageInput input = controller.getPageInput();
                    int frameX = DRAW_WIDTH-18, frameY = DRAW_HEIGHT-10;

                    if(input!=null){
                        String[][] frames = controller.getPRASFrames();
                        boolean[] hitArr = controller.getPRASBolArr();
                        int startX = 25, x, y, startY = 65;

                        frameY = input.getFrameLength()*27;
                        frameX = input.getReferenceLength()*72;
                        
                        if(frameY<(DRAW_HEIGHT-18))
                            frameY = DRAW_HEIGHT-18;

                        startY = frameY-45;
                        x = startX;
                        y = startY;

                        for(int i = 0; i<input.getReferenceLength(); i++){
                            for(int j = 0; j<input.getFrameLength(); j++){
            
                                if(j%2==0)
                                    g.drawImage(f1, x, y,null);
                                else
                                    g.drawImage(f2, x, y,null);

                                if(frames!=null && i<=controller.getExecCurrentReference()){
                                    if(frames[i][j]!=null)
                                        g.drawString(String.valueOf(frames[i][j]), x+13, y+20);
                                }
                                y -= 27; 
                            } 
                            if(hitArr!=null && i<=controller.getExecCurrentReference()){
                                    String hit = "Hit";
                                    if(!hitArr[i])
                                        hit = "Miss";
                                    g.drawString(hit, x+3, startY+40);
                                    g.drawString(input.getReferenceValues()[i], x+13, y+25);
                            }

                            y = startY;
                            x += 72;
                        }
                    }
                    setPreferredSize(new Dimension(frameX, frameY));
                    updateUI();
                }
            
            };
                
            outputPanel.setBackground(Color.white);

            JScrollPane jspDraw = new JScrollPane(outputPanel);
            jspDraw.setBounds(0,0,DRAW_WIDTH+1, DRAW_HEIGHT); 
            JPanel outerDraw = new JPanel(null);
            outerDraw.setBounds(5,165,DRAW_WIDTH, DRAW_HEIGHT);
            outerDraw.add(jspDraw);
            return outerDraw;
        }
        
        public ValuesPanel getValuePanel(){
            return this.valPanel;
        }

        public JPanel getOutputPanel(){
            return this.outputPanel;
        }
    }	
