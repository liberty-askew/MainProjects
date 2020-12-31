import numpy as np
import matplotlib.pyplot as plt

def ms2kmh(v):
    """ 
    Convert from m/s to km/h.
    """
    return v*60*60/1000

def kmh2ms(v): 
    """ 
    Convert from km/h to m/s.
    """
    return v*1000/60/60

class TrafficODEBase:
    """
    A class to simulate traffic behaviour in terms of N cars on a
    circle, modelled with ordinary differential equations.
    """

    def __init__(self, N, vmax, s=1.8, bc=7.0, bf=25.0, m=0.0860, road_length=1000):
        """
        :param N:           number of cars.
        :param vmax:        maximal allowed velocity.
        :param s:           sensitivity of the drivers.
        :param bc:          length of car + safe distance to next car.
        :param bf:          another parameter to tune v_goal.
        :param m:           another parameter to tune v_goal.
        :param road_length: length of the road in meters.
        """
        self.N           = N
        self.vmax        = vmax   
        self.s           = s     
        self.bc          = bc
        self.bf          = bf
        self.m           = m
        self.dt          = 0.05   # timestep in seconds
        self.t           = 0
        self.road_length = road_length;
        self.detector    = 0
        # vgoal is proportional to v0, which can be computed in terms
        # of vmax, and is then stored in self.v0.
        self.v0          = self.vmax/(1-np.tanh(self.m*(self.bc-self.bf)))
        self.setup_cars()

    def setup_cars(self, single=False, random=False, strength=0.8):
        """
        Position the cars equally spaced and with optimal velocity.
        Optionally perturb either one or all cars randomly.

        :param single:   perturb the first car's position if True.
        :param random:   perturb all positions and velocities if True.
        :param strength: strength of the random perturbation.
        """
        self.x  = np.linspace(0, self.road_length, self.N, endpoint=False)

        # Make all velocities optimal.
        headway = np.append(self.x[1:] - self.x[:-1], self.x[0]+self.road_length-self.x[-1])
        self.v  = self.vgoal(headway)

        # Perturb the car positions and velocities if requested.
        if single:
            self.x[0] += strength*(self.x[1]-self.x[0])
        if random:
            self.x += strength*np.random.rand(self.N)*(self.x[1]-self.x[0])
            self.v -= strength*np.random.rand(self.N)*self.vmax
            self.v  = np.clip(self.v, 0, self.vmax)
      
    def vgoal(self, headway):
        """ 
        Compute the optimal velocity, given the headway. 
        """
        raise NotImplementedError

    def plot_free_flow_density(self):
        """
        Make a flow-density plot for free flow using the theoretical curve.
        """
        raise NotImplementedError

    def update(self):
        """ 
        Update all positions & velocities using Euler integration. Also update the
        self.detector variable.
        """
        raise NotImplementedError
    
    def simulate(self, seconds, plot=False, filename='', fig_step=5):
        """ 
        Simulate traffic flow and optionally make a spacetime plot.

        :param seconds : simulation duration
        :param plot    : make a spacetime plot of the traffic if True.
        :param filename: filename for pdf figure (if not '')  
        :param fig_step: interval between figure steps
        :return        : (density, flow) tuple
        """
        steps = seconds/self.dt
        try:
            self.detector=0
            self.t=0
            for step in range(int(steps)):
                self.update()
                self.t+=self.dt
                if plot and step%fig_step==0:
                    plt.plot(self.x, np.ones(self.N)*self.dt*step, 'b.', markersize=2)
        except ValueError as estr:
            print(estr)

        if plot:
            plt.ylabel('time (s)')
            plt.xlabel('position (m)')
            plt.title(r'trajectories: $b_f$= '+str(self.bf)+ r', $b_c$= '+\
                      str(self.bc) +r', $m$= '+ str(self.m)+ r', $s$= '+\
                      str(self.s))
            if filename!='':
                print("saving plot as", filename)
                plt.savefig(filename, bbox_inches='tight')
            plt.show()

        return (self.N/self.road_length*1000, 60*self.detector/self.t)

    def plot_velocities(self, filename=''):
        """
        Make a plot of the velocities versus the car number at the
        current instance of time.

        :param filename: filename for the pdf figure (if not '')  
        """
        n = np.arange(0, self.N)
        plt.plot(n, ms2kmh(self.v), '.-')
        plt.xlabel('car number')
        plt.ylabel('velocity (km/h)')
        plt.ylim([0, ms2kmh(self.vmax)*1.1])
        plt.title(r'Car velocity : $\rho$='\
                  +("%.2f"% float(self.N/self.road_length*1000))\
                  +" cars/km, time = "+("%.2f"% self.t)+"s")
        if filename!='':
            print("saving plot as", filename)
            plt.savefig(filename, bbox_inches='tight')
        plt.show()
        
    def plot_vgoal(self, filename=''):
        """ 
        Plot the optimal velocity versus the headway.

        :param filename: filename for the pdf figure (if not '')  
        """
        dx = np.linspace(1.0, 50, 30)
        vg = list(map(lambda d: ms2kmh(self.vgoal(d)), dx))
        plt.plot(dx, vg)
        plt.plot(dx, ms2kmh(self.vmax)*np.ones(len(dx))) 
        plt.ylabel('velocity (km/h)')
        plt.xlabel('headway (m)')
        plt.ylim([0, 1.1*ms2kmh(self.vmax)]) 
        plt.legend([r'$v_{\rm goal}$',r'$v_{\rm max}$'])
        title = r'$v_{goal}$ for $b_f$ = '+str(self.bf)+ r', $b_c$= '+str(self.bc) +\
                r', $m$= '+str(self.m)+ r', $s$= '+str(self.s)
        plt.title(title) 
        if filename!='':
            print("saving plot as", filename)
            plt.savefig(filename, bbox_inches='tight')
        plt.show()

    def is_stable(self, b):
        """ 
        Check if the homogeneous flow is stable.

        :param b: distance between cars.
        """
        crit = 2*self.v0*self.m/np.cosh(self.m*(b-self.bf))**2/self.s
        return crit<1

    def flow_density_plot(self, seconds, filename=''):
        """
        Simulate traffic flow for a time 'seconds' and then plot the
        flow versus density.

        :param seconds : simulation duration.
        :param filename: filename for the pdf figure (if not '').
        """
        rhos=[]
        flows=[]
        self.plot_free_flow_density()
        for cars in np.arange(10,80,2):
            self.N=cars
            b=self.road_length/cars
            print("density "+str(1000/b)+" (cars/km) stable: "+str(self.is_stable(b)))
            self.setup_cars(random=True, strength=0.3)
            flow = self.simulate(seconds)
            rhos.append(flow[0])
            flows.append(flow[1])
        plt.plot(rhos,flows,'bo')
        plt.xlabel('density (cars/km)')
        plt.ylabel('flow (cars/min)')
        plt.legend(['free flow', 'simulated'])
        plt.title(r'$b_f$= '+str(self.bf)+ r', $b_c$= '+str(self.bc) +\
                  r', $m$= '+str(self.m)+ r', $s$= '+str(self.s)) 
        if filename!='':
            print("saving plot as", filename)
            plt.savefig(filename, bbox_inches='tight')
        plt.show()
