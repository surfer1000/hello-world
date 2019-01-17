package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.Board;

public class BoardDao {
	private BoardDao() {
	}

	private static BoardDao bDao = new BoardDao();

	public static BoardDao getInstance() {
		return bDao;
	}

	public int selectListCount() {

		int listCount = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			System.out.println("getConnection");
			conn = Dao.getConnection();
			pstmt = conn.prepareStatement("select count(*) from board2");
			rs = pstmt.executeQuery();

			if (rs.next()) {
				listCount = rs.getInt(1);
			}
		} catch (Exception ex) {
			System.out.println("getListCount 에러: " + ex);
		} finally {
			Dao.close(rs);
			Dao.close(pstmt);
			Dao.close(conn);
		}

		return listCount;
	}

	// 글 목록 보기.
	public ArrayList<Board> selectArticleList(int start, int limit) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String board_list_sql = "select * from board2 order by RE_REF desc,RE_SEQ asc limit ?,?";
		ArrayList<Board> articleList = new ArrayList<Board>();
		Board board = null;

		try {
			conn = Dao.getConnection();
			pstmt = conn.prepareStatement(board_list_sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				board = new Board();
				board.setNum(rs.getInt("num"));
				board.setName(rs.getString("name"));
				board.setSubject(rs.getString("subject"));
				board.setContent(rs.getString("content"));
				board.setFile(rs.getString("file"));
				board.setRe_ref(rs.getInt("re_ref"));
				board.setRe_lev(rs.getInt("re_lev"));
				board.setRe_seq(rs.getInt("re_seq"));
				board.setReadcount(rs.getInt("readcount"));
				board.setReg_date(rs.getTimestamp("reg_date"));
				articleList.add(board);
			}

		} catch (Exception ex) {
			System.out.println("getBoardList 에러 : " + ex);
		} finally {
			Dao.close(rs);
			Dao.close(pstmt);
			Dao.close(conn);
		}

		return articleList;
	}

	public Board selectArticle(int board_num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Board board = null;

		try {
			conn = Dao.getConnection();
			pstmt = conn.prepareStatement("select * from board2 where num = ?");
			pstmt.setInt(1, board_num);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				board = new Board();
				board.setNum(rs.getInt("num"));
				board.setName(rs.getString("name"));
				board.setSubject(rs.getString("subject"));
				board.setContent(rs.getString("content"));
				board.setFile(rs.getString("file"));
				board.setRe_ref(rs.getInt("re_ref"));
				board.setRe_lev(rs.getInt("re_lev"));
				board.setRe_seq(rs.getInt("re_seq"));
				board.setReadcount(rs.getInt("readcount"));
				board.setReg_date(rs.getTimestamp("reg_date"));
			}
		} catch (Exception ex) {
			System.out.println("getDetail 에러 : " + ex);
		} finally {
			Dao.close(rs);
			Dao.close(pstmt);
		}

		return board;

	}

	public int insertArticle(Board article) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int num = 0;
		String sql = "";
		int insertCount = 0;

		try {
			conn = Dao.getConnection();
			pstmt = conn.prepareStatement("select max(num) from board2");
			rs = pstmt.executeQuery();

			if (rs.next())
				num = rs.getInt(1) + 1;
			else
				num = 1;

			sql = "insert into board2(NUM,NAME,PASS,SUBJECT,CONTENT, FILE, RE_REF) values(?,?,?,?,?,?,?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, article.getName());
			pstmt.setString(3, article.getPass());
			pstmt.setString(4, article.getSubject());
			pstmt.setString(5, article.getContent());
			pstmt.setString(6, article.getFile());
			pstmt.setInt(7, num);
			insertCount = pstmt.executeUpdate();
			if(insertCount==1) {
				Dao.commit(conn);
			}else {
				Dao.rollback(conn);
			}

		} catch (Exception ex) {
			System.out.println("boardInsert 에러 : " + ex);
		} finally {
			Dao.close(rs);
			Dao.close(pstmt);
			Dao.close(conn);
		}

		return insertCount;
	}

	public int insertReplyArticle(Board article) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String board_max_sql = "select max(num) from board2";
		String sql = "";
		int num = 0;
		int insertCount = 0;
		int re_ref = article.getRe_ref();
		int re_lev = article.getRe_lev();
		int re_seq = article.getRe_seq();

		try {
			conn = Dao.getConnection();
			pstmt = conn.prepareStatement(board_max_sql);
			rs = pstmt.executeQuery();
			if (rs.next())
				num = rs.getInt(1) + 1;
			else
				num = 1;
			sql = "update board2 set RE_SEQ=RE_SEQ+1 where RE_REF=? and RE_SEQ>?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, re_ref);
			pstmt.setInt(2, re_seq);
			int updateCount = pstmt.executeUpdate();

			if (updateCount > 0) {
				Dao.commit(conn);
			}

			re_seq = re_seq + 1;
			re_lev = re_lev + 1;
			sql = "insert into board2 (NUM,NAME,PASS,SUBJECT,CONTENT,FILERE_REF,RE_LEV,RE_SEQ,) "
					+ "values(?,?,?,?,?,?,?,?,?,)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, article.getName());
			pstmt.setString(3, article.getPass());
			pstmt.setString(4, article.getSubject());
			pstmt.setString(5, article.getContent());
			pstmt.setString(6, ""); // 답장에는 파일을 업로드하지 않음.
			pstmt.setInt(7, re_ref);
			pstmt.setInt(8, re_lev);
			pstmt.setInt(9, re_seq);
			insertCount = pstmt.executeUpdate();
			if(insertCount==1) {
				Dao.commit(conn);
			}else {
				Dao.rollback(conn);
			}
		} catch (SQLException ex) {
			System.out.println("boardReply 에러 : " + ex);
		} finally {
			Dao.close(rs);
			Dao.close(pstmt);
			Dao.close(conn);
		}

		return insertCount;

	}

	public int updateArticle(Board article) {

		int updateCount = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "update board2 set SUBJECT=?,CONTENT=? where NUM=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, article.getSubject());
			pstmt.setString(2, article.getContent());
			pstmt.setInt(3, article.getNum());
			updateCount = pstmt.executeUpdate();
			if(updateCount==1) {
				Dao.commit(conn);
			}else {
				Dao.rollback(conn);
			}
		} catch (Exception ex) {
			System.out.println("boardModify 에러 : " + ex);
		} finally {
			Dao.close(pstmt);
			Dao.close(conn);
		}

		return updateCount;

	}

	// 글 삭제.
	public int deleteArticle(int board_num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String board_delete_sql = "delete from board2 where num=?";
		int deleteCount = 0;

		try {
			conn = Dao.getConnection();
			pstmt = conn.prepareStatement(board_delete_sql);
			pstmt.setInt(1, board_num);
			deleteCount = pstmt.executeUpdate();
		} catch (Exception ex) {
			System.out.println("boardDelete 에러 : " + ex);
		} finally {
			Dao.close(pstmt);
			Dao.close(conn);
			;
		}

		return deleteCount;

	}

	// 조회수 업데이트.
	public int updateReadCount(int num) {
		Connection conn=null;
		PreparedStatement pstmt = null;
		int updateCount = 0;
		String sql = "update board2 set READCOUNT =READCOUNT+1 where NUM = ?";

		try {
			conn=Dao.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			updateCount = pstmt.executeUpdate();
			if(updateCount==1) {
				Dao.commit(conn);
			}else {
				Dao.rollback(conn);
			}
		} catch (SQLException ex) {
			System.out.println("setReadCountUpdate 에러 : " + ex);
		} finally {
			Dao.close(pstmt);
			Dao.close(conn);

		}

		return updateCount;

	}
	//글쓴이인지 확인.
	public boolean isArticleBoardWriter(int num,String pass){
		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String board_sql="select * from board2 where NUM=?";
		boolean isWriter = false;

		try{
			conn=Dao.getConnection();
			pstmt=conn.prepareStatement(board_sql);
			pstmt.setInt(1, num);
			rs=pstmt.executeQuery();
			rs.next();

			if(pass.equals(rs.getString("PASS"))){
				isWriter = true;
			}
		}catch(SQLException ex){
			System.out.println("isBoardWriter 에러 : "+ex);
		}
		finally{
			Dao.close(pstmt);
			Dao.close(conn);
		}

		return isWriter;

	}

}
