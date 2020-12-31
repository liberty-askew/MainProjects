#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sun Dec 24 11:11:56 2017

@author: libertyaskew
"""

import numpy as np
import matplotlib.pyplot as plt

from ode_rk4 import ODE_RK4

class Bxl_ODE(ODE_RK4):
    
    def __init__(self,V0=[0], dt=0.1, t0=0,a=1,b=1):
        
        """ Set the variables used by the class:
        :param V0 : initial value (as an array or a list) 
        :param dt : integration time step
        :param t0 : initial time
        :param a  : class variable
        :param b  : another class variable
        """
        super().__init__(V0,dt,t0) 
        self.a = a
        self.b = b
     
    
    def  F(self,t,V):
        """ Return the right hand side of the equation dV/dt = F(t,V)

        :param t  : current time
        :param V0 : current function values
        """
        u = V[0]
        v = V[1]
        
        return(np.array([self.a - (self.b + 1)*u + (u**2)*v, 
                       self.b * u - (u**2) * v ]))
    
    def static_solution(self):
        """computes the homogeneous static solution to equations
        f1(v0 , u0) = f2(v0 , u0) = 0
        """
        u0 = self.a
        v0 = self.b / self.a
        return ( np.array( [u0 , v0]))
    
    def sigma(self):
        """ computes the 2 static eigenvalue solutions of
        det(A - (sigma)1) = 0
        """
        detA = (self.a)**2
        trA = self.b - 1 - detA
        discA = (trA)**2 - 4*detA
        sigmaP = 0.5*( trA + discA **(0.5))
        sigmaN = 0.5*( trA - discA **(0.5))
        return (tuple ( [ sigmaP , sigmaN , trA , discA ]))
    
    def plot_u(self,title="Plot showing u against time",fname="utimeplot"):
        """ display u(t)
        :param title: the title for the figure
        :param fname: the ouput filename, including the file
        extension.
        """
        self.plot(1,0,style="k-")
        plt.title(title,fontsize=16)
        plt.xlabel("t",fontsize=22) 
        # Set horizontal (x) figure label to "t"
        plt.ylabel("u",fontsize=22) 
        # Set vertical (y) figure label to "u"
        if fname!="" : plt.savefig(fname)
        plt.show()
    
    def plot_v(self,title="Plot showing v against time",fname="vtimeplot"):
        """ display v(t)
        :param title: the title for the figure
        :param fname: the ouput filename, including the file
        extension.
        """
        self.plot(2,0,style="b-")
        plt.title(title,fontsize=16)
        plt.xlabel("t",fontsize=22) 
        # Set horizontal (x) figure label to "t"
        plt.ylabel("v",fontsize=22) 
        # Set vertical (y) figure label to "u"
        if fname!="" : plt.savefig(fname)
        plt.show()
    
    def plot_v_u(self,title="Plot showing u against v",fname="uvplot"):
        """ display u(v)
        :param title: the title for the figure
        :param fname: the ouput filename, including the file
        extension.
        """
        self.plot(1,2,style="r *")
        plt.title(title,fontsize=16)
        plt.xlabel("v",fontsize=22) 
        # Set horizontal (x) figure label to "t"
        plt.ylabel("u",fontsize=22) 
        # Set vertical (y) figure label to "u"
        if fname!="" : plt.savefig(fname)
        plt.show()
    