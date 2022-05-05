    package ui;

    import javax.swing.JPanel;
    import javax.swing.JScrollPane;
    import javax.swing.JViewport;
    import java.awt.Graphics;
    import java.awt.image.BufferedImage;
    import java.awt.Dimension;
    import java.awt.Font;
    import java.awt.Color;
    import java.awt.Point;
    import java.awt.Rectangle;

    import controller.PageController;
    import model.PageInput;


    public class VisualPanel extends JPanel{
        private PageController controller;
        public JPanel outputPanel;
        public JScrollPane jspDraw;
        public ValuesPanel valPanel;
        private Font font;
        private BufferedImage f1 = null, f2 =null, f3 =null, f4=null;
        private final int DRAW_WIDTH = 385;
        private final int DRAW_HEIGHT = 386;
        private boolean isSavingVal = false;
        private boolean isExecutingVal = false;

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
            imgLoader.reloadImage("src/frame3.png", "frame3");
            f3 = imgLoader.getBuffImage();
            imgLoader.reloadImage("src/frame4.png", "frame4");
            f4 = imgLoader.getBuffImage();
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
                        int[][] hitArr = controller.getPRASBolArr();
                        int startX = 25, x, y, startY = 65;

                        frameY = input.getFrameLength()*27;
                        frameX = input.getReferenceLength()*72;
                        
                        if(frameY<(DRAW_HEIGHT-18))
                            frameY = DRAW_HEIGHT-18;

                        startY = frameY-45;
                        x = startX;
                        y = startY;

                        if(isSaving()){
                            String refString = java.util.Arrays.toString(input.getReferenceValues());
                            refString = refString.substring(1,refString.length()-1);
                            g.drawString("Reference String: "+refString,startX, 20);
                            g.drawString("No. of Page Frame: "+String.valueOf(input.getFrameLength())
                                +"          Total Page Fault: "+String.valueOf(controller.getExecCurrentPageFault()),startX, 50);
                        }

                        for(int i = 0; i<input.getReferenceLength(); i++){
                            for(int j = 0; j<input.getFrameLength(); j++){
                                
                                if(hitArr!=null && i<=controller.getExecCurrentReference() && hitArr[i][1]==1 && hitArr[i][0]==j )
                                    g.drawImage(f3, x,y, null);
                                else if(hitArr!=null && i<=controller.getExecCurrentReference() && hitArr[i][1]==0 && hitArr[i][0]==j)
                                    g.drawImage(f4, x, y, null);
                                else if((j+i)%2==0)
                                    g.drawImage(f1, x, y,null);
                                else
                                    g.drawImage(f2, x, y,null);
                            


                                if(frames!=null && i<=controller.getExecCurrentReference()){

                                    if(frames[i][j]!=null){
                                        g.drawString(frames[i][j], x+13, y+20);
                                    }
                                }
                                y -= 27; 
                            }  

                            if(hitArr!=null && i<=controller.getExecCurrentReference()){
                                    g.setColor(Color.green);
                                    String hit = "Hit";
                                    if(hitArr[i][1]==0){
                                        g.setColor(Color.red);
                                        hit = "Miss";
                                    }
                                    g.drawString(hit, x+3, startY+40);
                                    g.setColor(Color.black);
                                    g.drawString(input.getReferenceValues()[i], x+13, y+25);
                            }

                            y = startY;
                            x += 72;
                        }

                        if(jspDraw!=null){
                            int xJ = controller.getExecCurrentReference()*72+25;
                            JViewport jvp = jspDraw.getViewport();
                            if(isExecuting() && xJ>0 && !jvp.getViewRect().contains(new Point(xJ+144, 0))){
                                jvp.setViewPosition( new Point(xJ-(int)(DRAW_WIDTH/2)+72, 0));
                                jspDraw.setViewport(jvp);
                            }
                        }
                    }
                    setPreferredSize(new Dimension(frameX+25, frameY));
                    updateUI();
                }
            
            };
                
            outputPanel.setBackground(Color.white);

            jspDraw = new JScrollPane(outputPanel);
            jspDraw.setBounds(0,0,DRAW_WIDTH+1, DRAW_HEIGHT); 

            JPanel outerDraw = new JPanel(null);
            outerDraw.setBounds(5,165,DRAW_WIDTH, DRAW_HEIGHT);
            outerDraw.add(jspDraw);
            return outerDraw;
        }

        public boolean isSaving(){
            return this.isSavingVal;
        }

        public boolean isExecuting(){
            return this.isExecutingVal;
        }

        public void setIsSaving(boolean stat){
            this.isSavingVal = stat;
        }

        public void setIsExecuting(boolean stat){
            this.isExecutingVal = stat;
        }
        
        public ValuesPanel getValuePanel(){
            return this.valPanel;
        }

        public JPanel getOutputPanel(){
            return this.outputPanel;
        }
    }	
