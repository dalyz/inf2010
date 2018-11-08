import java.util.*; 

public class BinaryHeap<AnyType extends Comparable<? super AnyType>> extends AbstractQueue<AnyType>
{
    private static final int DEFAULT_CAPACITY = 100;
    private int currentSize;      // Nombre d'elements
    private AnyType [ ] array;    // Tableau contenant les donnees (premier element a l'indice 1)
    private boolean min;		  // Pour savoir de quelle fa√ßon on va faire le tableau
    private int modifications;    // Nombre de modifications apportees a ce monceau
    
    @SuppressWarnings("unchecked")
    public BinaryHeap( boolean min )
    {
	this.min = min;
	currentSize = 0;
	array = (AnyType[]) new Comparable[ DEFAULT_CAPACITY + 1];
    }
    
    @SuppressWarnings("unchecked")
    public BinaryHeap( AnyType[] items, boolean min )
    {
	this.min = min;
	array = (AnyType[]) new Comparable[ DEFAULT_CAPACITY + 1];
	for (int i = 0 ; i < items.length; i++)
		array[i + 1] = items[i];
	currentSize = items.length;
	// COMPLETEZ
	/* invoquez buildMinHeap() ou buildMaxHeap() en fonction du parametre min */
	if (min == true) {
		buildMinHeap();
    	}
	else if (min == false) {
		buildMaxHeap();
		}
    }
    
    public boolean offer( AnyType x )
    {
    /* regarder si c'est un max ou min heap	*/
	if (x == null)
	    throw new NullPointerException("Cannot insert null in a BinaryHeap");
	
	if( currentSize + 1 == array.length )
	    doubleArray();
	
	/*percolation up */
	int trou = ++currentSize;
	if (min ==  true) {
		for(; trou > 1 && x.compareTo(array[trou / 2]) < 0; trou /= 2)
			array[trou] = array[trou / 2];
		array[trou] = x;
	}
		/*percolation up */
	else if (min == false) {
			for(; trou > 1 && x.compareTo(array[trou / 2]) > 0; trou /= 2)
				array[trou] = array[trou / 2];
			array[trou] = x;
	}
	return true; // √† quoi ca sert true vs false
    }
    /* Retourne l'ÈlÈment ‡ la position 1 (heap) */
    public AnyType peek()
    {
    	return ((isEmpty()) ? null : array[1]);
    }
    /* Trouve et enleve le head du queue ou return null si la queue est vide*/
    public AnyType poll()
    {
    AnyType removedHeap;
	if (isEmpty())
    	return null;
    else
    	removedHeap = array[1];
    	array[ 1 ] = array[ currentSize-- ];
    	if (array[1].compareTo(array[2]) < 0)
    		percolateDownMinHeap(1, currentSize);
    	else 
    		percolateDownMaxHeap(1, currentSize);
    	return removedHeap;
    }
    
    
    public Iterator<AnyType> iterator()
    {
	return new HeapIterator();
    }
    
    private void buildMinHeap()
    {
	//COMPLETEZ
    	for ( int i = currentSize / 2; i > 0; i--)
    		percolateDownMinHeap(i, currentSize);
    }
    
    private void buildMaxHeap()
    {
	//COMPLETEZ
    	for (int i = currentSize / 2; i > 0; i--)
    		percolateDownMaxHeap(i, currentSize);
    }
    
    public boolean isEmpty()
    {
	return size() == 0;
    }
    
    public int size()
    {
	return currentSize;
    }
    
    public void clear()
    {
	currentSize = 0;
	modifications = 0;
	array = (AnyType[]) new Comparable[ DEFAULT_CAPACITY + 1];
    }
    
    private static int leftChild( int i, boolean heapIndexing )
    {
	return ( heapIndexing ? 2*i : 2*i+1 ); 	// d√©pendamment si le tableau commence √† 0 ou 1, 
    }										// le terme de gauche de l'arbre change de position dans le 
    										// le tableau
    
    private void swapReferences( int index1, int index2 )
    {
	swapReferences(array, index1, index2);
    }
    
    private static <AnyType extends Comparable<? super AnyType>>
				    void swapReferences( AnyType[] array, int index1, int index2 )
    {
	AnyType tmp = array[ index1 ];
	array[ index1 ] = array[ index2 ];
	array[ index2 ] = tmp;
    }
    
    @SuppressWarnings("unchecked")
	private void doubleArray()
    {
	AnyType [ ] newArray;
	
	newArray = (AnyType []) new Comparable[ array.length * 2 ];
	for( int i = 0; i < array.length; i++ )
	    newArray[ i ] = array[ i ];
	array = newArray;
    }
    
    
    /**
     * @param hole    Position a percoler
     * @param size    Indice max du tableau
     */
    private void percolateDownMinHeap( int hole, int size )
    {
	percolateDownMinHeap(array, hole, size, true);
    }
    
