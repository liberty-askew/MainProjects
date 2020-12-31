#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Tue Nov 28 20:57:45 2017

@author: libertyaskew
"""

import numpy as np
import matplotlib.pyplot as plt

from traffic_ode_base import TrafficODEBase

class TrafficODE(TrafficODEBase):
    
    def vgoal(self, headway):
        """ 
        Compute the optimal velocity, given the headway.
        :param s:           sensitivity of the drivers.
        :param bc:          length of car + safe distance to next car.
        :param bf:          another parameter to tune v_goal.
        :param m:           another parameter to tune v_goal.
        :param v0           inital velocity of cars
        """
        return ( self.v0 * (( np.tanh (self.m * ( headway + self.bc - self.bf ))) \
                - (np.tanh (self.m * (self.bc - self.bf))) ))
        
              
        
    def plot_free_flow_density(self):
        """
        Makes a flow-density plot for free flow using the theoretical curve.
        """
        #array from number near 0 (cannot use zero because divide by zero error) to
        #max density ~ 1/car length when car is 5m long
        n = np.arange ( 0.001 , 0.2 ,  0.001)
        #plots density against flow, and converts from cars/second to cars/minute and
        #cars/meter to cars/kilometer.
        plt.plot( 1000 * n  , 60 * self.vgoal( 1 / n ) * n)
    
    def update(self):
        """ 
        Updates all positions & velocities using Euler integration. Also updates 
        the self.detector variable.
        """
        #sets detector itially to 0
        d = 0
        #distance between car and the one infront
        headway  = np.append(self.x[1:] - self.x[:-1] , self.x[0]+self.road_length-self.x[-1])   
        #calculates new postion
        x2       = self.x + self.v * self.dt + self.s * ( self.vgoal (headway) - self.v) * self.dt**2
        for i in range (self.N):
            #updates detector as cars reach end of road
            if x2[i] > (self.road_length ) :
                d += 1
            self.detector += d
        #calculated new velocity
        v2     = self.v + self.s * ( self.vgoal (headway ) - self.v) * self.dt
        #returns cars that have reached the end of the road to the beginging 
        x2     = np.remainder( x2, self.road_length  )
        #updates position and velocities 
        self.x = np.roll ( x2 , d)
        self.v = np.roll (v2 , d)
        
   