package com.zfk.base.constant;

import com.zfk.base.util.PropertyUtils;

/**
 * <p>Description: 常量类(不知道放在那里的常量,可以放到这里来...)</p>
 *
 * @author evan
 * @version 1.0.0
 * <p>Company:workway</p>
 * <p>Copyright:Copyright(c)2017-2018</p>
 * @date 2017/11/1
 */
public final class WayConsts {
    /**
     * 分页数
     */
    public static final Integer PAGE_SIZE = 15;

    /**
     * 图片保存路径
     */
    public static final String IMAGE_PATH = PropertyUtils.getConfig("image.formal.path");


    /**
     * 文件保存路径
     */
    public static final String FILE_PATH = PropertyUtils.getConfig("file.formal.path");
}