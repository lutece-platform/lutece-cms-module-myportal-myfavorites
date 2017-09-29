/*
 * Copyright (c) 2002-2017, Mairie de Paris
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
package fr.paris.lutece.plugins.myportal.modules.myfavorites.services;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import fr.paris.lutece.plugins.myportal.modules.myfavorites.business.MyFavorites;
import fr.paris.lutece.plugins.myportal.modules.myfavorites.business.MyFavoritesHome;
import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.ReferenceList;

/**
 * 
 * MyFavorites service
 * 
 */
public class MyFavoritesService
{

    /**
     * Name of the bean of the service
     */
    public static final String BEAN_NAME = "myportal-myfavorites.myFavoritesService";

    /**
     * Return the list of all the order possible for the user for the creation of a new favorite
     * 
     * @param strIdUser
     *            the id of the user
     * @return the list of all the order possible for the user for the creation of a new favorite
     */
    public ReferenceList getUserOrderFavoritesListForCreation( String strIdUser )
    {
        // Retrieve all the order already used by the user
        ReferenceList referenceOrderList = getUserOrderFavorites( strIdUser );
        if ( referenceOrderList == null )
        {
            referenceOrderList = new ReferenceList( );
        }

        // Add another element for the next element to allow the user to add it at the end of his list
        String strNextOrder = String.valueOf( referenceOrderList.size( ) + 1 );
        ReferenceItem referenceItemOrder = new ReferenceItem( );
        referenceItemOrder.setCode( strNextOrder );
        referenceItemOrder.setName( strNextOrder );

        referenceOrderList.add( referenceItemOrder );
        return referenceOrderList;
    }

    /**
     * Return the list of the order of all the favorites of a user
     * 
     * @param strIdUser
     *            the id of the user
     * @return the list of all order of the favorites of the user
     */
    public ReferenceList getUserOrderFavorites( String strIdUser )
    {
        ReferenceList referenceOrderList = new ReferenceList( );

        List<Integer> listOrder = MyFavoritesHome.getUserFavoritesOrder( strIdUser );
        if ( listOrder != null && !listOrder.isEmpty( ) )
        {
            for ( Integer nOrder : listOrder )
            {
                String strOrder = String.valueOf( nOrder );

                ReferenceItem itemOrder = new ReferenceItem( );
                itemOrder.setName( strOrder );
                itemOrder.setCode( strOrder );

                referenceOrderList.add( itemOrder );
            }
        }

        return referenceOrderList;
    }

    /**
     * Manage the creation of a new Favorites and reorder the existing favorites
     * 
     * @param strIdUser
     *            the id of the user
     * @param myFavorites
     *            the favorites to create
     */
    public void manageMyFavoritesCreation( String strIdUser, MyFavorites myFavorites )
    {
        if ( myFavorites != null && StringUtils.isNoneBlank( strIdUser ) )
        {
            int nOrder = myFavorites.getOrder( );
            MyFavorites userFavorite = MyFavoritesHome.getUserFavoriteWithOrder( strIdUser, nOrder );
            if ( userFavorite != null )
            {
                int nOrderLimit = getLastOrderUserFavorites( strIdUser ) + 1;
                updateUserFavoritesOrder( userFavorite, strIdUser, ( nOrder + NumberUtils.INTEGER_ONE ), NumberUtils.INTEGER_ONE, nOrderLimit );
            }

            MyFavoritesHome.create( myFavorites );
        }
    }

    /**
     * Manage the removing of the favorites of a user and reorder the existing favorites
     * 
     * @param nId
     *            the id of the favorites to remove
     * @param strIdUser
     *            the id of the user
     */
    public void manageMyFavoritesRemoving( int nId, String strIdUser )
    {
        if ( nId != 0 && StringUtils.isNotBlank( strIdUser ) )
        {
            MyFavorites myFavoritesToDelete = MyFavoritesHome.findByPrimaryKey( nId );
            if ( myFavoritesToDelete != null )
            {
                // Check if there are favorites after those we remove to reorder them
                int nOrderFavoriteToDelete = myFavoritesToDelete.getOrder( );
                int nOrderLastUserFavorites = getLastOrderUserFavorites( strIdUser );
                if ( nOrderFavoriteToDelete < nOrderLastUserFavorites )
                {
                    MyFavorites myFavoritesLast = MyFavoritesHome.getUserFavoriteWithOrder( strIdUser, nOrderLastUserFavorites );
                    if ( myFavoritesLast != null )
                    {
                        updateUserFavoritesOrder( myFavoritesLast, strIdUser, ( nOrderLastUserFavorites + NumberUtils.INTEGER_MINUS_ONE ),
                                NumberUtils.INTEGER_MINUS_ONE, nOrderFavoriteToDelete );
                    }
                }

                MyFavoritesHome.remove( nId );
            }
        }
    }

