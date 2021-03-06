/*
 * Copyright (c) 2002-2016, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.myportal.modules.myfavorites.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.util.ReferenceList;

import java.util.List;

/**
 * This class provides instances management methods (create, find, ...) for MyFavorites objects
 */
public final class MyFavoritesHome
{
    // Static variable pointed at the DAO instance
    private static IMyFavoritesDAO _dao = SpringContextService.getBean( "myportal-myfavorites.myFavoritesDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "myportal-myfavorites" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private MyFavoritesHome( )
    {
    }

    /**
     * Create an instance of the myFavorites class
     * 
     * @param myFavorites
     *            The instance of the MyFavorites which contains the informations to store
     * @return The instance of myFavorites which has been created with its primary key.
     */
    public static MyFavorites create( MyFavorites myFavorites )
    {
        _dao.insert( myFavorites, _plugin );

        return myFavorites;
    }

    /**
     * Update of the myFavorites which is specified in parameter
     * 
     * @param myFavorites
     *            The instance of the MyFavorites which contains the data to store
     * @return The instance of the myFavorites which has been updated
     */
    public static MyFavorites update( MyFavorites myFavorites )
    {
        _dao.store( myFavorites, _plugin );

        return myFavorites;
    }

    /**
     * Remove the myFavorites whose identifier is specified in parameter
     * 
     * @param nKey
     *            The myFavorites Id
     */
    public static void remove( int nKey )
    {
        _dao.delete( nKey, _plugin );
    }

    /**
     * Returns an instance of a myFavorites whose identifier is specified in parameter
     * 
     * @param nKey
     *            The myFavorites primary key
     * @return an instance of MyFavorites
     */
    public static MyFavorites findByPrimaryKey( int nKey )
    {
        return _dao.load( nKey, _plugin );
    }

    /**
     * Load the data of all the myFavorites objects and returns them as a list
     * 
     * @return the list which contains the data of all the myFavorites objects
     */
    public static List<MyFavorites> getMyFavoritessList( )
    {
        return _dao.selectMyFavoritessList( _plugin );
    }

    /**
     * Load the data of all the myFavorites objects and returns them as a list
     * 
     * @return the list which contains the data of all the myFavorites objects
     */
    public static List<MyFavorites> getMyFavoritessList( String idUser )
    {
        return _dao.selectMyFavoritesList( idUser, _plugin );
    }

    /**
     * Load the id of all the myFavorites objects and returns them as a list
     * 
     * @return the list which contains the id of all the myFavorites objects
     */
    public static List<Integer> getIdMyFavoritessList( )
    {
        return _dao.selectIdMyFavoritessList( _plugin );
    }

    /**
     * Load the data of all the myFavorites objects and returns them as a referenceList
     * 
     * @return the referenceList which contains the data of all the myFavorites objects
     */
    public static ReferenceList getMyFavoritessReferenceList( )
    {
        return _dao.selectMyFavoritessReferenceList( _plugin );
    }

    /**
     * Load the list of the order of the favorites of a user
     * 
     * @param strIdUser
     *            the id of the user
     * @return the list of all order of the favorites of the user
     */
    public static List<Integer> getUserFavoritesOrder( String strIdUser )
    {
        return _dao.selectMyFavoritesOrderList( strIdUser, _plugin );
    }

    /**
     * Return the favorite of the user with the specified order null otherwise
     * 
     * @param strIdUser
     *            the id of the user
     * @param nOrder
     *            the order of the Favorite
     * @return the favorite of the user with the specified order null otherwise
     */
    public static MyFavorites getUserFavoriteWithOrder( String strIdUser, int nOrder )
    {
        return _dao.selectUserFavoriteByOrder( strIdUser, nOrder, _plugin );
    }

    /**
     * Update the favorites with the specified id with the new order
     * 
     * @param nIdFavorites
     *            the id of the favorites
     * @param nNewOrder
     *            the new ordr of the favorites
     */
    public static void updateFavoriteOrder( int nIdFavorites, int nNewOrder )
    {
        _dao.updateFavoritesOrder( nIdFavorites, nNewOrder, _plugin );
    }
}
