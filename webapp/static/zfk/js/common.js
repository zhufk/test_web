/**
 * 日期格式化(修改——转换日期格式(时间戳转换为datetime格式))
 */
function dateFormat(date) {
	if(date instanceof Date){
		var Y = date.getFullYear() + '-';
		var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date
				.getMonth() + 1)
				+ '-';
		var D = (date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + ' ';
		var h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours())
				+ ':';
		var m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date
				.getMinutes())
				+ ':';
		var s = (date.getSeconds() < 10 ? '0' + date.getSeconds() : date
				.getSeconds());
		return (Y + M + D + h + m + s);
	}
	return date;
}

/**
 * 表单提交
 * 
 * @param url
 * @param formName
 * @param callback
 */
function formSubmit(formName, url, callback) {

	// 提交参数
	var params = $('#' + formName).serialize();

	if (params == '' || params == undefined) {
		return;
	}

	// 提交参数字符串转化为参数数组
	var paramArr = params.split("&");
	for (var i = 0; i < paramArr.length; i++) {
		var keyValue = paramArr[i].split("=");
		// 参数值为空
		if (keyValue[1] == '') {
			// 删除元素
			paramArr.splice(i, 1);
			// 因为删除了一个元素所以这里需要i-1
			i--;
		}
	}

	// 如果paramArr为空，则不传任何参数
	// 如果paramArr不为空，则将数组用&连接成字符串
	if (paramArr.lenght == 0) {
		return;
	} else {
		params = paramArr.join("&");
	}

	// 显示loadding
	layer.load(1, {
		shade : [ 0.1, '#fff' ],
		time : 2000
	});

	$.ajax({
		type : "POST",
		url : url,
		data : params,
		async : true,
		error : function(request) {
			goSync("error/500");
		},
		success : function(data) {
			if (data.success) {
				messageSucces(data.message, function() {
					callback();
				});
			} else {
				messageError(data.message);
			}
		}
	});
};

/**
 * 表单提交
 * 
 * @param params
 *            请求参数
 * @param url
 *            请求URL
 * @param callback
 *            成功回调方法
 */
