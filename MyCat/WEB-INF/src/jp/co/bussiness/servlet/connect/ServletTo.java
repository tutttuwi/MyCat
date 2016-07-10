package jp.co.bussiness.servlet.connect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.jndi.toolkit.url.UrlUtil;

public class ServletTo extends HttpServlet {

	String ENCODE = "utf-8";


	@Override
	public void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println(this.getClass().toString() + "▼処理開始▼");

		log("encoding:" + req.getCharacterEncoding());

		BufferedReader in;
		in = new BufferedReader(new InputStreamReader(req.getInputStream()));

		String line;
		// Bodyに書き込んだ文字列がそのまま取れることは確認済み。加工しないとだめ？ また、日本語がもじバケる現象
		while((line = in.readLine()) != null){
			log(line);

			log(URLDecoder.decode(line, ENCODE));

			Map<String, String> map = getParamMap(line);

			log("▼Map形式で取得確認▼");
			log("members-id   : " + map.get("members-id"));
			log("members-name : " + map.get("members-name"));
			log("address      : " + map.get("address"));
			log("▲Map形式で取得確認▲");
		}

		in.close();

//		// bodyに&区切りで入れたデータが取れるかと思ったが取れない...
//		System.out.println(req.getParameter("test"));
//		System.out.println(req.getParameter("fruits"));
//		System.out.println(req.getParameter("hoge"));
//
//		// setRequestPropertyで設定した値はこれでは取れない...
//		System.out.println(req.getParameter("key"));

//		resp.setStatus(200);
//		OutputStream out = resp.getOutputStream();
//		out.write("★成功★".getBytes());

		System.out.println(this.getClass().toString() + "▲処理終了▲");

	}

	/**
	 * ボディパラメータをマップ形式に変換します。
	 * @param line	ボディパラメータ読み込み行を指定
	 * @return		変換したMapオブジェクトを返却
	 * @throws IllegalArgumentException
	 */
	private Map getParamMap(String line) throws IOException{

		String keyValue[] = line.split("&");
		String key0val1[];
		Map<String, String> map = new HashMap<String, String>();
		try{
			for(int i = 0; i < keyValue.length;  i++ ){
				key0val1 = keyValue[i].split("=");
				if (key0val1.length != 2){
					throw new IllegalArgumentException("ボディ文字列が不正です");
				}
				map.put(key0val1[0], UrlUtil.decode(key0val1[1],ENCODE));
			}
		} catch(IOException ex) {
			ex.printStackTrace();
			throw ex;
		}
		return map;
	}
}
