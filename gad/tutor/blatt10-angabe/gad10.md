```java

public static void find(Tree t, int a, int b) {

	while (!isLeaf(t)) {
	   if (t.number > a) t = t.leftChild;
	   else t = t.rightChild;
	}
	
	out(t.number);
	
	

}

```
