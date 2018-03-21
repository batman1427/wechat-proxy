package database;

import java.text.SimpleDateFormat;
import java.util.Date;

public class detailData {
	int id;
	String biz;
	int read;
	int like;	
	String publish_time;	
	String get_time;	
	String title;		
	String get_url;	
	String source_url;
	String summary;
	int is_fail;
	String author;
	public detailData(int id,String biz,int read,int like,	String publish_time,	String get_time,	String title,		String get_url,	String source_url,String summary,int is_fail,String author) {
		this.id=id;
		this.biz=biz;
		this.read=read;
		this.like=like;
		this.publish_time=publish_time;
		this.get_time=get_time;
		this.title=title;
		this.get_url=get_url;
		this.source_url=source_url;
		this.summary=summary;
		this.is_fail=is_fail;
		this.author=author;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBiz() {
		return biz;
	}
	public void setBiz(String biz) {
		this.biz = biz;
	}
	public int getRead() {
		return read;
	}
	public void setRead(int read) {
		this.read = read;
	}
	public int getLike() {
		return like;
	}
	public void setLike(int like) {
		this.like = like;
	}
	public String getPublish_time() {
		return publish_time;
	}
	public void setPublish_time(String publish_time) {
		this.publish_time = publish_time;
	}
	public String getGet_time() {
		return get_time;
	}
	public void setGet_time(String get_time) {
		this.get_time = get_time;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getGet_url() {
		return get_url;
	}
	public void setGet_url(String get_url) {
		this.get_url = get_url;
	}
	public String getSource_url() {
		return source_url;
	}
	public void setSource_url(String source_url) {
		this.source_url = source_url;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public int getIs_fail() {
		return is_fail;
	}
	public void setIs_fail(int is_fail) {
		this.is_fail = is_fail;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
}
