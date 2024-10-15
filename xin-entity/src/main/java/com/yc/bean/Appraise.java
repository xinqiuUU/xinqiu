package com.yc.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *   评价  类
 * @author SLGRoutine
 * @date 2024/7/1
 */
@Data
public class Appraise implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer appraise_id;   //#主键，自动递增
    private Integer order_id ;          //订单 ID
    private Integer uid ;               //#用户 ID
    private Integer pid ;               //#产品 ID
    private Integer rating ;            //# 产品的评分，限制在 1 到 5 之间。
    private String comment ;            //#评价的文字内容。
    private Integer status ;            //#     -1 删除评价  0  未审核 , 1 审核通过  2 审核不通过
    private String created_at ;         //#记录创建时间，默认为当前时间戳
    private String image_url ;           //#保存  图片  url地址 多张-连接
    private String admin_reply ;         //#商家回复内容
    private Integer reply_status ;       //#商家回复状态

    private List<String> image_url_list; //切割 url oss地址保存
    private String head_photo;          //用户头像
    private String uname;               //用户姓名
}
