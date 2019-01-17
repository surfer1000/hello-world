package controller.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDao;
import dto.Board;

public class BoardReplyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String pageNum = request.getParameter("pageNum");
	 	Board article = new Board();  
	 	System.out.println("aa:"+request.getParameter("num"));
	 	article.setNum(Integer.parseInt(request.getParameter("num")));
	 	article.setName(request.getParameter("name"));
	 	article.setPass(request.getParameter("pass"));
	 	article.setSubject(request.getParameter("subject"));
	 	article.setContent(request.getParameter("content"));
	 	article.setRe_ref(Integer.parseInt(request.getParameter("re_ref")));
	 	article.setRe_lev(Integer.parseInt(request.getParameter("re_lev")));
	 	article.setRe_seq(Integer.parseInt(request.getParameter("re_seq")));	   		
	 	BoardDao bDao=BoardDao.getInstance();
	 	bDao.insertReplyArticle(article);
	 	response.sendRedirect("boardList.bo?pageNum="+pageNum);
	 	
	}

}
