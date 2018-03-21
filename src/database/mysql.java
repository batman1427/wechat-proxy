package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.PreparedStatement;
public class mysql {	
	 // 驱动程序名
    static String driver = "com.mysql.jdbc.Driver";
    // URL指向要访问的数据库名scutcs
    static String url = "jdbc:mysql://127.0.0.1:3306/wechat?characterEncoding=utf8&useSSL=false";
    // MySQL配置时的用户名
    static String user = "root"; 
    // MySQL配置时的密码
    static String password = "123456";   
	public static void main(String[] args) {
		ArrayList<initialData> al=getall();
	    for(int i=0;i<al.size();i++) {
	    	System.out.println(al.get(i).url);
	    	System.out.println(al.get(i).json);
	    	System.out.println(al.get(i).time);
	    }
	}
     
	
	//获取全部信息
	public static ArrayList<initialData> getall() {
		ArrayList<initialData> all=new ArrayList<initialData>(); 
		try { 
	         // 加载驱动程序
	         Class.forName(driver);

	         // 连续数据库
	         Connection conn = DriverManager.getConnection(url, user, password);

	         if(!conn.isClosed()) {
	          System.out.println("Succeeded connecting to the Database!");
	         }
	         Statement statement = conn.createStatement();
	            //要执行的SQL语句
	            String sql = "select * from initial";
	            //3.ResultSet类，用来存放获取的结果集！！
	            ResultSet rs = statement.executeQuery(sql);
	            String url = null;
	            String json= null;
	            String time=null;
	            Calendar todayStart = Calendar.getInstance();  
	            todayStart.set(Calendar.HOUR_OF_DAY, 0);  
	            todayStart.set(Calendar.MINUTE, 0);  
	            todayStart.set(Calendar.SECOND, 0);  
	            todayStart.set(Calendar.MILLISECOND, 0);  
	            long today=(long) todayStart.getTimeInMillis();
	            while(rs.next()){      
	                url = rs.getString("url");
	                json = rs.getString("json");
                    time = rs.getString("time");
                    if(today<Long.valueOf(time)) {
                    	initialData ini=new initialData(url,json,time);
                    	all.add(ini);
                    }
	            }
	       
	         conn.close();
	         System.out.println("读取数据完成!");
	        } catch(ClassNotFoundException e) {


	         System.out.println("Sorry,can`t find the Driver!"); 
	         e.printStackTrace();


	        } catch(SQLException e) {


	         e.printStackTrace();


	        } catch(Exception e) {


	         e.printStackTrace();


	        }
		return all; 
	}
	
	//插入文章
	public static boolean insert(detailData de) {
		try { 
	         // 加载驱动程序
	         Class.forName(driver);

	         // 连续数据库
	         Connection conn = DriverManager.getConnection(url, user, password);

	         if(!conn.isClosed()) {
	          //System.out.println("Succeeded connecting to the Database!");
	         }
	         Statement statement = conn.createStatement();
	            //要执行的SQL语句
	            String sql = "select * from article where title='"+de.title+"'and author='"+de.author+"'";
	            //3.ResultSet类，用来存放获取的结果集！！
	            ResultSet rs = statement.executeQuery(sql);
	            if(rs.next()) {
	            	System.out.println("插入失败"+"___"+"文章:"+de.title+"___已存在!");
	            	return false;
	            }else {
	            	    int num=0;
	            	    sql = "select * from article";
	    	            //3.ResultSet类，用来存放获取的结果集！！
	    	            rs = statement.executeQuery(sql);
	    	            while(rs.next()) {
	    	            	num++;
	    	            }
	            	    sql="insert into article (id,biz,`read`,`like`,publish_time,get_time,title,get_url,source_url,summary,is_fail,author) values (?,?,?,?,?,?,?,?,?,?,?,?)";   
	                    PreparedStatement preStmt=(PreparedStatement) conn.prepareStatement(sql);  
	                    preStmt.setInt(1,num+1);  
	                    preStmt.setString(2,de.biz);  
	                    preStmt.setInt(3, de.read);  
	                    preStmt.setInt(4, de.like);  	
	                    preStmt.setString(5, de.publish_time);  	
	                    preStmt.setString(6, de.get_time);  	
	                    preStmt.setString(7, de.title);  	
	                    preStmt.setString(8, de.get_url);  	
	                    preStmt.setString(9, de.source_url);  	
	                    preStmt.setString(10, de.summary);  	
	                    preStmt.setInt(11, de.is_fail); 
	                    preStmt.setString(12, de.author);  
	                    //System.out.println(preStmt);
	                    preStmt.executeUpdate();  
	                    System.out.println("插入成功"+"___"+"文章:"+de.title);
	            }
	         conn.close();
	        } catch(ClassNotFoundException e) {


	         System.out.println("Sorry,can`t find the Driver!"); 
	         e.printStackTrace();


	        } catch(SQLException e) {


	         e.printStackTrace();


	        } catch(Exception e) {


	         e.printStackTrace();


	        }
		return true;
	}
}
