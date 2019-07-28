import com.sun.xml.internal.ws.dump.LoggingDumpTube.Position;

/* A quadtree is a tree that every parentNode has 4 childNodes. */
public class Quadtree<E> { 

	
    protected static class Node<E> implements Position<E> {

      private E element;          
      private Node<E> parent;     
      private Node<E> NW; //In a 2D scale the child is at North West of parent
      private Node<E> NE; //In a 2D scale the child is at North East of parent
	  private Node<E> SW; //In a 2D scale the child is at South West of parent
	  private Node<E> SE; //In a 2D scale the child is at South East of parent

      public Node(E e, Node<E> above, Node<E> NW, Node<E> NE, Node<E> SW,Node<E> SE ) {

        element = e;
		parent = above;
		this.NW = NW;
		this.NE = NE;
		this.SW = SW;
		this.SE = SE;
      
    }
  
	  //Get Methods
      public E getElement() { return element; }
      public Node<E> getParent() { return parent; }
	  public Node<E> getNW(){return NW;}
	  public Node<E> getNE(){return NE;}
	  public Node<E> getSW(){return SW;}
	  public Node<E> getSE(){return SE;}
	  
  
      // Set Methods
      public void setElement(E e) { element = e; }
      public void setParent(Node<E> parentNode) { parent = parentNode; }
	  public void setNW(Node<E> NW){this.NW = NW;}
	  public void setNE(Node<E> NE){this.NE = NE;}
	  public void setSW(Node<E> SW){this.SW = SW;}
	  public void setSE(Node<E> SE){this.SE = SE;}

    } //----------- end of nested Node class -----------
  
    /** Factory function to create a new node storing element e. */
    protected Node<E> createNode(E e, Node<E> above, Node<E> NW, Node<E> NE, Node<E> SW,Node<E> SE ) {
      return new Node<E>(e, parent,NW,NE,SW,SE);
    }
  
    // Quadtree instance variables
    /** The root of the quad tree is the one on exactly mids NW */
    protected Node<E> root = null;     // root of the tree
  
    /** The number of nodes in the QuadTree **/
    private int size = 0;              // number of nodes in the tree
  
    // constructor
    /** Construts an empty quadtree. */
    public LinkedBinaryTree() { }      // constructs an empty binary tree
  
    // nonpublic utility
    /**
     * Verifies that a Position belongs to the appropriate class, and is
     * not one that has been previously removed. Note that our current
     * implementation does not actually verify that the position belongs
     * to this particular list instance.
     *
     * @param p   a Position (that should belong to this tree)
     * @return    the underlying Node instance for the position
     * @throws IllegalArgumentException if an invalid position is detected
     */
    protected Node<E> validate(Position<E> p) throws IllegalArgumentException {
      if (!(p instanceof Node))
        throw new IllegalArgumentException("Not valid position type");
      Node<E> node = (Node<E>) p;       // safe cast
      if (node.getParent() == node)     // our convention for defunct node
        throw new IllegalArgumentException("p is no longer in the tree");
      return node;
    }
  
    // accessor methods 
    /**
     * Returns the number of nodes in the tree.
     * @return number of nodes in the tree
     */
    public int size() {
      return size;
    }
  
    /**
     * Returns the root Position of the tree (or null if tree is empty).
     * @return root Position of the tree (or null if tree is empty)
     */
    public Position<E> root() {
      return root;
    }
  
    /**
     * Returns the Position of p's parent (or null if p is root).
     *
     * @param p    A valid Position within the tree
     * @return Position of p's parent (or null if p is root)
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    public Position<E> parent(Position<E> p) throws IllegalArgumentException {
      Node<E> node = validate(p);
      return node.getParent();
    }
  
    /**
     * Returns the Position of p's NW child (or null if no child exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the NW child (or null if no child exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    public Position<E> NW(Position<E> p) throws IllegalArgumentException {
      Node<E> node = validate(p);
      return node.getNW();
    }
  
	/**
     * Returns the Position of p's NE child (or null if no child exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the NE child (or null if no child exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    public Position<E> NE(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return node.getNE();
	  }
	
	/**
     * Returns the Position of p's SW child (or null if no child exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the SW child (or null if no child exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    public Position<E> SW(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return node.getSW();
	  }
	
	/**
     * Returns the Position of p's SE child (or null if no child exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the SE child (or null if no child exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    public Position<E> SE(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return node.getSE();
	  }
	
		  
  
    // update methods supported by this class
    /**
     * Places element e at the root of an empty tree and returns its new Position.
     *
     * @param e   the new element
     * @return the Position of the new element
     * @throws IllegalStateException if the tree is not empty
     */
    public Position<E> addRoot(E e) throws IllegalStateException {
      if (!isEmpty()) throw new IllegalStateException("Tree is not empty");
      root = createNode(e, null, null, null,null,null);
      size = 1;
      return root;
    }
  