    /**
     * @param array   Tableau d'element
     * @param hole    Position a percoler
     * @param size    Indice max du tableau
     * @param heapIndexing  True si les elements commencent a l'index 1, false sinon
     */
    private static <AnyType extends Comparable<? super AnyType>>
				    void percolateDownMinHeap( AnyType[] array, int hole, int size, boolean heapIndexing )
    {
	int child;
	AnyType tmp = array[hole];
	for(; hole * 2 <= size; hole = child) {
		child = leftChild(hole, heapIndexing);
		if(child != size && array[child + 1].compareTo(array[child]) < 0)
			child++;
		if(array[child].compareTo(tmp)<0)
			array[hole] = array[child];
		else
			break;
	}
    	array[hole] = tmp;
    }
    
    /**
     * @param hole    Position a percoler
     * @param size    Indice max du tableau
     */
    private void percolateDownMaxHeap( int hole, int size )
    {
	percolateDownMaxHeap(array, hole, size, true);
    }
    
    /**
     * @param array         Tableau d'element
     * @param hole          Position a percoler
     * @param size          Indice max du tableau
     * @param heapIndexing  True si les elements commencent a l'index 1, false sinon
     */
    private static <AnyType extends Comparable<? super AnyType>> 
				    void percolateDownMaxHeap( AnyType[] array, int hole, int size, boolean heapIndexing )
    {
    	int child;
    	AnyType tmp = array[hole];
    	for(; hole * 2 <= size; hole = child) {
    		child = leftChild(hole, heapIndexing);
    		if(child != size && array[child + 1].compareTo(array[child]) > 0) // a verifier
    			child++;
    		if(array[child].compareTo(tmp) > 0)
    			array[hole] = array[child];
    		else
    			break;
    	}
        	array[hole] = tmp;
        }
    
    public static <AnyType extends Comparable<? super AnyType>>
				   void heapSort( AnyType[] a )
    {
	//COMPLETEZ
    	BinaryHeap tri = new BinaryHeap(a, false);
    	tri.buildMaxHeap();
        for(int j = tri.size() - 1; j > 0; j--) {
        		tri.swapReferences(1, j);
        	tri.percolateDownMaxHeap(j, tri.size());
        }
        Iterator<AnyType> iter = tri.iterator();
        for (int i = tri.size(); i > 0; i--)
        	System.out.println(iter.next());
    }
    
    public static <AnyType extends Comparable<? super AnyType>>
				   void heapSortReverse( AnyType[] a )
    {
    	//COMPLETEZ
    	BinaryHeap tri = new BinaryHeap(a, true);
    	tri.buildMinHeap();
        for(int j = tri.size() - 1; j > 0; j--) {
        		tri.swapReferences(1, j);
        	tri.percolateDownMinHeap(j, tri.size());
        }
     }
    
    public String nonRecursivePrintFancyTree()
    {
	String outputString = "";
	
	//COMPLETEZ
	

	return outputString;
    }
    
    public String printFancyTree()
    {
	return printFancyTree(1, "");
    }
    
    private String printFancyTree( int index, String prefix)
    {
	String outputString = "";
	
	outputString = prefix + "|__";
	
	if( index <= currentSize )
	    {
		boolean isLeaf = index > currentSize/2;
		
		outputString += array[ index ] + "\n";
		
		String _prefix = prefix;
		
		if( index%2 == 0 )
		    _prefix += "|  "; // un | et trois espace
		else
		    _prefix += "   " ; // quatre espaces
		
		if( !isLeaf ) {
		    outputString += printFancyTree( 2*index, _prefix);
		    outputString += printFancyTree( 2*index + 1, _prefix);
		}
	    }
	else
	    outputString += "null\n";
	
	return outputString;
    }
    
    private class HeapIterator implements Iterator {
	/* Dans les notes de cours, ils ont un private int current = 0;
	 * Alors, current < size() ou on met directe 0 < size();
	 */
    
    private int current = 0; // peut pas commencer a 0 ?
    
	public boolean hasNext() {
	   return current < size();
	}
	/* Note de cours cours 2, diapo 30 */
	public AnyType next() throws NoSuchElementException, 
				    ConcurrentModificationException, 
				    UnsupportedOperationException {
	    if(!hasNext())
	    	throw new NoSuchElementException();
	    return array[current++];
	}
	
	public void remove() {
	    throw new UnsupportedOperationException();
	}
    }
}
