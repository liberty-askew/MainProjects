import sys
from pde_rk4 import PDE_RK4
import numpy as np
import matplotlib.pyplot as plt

class BrusselatorPDE(PDE_RK4):
  """A class to solve the Brusselator model PDE.
  """
  def __init__(self, L, N, t0, Du, Dv, a, b):
      """Initialiser for our class. 
      : param L : domain length
      : param N : number of grid points
      : param t0: initial time
      : param Du: Diffusion coefficient for u
      : param Dv: Diffusion coefficient for v
      : param a : parameter a
      : param b : parameter b
      """  
      super().__init__(L, N, t0)
      self.Du = Du
      self.Dv = Dv
      self.a = a
      self.b = b
      D = max(Du,Dv)
      self.dt = self.dx**2/(2*D)
      self.u0= self.a
      self.v0=  self.b / self.a
  
  def initial_condition(self, eps=0.01, t0=0):
      """ Compute the initial condition: perturbed homogeneous static solution. 
      :param eps : amplitude of perturbation
      :param t0  : initial time
      """
      self.t = t0
     
      self.v = np.empty(2*self.N, dtype='float64')
      # v[0:2*N:2] : u
      # v[1:2*N:2] : v
      x = np.array(range(0,self.N))*self.dx
      self.v[0:2*self.N:2] = self.u0+eps*np.exp(-(x-0.5*self.L)**2)
      self.v[1:2*self.N:2] = self.v0

      self.l_t = []  # list of t values for figures
      self.l_f = []  # list of y values (arrays) for figures
 
  def F(self, t, V):
      """ equation to solve: 
          du/dt = a-(b+1)*u[i]+u[i]**2*v[i]+ Du*(u[i+1]+u[i-1]-2*u[i])
          dv/dt = b*u[i]-u[i]**2*v[i] +      Dv*(v[i+1]+v[i-1]-2*v[i])
      :param t : current time 
      :param V : current function as a vector
      """
      F = np.zeros(2*self.N)
      nu1 = 2; nu2 = 2*self.N-2
      nv1 = 3; nv2 = 2*self.N-1
      F[nu1:nu2:2] = self.a-(self.b+1)*V[nu1:nu2:2]+V[nu1:nu2:2]**2*V[nv1:nv2:2]+\
           self.Du*(V[nu1-2:nu2-2:2]+V[nu1+2:nu2+2:2]-2*V[nu1:nu2:2])/self.dx**2
      F[nv1:nv2:2] = self.b*V[nu1:nu2:2]-V[nu1:nu2:2]**2*V[nv1:nv2:2]+\
             self.Dv*(V[nv1-2:nv2-2:2]+V[nv1+2:nv2+2:2]-2*V[nv1:nv2:2])/self.dx**2
      
      F[0] = F[1] = 0                    # the edges are handled in boundary
      F[2*self.N-1] = F[2*self.N-2] = 0  # the edges are handled in boundary
      return(F)
    
  def boundary(self, v):
      """ Impose boundary conditions du/dx = dv/dx = 0 at the edges
      """
      v[0] = v[2];
      v[1] = v[3];
      v[2*self.N-2] = v[2*self.N-4];
      v[2*self.N-1] = v[2*self.N-3];

  def plot_v_u(self, title="Plot showing v(t) and u(t)",fname="v_t_u_t_plot"):
      """ Generate figure of u(x), black curve, and v(x), blue curve.
          Write figure in file fname if the fname is not ""
      :param fname : output filename
      """
      i = 0;
      x = np.array(range(0, self.N))*self.dx
      fig = self.last_fig_index()
      plt.title(title,fontsize=12)
      plt.plot(x, self.l_f[fig][0:2*self.N:2], "k-" , label="u(x)")
      plt.plot(x, self.l_f[fig][1:2*self.N:2], "b-",label="v(x)")
      plt.xlabel("x", fontsize=24)
      plt.ylabel("u,v", fontsize=24)
      if(fname != ""):
        plt.savefig(fname)
      plt.legend()
      plt.show()

  def plot_u_v_Lo2(self, x, title="Plot showing v(x) and u(x)", fname="v_x_u_plot"):
      """ Generate figure u(x,t), black curve, and v(x,t), blue curve
          as a function of time for a fixed value of x.
          Write figure in file fname if the fname is not ""
      :param x     : x value for u and v for figure
      :param fname : output filename
      """
      Lratio=x/self.L 
      # self.N*Lratio*2 : *2 because u and v as saved as pairs
      self.plot(int(self.N*Lratio*2+1), 0, "b-") # +1 because plot() substracts it
      self.plot(int(self.N*Lratio*2+2), 0, "k-") # +2 because plot() substracts 1
      plt.title(title,fontsize=12)
      plt.xlabel("t", fontsize=24)
      plt.ylabel("u, v["+"{:.2f}".format(x)+"]", fontsize=24)
      if(fname != ""):
        plt.savefig(fname)
      plt.legend()
      plt.show()
      
