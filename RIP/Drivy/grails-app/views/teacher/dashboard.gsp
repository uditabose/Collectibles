
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'teacher.label', default: 'Teacher Dashboard')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="container-fluid" id="teacherDetail">
            <div class="well well-lg">
                <b style="font-size:16px">${teacher.teacherName}</b> 
            </div>
            <g:if test="${teacher.weeklySlots != null}">
                <g:each var="slot" in="${teacher.weeklySlots}">
                    <div class="row">
                        <div class="col-sm-12 col-xs-12">
                            <span>${slot.slotDate} - ${slot.timeFrom} - ${slot.timeTo}</span>
                        </div>
                    </div> 
                </g:each>
            </g:if>

        </div>


    </body>
</html>