function formSubmitParam(params, url, callback) {
	// 显示loadding
	layer.load(1, {
		shade : [ 0.1, '#fff' ],
		time : 2000
	});

	$.ajax({
		type : "POST",
		url : url,
		data : params,
		contentType : 'application/json;charset=utf-8',
		async : true,
		error : function(request) {
			goSync("error/500");
		},
		success : function(data) {
			if (data.success) {
				messageSucces(data.message, function() {
					callback();
				});
			} else {
				messageError(data.message);
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
	// Metronic.startPageLoading();//显示loadding
	/*
	 * 去掉所有的前后空格
	 */
	$('#' + formId + ' input').each(function() {
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
		type : "POST",
		url : url,
		data : finalParam,
		async : true,
		error : function(request) {
			goSync("error/500");
		},
		success : function(data) {
			initPage(data);
		}
	});
};


/**
 * 初始化页面数据
 * 
 * @param data
 */
function initPage(data) {
	// Metronic.stopPageLoading();//隐藏loadding
	if (isLogin(data)) {
		$('.page-content-wrapper').html(data);
		// Layout.fixContentHeight(); // fix content height
		// Metronic.initAjax(); // initialize core stuff
		// Metronic.scrollTop();//加载完成回到顶部
	}
};

function isLogin(data) {
	if (typeof (data) == "string" && data.indexOf('<meta id="login">') >= 0) {
		var title = '注意';
		var content = '<p>您已退出系统，是否重新登录？</p>';
		showModal("logoutModal", title, content);
		return false;
	}

	return true;
};

/**
 * get提交，用户页面跳转
 * 
 * @param url
 *            必选，提交地址
 */
function go(url) {
	// Metronic.startPageLoading();//显示loadding
	$.ajax({
		type : "GET",
		url : url,
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		cache : false,
		error : function(request) {
			goSync("error/500");
		},
		success : function(data) {
			initPage(data);
		}
	});
};

/**
 * 用户页面跳转，带回调方法
 * 
 * @param url
 * @param callback
 */
function goUrlCall(url, callback) {
	$.ajax({
		type : "POST",
		url : url,
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		cache : false,
		error : function(request) {
			goSync("error/500");
		},
		success : function(data) {
			if (data.success) {
				messageSucces(data.message, function() {
					callback();
				});
			} else {
				messageError(data.message);
			}
		}
	});
};

/**
 * get提交，用户页面跳转 同步提交
 * 
 * @param url
 *            必选，提交地址
 */
function goSync(url) {
	$.ajax({
		type : "GET",
		url : url,
		cache : false,
		async : false,
		error : function(request) {
			goSync("error/500");
		},
		success : function(data) {
			initPage(data);
		}
	});
};

/**
 * 同步请获取json数据
 * @param url 请求地址
 */
function getDataBySync(url, data){
	var dataResult = '';
	$.ajax({
		url: url,
		type: 'POST',
		data: data,
		async: false,
		dataType: 'json', 
		success: function(result){
			dataResult = result;
		}
	});
	return dataResult;
}


/**
 * 初始化异常显示层
 * 
 * @param errorStatusId
 *            异常状态
 * @param fromId
 *            表单
 */
function initNoticeMessage(fromId) {
	if ($("#returnMessaStatus").val()) {
		var error1 = $('.alert-danger', $('#' + fromId));
		error1.show();
		// Metronic.scrollTo(error1, -200);
	}
};

function logout() {
	$(window).unbind('beforeunload');
	window.location.href = "logout";
};

/*
 * 分页跳转
 */
function pageJump(url, formId, action) {
	// 页面进来不查询数据时也就不存在分页了
	if ($('input[name="pageNo"]').length != 0
			|| $('input[name="totalPageNo"]').length != 0) {
		var pageNo = $('input[name="pageNo"]').val() - 0;// 获得当前页
		var totalPageNo = $('input[name="totalPageNo"]').val() - 0;// 获得总页码
		if (isNaN(pageNo) || isNaN(totalPageNo)) {
			return false;
		}
		if (action == 'next') {
			if (totalPageNo == pageNo) {
				alert("已经是最后一页了~"); // 最后一页不能查询下一页
				return false;
			}
			pageNo += 1;
		} else if (action == 'previous') {
			if (pageNo == 1) {
				alert("已经是第一页了~");// 第一页不能查询上一页
				return false;
			}
			pageNo -= 1;
		} else if (action == 'go') {
			pageNo = $('input[name="expectPageNo"]').val() - 0;
			if (isNaN(pageNo)) {
				alert("请输入数字~");// 请输入数字;
				return false;
			} else if (pageNo < 1 || pageNo > totalPageNo) {
				alert("请输入合适的页码~");// 请输入合适的页码;
				return false;
			}

		} else {
			pageNo = 1;
		}
		$('input[name="pageNo"]').val(pageNo);
	}

	subForm(url, formId);// 提交表单
};

/*
 * 提示语
 */

// 1.深蓝提示框
function messageAlert(str) {
	layer.alert(str, {
		skin : 'layui-layer-lan',
		closeBtn : 0,
		anim : 4
	// 动画类型
	});
};

/**
 * 确认提示框
 * 
 * @param str
 *            提示语
 * @param meth
 *            确认方法
 */
function messageConfirm(str, meth) {
	layer.confirm(str, {
		icon : 3,
		title : '提示'
	}, function(index) {
		meth();
		layer.close(index);
	}, function(index) {
		layer.close(index);
	});

};

// 3.消息成功提示框
function messageSucces(str) {
	layer.msg(str.message, {
		icon : 1,
		time : 2000
	});
};

function messageSucces(str, callback) {
	layer.msg(str, {
		icon : 1,
		time : 2000
	}, function() {
		callback();
	});
};

// 4.消息失败提示框
function messageError(str) {
	layer.msg(str, {
		icon : 2
	});
};

function messageError(str, callback) {
	layer.msg(str, {
		icon : 2
	}, function() {
		callback();
	});
}

/* 查询作用范围 */
function getActionScopes(sel, vals) {
	$.ajax({
		type : 'post',
		url : "message_publish/list_actionScopes/123",
		dataType : 'json',
		success : function(result) {
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
				buttonWidth : '100%',
				nonSelectedText : "请选择作用范围",
				nSelectedText : "已选",
				allSelectedText : '全选',
				delimiterText : '; ',// 间隔符
				enableClickableOptGroups : true,// 分组可选
				enableCollapsibleOptGroups : true,// 分组可收
				enableFiltering : true,// 可过滤
				includeSelectAllOption : true,// 设置可全选
				selectAllText : '全选',
				selectAllValue : -1,
				maxHeight : 400,
				dropUp : true
			});
			if (vals != null && vals != undefined && vals.length > 0) {
				$.each(vals, function(index, value) {
					$("#" + sel).multiselect('select', value);
				});
				$("#" + sel).multiselect("refresh");
			}
		}
	});
}

/* 复选下拉列表 */
function getMultiSelect(sel, url, vals, value, name) {
	$.ajax({
		type : 'post',
		url : url,
		dataType : 'json',
		success : function(data) {
			var option = '';
			$.each(data, function(i, n) {
				option += '<option value="' + n[value] + '">' + n[name]
						+ '</option>';
			});
			$('#' + sel).empty();
			$('#' + sel).append(option);
			$('#' + sel).multiselect({
				buttonWidth : '100%',
				nonSelectedText : "请选择",
				nSelectedText : "已选",
				allSelectedText : '全选',
				delimiterText : '; ',// 间隔符
				enableFiltering : true,// 可过滤
				includeSelectAllOption : true,// 设置可全选
				selectAllText : '全选',
				selectAllValue : -1,
				maxHeight : 400,
				dropUp : true
			});
			if (vals != null && vals != undefined && vals.length > 0) {
				$.each(vals, function(index, val) {
					$("#" + sel).multiselect('select', val);
				});
				$("#" + sel).multiselect("refresh");
			}
		}
	});
}

/* 单选下拉列表 */
function getDropSelect(sel, url, vals, value, name) {
	$.ajax({
		type : 'post',
		url : url,
		dataType : 'json',
		success : function(data) {
			var option = '';
			$.each(data, function(i, n) {
				option += '<option value="' + n[value] + '">' + n[name]
						+ '</option>';
			});
			$('#' + sel).empty();
			$('#' + sel).append(option);
			if (vals != null && vals != undefined) {
				$("#" + sel).val(vals);
			}
		}
	});
}

/**
 * IP验证
 * @param ip
 * @returns {Boolean}
 */
function isIP(ip){  
    var reSpaceCheck = /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/;  
    if (reSpaceCheck.test(ip)){  
        ip.match(reSpaceCheck); 
        var result = RegExp.$1<=255&&RegExp.$1>=0&&RegExp.$2<=255&&RegExp.$2>=0&&RegExp.$3<=255&&RegExp.$3>=0&&RegExp.$4<=255&&RegExp.$4>=0;
        return result;
    }else{
    	return false;
    }
}  

/**
 * IP转换成十进制值
 * @param ip
 * @returns {Number}
 */
function ip2num(ip){ 
    var num = 0;
    ip = ip.split(".");
    num = Number(ip[0]) * 256 * 256 * 256 + Number(ip[1]) * 256 * 256 + Number(ip[2]) * 256 + Number(ip[3]);
    num = num >>> 0;
    return num;
}