<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Operatori</title>
<link rel="stylesheet" href="css/style.css" type="text/css" />
</head>
<body>
<!-- $Id: operators.jsp,v 1.2 2007/01/09 22:11:27 marco Exp $ -->
<f:view>
	<f:loadBundle basename="org.mmarini.ratio.web.Messages" var="messages" />
	<h:form>
		<h:panelGrid columns="4" columnClasses="help">
			<h:column>
				<h:commandButton action="gotoCalculator" value="#{messages.gotoCalcLabel}" />
			</h:column>
			<h:column>
				<h:commandButton action="gotoSyntax" value="#{messages.syntaxLabel}" />
			</h:column>
		</h:panelGrid>
	</h:form>
</f:view>
<hr />
<c:import url="${messages.operatorsPage}" />
</body>
</html>
