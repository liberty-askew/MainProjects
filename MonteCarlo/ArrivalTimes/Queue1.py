#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Thu Mar  8 10:37:30 2018

@author: libertyaskew
"""

import matplotlib.pyplot as plt

from scipy import stats
def rndTime():
    return stats.expon(scale=5).rvs()
print ( rndTime() )

def servTime():
    return stats.expon(scale=2).rvs()
print ( servTime() )

def firstQ(runTime): 
    t=0
    cust = []
    arrL = []
    servL = []
    count = 0 #Start with no customers
    while True:
        count = count + 1   # Add one customer
        arrI = rndTime()    # Time between finish of previous customer
                            # and arrival of current customer
        servI = servTime()  # Time taken to serve current customer
        arrT = t+arrI       # Arrival time
        if arrT>runTime:
            plt.plot(servL)
            plt.plot(arrL)
            plt.show()
            return(cust)
           
            
        servT = t+arrI+servI # Departure time
        arrL.append(arrT)
        servL.append(servT)
        print('arrL=',arrL)
        print('servL=',servL)
        cust.append([count,arrI,servI,arrT,servT])
        print("Customer %d arrived after %s minutes." %(count,arrI))
        print("Service time: %s minutes." %servI)
        print("Arrival time: %s minutes past 7. "%arrT)
        print("Departure time: %s minutes past 7." %servT)
        print('')
        
        
        t = servT #Update time
        
            
q = (firstQ(60))

