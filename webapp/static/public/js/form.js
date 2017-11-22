

$(function(){
	initTooltips();
	inputBlurEvent();
	//$('[data-toggle="multiselect"]').multiselectExtend();
});

function initTooltips(){
	//设置当前页Tooltips选项
	var options = {
			animation: true,
			trigger: 'manual' //手动触发
		};
	$('[data-toggle="tooltips"]').tooltip(options);
}

/**
 * 加载下拉框
 * selId 下拉框id
 * url 请求地址
 * flag 是否包含“请选择”选项
 * val 默认选中项值
 */
function loadSel(selId, url, flag, val){
	$.ajax({
		url: url,
		type: 'POST',
		dataType: 'json', 
		success: function(data){
			var $selId = $('#' + selId),
				option = '';
			$selId.children().remove();
			if(flag){
				option = '<option value="">请选择</option>';
			}
			$.each(data, function(i, n){
				if(val == n.value){
					option += '<option selected="selected" value="' + n.key + '">' + n.value+ '</option>';
				}else{
					option += '<option value="' + n.key + '">' + n.value + '</option>';
				}
			});
			$selId.append(option);
		}
	});
}

/**
 * 加载下拉框
 * selId 下拉框id
 * url 请求地址
 * flag 是否包含“请选择”选项
 * val 默认选中项值
 */
function loadSelTwo(selId, url, flag, val){
	$.ajax({
		url: url,
		type: 'POST',
		dataType: 'json', 
		success: function(data){
			var $selId = $('#' + selId),
				option = '';
			$selId.children().remove();
			if(flag){
				option = '<option value="">请选择</option>';
			}
			$.each(data, function(i, n){
				if(val == n.key){
					option += '<option selected="selected" value="' + n.key + '">' + n.value+ '</option>';
				}else{
					option += '<option value="' + n.key + '">' + n.value + '</option>';
				}
			});
			$selId.append(option);
		}
	});
}

/**
 * 加载下拉框
 * @param selId 下拉框id
 * @param data json数组
 * @param flag 是否包含“请选择”选项
 * @param val 默认选中项值
 */
function loadSelThree(selId, data, flag, val){
	var $selId = $('#' + selId),
		option = '';
	$selId.children().remove();
	if(flag){
		option = '<option value="">请选择</option>';
	}
	$.each(data, function(i, n){
		if(val == n.key){
			option += '<option selected="selected" value="' + n.key + '">' + n.value+ '</option>';
		}else{
			option += '<option value="' + n.key + '">' + n.value + '</option>';
		}
	});
	$selId.append(option);
}

/**
 * 加载下拉框
 * selId 下拉框id
 * url 请求地址
 */
function loadSelFour(selId, url, data){
	$.ajax({
		url: url,
		type: 'POST',
		data: data,
		dataType: 'json', 
		success: function(result){
			var $selId = $('#' + selId),
				option = '';
			$selId.children().remove();
			$.each(result.resultData, function(i, n){
				option += '<option value="' + n.ColValue + '">' + n.ColText + '</option>';
			});
			$selId.append(option);
		}
	});
}

/**
 * 加载下拉框
 * selName 下拉框name
 * url 请求地址
 * flag 是否包含“请选择”选项
 */
function loadSels(selName, url, flag){
	$.ajax({
		url: url,
		type: 'POST',
		dataType: 'json', 
		success: function(data){
			var $sels = $('select[name*="' + selName + '"]');
			$sels.each(function(i){
				var $this = $(this),
					option = '';
				$this.children().remove();
				if(flag){
					option = '<option value="">请选择</option>';
				}
				$.each(data, function(i, n){
					option += '<option value="' + n.key + '">' + n.value + '</option>';
				});
				$this.append(option);
			});
		}
	});
}

/**
 * 加载下拉框
 * selName 下拉框name
 * url 请求地址
 * flag 是否包含“请选择”选项
 */
function loadSelsTwo(selName, url, flag, async, val){
	$.ajax({
		url: url,
		type: 'POST',
		async: async,
		dataType: 'json', 
		success: function(data){
			var $sels = $('select[name*="' + selName + '"]');
			$sels.each(function(i){
				var $this = $(this),
					option = '';
				$this.children().remove();
				if(flag){
					option = '<option value="">请选择</option>';
				}
				$.each(data, function(i, n){
					if(val == n.key){
						option += '<option selected="selected" value="' + n.key + '">' + n.value+ '</option>';
					}else{
						option += '<option value="' + n.key + '">' + n.value + '</option>';
					}
				});
				$this.append(option);
			});
		}
	});
}

//同步或异步加载
function loadSelFive(selId, url, flag, async, val){
	$.ajax({
		url: url,
		type: 'POST',
		dataType: 'json', 
		async: async,
		success: function(data){
			var $selId = $('#' + selId),
				option = '';
			$selId.children().remove();
			if(flag){
				option = '<option value="">请选择</option>';
			}
			$.each(data, function(i, n){
				if(val == n.value){
					option += '<option selected="selected" value="' + n.key + '">' + n.value+ '</option>';
				}else{
					option += '<option value="' + n.key + '">' + n.value + '</option>';
				}
			});
			$selId.append(option);
		}
	});
}

