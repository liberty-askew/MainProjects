#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Thu Mar  8 10:56:24 2018

@author: libertyaskew
"""
import numpy as np

def rndTime():
    return stats.expon(scale=5).rvs()


def servTime():
    return stats.expon(scale=2).rvs()

infinity = float('inf')

def secondQ(runTime):
    cust = []
    freeCashier = []
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
        
        print("It is now %g minutes past 7." % shopT)
        if shopT==nextArr and shopT<runTime: #processing arrival
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
                if shopT>runTime:
                    print("The time is %g minutes past 7. We are now closed." %shopT)
                    print (cust)
                    return [cust,freeCashier]
                else:
                    servT = infinity
                    continue #No-one to serve, go back to beginning of the loop
        lengthQ = lengthQ - 1 #customer left the queue and is being served
        served = served + 1
        print("Customer %d is now starting to be served at %g minutes past  7." %(served,shopT))
        servI = servTime() # time to serve this customer
        servT = shopT + servI  # time this customer will leave the shop
        

Q2 = secondQ(60)