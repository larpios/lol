package univ;

import java.sql.*;
import java.util.*;
import javax.naming.*;
import javax.sql.*;

public class BoardDBCP {
	Connection con = null;
	PreparedStatement pstmt = null;
	DataSource ds = null;

	public BoardDBCP() {
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/mysql");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public void connect() {
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void disconnect() {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public ArrayList<BoardEntity> getBoardList() {
		connect();
		ArrayList<BoardEntity> list = new ArrayList<>();
		String sql = "select * from board";
		try {
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				BoardEntity brd = new BoardEntity();
				brd.setId(rs.getInt("id"));
				brd.setName(rs.getString("name"));
				brd.setPasswd(rs.getString("passwd"));
				brd.setTitle(rs.getString("title"));
				brd.setEmail(rs.getString("email"));
				brd.setRegdate(rs.getTimestamp("regdate"));
				brd.setContent(rs.getString("content"));
				list.add(brd);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}

	public BoardEntity getBoard(int id) {
		connect();
		BoardEntity brd = new BoardEntity();
		String sql = "select * from board where id=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			brd.setId(id);
			brd.setName(rs.getString("name"));
			brd.setPasswd(rs.getString("passwd"));
			brd.setTitle(rs.getString("title"));
			brd.setEmail(rs.getString("email"));
			brd.setRegdate(rs.getTimestamp("regdate"));
			brd.setContent(rs.getString("content"));
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return brd;
	}

	public boolean insertDB(BoardEntity brd) {
		connect();
		boolean success = false;
		String sql = "insert into board values (0, ?, ?, ?, ?, sysdate(), ?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, brd.getName());
			pstmt.setString(2, brd.getPasswd());
			pstmt.setString(3, brd.getTitle());
			pstmt.setString(4, brd.getEmail());
			pstmt.setString(5, brd.getContent());
			int rownum = pstmt.executeUpdate();
			if (rownum == 1)
				success = true;
		} catch (SQLException e) {
			e.printStackTrace();
			return success;
		} finally {
			disconnect();
		}
		return success;
	}

	public boolean updateDB(BoardEntity brd) {
		connect();
		boolean success = false;
		String sql = "update board set name=?, title=?, email=?, content=? where id=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, brd.getName());
			pstmt.setString(2, brd.getTitle());
			pstmt.setString(3, brd.getEmail());
			pstmt.setString(4, brd.getContent());
			pstmt.setInt(5, brd.getId());
			int rownum = pstmt.executeUpdate();
			if (rownum >= 1)
				success = true;
		} catch (SQLException e) {
			e.printStackTrace();
			return success;
		} finally {
			disconnect();
		}
		return success;
	}

	public boolean deleteDB(int id) {
		connect();
		boolean success = false;
		String sql = "delete from board where id=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			int rownum = pstmt.executeUpdate();
			if (rownum >= 1)
				success = true;
		} catch (SQLException e) {
			e.printStackTrace();
			return success;
		} finally {
			disconnect();
		}
		return success;
	}

	public boolean isPasswd(int id, String passwd) {
		connect();
		boolean success = false;
		String sql = "select passwd from board where id=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			success = passwd.equals(rs.getString(1));
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return success;
		} finally {
			disconnect();
		}
		return success;
	}

}
