package initial;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;

public class test {

	public static void main(String[] args) throws IOException {
		File html=new File("g:/1517761399095.html");
		Document doc=Jsoup.parse(html,"UTF-8");
		String result=doc.toString();
		while(result.contains("</script>")) {
			result=searchIndex(result);
		}
		result=result.replaceAll("data-src", "src");
		result=result.replaceAll("https://mmbiz.qpic.cn/mmbiz_gif/", "./images/1517380235334/");
		result=result.replaceAll("https://mmbiz.qpic.cn/mmbiz_png/", "./images/1517380235334/");
		result=result.replaceAll("https://mmbiz.qpic.cn/mmbiz_jpg/", "./images/1517380235334/");
		result=result.replaceAll("https://mmbiz.qpic.cn/mmbiz_jpeg/", "./images/1517380235334/");
		result=result.replaceAll("jpeg", "jpg");
		result=result.replaceAll("阅读原文", "");
		result=result.replaceAll("\\/640\\?wx_fmt=", ".");
		result=result.replaceAll("\\/0\\?wx_fmt=", ".");
		System.out.println(result);//完整的html字符串
	}

	private static String searchIndex(String str) {
		//System.out.println(1);
        int a = str.indexOf("<script");
        int b = str.indexOf("</script>")+9;
        str=str.substring(0, a)+str.substring(b);
		return str;
    }
}
