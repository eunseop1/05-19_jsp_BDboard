<%@page import="kr.human.board.service.BoardServiceImpl"%>
<%@page import="kr.human.board.service.BoardService"%>
<%@page import="kr.human.board.vo.BoardVO"%>
<%@page import="kr.human.board.vo.PagingVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include.jsp" %>
<%
	// 서비스 클래스를 호출하여 결과를 받아 request영역에 넣는다.
	PagingVO<BoardVO> pagingVO = BoardServiceImpl.getInstance().selectList(currentPage, pageSize, blockSize);
	request.setAttribute("pv", pagingVO);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>목록보기</title>
<link href="${pageContext.request.servletContext.contextPath }/webjars/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.servletContext.contextPath }/webjars/jquery/3.6.0/jquery.min.js" ></script>
<script type="text/javascript" src="${pageContext.request.servletContext.contextPath }/webjars/bootstrap/5.1.3/js/bootstrap.min.js" ></script>
<script type="text/javascript">
	$(function(){
		
	});
</script>
<style type="text/css">
	table { width: 900px; margin: auto; border: none;}
	th{ padding: 5px; text-align: center; background-color: silver; border: 1px solid gray;}
	td{ padding: 5px; text-align: center;  border: 1px solid gray;}
	.title{ padding: 5px; text-align: center; border: none; font-size: 18pt;}
	.pageinfo{ padding: 5px; text-align: right; border: none; }
</style>
</head>
<body>
	<table>
		<tr>
			<td class="title" colspan="5">자유게시판</td>
		</tr>
		<tr>
			<td class="pageinfo" colspan="5">${pv.pageInfo }</td>
		</tr>
		<tr>
			<th>No</th>
			<th width="60%">제목</th>
			<th>작성자</th>
			<th>작성일</th>
			<th>조회수</th>
		</tr>

		<c:if test="${empty pv.list }"> <!-- pv가 비어있다면? -->
			<tr>
				<td align="center" colspan="5">등록된 글이 없습니다.</td>
			</tr>		
		</c:if>
		<c:if test="${not empty pv.list }"> <!-- pv가 비어있지 않다면? tr의 내용들이 반복된다 -->
			<%-- 현재 페이지의 글번호 시작값을 계산한다. --%>
			
			<%--
			전체 페이지가 57개일때
			1페이지일 경우, 57~48까지 나오고
			2페이지에는 47~38까지
			3페이지에서는 37~28
			27~18
			17~18
			8~1
			이렇게 페이지가 지날때마다 보여줘야 한다
			현재 아는건 페이지 번호와 전체 개수를 안다
			전체개수 - (현재페이지 - 1) * 페이지 사이즈
			1페이지 : 57 - (1-1) * 10 = 57
			2페이지: 57 - (2-1) * 10 = 47
			따라서 for문 돌기전에 글번호 시작값을 계산해야 한다
			변수는 c:set으로 만들어 보자
			 --%>
			<c:set var="no" value="${pv.totalCount - (pv.currentPage-1) * pv.pageSize }"></c:set>
			<c:forEach var="vo" items="${pv.list }" varStatus="vs">
				<tr>
					<td>${no - vs.index  }</td> 
					<%--
					반복되는 횟수는 vo.count 몇번째 인지를 구하는게 vs.index이다. 
					따라서 vs의 인덱스가 점차 증가하면서 해당 페이지의 최대값인 no를 빼준다 
					--%>
					<td style="text-align: left;">
						<c:url var="url" value="view.jsp">
							<c:param name="p" value="${pv.currentPage }"/>						
							<c:param name="s" value="${pv.pageSize }"/>						
							<c:param name="b" value="${pv.blockSize }"/>						
							<c:param name="idx" value="${vo.idx }"/>						
							<c:param name="click" value="${true }"/>						
						</c:url>
						<a href="${url }"><c:out value="${vo.subject }" /></a> <!-- a에 링크를 붙이고  -->
					</td>
					<td>
						<c:out value="${vo.name }" />
						<%-- c:out은 태그를 무시(사람이 입력하는 것은 모두 c:out으로 찍자) --%>
					</td>
					<td>
						<fmt:formatDate value="${vo.regDate }" pattern="yy-MM-dd"/>
					</td>
					<td>${vo.clickCount }</td>
					<!-- 조회수는 스스로 늘어나는 것이기에 -->
				</tr>			
			</c:forEach>
			<tr>
				<td class="pageinfo" style="text-align: center;" colspan="5">${pv.pageList }</td>
			</tr>
			<tr>
				<td class="pageinfo" colspan="5">
					<c:url var="url" value="insertForm.jsp">
						<c:param name="p" value="${pv.currentPage }"/>						
						<c:param name="s" value="${pv.pageSize }"/>						
						<c:param name="b" value="${pv.blockSize }"/>						
					</c:url>
					<button onclick="location.href='${url}'">글쓰기</button>
				</td>
			</tr>
		</c:if>
	</table>
</body>
</html>