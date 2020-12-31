import numpy as np
import matplotlib.pyplot as plt

class TrafficDiscreteBase:
    """
    A class with helper methods and storage set up to simulate traffic
    behaviour in terms of N cars on a circular road in terms of a
    discrete road model.
    """

    def __init__(self, density=0.1, vmax = 3, road_len=200, p_slowdown=0.1):
        """
        :param density:       density of cars.
        :param vmax:          maximal velocity of cars.
        :param road_len:      length of the road in segments (periodic boundary conditions).
        :param p_slowdown:    probability of cars slowing down at every step.
        """
        self.density       = density
        self.vmax          = vmax
        self.road_len      = road_len
        self.p_slowdown    = p_slowdown
        self.v             = -np.ones(self.road_len) # velocity in each segment, -1 if no car
        self.detector      = 0                       # count cars reaching end of road
        self.fill_road_randomly()

    def fill_road_randomly(self):
        """ 
        Fill the road with random cars at approximate density 'self.density', 
        adjust self.density afterwards to the exact value of the density.
        """
        # Create an array filled with True or False, with True occuring
        # approximately at density 'self.density'.
        onoff = np.random.rand(self.road_len) < self.density

        # Create an array with random velocities for each segment,
        # taken between 0 and self.vmax.
        veloc = np.random.randint(self.vmax+1, size=self.road_len)

        # Set velocities, or -1 if there is no car in a segment.
        self.v = np.choose( onoff, [-1, veloc] )
        self.density = len(np.where( onoff )[0])/self.road_len
        
    def step(self):
        """ 
        Perform one update cycle of the system. Also updates the car counter 
        in self.detector when a car moves past the detector at the 'end' of the
        road.
        """
        raise NotImplementedError

    def evolve(self, steps, store=True):
        """ 
        Follow the current configuration for a number of steps, 
        store in self.mat. Resets the car detector at the start.

        :param steps: number of simulation steps.
        :param store: determines whether to keep track of the history in self.mat.
        """
        self.detector=0
        self.mat = np.array([self.v])
        for i in range(steps):
            self.step()
            if store:
                self.mat=np.vstack([self.mat, self.v])
        
    def make_plot(self, filename=''):
        """ 
        Make a space-time plot of the traffic collected in self.mat. 

        :param filename: filename for the pdf figure (if not '')  
        """
        np.set_printoptions(threshold=np.nan)
        plt.imshow(self.mat >= 0, interpolation='nearest', cmap='Greys')
        plt.xlabel('position (segment)')
        plt.ylabel('time (timestep)')
        plt.title(r'$\rho$= '+str(self.density) + r', $v_{\rm max}$= '+ str(self.vmax))
        if filename!='':
            print("saving plot as", filename)
            plt.savefig(filename, bbox_inches='tight')
        plt.show()

    def flow_density(self, density, steps):
        """ 
        Determine the flow given an approximate density.

        :param density:    traffic density.
        :param steps:      number of steps to iterate.
        :return:           tuple of (density, flow).
        """
        raise NotImplementedError

    def flow_density_plot(self, steps):
        """
        Make a flow-density plot (do not show yet so different ones can be overlapped).

        :param steps: number of iteration steps.
        """
        rhos  = np.linspace(0.05, 1.0, 40)
        rhos, flows = zip( *list(map(lambda rho: self.flow_density(rho, steps), rhos)) )
        plt.plot(rhos, flows, 'o')
        plt.xlabel('density (cars/segment)')
        plt.ylabel('flow (cars/timestep)')

    def flow_density_comparison_plot(self, steps, vmaxs, filename=''):
        """
        Make a flow-density plot for a list of velocities.

        :param steps:    number of iteration steps.
        :param vmaxs:    list of values of vmax for which plots should be made.
        :param filename: filename for the pdf figure (if not '')  
        """
        print("creating flow-density plots for "+str(vmaxs)+", wait...")
        for v in vmaxs:
            self.vmax=v
            self.flow_density_plot(steps)
        plt.title("flow-density plot for vmax="+str(vmaxs))
        plt.xlabel("density (cars/segment)")
        plt.ylabel("flow (cars/timestep)")
        plt.legend(list(map(lambda v: "vmax ="+str(v), vmaxs)))
        if filename!='':
            print("saving plot as", filename)
            plt.savefig(filename, bbox_inches='tight')
        plt.show()

    def average_velocity(self):
        """
        Compute the average velocity of all cars on the road.

        :return:  the average velocity.
        """
        cars = np.where(self.v!=-1)[0]
        sumv = np.sum(self.v[cars])
        return sumv/len(cars)
        
    def average_velocity_density_plot(self, steps, filename=''):
        """
        Make a plot of the average velocity versus the density.

        :param steps:    number of simulation steps.
        :param filename: filename for the pdf figure (if not '')  
        """
        print("creating velocity-density plot, wait...")
        trhos = np.linspace(0.05, 1.0, 20)
        rhos = []
        vs   = []
        for rho in trhos:
            self.density=rho
            self.fill_road_randomly()
            rhos.append(self.density)
            self.evolve(steps, store=False)
            vs.append(self.average_velocity())
        plt.plot( rhos, vs, 'bo' )
        plt.xlabel("density (cars/segment)")
        plt.ylabel("average velocity (segment/s)")
        plt.ylim([0, np.max(vs)+0.2])
        plt.title(r'$v_{\rm max}$= '+ str(self.vmax)+r", $p_{\rm slowdown}$="+str(self.p_slowdown))
        if filename!='':
            print("saving plot as", filename)            
            plt.savefig(filename, bbox_inches='tight')
        plt.show()

    
