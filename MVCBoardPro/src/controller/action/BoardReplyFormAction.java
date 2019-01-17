package controller.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDao;
import dto.Board;

public class BoardReplyFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int num=Integer.parseInt(request.getParameter("num"));
		String pageNum=request.getParameter("pageNum");
		Board board=BoardDao.getInstance().selectArticle(num);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("board", board);
		request.getRequestDispatcher("board/board_reply.jsp")
		.forward(request, response);
	}

}
