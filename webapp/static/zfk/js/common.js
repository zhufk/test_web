
function formatDate(date) {
    var formatDate = date.getFullYear() + "-";
    formatDate += ((date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1)) + '-';
    formatDate += date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
    return formatDate;
};

//将xxxx-xx-xx的时间格式，转换为 xxxx/xx/xx的格式
//Date兼容safari浏览器
function GetDateDiff(dateStr) {
    return dateStr.replace(/\-/g, "/");
};

Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, //小时
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    var week = {
        "0": "/u65e5",
        "1": "/u4e00",
        "2": "/u4e8c",
        "3": "/u4e09",
        "4": "/u56db",
        "5": "/u4e94",
        "6": "/u516d"
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    if (/(E+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "/u661f/u671f"
            : "/u5468") : "") + week[this.getDay() + ""]);
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1,
                (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
};

/**
 * 表单提交
 * @param url
 * @param formName
 * @param callback
 */
function formSubmit(formName, url, callback) {

    //提交参数
    var params = $('#' + formName).serialize();

    if (params == '' || params == undefined) {
        return;
    }

    //提交参数字符串转化为参数数组
    var paramArr = params.split("&");
    for (var i = 0; i < paramArr.length; i++) {
        var keyValue = paramArr[i].split("=");
        //参数值为空
        if (keyValue[1] == '') {
            //删除元素
            paramArr.splice(i, 1);
            //因为删除了一个元素所以这里需要i-1
            i--;
        }
    }

    //如果paramArr为空，则不传任何参数
    //如果paramArr不为空，则将数组用&连接成字符串
    if (paramArr.length == 0) {
        return;
    } else {
        params = paramArr.join("&");
    }

    //显示loadding
    layer.load(1, {
        shade: [0.1, '#fff'],
        time: 1000
    });

    $('.workway-disabled').attr("disabled", true);

    $.ajax({
        type: "POST",
        url: url,
        data: params,
        async: true,
        error: function (request) {
            goSync("page/500");
        },
        success: function (data) {
            if (data.success) {
                messageSucces(data.message, function () {
                    callback();
                    $('.workway-disabled').attr("disabled", false);
                });
            }
            else {
                messageError(data.message);
                $('.workway-disabled').attr("disabled", false);
            }

        }
    });
};

/**
 * 表单提交
 * @param params 请求参数
 * @param url 请求URL
 * @param callback 成功回调方法
 */
function formSubmitParam(params, url, callback) {
    //显示loadding
    layer.load(1, {
        shade: [0.1, '#fff'],
        time: 1000
    });

    $('.workway-disabled').attr("disabled", true);

    $.ajax({
        type: "POST",
        url: url,
        data: params,
        contentType: 'application/json;charset=utf-8',
        async: true,
        error: function (request) {
            goSync("page/500");
        },
        success: function (data) {
            if (data.success) {
                messageSucces(data.message, function () {
                    callback();
                    $('.workway-disabled').attr("disabled", false);
                });
            }
            else {
                messageError(data.message);
                $('.workway-disabled').attr("disabled", false);
            }
        }
    });
};

/**
 * 初始化页面数据
 * @param data
 */
function initPage(data) {
    if (isLogin(data)) {
        $('.page-content-wrapper').html(data);
    }
};

function isLogin(data) {
    if (typeof(data) == "string" && data.indexOf('<meta id="login">') >= 0) {
        var title = '注意';
        var content = '<p>您已退出系统，是否重新登录？</p>';

        layer.confirm(content, {icon: 5, title: title}, function (index) {
            //重新登录
            window.location.href = "initLogin";
            layer.close(index);
        }, function (index) {
            layer.close(index);
        });
        return false;
    }
    return true;
};

/**
 * 系统登出
 */
function logout() {
    $(window).unbind('beforeunload');
    window.location.href = "logout";
};

/**
 * get提交，用户页面跳转
 * @param  url 必选，提交地址
 */
function go(url) {
    $.ajax({
        type: "GET",
        url: url,
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        cache: false,
        error: function (request) {
            goSync("page/500");
        },
        success: function (data) {
            initPage(data);
        }
    });
};

/**
 * get提交，用户页面跳转
 * 同步提交
 * @param  url 必选，提交地址
 */
