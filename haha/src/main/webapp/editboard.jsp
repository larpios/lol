<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="brddb" class="univ.BoardDBCP" />
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<script type="text/javascript" src="boardform.js" ></script>
<style>
	table {
		margin-left: auto;
		margin-right: auto;
		width: 60%;
		table-layout: fixed;
	}
	textarea {
		width: 100%;
		height: 200px;
		resize: none;
	}
	input {
		width: 90%;
	
	}
	input[type=button], input[type=reset]{
		border-radius: 20%;
		background-color: rgb(80, 175, 80);
		color: white;
		margin-left: auto;
		margin-right: auto;
		width: 60%;
		height: 50px;
	}
	
</style>
</head>
<body>
	<c:set var="id" value="${param.id }" />
	<c:set var="name" value="" />
	<c:set var="title" value="" />
	<c:set var="email" value="" />
	<c:set var="content" value="" />
	<c:set var="headline" value="���" />
	<c:if test="${not empty id }">
		<c:set var="brd" value="${brddb.getBoard(id)}" />
		<c:set var="name" value="${brd.getName() }" />
		<c:set var="title" value="${brd.getTitle() }" />
		<c:set var="email" value="${brd.getEmail() }" />
		<c:set var="content" value="${brd.getContent() }" />
		<c:set var="headline" value="���� ����" />
	</c:if>
	<h2>�Խ��� ${headline } ���α׷�</h2>
	<form name="boardform" method="post" action="processboard.jsp">
		<input type="hidden" name="menu" value="insert">
		<input type="hidden" name="id" value="${id }">
		<table>
			<tr >
				<td colspan="2">�� �� : <input type="text" name="name" value="${name}"></td>
				<td colspan="2">���ڸ��� : <input type="text" name="email" value="${email}"></td>
			</tr>
			<tr>
				<td colspan="4">�� �� : <input type="text" name="title" value="${title}"></td>
			</tr>
			<tr>
				<td colspan="4"><textarea name="content">${content}</textarea></td>
			</tr>
			<tr>
				<td colspan="2">��й�ȣ : <input type="password" name="passwd"></td>
				<td class="warn" colspan="2">���� �Խ� ������ ���� �� �����϶�� �̹� ����� ��й�ȣ�� �ʿ��մϴ�</td>
			</tr>
			<tr>
				<td colspan="4"><hr></td>
			</tr>
			<tr>
				<c:choose>
					<c:when test="${empty id }">
						<td colspan="2"><input type="button" value="���" onclick="insertcheck()"></td>
					</c:when>
					<c:otherwise>
						<td><input type="button" value="�����Ϸ�" onclick="updatecheck()"></td>
						<td><input type="button" value="����" onclick="deletecheck()"></td>
					</c:otherwise>
				</c:choose>
				<td><input type="button" value="��Ϻ���"
					onclick="location.href='listboard.jsp'"></td>
				<td><input type="reset" value="���"></td>
			</tr>
		</table>
	</form>

</body>
</html>