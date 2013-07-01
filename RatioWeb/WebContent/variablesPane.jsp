<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<h:panelGrid columns="3" styleClass="mainTable" rowClasses="varList"
	columnClasses="varList,buttons,value">
	<h:column>
		<h:selectOneListbox onchange="submit()"
			valueChangeListener="#{ratioBean.handleVariableSelected}"
			binding="#{ratioBean.variableList}" />
	</h:column>
	<h:column>
		<c:import url="buttonsPane.jsp" />
	</h:column>
	<h:column>
		<c:choose>
			<c:when test="${empty ratioBean.selectedValue}">
				<f:verbatim>&nbsp;</f:verbatim>
			</c:when>
			<c:when
				test="${ratioBean.selectedValue.class.name == 'org.mmarini.ratio.processor.ArrayRationalValue'}">
				<f:verbatim>
					<c:import url="arrayPane.jsp" />
				</f:verbatim>
			</c:when>
			<c:when
				test="${ratioBean.selectedValue.class.name == 'org.mmarini.ratio.processor.ScalarRationalValue'}">
				<f:verbatim>
					<c:import url="scalarPane.jsp" />
				</f:verbatim>
			</c:when>
			<c:otherwise>
				<c:out value="?${ratioBean.selectedValue.class.name}?" />
			</c:otherwise>
		</c:choose>
	</h:column>
</h:panelGrid>
