package com.yc.service;

import com.yc.web.model.ResuserVO;

public interface ResuserBiz {

    //ResuserVO 是前端传给后端的数据
    public String regUser(ResuserVO resuser);

}
