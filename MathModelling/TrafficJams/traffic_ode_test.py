from traffic_ode      import *
from traffic_ode_base import kmh2ms
import numpy as np

vmax = 115  # km/h

# Show the optimal velocity curve.
tr = TrafficODE(60, s=1.7, bc=7, bf=25, m=0.12, vmax=kmh2ms(vmax), road_length=1000)
tr.plot_vgoal(filename='3_optimal_velocity.pdf')

# Compare two situations, one with 60 and one with 40 cars per kilometer.
tr = TrafficODE(60, s=1.7, bc=7, bf=25, m=0.12, vmax=kmh2ms(vmax), road_length=1000)
tr.setup_cars(random=True, strength=0.5)
# ... simulate and let system settle
tr.simulate(2000)
# ... then plot trajectory for 1 min
tr.simulate(60,plot=True, filename='3_trajectories_60_cars.pdf', fig_step=1)
tr.plot_velocities(filename='3_velocities_60_cars.pdf')

tr = TrafficODE(40, s=1.7, bc=7, bf=25, m=0.12, vmax=kmh2ms(vmax), road_length=1000)
tr.setup_cars(random=True, strength=0.5)
# ... simulate and let system settle
tr.simulate(2000)
# ... then plot trajectory for 1 min
tr.simulate(60,plot=True, filename='3_trajectories_40_cars.pdf',fig_step=1)
tr.plot_velocities(filename='3_velocities_40_cars.pdf')

# To understand the above in context, plot a flow-density graph.
tr.flow_density_plot(1000, filename='3_flow_density.pdf')    

