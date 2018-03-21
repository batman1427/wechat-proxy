package initial;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import database.*;

public class modify {

	public static void main(String[] args) throws JSONException, IOException {
		// TODO Auto-generated method stub
		mysql mysql=new mysql();
		ArrayList<initialData> al=mysql.getall();
		//文章id（递增）；公众号id；阅读数；点赞数；发布时间；爬取到的时间；标题；爬取的链接；原文链接；摘要；是否通过审核；作者
		for(int i=0;i<al.size();i++) {
			File html=new File("g:/article/"+al.get(i).gettime()+".html");
			int id=0;
			String biz=al.get(i).geturl().substring(33, 47);
			//System.out.println(biz);
			
			//获取阅读数和点赞数
			int[] read_like=getNum(al.get(i).getjson());
			int read=read_like[0];
			int like=read_like[1];
			
			String publish_time=getPublish_time(html);
			
	       // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	       // long lt = new Long(al.get(i).gettime());
	        //Date date = new Date(lt);
			//String get_time=simpleDateFormat.format(date);
			String get_time=al.get(i).gettime();
			String title=getTitle(html);
			
			
			String get_url=al.get(i).geturl();
			
			String source_url=getSource_url(html);
			String summary=getSummary(html,al.get(i).gettime());
			int is_fail=0;
			String author=getAuthor(html);
			detailData de=new detailData(id, biz, read, like, publish_time, get_time, title, get_url, source_url, summary, is_fail, author);
			System.out.println("开始插入文章:  "+title);
			boolean success=mysql.insert(de);
			if(success) {
				images.getImages(al.get(i).gettime());
				//图片文件夹复制到web项目
			}
			
		}
		System.out.println("文章处理完成!");	

	}
    
	//文章标题
	public static String getTitle(File html) throws IOException {
		Document doc=Jsoup.parse(html,"UTF-8");
		Element content = doc.getElementById("activity-name"); 
		//System.out.println(content.text());
		return content.text();	
	}
	
	//处理阅读数和点赞数
	public static int[] getNum(String json) throws JSONException {
		JSONObject dataJson = new JSONObject(json);
		//System.out.println(dataJson);
		JSONObject data = new JSONObject(dataJson.get("appmsgstat").toString());
		//System.out.println(data);
		int read=data.getInt("read_num");
		int like=data.getInt("like_num");
		int [] result= {read,like};
		return result;		
	}
	
	//发布时间
	public static String getPublish_time(File html) throws IOException {
		Document doc=Jsoup.parse(html,"UTF-8");
		Element content = doc.getElementById("post-date"); 
		//System.out.println(content.text());
		return content.text();	
	}
	
	//原文链接
	public static String getSource_url(File html) throws IOException {
		Document doc=Jsoup.parse(html,"UTF-8");
		String result=doc.toString();
		int location=result.indexOf("msg_source_url");
		int start=0;
		int end=0;
		for(int i=location;i<location+300;i++) {
			if(result.charAt(i)=='\''&&start==0) {
				start=i;
			}else if(result.charAt(i)=='\''){
				end=i;
				i=location+300;
			}
					
		}
		if(end-start>1) {
			result=result.substring(start+1, end);
		}else {
			result="无";
		}
		//System.out.println(result.substring(start+1, end));
		return result;	
	}
	
	//摘要
	public static String getSummary(File html,String time) throws IOException {
		Document doc=Jsoup.parse(html,"UTF-8");
		Element content = doc.getElementById("js_content"); 
		String str=content.text();
        FileWriter writer;
        try {
            writer = new FileWriter("g:/txt/"+time+".txt");
            writer.write(str);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		String result="";
		if(content.text().length()<120) {
			result=content.text();
		}else {
			result=content.text().substring(0, 110);
		}
		//System.out.println(content.text().substring(0, 100));
		return result;	
	}
	
	//作者
	public static String getAuthor(File html) throws IOException {
		Document doc=Jsoup.parse(html,"UTF-8");
		String result=doc.toString();
		if(result.contains("<em class=\"rich_media_meta rich_media_meta_text\">")) {
			int location=result.indexOf("<em class=\"rich_media_meta rich_media_meta_text\">");
			int start=0;
			int end=0;
			for(int i=location+1;i<location+100;i++) {
				if(result.charAt(i)=='>'&&start==0) {
					start=i;
				}else if(result.charAt(i)=='<'){
					end=i;
					i=location+100;
				}
						
			}
			result=result.substring(start+1, end);
		}else {
			Element content = doc.getElementById("post-user"); 
			result=content.text();
		}
		//System.out.println(result);
		return result;	
	}
	
	
	
 }