function goSync(url) {
    $.ajax({
        type: "GET",
        url: url,
        cache: false,
        async: false,
        error: function (request) {
            goSync("page/500");
        },
        success: function (data) {
            initPage(data);
        }
    });
};

/**
 * get提交，页面跳转，带禁用元素
 * @param url
 * @param clazz 禁用元素类名
 */
function goDisabled(url) {
    $('.workway-disabled').attr("disabled", true);
    $.ajax({
        type: "GET",
        url: url,
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        cache: false,
        error: function (request) {
            goSync("page/500");
        },
        success: function (data) {
            initPage(data);
        }
    });
};


/**
 * ajax POST提交，带回调方法
 * @param url
 * @param callback
 */
function goUrlCall(url, callback) {
    $.ajax({
        type: "POST",
        url: url,
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        cache: false,
        error: function (request) {
            goSync("page/500");
        },
        success: function (data) {
            if (data.success) {
                if (null != callback && undefined != callback && "function" == typeof callback) {
                    messageSucces(data.message, function () {
                        callback();
                    });
                } else {
                    messageSucces(data.message);
                }
            }
            else {
                messageError(data.message);
            }
        }
    });
};


/**
 * ajax POST提交，带回调，带禁用元素
 * @param url
 * @param callback
 * @param clazz 禁用元素类名
 */
function postUrlCallDisabled(url,params, callback) {
    $('.workway-disabled').attr("disabled", true);
    $.ajax({
        type: "POST",
        url: url,
        data: params,
        async: false,
        cache: false,
        error: function (request) {
            goSync("page/500");
        },
        success: function (data) {
            if(isLogin(data)){
                if(typeof(data)=="string" && data.indexOf('<div class="col-md-12 page-error-content page-500">')>=0){
                    goSync("page/500");
                }
                if (data.success) {
                    if (null != callback && undefined != callback && "function" == typeof callback) {
                        messageSucces(data.message, function () {
                            callback();
                            $('.workway-disabled').attr("disabled", false);
                        });
                    } else {
                        messageSucces(data.message, function () {
                            $('.workway-disabled').attr("disabled", false);
                        });
                    }
                }else {
                    messageError(data.message);
                    $('.workway-disabled').attr("disabled", false);
                }
            }
        }
    });
};


function subForm(url, formId) {
    if (!formId) {
        formId = "searchForm";
    }
    if (!url) {
        url = $('#' + formId).attr('action');
    }
    /*
     * 去掉所有的前后空格
     */
    $('#' + formId + ' input').each(function () {
        $(this).val($(this).val().trim());
    });
    var param = $('#' + formId).serialize();
    var paramArr = param.split('&');
    var finalParam = '';
    if (paramArr && paramArr.length > 0) {
        for (var i = 0; i < paramArr.length; i++) {
            var key_value = paramArr[i].split('=');
            if (key_value[1] && key_value[1].trim()) {
                finalParam += key_value[0];
                finalParam += '=';
                finalParam += key_value[1].trim();
                finalParam += '&';
            }
        }
        finalParam = finalParam.substr(0, finalParam.length - 1);
    }
    $.ajax({
        type: "POST",
        url: url,
        data: finalParam,
        async: true,
        error: function (request) {
            goSync("page/500");
        },
        success: function (data) {
            initPage(data);
        }
    });
};

/**
 * 初始化异常显示层
 * @param errorStatusId 异常状态
 * @param fromId 表单
 */
function initNoticeMessage(fromId) {
    if ($("#returnMessaStatus").val()) {
        var error1 = $('.alert-danger', $('#' + fromId));
        error1.show();
    }
};


/*
 * 分页跳转
 */
function pageJump(url, formId, action) {
    //页面进来不查询数据时也就不存在分页了
    if ($('input[name="pageNo"]').length != 0 || $('input[name="totalPageNo"]').length != 0) {
        var pageNo = $('input[name="pageNo"]').val() - 0;//获得当前页
        var totalPageNo = $('input[name="totalPageNo"]').val() - 0;//获得总页码
        if (isNaN(pageNo) || isNaN(totalPageNo)) {
            return false;
        }
        if (action == 'next') {
            if (totalPageNo == pageNo) {
                alert("已经是最后一页了~"); //最后一页不能查询下一页
                return false;
            }
            pageNo += 1;
        } else if (action == 'previous') {
            if (pageNo == 1) {
                alert("已经是第一页了~");//第一页不能查询上一页
                return false;
            }
            pageNo -= 1;
        } else if (action == 'go') {
            pageNo = $('input[name="expectPageNo"]').val() - 0;
            if (isNaN(pageNo)) {
                alert("请输入数字~");//请输入数字;
                return false;
            } else if (pageNo < 1 || pageNo > totalPageNo) {
                alert("请输入合适的页码~");//请输入合适的页码;
                return false;
            }

        } else {
            pageNo = 1;
        }
        $('input[name="pageNo"]').val(pageNo);
    }

    subForm(url, formId);//提交表单
};

