package fr.paris.lutece.plugins.myportal.modules.myfavorites.services;


public class MyFavoritesService {
	
	   private static MyFavoritesService _singleton;
	
	   public static synchronized MyFavoritesService getInstance(  )
	   {
	        if ( _singleton == null )
	        {
	            _singleton = new MyFavoritesService(  );
	        }

	        return _singleton;
	    }
	   
	   

}
