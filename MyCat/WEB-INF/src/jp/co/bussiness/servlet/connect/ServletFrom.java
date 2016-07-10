package jp.co.bussiness.servlet.connect;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;


public class ServletFrom extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println(this.getClass().toString() + "▼処理開始▼");

		URL url = new URL("http://localhost:8080/MyCat/To");
//		URL url = new URL("http://localhost:8080/MyCat/To");
//		URL url = new URL("http://requestb.in/1a8oz201");

		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		con.setRequestMethod("PUT");
		con.setInstanceFollowRedirects(false);
		con.setRequestProperty("Content-Type","text/plain;charset=UTF-8");
		// setRequestPropertyでセットした値は送り先で取得できない... そもそも概念が違う？
//		con.setRequestProperty("key", "value");
		con.setDoOutput(true);
//		BufferedWriter bf;
//		bf = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));

		String body = "members-id=" + URIUtil.encode("A=9999", null) + "&"
					+ "members-name=" + URIUtil.encode("テスト=管理者", null) + "&"
					+ "address="  + URIUtil.encode("テス都テスト区テスト町９丁目９－９９&マイハウス", null);

		PrintStream ps = new PrintStream(con.getOutputStream());
		// ボディに" &区切りKey=Value "で設定しても送り先でget系メソッドを使用して取得することはできない？
		ps.print(body);

		con.connect();

		int statusCode = con.getResponseCode();
        log("Httpステータス : " +  statusCode);

		System.out.println(this.getClass().toString() + "▲処理終了▲");

		resp.setContentType("text/html; charset=UTF-8");
		resp.getWriter().write(
				"<html>"
				+ "<head></head>"
				+ "<body>"
				+ "<h1>クラス : "+ this.getClass().toString() + "</h1>"
				+ "</br>"
				+ "レスポンスコード  : " +con.getResponseCode() +
				"<br/>" +
				"<a href=\"/MyCat/\">トップページに戻る</a>"
				+ "</body>"
				+ "</html>"
				);
	}

    /**
     * URLエンコード用メソッドの実装
     * @param body		ボディ部に指定する文字列
     * @return
     * @throws IllegalArgumentException
     *
     * Value部が本来動的で、"=", "&"が含まれていた場合挙動がおかしくなるので非推奨
     */
	@Deprecated
	private String valueURLEncode(String body) throws IllegalArgumentException, URIException{

		String keyValue[] = body.split("&");
		String key0val1[];
		StringBuffer bf = new StringBuffer();
		try{
			for(int i = 0; i < keyValue.length;  i++ ){
				key0val1 = keyValue[i].split("=");
				if (key0val1.length != 2){
					throw new IllegalArgumentException("ボディ文字列が不正です");
				}
				bf.append(key0val1[0] + "=" + URIUtil.encode(key0val1[1], null));
				if (i + 1 == keyValue.length) {
					break;
				}
				bf.append("&");
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return bf.toString();
	}
}
