<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<fmt:setLocale value="${locale}" />
<fmt:setBundle basename="local" />
<title><fmt:message key="thePage" /></title>
<script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script
	src="//ajax.aspnetcdn.com/ajax/jquery.ui/1.10.3/jquery-ui.min.js"></script>
<link rel="stylesheet"
	href="http://ajax.aspnetcdn.com/ajax/jquery.ui/1.10.3/themes/sunny/jquery-ui.css">
<style type="text/css">
input {
	width: 200px;
	text-align: left
}
</style>
<script type="text/javascript">
function Formdata(data){
	if (data.dateBegin != null && data.dateBegin.value.length == 0 )
	{
	alert('<fmt:message key="validDateBegin" />');
	return false;}
	if (data.dateFinish != null && data.dateFinish.value.length == 0)
	{
	alert('<fmt:message key="validDateFinish" />');
	return false;}
	}
	$(function() {

		$('#datep').datepicker(

		);

	});

	$(function() {

		$('#datepic').datepicker();

	});
</script>
<style>
input[type=submit], button {
	width: 28%;
	background-color: #D2B48C;
	color: #000000;
	padding: 14px 20px;
	margin: 8px 0;
	border: none;
	border-radius: 4px;
	cursor: pointer;
}

h1 {
	text-align: center;
}

#tabl {
	background-color: #8B4513;
	padding: 20px;
	margin-left: 400px;
	margin-right: 200px;
	width: 600px;
}

body {
	background-image:
		url("${pageContext.servletContext.contextPath}/resources/fon.jpg");
}
</style>
</head>
<body>

	<section>
		<div id="tabl">
			<h1>
				<fmt:message key="thePage" />
			</h1>

			<s:form
				action="${pageContext.servletContext.contextPath}/client/confirm"
				modelAttribute="orderDto" method="post">
				<fieldset>

					<label for="target"><fmt:message key="target" /> :</label> <input
						id="target" type="text" size="20" maxlength="20" name="target"
						required /> <br> <br>
					<fmt:message key="distanse" />
					: <br>
					<s:select name="distanse" path="dictanse">
						<c:forEach var="i" begin="1" end="10">
							<option value="${i*10}">${i*10}</option>
						</c:forEach>
					</s:select>
					<br> <br>
					<fmt:message key="dateBegin" />
					:<br> <input name="dateBegin" readonly
								id="datep" required />
							<s:errors name="dateBegin" /> <br> <br>
					<fmt:message key="dateFinish" />
					: <br>
					<input name="dateFinish" readonly id="datepic" required />  <s:errors
								name="dateFinish" /><s:errors name="dateFinish" />
							<br> <br>
					<fmt:message key="volume" />
					: <br>
					<s:select name="volume" path="volume">
						<c:forEach var="i" begin="1" end="50">
							<option value="${i}">${i}</option>
						</c:forEach>
					</s:select>
					<br> <br>
					<fmt:message key="mass" />
					:<br>
					<s:select name="mass" path="mass">
						<c:forEach var="i" begin="1" end="50">
							<option value="${i}">${i}</option>
						</c:forEach>
					</s:select>
					<br> <br>
					<fmt:message key="count" />
					: <br>
					<s:select name="seatCount" path="seatCount">
						<c:forEach var="i" begin="1" end="4">
							<option value="${i}">${i}</option>
						</c:forEach>
					</s:select>
					<br> <br> <input type="submit"
								value="<fmt:message key="true" />" onClick="return Formdata(this.form)"/>
				
						</fieldset>
			</s:form>
			<a href="${pageContext.servletContext.contextPath}/client/return"><button>
					<fmt:message key="backClient" />
				</button></a>

		</div>

	</section>

</body>
</html>
