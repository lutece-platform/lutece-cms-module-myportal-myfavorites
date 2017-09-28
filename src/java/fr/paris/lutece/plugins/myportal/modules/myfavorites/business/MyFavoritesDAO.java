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
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * This class provides Data Access methods for MyFavorites objects
 */
public final class MyFavoritesDAO implements IMyFavoritesDAO
{
    // Constants
    private static final String SQL_QUERY_NEW_PK = "SELECT max( id_my_favorites ) FROM myportal_myfavorites_myfavorites";
    private static final String SQL_QUERY_SELECT = "SELECT id_my_favorites, url, id_icon, label, user_id, myfavorites_order FROM myportal_myfavorites_myfavorites WHERE id_my_favorites = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO myportal_myfavorites_myfavorites ( id_my_favorites, url, id_icon, label, user_id, myfavorites_order ) VALUES ( ?, ?, ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM myportal_myfavorites_myfavorites WHERE id_my_favorites = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE myportal_myfavorites_myfavorites SET id_my_favorites = ?, url = ?, id_icon = ?, label = ?, user_id = ?, myfavorites_order = ? WHERE id_my_favorites = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_my_favorites, url, id_icon, label, user_id, myfavorites_order FROM myportal_myfavorites_myfavorites";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_my_favorites FROM myportal_myfavorites_myfavorites";
    private static final String SQL_QUERY_SELECT_BY_USER = "SELECT id_my_favorites, url, id_icon, label, user_id, myfavorites_order FROM myportal_myfavorites_myfavorites WHERE user_id = ? ORDER BY myfavorites_order";
    private static final String SQL_QUERY_SELECT_ORDER_BY_USER = "SELECT myfavorites_order FROM myportal_myfavorites_myfavorites WHERE user_id = ? ORDER BY myfavorites_order";
    private static final String SQL_QUERY_SELECT_BY_ORDER = "SELECT id_my_favorites, url, id_icon, label, user_id, myfavorites_order FROM myportal_myfavorites_myfavorites WHERE user_id = ? AND myfavorites_order = ?";
    private static final String SQL_QUERY_UPDATE_ORDER = "UPDATE myportal_myfavorites_myfavorites SET myfavorites_order = ? WHERE id_my_favorites = ?";

    /**
     * Generates a new primary key
     * 
     * @param plugin
     *            The Plugin
     * @return The new primary key
     */
    public int newPrimaryKey( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_PK, plugin );
        daoUtil.executeQuery( );
        int nKey = 1;

        if ( daoUtil.next( ) )
        {
            nKey = daoUtil.getInt( 1 ) + 1;
        }

        daoUtil.free( );
        return nKey;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( MyFavorites myFavorites, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );
        myFavorites.setId( newPrimaryKey( plugin ) );
        int nIndex = 1;

