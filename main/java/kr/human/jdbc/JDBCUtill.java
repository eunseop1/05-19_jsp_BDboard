package kr.human.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JDBCUtill {
	// DB Pool 로부터 컨넥션을 얻어온다
	public static Connection getConnection() {
		Connection conn = null;
		
		try {
			//web.xml이 제일 먼저 실행되는데  resource-ref를 설정했기에 해당 하는 이름의 DB를 찾는다. 이 내용은 어플리케이션의 모든 정보가 저장된 Context.xml에 존재한다. 그중에서 내가 필요로 하는 이름과 같은 것을 찾아서 이용한다. 등록된 자원을 구분하기 위해서 이름으로 찾는데 이름 세군데가 일치하는 존재를 찾아서 해당 파일의 connection pool을 찾아내서 쓴다 
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource)envContext.lookup("jdbc/mariaDB");
			conn = ds.getConnection();
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("DB 연결 정보: " + conn);
		return conn;
	}
	
	// 닫는 메서드 3개
	public static void close(Connection conn) {
		try {
			if(conn!=null) conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void close(Statement stmt) {
		try {
			if(stmt!=null) stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void close(ResultSet rs) {
		try {
			if(rs!=null) rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 롤백 메서드 1개
	public static void rollback(Connection conn) {
		try {
			if(conn!=null) conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}