package com.employment.employments.service.impl;


import com.employment.employments.entity.ChatList;
import com.employment.employments.entity.ChatMessage;
import com.employment.employments.mapper.ChatMapper;
import com.employment.employments.service.ChatService;
import com.employment.employments.util.Result;
import com.employment.employments.vo.ChatListData;
import com.employment.employments.vo.ChatMessageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shao
 * @since 2023-05-28
 */
@Service
public class ChatServiceImpl implements ChatService {
    private static int MESSAGE_SIZE = 6;
    @Autowired
   private ChatMapper chatMapper;
    @Override
    public boolean isFirstChat(String username, String toUser) {
        String linkId= chatMapper.selectIsFirstChat(username,toUser);
        if(linkId==null){
            String newLinkId = UUID.randomUUID().toString();
            ChatList chatList=new ChatList(newLinkId,username,toUser,new Date());
            chatMapper.addChat(chatList);
            //查询对应用户的头像

            //添加两条聊天记录
            ChatList fromChatList=new ChatList(newLinkId,username,toUser,false,false,false,0);
            ChatList toChatList=new ChatList(newLinkId,toUser,username,false,false,false,0);
            ArrayList<ChatList>chatLists=new ArrayList<>();
            chatLists.add(fromChatList);
            chatLists.add(toChatList);
            chatMapper.addChatList(chatLists);
            //插入一条空白消息（为了更好地联表查询数据）
            ChatMessage chatMessage = new ChatMessage(newLinkId, username, toUser, "",new Date(), true);
            chatMapper.addChatMessage(chatMessage);
            return true;
        }
        return false;
    }

    @Override
    public Result selectChatList(String username) {
        List<ChatListData> chatListData = chatMapper.selectChatLists(username);
        return Result.success(chatListData);
    }

    @Override
    public Result recentChatRecords(String username, String toUser, int startIndex, int pageSize) {
        String s = chatMapper.selectIsFirstChat(username, toUser);
        List<ChatMessageData>chatMessageData= chatMapper.selectChatMessage(s,startIndex,pageSize);
        //反转list
        Collections.reverse(chatMessageData);
        chatMapper.clearUnread(username,toUser,s);
        return Result.success(chatMessageData);
    }

    @Override
    public Result getPageNumber(String linkId) {

        int MessagesTotalNumber = chatMapper.selectChatMessagesTotalNumber(linkId);

        int pageNumber = (MessagesTotalNumber % MESSAGE_SIZE == 0) ? (MessagesTotalNumber / MESSAGE_SIZE) : (MessagesTotalNumber / MESSAGE_SIZE) + 1;

        return Result.success(pageNumber);
    }

    @Override
    public void updateWindows(String fromUser, String toUser) {

        //获取两者之间的关联id
        String linkId = chatMapper.selectIsFirstChat(fromUser, toUser);

        //更新点击了聊天框的同一窗口值
        chatMapper.updateIsSaveWindows(linkId, fromUser);

        //清除当前fromUser的未读数
        chatMapper.clearUnread(fromUser, toUser, linkId);

        //更新其他窗口的值
        chatMapper.updateOtherWindows(linkId, fromUser);
    }

    @Override
    public Result getUnreadTotalNumber(String username) {

        //查询用户的所有的未读数
        Integer unreadTotalNumber = chatMapper.selectUnreadTotalNumber(username);

        if (unreadTotalNumber != null) {
            return Result.success( unreadTotalNumber);
        } else {
            return Result.success( 0);
        }

    }

    @Override
    public void resetWindows(String username) {

        //退出websocket连接时，重置窗口值
        chatMapper.resetWindows(username);
    }

}
