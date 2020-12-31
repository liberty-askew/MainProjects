#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Thu Mar  8 12:06:25 2018

@author: libertyaskew
"""
import matplotlib.pyplot as plt
import numpy as np
from scipy import stats
def rndTime():
    return stats.expon(scale=5).rvs()


def servTime1():
    return stats.expon(scale=2).rvs()


def servTime2():
    return stats.expon(scale=2.5).rvs()


def thirdQ(runTime):
    cust = []
    freeCashier = []
    waitL = []
    count = 0
    served = 0
    lengthQ = 0
    shopT = 0
    print("It is now %g minutes past 7." % shopT)
    arrI = rndTime() # Time until next arrival
    nextArr = arrI # Time of next arrival
    servT = infinity
    while True:
        shopT = min(nextArr,servT)
        if shopT>runTime:
            print("The time is %g minutes past 7. We are now closed." %runTime)
            plt.hist(waitL,10)
            plt.show()
            return [cust,freeCashier]
        print("It is now %g minutes past 7." % shopT)
        if shopT==nextArr: #processing arrival
            count = count+1 #new customer
            lengthQ = lengthQ+1 #Customer is queueing
            # Add entry to cust:
            print("Customer %d arrived %g minutes after the previous one and is waiting to be served." % (count, arrI))
            print("Arrival Time: %g minutes past 7." % shopT)
            print("Queue length: %d" %lengthQ)
            cust.append([count,lengthQ,arrI,'NA',shopT,'NA'])
            arrI = rndTime() # Time until next arrival.
            nextArr = shopT + arrI
            if servT<infinity: # Check if server is busy
                continue # Server is busy
        else: # shopT==servT, record service
            print("Customer %d left the shop. Departure time: %g minutes past 7." %(served,shopT))
            print("Service time for customer %d: %g minutes" % (served, servI))
        
            cust[served-1][3]=servI
            cust[served-1][5]=shopT
            freeCashier.append(shopT)
            if lengthQ == 0: #No-one in queue
                servT = infinity
                continue #No-one to serve, go back to beginning of the loop
        lengthQ = lengthQ - 1 #customer left the queue and is being served
        served = served + 1
        print("Customer %d is now starting to be served at %g minutes past  7." %(served,shopT))
        if (lengthQ==0):
            servI = servTime1() # time to serve this customer
        else:
            servI = servTime2()
        waitL.append(servI)
        servT = shopT + servI  # time this customer will leave the shop
        
print (thirdQ(60*16))
