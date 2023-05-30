package com.employment.employments.mapper;

import com.employment.employments.entity.ChatList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.employment.employments.entity.ChatMessage;
import com.employment.employments.vo.ChatListData;
import com.employment.employments.vo.ChatMessageData;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author shao
 * @since 2023-05-28
 */
@Repository
public interface ChatMapper {

    String selectIsFirstChat(String username, String toUser);

    void addChat(ChatList chatList);

    void addChatList(ArrayList<ChatList> chatLists);

    void addChatMessage(ChatMessage chatMessage);

    List<ChatListData> selectChatLists(String username);

    List<ChatMessageData> selectChatMessage(String s, int startIndex, int pageSize);

    void clearUnread(String username, String toUser, String s);

    int selectChatMessagesTotalNumber(String linkId);

    void resetWindows(String username);

    Integer selectUnreadTotalNumber(String username);

    void updateOtherWindows(String linkId, String fromUser);

    void updateIsSaveWindows(String linkId, String fromUser);
}
