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
package fr.paris.lutece.plugins.myportal.modules.myfavorites.web.rs;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;

import fr.paris.lutece.plugins.myportal.modules.myfavorites.business.MyFavorites;
import fr.paris.lutece.plugins.myportal.modules.myfavorites.business.MyFavoritesHome;
import fr.paris.lutece.plugins.rest.service.RestConstants;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * REST service for MyFavorites resource
 */
@Path( RestConstants.BASE_PATH + MyFavoritesRest.PLUGIN_PATH + MyFavoritesRest.FAVORITES_PATH )
public class MyFavoritesRest
{
    // Path constants
    protected static final String PLUGIN_PATH = "myfavorites/";
    protected static final String FAVORITES_PATH = "favorites/";

    // Format constants
    private static final String FORMAT_FAVORITES_STATUS_RESPONSE = "status";
    private static final String FORMAT_FAVORITES_RESPONSE_RESULT = "result";
    private static final String FORMAT_FAVORITES_KEY = "favorites";
    private static final String FORMAT_FAVORITES_ID = "id";
    private static final String FORMAT_FAVORITES_URL = "url";
    private static final String FORMAT_FAVORITES_LABEL = "label";
    private static final String FORMAT_FAVORITES_USER_ID = "strIdUser";
    private static final String FORMAT_FAVORITES_ORDER = "order";

    // Status constants
    private static final String STATUS_OK = "OK";
    private static final String STATUS_KO = "KO";

    // Parameters
    private static final String PARAMETER_ID_USER = "id_user";

    /**
     * Return the list of all favorites of a user
     * 
     * @param strIdUser
     *            the id of the user
     * @return the list of all favorites of a user
     */
    @GET
    @Path( "{" + PARAMETER_ID_USER + "}" )
    public Response getUserListFavorites( @PathParam( PARAMETER_ID_USER ) String strIdUser )
    {
        String strStatus = STATUS_OK;
        String strFavoritesList = StringUtils.EMPTY;

        try
        {
            List<MyFavorites> listUserFavorites = MyFavoritesHome.getMyFavoritessList( strIdUser );
            if ( listUserFavorites != null && !listUserFavorites.isEmpty( ) )
            {
                strFavoritesList = formatMyFavoritesList( listUserFavorites );
            }
        }
        catch( Exception exception )
        {
            // We set the status at KO if an error occurred during the processing
            strStatus = STATUS_KO;
        }

        // Format the response with the given status and the list of favorites
        String strResponse = formatResponse( strStatus, strFavoritesList );

        return Response.ok( strResponse, MediaType.APPLICATION_JSON ).build( );
    }

    /**
     * Return the Json response with the given status
     * 
     * @param strStatus
     *            The status of the treatment "OK" by default "KO" if an error occurred during the processing
     * @param strResponse
     *            The response to send
     * @return the Json response with the given status
     */
    private String formatResponse( String strStatus, String strResponse )
    {
        JSONObject jsonResponse = new JSONObject( );
        jsonResponse.accumulate( FORMAT_FAVORITES_STATUS_RESPONSE, strStatus );
        jsonResponse.accumulate( FORMAT_FAVORITES_RESPONSE_RESULT, strResponse );

        return jsonResponse.toString( );
    }

    /**
     * Return the Json of a list of MyFavorites object
     * 
     * @param listUserFavorites
     *            the list of the favorites to format
     * @return the Json of a list of MyFavorites object
     */
    private String formatMyFavoritesList( List<MyFavorites> listUserFavorites )
    {
        JSONObject jsonResponse = new JSONObject( );
        JSONArray jsonAllMyFavorites = new JSONArray( );

        for ( MyFavorites myFavorites : listUserFavorites )
        {
            JSONObject jsonMyFavorites = new JSONObject( );
            add( jsonMyFavorites, myFavorites );
            jsonAllMyFavorites.add( jsonMyFavorites );
        }

        jsonResponse.accumulate( FORMAT_FAVORITES_KEY, jsonAllMyFavorites );

        return jsonResponse.toString( );
    }

    /**
     * Add the data from a MyFavorites object to a JsonObject
     * 
     * @param jsonMyFavorites
     *            the Json to include the data
     * @param myFavorites
     *            the MyFavorites to retrieve the data from
     */
    private void add( JSONObject jsonMyFavorites, MyFavorites myFavorites )
    {
        if ( jsonMyFavorites != null && myFavorites != null )
        {
            jsonMyFavorites.accumulate( FORMAT_FAVORITES_ID, myFavorites.getId( ) );
            jsonMyFavorites.accumulate( FORMAT_FAVORITES_URL, myFavorites.getUrl( ) );
            jsonMyFavorites.accumulate( FORMAT_FAVORITES_LABEL, myFavorites.getLabel( ) );
            jsonMyFavorites.accumulate( FORMAT_FAVORITES_USER_ID, myFavorites.getIdUser( ) );
            jsonMyFavorites.accumulate( FORMAT_FAVORITES_ORDER, myFavorites.getOrder( ) );
        }
    }
}
