#pass the set for which you want to find the powerset as an argument in a list
def subset(a):
	if len(a)==1:
		return [[],a]
	p=subset(a[1:])
	ll=[]
	for i in p:
		ll.append(i)
		ll.append(i+[a[0]])
	return ll
