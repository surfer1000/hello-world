package controller.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDao;
import dto.Board;
import ect.PageMaker;



public class BoardListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int pageNum=1;
		PageMaker pageM=null;
		String strPage=request.getParameter("pageNum");
		if(strPage!=null){
			pageNum=Integer.parseInt(strPage);
		}
		String url = "/board/board_list.jsp?pageNum="+pageNum;
		BoardDao dao = BoardDao.getInstance();
		int totalCount=dao.selectListCount();
		pageM=new PageMaker(pageNum,totalCount);
		List<Board> boardList = dao.selectArticleList(pageM.getStart(),pageM.getPageSize());
		request.setAttribute("pageM", pageM);
		request.setAttribute("boardList", boardList);
		request.getRequestDispatcher(url)
		.forward(request, response);
	}

}
