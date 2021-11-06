import java.util.prefs.NodeChangeEvent;

public class NBody {
    public static double readRadius(String filePath){
        In in = new In(filePath);
        int bodyNum = in.readInt();
        double radius = in.readDouble();
        return radius;
    }
    public static Planet[] readPlanets(String filePath){
        In in = new In(filePath);
        int bodyNum = in.readInt();
        int currNum = 0;
        double radius = in.readDouble();
        Planet[] StarArray = new Planet[bodyNum];
        while (currNum < bodyNum){
            double currXXPos= in.readDouble();
            double currYYPos= in.readDouble();
            double currXXVel= in.readDouble();
            double currYYVel= in.readDouble();
            double currMass= in.readDouble();
            String path = "images/";
            String gif = path + in.readString();
            Planet currPlanet = new Planet(currXXPos,currYYPos,
                    currXXVel,currYYVel,currMass,gif);
            StarArray[currNum]=currPlanet;
            currNum ++;
        }
        return StarArray;
    }
    public static void main(String[] args){
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename =args[2];
        double radius = NBody.readRadius(filename);
        Planet[] planets = NBody.readPlanets(filename);

        StdDraw.enableDoubleBuffering();

        StdDraw.setScale(-radius, radius);
        StdDraw.clear();
        StdDraw.picture(0, 0,"images/starfield.jpg"); /*不支持单引号*/
        for (int i = 0; i < planets.length; i++){
            planets[i].draw();
        }
        StdDraw.show();
        for (double initT = 0; initT < T; initT+=dt){
            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];
            for (int k = 0; k < planets.length; k++){
                xForces[k] = planets[k].calcNetForceExertedByX(planets);
                yForces[k] = planets[k].calcNetForceExertedByY(planets);
            }
            for (int j = 0; j < planets.length; j++){
                planets[j].update(dt,xForces[j],yForces[j]);
            }
            StdDraw.picture(0, 0,"images/starfield.jpg");
            for (int i = 0; i < planets.length; i++){
                planets[i].draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
        }
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }
}
