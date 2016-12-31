package com.webmartini.controller;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.webmartini.domain.UserMessage;
import com.webmartini.log.ClickStream;
import com.webmartini.log.OperType;
import com.webmartini.service.UserMessageService;
import com.webmartini.util.DataTablesPage;
import com.webmartini.util.DateUtil;
import com.webmartini.util.FileHelper;
import com.webmartini.util.StringHelper;
import com.webmartini.util.WebHelper;

@Controller
public class UserMessageController {

	@Autowired
	private UserMessageService userMsgService;

	@RequestMapping("/user/message/usermsg")
	@ResponseBody
	public String ajax(long timed, HttpServletResponse response) throws Exception {
		 String str = "";
	     Random rand = new Random();
	     // 死循环 查询有无数据变化
	     while (true) {
	         Thread.sleep(3000); // 休眠300毫秒，模拟处理业务等
	         int i = rand.nextInt(100); // 产生一个0-100之间的随机数
	         if (i > 20 && i < 56) { // 如果随机数在20-56之间就视为有效数据，模拟数据发生变化
	             long responseTime = System.currentTimeMillis();
	             // 返回数据信息，请求时间、返回数据时间、耗时
	             str = "result: " + i + ", response time: " + responseTime + ", request time: " + timed + ", use time: " + (responseTime - timed);
	             break; // 跳出循环，返回数据
	         } else { // 模拟没有数据变化，将休眠 hold住连接
	             Thread.sleep(13000);
	         }
	     }
	     return "{\"status\": success,   \"message\": \""+str+"\"}";
	}

	//获取未读站内信条数
	@RequestMapping("/user/message/count")
	@ResponseBody
	public int getUnreadCount(HttpServletRequest request) {
		int count = userMsgService.getUnReadCountByRecId(WebHelper.getUserIdFromSession());
		return count;
	}

	@RequestMapping("/user/message/page")
	@ResponseBody
	public DataTablesPage<UserMessage> userMessagePage(HttpServletRequest request) {
		String searchValue = request.getParameter("status");
		int start = StringHelper.isNullOrBlank(request.getParameter("start")) ? 0
				: Integer.valueOf(request.getParameter("start"));
		int pageSize = StringHelper.isNullOrBlank(request.getParameter("length")) ? 0
				: Integer.valueOf(request.getParameter("length"));
		int draw = StringHelper.isNullOrBlank(request.getParameter("draw")) ? 0
				: Integer.valueOf(request.getParameter("draw"));

		String orderColumn = request.getParameter("order[0][column]");
		String orderDir = request.getParameter("order[0][dir]");

		int pageNum = DataTablesPage.getPageNum(start, pageSize);
		PageHelper.startPage(pageNum, pageSize);
		if (!StringHelper.isNullOrBlank(orderColumn)) {
			if (orderColumn.equals("1")) {
				orderColumn = "id";
			}else if (orderColumn.equals("2")) {
				orderColumn = "name";
			}
			PageHelper.orderBy(orderColumn + " " + orderDir);
		}

		List<UserMessage> list = userMsgService.getUserMessagesByRecId(WebHelper.getUserIdFromSession(), searchValue);
		String webpath = request.getSession().getServletContext().getRealPath("/");
		for(UserMessage msg : list){
			msg.setSendTime(DateUtil.dateToStr(DateUtil.strToDate(msg.getSendTime())));
			String attachement1 = webpath + msg.getAttachment1();
			String attachement2 = webpath + msg.getAttachment2();
			String attachement3 = webpath + msg.getAttachment3();
			if(!FileHelper.exist(attachement1)){
				msg.setAttachment1(null);
			}
			if(!FileHelper.exist(attachement2)){
				msg.setAttachment2(null);
			}
			if(!FileHelper.exist(attachement3)){
				msg.setAttachment3(null);
			}
		}
		DataTablesPage<UserMessage> page = new DataTablesPage<UserMessage>(list,draw,start,pageSize);
		return page;
	}

	@RequestMapping(value = "/user/message", method = RequestMethod.GET)
	public String getMyMessage(HttpServletRequest request) {
		return "msg/user_msg_list";
	}

	@RequestMapping(value = "/user/message/view/{id}", method = RequestMethod.GET)
	@ClickStream(value="用户查看系统站内信详情",  type=OperType.view)
	public String viewMessage(@PathVariable("id") String id,HttpServletRequest request,HttpServletResponse response) throws IOException {
		userMsgService.read(id);
		UserMessage msg = userMsgService.getUserMessageById(id);
		msg.setSendTime(DateUtil.dateToStr(DateUtil.strToDate(msg.getSendTime())));
		String webpath = request.getSession().getServletContext().getRealPath("/");
		String attachement1 = webpath + msg.getAttachment1();
		String attachement2 = webpath + msg.getAttachment2();
		String attachement3 = webpath + msg.getAttachment3();
		if(!FileHelper.exist(attachement1)){
			msg.setAttachment1(null);
		}
		if(!FileHelper.exist(attachement2)){
			msg.setAttachment2(null);
		}
		if(!FileHelper.exist(attachement3)){
			msg.setAttachment3(null);
		}
		request.setAttribute("message", msg);
		return "msg/user_msg_view";
	}
	
	@RequestMapping(value = "/user/message/download", method = RequestMethod.GET)
	@ClickStream(value="用户下载某系统站内信中的附件",  type=OperType.download)
	public void downloadAttachment(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String url = request.getParameter("url");
		String webpath = request.getSession().getServletContext().getRealPath("/") + url;
		FileHelper.download(webpath, response);
	}
	
	@RequestMapping("/user/message/delete/{id}")
	@ClickStream(value="用户删除系统站内信",  type=OperType.delete)
	public @ResponseBody boolean delete(@PathVariable("id") String id) {
		List<String> list = StringHelper.StringToList(id);
		userMsgService.delete(list);
		return true;
	}

}
