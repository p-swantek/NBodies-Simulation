package nbodies;

/**
 * Data type that represents a particle in the universe. The movement of this
 * particle will be determined by the net gravitational forces acting upon it
 * from other particles within the universe
 * 
 * @author Peter Swantek
 * @version 1.8
 */

public final class Planet {

    public static final double GRAVITATIONAL_CONSTANT = 6.67e-11;

    private double x; // current x coordinate of the particle
    private double y; // current y coordinate of the particle
    private double xVelocity; // velocity of the particle in the x coordinate
    private double yVelocity; // velocity of the particle in the y coordinate
    private double xAccel; // acceleration of the particle in the x coordinate
    private double yAccel; // acceleration of the particle in the y coordinate
    private double mass; // mass of this particle
    private String img; // image file associated with this particle

    private double xNetForce = 0.0; // net force acting on this particle in the
                                    // x coordinate
    private double yNetForce = 0.0; // net force acting on this particle in the
                                    // y coordinate

    /**
     * Constructs a new instance of a Planet in the universe
     * 
     * @param xCoord
     *            The initial x coordinate
     * @param yCoord
     *            The initial y coordinate
     * @param xVelo
     *            The initial x velocity
     * @param yVelo
     *            The initial y velocity
     * @param newMass
     *            The mass of this planet
     * @param newImageFile
     *            The image file to associate with this planet
     */
    public Planet(double xCoord, double yCoord, double xVelo, double yVelo, double newMass, String newImageFile) {

        x = xCoord;
        y = yCoord;
        xVelocity = xVelo;
        yVelocity = yVelo;
        xAccel = 0.0;
        yAccel = 0.0;
        mass = newMass;
        img = newImageFile;

    }

    /**
     * Get the current x coordinate
     * 
     * @return The x coordinate of this planet
     */
    public double getX() {
        return x;
    }

    /**
     * Get the current y coordinate
     * 
     * @return The y coordinate of this planet
     */
    public double getY() {
        return y;
    }

    /**
     * Get the current x velocity
     * 
     * @return The x velocity of this planet
     */
    public double getXVelocity() {
        return xVelocity;
    }

    /**
     * Get the current y velocity
     * 
     * @return The y velocity of this planet
     */
    public double getYVelocity() {
        return yVelocity;
    }

    /**
     * Get the current x acceleration
     * 
     * @return The x acceleration of this planet
     */
    public double getXAccel() {
        return xAccel;
    }

    /**
     * Get the current y acceleration
     * 
     * @return The y acceleration of this planet
     */
    public double getYAccel() {
        return yAccel;
    }

    /**
     * Get the current mass
     * 
     * @return The mass of this planet
     */
    public double getMass() {
        return mass;
    }

    /**
     * Get the image file name
     * 
     * @return The image file name associated with this planet
     */
    public String getImg() {
        return img;
    }

    /**
     * Get the current net force in the x coordinate
     * 
     * @return The x coordinate net force for this planet
     */
    public double getXNetForce() {
        return xNetForce;
    }

    /**
     * Get the current net force in the y coordinate
     * 
     * @return The y coordinate net force for this planet
     */
    public double getYNetForce() {
        return yNetForce;
    }

    /**
     * Calculates the distance from this planet to another planet in the
     * universe
     * 
     * @param other
     *            The planet to which distance should be calculated
     * @return The distance between the two planets
     */
    public double calcDistance(Planet other) {

        double deltaX = other.getX() - getX();
        double deltaY = other.getY() - getY();

        return Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));

    }

    /**
     * Calculates the total force acting upon two planets in the universe
     * 
     * @param other
     *            The planet to which the amount of force should be calculated
     * @return The force that is acting on these two planets
     */
    public double calcPairwiseForce(Planet other) {

        return getGravitationalForce(getMass(), other.getMass(), calcDistance(other)); // calculate
                                                                                       // the
                                                                                       // gravitational
                                                                                       // force
                                                                                       // between
                                                                                       // the
                                                                                       // two
                                                                                       // planets

    }

    /*
     * Calculate the force of gravity between two planets using Newton's law of
     * Gravitation
     */
    private static double getGravitationalForce(double mass1, double mass2, double distance) {

        return (GRAVITATIONAL_CONSTANT * mass1 * mass2) / (distance * distance);
    }

    /*
     * Calculates and sets the net force amount in the x and y directions for
     * this planet based on the gravitational forces acting upon it from other
     * planets
     */
    void setNetForce(Planet[] otherPlanets) {

        double deltaX = 0.0;
        double deltaY = 0.0;

        for (Planet p : otherPlanets) {

            if (p != this) {
                deltaX += calcPairwiseForceX(p);
                deltaY += calcPairwiseForceY(p);
            }
        }

        xNetForce = deltaX;
        yNetForce = deltaY;

        xAccel = getXNetForce() / getMass();
        yAccel = getYNetForce() / getMass();

    }

    /*
     * Calculates the x coordinate portion of the force acting on this planet
     */
    private double calcPairwiseForceX(Planet other) {

        return getForceVector(calcPairwiseForce(other), other.getX() - getX(), calcDistance(other));
    }

    /*
     * Calculates the y coordinate portion of the force acting on this planet
     */
    private double calcPairwiseForceY(Planet other) {

        return getForceVector(calcPairwiseForce(other), other.getY() - getY(), calcDistance(other));
    }

    /*
     * calculates the the amount of force acting on this planet for either the x
     * or y coordinate
     */
    private static double getForceVector(double netForce, double distDelta, double distance) {
        return (netForce * distDelta) / distance;
    }

    /*
     * Updates the x and y velocities of this planet
     */
    void updateVelocity(double dt) {

        xVelocity = getXVelocity() + dt * getXAccel();
        yVelocity = getYVelocity() + dt * getYAccel();

    }

    /*
     * Updates the x and y coordinates of this planet
     */
    void updatePosition(double dt) {

        x = getX() + dt * getXVelocity();
        y = getY() + dt * getYVelocity();

    }

    @Override
    public String toString() {

        return String.format(
                "Planet: %s, XVelocity: %f, YVelocity: %f, XAcceleration: %f, YAcceleration: %f, Mass: %f, PosX: %f, PosY: %f",
                getImg(), getXVelocity(), getYVelocity(), getXAccel(), getYAccel(), getMass(), getX(), getY());

    }

}
