import java.util.Scanner;
import java.io.*;

public class Main{

    static String fileName;
    static Scanner k ;
    static QuadTree n0002 = new QuadTree<>();
    static QuadTree n0004 = new QuadTree<>();
    static QuadTree n001 = new QuadTree<>();
    static QuadTree n0033 = new QuadTree<>();
    static QuadTree n0077 = new QuadTree<>();
    static QuadTree n02 = new QuadTree<>();
    static QuadTree n05 = new QuadTree<>();
    static QuadTree n065 = new QuadTree<>();
    
    static File newFile;
    
    static Colour[][] imageArray;
    static Colour newImage[][] ;
    static int widthNum;

    public static void readImage(String name){
        try{
        
        newFile = new File(name);
        k = new Scanner(newFile);
        }catch(Exception e){ e.printStackTrace();};

        try {
       
            
            
            k.nextLine();//P3
            
            String width = k.nextLine();
            width = width.substring(0,width.indexOf(" ")); // 512 512 or num" "num
            widthNum = Integer.parseInt(width);
            
            imageArray = new Colour[widthNum][widthNum];
            

            k.nextLine(); //255
            int lineNum = 0;
            int num = 0;
            int indexNum = 0;
            Colour pixel = new Colour();
            while(k.hasNext()){
                
                
                String line = k.next();
                
                if(num == 0){
                    pixel.setRed(Integer.parseInt(line));
                }

                if(num == 1){
                    pixel.setGreen(Integer.parseInt(line));
                } 
                    
                if(num == 2){
                    pixel.setBlue(Integer.parseInt(line));
                }    
                num++;
                if(num == 3){ //R G B values 
                    imageArray[lineNum][indexNum] = pixel;
                    pixel = new Colour();
                    num = 0;
                    indexNum++;
                }

                if(indexNum == widthNum){ //end of line
                    lineNum++;
                    num = 0;
                    indexNum = 0;
                }

                

            }

            //imageArray contains the image pixel by pixel with RGB values.
        
            
            double threshold0002 = 14300;
            double threshold0004 = 11500;
            double threshold001 = 7500;
            double threshold0033 = 2000;
            double threshold0077 = 280;
            double threshold02 = 29;
            double threshold05 = 1;
            double threshold065 = 0.000001;

            
            createQuadTree(n0002,threshold0002);
            Colour[][] n0002c = new Colour[imageArray.length][imageArray.length];
            n0002c = treeTo2DArray(n0002c,n0002);
            arrayToImage(n0002c,"-1");


            createQuadTree(n0004,threshold0004);
            Colour[][] n0004c = new Colour[imageArray.length][imageArray.length];
            n0004c = treeTo2DArray(n0004c,n0004);
            arrayToImage(n0004c,"-2");


            createQuadTree(n001,threshold001);
            Colour[][] n001c = new Colour[imageArray.length][imageArray.length];
            n001c = treeTo2DArray(n001c,n001);
            arrayToImage(n001c,"-3");


            createQuadTree(n0033,threshold0033);
            Colour[][] n0033c = new Colour[imageArray.length][imageArray.length];
            n0033c = treeTo2DArray(n0033c,n0033);
            arrayToImage(n0033c,"-4");

            createQuadTree(n0077,threshold0077);
            Colour[][] n0077c = new Colour[imageArray.length][imageArray.length];
            n0077c = treeTo2DArray(n0077c,n0077);
            arrayToImage(n0077c,"-5");

            createQuadTree(n02,threshold02);
            Colour[][] n02c = new Colour[imageArray.length][imageArray.length];
            n02c = treeTo2DArray(n02c,n02);
            arrayToImage(n02c,"-6");

            createQuadTree(n05,threshold05);
            Colour[][] n05c = new Colour[imageArray.length][imageArray.length];
            n05c = treeTo2DArray(n05c,n05);
            arrayToImage(n05c,"-7");

            createQuadTree(n065,threshold065);
            Colour[][] n065c = new Colour[imageArray.length][imageArray.length];
            n065c = treeTo2DArray(n065c,n065);
            arrayToImage(n065c,"-8");


            
        } catch (Exception e) {
            System.out.print("Error");
            e.printStackTrace();
        }

       
        
        //countleaf can be used to find compression level
        
    }


