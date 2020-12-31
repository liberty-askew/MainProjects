def hist_scatter(x,y):
    import numpy as np
    import matplotlib.pyplot as plt
    from matplotlib.ticker import NullFormatter
    nullfmt = NullFormatter()         # no labels
    # definitions for the axes
    left, width = 0.1, 0.65
    bottom, height = 0.1, 0.65
    bottom_h = left_h = left + width + 0.02  
    rect_scatter = [left, bottom, width, height]
    rect_histx = [left, bottom_h, width, 0.2]
    rect_histy = [left_h, bottom, 0.2, height]
    # start with a rectangular Figure
    plt.figure(1, figsize=(8, 8))    
    axScatter = plt.axes(rect_scatter)
    axHistx = plt.axes(rect_histx)
    axHisty = plt.axes(rect_histy)
    # no labels
    axHistx.xaxis.set_major_formatter(nullfmt)
    axHisty.yaxis.set_major_formatter(nullfmt)
    # the scatter plot:
    axScatter.scatter(x, y)
    # now determine nice limits by hand:
    binwidth = 0.25
    axScatter.set_xlim(np.min(x), np.max(x))
    axScatter.set_ylim(np.min(y), np.max(y))
    binsx = np.arange(np.min(x), np.max(x) + binwidth, binwidth)
    binsy = np.arange(np.min(y), np.max(y) + binwidth, binwidth)
    axHistx.hist(x, bins=binsx)
    axHisty.hist(y, bins=binsy, orientation='horizontal')
    axHistx.set_xlim(axScatter.get_xlim())
    axHisty.set_ylim(axScatter.get_ylim())
    plt.show()


def side_by_side(x,y):
    import matplotlib.pyplot as plt
    import numpy as np
    f, ax = plt.subplots(1,2,sharex=True,sharey=True)
    ax[0].plot(x,y,".")
    heatmap, xedges, yedges = np.histogram2d(x,y, bins=30)
    extent = [xedges[0], xedges[-1], yedges[0], yedges[-1]]
    ax[1].imshow(heatmap.T, extent=extent, origin='lower',cmap=plt.get_cmap("Blues"))
    plt.show()