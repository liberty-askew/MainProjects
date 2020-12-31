# Solve a system of ODE   
# dy/dt = F(y)
# where F and y are N components vectors
import numpy as np
import matplotlib.pyplot as plt

"""
The pde_rk4 module contains a class to integrate a system of partial
differential equations using the 4th order Runge Kutta method
"""

class PDE_RK4:
  """A class to integrate a system of PDE using Runge Kutta 4th order"""

  def __init__(self, L, N, t0=0):
     """
     :param L : size of the domain
     :param N : number of grid points
     :param t0: initial time
     """
     self.L = L    # The size of the domain
     self.N = N    # The number of points on the lattice
     self.t = t0   # Integration variable
     self.dx = self.L/(self.N-1) # lattice spacing
     self.dt = 0   # Integration step (very equation dependant)
     self.v = np.zeros(N, dtype='float64') # ensure we use floats!
     self.l_t = []  # list of t values for figures
     self.l_f = []  # list of f values (arrays) for figures

  def set_dt(self,dt):
     """ Overwides the integration time step"""
     self.dt = dt

  def initial_condition(self, t0=0):
     """ Compute the initial condition. This is very specific to each equation
     and to the prblem being solved.

     :param t0: initial time
     """
     self.v = np.zeros(N, dtype='float64')
     self.dt = 0
     self.t = t0
          
  def F(self, t, v):
      """ THIS FUNCTION MUST BE CHANGED IN THE CHILD CLASS
       Returns the right hand side of the equation: dv/dt = f(v,t) 

       :param t : current time
       :param v : curent fct value (a vector)
       """
      return(np.array(-1.0*v)) # example : dv/dt = -v


  def RK4_1step(self):
     """ Perform a single integration setp using
         the 4th order Runge Kutta step."""
     k1 = self.F(self.t,self.v)
     K = self.v+0.5*self.dt*k1
     self.boundary(K)

     k2 = self.F(self.t+0.5*self.dt,K)
     K = self.v+0.5*self.dt*k2
     self.boundary(K)

     k3 = self.F(self.t+0.5*self.dt,K)
     K = self.v+self.dt*k3
     self.boundary(K)
 
     k4 = self.F(self.t+self.dt,K)
     self.v += self.dt/6.0*(k1+2.0*(k2+k3)+k4)
     self.boundary(self.v)

     self.t += self.dt


  def iterate(self, tmax, fig_dt=-1):
     """ Integrate equation up to tmax and add figure date in list
         l_t and l_f every fig_dt
         If fig_dt <= 0 : no data are stored in l_t and l_f 

      :param tmax   : integration upper bound
      :param fig_dt : interval between data point for figures (use dt if < 0)
     """
     next_fig_t = self.t+fig_dt
     # add initial value to figure data
     self.l_t.append(self.t)
     self.l_f.append(np.array(self.v))
     
     while(self.t <= tmax):
       self.RK4_1step()
       if(fig_dt > 0 and (self.t+self.dt*0.5) >next_fig_t):
         self.l_t.append(self.t)
         self.l_f.append(np.array(self.v)) # we must make a copy of v
         next_fig_t += fig_dt

  def plot(self, i, j=0, format="k-"):
      """ plot V[i] versus t    (i > 1 and j = 0)  using style
          plot V[i] versus V[j] (i > 1 and j > 1)  using style
          plot t    versus V[j] (i = 0 and j > 1)  using style

      :param i : index of function for abscissa 
      :param j : index of function for ordinate
      :param format : format for the plot function
      """

      if(j==0):
        lx = self.l_t
      else:
        lx = list(map (lambda v : v[j-1] , self.l_f))
      if(i==0):
        ly = self.l_t
      else:
        ly = list(map (lambda v : v[i-1] , self.l_f))
      plt.plot(lx,ly,format);

  def last_fig_index(self):
    """ return the index of the last figure profile
    """
    return(len(self.l_f)-1)
