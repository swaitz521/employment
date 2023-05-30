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
@ApiModel(value="ChatUserLink对象", description="")
public class ChatUserLink implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "聊天关系表id")
    @TableId(value = "link_id", type = IdType.ID_WORKER_STR)
    private String linkId;

    @ApiModelProperty(value = "发送者")
    private String fromUser;

    @ApiModelProperty(value = "接收者")
    private String toUser;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间（比如删除好友）")
    private Date updateTime;


}
