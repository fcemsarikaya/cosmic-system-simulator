import java.awt.*;

// This class represents vectors in a 3D vector space.
public class Vector3 {

    private double x;
    private double y;
    private double z;

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public double getX() {return x;}
    public double getY() {return y;}
    public double getZ() {return z;}

    public void setX(double x) { this.x = x;}
    public void setY(double y) { this.y = y;}
    public void setZ(double z) { this.z = z;}



    // Returns the sum of this vector and vector 'v'.
    public  Vector3 plus(Vector3 v) {
        Vector3 result = new Vector3(0, 0, 0);
        result.x = x + v.x;
        result.y = y + v.y;
        result.z = z + v.z;
        return result;
    }

    // Returns the product of this vector and 'd'.
    public Vector3 times(double d) {
        Vector3 result = new Vector3(0, 0, 0);
        result.x = x * d;
        result.y = y * d;
        result.z = z * d;
        return result;
    }

    // Returns the sum of this vector and -1*v.
    public Vector3 minus(Vector3 v) {
        Vector3 result = new Vector3(0,0,0);
        result.x = x + (-1 * v.x);
        result.y = y + (-1 * v.y);
        result.z = z + (-1 * v.z);
        return result;
    }

    // Returns the Euclidean distance of this vector
    // to the specified vector 'v'.
    public double distanceTo(Vector3 v) {
        double dX = x - v.x;
        double dY = y - v.y;
        double dZ = z - v.z;
        return Math.sqrt(dX*dX+dY*dY+dZ*dZ);
    }

    // Returns the length (norm) of this vector.
    public double length() {
        Vector3 origin = new Vector3(0,0,0);
        return this.distanceTo(origin);
    }

    // Normalizes this vector: changes the length of this vector such that it becomes 1.
    // The direction and orientation of the vector is not affected.
    public void normalize() {
        double length = this.length();
        x = x / length;
        y = y / length;
        z = z / length;
    }

    // Draws a filled circle with a specified radius centered at the (x,y) coordinates of this vector
    // in the existing StdDraw canvas. The z-coordinate is not used.
    public void drawAsDot(double radius, Color color) {
        StdDraw.setPenColor(color);
        StdDraw.filledCircle(this.x, this.y, 1e9*Math.log10(radius));
    }

    // Returns the coordinates of this vector in brackets as a string
    // in the form "[x,y,z]", e.g., "[1.48E11,0.0,0.0]".
    public String toString() {
        return "[" + x + "," + y + "," + z + "]";
    }
}

