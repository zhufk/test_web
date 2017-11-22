
/**
 * 实现div的收缩与展开
 * @param $target 目标对象
 * @param $resetBtn 重置按钮对象
 */
function slideUpDown($target, $resetBtn){
	if ($target.css('display') == 'none') {
		$target.slideDown(200);
	} else {
		$target.slideUp(200);
		//$resetBtn.click(); //重置表单
		var $paramDiv = $('#paramDiv');
		$target.find(':input').tooltip('hide'); //隐藏所有tooltip
		//系统参数配置模块需执行代码
		$paramDiv.siblings('div[id^="paramDiv"]').remove();
		$paramDiv.children().each(function(i){
			if(i > 1){
				$(this).addClass('hide');
			}
		});
	}
}

/**
 * 实现div的展开(报表切换时用）
 * @param $target 目标对象 
 */
function slideUpDownForCharts($target){
	if ($target.css('display') == 'none') {
		$target.slideDown(200);
	} 
}

/**
 * 导入(xml)
 * @param $importFile 文件输入框对象
 * @param url 请求地址
 * @param fileTypes 支持的文件类型数组
 * @param $table 列表
 * @param initTableUrl 加载列表地址
 * @returns
 */
function ajaxFileUpload(url, fileTypes, $table, initTableUrl){
	var $importType = $('#importType'),
		importType = $importType.val(),
		$importFile = $('#importFile'),
		filePath = $.trim($importFile.val());
	if(importType == ''){
		$importType.tooltip('show');
		return;
	}
	if(filePath == ''){
		$importFile.attr('data-original-title', '请选择文件！').tooltip('show');
		return;
	}
	//文件类型校验
	var	suffix = filePath.substring(filePath.lastIndexOf('.') + 1,filePath.length);		
	if($.inArray(suffix, fileTypes) == -1){
		$importFile.attr('data-original-title', '文件类型错误！').tooltip('show');
		return;
	}
	$.ajaxFileUpload({
        url: url,
        secureuri: false, 
        fileElementId: $importFile.attr('id'), //多个id时，用数组，如： ["gridDoc","caseDoc"];  
        dataType: 'text',
        success: function (result){   
        	alert(result);
        	$table.bootstrapTable('refresh', {silent: true, url: initTableUrl}); //刷新列表
        }, 
        error: function(){
        	alert('导入失败！');
        }
     });
}

//导入(excel)
function excelImport(url, fileTypes, $table, initTableUrl){
	var $importType = $('#importType'),
		importType = $importType.val(),
		$importFile = $('#importFile'),
		filePath = $.trim($('#importFile').val());
	if(importType == ''){
		$importType.tooltip('show');
		return;
	}
	if(filePath == ''){
		$importFile.attr('data-original-title', '请选择文件！').tooltip('show');
		return;
	}
	//文件类型校验
	var	suffix = filePath.substring(filePath.lastIndexOf('.') + 1,filePath.length);		
	if($.inArray(suffix,fileTypes) == -1){
		$importFile.attr('data-original-title', '文件类型错误！').tooltip('show');
		return;
	}
	$.ajaxFileUpload({
        url: url,
        secureuri: false, 
        fileElementId: $importFile.attr('id'), //多个id时，用数组，如： ["gridDoc","caseDoc"];  
        dataType: 'text',
        success: function (data){   
        	var dataJson = $.parseJSON(data);
        	if(dataJson.fileType == ''){
        		alert('文件类型错误！');
        		return;
        	}    
        	var errorInfo = dataJson.dataErrorInfo;
        	if(errorInfo != ''){
    			if(errorInfo.isCorrect == false){
    				alert('第' + errorInfo.sheet + '个工作表的总列数不能小于' + errorInfo.count + '！');
    			}else{
    				alert('第' + errorInfo.sheet + '个工作表第' + errorInfo.row + '行第' 
					  	   + errorInfo.column + '列数据' + errorInfo.errorType + '！');
    			}
    		}else{
    			alert('共有' + dataJson.count + '条数据成功导入！');
    			slideUpDown($('#import'));
    			$table.bootstrapTable('refresh', {silent: true, url: initTableUrl}); //刷新列表
    		}
        }, 
        error: function(){
        	alert('导入失败！');
        }
     });
}	

