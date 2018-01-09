/*! T.js
 * ================
 * 主要包括各模块系统输入框验证
 * @Author  liulei
 * @Date 2017-12-08
 * @version 1.0.0
 */
var T = {
    Validator:function (valArr) {
        var fieldsObj = {};
        valArr.forEach(function(value){
            fieldsObj[value.name] = {
                validators: {
                    notEmpty: {
                        message: value.message
                    }
                }
            }
        });
        var obj = {
            message: '此值无效',
            feedbackIcons: {
                valid: 'glyphicon',
                invalid: 'glyphicon',
                validating: 'glyphicon'
            },
            fields: fieldsObj
        };
        $('form').bootstrapValidator(obj);
    },

    //提交
     sub:function (eleID) {
         $(eleID).click(function () {
             $('.form-horizontal').bootstrapValidator('validate');
             var temp= $(".help-block").is(":visible");
             if(temp){
                 $(".layui-layer-shade").css("display","none");
                 $(".layui-layer").hide();
             }
         })
     },


    /**
     * 信息发布
     */
    message:function () {
        T.Validator([{name:"title",message:"标题不能为空"},
            {name:"counent",message:"内容不能为空"}
        ]);
        T.sub("#addBtn");
    },
    /**
     * 知识库流程
     */
    knowledgeFlow:function(){
        T.Validator([{name:"flowName",message:"请输入流程名称"},
            {name:"triggerCondition",message:"请输入触发条件"}]);
        T.sub("#addBtn");
    },

    /**
     * 知识库问答库
     */
    knowledgeQuestion:function(){
        T.Validator([{name:"question",message:"问题不能为空"},
            {name:"answer",message:"答案不能为空"}]);
        T.sub("#addBtn");
    },


    /**
     * 实体库
     */
    object:function(){
        T.Validator([{name:"objectName",message:"实体名称不能为空"},
            {name:"objectValue",message:"实体值不能为空"}]);
        T.sub("#addBtn");
    },

    /**
     * 知识库技能
     */
    knowledgeSkill:function(){
        T.Validator([{name:"skillName",message:"技能名称不能为空"},
            {name:"triggerCondition",message:"触发条件不能为空"},
            {name:"file",message:"执行脚本不能为空"}
        ]);
        T.sub("#addBtn");
    },


    /**
     * 知识模板
     */
    flowTemp:function(){
        T.Validator([{name:"flowName",message:"流程名称不能为空"},
            {name:"triggerCondition",message:"触发条件不能为空"}
        ]);
        T.sub("#addBtn");
    },
    /**
     * 新增机器人
     */

    addRobot:function(){
        T.Validator([{name:"skillName",message:"技能名称不能为空"},
            {name:"nickName",message:"名字不能为空"},
            {name:"robotAge",message:"年龄不能为空"},
            {name:"installSiteId",message:"安装位置不能为空"}]);
        T.sub("#submitButton");
    },

    /**
     * 机器人表情
     */
    robotExpression:function(){
        T.Validator([{name:"robotTypeId",message:"类型不能为空"},
            {name:"description",message:"表情描述不能为空"}]);
        T.sub("#submitButton");
    },

    /**
     * 机器人动作
     */
    robotAction:function(){
        T.Validator([{name:"actionsName",message:"动作名称不能为空"},
            {name:"description",message:"动作描述不能为空"}]);
        T.sub("#submitButton");
    },

    /**
     * 机器人类型
     */

    robotType:function(){
        T.Validator([{name:"robotTypeName",message:"类型不能为空"},
            {name:"manufacturer",message:"请输入厂家名称"},
            {name:"nickName",message:"用户名不能为空"},
            {name:"phone",message:"手机号码不能为空"},
            {name:"robotAge",message:"年龄不能为空"},
            {name:"description",message:"请描述它哦！！！"}]);
        T.sub("#submitButton");
    },

    /**
     * 安装位置
     */
    robotSite:function(){
        T.Validator([{name:"siteName",message:"安装位置名称不能为空"},
            {name:"description",message:"安装位置描述不能为空"}]);
        T.sub("#submitButton");
    },

    /**
     * 室内定点
     */
    robotPoint:function(){
        T.Validator([{name:"anchorPointName",message:"室内定点名称不能为空"},
            {name:"description",message:"室内定点描述不能为空"},
            {name:"installSiteId",message:"安装位置不能为空"}]);
        T.sub("#submitButton");
    },

    /**
     * 子系统
     */

    subSystem:function(){
        T.Validator([{name:"subsystemName",message:"子系统名称不能为空"},
        {name:"description",message:"子系统描述不能为空"}]);
        T.sub("#submitButton");

    },
    /**
     * 重要词
     */

    bigWord:function(){
        T.Validator([{name:"bigWord",message:"重要词内容不能为空"}]);
        T.sub("#submitButton");

    },

    /**
     * 敏感词
     */

    sensitivity:function(){
        T.Validator([{name:"sensitivityWord",message:"敏感词内容不能为空"}]);
        T.sub("#submitButton");

    },

    /**
     * 停用词
     */

    stopWord:function(){
        T.Validator([{name:"stopWord",message:"停用词内容不能为空"}]);
        T.sub("#submitButton");

    },

    /**
     * 同义词
     */

    synonym:function(){
        T.Validator([{name:"synonymValue",message:"同义词值不能为空"},
            {name:"synonymDesp",message:"描述不能为空"}]);
        T.sub("#submitButton");

    },

    /**
     * 配置
     */

    param:function(){
        T.Validator([{name:"confKey",message:"KEY值不能为空"},
            {name:"confValue",message:"VALUE值不能为空"},
            {name:"confDesp",message:"配置描述不能为空"}]);
        T.sub("#submitButton");

    },

    /**
     * 提示语
     */

    markeWord:function(){
        T.Validator([{name:"markeValue",message:"提示词代码不能为空"},
            {name:"markeDesp",message:"描述不能为空"}]);
        T.sub("#submitButton");

    },


    /**
     * 修改密码
     */

    pwd:function(){
        T.Validator([{name:"userToke",message:"原始密码不能为空"},
            {name:"newUserToke",message:"新密码不能为空"},
            {name:"confirmUserToke",message:"确认密码不能为空"}]);
        T.sub("#submitButton");

    },

    /**
     * 新增用户
     */
    addUseVa:function () {
        $('form').bootstrapValidator({
            message: '此值无效',
            feedbackIcons: {
                valid: 'glyphicon',
                invalid: 'glyphicon',
                validating: 'glyphicon'
            },
            fields: {
                loginName: {
                    validators: {
                        notEmpty: {
                            message: '登录名不能为空'
                        },
                        regexp: {
                            regexp: /(?=.*\(.*\)|.*（.*）)^[a-zA-Z0-9\u4e00-\u9fa5()（）]*$|^[a-zA-Z0-9\u4e00-\u9fa5]*$/,
                            message: '请填写有效的登录名'
                        },
                        stringLength: {
                            message: '登录名长度必须在2到15位之间'
                        }
                    }
                },
                userName:{
                    validators: {
                        notEmpty: {
                            message: '用户名称不能为空'
                        },
                        regexp: {
                            regexp: /(?=.*\(.*\)|.*（.*）)^[a-zA-Z0-9\u4e00-\u9fa5()（）]*$|^[a-zA-Z0-9\u4e00-\u9fa5]*$/,
                            message: '请填写有效的用户名称'
                        },
                        stringLength: {
                            message: '用户名长度必须在2到20位之间'
                        }
                    }
                },
                userToke: {
                    validators: {
                        notEmpty: {
                            message: '请填写密码'
                        },
                        stringLength: {
                            min: 6,
                            max: 12,
                            message: '密码长度必须在6到12位之间'
                        }
                    },
                    threshold:3
                },

                mobile:{
                    validators: {
                        notEmpty: {
                            message: '手机号码不能为空'
                        },
                        regexp: {
                            regexp: /^1\d{10}$/,
                            message: '请填写正确的手机号'
                        }
                    },
                    threshold:11
                },
                unitName:{
                    validators: {
                        notEmpty: {
                            message: '单位名称不能为空'
                        },
                        regexp: {
                            regexp: /(?=.*\(.*\)|.*（.*）)^[a-zA-Z0-9\u4e00-\u9fa5()（）]*$|^[a-zA-Z0-9\u4e00-\u9fa5]*$/,
                            message: '请填写有效的单位名称'
                        }
                    }
                },
                roleIds:{
                    validators: {
                        notEmpty: {
                            message: '请选择所属角色'
                        }
                    }
                },
                email: {
                    validators: {
                        emailAddress: {
                            message: '邮箱地址格式有误'
                        }
                    },
                    threshold:10
                }

            }
        });
        T.sub("#submitButton");
    }

};