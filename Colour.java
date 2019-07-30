public class Colour{

    double red=-1;
    double green=-1;
    double blue=-1;

    Colour(){

        this.red=-1;
        this.green=-1;
        this.blue=-1;

}

    Colour(double red,double green,double blue){

        this.red = red;
        this.green = green;
        this.blue = blue;

    }

    /**
     * @return the blue
     */
    public double getBlue() {
        return blue;
    }

    /**
     * @return the red
     */
    public double getRed() {
        return red;
    }

    /**
     * @return the green
     */
    public double getGreen() {
        return green;
    }

    /**
     * @param blue the blue to set
     */
    public void setBlue(double blue) {
        this.blue = blue;
    }

    /**
     * @param green the green to set
     */
    public void setGreen(double green) {
        this.green = green;
    }
    /**
     * @param red the red to set
     */
    public void setRed(double red) {
        this.red = red;
    }

}