/**
 * 确认提示框
 * @param str 提示语
 * @param meth 确认方法
 */
function messageConfirm(str, meth, closemeth) {
    layer.confirm(str, {icon: 3, title: '提示'}, function (index) {
        meth();
        layer.close(index);
    }, function (index) {
        if (null != closemeth && undefined != closemeth && "function" == typeof closemeth) {
            closemeth();
        }
        layer.close(index);
    });

};

//3.消息成功提示框
function messageSucces(str, callback) {
    if (null != callback && undefined != callback && "function" == typeof callback) {
        layer.msg(str, {
            icon: 1,
            time: 2000
        }, function () {
            callback();
        });
    } else {
        layer.msg(str.message, {
            icon: 1,
            time: 2000
        });
    }
};

//4.消息失败提示框
function messageError(str, callback) {
    if (null != callback && undefined != callback && "function" == typeof callback) {
        layer.msg(str, {icon: 2}, function () {
            callback();
        });
    } else {
        layer.msg(str, {icon: 2});
    }
};

function indexInit() {
    goSync("home/index");
};


/*查询作用范围*/
function getActionScopes(sel, vals) {
    $.ajax({
        type: 'post',
        url: "message_publish/list_action_scopes?typeStr=123",
        dataType: 'json',
        success: function (result) {
            var robotOpts = '<optgroup label="机器人" id="1">';
            var robotTypeOpts = '<optgroup label="机器人类型" id="2">';
            var robotSiteOpts = '<optgroup label="机器人安装位置" id="3">';
            for (var i = 0; i < result.length; i++) {
                var scope = result[i];
                if (scope.scopeType == 1) {
                    robotOpts += ('<option value="' + scope.scopeId + '">'
                        + scope.scopeName + '</option>');
                } else if (scope.scopeType == 2) {
                    robotTypeOpts += ('<option value="' + scope.scopeId + '">'
                        + scope.scopeName + '</option>');
                } else if (scope.scopeType == 3) {
                    robotSiteOpts += ('<option value="' + scope.scopeId + '">'
                        + scope.scopeName + '</option>');
                }
            }
            robotOpts += '</optgroup>';
            robotTypeOpts += '</optgroup>';
            robotSiteOpts += '</optgroup>';
            $('#' + sel).append(robotOpts);
            $('#' + sel).append(robotTypeOpts);
            $('#' + sel).append(robotSiteOpts);


            $('#' + sel).multiselect({
                buttonWidth: '83%',
                nonSelectedText: "请选择作用范围",
                nSelectedText: "已选",
                allSelectedText: '全选',
                delimiterText: '; ',//间隔符
                enableClickableOptGroups: true,//分组可选
                enableCollapsibleOptGroups: true,//分组可收
                enableFiltering: true,//可过滤
                includeSelectAllOption: true,//设置可全选
                selectAllText: '全选',
                selectAllValue: -1,
                maxHeight: 300,
                dropUp: true
            });
            if (vals != null && vals != undefined && vals.length > 0) {
                $.each(vals, function (index, value) {
                    $("#" + sel).multiselect('select', value);
                });
                $("#" + sel).multiselect("refresh");
            }
        }
    });
}

