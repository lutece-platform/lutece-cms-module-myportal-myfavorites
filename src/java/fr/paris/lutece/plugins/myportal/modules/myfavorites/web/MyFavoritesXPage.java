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

package fr.paris.lutece.plugins.myportal.modules.myfavorites.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import fr.paris.lutece.plugins.myportal.modules.myfavorites.business.MyFavorites;
import fr.paris.lutece.plugins.myportal.modules.myfavorites.business.MyFavoritesHome;
import fr.paris.lutece.plugins.myportal.modules.myfavorites.services.MyFavoritesService;
import fr.paris.lutece.plugins.myportal.service.IconService;
import fr.paris.lutece.plugins.myportal.service.MyPortalPlugin;
import fr.paris.lutece.plugins.myportal.service.WidgetContentService;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.message.SiteMessage;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.message.SiteMessageService;
import fr.paris.lutece.portal.service.page.PageNotFoundException;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.security.SecurityService;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.util.mvc.xpage.MVCApplication;
import fr.paris.lutece.portal.util.mvc.xpage.annotations.Controller;
import fr.paris.lutece.portal.web.constants.Messages;
import fr.paris.lutece.portal.web.xpages.XPage;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.url.UrlItem;

/**
 * This class provides the user interface to manage MyFavorites xpages ( manage, create, modify, remove )
 */
@Controller( xpageName = "myfavorites", pageTitleI18nKey = "module.myportal.myfavorites.xpage.myfavorites.pageTitle", pagePathI18nKey = "module.myportal.myfavorites.xpage.myfavorites.pagePathLabel" )
public class MyFavoritesXPage extends MVCApplication
{
    /**
     * Generated serial ID
     */
    private static final long serialVersionUID = -2075209499748771950L;

    // Templates
    private static final String TEMPLATE_MANAGE_MYFAVORITESS = "/skin/plugins/myportal/modules/myfavorites/manage_myfavoritess.html";
    private static final String TEMPLATE_CREATE_MYFAVORITES = "/skin/plugins/myportal/modules/myfavorites/create_myfavorites.html";
    private static final String TEMPLATE_MODIFY_MYFAVORITES = "/skin/plugins/myportal/modules/myfavorites/modify_myfavorites.html";

    // JSP
    private static final String JSP_PAGE_REMOVE_FAVORITE = "jsp/site/plugins/myportal/modules/myfavorites/DoRemoveMyFavorite.jsp";
    // Parameters
    private static final String PARAMETER_ID_MYFAVORITES = "id";
    private static final String PARAMETER_FAVORITES_URL_RETURN = "myfavorites_url_return";
    private static final String PARAMETER_ID_WIDGET = "id_widget";
    private static final String PARAMETER_MYPORTAL_URL_RETURN = "myportal_url_return";
    private static final String PARAMETER_BACK = "back";

    // Markers
    private static final String MARK_MYFAVORITES_LIST = "myfavorites_list";
    private static final String MARK_MYFAVORITES = "myfavorites";

    private static final String MARK_MYFAVORITES_URL_RETURN = "myfavorites_url_return";
    private static final String MARK_MYPORTAL_URL_RETURN = "myportal_url_return";
    private static final String MARK_ID_WIDGET = "id_widget";
    private static final String MARK_ICONS_LIST = "icons_list";
    private static final String MARK_FAVORITES_ORDER_LIST = "favorites_order_list";
    private static final String MARK_USER_CONNECTED = "user_connected";

    // Message
    private static final String MESSAGE_CONFIRM_REMOVE_MYFAVORITES = "module.myportal.myfavorites.message.confirmRemoveMyFavorites";

    // Views
    private static final String VIEW_MANAGE_MYFAVORITESS = "manageMyFavoritess";
    private static final String VIEW_CREATE_MYFAVORITES = "createMyFavorites";
    private static final String VIEW_MODIFY_MYFAVORITES = "modifyMyFavorites";

    // Actions
    private static final String ACTION_CREATE_MYFAVORITES = "createMyFavorites";
    private static final String ACTION_MODIFY_MYFAVORITES = "modifyMyFavorites";
    private static final String ACTION_REMOVE_MYFAVORITES = "removeMyFavorites";
    private static final String ACTION_CONFIRM_REMOVE_MYFAVORITES = "confirmRemoveMyFavorites";

