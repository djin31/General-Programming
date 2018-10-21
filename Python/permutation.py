# pass a list of items to be permuted as argument, returns a list of all possible permutations
# can also permute a string 
# to remove duplicates use set operator on the returned list
def permute(l):
    ll=[]
    num=len(l)
    if num==1:
        return [l]
    else:
        for i in range(num):
            tmp=permute(l[:i]+l[i+1:])
            for j in tmp:
                ll.append(l[i]+j)
    return ll     
            
# binary string permutations
# arguments are number of ones in the string and length of string 
def permute(ones,chars):
  permutations=[]
  for i in xrange(chars+1):
    permutations.append([])
    for j in xrange(ones+1):
      permutations[i].append([])
      if (j>i):
        continue
      if (j==i):
        permutations[i][j].append("1"*i)
      else:
        l = permutations[i-1][j]
        for p in l:
          permutations[i][j].append("0"+p)
        l = permutations[i-1][j-1]
        for p in l:
          permutations[i][j].append("1"+p)
  return permutations[-1][-1]

