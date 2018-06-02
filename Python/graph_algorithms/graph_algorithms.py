# implementation of common graph algorithms

def read_graph():
	# to make input function compatible across python versions
	global input
	try:
		input = raw_input
	except NameError:
		pass

	directed_graph = {"nodes":0, "edges":0, "adjacency_matrix": []}
	undirected_graph = {"nodes":0, "edges":0, "adjacency_matrix": []}

	nodes,edges = map(int,input("Enter number of nodes and edges\n").strip().split(" "))
	directed_graph["nodes"] = nodes
	undirected_graph["nodes"] = nodes
	directed_graph["edges"] = edges
	undirected_graph["edges"] = edges

	for i in range(nodes):
		directed_graph["adjacency_matrix"].append([])
		undirected_graph["adjacency_matrix"].append([])

	for i in range(edges):
		n1, n2 = map(int, input().strip().split(' '))
		directed_graph["adjacency_matrix"][n1].append(n2)
		undirected_graph["adjacency_matrix"][n1].append(n2)
		undirected_graph["adjacency_matrix"][n2].append(n1)

	return directed_graph, undirected_graph


def dfs(graph, current_node, visited_nodes = None, marker = 1):
	if (visited_nodes==None):
		visited_nodes = [0]*graph["nodes"]

	visited_nodes[current_node] = marker
	order_of_visit = [current_node]

	neighbours = graph["adjacency_matrix"][current_node]
	for i in neighbours:
		if (visited_nodes[i]==0):
			visited_nodes[i] = marker
			(new_order_of_visit, visited_nodes) = dfs(graph, i, visited_nodes, marker)
			order_of_visit += new_order_of_visit
	return order_of_visit, visited_nodes


directed_graph, undirected_graph = read_graph()
print dfs(directed_graph,0)
