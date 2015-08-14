
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'teacher.label', default: 'Teacher Dashboard')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="row">
            <div class="col-sm-12 col-xs-12">&nbsp;</div>
        </div>


        <g:if test="${teacher != null}">
            <div class="container-fluid" id="teacherDetail">
                <div class="well well-lg">
                    <b style="font-size:16px">${teacher.teacherName}</b> 
                </div>
            </div>

            <div class="row">
                <div class="col-sm-12 col-xs-12">&nbsp;</div>
            </div>

            <div class="row">
                <div class="col-sm-6 col-xs-6">
                    <div class="panel panel-success">
                        <div class="panel-heading">Registered Slots</div>
                        <div class="panel-body">
                            
                            <g:if test="${teacher.weeklySlots != null}">
                                <table>
                                <g:each var="slot" in="${teacher.weeklySlots}">
                                    <tr>
                                    <td>
                                    <span class="button-checkbox">
                                        <button type="button" class="btn btn-default">${slot.slotDate} : ${slot.timeFrom}-${slot.timeTo}</button>
                                    </span>
                                    </td>
                                    </tr>
                                    
                                </g:each>
                                </table>
                            </g:if>
                            
                        </div>

                    </div>
                </div>
            </div>

            <div class="col-sm-6 col-xs-6">
                <div class="panel panel-info">
                    <div class="panel-heading">Free Slots</div>
                    <div class="panel-body">
                        <g:form name="update" url="[controller:'teacher',action:'update']" >
                            <input type="hidden" name="teacherId" value="${teacher.teacherId}" >
                        <g:if test="${freeSlots != null}">
                            <table>
                            <g:each var="slot" in="${freeSlots}">
                                <tr><td>
                                <span class="button-checkbox">
                                    <button type="button" class="btn btn-default">${slot.slotDate} : ${slot.timeFrom}-${slot.timeTo}</button>
                                    <input type="checkbox"  name="freeSlots" value="${slot.slotDate}#${slot.slotNumber}"/>
                                </span>
                                </td></tr>
                            </g:each>
                            <tr><td>
                            <g:actionSubmit action="update" value="Save Updates" />
                            </td></tr>
                        </g:if>
                            </table>
                        </g:form>
                    </div>
                </div>
            </div>

        </g:if>

        <div class="row">
            <div class="col-sm-12 col-xs-12">&nbsp;</div>
        </div>
    </body>
</html>
