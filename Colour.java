public class Colour{

    int red=-1;
    int green=-1;
    int blue=-1;

    Colour(){

        this.red=-1;
        this.green=-1;
        this.blue=-1;

}

    Colour(int red,int green,int blue){

        this.red = red;
        this.green = green;
        this.blue = blue;

    }

    /**
     * @return the blue
     */
    public int getBlue() {
        return blue;
    }

    /**
     * @return the red
     */
    public int getRed() {
        return red;
    }

    /**
     * @return the green
     */
    public int getGreen() {
        return green;
    }

    /**
     * @param blue the blue to set
     */
    public void setBlue(int blue) {
        this.blue = blue;
    }

    /**
     * @param green the green to set
     */
    public void setGreen(int green) {
        this.green = green;
    }
    /**
     * @param red the red to set
     */
    public void setRed(int red) {
        this.red = red;
    }

}