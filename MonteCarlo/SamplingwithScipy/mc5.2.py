#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Tue Feb 27 13:11:37 2018

@author: libertyaskew
"""

from scipy.stats import norm
import matplotlib.pyplot as plt
l  = 10000

nsamp1 = (norm.rvs(loc=1,scale=5,size=l) )
nsamp2 = (norm.rvs(loc=2,scale=3,size=l) )


Nsamp  = [[nsamp1[i] , nsamp2[i]] for i in range (l)]


import numpy as np
from numpy.linalg import cholesky
sigMat = [[25,7.5],[7.5,15]]         # 2x2 matrix as list of lists
sigMatarr = np.asarray(sigMat) # convert sigMat to a numpy array
cholSig = cholesky(sigMatarr)  # compute the Cholesky decomposition
print(cholSig.tolist())  


'''
plt.scatter(nsamp1,nsamp2, linewidth=0.01)
plt.show()

import numpy as np
import matplotlib.pyplot as plt
heatmap, xedg, yedg = np.histogram2d(nsamp1,nsamp2, bins=20)
extent = [xedg[0], xedg[-1], yedg[0], yedg[-1]]
plt.clf()
plt.imshow(heatmap.T, extent=extent,
        origin='lower',cmap=plt.get_cmap("Blues"))
plt.show()
           # Show the result in same format as sigMat

'''