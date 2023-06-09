package com.employment.employments.vo;

/**
 * @author 谢佳豪
 * @data 2021/1/17 - 20:58
 * @description 聊天信息的数据
 */
public class ChatMessageData {

    //发送者
    private String sendUser;

    //信息内容
    private String content;

    //发送时间
    private String sendTime;


    public String getSendUser() {
        return sendUser;
    }

    public void setSendUser(String sendUser) {
        this.sendUser = sendUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public String toString() {
        return "ChatMessageData{" +
                "sendUser='" + sendUser + '\'' +
                ", content='" + content + '\'' +
                ", sendTime='" + sendTime + '\'' +
                '}';
    }
}
