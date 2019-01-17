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
</head>
<body>
	<div id="wrap" align="center">
		<h1>게시글 리스트</h1>
		<table class="list">
			<tr>
				<td colspan="5" style="border: white; text-align: right"><a
					href="board.do?command=board_write_form">게시글 등록</a></td>
			</tr>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>작성일</th>
				<th>조회</th>
			</tr>
			<c:forEach var="board" items="${boardList }">
				<tr class="record">
					<td>${board.num }</td>
					<td><a href="board.do?command=board_view&num=${board.num}&pageNum=${pageM.currentPage}">
							${board.title } </a></td>
					<td>${board.name}</td>
					<td><fmt:formatDate value="${board.writedate }" /></td>
					<td>${board.readcount}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div id="page" align="center">
		<c:if test="${pageM.prev}">
		<button onclick="location.href='board.do?command=board_list&pageNum=${pageM.startPage-pageM.pageSize}'">이전</button>
		</c:if>
		<c:forEach var="page" begin="${pageM.startPage}" end="${pageM.endPage}">
			<a href="board.do?command=board_list&pageNum=${page}">${page}</a>
		</c:forEach>
		<c:if test="${pageM.next}">
		<button onclick="location.href='board.do?command=board_list&pageNum=${pageM.startPage+pageM.pageSize}'">다음</button>
		</c:if>
	</div>
</body>
</html>