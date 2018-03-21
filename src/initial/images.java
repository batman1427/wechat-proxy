package initial;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class images {
    /**
     * 下载图片到指定目录
     *
     * @param filePath 文件路径
     * @param imgUrl   图片URL
     */
	public static void main(String[] args) {
		getcover("1517380235334");
	}
    public static void downImages(String filePath, String imgUrl,boolean cover) {
        // 若指定文件夹没有，则先创建
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filename="";
        String type=imgUrl.substring(imgUrl.indexOf("_")+1,imgUrl.indexOf("_")+4);
        if(imgUrl.contains("/mmbiz/")||(imgUrl.contains("/640?")&&!imgUrl.contains("wx_fmt"))) {
        	type="jpg";
        }
        if(imgUrl.contains("mmbiz_png")&&imgUrl.contains("wx_fmt=gif")) {
        	type="gif";
        }
        if(cover) {
        	 filename="cover."+imgUrl.substring(imgUrl.indexOf("_")+1,imgUrl.indexOf("_")+4);
        }else {
        	 if(imgUrl.contains("https://mmbiz.qpic.cn/mmbiz/")) {
        		 filename=imgUrl.substring(28,imgUrl.lastIndexOf("/"))+"."+type;
        	 }else if(imgUrl.contains("http://mmbiz.qpic.cn/mmbiz/")) {
        		 filename=imgUrl.substring(27,imgUrl.lastIndexOf("/"))+"."+type;
        	 }else if(imgUrl.contains("https")) {
        		 filename=imgUrl.substring(32,imgUrl.lastIndexOf("/"))+"."+type;
        	 }else if(imgUrl.contains("http")){
        		 filename=imgUrl.substring(31,imgUrl.lastIndexOf("/"))+"."+type;
        	 }
        }
        // 写出的路径
        File file = new File(filePath + File.separator + filename );

        try {
            // 获取图片URL
            URL url = new URL(imgUrl);
            // 获得连接
            URLConnection connection = url.openConnection()
            		;
            // 设置10秒的相应时间
            connection.setConnectTimeout(10 * 1000);
            // 获得输入流
            InputStream in = connection.getInputStream();
            // 获得输出流
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
            // 构建缓冲区
            byte[] buf = new byte[1024];
            int size;
            // 写入到文件
            while (-1 != (size = in.read(buf))) {
                out.write(buf, 0, size);
            }
            out.close();
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void getImages(String url) {
        	try {
            // 得到Document对象
        	File html=new File("g:/article/"+url+".html");
        	Document doc=Jsoup.parse(html,"UTF-8");
            // 查找所有img标签
        	Element content = doc.getElementById("js_content"); 
            Elements imgs = content.getElementsByTag("img");
            System.out.println("共检测到下列图片URL：");
            System.out.println("开始下载封面");
    	    getcover(url);
            System.out.println("开始下载");
            // 遍历img标签并获得src的属性
            //System.out.println(doc.text());
            for (Element element : imgs) {
                //获取每个img标签URL "abs:"表示绝对路径
                String imgSrc = element.attr("data-src");
                // 打印URL
                System.out.println(imgSrc);
                //下载图片到本地
                if(imgSrc.contains("mmbiz.qpic.cn")) {
                	images.downImages("g:/img/"+url, imgSrc,false);
                }else {
                	 System.out.println("图片路径不合法");
                }
            }
            System.out.println("下载完成");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    //获取封面图片
    public static void  getcover(String url) {
    	try {
            // 得到Document对象
        	File html=new File("g:/article/"+url+".html");
        	Document doc=Jsoup.parse(html,"UTF-8");
        	String result=doc.toString();
    		int location=result.indexOf("msg_cdn_url");
    		//System.out.println(location);
    		int start=0;
    		int end=0;
    		for(int i=location;i<location+300;i++) {
    			if(result.charAt(i)=='\"'&&start==0) {
    				start=i;
    			}else if(result.charAt(i)=='\"'){
    				end=i;
    				i=location+300;
    			}
    					
    		}
    		//System.out.println(start);
    		//System.out.println(end);
    		if(end-start>1) {
    			result=result.substring(start+1, end);
    		}else {
    			result="无";
    		}
    		//System.out.println(result);
            // 查找所有img标签
                //下载图片到本地
                if(result.contains("mmbiz.qpic.cn")) {
                	images.downImages("g:/img/"+url, result,true);
                }else {
                	 System.out.println("图片路径不合法");
                }
            System.out.println("封面下载完成");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}