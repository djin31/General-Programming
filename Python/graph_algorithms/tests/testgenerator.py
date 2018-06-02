from random import *
import sys

if (sys.argv[1]=='s'):
	minlimit = 4
	maxlimit = 6
else:
	minlimit = 1000
	maxlimit = 10000

nodes = randint(minlimit,maxlimit)
edges = randint(nodes, minlimit*minlimit)

print nodes,edges

for i in xrange(edges):
	print randint(0,nodes),randint(0,nodes)