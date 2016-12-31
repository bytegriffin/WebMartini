package com.webmartini.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.webmartini.domain.Group;
import com.webmartini.domain.Message;
import com.webmartini.domain.Role;
import com.webmartini.init.SiteConfig;
import com.webmartini.interceptor.PermissionResource;
import com.webmartini.log.ClickStream;
import com.webmartini.log.OperType;
import com.webmartini.service.GroupService;
import com.webmartini.service.MessageService;
import com.webmartini.service.RoleService;
import com.webmartini.util.DataTablesPage;
import com.webmartini.util.DateUtil;
import com.webmartini.util.FileHelper;
import com.webmartini.util.StringHelper;
import com.webmartini.util.WebHelper;


@Controller
public class MessageController {

	@Autowired
	private MessageService msgService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private RoleService roleService;

	// @Autowired
	// private MyHandler myHandler;

	@RequestMapping("/message/list")
	@RequiresPermissions(PermissionResource.MESSAGE)
	public String messgeList(HttpServletRequest request) throws IOException {
		// myHandler.sendMessageToUsers(new
		// TextMessage("sssssssssssssssssssssssss"));
		return "msg/msg_list";
	}

	@RequestMapping("/message/page")
	@ResponseBody
	@RequiresPermissions(PermissionResource.MESSAGE)
	public DataTablesPage<Message> userlogPage(HttpServletRequest request) throws Exception{
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
			}
			PageHelper.orderBy(orderColumn + " " + orderDir);
		}
		List<Message> list = msgService.getMessages(searchValue);
		for(Message msg : list){
			msg.setSendTime(DateUtil.dateToStr(DateUtil.strToDate(msg.getSendTime())));
			msg.setSaveTime(DateUtil.dateToStr(DateUtil.strToDate(msg.getSaveTime())));
		}
		DataTablesPage<Message> page = new DataTablesPage<Message>(list,draw,start,pageSize);
		return page;
	}

	@RequestMapping(value="/message/add",  method = RequestMethod.GET)
	@RequiresPermissions(PermissionResource.MESSAGE)
	public String switchAddPage(HttpServletRequest request) throws IOException {
		return "msg/msg_add";
	}

	@RequestMapping(value="/role/tree",  method = RequestMethod.GET)
	@RequiresPermissions(PermissionResource.MESSAGE)
	@ResponseBody
	public List<Role> getRoleTree(HttpServletRequest request) throws IOException {
		List<Role> roleTree = groupService.getRoleTrees();
		return roleTree;
	}

	@RequestMapping(value="/group/tree",  method = RequestMethod.GET)
	@RequiresPermissions(PermissionResource.MESSAGE)
	@ResponseBody
	public List<Group> getGroupTree(HttpServletRequest request) throws IOException {
		List<Group> groupTree = roleService.getGroupTrees();
		return groupTree;
	}

	@RequestMapping(value = "/message/save", method = RequestMethod.POST)
	@ResponseBody
	@ClickStream(value="保存站内信到草稿箱",  type=OperType.add)
	@RequiresPermissions(PermissionResource.MESSAGE)
	public String saveMessage(@RequestParam("attachment1") MultipartFile attachment1,@RequestParam("attachment2") MultipartFile attachment2,
			@RequestParam("attachment3") MultipartFile attachment3,HttpServletRequest request) {
		int id = addMessage(attachment1,attachment2,attachment3,request);
		return "{\"status\": true,   \"msgid\": \"" +id+ "\"}";
	}

	@RequestMapping(value = "/message/publish", method = RequestMethod.POST)
	@ResponseBody
	@ClickStream(value="发送站内信",  type=OperType.send)
	@RequiresPermissions(PermissionResource.MESSAGE)
	public String publishMessage(@RequestParam("attachment1") MultipartFile attachment1,@RequestParam("attachment2") MultipartFile attachment2,
			@RequestParam("attachment3") MultipartFile attachment3,HttpServletRequest request) {
		String msgid = request.getParameter("msgid");
		if(StringHelper.isNullOrBlank(msgid)){
			addMessage(attachment1,attachment2,attachment3,request);
		} else {
			msgService.publish(msgid);
		}
		//给所有在线的人发送消息提醒
		return "{\"status\": true,   \"msgid\": \"" + null + "\"}";
	}

	private int addMessage(@RequestParam("attachment1") MultipartFile attachment1,@RequestParam("attachment2") MultipartFile attachment2,
			@RequestParam("attachment3") MultipartFile attachment3,HttpServletRequest request){
		String title = request.getParameter("msgtitle");
		String content = request.getParameter("msgcontent");
		String alluser = request.getParameter("alluser");
		String roleids = request.getParameter("roleids");
		String groupids = request.getParameter("groupids");
		String status = Message.SEND_STATUS;
		String submittype = request.getParameter("submittype");
		Message message = new Message();
		if("save".equals(submittype)){
			status = Message.DRAFTS_STATUS;
			message.setSaveTime(DateUtil.getCurrentDayTime());
		}else{
			message.setSendTime(DateUtil.getCurrentDayTime());
		}
		message.setSendId(WebHelper.getUserIdFromSession());
		message.setStatus(status);
		message.setTitle(title);
		message.setContent(content);
		message.setSaveTime(DateUtil.getCurrentDayTime());
		if(!StringHelper.isNullOrBlank(groupids)){
			message.setGroupIds(groupids);
		}
		if(!StringHelper.isNullOrBlank(roleids)){
			message.setRoleIds(roleids);
		}
		if(!StringHelper.isNullOrBlank(alluser) && alluser.equals("on")){
			message.setRecId(Message.ALL_USER_RECID);
		}
		if(!attachment1.isEmpty()){
			 String webpath = request.getSession().getServletContext().getRealPath("/");
			 String filepath = SiteConfig.ATTACHMENT_PATH + WebHelper.getUserIdFromSession() + "/";			
			 FileHelper.createFile(webpath + filepath, attachment1);
			 String path = filepath + attachment1.getOriginalFilename();
			 message.setAttachment1(path);
		}
		if(!attachment2.isEmpty()){
			 String webpath = request.getSession().getServletContext().getRealPath("/");
			 String filepath = SiteConfig.ATTACHMENT_PATH + WebHelper.getUserIdFromSession() + "/";			
			 FileHelper.createFile(webpath + filepath, attachment2);
			 String path = filepath + attachment2.getOriginalFilename();
			 message.setAttachment2(path);
		}
		if(!attachment3.isEmpty()){
			 String webpath = request.getSession().getServletContext().getRealPath("/");
			 String filepath = SiteConfig.ATTACHMENT_PATH + WebHelper.getUserIdFromSession() + "/";			
			 FileHelper.createFile(webpath + filepath, attachment3);
			 String path = filepath + attachment3.getOriginalFilename();
			 message.setAttachment3(path);
		}
		return msgService.insert(message);
	}

	@RequestMapping(value = "/message/view/{id}", method = RequestMethod.GET)
	@RequiresPermissions(PermissionResource.MESSAGE)
	@ClickStream(value="查看系统站内信详情",  type=OperType.view)
	public String viewMessage(@PathVariable("id") String id,HttpServletRequest request,HttpServletResponse response) throws IOException {
		Message msg = msgService.getMessageById(id);
		msg.setSendTime(DateUtil.dateToStr(DateUtil.strToDate(msg.getSendTime())));
		msg.setSaveTime(DateUtil.dateToStr(DateUtil.strToDate(msg.getSaveTime())));
		if(msg.getStatus().equals(Message.SEND_STATUS)){
			msg.setStatus("已发送");
		} else {
			msg.setStatus("已保存到草稿箱");
		}
		request.setAttribute("message", msg);
		return "msg/msg_view";
	}

	@RequestMapping(value = "/message/download", method = RequestMethod.GET)
	@RequiresPermissions(PermissionResource.MESSAGE)
	@ClickStream(value="下载某系统站内信中的附件",  type=OperType.download)
	public void downloadAttachment(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String url = request.getParameter("url");
		String webpath = request.getSession().getServletContext().getRealPath("/");
		FileHelper.download(webpath + url, response);
	}

	@RequestMapping("/message/delete/{id}")
	@RequiresPermissions(PermissionResource.MESSAGE)
	@ClickStream(value="删除系统站内信",  type=OperType.delete)
	public @ResponseBody boolean delete(@PathVariable("id") String id) {
		List<String> list = StringHelper.StringToList(id);
		msgService.delete(list);
		return true;
	}

	@RequestMapping(value = "/message/draftspublish/{id}", method = RequestMethod.GET)
	@RequiresPermissions(PermissionResource.MESSAGE)
	@ClickStream(value="将草稿箱的站内信发送",  type=OperType.send)
	@ResponseBody
	public boolean draftsPublish(@PathVariable("id") String id,HttpServletRequest request) throws IOException {
		//先发送消息
		
		//再修改状态位
		msgService.publish(id);
		return true;
	}
	

}
