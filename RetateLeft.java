

public void rotateLeft(Node p){
	Node r, rl, pp;
	if(p != null && (r = p.right) !=null){
		if((rl = p.right = r.left) != null ){
			rl.parent = p;
		}
		if((pp = r.parent = p.parent) == null){
			r.red = false;
		}else if(pp.left == p){
			pp.left = r;
		}else{
			pp.right = r;
		}
		r.left = p;
		p.parent = r;
	}
}