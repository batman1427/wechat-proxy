package database;

public class initialData {
	  String url="";
	  String json="";
	  String time="";
      public initialData(String url,String json,String time) {
    	  this.url=url;
    	  this.json=json;
    	  this.time=time;
      }
      
      public String geturl() {
    	  return url;
      }
      
      public String getjson() {
    	  return json;
      }
      
      public String gettime() {
    	  return time;
      }
}