    // Infos
    private static final String INFO_MYFAVORITES_CREATED = "module.myportal.myfavorites.info.myfavorites.created";
    private static final String INFO_MYFAVORITES_UPDATED = "module.myportal.myfavorites.info.myfavorites.updated";
    private static final String INFO_MYFAVORITES_REMOVED = "module.myportal.myfavorites.info.myfavorites.removed";

    // Errors
    private static final String ERROR_USER_NOT_CONNECTED = "module.myportal.myfavorites.error.user.notConnected";

    // Session variable to store working values
    private MyFavorites _myfavorites;
    private final MyFavoritesService _myFavoritesService = SpringContextService.getBean( MyFavoritesService.BEAN_NAME );
    private final WidgetContentService _widgetContentService = SpringContextService.getBean( WidgetContentService.BEAN_NAME );
    
    @View( value = VIEW_MANAGE_MYFAVORITESS, defaultView = true )
    public XPage getManageMyFavoritess( HttpServletRequest request )
    {
        _myfavorites = null;
        Map<String, Object> model = getModel( );
        model.put( MARK_MYFAVORITES_LIST, MyFavoritesHome.getMyFavoritessList( ) );

        return getXPage( TEMPLATE_MANAGE_MYFAVORITESS, request.getLocale( ), model );
    }

    /**
     * Returns the form to create a myfavorites
     *
     * @param request
     *            The Http request
     * @return the html code of the myfavorites form
     * @throws SiteMessageException
     * @throws UserNotSignedException
     */
    @View( VIEW_CREATE_MYFAVORITES )
    public XPage getCreateMyFavorites( HttpServletRequest request ) throws SiteMessageException
    {
        Map<String, Object> model = getModel( );

        LuteceUser user = null;
        try
        {
            user = getUser( request );
            model.put( MARK_USER_CONNECTED, Boolean.TRUE );
        }
        catch( UserNotSignedException exception )
        {
            addError( I18nService.getLocalizedString( ERROR_USER_NOT_CONNECTED, request.getLocale( ) ) );
            model.put( MARK_USER_CONNECTED, Boolean.FALSE );
        }

        _myfavorites = ( _myfavorites != null ) ? _myfavorites : new MyFavorites( );

        String strIdWidget = request.getParameter( PARAMETER_ID_WIDGET );
        String strMyPortalMyFavoritesUrlReturn = request.getParameter( MARK_MYFAVORITES_URL_RETURN );
        String strMyPortalUrlReturn = request.getParameter( PARAMETER_MYPORTAL_URL_RETURN );

        if ( StringUtils.isBlank( strMyPortalMyFavoritesUrlReturn ) )
        {
            strMyPortalMyFavoritesUrlReturn = StringUtils.EMPTY;
        }

        if ( StringUtils.isBlank( strMyPortalUrlReturn ) )
        {
            strMyPortalUrlReturn = StringUtils.EMPTY;
        }

        if ( StringUtils.isNotBlank( strIdWidget ) && StringUtils.isNumeric( strIdWidget ) )
        {
            model.put( MARK_ICONS_LIST, IconService.getInstance( ).getIconFO( ) );
            model.put( PARAMETER_FAVORITES_URL_RETURN, strMyPortalMyFavoritesUrlReturn );
            model.put( MARK_MYPORTAL_URL_RETURN, strMyPortalUrlReturn );
            model.put( MARK_ID_WIDGET, strIdWidget );

            String strIdUser = ( user != null ) ? user.getName( ) : StringUtils.EMPTY;
            ReferenceList listOrderUserFavorites = _myFavoritesService.getUserOrderFavoritesListForCreation( strIdUser );
            model.put( MARK_FAVORITES_ORDER_LIST, listOrderUserFavorites );
        }

        model.put( MARK_MYFAVORITES, _myfavorites );
        fillCommons( model );

        return getXPage( TEMPLATE_CREATE_MYFAVORITES, request.getLocale( ), model );
    }