        daoUtil.setInt( nIndex++, myFavorites.getId( ) );
        daoUtil.setString( nIndex++, myFavorites.getUrl( ) );
        daoUtil.setInt( nIndex++, myFavorites.getIdIcon( ) );
        daoUtil.setString( nIndex++, myFavorites.getLabel( ) );
        daoUtil.setString( nIndex++, myFavorites.getIdUser( ) );
        daoUtil.setInt( nIndex++, myFavorites.getOrder( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public MyFavorites load( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeQuery( );
        MyFavorites myFavorites = null;

        if ( daoUtil.next( ) )
        {
            myFavorites = new MyFavorites( );
            int nIndex = 1;

            myFavorites.setId( daoUtil.getInt( nIndex++ ) );
            myFavorites.setUrl( daoUtil.getString( nIndex++ ) );
            myFavorites.setIdIcon( daoUtil.getInt( nIndex++ ) );
            myFavorites.setLabel( daoUtil.getString( nIndex++ ) );
            myFavorites.setIdUser( daoUtil.getString( nIndex++ ) );
            myFavorites.setOrder( daoUtil.getInt( nIndex++ ) );
        }

        daoUtil.free( );
        return myFavorites;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void store( MyFavorites myFavorites, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        int nIndex = 1;

        daoUtil.setInt( nIndex++, myFavorites.getId( ) );
        daoUtil.setString( nIndex++, myFavorites.getUrl( ) );
        daoUtil.setInt( nIndex++, myFavorites.getIdIcon( ) );
        daoUtil.setString( nIndex++, myFavorites.getLabel( ) );
        daoUtil.setString( nIndex++, myFavorites.getIdUser( ) );
        daoUtil.setInt( nIndex++, myFavorites.getOrder( ) );

        daoUtil.setInt( nIndex, myFavorites.getId( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<MyFavorites> selectMyFavoritesList( String idUser, Plugin plugin )
    {
        List<MyFavorites> myFavoritesList = new ArrayList<MyFavorites>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_USER, plugin );
        daoUtil.setString( 1, idUser );

        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            MyFavorites myFavorites = new MyFavorites( );
            int nIndex = 1;

            myFavorites.setId( daoUtil.getInt( nIndex++ ) );
            myFavorites.setUrl( daoUtil.getString( nIndex++ ) );
            myFavorites.setIdIcon( daoUtil.getInt( nIndex++ ) );
            myFavorites.setLabel( daoUtil.getString( nIndex++ ) );
            myFavorites.setIdUser( daoUtil.getString( nIndex++ ) );
            myFavorites.setOrder( daoUtil.getInt( nIndex++ ) );

            myFavoritesList.add( myFavorites );
        }

        daoUtil.free( );
        return myFavoritesList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<MyFavorites> selectMyFavoritessList( Plugin plugin )
    {
        List<MyFavorites> myFavoritesList = new ArrayList<MyFavorites>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            MyFavorites myFavorites = new MyFavorites( );
            int nIndex = 1;

            myFavorites.setId( daoUtil.getInt( nIndex++ ) );
            myFavorites.setUrl( daoUtil.getString( nIndex++ ) );
            myFavorites.setIdIcon( daoUtil.getInt( nIndex++ ) );
            myFavorites.setLabel( daoUtil.getString( nIndex++ ) );
            myFavorites.setIdUser( daoUtil.getString( nIndex++ ) );
            myFavorites.setOrder( daoUtil.getInt( nIndex++ ) );

            myFavoritesList.add( myFavorites );
        }

        daoUtil.free( );
        return myFavoritesList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdMyFavoritessList( Plugin plugin )
    {
        List<Integer> myFavoritesList = new ArrayList<Integer>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            myFavoritesList.add( daoUtil.getInt( 1 ) );
        }

        daoUtil.free( );
        return myFavoritesList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectMyFavoritessReferenceList( Plugin plugin )
    {
        ReferenceList myFavoritesList = new ReferenceList( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            myFavoritesList.addItem( daoUtil.getInt( 1 ), daoUtil.getString( 2 ) );
        }

        daoUtil.free( );
        return myFavoritesList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectMyFavoritesOrderList( String strIdUser, Plugin plugin )
    {
        List<Integer> myFavoritesOrderList = new ArrayList<Integer>( );

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_ORDER_BY_USER, plugin );
        daoUtil.setString( 1, strIdUser );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            myFavoritesOrderList.add( daoUtil.getInt( NumberUtils.INTEGER_ONE ) );
        }
        daoUtil.free( );

        return myFavoritesOrderList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public MyFavorites selectUserFavoriteByOrder( String strIdUser, int nOrder, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_ORDER, plugin );
        daoUtil.setString( 1, strIdUser );
        daoUtil.setInt( 2, nOrder );
        daoUtil.executeQuery( );
        MyFavorites myFavorites = null;

        if ( daoUtil.next( ) )
        {
            myFavorites = new MyFavorites( );
            int nIndex = 1;

            myFavorites.setId( daoUtil.getInt( nIndex++ ) );
            myFavorites.setUrl( daoUtil.getString( nIndex++ ) );
            myFavorites.setIdIcon( daoUtil.getInt( nIndex++ ) );
            myFavorites.setLabel( daoUtil.getString( nIndex++ ) );
            myFavorites.setIdUser( daoUtil.getString( nIndex++ ) );
            myFavorites.setOrder( daoUtil.getInt( nIndex++ ) );
        }

        daoUtil.free( );

        return myFavorites;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void updateFavoritesOrder( int nFavoritesId, int nOrder, Plugin plugin )
    {
        if ( nFavoritesId > 0 && nOrder > 0 )
        {
            DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE_ORDER, plugin );
            int nIndex = 1;

            daoUtil.setInt( nIndex++, nOrder );
            daoUtil.setInt( nIndex, nFavoritesId );

            daoUtil.executeUpdate( );
            daoUtil.free( );
        }
    }
}
