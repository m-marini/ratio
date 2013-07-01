<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<h:panelGrid columns="2">
	<h:column>
		<td valign="top"><h:outputLabel value="#{messages.commandLabel}" /> <h:inputText
			size="60" maxlength="200" binding="#{ratioBean.command}"
			onkeypress="return submitDefaultForm(event);" />
	</h:column>
	<h:column>
		<h:commandButton id="run" value="#{messages.executeButton}"
			actionListener="#{ratioBean.executeCommand}" />
	</h:column>
</h:panelGrid>
