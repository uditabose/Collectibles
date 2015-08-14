import grails.plugins.metadata.GrailsPlugin
import org.grails.gsp.GroovyPage
import org.grails.web.taglib.*
import org.grails.taglib.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_Drivy_layoutsmain_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/layouts/main.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
createTagBody(1, {->
printHtmlPart(1)
invokeTag('captureMeta','sitemesh',4,['gsp_sm_xmlClosingForEmptyTag':(""),'http-equiv':("Content-Type"),'content':("text/html; charset=UTF-8")],-1)
printHtmlPart(1)
invokeTag('captureMeta','sitemesh',5,['gsp_sm_xmlClosingForEmptyTag':(""),'http-equiv':("X-UA-Compatible"),'content':("IE=edge")],-1)
printHtmlPart(1)
createTagBody(2, {->
createTagBody(3, {->
invokeTag('layoutTitle','g',6,['default':("Grails")],-1)
})
invokeTag('captureTitle','sitemesh',6,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',6,[:],2)
printHtmlPart(1)
invokeTag('captureMeta','sitemesh',7,['gsp_sm_xmlClosingForEmptyTag':(""),'name':("viewport"),'content':("width=device-width, initial-scale=1")],-1)
printHtmlPart(1)
invokeTag('stylesheet','asset',8,['src':("application.css")],-1)
printHtmlPart(1)
invokeTag('javascript','asset',9,['src':("jquery.js")],-1)
printHtmlPart(1)
invokeTag('javascript','asset',10,['src':("bootstrap.js")],-1)
printHtmlPart(1)
invokeTag('javascript','asset',11,['src':("bootstrap.css")],-1)
printHtmlPart(1)
invokeTag('javascript','asset',12,['src':("bootstrap-theme.css")],-1)
printHtmlPart(2)
invokeTag('layoutHead','g',14,[:],-1)
printHtmlPart(3)
})
invokeTag('captureHead','sitemesh',15,[:],1)
printHtmlPart(3)
createTagBody(1, {->
printHtmlPart(4)
invokeTag('image','asset',17,['src':("grails_logo.png"),'alt':("Grails")],-1)
printHtmlPart(5)
invokeTag('layoutBody','g',18,[:],-1)
printHtmlPart(6)
invokeTag('message','g',20,['code':("spinner.alt"),'default':("Loading&hellip;")],-1)
printHtmlPart(7)
})
invokeTag('captureBody','sitemesh',21,[:],1)
printHtmlPart(8)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1439568329000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'none'
public static final String TAGLIB_CODEC = 'none'
}
