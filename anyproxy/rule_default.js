var mysql = require('mysql');
var url = require('url');
var querystring = require('querystring');
var fs = require('fs');
var url='';
var html='';
var json='';
var count=0;
var time=new Date().getTime();
var connection = mysql.createConnection({
	host: '127.0.0.1',
	user: 'root',
	password: '123456',
	database: 'wechat'
});

connection.connect();
function insert(content){
		count++;
		if(count%3==0){
			json=content;
			connection.query('INSERT INTO initial SET  ?',{url:url,json:json,time:time});
		}else if(count%3==1){
			time=new Date().getTime();
			url=content;
		}else if(count%3==2){
			html=content;
			fs.writeFileSync('g:/article/'+time+'.html',html);
			
		};
			
}
module.exports = {
  
  summary: 'the default rule for AnyProxy',
  
  /**
   * 
   * 
   * @param {object} requestDetail
   * @param {string} requestDetail.protocol
   * @param {object} requestDetail.requestOptions
   * @param {object} requestDetail.requestData
   * @param {object} requestDetail.response
   * @param {number} requestDetail.response.statusCode
   * @param {object} requestDetail.response.header
   * @param {buffer} requestDetail.response.body
   * @returns
   */
  *beforeSendRequest(requestDetail) {
	  //console.log('1');
    return null;
  },


  /**
   * 
   * 
   * @param {object} requestDetail
   * @param {object} responseDetail
   */
  *beforeSendResponse(req,res) {
      if(/mp\/getmasssendmsg/i.test(req.url)){
      
      }else if(/mp\/profile_ext\?action=home/i.test(req.url)){//当链接地址为公众号历史消息页面时(第二种页面形式)
         
      }else if(/mp\/profile_ext\?action=getmsg/i.test(req.url)){//第二种页面表现形式的向下翻页后的json
         
      }else if(/mp\/getappmsgext/i.test(req.url)){//当链接地址为公众号文章阅读量和点赞量时
		   //console.log(res.response.body.toString());
		   insert(res.response.body.toString());
      }else if(/s\?__biz/i.test(req.url) || /mp\/rumor/i.test(req.url)){//当链接地址为公众号文章时（rumor这个地址是公众号文章被辟谣了）
           //console.log(res.response.body.toString()); 
           insert(req.url);			   
           insert(res.response.body.toString());		   
      }else{
          //callback(serverResData);
      }
      //callback(serverResData);
   return null;
  },


  /**
   * 
   * 
   * @param {any} requestDetail 
   * @returns 
   */
  *beforeDealHttpsRequest(requestDetail) {
     //console.log('3');
	return false;
  },

  /**
   * 
   * 
   * @param {any} requestDetail 
   * @param {any} error 
   * @returns 
   */
  *onError(requestDetail, error) {
     //console.log('4');
	return null;
  },


  /**
   * 
   * 
   * @param {any} requestDetail 
   * @param {any} error 
   * @returns 
   */
  *onConnectError(requestDetail, error) {
     console.log('5');
	return null;
  },

};
