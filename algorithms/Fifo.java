package algorithms;

import model.PageInput;

public class Fifo {
    private int[] values;
    private int frameLen;
    private int refLen;
    public String[][] fifoFrame;
    private int currentReference;
    private int currentFrame;

    public Fifo(PageInput input){
        setValues(input);
    }

    public void setValues(PageInput input){
        values = input.getReferenceValues();
        frameLen = input.getFrameLength();
        refLen = input.getReferenceLength();
        fifoFrame = new String[refLen][frameLen];
        currentReference = 0;
        currentFrame = 0;
    }

    public void move(){
        if(currentReference==0){
            fifoFrame[0][0]= String.valueOf(values[0]);
            this.currentFrame++;
        }else{
            insertFrameAtReference(currentReference, currentFrame, fifoFrame);
        }
        this.currentReference++;
    }

    private void insertFrameAtReference(int currentReference, int currentFrame, String[][] fifoFrame){
        int prevRef = currentReference-1;
        boolean exist = alreadyExistInFrame(currentReference, fifoFrame);
        for(int i=0; i<frameLen; i++){
            if(!exist && i==currentFrame){
                fifoFrame[currentReference][i] = String.valueOf(values[currentReference]);
                this.currentFrame = (this.currentFrame+1)%frameLen;
            }else{
                fifoFrame[currentReference][i] = fifoFrame[prevRef][i];
            }
        }
    } 

    private boolean alreadyExistInFrame(int currentReference, String[][] fifoFrame){
        int prevRef = currentReference-1;
        for(int i=0; i<frameLen; i++){
            if(fifoFrame[prevRef][i]==null) //safety catch for null
                break;
            if(Integer.parseInt(fifoFrame[prevRef][i])==values[currentReference])
                return true;
        }
        return false;
    }

    public String[][] getFifoFrame(){
        return fifoFrame;
    }
}