    /**
     * Process the data capture form of a new myfavorites
     *
     * @param request
     *            The Http Request
     * @return The Jsp URL of the process result
     * @throws SiteMessageException
     * @throws UserNotSignedException
     */
    @Action( ACTION_CREATE_MYFAVORITES )
    public String doCreateMyFavorites( HttpServletRequest request ) throws SiteMessageException, UserNotSignedException
    {
        // Manage the case where the user cancel his action
        String strUrlReturn = request.getParameter( PARAMETER_FAVORITES_URL_RETURN );
        if ( request.getParameter( PARAMETER_BACK ) != null && StringUtils.isNotEmpty( strUrlReturn ) )
        {
            return strUrlReturn;
        }

        LuteceUser user = getUser( request );

        // Populate MyFavorites
        _myfavorites = ( _myfavorites != null ) ? _myfavorites : new MyFavorites( );
        populate( _myfavorites, request );
        _myfavorites.setIdUser( user.getName( ) );

        // Check constraints
        if ( !validateBean( _myfavorites, getLocale( request ) ) )
        {
            SiteMessageService.setMessage( request, Messages.MANDATORY_FIELDS, SiteMessage.TYPE_ERROR );
        }

        String strIdWidget = request.getParameter( PARAMETER_ID_WIDGET );
        if ( StringUtils.isNotBlank( strIdWidget ) && StringUtils.isNumeric( strIdWidget ) )
        {
        	int nIdWidget = Integer.parseInt( strIdWidget );
        	_widgetContentService.removeCache(nIdWidget, user);
            String strUserName = ( user != null ) ? user.getName( ) : StringUtils.EMPTY;
            _myFavoritesService.manageMyFavoritesCreation( strUserName, _myfavorites );
            addInfo( INFO_MYFAVORITES_CREATED, getLocale( request ) );
        }
        else
        {
            SiteMessageService.setMessage( request, Messages.MANDATORY_FIELDS, SiteMessage.TYPE_ERROR );
        }

        return strUrlReturn;
    }

