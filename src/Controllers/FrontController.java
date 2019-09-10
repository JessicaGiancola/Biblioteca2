package Controllers;

import java.util.ArrayList;


public interface FrontController {
	boolean SetData(Object[] T);
	<T> ArrayList<T> GetData(String Query);
}