//导入(excel)
function excelImportSecond(url, fileTypes, $table, initTableUrl){
	var $importType = $('#importType'),
		importType = $importType.val(),
		$importFile = $('#importFile'),
		filePath = $.trim($('#importFile').val());
	if(importType == ''){
		$importType.tooltip('show');
		return;
	}
	if(filePath == ''){
		$importFile.attr('data-original-title', '请选择文件！').tooltip('show');
		return;
	}
	//文件类型校验
	var	suffix = filePath.substring(filePath.lastIndexOf('.') + 1,filePath.length);		
	if($.inArray(suffix,fileTypes) == -1){
		$importFile.attr('data-original-title', '文件类型错误！').tooltip('show');
		return;
	}
	$.ajaxFileUpload({
        url: url,
        secureuri: false, 
        fileElementId: $importFile.attr('id'), //多个id时，用数组，如： ["gridDoc","caseDoc"];  
        dataType: 'text',
        success: function (data){   
        	var dataJson = $.parseJSON(data);
        	if(dataJson.fileType == ''){
        		alert('文件类型错误！');
        		return;
        	}    
        	var errorInfo = dataJson.dataErrorInfo;
        	if(errorInfo != ''){
    			if(errorInfo.isCorrect == false){
    				alert('第' + errorInfo.sheet + '个工作表的总列数不能小于' + errorInfo.count + '！');
    			}
    		}else{
    			alert('共有' + dataJson.count + '条数据成功导入！');
    			slideUpDown($('#import'));
    			$table.bootstrapTable('refresh', {silent: true, url: initTableUrl}); //刷新列表
    		}
        }, 
        error: function(){
        	alert('导入失败！');
        }
     });
}	

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
 * 设置默认类型，1代表趋势，2代表统计
 * @param type
 */
function setStatisBtnValue(type){
	if(type == 1){
		$("#statisBtnHead").empty().data('charts_type', {"chartsType": 1})
		 .append('<i class="fa fa-line-chart"></i> 趋势');
		return 2;
	}else if(type == 2){
		$("#statisBtnHead").empty().data('charts_type', {"chartsType": 2})
		 .append('<i class="fa fa-line-chart"></i> 统计');
		return 1;
	}
}


/**
 * 趋势和统计按钮的来回切换,返回1代表当前统计类型为趋势，2代表统计
 * @$this 切换按钮对象
 */
function toggleDateDiv($this){
	if($.trim($this.text()) == '统计'){
		$this.empty()
			 .data('charts_type', {"chartsType": 1})
			 .data('cur_charts_type', 2)
			 .append('<i class="fa fa-line-chart"></i> 趋势');
	}else{
		$this.empty()
			 .data('charts_type', {"chartsType": 2})
			 .data('cur_charts_type', 1)
			 .append('<i class="fa fa-bar-chart"></i> 统计');
	}
}

/**
 * 设置报表默认查询时间区间
 * @param url
 * @param chartsType 报表类型(1-趋势、2-统计)
 * @param statType 统计类型
 */
function setChartsTimePeriod(url, statType, chartsType){
	var timePeriod = getDataBySync(url, {"chartsType": chartsType, "statType": statType});
	$('#startTime').val(timePeriod.beginTime);
	$('#endTime').val(timePeriod.endTime);
}

/**
 * 验证报表查询时间（适合不同类型报表统计周期一样的情况）
 * @param url 
 * @param chartsType 报表类型(1-趋势、2-统计)
 * @returns
 */
function validateTimePeriod(url, chartsType){
	var $startTime = $('#startTime'),
		$endTime = $('#endTime'),
		startTime = $.trim($('#startTime').val()),
		endTime = $.trim($('#endTime').val());
	if(startTime == ''){
		$startTime.tooltip('show');
		return false;
	}
	if(endTime == ''){
		$endTime.tooltip('show');
		return false;
	}
	var statTypes = '',
		$statType = $('#statType'),
		$options = $statType.children();
	$options.each(function(i){
		statTypes += $(this).val() + ',';
	});
	statTypes = statTypes.substring(0, statTypes.length - 1);
	var timePeriod = getDataBySync(url, {"chartsType": chartsType, "statType": $statType.val(), "statTypes": statTypes, "beginTime": startTime, "endTime": endTime});
	var max = timePeriod.maxDiffTime;
	if(max != null){
		alert('时间不能超过' + max + '天！');
		return false;
	}
	$options.each(function(i){
		var $this = $(this);
		if($this.val() == timePeriod.statType){
			$this.attr('selected', 'selected');
			return false;
		}
	});
	statTypeDataChange($statType, $startTime, $endTime);
	return true;
}

/**
 * 验证报表查询时间（适合不同类型报表统计周期不一样的情况）
 * @param url
 * @param chartsType
 * @returns
 */
function validateTimePeriodSecond(url, chartsType){
	var $startTime = $('#startTime'),
		$endTime = $('#endTime'),
		startTime = $.trim($('#startTime').val()),
		endTime = $.trim($('#endTime').val());
	if(startTime == ''){
		$startTime.tooltip('show');
		return {"result": false};
	}
	if(endTime == ''){
		$endTime.tooltip('show');
		return {"result": false};
	}
	var statTypes = '',
		$statType = $('#statType');
	$statType.children().each(function(i){
		statTypes += $(this).val() + ',';
	});
	statTypes = statTypes.substring(0, statTypes.length - 1);
	var timePeriod = getDataBySync(url, {"chartsType": chartsType, "statType": $statType.val(), "statTypes": statTypes, "beginTime": startTime, "endTime": endTime});
	var max = timePeriod.maxDiffTime;
	if(max != null){
		alert('时间不能超过' + max + '天！');
		return {"result": false};
	}
	//alert(timePeriod.statType);
	return {"result": true, "statType": timePeriod.statType};
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