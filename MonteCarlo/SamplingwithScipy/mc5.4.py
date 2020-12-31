#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Tue Feb 27 14:05:12 2018

@author: libertyaskew
"""
import numpy as np
from hist_scatter import *
'''
n = 10000
samp2Ga = [(stats.norm(0,1).rvs(n))for i in range(2)]
side_by_side(samp2Ga[0],samp2Ga[1])
hist_scatter(samp2Ga[0],samp2Ga[1])


import numpy as np
from numpy.linalg import cholesky
sigMat = [[1,0.8],[0.8,1]]         # 2x2 matrix as list of lists
sigMatarr = np.array(sigMat) # convert sigMat to a numpy array
cholSig = cholesky(sigMatarr)  # compute the Cholesky decomposition
cholSig.tolist()               # Show the result in same format as sigMat

XSamp = (np.dot(cholSig , samp2Ga))

samp2Ga = [(stats.norm(0,1).rvs(n))for i in range(2)]
side_by_side(XSamp[0],XSamp[1])
hist_scatter(XSamp[0],XSamp[1])


'''
n = 10000
sampG2 = np.transpose(stats.multivariate_normal([0,0],[[1,0.8],[0.8,1]]).rvs(n))
hist_scatter(sampG2[0],sampG2[1])
print (sampG2)
normG2 = stats.norm(0,1).cdf(sampG2)
print (normG2)
hist_scatter(normG2[0],normG2[1])


