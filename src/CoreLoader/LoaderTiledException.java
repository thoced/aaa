package CoreLoader;

public class LoaderTiledException extends Exception 
{

	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	
	public String getMessage() {
		// TODO Auto-generated method stub
		return "Erreur dans le flux";
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#printStackTrace()
	 */
	
	public void printStackTrace() {
		// TODO Auto-generated method stub
		super.printStackTrace();
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#toString()
	 */
	
	public String toString() {
		// TODO Auto-generated method stub
		return "Erreur dans le flux LoaderTiled";
	}
	
	
	
}