/*查询作用范围，生成下拉框*/
function getActionScopesWithConfig(sel, vals, width, isDropUp) {
    $.ajax({
        type: 'post',
        url: "message_publish/list_action_scopes?typeStr=123",
        dataType: 'json',
        success: function (result) {
            var robotOpts = '<optgroup label="机器人" id="1">';
            var robotTypeOpts = '<optgroup label="机器人类型" id="2">';
            var robotSiteOpts = '<optgroup label="机器人安装位置" id="3">';
            for (var i = 0; i < result.length; i++) {
                var scope = result[i];
                if (scope.scopeType == 1) {
                    robotOpts += ('<option value="' + scope.scopeId + '">'
                        + scope.scopeName + '</option>');
                } else if (scope.scopeType == 2) {
                    robotTypeOpts += ('<option value="' + scope.scopeId + '">'
                        + scope.scopeName + '</option>');
                } else if (scope.scopeType == 3) {
                    robotSiteOpts += ('<option value="' + scope.scopeId + '">'
                        + scope.scopeName + '</option>');
                }
            }
            robotOpts += '</optgroup>';
            robotTypeOpts += '</optgroup>';
            robotSiteOpts += '</optgroup>';
            $('#' + sel).append(robotOpts);
            $('#' + sel).append(robotTypeOpts);
            $('#' + sel).append(robotSiteOpts);

            $('#' + sel).multiselect({
                buttonWidth: width,
                nonSelectedText: "请选择作用范围",
                nSelectedText: "已选",
                allSelectedText: '全选',
                delimiterText: '; ',//间隔符
                enableClickableOptGroups: true,//分组可选
                enableCollapsibleOptGroups: true,//分组可收
                enableFiltering: true,//可过滤
                includeSelectAllOption: true,//设置可全选
                selectAllText: '全选',
                selectAllValue: -1,
                maxHeight: 300,
                dropUp: isDropUp
            });
            if (vals != null && vals != undefined && vals.length > 0) {
                $.each(vals, function (index, value) {
                    $("#" + sel).multiselect('select', value);
                });
                $("#" + sel).multiselect("refresh");
            }
        }
    });
}

/*复选下拉列表*/
function getMultiSelect(sel, url, vals, value, name, noval) {
    $.ajax({
        type: 'post',
        url: url,
        dataType: 'json',
        success: function (data) {
            var option = '';
            $.each(data, function (i, n) {
                if (noval == null || noval == undefined || noval != n[value]) {
                    option += '<option value="' + n[value] + '">' + n[name] + '</option>';
                }
            });
            $('#' + sel).empty();
            $('#' + sel).append(option);
            $('#' + sel).multiselect({
                buttonWidth: '83%',
                nonSelectedText: "请选择",
                nSelectedText: "已选",
                allSelectedText: '全选',
                delimiterText: '; ',//间隔符
                enableFiltering: true,//可过滤
                includeSelectAllOption: true,//设置可全选
                selectAllText: '全选',
                selectAllValue: -1,
                maxHeight: 400,
                dropUp: true
            });
            if (vals != null && vals != undefined && vals.length > 0) {
                $.each(vals, function (index, val) {
                    $("#" + sel).multiselect('select', val);
                });
                $("#" + sel).multiselect("refresh");
            }
        }
    });
}

/*记录分页页面*/
var knowledge_pagenum=1;

/*单选下拉列表*/
function getDropSelect(sel, url, vals, value, name) {
    $.ajax({
        type: 'post',
        url: url,
        dataType: 'json',
        success: function (data) {
            var option = '';
            option += '<option value="">请选择</option>';
            $.each(data, function (i, n) {
                option += '<option value="' + n[value] + '">' + n[name] + '</option>';
            });
            $('#' + sel).empty();
            $('#' + sel).append(option);
            if (vals != null && vals != undefined && vals.length > 0) {
                $("#" + sel).val(vals);
            }
        }
    });
}


/**
 * 字符串过长省略
 */
function cutString(str, len) {
    //length属性读出来的汉字长度为1
    if (null == str || '' == str || undefined == str) {
        return str;
    }
    if (str.length * 2 <= len) {
        return str;
    }
    var strlen = 0;
    var s = "";
    for (var i = 0; i < str.length; i++) {
        s = s + str.charAt(i);
        if (str.charCodeAt(i) > 128) {
            strlen = strlen + 2;
            if (strlen >= len) {
                return s.substring(0, s.length - 1) + "...";
            }
        } else {
            strlen = strlen + 1;
            if (strlen >= len) {
                return s.substring(0, s.length - 2) + "...";
            }
        }
    }
    return s;
};

/**
 * 室内定位点初始化
 */
