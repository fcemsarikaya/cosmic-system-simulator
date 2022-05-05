import java.awt.*;
import java.io.IOException;

// This class represents celestial bodies like stars, planets, asteroids, etc..
public class Body implements CosmicComponent, Cluster {

    private final String name;
    private final double mass;
    private final double radius;
    private Vector3 position; // position of the center.
    private Vector3 currentMovement;
    private final Color color; // for drawing the body.
    private Vector3 force;

    public Body(String name, double mass, double radius, Vector3 position, Vector3 currentMovement, Color color) {
        this.name = name;
        this.mass = mass;
        this.radius = radius;
        this.position = position;
        this.currentMovement = currentMovement;
        this.color = color;
        this.force = null;
    }

    public Body(Body body) {
        this.name = body.name;
        this.mass = body.mass;
        this.radius = body.radius;
        this.position = body.position;
        this.currentMovement = body.currentMovement;
        this.color = body.color;
        this.force = null;
    }


    public String getName() {return name;}
    public double getMass() {return mass;}
    public double getRadius() {return radius;}
    public Vector3 getPosition() {return position;}
    public Vector3 getCurrentMovement() {return currentMovement;}
    public Color getColor() {return color;}
    public Vector3 getForce() {return this.force;}

    public void setPosition(Vector3 position) { this.position = position; }
    public void setCurrentMovement(Vector3 currentMovement) { this.currentMovement = currentMovement; }
    public void setForce(Vector3 v) {this.force = v;}

    public Vector3 getMassCenter() {return position;}

    @Override
    public Cluster add(Body c) throws BalancedSystemIllegalArgumentException, IOException {
        return new BalancedSystem(this, c);
    }

    public int numberOfBodies() {return 1;}

    public boolean equals(Body body) {
        double epsilon = 0.0000001;
        double diff = Math.abs(this.mass - body.mass);
        int compString = this.getName().compareTo(body.getName());
        return (diff < epsilon) && compString == 0;
    }

    public int hashCode() {
        int hs = this.name.hashCode();
        return Double.hashCode(this.mass) + hs;
    }

    // Returns the distance between this body and the specified 'body'.
    public double distanceTo(Body body) {
        Vector3 position1 = this.getPosition();
        Vector3 position2 = body.getPosition();
        return position1.distanceTo(position2);
    }

    //Returns a vector representing the gravitational force exerted by 'body' on this body.
    //The gravitational Force F is calculated by F = G*(m1*m2)/(r*r), with m1 and m2 being the masses of the objects
    //interacting, r being the distance between the centers of the masses and G being the gravitational constant.
    //To calculate the force exerted on b1, simply multiply the normalized vector pointing from b1 to b2 with the
    //calculated force
    public Vector3 gravitationalForce(Body body) {
        Vector3 direction = body.getPosition().minus(this.getPosition());
        double distance = direction.length();
        direction.normalize();
        double force = Simulation.G *this.getMass()*body.getMass()/(distance * distance);
        return direction.times(force);
    }

    // Moves this body to a new position, according to the specified force vector 'force' exerted
    // on it, and updates the current movement accordingly.
    // (Movement depends on the mass of this body, its current movement and the exerted force)
    // Hint: see simulation loop in Simulation.java to find out how this is done
    public void move(Vector3 force) {
        Vector3 newPosition = this.currentMovement.plus(this.position.plus(
                force.times(1/this.mass))
                // F = m*a -> a = F/m
        );

        Vector3 newMovement = newPosition.minus(this.position); // new minus old position.

        this.position = newPosition;
        this.currentMovement = newMovement;
    }

    public void move() {
        Vector3 newPosition = this.currentMovement.plus(this.position.plus(
                this.force.times(1/this.mass))
                // F = m*a -> a = F/m
        );

        Vector3 newMovement = newPosition.minus(this.position); // new minus old position.

        this.position = newPosition;
        this.currentMovement = newMovement;
    }

    public void setState(Vector3 position, Vector3 velocity) {
        this.position = position;
        this.currentMovement = velocity;
    }


    // Returns a string with the information about this body including
    // name, mass, radius, position and current movement. Example:
    // "Earth, 5.972E24 kg, radius: 6371000.0 m, position: [1.48E11,0.0,0.0] m, movement: [0.0,29290.0,0.0] m/s."
    public String toString() {
        return name + ", " + mass + " kg, radius: " + radius + "m, position: " + "[" + position.getX() + "," + position.getY() + "," +
                position.getZ() + "]" + " m, movement: " +  "[" + currentMovement.getX() + "," + currentMovement.getY() + "," +  currentMovement.getZ() + "]" + " m/s.";
    }


    // Draws the body to the current StdDraw canvas as a dot using 'color' of this body.
    //    // The radius of the dot is in relation to the radius of the celestial body
    //    // (use a conversion based on the logarithm as in 'Simulation.java').
    //    // Hint: use the method drawAsDot implemented in Vector3 for this
    public void draw() {
        System.out.println(position);
        System.out.println(name);
        this.position.drawAsDot(radius, color);
    }

    @Override
    public BodyIterator iterator() {
        return new myIterator(this);
    }

    public static class myIterator implements BodyIterator {
        private final Body thisBody;
        private Body current;

        public myIterator(Body body){
            thisBody = body;
        }

        @Override
        public boolean hasNext() {
            return current == null;
        }

        @Override
        public Body next() {
            if (hasNext()) {return current = thisBody;}
            else {return null;}
        }

        @Override
        public void remove() {}

    }
}
