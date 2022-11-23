package com;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Search
 */
@WebServlet("/Search")
public class Search extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");

		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
			String dbid = "simple_project_1";
			String dbpw = "1234";
			conn = DriverManager.getConnection(url, dbid, dbpw);

			if (conn != null) {
				System.out.println("DB Connected");
				String query = "SELECT * FROM S_USER WHERE ID=?";
				psmt = conn.prepareStatement(query);
				psmt.setString(1, id);

				ResultSet rs = psmt.executeQuery();

				while (rs.next()) {
					String name = rs.getString(3);
					System.out.println("<tr><td>" + name + "</tr></td>");
				}

			} else {
				System.out.println("DB 연결 실패");
			}
		} catch (Exception e) { // Exception e 는 모든 exception 을 모두 검사하기 때문에 과부하가 올 수 있음. => 해당 클래스에 맞는
								// Exception을 선언해주는게 좋음.
			System.out.println("Get Connection Failed...");
			e.printStackTrace(); // exception 출력
		} finally {
			try {
				if (psmt != null) { // null 을 닫게 되면 error 가 발생할 수 있기 때문
					psmt.close();
				}
				if (conn != null) { // null 을 닫게 되면 error 가 발생할 수 있기 때문
					conn.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
