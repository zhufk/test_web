package com.zfk.util;

import java.lang.reflect.Field;

import org.springframework.util.StringUtils;

import com.zfk.base.entity.ResultData;

/**
 * <p>Description: 参数验证工具类</p>
 *
 * @author evan
 * @version 1.0.0
 * <p>Company:workway</p>
 * <p>Copyright:Copyright(c)2017-2018</p>
 * @date 2017/11/2
 */
public final class ParamValidationUtils {

    /**
     * 校验参数非空，目前只支持简单数据类型不包括自定义的javabean
     *
     * @param vo
     * @param fields    要验证的字段名称
     * @param fieldsDes 对应的返回描述
     * @return
     * @throws Exception
     */
    public static ResultData check(Object vo, String[] fields, String[] fieldsDes) throws Exception {

        if (fields == null || fieldsDes == null || fields.length != fieldsDes.length) {
            throw new Exception("check方法的数组入参有误！");
        }

        ResultData result = new ResultData(true);
        if (null == vo) {
            result.setSuccess(false);
            result.setMessage("请求参数对象为空");
            return result;
        }

        Class<Object> class1 = (Class<Object>) vo.getClass();
        for (int i = 0; i < fields.length; i++) {
            Field field = class1.getDeclaredField(fields[i]);
            //设置私有属性也可以访问
            field.setAccessible(true);
            String fieldType = field.getType().toString();
            Object fieldValue = field.get(vo);
            if (fieldType.endsWith("String")) {
                if (fieldValue == null || StringUtils.isEmpty(fieldValue.toString())) {
                    result.setSuccess(false);
                    result.setMessage(fieldsDes[i] + "不能为空");
                    return result;
                }
            } else if(fieldType.endsWith("java.util.List")){
            	if (fieldValue == null || ((java.util.List)fieldValue).size()==0) {
                    result.setSuccess(false);
                    result.setMessage(fieldsDes[i] + "不能为空");
                    return result;
                }
            }else{
                if (fieldValue == null) {
                    result.setSuccess(false);
                    result.setMessage(fieldsDes[i] + "不能为空");
                    return result;
                }
            }
        }
        return result;
    }
}