<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<f:view>
	<f:loadBundle basename="org.mmarini.ratio.web.Messages" var="messages" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><h:outputText value="#{messages.newArrayTitle}" /></title>
	<link rel="stylesheet" href="css/style.css" type="text/css" />
</head>
<body>
<!-- $Id: arraySize.jsp,v 1.2 2007/01/09 22:11:27 marco Exp $ -->
<h:form id="arraySize">
	<table>
		<tr>
			<td class="title" colspan="2">
			<h1><h:outputText value="#{messages.newArrayTitle}" /></h1>
			</td>
		</tr>
		<tr>
			<td><h:outputLabel value="#{messages.nameLabel}" /></td>
			<td><h:inputText binding="#{newArrayBean.variableName}" id="varName"
				size="32" maxlength="32" required="true"
				validator="#{newArrayBean.validateName}" /> <h:message for="varName" /></td>
		</tr>
		<tr>
			<td><h:outputLabel value="#{messages.rowsCountLabel}" /></td>
			<td><h:inputText id="rowsCount" binding="#{newArrayBean.rowsCount}"
				size="2" maxlength="2" required="true">
				<f:validateLongRange minimum="1" maximum="30" />
				<f:convertNumber integerOnly="true" />
			</h:inputText> <h:message for="rowsCount" /></td>
		</tr>
		<tr>
			<td><h:outputLabel value="#{messages.colsCountLabel}" /></td>
			<td><h:inputText id="colsCount" binding="#{newArrayBean.colsCount}"
				size="2" maxlength="2" required="true">
				<f:validateLongRange minimum="1" maximum="30" />
				<f:convertNumber integerOnly="true" />
			</h:inputText> <h:message for="colsCount" /></td>
		</tr>
		<tr>
			<td><h:commandButton value="#{messages.okButton}"
				action="#{newArrayBean.execute}" /></td>
			<td><h:commandButton value="#{messages.cancelButton}" action="cancel" /></td>
		</tr>
	</table>
</h:form>
</f:view>
</body>
</html>
