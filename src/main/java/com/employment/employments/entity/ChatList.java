package com.employment.employments.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
@ApiModel(value="ChatList对象", description="")
@AllArgsConstructor
@NoArgsConstructor
public class ChatList implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "聊天列表主键")
    @TableId(value = "list_id", type = IdType.AUTO)
    private Integer listId;

    @ApiModelProperty(value = "聊天主表id")
    private String linkId;

    @ApiModelProperty(value = "发送者")
    private String fromUser;

    @ApiModelProperty(value = "接收者")
    private String toUser;

    @ApiModelProperty(value = "接收者头像")
    private String toUserPicture;

    @ApiModelProperty(value = "发送方是否在窗口")
    private Integer fromWindow;

    @ApiModelProperty(value = "接收方是否在窗口")
    private Integer toWindow;

    @ApiModelProperty(value = "未读数")
    private Integer unread;

    @ApiModelProperty(value = "是否删除")
    private Integer status;


    public ChatList(String newLinkId, String username, String toUser, Date date) {
    }

    public ChatList(String newLinkId, String username, String toUser, boolean b, boolean b1, boolean b2, int i) {
    }
}
