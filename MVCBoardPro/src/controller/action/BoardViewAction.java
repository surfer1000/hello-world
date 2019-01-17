package controller.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDao;
import dto.Board;

public class BoardViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String url="board/board_view.jsp";
		String pageNum=request.getParameter("pageNum");
		int board_num=Integer.parseInt(request.getParameter("num"));
		BoardDao bDao=BoardDao.getInstance();
		Board board=bDao.selectArticle(board_num);
		int updateCount = bDao.updateReadCount(board_num);
		
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("board", board);
		request.getRequestDispatcher(url).forward(request, response);

	}

}
