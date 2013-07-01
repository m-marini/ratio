<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://www.mmarini.org/jsf/html" prefix="mm"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%response.setHeader("Cache-Control", "no-Cache");
			response.setHeader("Pragma", "No-cache");
			response.setDateHeader("Expires", 0);

		%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<f:view>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Test</title>
</head>
<body>
<!-- $Id: gridTest.jsp,v 1.2 2007/01/09 22:11:27 marco Exp $ -->
<h:form>
	<mm:grid styleClass="#{testBean.data[0][0]}" id="grande"
		rowIndexVar="row" colIndexVar="col" value="#{testBean.data}">
		<h:outputText value="#{row}" />
		<f:verbatim>-</f:verbatim>
		<h:outputText value="#{col}" />
		<h:inputText value="#{testBean.data[row][col]}" />
	</mm:grid>
	<h:dataTable var="row" value="#{testBean.data}" styleClass="styleClass" rowClasses="R1,R2" columnClasses="C1,C2" >
		<h:column>
			<h:outputText value="C1" />
		</h:column>
		<h:column>
			<h:outputText value="C2" />
		</h:column>
		<h:column>
			<h:outputText value="C3" />
		</h:column>
	</h:dataTable>
	<h:commandButton value="change" />
</h:form>
</f:view>
</body>
</html>