function anchorPointInit(paramId) {
    $.ajax({
        type: 'post',
        url: "install_site/list_site_all",
        dataType: 'json',
        error: function (request) {
            goSync("page/500");
        },
        success: function (result) {
            var opts = new Array();
            opts.push('<option value="">请选择安装位置</option>');
            for (var i = 0; i < result.length; i++) {
                var site = result[i];
                opts.push('<option value="' + site.installSiteId + '">' + site.siteName + '</option>');
            }
            $("select[name='installSiteId']").append(opts.join(''));

            $("select[name='installSiteId']").multiselect({
                buttonWidth: '100%',
                nonSelectedText: "请选择安装位置",
                nSelectedText: "已选",
                allSelectedText: '全选',
                delimiterText: '; ',//间隔符
                enableClickableOptGroups: true,//分组可选
                enableCollapsibleOptGroups: true,//分组可收
                enableFiltering: true,//可过滤
                includeSelectAllOption: true,//设置可全选
                selectAllText: '全选',
                selectAllValue: -1,
                maxHeight: 400,
                dropUp: true
            });

            if (!!paramId) {
                $("select[name='installSiteId'] option").each(function (i, content) {
                    if (content.value == paramId) {
                        this.selected = true;
                        return;
                    }
                });
                //设置选中值后，需要刷新select控件
                $("select[name='installSiteId']").multiselect('refresh');
            }
        }
    });
};

/**
 * EXCEL后缀名验证
 * @param val
 */
function excelSuffix(val) {
    if (val) {
        var fileName = $('input[name="filename"]').val();
        var suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length);
        if (suffix == ".xls" || suffix == '.xlsx') {
            var index = val.lastIndexOf("\\");
            val = val.substring(index + 1, val.length);
            $(".wenji").text("当前文件: " + val);
        } else {
            messageError("您选择不是excel文件！");
            return;
        }
    }
};

function currentPage(id) {
    var page = $(id).bootstrapTable("getPage");
    return {pageSize: page.pageSize, pageNo: page.pageNumber};
};

/**
 * 日期加减天数
 * @param day 0:表示当前日期yyyy-MM-dd,1:表示当前日期加上天数，-1：表示当前日期减少天数;
 */
function formatTime(day) {
    var endDate = new Date();
    if (day == 0) {
        return formatDate(endDate);
    }
    var beginDate = new Date(endDate);
    beginDate.setDate(endDate.getDate() + day);
    return formatDate(beginDate);
};

/**
 * 时间范围控制初始化
 */
function timeBetweenInit(callback) {
    $('#dateBegin').datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        minView: "month",
        autoclose: true,
        todayBtn: true,
        todayHighlight: true,
        minuteStep: 2,
        startDate: formatTime(-29),
        endDate: formatTime(0)
    }).on('changeDate', function (ev) {
        var beginDate = ev.date;
        $('#dateEnd').datetimepicker('setStartDate', formatDate(beginDate));
    });

    $('#dateEnd').datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        minView: "month",
        autoclose: true,
        todayBtn: true,
        todayHighlight: true,
        minuteStep: 2,
        startDate: formatTime(-29),
        endDate: formatTime(0)
    }).on('changeDate', function (ev) {
        var endDate = ev.date;
        $('#dateBegin').datetimepicker('setEndDate', formatDate(endDate));
    });

    if (null != callback && undefined != callback && "function" == typeof callback) {
        $('.search').click(function () {
            if ($(this).hasClass('setDateValue')) {
                //当前按钮高亮
                $('.setDateValue').removeClass("bg-orange");
                $('.setDateValue').addClass("bg-gray");
                $(this).addClass("bg-orange");
                $(this).removeClass("bg-gray");
                var interval = $(this).attr("interval");
                $('#beginTimeInput').val(formatTime(parseInt(interval)));
                $('#endTimeInput').val(formatTime(0));

                $('#dateEnd').datetimepicker('setStartDate', formatTime(parseInt(interval)));
                $('#dateBegin').datetimepicker('setEndDate', formatTime(0));
            }
            callback();
        });
    }
};



// $('.main-sidebar li').on('click',function(){
//     setTimeout(function(){
//         if(($('.sidebar').height())>$(window).height()){
//             $('.wrapper').height($('.sidebar').height());
//         }else{
//             $('.wrapper').height('100%');
//         }
//     },700)
//
// })
