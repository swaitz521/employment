package com.employment.employments.controller;

import com.employment.employments.entity.User;
import com.employment.employments.service.ChatService;
import com.employment.employments.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
   @Autowired
    private ChatService chatService;

   @GetMapping("checkIsFirstChat")
    public Result checkIsFirstChat(HttpSession session, @RequestParam String toUser){
       User user = (User) session.getAttribute("user");
       String username = user.getUsername();
       boolean b= chatService.isFirstChat(username,toUser);
       if(b)
           return Result.success().msg("第一次进行聊天");
       return Result.error().msg("不是第一次聊天");
   }
   @GetMapping("getChatList")
    public Result getChatList(HttpSession httpSession){
       User user = (User) httpSession.getAttribute("user");
       String username = user.getUsername();
       return chatService.selectChatList(username);
   }
   @GetMapping("getChatRecords/{toUser}")
    public Result recentChatRecords(@PathVariable String toUser,@RequestParam int startIndex,@RequestParam int pageSize,HttpSession httpSession){
       User user = (User) httpSession.getAttribute("user");
       String username = user.getUsername();
       return chatService.recentChatRecords(username,toUser,startIndex,pageSize);
   }
    @GetMapping("/getPageNumber")
    public Result chatRecordsPageNumber(String linkId){

        return chatService.getPageNumber(linkId);
    }

    @GetMapping("/inWindows/{toUser}")
    public void updateIsSaveWindows(@PathVariable String toUser, HttpSession httpSession) {

        User user = (User) httpSession.getAttribute("user");
        String fromUser = user.getUsername();

        chatService.updateWindows(fromUser, toUser);
    }

    @GetMapping("/unread")
    public Result unreadTotalNumber(HttpSession httpSession) {

        // 获取httpSession中的user值
        User user = (User) httpSession.getAttribute("user");
        String username = user.getUsername();

        return chatService.getUnreadTotalNumber(username);

    }

    @GetMapping("/resetWindows")
    public Result resetWindows(HttpSession httpSession){

        // 获取httpSession中的user值
        User user = (User) httpSession.getAttribute("user");
        String username = user.getUsername();
        chatService.resetWindows(username);

        return Result.success("重置成功！");
    }
}
