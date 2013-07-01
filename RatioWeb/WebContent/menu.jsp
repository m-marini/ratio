<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<h:form>
	<h:panelGrid columns="4" columnClasses="help">
		<h:column>
			<h:commandButton action="operatorsHelp"
				value="#{messages.operatorsLabel}" />
		</h:column>
		<h:column>
			<h:commandButton action="syntaxHelp" value="#{messages.syntaxLabel}" />
		</h:column>
		<h:column>
			<h:commandButton action="#{localeBean.selectEnglish}"
				binding="#{localeBean.selectEnCommand}"
				value="#{messages.selectEnLabel}" />
		</h:column>
		<h:column>
			<h:commandButton action="#{localeBean.selectItalian}"
				binding="#{localeBean.selectItCommand}"
				value="#{messages.selectItLabel}" />
		</h:column>
	</h:panelGrid>
</h:form>
