# NBodies-Simulation
A simulation of the movement of N particles in a plane, particles' movement based on gravitational forces between them

# Executing the simulation
After compilation, run NBody and pass in 3 command line arguments: a total time, a time step, and a universe data file (universe data files
located in data folder). For example:
> $java NBody 40000.0 25.0 data/planets.txt
Will execute the simulation for a time of 40000.0 with a time step of 25.0, the universe and associated particles will be constructed using
the data from planets.txt
