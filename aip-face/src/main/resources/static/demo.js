$(function() {

	// 1.初始化Table
	var oTable = new TableInit();
	oTable.Init();

	// 2.初始化Button的点击事件
	var oButtonInit = new ButtonInit();
	oButtonInit.Init();

});


var TableInit = function() {
	var oTableInit = new Object();
	// 初始化Table
	oTableInit.Init = function() {
		$('#tb_users').bootstrapTable({
			url : '/demo/getUsers', // 请求后台的URL（*）
			method : 'get', // 请求方式（*）
			toolbar : '#toolbar', // 工具按钮用哪个容器
			striped : true, // 是否显示行间隔色
			undefinedText:'--',
			cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination : true, // 是否显示分页（*）
			sortable : true, // 是否启用排序
			sortOrder : "asc", // 排序方式
			queryParams : oTableInit.queryParams,// 传递参数（*）
			sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
			pageNumber : 1, // 初始化加载第一页，默认第一页
			pageSize : 10, // 每页的记录行数（*）
			pageList : [ 10, 25, 50, 100 ], // 可供选择的每页的行数（*）
			search : false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端
			strictSearch : true,
			showColumns : true, // 是否显示所有的列
			showRefresh : true, // 是否显示刷新按钮
			minimumCountColumns : 1, // 最少允许的列数
			clickToSelect : true, // 是否启用点击选中行
			height : 500, // 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
			showToggle : true, // 是否显示详细视图和列表视图的切换按钮
			cardView : false, // 是否显示详细视图
			detailView : false, // 是否显示父子表
			showExport : true, //是否显示导出按钮
			
			
			//列参数
			columns : [ {
				checkbox : true
				
			}, {
				field : 'id',
				title : 'ID',
				radio:false
				
			}, {
				field : 'name',
				title : '姓名',
				editable: {  
                    type: 'text',  
                    validate: function (value) {  
                        if ($.trim(value) == '') {  
                            return '不能为空!';  
                        }  
                    }  
                }  
			}, {
				field : 'age',
				title : '年龄',
				sortable : true
				
				

			} ,
			{   field:"operation",
				title:"操作",
				align:"center",
				valign:"middle",
				formatter:function(value,row,index){
				 // alert(value);
			      return "<a onclick=editInfo('update')>详细</a>";
			   }
		    }
			],
			
			//事件demo
			onAll: function (name, args) {
//				console.log("方法名称:"+name );
//				console.log(args);
		    },
		    onClickRow:function(row, $element){
		    	alert("行ID:"+row.id);
		    },
		    
		    //---------------插件扩展-----------------
			//cookie参数
			cookie:true,//是否存cookie
			cookieIdTable:"tb_users",
			
			//菜单参数
			contextMenuTrigger:'right',
			contextMenu: '#context-menu',
			onContextMenuItem: function(row, $el){
			        if($el.data("item") == "edit"){
			            alert("Edit: " + row.id + ' ' + row.name );
			        } else if($el.data("item") == "delete"){
			            alert("Delete: " + row.id + ' ' + row.name);
			        } else if($el.data("item") == "action1"){
			            alert("Action1: " + row.id + ' ' + row.name);
			        } else if($el.data("item") == "action2"){
			            alert("Action2: " + + row.id + ' ' + row.name);
			        }
			   },
			   
			   filter:true,
			
			
			
			
		});
		

		$("#tb_users").find("td").each(function () {
			  
			　　$(this).editable();
			   console.log();
			});
			　　
	};
	//$('#tb_users').bootstrapTable("setFilterData", "name", ["item 1", "item 2", "item 3"]);
	
	

	// 得到查询的参数
	oTableInit.queryParams = function(params) {
		var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, // 页面大小
			offset : params.offset, // 页码
			sortName : this.sortName,
			sortOrder : this.sortOrder,
			name : $("#txt_search_name").val()
		};
		return temp;
	};
	return oTableInit;
};


//触发模态框的同时调用此方法
function editInfo(method) {
	// 新增
	if (method == "add") {
		$('#update').modal('show');
		
	 
	 $('#tb_users').bootstrapTable('prepend',data);
	} else if (method == "update") {
		// 更新
		var rows = $('#tb_users').bootstrapTable('getAllSelections');
		var selectedRow = rows[0];
		// 获取表格中的一行数据
		var id = selectedRow.id
		var name = selectedRow.name
		var age = selectedRow.age

		// 向模态框中传值
		$('#id').val(id);
		$('#name').val(name);
		$('#age').val(age);
		$('#update').modal('show');
	}
}

function add(){
	var name=$("#name").val();
	var age=$("#age").val();
	 var data=
		 [{"id":100,name:name,age:age}];
	 $('#tb_users').bootstrapTable('prepend',data);
}


var ButtonInit = function() {
	var oInit = new Object();

	oInit.Init = function() {

		//查询参数
		$("#btn_query").click(function() {
			var opt = {
				query : {
					name : $("#txt_search_name").val()
				}
			}
			$("#tb_users").bootstrapTable('refresh', opt);
		});

		$("#btn_edit").click(function() {
			editInfo("update");
		});
		$("#btn_add").click(function() {
			editInfo("add");
		});

		$("#btn_delete").click(function() {
			var rows = $('#tb_users').bootstrapTable('getAllSelections');

			var selectedRow = rows[0];
			var r = confirm("删除ID为" + selectedRow.id + "的记录");
			
		});
		
		$("#btn_all_options").click(function() {
			 alert(JSON.stringify($('#tb_users').bootstrapTable('getOptions')));
		});
		
		$("#btn_append").click(function() {
			 var data=
					 [{"id":100,"name":"name_append1",age:100}];
			 
			 $('#tb_users').bootstrapTable('prepend',data);
			 
			 $('#tb_users').bootstrapTable('remove',{field: 'id', values: [2,3]});
		});

		 
	};

	return oInit;
};

