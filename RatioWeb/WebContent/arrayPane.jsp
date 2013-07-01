<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<p>Name <c:out value="${ratioBean.variableList.value}" /></p>
<table>
	<c:forEach var="row"
		items="${ratioBean.selectedValue.array.array}">
		<tr>
			<c:forEach var="cell" items="${row}">
				<td class="value"><c:out value="${cell}" /></td>
			</c:forEach>
		</tr>
	</c:forEach>
</table>