//同步或异步加载
function loadSelEven(selId, url, data, flag, async, val){
	$.ajax({
		url: url,
		type: 'POST',
		dataType: 'json', 
		async: async,
		data: data,
		success: function(data){
			var $selId = $('#' + selId),
				option = '';
			$selId.children().remove();
			if(flag){
				option = '<option value="">请选择</option>';
			}
			$.each(data, function(i, n){
				if(val == n.value){
					option += '<option selected="selected" value="' + n.key + '">' + n.value+ '</option>';
				}else{
					option += '<option value="' + n.key + '">' + n.value + '</option>';
				}
			});
			$selId.append(option);
		}
	});
}

//同步或异步加载
function loadSelSix(selId, url, data, async){
	$.ajax({
		url: url,
		type: 'POST',
		data: data,
		dataType: 'json', 
		async: async,
		success: function(result){
			var $selId = $('#' + selId),
				option = '';
			$selId.children().remove();
			$.each(result.resultData, function(i, n){
				option += '<option value="' + n.ColValue + '">' + n.ColText + '</option>';
			});
			$selId.append(option);
		}
	});
}

/**
 * 加载下拉框
 * @param selId 下拉框id
 * @param data json数组
 * @param flag 是否包含“请选择”选项
 * @param val 默认选中项值
 */
function loadSelSeven(selId, data, flag, val){
	var $selId = $('#' + selId),
		option = '';
	$selId.children().remove();
	if(flag){
		option = '<option value="">请选择</option>';
	}
	$.each(data, function(i, n){
		if(val == n.value){
			option += '<option selected="selected" value="' + n.key + '">' + n.value+ '</option>';
		}else{
			option += '<option value="' + n.key + '">' + n.value + '</option>';
		}
	});
	$selId.append(option);
}

function loadSelTen(selId, data, flag){
	var $selId = $('#' + selId),
		option = '';
	$selId.children().remove();
	if(flag){
		option = '<option value="">请选择</option>';
	}
	$.each(data, function(i, n){
		option += '<option value="' + n.key + '">' + n.value + '</option>';
	});
	$selId.append(option);
}

//同步或异步加载
function loadSelEight(selId, url, data, async){
	$.ajax({
		url: url,
		type: 'POST',
		data: data,
		dataType: 'json', 
		async: async,
		success: function(result){
			var $selId = $('#' + selId),
				option = '';
			$selId.children().remove();
			$.each(result, function(i, n){
				option += '<option value="' + n.key + '">' + n.value + '</option>';
			});
			$selId.append(option);
		}
	});
}


//表单元素focus事件(当input/textarea/select/button得到焦点隐藏tooltip)
function inputBlurEvent(){
	$(':input').on('focus', function(){
		$(this).tooltip('hide');
	});
}

/**
 * 表单字段去重校验
 * @param url 校验请求地址
 * @param data 请求参数
 * @returns {Boolean} 返回值，true：不重复，false:重复
 */
function repeatValidation(url, data){
	var flag = true;
	$.ajax({
		url: url,
		type: 'POST',
		async: false,
		data: data,
		success: function(data){
			if(data > 0){
				flag = false;
			}
		}
	});
	return flag;
}

/**
 * 不同统计粒度下的时间格式 1(分钟)，2(小时),3(日),4(周),5(月)
 */
function statTypeDataChange($statType, $startTime, $endTime) {
	var statType = $statType.val();
	var startTime = $startTime.val();
	var endTime = $endTime.val();
	switch (statType) {
	case '1':
		dateFmt = 'yyyy-MM-dd HH:mm';
		startTimeResult = dateFormat(startTime, dateFmt);
		endTimeResult = dateFormat(endTime, dateFmt);
		break;
	case '2':
		dateFmt = 'yyyy-MM-dd HH';
		startTimeResult = dateFormat(startTime, dateFmt);
		endTimeResult = dateFormat(endTime, dateFmt);
		break;
	case '3':
	case '4':
		dateFmt = 'yyyy-MM-dd';
		startTimeResult = dateFormat(startTime, dateFmt);
		endTimeResult = dateFormat(endTime, dateFmt);
		break;
	case '5':
		dateFmt = 'yyyy-MM';
		startTimeResult = dateFormat(startTime, dateFmt);
		endTimeResult = dateFormat(endTime, dateFmt);
	}
	$startTime.val(startTimeResult);
	$endTime.val(endTimeResult);
}

/**
 * 格式化日期
 * @param cellValue 日期字符串
 * @param fmt 日期格式
 * @returns {String}
 */
