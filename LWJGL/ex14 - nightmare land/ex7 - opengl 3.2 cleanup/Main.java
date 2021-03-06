class Main{
	private GlobalObjects GO = new GlobalObjects();
	
	Thread myWindowThread = new Thread(new Window(
			800,					//Resolution width
			600,					//Resolution height
			60,						//Maximum FPS
			"OpenGL 3.2 - ex7",		//Window title
			GO		//Pass GlobalObject to window-class
			));
	
	public void run(){
		myWindowThread.start();
	}
	
	public static void main(String[] args){
		Main application = new Main();
		application.run();
	}
}

/* Here is how this is organized:
 * GlobalObjects.java keep global variables, usable
 * by all threads and classes. If a variable in there
 * is changed by one class or thread, all the others
 * using that variable will be affected. Neat! huh?
 * 
 * Window.java maintains the window and input and the
 * idea is to make a 3D-thread that renders onto two
 * screen buffers, flip flopping between them. When
 * the 3D-thread is using one buffer, the window 
 * thread can grab the info from the other and vice
 * versa.
 */