import java.util.Scanner;
import java.io.*;

public class ImageCompressor{

    static QuadTree<Colour> tree = new QuadTree<Colour>();
    static Scanner k ;
    static FileWriter writer;
    static File newFile;
    static File temp;
    static Colour[][] imageArray;
    static int widthNum;

    public static void readImage(String name){
        try{
        
        newFile = new File(name);
        k = new Scanner(newFile);
        temp = new File("tempfile.ppm");
        writer = new FileWriter(temp);
        }catch(Exception e){ e.printStackTrace();};

        try {
       
            
            
            k.nextLine();//P3
            
            String width = k.nextLine();
            width = width.substring(0,width.indexOf(" ")); // 512 512 or num" "num
            widthNum = Integer.parseInt(width);
            imageArray = new Colour[widthNum][widthNum];
            System.out.print(widthNum);
            writer.write("P3\n"+widthNum+" "+widthNum+"\n255\n");

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
                    System.out.println(lineNum+" "+indexNum);
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

            createQuadTree();
            

            

            
        } catch (Exception e) {
            System.out.print("Error");
            e.printStackTrace();
        }

       
        /*
        try {
        
            for(int i = 0 ; i < 512 ; i++){
                for(int j = 0 ; j < 512 ; j++){
                    writer.write(imageArray[i][j]);
                }
                writer.write("\n");
            }
    

        } catch (Exception e) {
            //TODO: handle exception
        }
        
        //temp.renameTo(newFile);
       */
    }


    private static void createQuadTree(){

        tree.addRoot(new Colour(),0,widthNum,0,widthNum);
        addToQuadTree(tree.root,0,widthNum,0,widthNum);
        colourSetter();
    }

    private static void addToQuadTree(Position p,int x1,int x2 ,int y1,int y2){
        
        /**
         * x1 is the start index x2 is the end index we divide the given position into four childs
         * in 2D 
         */

        //new x2 is ((x2-x1)/2)+x1
        int newX2 = ((x2-x1)/2)+x1;

        int newY2 = ((y2-y1)/2)+y1;

        if(newX2-x1 != 1 ){ //if it is 1 it means it is a pixel and it has a rgb value.
            tree.addNW(p,new Colour(),x1,newX2,y1,newY2); 
        }else{
            //TODO do the base case
            Colour rgb = imageArray[x1][y1];
            tree.addNW(p,rgb,x1,newX2,y1,newY2); 
        }

        


        //new x1 is x2-(x2-x1/2)
        int newX1 = x2-((x2-x1)/2);

        //newY2 is SAME AS LINE ABOVE

        if( x2-newX1 != 1 ){ //if it is 1 it means it is a pixel and it has a rgb value.
            tree.addNE(p,new Colour(),newX1,x2,y1,newY2); 
        }else{
            Colour rgb = imageArray[newX1][y1];
            tree.addNE(p,rgb,newX1,x2,y1,newY2); 
        }


        
        //new x2 is ((x2-x1)/2)+x1
        newX2 = ((x2-x1)/2)+x1;

        int newY1 = ((y2-y1)/2)+y1;
  
        if(newX2-x1 != 1 ){ //if it is 1 it means it is a pixel and it has a rgb value.
            tree.addSW(p,new Colour(),x1,newX2,newY1,y2); 
        }else{
            Colour rgb = imageArray[x1][newY1];
            tree.addSW(p,rgb,x1,newX2,newY1,y2); 
        }



        newX1 = x2-((x2-x1)/2);
        newY1 = ((y2-y1)/2)+y1;
        if(x2-newX1 != 1 ){ //if it is 1 it means it is a pixel and it has a rgb value.
            tree.addSE(p,new Colour(),newX1,x2,newY1,y2); 
        }else{
            Colour rgb = imageArray[newX1][newY1];
            tree.addSE(p,rgb,newX1,x2,newY1,y2); 
            return;
        }

        addToQuadTree(tree.NW(p), x1, newX2, y1, newY2);
        addToQuadTree(tree.NE(p), newX1, x2, y1, newY2);
        addToQuadTree(tree.SW(p), x1, newX2, newY1, y2);
        addToQuadTree(tree.SE(p), newX1, x2, newY1, y2);
       
        
        

    }
    
    protected static void colourSetter(){
        

        tree.colourSetterAux(tree.root());
        tree.printRoot();

    }

    
    


    public static void main(String[] args){

        readImage("kira.ppm");

    }

}