<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'student.label', default: 'Student')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-12 col-xs-12">&nbsp;</div>
            </div>

            <g:if test="${student != null}">
                <div class="well well-lg">
                    <b style="font-size:16px">${student.studentName}</b> 
                </div>
                
                <g:if test="${student.weeklyLesson != null}">
                    <table>
                        <tr>
                            <th>Lesson Title</th>
                            <th>Teacher</th>
                        </tr>
                    <g:each var="lesson" in="${student.weeklyLesson}">
                        <tr>
                            <td>${lesson.lessonTitle}</td>
                            <td>${lesson.lessonTeacher?.teacherName}</td>
                        </tr>
                    </g:each>
                    </table>
                </g:if>
            </g:if>

            <g:if test="${teachers != null}">
                <g:each var="teacher" in="${teachers}">
                    <div class="well well-lg">
                        <b style="font-size:14px">${teacher.teacherName}</b> 
                    </div>
                    <div>
                        <g:if test="${teacher.weeklySlots != null}">
                            <g:form name="update" url="[controller:'student',action:'update']" >

                                <input type="hidden" name="teacherId" value="${teacher.teacherId}">
                                <input type="hidden" name="studentId" value="${student.studentId}">
                                <table>
                                    <g:each var="slot" in="${teacher.weeklySlots}" status="counter">
                                        <tr>
                                            <td>
                                                <span class="button-checkbox">
                                                    <button type="button" class="btn btn-default">${slot.slotDate} : ${slot.timeFrom}-${slot.timeTo}</button>
                                                    <input type="checkbox" name="weeklySlots" value="${counter}"/>
                                                </span>
                                            </td>
                                        </tr>

                                    </g:each>
                                </table>
                                <tr><td>
                                        <g:actionSubmit action="update" value="Save Lessons" />
                                    </td></tr>
                                </g:form>

                            </g:if>
                    </div>
                </g:each>
            </g:if>
        </div>

    </body>
</html>
