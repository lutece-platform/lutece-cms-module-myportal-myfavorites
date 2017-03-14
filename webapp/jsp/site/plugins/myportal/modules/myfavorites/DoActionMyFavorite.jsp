
<jsp:useBean id="myPortalMyFavoritesApp" scope="request" class="fr.paris.lutece.plugins.myportal.modules.myfavorites.web.MyFavoritesXPage" />
<%
response.sendRedirect(myPortalMyFavoritesApp.doCreateMyFavorites( request ));
%>

