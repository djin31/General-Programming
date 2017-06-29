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
            
