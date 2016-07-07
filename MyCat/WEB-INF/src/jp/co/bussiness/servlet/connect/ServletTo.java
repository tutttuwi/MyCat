package jp.co.bussiness.servlet.connect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletTo extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println(this.getClass().toString() + "▼処理開始▼");
		BufferedReader in;
		in = new BufferedReader(new InputStreamReader(req.getInputStream()));

		String line;
		while((line = in.readLine()) != null){
			System.out.println(line);
		}

		in.close();
		System.out.println(this.getClass().toString() + "▲処理終了▲");







	}



}