    /**
     * Manages the removal form of a myfavorites whose identifier is in the http request
     *
     * @param request
     *            The Http request
     * @return the html code to confirm
     * @throws fr.paris.lutece.portal.service.message.SiteMessageException
     * @throws UserNotSignedException
     */
    @Action( ACTION_CONFIRM_REMOVE_MYFAVORITES )
    public XPage getConfirmRemoveMyFavorites( HttpServletRequest request ) throws SiteMessageException, UserNotSignedException
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_MYFAVORITES ) );
        String strMyPortalMyFavoritesUrlReturn = request.getParameter( MARK_MYFAVORITES_URL_RETURN );
        String strIdWidget = request.getParameter( PARAMETER_ID_WIDGET );

        UrlItem url = new UrlItem( JSP_PAGE_REMOVE_FAVORITE );
        url.addParameter( PARAMETER_ID_MYFAVORITES, nId );
        url.addParameter( PARAMETER_FAVORITES_URL_RETURN, strMyPortalMyFavoritesUrlReturn );
        url.addParameter( MARK_ID_WIDGET, strIdWidget );

        SiteMessageService.setMessage( request, MESSAGE_CONFIRM_REMOVE_MYFAVORITES, SiteMessage.TYPE_CONFIRMATION, url.getUrl( ) );
        return null;
    }

    /**
     * Handles the removal form of a myfavorites
     *
     * @param request
     *            The Http request
     * @return the jsp URL to display the form to manage myfavoritess
     * @throws UserNotSignedException
     */
    @Action( ACTION_REMOVE_MYFAVORITES )
    public String doRemoveMyFavorites( HttpServletRequest request ) throws UserNotSignedException
    {
        LuteceUser user = getUser( request );
        String strUrlReturn = request.getParameter( PARAMETER_FAVORITES_URL_RETURN );

        String strIdWidget = request.getParameter( PARAMETER_ID_WIDGET );
        int nIdWidget = Integer.parseInt( strIdWidget );
        _widgetContentService.removeCache(nIdWidget, user);
        
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_MYFAVORITES ) );
        String strIdUser = ( user != null ) ? user.getName( ) : StringUtils.EMPTY;
        _myFavoritesService.manageMyFavoritesRemoving( nId, strIdUser );

        addInfo( INFO_MYFAVORITES_REMOVED, getLocale( request ) );

        return strUrlReturn;
    }

    /**
     * Returns the form to update info about a myfavorites
     *
     * @param request
     *            The Http request
     * @return The HTML form to update info
     * @throws UserNotSignedException
     */
    @View( VIEW_MODIFY_MYFAVORITES )
    public XPage getModifyMyFavorites( HttpServletRequest request ) throws UserNotSignedException
    {
        LuteceUser user = getUser( request );

        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_MYFAVORITES ) );

        if ( _myfavorites == null || ( _myfavorites.getId( ) != nId ) )
        {
            _myfavorites = MyFavoritesHome.findByPrimaryKey( nId );
        }

        Map<String, Object> model = getModel( );

        String strIdWidget = request.getParameter( PARAMETER_ID_WIDGET );
        String strMyPortalMyFavoritesUrlReturn = request.getParameter( MARK_MYFAVORITES_URL_RETURN );
        String strMyPortalUrlReturn = request.getParameter( PARAMETER_MYPORTAL_URL_RETURN );

        if ( StringUtils.isBlank( strMyPortalMyFavoritesUrlReturn ) )
        {
            strMyPortalMyFavoritesUrlReturn = StringUtils.EMPTY;
        }

        if ( StringUtils.isBlank( strMyPortalUrlReturn ) )
        {
            strMyPortalUrlReturn = StringUtils.EMPTY;
        }

        if ( StringUtils.isNotBlank( strIdWidget ) && StringUtils.isNumeric( strIdWidget ) )
        {
            model.put( MARK_ICONS_LIST, IconService.getInstance( ).getIconFO( ) );
            model.put( PARAMETER_FAVORITES_URL_RETURN, strMyPortalMyFavoritesUrlReturn );
            model.put( MARK_MYPORTAL_URL_RETURN, strMyPortalUrlReturn );
            model.put( MARK_ID_WIDGET, strIdWidget );
        }

        model.put( MARK_MYFAVORITES, _myfavorites );

        String strIdUser = ( user != null ) ? user.getName( ) : StringUtils.EMPTY;
        ReferenceList listOrderUserFavorites = _myFavoritesService.getUserOrderFavorites( strIdUser );
        model.put( MARK_FAVORITES_ORDER_LIST, listOrderUserFavorites );

        return getXPage( TEMPLATE_MODIFY_MYFAVORITES, request.getLocale( ), model );
    }

    /**
     * Process the change form of a myfavorites
     *
     * @param request
     *            The Http request
     * @return The Jsp URL of the process result
     * @throws UserNotSignedException
     * @throws SiteMessageException
     */
    @Action( ACTION_MODIFY_MYFAVORITES )
    public String doModifyMyFavorites( HttpServletRequest request ) throws UserNotSignedException, SiteMessageException
    {
        // Manage the case where the user cancel his action
        String strUrlReturn = request.getParameter( PARAMETER_FAVORITES_URL_RETURN );
        if ( request.getParameter( PARAMETER_BACK ) != null && StringUtils.isNotEmpty( strUrlReturn ) )
        {
            return strUrlReturn;
        }

        LuteceUser user = getUser( request );

        int nIdMyFavorites = NumberUtils.toInt( request.getParameter( PARAMETER_ID_MYFAVORITES ), NumberUtils.INTEGER_ZERO );
        MyFavorites myFavorites = MyFavoritesHome.findByPrimaryKey( nIdMyFavorites );

        int nOrderOrigin = NumberUtils.INTEGER_ZERO;
        if ( myFavorites != null )
        {
            nOrderOrigin = myFavorites.getOrder( );
        }

        _myfavorites = ( _myfavorites != null ) ? _myfavorites : new MyFavorites( );
        populate( _myfavorites, request );
        _myfavorites.setIdUser( user.getName( ) );

        // Check constraints
        if ( !validateBean( _myfavorites, getLocale( request ) ) )
        {
            SiteMessageService.setMessage( request, Messages.MANDATORY_FIELDS, SiteMessage.TYPE_ERROR );
        }

        String strIdWidget = request.getParameter( PARAMETER_ID_WIDGET );
        int nIdWidget = Integer.parseInt( strIdWidget );
        _widgetContentService.removeCache(nIdWidget, user);
        String strIdUser = ( user != null ) ? user.getName( ) : StringUtils.EMPTY;
        _myFavoritesService.manageModifyMyFavorites( _myfavorites, strIdUser, nOrderOrigin );
        addInfo( INFO_MYFAVORITES_UPDATED, getLocale( request ) );

        return strUrlReturn;
    }

    /**
     * Gets the user from the request
     * 
     * @param request
     *            The HTTP user
     * @return The Lutece User
     * @throws UserNotSignedException
     *             exception if user not connected
     */
    public LuteceUser getUser( HttpServletRequest request ) throws UserNotSignedException
    {
        if ( SecurityService.isAuthenticationEnable( ) )
        {
            LuteceUser user = SecurityService.getInstance( ).getRemoteUser( request );

            if ( user == null )
            {
                throw new UserNotSignedException( );
            }

            return user;
        }
        else
        {
            throw new PageNotFoundException( );
        }
    }
}
