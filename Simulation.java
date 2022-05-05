import java.io.File;
import java.io.IOException;

public class Simulation {

    // gravitational constant
    public static final double G = 6.6743e-11;
    // one astronomical unit (AU) is the average distance of earth to the sun.
    public static final double AU = 150e9;
    // all quantities are based on units of kilogram respectively second and meter.

    // The main simulation method using instances of other classes.
    public static void main (String[] args){

        //todo: enable user input
        //todo: extend simulation to use all classes
        
        //Example cosmic system:
        Body sun = new Body("Sol", 1.989e30, 696340e3, new Vector3(0,0,0) ,new Vector3(0,0,0), StdDraw.YELLOW);
        Body earth = new Body("Earth", 5.972e24, 6371e3, new Vector3(148e9,0,0) ,new Vector3(0,29.29e3,0), StdDraw.BLUE);
        Body mercury = new Body("Mercury", 3.301e23, 2.4397e3, new Vector3(-46.0e9,0,0) ,new Vector3(0,-47.87e3,0), StdDraw.RED);
        Body venus = new Body("Venus",4.86747e24,6052e3,new Vector3(-1.707667e10,1.066132e11,2.450232e9),new Vector3(-34446.02,-5567.47,2181.10),StdDraw.PINK);
        Body mars = new Body("Mars",6.41712e23,3390e3,new Vector3(-1.010178e11,-2.043939e11,-1.591727E9),new Vector3(20651.98,-10186.67,-2302.79),StdDraw.RED);
        
        ComplexCosmicSystem bodies = new ComplexCosmicSystem("SolarSystem", sun, earth, mercury, venus, mars);
        
        
        System.out.println("Running simulation...");

        StdDraw.setCanvasSize(500, 500);
        StdDraw.setXscale(-2*AU,2*AU);
        StdDraw.setYscale(-2*AU,2*AU);
        StdDraw.enableDoubleBuffering();
        StdDraw.clear(StdDraw.BLACK);

        double seconds = 0;

        // simulation loop
        while(true) {
            seconds++; // each iteration computes the movement of the celestial bodies within one second.

            // for each body compute the total force exerted on it.
            for (Body body : bodies) {
                body.setForce(new Vector3(0, 0, 0));

                for (Body body2 : bodies) {
                    if (body.equals(body2)) continue;

                    Vector3 forceToAdd = body.gravitationalForce(body2);
                    body.setForce(body.getForce().plus(forceToAdd));
                }
            }

            // move each body according to the total force exerted on it.
            for (Body body : bodies) {
                body.move();
            }

            // show all movements in StdDraw canvas only every 3 hours (to speed up the simulation)
            if (seconds%(3*3600) == 0) {
                // clear old positions (exclude the following line if you want to draw orbits).
                StdDraw.clear(StdDraw.BLACK);

                // draw new positions
                for (Body body : bodies) {
                    body.draw();
                }
                // show new positions
                StdDraw.show();
            }
        }
    }
}

