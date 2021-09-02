import java.util.Scanner;
import java.util.Vector;

//import jdk.javadoc.internal.tool.resources.javadoc;


public class NBody {
    private static int N;//represent existing N planets of the universe
    private static double R;//represent the radius of the universe
    private static Planet[] universe;//represent all the planets existing in the universe
    public static double readRadius(String path) {
        double result=0.0;
        int i=0;
        In in = new In(path);
        //the radius of the universe is in the 2nd line
        while(i<2){
            result=in.readDouble();
            i++;
        }
        return result;
    }
    private static boolean isNumber(String num) {
        try{
            double inValue = Double.parseDouble(num);
            return true;
        }catch(NumberFormatException e) {
            //do nothing
        }
        return false;
    }
    public static Planet[] readPlanets(String path) {
        In in=new In(path);
        int i=0;
        //move to the start of the planet data
        while(i<2) {
            in.readLine();
            i++;
        }
        
        Vector                      <Planet> planets = new Vector<Planet>();
        //read the data of every planet
        while(in.hasNextLine()) {
           
            //double xP = in.readDouble();
            String xpS;
            if(in.hasNextChar()) {
                xpS = in.readString();
            }else {
                break;
            }
            
            if(!isNumber(xpS)) {
                break;
            }
            double xP = Double.parseDouble(xpS);
            double yP = in.readDouble();
            double xV = in.readDouble();
            double yV = in.readDouble();
            Double m = in.readDouble();
            String im = in.readString();

            Planet theP=new Planet(xP, yP, xV, yV, m, im);
            planets.add(theP);

        }
        Planet [] planetsArr = new Planet[planets.size()];
        for(int j=0;j<planets.size();j++) {
            planetsArr[j] = planets.elementAt(j);
        }
        return planetsArr;
    }
    /**draw the background of the universe */
    private static void drawBackground() {
        String imageToDraw = "images/starfield.jpg";
        StdDraw.setScale(-R,R);

		/* Clears the drawing window. */
		StdDraw.clear();

		/* Stamps three copies of advice.png in a triangular pattern. */
		StdDraw.picture(0, 0, imageToDraw);

		/* Shows the drawing to the screen, and waits 2000 milliseconds. */
		//StdDraw.show();
		//StdDraw.pause(2000);
    }
    /**draw all the planets */
    private static void drawPlanets() {

        for(int i = 0;i < N;i++) {
            String imageToDraw = "images\\" + universe[i].imgFileName;
            /* Stamps three copies of advice.png in a triangular pattern. */
            StdDraw.picture(universe[i].xxPos, universe[i].yyPos, imageToDraw);
            /* Shows the drawing to the screen, and waits 2000 milliseconds. */
            //StdDraw.show();
            //StdDraw.pause(200);
        }
        
    }
    /**create an animation */
    private static void animation(double T, double dt) {
        StdDraw.enableDoubleBuffering();
        
        double time=0;
        while(time < T) {
            
            drawBackground();
            drawPlanets();
            StdDraw.show();
            StdDraw.pause(5);

            for(Planet p:universe) {
                double fX = p.calcNetForceExertedByX(universe);
                double fY = p.calcNetForceExertedByY(universe);
                p.update(dt, fX, fY);
                //System.out.printf("star %s: position is (%f,%f) \n",p.imgFileName ,p.xxPos, p.yyPos);
            }
            
            time += dt;
        }
    }
    /**this is the main method of the whole project */
    public static void main(String[] args){
        
        //input T dt filename
        Scanner scanner = new Scanner(System.in);
        //System.out.println("T:");
        //double T = 1e10;
        //double T = Double.parseDouble(scanner.nextLine());
        double T = scanner.nextDouble();
        //System.out.println("dt:");
        //double dt = 1e5;giy
        //double dt = Double.parseDouble(scanner.nextLine());
        double dt = scanner.nextDouble();
        //System.out.println("filename:");
        String filename = scanner.next();
        //String filename = "data\\awesome.txt";
        scanner.close();

        R = readRadius(filename) ;
        universe = readPlanets(filename);
        N = universe.length;
        
        //show the animation
        animation(T, dt);

        StdOut.printf("%d\n", universe.length);
        StdOut.printf("%.2e\n", R);
        for (int i = 0; i < universe.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                        universe[i].xxPos, universe[i].yyPos, universe[i].xxVel,
                        universe[i].yyVel, universe[i].mass, universe[i].imgFileName);   
        }
    }
}

