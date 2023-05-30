package com.employment.employments.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author shao
 * @since 2023-05-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ChatMessage对象", description="")
public class ChatMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "聊天内容id")
    @TableId(value = "message_id", type = IdType.AUTO)
    private Integer messageId;

    @ApiModelProperty(value = "聊天主表id")
    private String linkId;

    @ApiModelProperty(value = "发送者")
    private String fromUser;

    @ApiModelProperty(value = "接收者")
    private String toUser;

    @ApiModelProperty(value = "聊天内容")
    private String content;

    @ApiModelProperty(value = "发送时间")
    private Date sendTime;

    @ApiModelProperty(value = "消息类型")
    private Integer type;

    @ApiModelProperty(value = "是否为最后一条信息")
    private Integer isLatest;


    public ChatMessage(String newLinkId, String username, String toUser, String s, Date date, boolean b) {
    }
}
