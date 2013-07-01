<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%response.setHeader("Cache-Control", "no-Cache");
			response.setHeader("Pragma", "No-cache");
			response.setDateHeader("Expires", 0);%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<f:view>
	<f:loadBundle basename="org.mmarini.ratio.web.Messages" var="messages" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><h:outputFormat value="#{messages.mainTitle}">
		<f:param value="#{messages.prodName}" />
		<f:param value="#{messages.prodVersion}" />
		<f:param value="#{messages.prodAuthor}" />
	</h:outputFormat></title>
	<link rel="stylesheet" href="css/style.css" type="text/css" />
	<script language="text/javascript"> <!--
	function submitDefaultForm(evt) {
		var charCode = (evt.charCode) ? evt.charCode : ((evt.which) ? evt.which	: evt.keyCode);
		if (charCode == 13 || charCode == 3) {
			document.forms['mainForm']['mainForm:run'].click();
			return false;
		}
		return true;
	}
-->
</script>
</head>

<body>
<!-- $Id: main.jsp,v 1.2 2007/01/09 22:11:27 marco Exp $ -->
<c:import url="menu.jsp" />
<hr />
<h1><h:outputFormat value="#{messages.mainTitle}">
	<f:param value="#{messages.prodName}" />
	<f:param value="#{messages.prodVersion}" />
	<f:param value="#{messages.prodAuthor}" />
</h:outputFormat></h1>
<h:form id="mainForm">
	<h:panelGrid styleClass="mainTable" rowClasses=",command,log,log">
		<h:column>
			<c:import url="variablesPane.jsp" />
		</h:column>
		<h:column>
			<c:import url="commandPane.jsp" />
		</h:column>
		<h:column>
			<h:dataTable binding="#{ratioBean.logList}" var="line">
				<h:column>
					<h:outputText value="#{line}" />
				</h:column>
			</h:dataTable>
		</h:column>
		<h:column>
			<h:messages />
		</h:column>
	</h:panelGrid>
</h:form>
</f:view>
</body>

</html>
