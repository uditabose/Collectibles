

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="layout" content="main" />
        <title>Product detail</title>
    </head>
    <body>
        <h2> Search Product Details</h2>
        <div style="width: 100%; float: left">
            <g:form name="searchForm" url="[controller:'product',action:'detail']" >
                Part No : <g:textField name="partNo" value="" size="50"/>
                <g:actionSubmit value="detail" />
            </g:form>
        </div>
        <div style="width: 100%; float: left; border: 2px solid green">
            <g:if test="${product != null}">
            <h1 id="partNumber">${product.primaryPartNumber}</h1><br
            <h4 id="manufacturer">${product.manufacturer}</h4></br>
            <div style="width: 90%; float: left; padding: 10px; margin: 10px">
                Alternate part no:
                <g:each var="det" in="${product.prodDetails}">
                    <p>${det.altPartNumber}</p>
                </g:each>               
            </div>
            <div  style="width: 90%; float: left; padding: 10px; margin: 10px">
                Description:
                <g:findAll in="${product.prodDetails}" expr="it.detType == 'Description'">
                     <p>${it.detailInfo}</p>
                </g:findAll>
            </div>
            <div  style="width: 90%; float: left; padding: 10px; margin: 10px">
                Notes:
                <g:findAll in="${product.prodDetails}" expr="it.detType == 'Notes'">
                     <p>${it.detailInfo}</p>
                </g:findAll>
            </div>
            <div  style="width: 100%; float: left; padding: 10px; margin: 10px">
                Images:
                <g:findAll in="${product.prodDetails}" expr="it.detType == 'Image'">
                    <img src="${it.detailInfo}" width="50" height="70"/>
                </g:findAll>
            </div>
            <div  style="width: 100%; float: left; padding: 10px; margin: 10px">
                Product detail:
                <g:findAll in="${product.prodDetails}" expr="it.detType == 'PDF'">
                    <img src="http://www.guidingtech.com/assets/postimages/2014/02/logo-adobe-pdf.jpg" width="50" height="70"/>
                </g:findAll>
            </div>
            </g:if>
        </div>
    </body>
</html>
