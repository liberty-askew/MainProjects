import matplotlib.pyplot as plt 
import numpy as np
from ode_rk4 import ODE_RK4

class LotkaVolterraRK4(ODE_RK4):

    def __init__(self, V0=[0], dt=0.1, t0=0, K=1):
      """ Set the variables used by the class:

      :param V0 : initial value (as an array or a list) 
      :param dt : integration time step
      :param t0 : initial time
      :param K  : Lotka Volterra parameter
      """
      super().__init__(V0,dt,t0) 
      self.K=K

    def set_K(self,K):
      """ Set the parameter K

      :param K : Lotka_Volterra model parameter 
      """
      self.K = K
    
    def  F(self,t,V):
      """ Return the right hand side of the equation dV/dt = F(t,V)

      :param t  : current time
      :param V0 : current function values
      """
      u = V[0]
      v = V[1]
      return(np.array([1 - (1 + 1)*u + (u**2)*v, 
                       1 * u - (u**2) * v ]))

# Only run this when not importing the module.
  
if __name__ == "__main__":
    # Solve the Lotka_Volterra model for some parameters
    lv = LotkaVolterraRK4(V0=[0.1,0.1], dt=0.1, K=1)
    lv.iterate(40,0.01)
    # plot N(t) and P(t)
    lv.plot(1,0,"r-")
    lv.plot(2,0,"b-")
    plt.xlabel("t", fontsize=22)
    plt.ylabel("Population", fontsize=22)
    plt.legend(['Preys', 'Predators'])   
    plt.show()
    # plot P(N)        
    lv.plot(1,2,"g-")  
    plt.xlabel("u", fontsize=22)
    plt.ylabel("v", fontsize=22)
    plt.axis('equal')
    plt.margins(0.1, 0.1) 
    plt.show()

