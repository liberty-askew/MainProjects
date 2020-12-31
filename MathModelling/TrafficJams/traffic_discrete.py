#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Tue Nov 21 14:43:19 2017

@author: libertyaskew
"""

import numpy as np

from traffic_discrete_base import TrafficDiscreteBase

class TrafficDiscrete(TrafficDiscreteBase):

    
    
    def step(self):
        """ 
        Perform one update cycle of the system. Also updates the car counter 
        in self.detector when a car moves past the detector at the 'end' of the
        road and implements the 4 traffic rules 
        """
        #fill with an array of velocities for each car
        cars = np.where(self.v!=-1)[0]  
        #calculates headway between cars
        headway = np.mod( np.roll(cars, -1) - cars, self.road_len)
        #cars accelerate by 1 but do not exceed speed limit
        v2 = np.minimum( self.v[cars] + 1 , int(self.vmax) )
        
        #generates list of 0 / -1 from list of True / False values with prob of p_slowdown
        rnd_tf  = np.random.rand(len(cars)) > self.p_slowdown  
        choices = np.choose( rnd_tf, [ -1, 0 ])
            
        #stops cars from crashing into each other
        v2     = np.minimum ( v2 , headway - 1 )     
        #implements random stoping list
        v2 = np.maximum(choices + v2 , 0 )
        #moves cars along by velocity
        cars += v2
        
        #increases counter when cars leave road
        for i in range (len(cars)): 
            if cars[i] > (self.road_len - 1) :
                self.detector += 1
        
        #loops cars at end of road back to the begining
        cars    = np.remainder( cars, len(self.v)  )
        #genrates list of -1 velocitys, so no cars
        self.v  = -np.ones(self.road_len)
        #fills list of -1 with velocity of cars in corresponding position
        for i in range(len(cars)):
            self.v[cars[i]] = v2[i]  
        self.v = np.array( [ int(self.v[i]) for i in range (self.road_len) ] )
        
    def flow_density(self, density , steps  ):
        """ 
        Determine the flow given an approximate density.

        :param density:    traffic density.
        :param steps:      number of steps to iterate.
        :return:           tuple of (density, flow).
        """
        self.density = density
        self.fill_road_randomly()
        #runs first 10 iterations to allow system to settle
        self.evolve(steps //10)
        #runs remaining iterations
        self.evolve(steps - steps //10 )
        return (tuple ([self. density ,  self.detector / (steps  - steps // 10 ) ]) ) 
