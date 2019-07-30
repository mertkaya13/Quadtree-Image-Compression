# -Quadtree-Image-Compression
Mert KAYA

To compile : 
	
	javac Colour.java
	javac Main.java
	javac Position.java
	javac QuadTree.java

To run:

	java Main -o <outputFileName> -i <inputFileName>
					OR
	java Main -i <inputFileName> -o <outputFileName>

Known Bugs or Limitations : 
	Contains no known bugs.
	
	Only works with ppm images.

	Only works with Square images(in term of pixel count).

File Directory : 

	Main : Creates pixelmapped 2D array than an quadtree's from that and creates 	compressed images of given.

	QuadTree : Tree type of adt. Each Node can have 0 to 4 child.

	Position : An abstraction class.(interface)

	Colour : Contains rgb colour values stored as doubles.