    private static void arrayToImage(Colour[][] newImage,String name){

        File temp = new File(fileName+name+".ppm");
        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(temp));
            writer.write("P3\n"+widthNum+" "+widthNum+"\n255\n");
            for(int i = 0 ; i < imageArray.length ; i++){
                for(int j = 0 ; j < imageArray.length ; j++){

                    writer.write(""+((int)newImage[i][j].getRed()));
                    writer.write(" ");
                    writer.write(""+((int)newImage[i][j].getGreen()));
                    writer.write(" ");
                    writer.write(""+((int)newImage[i][j].getBlue()));
                    writer.write(" ");
                }
                writer.write("\n");
            }
    
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
       


    }


    private static Colour[][] treeTo2DArray(Colour[][] array,QuadTree tree){
        
        return treeTo2DAux(array,tree.root(),tree);

    }

    private static Colour[][] treeTo2DAux(Colour[][] array,Position p,QuadTree tree){

        if(tree.isLeaf(p)){

            int x1 = tree.getX1(p);
            int y1 = tree.getY1(p);
            
            int x2 = tree.getX2(p);
            int y2 = tree.getY2(p);
            
            for(int i = x1 ; i < x2 ; i++ ){
                for(int j = y1 ; j < y2 ; j++){

                    array[i][j] = tree.getColour(p);
                    
                }
            }
             
            return null;
        }

        treeTo2DAux(array,tree.NW(p),tree);
        
        treeTo2DAux(array,tree.NE(p),tree);
        
        treeTo2DAux(array,tree.SW(p),tree);
        
        treeTo2DAux(array,tree.SE(p),tree);
        
        return array;

    }
    

    public static void createQuadTree(QuadTree tree,double threshold){

        tree.addRoot(null, 0, widthNum, 0, widthNum);
        createQuadTreeAux(tree.root(),threshold,tree);
        
    }

    public static void createQuadTreeAux(Position p, double threshold,QuadTree tree){
        

        
        int x1 = tree.getX1(p);
        int x2 = tree.getX2(p);
        int y1 = tree.getY1(p);
        int y2 = tree.getY2(p);

        double red = 0;
        double green = 0;
        double blue = 0;

        int n = tree.getX2(p)-tree.getX1(p);
        if(n == 1){
            
            red = imageArray[x1][y1].red;
            green = imageArray[x1][y1].green;
            blue = imageArray[x1][y1].blue;

            tree.setColour(p,red,green,blue);
            return;
        }
        
        
        for (int i = tree.getX1(p); i < tree.getX2(p); i++) {
            for (int j = tree.getY1(p) ; j < tree.getY2(p) ; j++) {

                red += imageArray[i][j].getRed();
                green += imageArray[i][j].getGreen();
                blue += imageArray[i][j].getBlue();
                
            }
        }

        red = red / (n*n);
        green = green / (n*n);
        blue = blue / (n*n);
        tree.setColour(p,red,green,blue);

        

        double errorRed = 0;
        double errorGreen = 0;
        double errorBlue = 0;
        double errorSum = 0;

        for (int i = tree.getX1(p); i < tree.getX2(p); i++) {
            for (int j = tree.getY1(p);j < tree.getY2(p) ; j++) {
                
                errorRed = Math.pow(imageArray[i][j].red-red,2);
                errorGreen = Math.pow(imageArray[i][j].green-green,2);
                errorBlue = Math.pow(imageArray[i][j].blue-blue,2);
                errorSum += (errorBlue+errorGreen+errorRed);

            }
        }

        
        errorSum = errorSum / (n*n);

        
        if(errorSum > threshold){



            tree.addNW(p , null , x1, ((x2-x1)/2)+x1 , y1, ((y2-y1)/2)+y1);
        
            tree.addNE(p , null , x1+((x2-x1)/2) , x2 , y1 , ((y2-y1)/2)+y1 );
            
            tree.addSW(p , null , x1 , x1+((x2-x1)/2) , ((y2-y1)/2)+y1, y2);

            tree.addSE(p , null , x1+((x2-x1)/2) , x2 , ((y2-y1)/2)+y1 , y2);

            createQuadTreeAux(tree.NW(p), threshold,tree);

            createQuadTreeAux(tree.NE(p), threshold,tree);

            createQuadTreeAux(tree.SW(p), threshold,tree);

            createQuadTreeAux(tree.SE(p), threshold,tree);

        }


    }

    public static void main(String[] args){

        String imageName = "";
        if(args[0].equals("-o")){
            fileName = args[1];
            imageName = args[3];
        }

        if(args[0].equals("-i")){
            
            imageName = args[1];
            fileName = args[3];
        }
        
        readImage(imageName);

    }

}