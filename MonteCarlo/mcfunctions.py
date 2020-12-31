#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Wed Apr 18 16:17:43 2018

@author: libertyaskew
"""
def factorial(n): # computed by direct loop
    result = 1
    while n>1:
        result = result * n
        n = n-1
    return result



def binomial(n,j):
    return factorial(n)/(factorial(j)*factorial(n-j))

def dbinom(j,n,p):
    return binomial(n,j) * p**j * (1-p)**(n-j)


def pbinom(j,n,p):
    return sum([dbinom(vj,n,p) for vj in range(j+1)])


c = np.arange(0,10,0.1)

def rbern1(p):
    u = random.random()
    if u<=p:
        return 1
    else:
        return 0  

def mean(l):
    return (sum(l)/len(l))

def var(l):
    ll = len(l)
    sq = [ (l[i])**2 for i in range (ll)]
    print (sq)
    return ( (1/(ll-1))*( sum(sq) - ll*(mean(l)**2)))

#SDerr = np.sqrt( sum( [(piValuesM1[i] - mean)**2 for i in range(n) ] )) / n
print (var([1,2,3,4,5]))

rvX = stats.expon(scale=2) #create scipy representation of r.v. X
rvXS = rvX.rvs(10)   

Xvals = np.linspace(0, 4, num=10) #10 values equally spaced from 0 to 4
XvalsPDF = rvX.pdf(Xvals) #apply the pdf f(x) to Xvals
XvalsCDF = rvX.cdf(Xvals) #apply the cdf F(x) to Xvals
XvalsCDF_PPF = rvX.ppf(XvalsCDF) #apply F^(-1) for XvalsCDF - returns Xvals
rvXCDF = rvX.cdf(rvXS) #applys cdf F(x) to sample for random variable 

from scipy.stats import norm
norm.rvs(loc=0,scale=1,size=1) #1 random sample from N(0,1)


import numpy as np
from numpy.linalg import cholesky
sigMat = [[9,6],[6,5]]         # 2x2 matrix as list of lists
sigMatarr = np.asarray(sigMat) # convert sigMat to a numpy array
cholSig = cholesky(sigMatarr)  # compute the Cholesky decomposition
cholSig.tolist()               # Show the result in same format














