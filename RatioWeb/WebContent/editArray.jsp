<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.mmarini.org/jsf/html" prefix="mm"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<f:view>
	<f:loadBundle basename="org.mmarini.ratio.web.Messages" var="messages" />
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><h:outputText value="#{messages.editTitle}" /></title>
	<link rel="stylesheet" href="css/style.css" type="text/css" />
	</head>
	<body>
	<!-- $Id: editArray.jsp,v 1.2 2007/01/09 22:11:27 marco Exp $ -->
	<h:form id="editArray">
		<table>
			<tr>
				<td class="title">
				<h1><h:outputText value="#{messages.editTitle}" /></h1>
				</td>
			</tr>
			<tr>
				<td><mm:grid colIndexVar="col" rowIndexVar="row"
					styleClass="edit" binding="#{editArrayBean.grid}">
					<h:inputText id="cell"
						value="#{editArrayBean.grid.value[row][col]}" size="10"
						required="true" validator="#{editArrayBean.validateCell}"></h:inputText>
					<f:verbatim>
						<br />
					</f:verbatim>
					<h:message for="cell" />
				</mm:grid></td>
			</tr>
			<tr>
				<td><h:commandButton value="#{messages.okButton}"
					action="#{editArrayBean.createArray}" /></td>
			</tr>
		</table>
	</h:form>
	</body>
</f:view>
</html>
