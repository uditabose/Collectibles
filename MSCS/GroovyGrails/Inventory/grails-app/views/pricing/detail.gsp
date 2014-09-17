<!--
  To change this license header, choose License Headers in Project Properties.
  To change this template file, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="layout" content="main" />
        <title>Pricing detail</title>
    </head>
    <body>
        <h2> Search Quote Details</h2>
        <div style="width: 100%; float: left">
            <g:form name="searchForm" url="[controller:'pricing',action:'detail']" >
                Part No : <g:textField name="partNo" value="" size="50"/>
                <g:actionSubmit value="detail" />
            </g:form>
        </div>
        <div style="width: 100%; float: left; border: 2px solid green">
            <g:if test="${pricingList != null}">
                <table>
                    <tr>
                        <th>Part Number</th>
                        <th>Pricing Type</th> 
                        <th>Price per unit</th>
                        <th>No of Items</th>
                        <th>Date of quote</th>
                        <th>Vendor</th>
                    </tr>
                <g:each var="price" in="${pricingList}">
                    <tr>
                        <td>${price.partNumber}</td>
                        <td>${price.pricingType}</td>
                        <td>${price.pricingPerUnit}</td>
                        <td>${price.noOfPieces}</td>
                        <td>${price.pricingDate}</td>
                        <td>${price.vendor}</td>
                    </tr>
                </g:each>
                </table>
            </g:if>
        </div>
    </body>
</html>
