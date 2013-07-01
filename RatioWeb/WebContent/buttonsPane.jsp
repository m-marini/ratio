<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<h:panelGrid styleClass="buttons">
	<h:column>
		<h:commandButton styleClass="button"
			value="#{messages.createNewArray}" action="createNewArray"
			binding="#{ratioBean.newArrayCommand}" />
	</h:column>
	<h:column>
		<h:commandButton styleClass="button" value="#{messages.changeValue}"
			action="#{ratioBean.changeValue}"
			binding="#{ratioBean.changeValueCommand}" />
	</h:column>
	<h:column>
		<h:commandButton styleClass="button" value="#{messages.deleteValue}"
			action="#{ratioBean.deleteValue}"
			binding="#{ratioBean.deleteValueCommand}" />
	</h:column>
	<h:column>
		<h:commandButton styleClass="button" value="#{messages.deleteAll}"
			action="#{ratioBean.deleteAllValues}"
			binding="#{ratioBean.deleteAllCommand}" />
	</h:column>
</h:panelGrid>