    /**
     * Creates a new NW child of Position p storing element e and returns its Position.
     *
     * @param p   the Position to the NW of which the new element is inserted
     * @param e   the new element
     * @return the Position of the new element
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     * @throws IllegalArgumentException if p already has a NW child
     */
    public Position<E> addNW(Position<E> p, E e)
                            throws IllegalArgumentException {
      Node<E> parent = validate(p);
      if (parent.getNW() != null)
        throw new IllegalArgumentException("p already has a NW child");
      Node<E> child = createNode(e, parent, null, null,null,null);
      parent.setNW(child);
      size++;
      return child;
	}
	
	/**
     * Creates a new NE child of Position p storing element e and returns its Position.
     *
     * @param p   the Position to the NE of which the new element is inserted
     * @param e   the new element
     * @return the Position of the new element
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     * @throws IllegalArgumentException if p already has a NE child
     */
    public Position<E> addNE(Position<E> p, E e)
                            throws IllegalArgumentException {
      Node<E> parent = validate(p);
      if (parent.getNE() != null)
        throw new IllegalArgumentException("p already has a NE child");
      Node<E> child = createNode(e, parent, null, null,null,null);
      parent.setNE(child);
      size++;
      return child;
	}
	
	/**
     * Creates a new SW child of Position p storing element e and returns its Position.
     *
     * @param p   the Position to the SW of which the new element is inserted
     * @param e   the new element
     * @return the Position of the new element
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     * @throws IllegalArgumentException if p already has a SW child
     */
    public Position<E> addSW(Position<E> p, E e)
                            throws IllegalArgumentException {
      Node<E> parent = validate(p);
      if (parent.getSW() != null)
        throw new IllegalArgumentException("p already has a SW child");
      Node<E> child = createNode(e, parent, null, null,null,null);
      parent.setSW(child);
      size++;
      return child;
	}
	
	/**
     * Creates a new SE child of Position p storing element e and returns its Position.
     *
     * @param p   the Position to the SE of which the new element is inserted
     * @param e   the new element
     * @return the Position of the new element
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     * @throws IllegalArgumentException if p already has a SE child
     */
    public Position<E> addSE(Position<E> p, E e)
                            throws IllegalArgumentException {
      Node<E> parent = validate(p);
      if (parent.getSE() != null)
        throw new IllegalArgumentException("p already has a SE child");
      Node<E> child = createNode(e, parent, null, null,null,null);
      parent.setSE(child);
      size++;
      return child;
    }
  
  
    /**
     * Replaces the element at Position p with element e and returns the replaced element.
     *
     * @param p   the relevant Position
     * @param e   the new element
     * @return the replaced element
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    public E set(Position<E> p, E e) throws IllegalArgumentException {
      Node<E> node = validate(p);
      E temp = node.getElement();
      node.setElement(e);
      return temp;
    }
  
	public int numChildren(Position p){
		Node<E> node = validate(p);
		int num = 0;
		if(node.NE != null)
			num++;
		if(node.NW != null)
			num++;
		if(node.SE != null)
			num++;
		if(node.SW != null)
			num++;
		return num;
			
	}

    /**
     * Removes the node at Position p and replaces it with its child, if any.
     *
     * @param p   the relevant Position
     * @return element that was removed
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     * @throws IllegalArgumentException if p has two or more children.
     */
    public E remove(Position<E> p) throws IllegalArgumentException {
      Node<E> node = validate(p);
      if (numChildren(p) >= 2)
        throw new IllegalArgumentException("p has two or more children");
	  Node<E> child = null;
	  
	  if(node.NE != null)
	  	child = node.NE;
  	  if(node.NW != null)
	  	child = node.NW;
      if(node.SE != null)
	  	child = node.SE;
  	  if(node.SW != null)
	  	child = node.SW;

      if (child != null)
        child.setParent(node.getParent());  // child's grandparent becomes its parent
      if (node == root)
        root = child;                       // child becomes root
      else {
        Node<E> parent = node.getParent();
        if (node == parent.getNW()){
          parent.setNW(child);
		}else if (node == parent.getNE()){
		  parent.setNE(child);
	    }else if (node == parent.getSW()){
		  parent.setSW(child);
		}else if (node == parent.getSE())
		  parent.setSE(child);
		  
      }
      size--;
      E temp = node.getElement();
      node.setElement(null);                // help garbage collection
      node.setNW(null);
	  node.setNE(null);
	  node.setSW(null);
      node.setSE(null);
	  node.setParent(node);                 // our convention for defunct node
	  
      return temp;
    }
  } //----------- end of QuadTree class -----------