function dateFormat(cellValue, fmt) {
	if (!$.trim(cellValue)) {
		return '';
	}
	var dateValue = cellValue.split(' ');
	var ymd = dateValue[0].split('-');
	var hmm = [ '00', '00' ];
	if (dateValue[1]) {
		hmm = dateValue[1].split(':');
	}
	var year = ymd[0]; // 年
	var m = ymd[1]; // 月份
	var d = m; // 日
	if (ymd[2]) {
		d = ymd[2];
	}
	var h = hmm[0]; // 小时
	var min = h; // 分
	if (hmm[1]) {
		min = hmm[1];
	}
	switch (fmt) {
	case 'yyyy-MM':
		return year + '-' + m;
	case 'yyyy-MM-dd':
		return year + '-' + m + '-' + d;
	case 'yyyy-MM-dd HH':
		return year + '-' + m + '-' + d + ' ' + h;
	}
	return year + '-' + m + '-' + d + ' ' + h + ':' + min;
}


//加载国家，省份，地市
function loadPosition($select, $url, postData, flag){
	$.ajax({
     type: 'POST',	            
     dataType: 'json',
     url: $url,
     data: postData,
     success: function(result) {
		var optionHtml = [];
		if(flag){
			optionHtml.push('<option value="">请选择</option>');
		}
		$.each(result, function(i, n){
			optionHtml.push('<option value="' + n.key + '">' + n.value + '</option>');
		});
		$select.empty().append(optionHtml.join(""));
     }
	});	
}

function loadSelNine(selId, url, data, flag, val){
	$.ajax({
		url: url,
		type: 'POST',
		dataType: 'json', 
		data: data,
		success: function(data){
			var $selId = $('#' + selId),
				option = '';
			$selId.children().remove();
			if(flag){
				option = '<option value="">请选择</option>';
			}
			$.each(data, function(i, n){
				if(val == n.key){
					option += '<option selected="selected" value="' + n.key + '">' + n.value+ '</option>';
				}else{
					option += '<option value="' + n.key + '">' + n.value + '</option>';
				}
			});
			$selId.append(option);
		}
	});
}

//显示选择列模态框
function selColForExport(url){
	$('#exportHead li').on('click', function(){
		var data = getDataBySync(url),
			$modal = $('#columnSel'),
			checkbox = '',
			$modalForm =  $modal.find('form');
		$modalForm.children().not('#exportTypeHide').remove();
		$('#exportTypeHide').val($(this).data('export_type'));
		$.each(data, function(i, n){
			if(!(!n.switchable && !n.visible)){
				var checked = n.visible ? 'checked="checked"' : '';
				checkbox += '<label class="checkbox-inline">'
					+ '<input name="selColum" type="checkbox" ' + checked + '" value="' + n.field + '"> ' + n.title
					+ '</label>';
			}
		});
		$modalForm.append(checkbox);
		checkboxToggle($('#checkedAll'), $('input[name="selColum"]'));
		$modal.modal('show');
	});
}

//获取选中的列信息
function getSelectedCol(){
	var data = {},
		fields = '',
		headers = '';
	$('#columnSel form').find('input:checked').each(function(i){
		var $this = $(this);
		fields += $this.val() + ',';
		headers += $.trim($this.parent().text()) + ',';
	});
	data.fields = fields != '' ? fields.substring(0, fields.length - 1) : fields;
	data.headers = headers != '' ? headers.substring(0, headers.length - 1) : headers;
	data.exportType = $('#exportTypeHide').val();
	return data;
}

function checkboxToggle($checkAll, $checkboxs){
	$checkAll.prop('checked', $checkboxs.length == $checkboxs.filter(':checked').length);
	$checkAll.on('click', function() {
		$checkboxs.prop('checked', this.checked);
    });
	$checkboxs.on('click', function() {
		$checkAll.prop('checked', $checkboxs.length == $checkboxs.filter(':checked').length);
	});
}

/**
 * 报表导出
 * @param url 导出请求地址
 * @param getColsURL 获取列请求地址
 * @param data 参数
 */
function exportCharts(url, getColsURL){
	selColForExport(getColsURL);
	$('#exportBtn').on('click', function(){
		var cols = getSelectedCol();
		if(cols.fields == ''){
			alert('请选择列！');
			return;
		}
		$('#columnSel').modal('hide'); //隐藏模态框
		$('#chartsExport').remove();
		var form = '<form class="hide" id="chartsExport">';
		var dataURLs = '';
		$.each(allChart, function(i, n){ //支持多报表导出
			dataURLs += n.getDataURL().split(',')[1] + ",";
		});
		if(dataURLs) dataURLs = dataURLs.substring(0, dataURLs.length - 1);
		var data = $.extend({}, $('#searchForm').formToJSON(), cols, {"chartsType": $('#statisBtnHead').data('cur_charts_type')}, {"dataURLs": dataURLs});
		$.each(data, function(i, n){
			form += '<input name="' + i + '" value="' + n + '" />';
		});
		form += '</form>';
		$('body').append(form);
		$('#chartsExport').attr('action', url).attr('method', 'post').submit();
	});
}

/**
 * 给echarts的图标展现添加缓存效果
 * @param myChart
 */
function initChart(myChart){
	myChart.showLoading({
	    //text: '正在努力的读取数据中...',    //loading话术
	    effect : 'ring',
	    textStyle : {
	        fontSize : 10
	    }
	});
}
