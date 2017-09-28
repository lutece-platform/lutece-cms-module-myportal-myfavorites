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

import javax.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import java.io.Serializable;

/**
 * This is the business class for the object MyFavorites
 */
public class MyFavorites implements Serializable
{
    private static final long serialVersionUID = 1L;

    // Variables declarations
    private int _nId;

    @NotEmpty( message = "#i18n{module.myportal.myfavorites.validation.myfavorites.Url.notEmpty}" )
    @Size( max = 255, message = "#i18n{module.myportal.myfavorites.validation.myfavorites.Url.size}" )
    private String _strUrl;

    private int _nIdIcon;

    @NotEmpty( message = "#i18n{module.myportal.myfavorites.validation.myfavorites.Label.notEmpty}" )
    @Size( max = 50, message = "#i18n{module.myportal.myfavorites.validation.myfavorites.Label.size}" )
    private String _strLabel;

    private String _strIdUser;

    private int _nOrder;

    /**
     * Returns the Id
     * 
     * @return The Id
     */
    public int getId( )
    {
        return _nId;
    }

    /**
     * Sets the Id
     * 
     * @param nId
     *            The Id
     */
    public void setId( int nId )
    {
        _nId = nId;
    }

    /**
     * Returns the Url
     * 
     * @return The Url
     */
    public String getUrl( )
    {
        return _strUrl;
    }

    /**
     * Sets the Url
     * 
     * @param strUrl
     *            The Url
     */
    public void setUrl( String strUrl )
    {
        _strUrl = strUrl;
    }

    /**
     * Returns the IdIcon
     * 
     * @return The IdIcon
     */
    public int getIdIcon( )
    {
        return _nIdIcon;
    }

    /**
     * Sets the IdIcon
     * 
     * @param nIdIcon
     *            The IdIcon
     */
    public void setIdIcon( int nIdIcon )
    {
        _nIdIcon = nIdIcon;
    }

    /**
     * Returns the Label
     * 
     * @return The Label
     */
    public String getLabel( )
    {
        return _strLabel;
    }

    /**
     * Sets the Label
     * 
     * @param strLabel
     *            The Label
     */
    public void setLabel( String strLabel )
    {
        _strLabel = strLabel;
    }

    /**
     * Returns the IdUser
     * 
     * @return The IdUser
     */
    public String getIdUser( )
    {
        return _strIdUser;
    }

    /**
     * Sets the IdUser
     * 
     * @param strIdUser
     *            The IdUser
     */
    public void setIdUser( String strIdUser )
    {
        _strIdUser = strIdUser;
    }

    /**
     * Returns the order
     * 
     * @return the order
     */
    public int getOrder( )
    {
        return _nOrder;
    }

    /**
     * Sets the order
     * 
     * @param order
     *            the order to set
     */
    public void setOrder( int order )
    {
        this._nOrder = order;
    }
}
