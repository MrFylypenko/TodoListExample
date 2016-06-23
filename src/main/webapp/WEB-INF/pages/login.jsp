<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- <%@ page session="false"%> --%>
<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div id="login-box">
	<h3>
		Авторизация
	</h3>

	<div>
	<c:if test="${not empty error}">
		<div class="error">
			Неправильный логин или пароль
		</div>
	</c:if>

	<form name='loginForm'
		action="<c:url value='/j_spring_security_check' />" method='POST'>
		<table>
			<tr>
				<td>Логин:</td>
				<td><input type='text' name='j_username'></td>
			</tr>
			<tr>
				<td>Пароль:</td>
				<td><input type='password' name='j_password' /></td>
			</tr>
			<tr>
				<td colspan='2'><input name="submit" type="submit"
					value="Войти" /></td>
			</tr>
		</table>
	</form>
	</div>
</div>
