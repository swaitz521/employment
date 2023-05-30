package com.employment.employments.service;

import com.employment.employments.entity.ChatList;
import com.baomidou.mybatisplus.extension.service.IService;
import com.employment.employments.util.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shao
 * @since 2023-05-28
 */
public interface ChatService {

    boolean isFirstChat(String username, String toUser);

    Result selectChatList(String username);

    Result recentChatRecords(String username, String toUser, int startIndex, int pageSize);

    Result getPageNumber(String linkId);

    void updateWindows(String fromUser, String toUser);

    Result getUnreadTotalNumber(String username);

    void resetWindows(String username);
}
