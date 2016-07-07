package jp.co.bussiness.servlet.connect;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ServletFrom extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println(this.getClass().toString() + "▼処理開始▼");

		URL url = new URL("http://localhost:8080/MyCat/To");
//		URL url = new URL("http://requestb.in/1a8oz201");

		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		con.setRequestMethod("GET");
		con.setInstanceFollowRedirects(false);
//		con.setRequestProperty("key", "value");
		con.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
		con.setDoOutput(true);
//		BufferedWriter bf;
//		bf = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
		PrintStream ps = new PrintStream(con.getOutputStream());
		ps.print("テスト文字列");

		con.connect();

		int st = con.getResponseCode();
		System.out.println(st);

		System.out.println(this.getClass().toString() + "▲処理終了▲");



	}


}
