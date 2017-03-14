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

import fr.paris.lutece.test.LuteceTestCase;


public class MyFavoritesBusinessTest extends LuteceTestCase
{
    private final static String URL1 = "Url1";
    private final static String URL2 = "Url2";
    private final static int IDICON1 = 1;
    private final static int IDICON2 = 2;
    private final static String LABEL1 = "Label1";
    private final static String LABEL2 = "Label2";

    public void testBusiness(  )
    {
        // Initialize an object
        MyFavorites myFavorites = new MyFavorites();
        myFavorites.setUrl( URL1 );
        myFavorites.setIdIcon( IDICON1 );
        myFavorites.setLabel( LABEL1 );

        // Create test
        MyFavoritesHome.create( myFavorites );
        MyFavorites myFavoritesStored = MyFavoritesHome.findByPrimaryKey( myFavorites.getId( ) );
        assertEquals( myFavoritesStored.getUrl() , myFavorites.getUrl( ) );
        assertEquals( myFavoritesStored.getIdIcon() , myFavorites.getIdIcon( ) );
        assertEquals( myFavoritesStored.getLabel() , myFavorites.getLabel( ) );

        // Update test
        myFavorites.setUrl( URL2 );
        myFavorites.setIdIcon( IDICON2 );
        myFavorites.setLabel( LABEL2 );
        MyFavoritesHome.update( myFavorites );
        myFavoritesStored = MyFavoritesHome.findByPrimaryKey( myFavorites.getId( ) );
        assertEquals( myFavoritesStored.getUrl() , myFavorites.getUrl( ) );
        assertEquals( myFavoritesStored.getIdIcon() , myFavorites.getIdIcon( ) );
        assertEquals( myFavoritesStored.getLabel() , myFavorites.getLabel( ) );

        // List test
        MyFavoritesHome.getMyFavoritessList();

        // Delete test
        MyFavoritesHome.remove( myFavorites.getId( ) );
        myFavoritesStored = MyFavoritesHome.findByPrimaryKey( myFavorites.getId( ) );
        assertNull( myFavoritesStored );
        
    }

}