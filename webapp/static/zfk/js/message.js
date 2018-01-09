/*! message.js
 * ================
 * 主要包括信息发布相关功能模块JS代码
 *
 *
 * @Author  朱富昆
 * @Date 2017-11-07
 * @version 1.0.0
 */

var MESSAGE = {
    param: {
        tableId: "",
        url: "",
        queryParams: null,
        uniqueId: "",
        columns: []
    },
    /**
     * 初始化方法
     */
    init: function (param) {
        MESSAGE.tableLoad(param);
        MESSAGE.messagePublish.timeBetweenInit();
    },
    /**
     * Table宣染
     */
    tableLoad: function (param) {
        $(param.tableId).bootstrapTable("destroy").bootstrapTable({
            method: 'post',
            url: param.url,
            queryParams: param.queryParams,
            contentType: "application/json; charset=UTF-8",
            cache: false,
            striped: true,
            pagination: true,
            pageNumber: (typeof(param.pageNumber) == 'undefined' ? 1 : param.pageNumber),
            pageSize: (typeof(param.pageSize) == 'undefined' ? 15 : param.pageSize),
            pageList: [15, 20, 30],
            sidePagination: 'server',
            sortable: false, //是否启用排序
            minimumCountColumns: 1,
            clickToSelect: true,
            singleSelect: (typeof(param.singleSelect) == 'undefined' ? false : param.singleSelect),
            paginationFirstText: "首页",
            paginationPreText: '上一页',
            paginationNextText: '下一页',
            paginationLastText: '最后一页',
            uniqueId: param.uniqueId,
            columns: param.columns
        });
    },
    /**
     * 日期格式化(修改——转换日期格式(时间戳转换为datetime格式))
     */
    changeDateFormat: function (value, row, index) {
        var date = new Date(value);
        return date.format("yyyy-MM-dd HH:mm:ss");
    },

    /**
     * 信息发布功能模块
     */
    messagePublish: {
        messageContentFormatter: function (value, row, index) {
            var temp = document.createElement("div");
            temp.innerHTML = value;
            var output = temp.innerText || temp.textContent;
            return output;
        },
        actionScopesFormatter: function (value, row, index) {
            var scopeNameArr = [];
            for (var i = 0; i < value.length; i++) {
                if (value[i].scopeId == null || value[i].scopeId == 'null') {
                    return "全部范围";
                }
                if (value[i].scopeName != null && value[i].scopeName != 'null') {
                    scopeNameArr.push(value[i].scopeName);
                }
            }
            return scopeNameArr.join("；");
        },
        publishStatusFormatter: function (value, row, index) {
            if (row['publishStatus'] == 0) {
                return '<span class="label label-danger">未发布</span>';
            }
            if (row['publishStatus'] == 1) {
                return '<span class="label label-success">发布中</span>';
            }
            if (row['publishStatus'] == 2) {
                return '<span class="label" style="background-color: #999 !important;">已过期</span>';
            }
            return value;
        },
        firstSortFormatter: function (value, row, index) {
            if (row['firstSort'] == 0) {
                return '<span class="label label-default">否</span>';
            }
            if (row['firstSort'] == 1) {
                return '<span class="label label-success">是</span>';
            }
            return value;
        },
        operateFormatter: function (value, row, index) {
            var messageId = row.messageId;
            return '<button type="button" onclick="javascript:MESSAGE.messagePublish.showMessage(\''
                + messageId
                + '\');" class="btn btn-default btn-sm workway-disabled"><i class="fa fa-search-minus"></i> 详情</button> '
                + '<button type="button" onclick="javascript:MESSAGE.messagePublish.editMessage(\''
                + messageId
                + '\');" class="btn btn-default btn-sm workway-disabled"><i class="fa fa-edit"></i> 修改</button> ';
        },
        // 查询
        search: function () {
            if ('' != $('#beginTimeInput').val() && '' != $('#endTimeInput').val()) {
                var data1 = Date.parse($('#beginTimeInput').val());
                var data2 = Date.parse($('#endTimeInput').val());
                var datadiff = data2 - data1;
                if (datadiff < 0) {
                    messageError("结束时间不能小于开始时间！");
                    return;
                }
            }
            MESSAGE.init(MESSAGE.param);
        },
        // 将查询条件，列表的相关分页，排序等信息作为查询参数封装
        queryParams: function (params) {
            var postData = new Object();
            postData.offset = params.offset;
            postData.limit = params.limit;
            postData.messageTitle = $('#messageTitleInput').val();
            //postData.scopeName = $('#scopeNameInput').val();
            postData.scopeIds = [];
            var selectedOpts = $('#actionScopesSel').find("option:selected").each(
                function () {
                    postData.scopeIds.push($(this).val());
                });
            postData.publishStatus = $('#publishStatusSel').val();
            postData.firstSort = $('#firstSortSel').val();
            if ($('#beginTimeInput').val() != '') {
                postData.beginTime = $('#beginTimeInput').val() + " 00:00:00";
            }
            if ($('#endTimeInput').val() != '') {
                postData.endTime = $('#endTimeInput').val() + " 23:59:59";
            }
            return JSON.stringify(postData);
        },
        // 重置查询条件
        resetSearch: function () {
            $('#messageTitleInput').val('');
            //$('#scopeNameInput').val('');
            $('#actionScopesSel').val('');
            $("#actionScopesSel").multiselect('refresh');
            $('#publishStatusSel').val('');
            $('#firstSortSel').val('');
            $('#beginTimeInput').val('');
            $('#endTimeInput').val('');
        },

        // 新建
        addMessage: function () {
            go("message_publish/add");
        },

        // 详细
        showMessage: function (messageId) {
            goDisabled("message_publish/show?messageId=" + messageId);
        },

        // 修改
        editMessage: function (messageId) {
            var page = currentPage(MESSAGE.param.tableId);
            goDisabled("message_publish/edit?pageNo=" + page.pageNo + "&pageSize=" + page.pageSize + "&messageId=" + messageId);
        },

        // 删除
        deleteMessage: function () {
            //获取所有被选中的记录
            var rows = $(MESSAGE.param.tableId).bootstrapTable('getSelections');
            if (rows.length == 0) {
                messageError("请先选择要删除的记录!");
                return;
            }
            var ids = [];
            for (var i = 0; i < rows.length; i++) {
                ids.push(rows[i]["messageId"]);
            }
            messageConfirm("确定删除这条信息吗?", function () {
                postUrlCallDisabled("message_publish/delete", "ids=" + ids, function () {
                    $(MESSAGE.param.tableId).bootstrapTable("refresh");
                });
            });
        },
        timeBetweenInit: function () {
            $('#dateBegin').datetimepicker({
                language: 'zh-CN',
                format: 'yyyy-mm-dd',
                minView: "month",
                autoclose: true,
                todayBtn: true,
                todayHighlight: true,
                minuteStep: 2,
                startDate: formatTime(-365),
                endDate: formatTime(365)
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
                startDate: formatTime(-365),
                endDate: formatTime(365)
            }).on('changeDate', function (ev) {
                var endDate = ev.date;
                $('#dateBegin').datetimepicker('setEndDate', formatDate(endDate));
            });
        }
    }

};