    /**
     * Manage the modification of a favorites and reorder the existing favorites
     * 
     * @param myFavorites
     *            the favorites to update
     * @param strIdUser
     *            the id of the user
     * @param nOrderOrigin
     *            the order of the favorites before the modification
     */
    public void manageModifyMyFavorites( MyFavorites myFavorites, String strIdUser, int nOrderOrigin )
    {
        if ( myFavorites != null && StringUtils.isNotBlank( strIdUser ) )
        {
            int nOrder = myFavorites.getOrder( );
            MyFavorites userFavorite = MyFavoritesHome.getUserFavoriteWithOrder( strIdUser, nOrder );
            if ( userFavorite != null )
            {
                // Determine the modifier of the order for the existing favorites
                int nOrderModifier = NumberUtils.INTEGER_ZERO;
                if ( nOrderOrigin > nOrder )
                {
                    // In this case we will push down the existing favorites
                    nOrderModifier = NumberUtils.INTEGER_ONE;
                }
                else
                    if ( nOrderOrigin < nOrder )
                    {
                        // In this case we will push up the existing favorites
                        nOrderModifier = NumberUtils.INTEGER_MINUS_ONE;
                    }
                    else
                    {
                        // In this case there is no modification of the order of the favorites
                        // so we will simply update its data with make modification on the order
                        // of others favorites
                        MyFavoritesHome.update( myFavorites );
                        return;
                    }
                updateUserFavoritesOrder( userFavorite, strIdUser, ( nOrder + nOrderModifier ), nOrderModifier, nOrderOrigin );
            }

            MyFavoritesHome.update( myFavorites );
        }
    }

    /**
     * Update the order of the favorites from the first given
     * 
     * @param myFavorites
     *            the favorites to update
     * @param strIdUser
     *            the id of the user to retrieve the favorites
     * @param nNewOrder
     *            the new order of the favorite
     * @param nOrderModifier
     *            the modifier of the order for the next iteration. For ascending the favorites it will be -1 and for moving favorites down it will be 1.
     * @param nOrderLimit
     *            the order limit to stop the modification
     */
    private void updateUserFavoritesOrder( MyFavorites myFavorites, String strIdUser, int nNewOrder, int nOrderModifier, int nOrderLimit )
    {
        if ( myFavorites != null && StringUtils.isNotBlank( strIdUser ) )
        {
            MyFavorites userFavorite = MyFavoritesHome.getUserFavoriteWithOrder( strIdUser, nNewOrder );

            MyFavoritesHome.updateFavoriteOrder( myFavorites.getId( ), nNewOrder );

            if ( userFavorite != null && userFavorite.getOrder( ) != nOrderLimit )
            {
                updateUserFavoritesOrder( userFavorite, strIdUser, ( nNewOrder + nOrderModifier ), nOrderModifier, nOrderLimit );
            }
        }
    }

    /**
     * Return the last order of the favorites for a specified user
     * 
     * @param strIdUser
     *            the id of the user
     * @return the last order of the favorites for a specified user
     */
    private int getLastOrderUserFavorites( String strIdUser )
    {
        int nOrderLastFavorites = NumberUtils.INTEGER_MINUS_ONE;

        if ( StringUtils.isNotBlank( strIdUser ) )
        {
            List<Integer> listFavoritesOrder = MyFavoritesHome.getUserFavoritesOrder( strIdUser );
            if ( listFavoritesOrder != null && !listFavoritesOrder.isEmpty( ) )
            {
                return listFavoritesOrder.get( listFavoritesOrder.size( ) - 1 );
            }
        }

        return nOrderLastFavorites;
    }

}
