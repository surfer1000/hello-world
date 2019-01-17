<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="css/shopping.css">
<script type="text/javascript" src="script/board.js"></script>
</head>
<body>
	<div id="wrap" align="center">
		<h1>게시글 상세보기</h1>
		<table>
			<tr>
				<th>작성자</th>
				<td>${board.name}</td>
				<th>이메일</th>
				<td>${board.email}</td>
			</tr>
			<tr>
				<th>작성일</th>
				<td><fmt:formatDate value="${board.writedate}" /></td>
				<th>조회수</th>
				<td>${board.readcount }</td>
			</tr>
			<tr>
				<th>제목</th>
				<td colspan="3">${board.title }</td>
			</tr>
			<tr>
				<th>내용</th>
				<td colspan="3"><pre>${board.content }</pre></td>
			</tr>
		</table>
		<form action="board.do?command=reply_write&pageNum=${pageNum}&num=${board.num}">
		<table>
		<tr><td colspan=3>댓글 수: </td></tr>
		<tr>
		<td align="center">name:<input class="box" type="text" name="name" size=10></td>
		<td rowspan=2 align="center"><textarea name="content" rows="3" cols="70"></textarea></td>
		<td rowspan=2 align="center"><input type="submit" class="box3" value="등록"></td>
		</tr>
		<tr>
		<td align="center">pass:<input type="password" class="box" name="pass" size="10"></td>
		</tr>
		</table>
		</form>
		<br> <br><input type="button" value="게시글 수정"
			onclick="open_win('BoardServlet?command=board_check_pass_form&num=${board.num}&pageNum=${pageNum}', 'update')">
		<input type="button" value="게시글 삭제"
			onclick="open_win('BoardServlet?command=board_check_pass_form&num=${board.num}&pageNum=${pageNum}', 'delete')">
		<input type="button" value="게시글 리스트"
			onclick="location.href='BoardServlet?command=board_list&pageNum=${pageNum}'"> 
		<input type="button" value="게시글 등록"
			onclick="location.href='BoardServlet?command=board_write_form'">
	</div>
</body>
</html>