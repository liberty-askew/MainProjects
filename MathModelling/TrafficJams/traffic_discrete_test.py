import matplotlib.pyplot as plt
from traffic_discrete import TrafficDiscrete

# Simulate traffic at three different densities and compare
# the plots.

tr = TrafficDiscrete(density=0.15, p_slowdown=0.4)
print (tr.evolve(tr.road_len))
tr.make_plot(filename='2_spacetime_d015.pdf')
tr = TrafficDiscrete(vmax=3.0, density=0.5, p_slowdown=0.4)
tr.evolve(tr.road_len)
'''
tr.make_plot(filename='2_spacetime_d050.pdf')
tr = TrafficDiscrete(vmax=3.0, density=0.7, p_slowdown=0.4)
tr.evolve(tr.road_len)
tr.make_plot(filename='2_spacetime_d070.pdf')

# Determine the velocity-density plot for purely deterministic
# traffic...

tr = TrafficDiscrete(vmax=1.0, p_slowdown=0.0)
tr.average_velocity_density_plot(4000, filename='2_average_velocity_deterministic.pdf')

# ... and for traffic with random slowdown.

tr = TrafficDiscrete(vmax=4.0, p_slowdown=0.3)
tr.average_velocity_density_plot(8000, filename='2_average_velocity_randomised.pdf')

# Generate the flow-density plot for deterministic behaviour.

tr = TrafficDiscrete(vmax=3.0, density=0.15, p_slowdown=0)
tr.flow_density_comparison_plot(4000, vmaxs=[1.0, 2.0, 3.0], filename='2_flow_density_deterministic.pdf')

# Generate the flow-density plot for three values of the
# maximal legal velocity.

tr = TrafficDiscrete(vmax=3.0, density=0.15, p_slowdown=0.4)
tr.flow_density_comparison_plot(4000, vmaxs=[1.0, 2.0, 3.0], filename='2_flow_density_randomised.pdf')
'''