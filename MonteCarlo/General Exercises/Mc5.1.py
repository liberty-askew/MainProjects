#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Apr 21 14:19:23 2018

@author: libertyaskew
"""

import numpy as np 
from scipy import stats
import matplotlib.pyplot as plt
import random 

rvX = stats.expon(scale=2) #create scipy representation of r.v. X
rvXS = rvX.rvs(10)           #take a sample of size 10
#print (rvXS)

Xvals = np.linspace(0, 4, num=10) #10 values equally spaced from 0 to 4
XvalsPDF = rvX.pdf(Xvals)
#print (XvalsPDF)
XvalsCDF = rvX.cdf(Xvals) #apply the cdf F(x) to Xvals
#print (XvalsCDF)
XvalsCDF_PPF = rvX.ppf(XvalsCDF)
rvXCDF = rvX.cdf(rvXS)
#print (rvXCDF)

from scipy.stats import norm
#print (norm.rvs(loc=0,scale=1,size=1) )


nSamp1 = norm.rvs(loc=1,scale=5,size=10000) 
nSamp2 = norm.rvs(loc=2,scale=3,size=10000) 

nSamp12 = [ [nSamp1[i] , nSamp2[i]] for i in range (len(nSamp1))]


plt.scatter(nSamp1 , nSamp2)

import numpy as np
import matplotlib.pyplot as plt
heatmap, xedg, yedg = np.histogram2d(nSamp1,nSamp2, bins=20)
extent = [xedg[0], xedg[-1], yedg[0], yedg[-1]]
plt.clf()
plt.imshow(heatmap.T, extent=extent,
            origin='lower',cmap=plt.get_cmap("Blues"))


n=100000
Z1 = norm.rvs(loc=1,scale=5,size=n) 
Z2 = norm.rvs(loc=2,scale=3,size=n)

b = float(np.sqrt(75/4))
m1 = 1
m2 = 2

X1 = [ (2.5*Z1[i] + b*Z2[i] + 2) for i in range (n)] 
X2 = [ (3*Z2[i] + m2) for i in range (n) ]

plt.scatter(X1 , X2)

heatmap, xedg, yedg = np.histogram2d(X1,X2, bins=20)
extent = [xedg[0], xedg[-1], yedg[0], yedg[-1]]
plt.clf()
plt.imshow(heatmap.T, extent=extent,
            origin='lower',cmap=plt.get_cmap("Blues"))

from hist_scatter import * 
n = 100000
samp2Ga = [stats.norm(0,1).rvs(n) for i in range (2)]





n = 10000
sampG = [stats.norm(0,1).rvs(n) for i in range (2)]
sampG1 = sampG[0]
sampG2 = sampG[1]
X1 = [ (sampG1[i] + 0.8 * sampG2[i]) for i in range (n)]
X2 = [ (sampG2[i] + 0.8 * sampG1[i]) for i in range (n)]
side_by_side(X1,X2)
hist_scatter(X1,X2)


n = 10000
sampG2 = np.transpose(stats.multivariate_normal([0,0],[[1,0.8],[0.8,1]]).rvs(n))
hist_scatter(sampG2[0],sampG2[1])
















