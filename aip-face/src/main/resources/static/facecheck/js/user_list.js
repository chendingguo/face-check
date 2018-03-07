$(function () {
    userList.init();
});

var userList = {
    init: function () {
        userList.initDate();
        userList.initEvent();
    },
    initDate: function () {
        userList.fn.loadData();

    },
    initEvent: function () {

    }
}
userList.fn = {

    deleteUser: function (userId) {
        $.ajax({
            url: '/deleteUser',
            type: 'POST', //GET
            async: true,    //或false,是否异步
            data: {
                userId: userId

            },
            timeout: 5000,    //超时时间
            dataType: 'json',    //返回的数据格式：json/xml/html/script/jsonp/text
            beforeSend: function (xhr) {
                // console.log(xhr)
                // console.log('发送前')
            },
            success: function (data, textStatus, jqXHR) {
                console.log("[RESULT]")
                console.log(data)
                console.log(textStatus)
                console.log(jqXHR)
                $("#tb_users").bootstrapTable('refresh', {});

            },
            error: function (xhr, textStatus) {
                console.log('错误')
                console.log(xhr)
                console.log(textStatus)
            }
        });

    },
    loadData: function () {
        $('#tb_users').bootstrapTable({
            url: '/getUsers', // 请求后台的URL（*）
            method: 'get', // 请求方式（*）
            striped: true, // 是否显示行间隔色
            undefinedText: '--',
            cache: false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true, // 是否显示分页（*）
            sortable: true, // 是否启用排序
            sortOrder: "asc", // 排序方式
            //queryParams: oTableInit.queryParams,// 传递参数（*）
            sidePagination: "server", // 分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1, // 初始化加载第一页，默认第一页
            pageSize: 10, // 每页的记录行数（*）
            pageList: [10, 25, 50, 100], // 可供选择的每页的行数（*）
            search: false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端
            strictSearch: true,
            showColumns: false, // 是否显示所有的列
            showRefresh: true, // 是否显示刷新按钮
            minimumCountColumns: 1, // 最少允许的列数
            clickToSelect: true, // 是否启用点击选中行
            height: 500, // 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            showToggle: true, // 是否显示详细视图和列表视图的切换按钮
            cardView: false, // 是否显示详细视图
            detailView: false, // 是否显示父子表



            //列参数
            columns: [{
                checkbox: true

            }, {
                field: 'id',
                title: 'ID',
                radio: false

            }, {
                field: 'name',
                title: 'Name'

            }, {
                field: 'image',
                title: 'Head',
                sortable: true,
                formatter: function (value, row, index) {
                    return '<img height="60" width="60" src="' + value + '" class="rounded-circle">';
                }
            },
                {
                    field: "id",
                    title: "操作",
                    align: "center",
                    valign: "middle",
                    formatter: function (value, row, index) {
                        // alert(value);
                        return '<button  type="button" onclick=userList.fn.deleteUser("' + value + '") class="btn btn-danger">Delete</button>';
                    }
                }
            ],


        });
    }
}

userList.event = {


}