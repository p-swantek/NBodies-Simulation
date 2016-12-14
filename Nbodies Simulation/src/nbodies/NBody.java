package nbodies;

/**
 * Simulates N particles in a plane, particles move due to the gravitational forces mutually affecting each particle as demonstrated by Sir Issac
 * Newton's Law of Universal Gravitation. Takes in 3 command line arguments: the length of the simulation, the size of a time step in the simulation,
 * and a text file containing planetary data from which to build the universe for the simulation. Theme from 2001: A Space Odyssey plays during the
 * simulation
 * 
 * @author Peter Swantek
 * @version 1.8
 *
 */

public final class NBody {

    private static int N; // amount of particles
    private static double universeSize; // radius of the universe
    public static final String SOUNDTRACK_FILE = "audio/2001.mid"; // 2001: A Space Odyssey theme song

    public static final int EXIT_SUCCESS = 0;
    public static final int EXIT_FAILURE = 1;

    /**
     * Builds a new instance of a Planet using data from a text file
     * 
     * @param in Input stream from a text file containing planetary data
     * @return A Planet instance with the properties obtained from a text file for planetary data
     */
    public static Planet getPlanet(In in) {

        String data = in.readLine();
        String[] dataValues = data.split(" ");

        double x = Double.valueOf(dataValues[0]);
        double y = Double.valueOf(dataValues[1]);
        double xVelo = Double.valueOf(dataValues[2]);
        double yVelo = Double.valueOf(dataValues[3]);
        double mass = Double.valueOf(dataValues[4]);
        String img = dataValues[5];

        return new Planet(x, y, xVelo, yVelo, mass, img);

    }

    /**
     * Build the universe for the simulation using a text file of data for a particular simulation
     * 
     * @param in Input stream from a text file containing data for a particular simulation
     * @return An array of Planet objects within the universe that will be involved in the simulation
     */
    public static Planet[] buildUniverse(In in) {

        N = Integer.parseInt(in.readLine());
        universeSize = Double.parseDouble(in.readLine());
        Planet[] planets = new Planet[N];

        int i = 0;
        while (in.hasNextLine()) {
            planets[i++] = getPlanet(in);
        }

        in.close();

        return planets;

    }

    /**
     * Draw the universe that is being simulated using StdDraw API. Draw the N particles and make the background a space image. Use the radius of the
     * universe to scale the canvas
     * 
     * @param planets An array of Planets (particles) that should be drawn in the universe
     */
    public static void drawUniverse(Planet[] planets) {
        StdDraw.setXscale(-universeSize, universeSize);
        StdDraw.setYscale(-universeSize, universeSize);
        StdDraw.picture(0.0, 0.0, "images/starfield.jpg");
        for (Planet p : planets) {
            StdDraw.picture(p.getX(), p.getY(), "images/" + p.getImg());

        }
    }

    /**
     * Runs the Nbodies simulation for a given universe
     * 
     * @param totalTime The total time of the simulation
     * @param dt The amount of time each simulation step will take
     * @param planets The array of Planets that will be involved in the simulation
     */
    public static void runSimulation(double totalTime, double dt, Planet[] planets) {

        for (double t = 0.0; t < totalTime; t += dt) {
            StdDraw.show(25);

            for (Planet p : planets) {
                p.setNetForce(planets);
            }

            for (Planet p : planets) {
                p.updateVelocity(t);
            }

            for (Planet p : planets) {
                p.updatePosition(t);
            }

            drawUniverse(planets);

        }
    }

    /**
     * After the simulation has completed, print to standard output the updated data for each particle in the plane
     * 
     * @param planets The array of Planets that was involved in the simulation
     */
    public static void simulationOutput(Planet[] planets) {
        System.out.println(N);
        System.out.println(universeSize);
        for (Planet p : planets) {
            System.out.println(String.format("%7.4e %7.4e %7.4e %7.4e %7.4e %s", p.getX(), p.getY(), p.getXVelocity(),
                    p.getYVelocity(), p.getMass(), p.getImg()));
        }
    }

    public static void main(String[] args) {

        if (args.length != 3) {
            System.out.println("Incorrect amount of arguments.\nUsage: java Nbody <time> <time step> <universe file>");
            System.exit(EXIT_FAILURE);
        }

        try {
            Planet[] planets = buildUniverse(new In(args[2]));
            drawUniverse(planets);
            StdAudio.loop(SOUNDTRACK_FILE);
            runSimulation(Double.parseDouble(args[0]), Double.parseDouble(args[1]), planets);
            simulationOutput(planets);
        } catch (Exception e) {
            System.out.println("Faulty command line arguments were supplied.");
            System.exit(EXIT_FAILURE);
        }

        System.exit(EXIT_SUCCESS);

    }

}
