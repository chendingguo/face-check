国际化
重新加载

得到查询的排序参数
sortName:this.sortName,
sortOrder:this.sortOrder,

onClickCell　
 {
            field: 'operate',
            title: 'Item Operate',
            align: 'center',
            events: operateEvents,
            formatter: operateFormatter
          }
           function operateFormatter(value, row, index) {
          return [
            '<a class="like" href="javascript:void(0)" title="Like">',
            '<i class="glyphicon glyphicon-heart"></i>',
            '</a>  ',
            '<a class="remove" href="javascript:void(0)" title="Remove">',
            '<i class="glyphicon glyphicon-remove"></i>',
            '</a>'
          ].join('');
        }
     window.operateEvents = {
      'click .like': function (e, value, row, index) {
        alert('You click like action, row: ' + JSON.stringify(row));
      },
      'click .remove': function (e, value, row, index) {
        $table.bootstrapTable('remove', {
          field: 'id',
          values: [row.id]
        });
      }
    };
	
	
	行内编辑