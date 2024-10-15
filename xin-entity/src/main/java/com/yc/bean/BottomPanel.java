package com.yc.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author SLGRoutine
 * @date 2024/7/5
 */
@Data
@TableName("bottompanel")
public class BottomPanel implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer bottom_id;  //#主键 底部栏内容ID
    private String title;        // 底部内容标题
    private String content;      //标题对应内容
    private Integer status;      //  状态，1为显示中，0为已经删除
}
