import numpy as np

# Aspects of the sample road in figure 1.1.
v       = np.array([2,-1,-1,1,0,3,-1,1,-1])
cars    = np.where(v!=-1)[0]
headway = np.mod( np.roll(cars, -1) - cars, len(v) ) 

print("v          = ", v)
print("cars index = ", cars)
print("v[cars]    = ", v[cars])
print("headway    = ", headway)
print()

# Generate an array with a random number 0-1 for each segment.
rnd     = np.random.rand(len(v))
print("rnd         = ", rnd)

# Which of these values are < 0.3 ?
rnd_tf  = rnd < 0.3
print("rnd_tf      = ", rnd_tf)

# Select one of two alternatives based on rnd_tf.
choices = np.choose( rnd_tf, [ -1, 1 ] )
print("choices     = ", choices)

# Ensure car positions satisfy the periodic boundary conditions.
cars   += 3
cars    = np.remainder( cars, len(v) )
print("cars, moved = ", cars)

# Clip velocities to a maximum of '3'.
v = np.minimum(v, 3)
print("v, clipped  = ", v)
