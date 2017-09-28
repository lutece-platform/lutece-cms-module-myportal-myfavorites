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
import fr.paris.lutece.util.ReferenceList;

import java.util.List;

/**
 * IMyFavoritesDAO Interface
 */
public interface IMyFavoritesDAO
{
    /**
     * Insert a new record in the table.
     * 
     * @param myFavorites
     *            instance of the MyFavorites object to insert
     * @param plugin
     *            the Plugin
     */
    void insert( MyFavorites myFavorites, Plugin plugin );

    /**
     * Update the record in the table
     * 
     * @param myFavorites
     *            the reference of the MyFavorites
     * @param plugin
     *            the Plugin
     */
    void store( MyFavorites myFavorites, Plugin plugin );

    /**
     * Delete a record from the table
     * 
     * @param nKey
     *            The identifier of the MyFavorites to delete
     * @param plugin
     *            the Plugin
     */
    void delete( int nKey, Plugin plugin );

    // /////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Load the data from the table
     * 
     * @param nKey
     *            The identifier of the myFavorites
     * @param plugin
     *            the Plugin
     * @return The instance of the myFavorites
     */
    MyFavorites load( int nKey, Plugin plugin );

    /**
     * Load the data of all the myFavorites objects and returns them as a list
     * 
     * @param plugin
     *            the Plugin
     * @return The list which contains the data of all the myFavorites objects
     */
    List<MyFavorites> selectMyFavoritessList( Plugin plugin );

    /**
     * Load the id of all the myFavorites objects and returns them as a list
     * 
     * @param plugin
     *            the Plugin
     * @return The list which contains the id of all the myFavorites objects
     */
    List<Integer> selectIdMyFavoritessList( Plugin plugin );

    /**
     * Load the data of all the myFavorites objects and returns them as a referenceList
     * 
     * @param plugin
     *            the Plugin
     * @return The referenceList which contains the data of all the myFavorites objects
     */
    ReferenceList selectMyFavoritessReferenceList( Plugin plugin );

    /**
     * 
     * @param idUser
     * @param plugin
     * @return
     */
    List<MyFavorites> selectMyFavoritesList( String idUser, Plugin plugin );

    /**
     * Return the list of all the order of a user favorites
     * 
     * @param strIdUser
     *            the id of the user to retrieve the order list from
     * @param plugin
     *            the Plugin
     * @return the list of all the order of a user favorites
     */
    List<Integer> selectMyFavoritesOrderList( String strIdUser, Plugin plugin );

    /**
     * Return the favorite of the user with the specified order null otherwise
     * 
     * @param strIdUser
     *            the id of the user to find the Favorites from
     * @param nOrder
     *            the order of the favorite to retrieve
     * @param plugin
     *            the plugin
     * @return the favorite of the user with the specified order null otherwise
     */
    MyFavorites selectUserFavoriteByOrder( String strIdUser, int nOrder, Plugin plugin );

    /**
     * Update the favorites with the specified id with the new order
     * 
     * @param nFavoritesId
     *            the id of the favorite to update
     * @param nOrder
     *            the new order of the favorites
     * @param plugin
     *            the plugin
     */
    void updateFavoritesOrder( int nFavoritesId, int nOrder, Plugin plugin );
}
