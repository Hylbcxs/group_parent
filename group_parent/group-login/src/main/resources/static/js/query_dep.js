$(function () {
    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

    //2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();
});

var TableInit = function () {
    var oTableInit = new Object();
    $("#tb_goods").bootstrapTable('destroy');
    //初始化Table
    oTableInit.Init = function () {
        $('#tb_goods').bootstrapTable({
            url: './ListServlet',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                       //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: false,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
            queryParams: oTableInit.queryParams,//传递参数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
            paginationPreText:"上一页",
            paginationNextText:"下一页",
            paginationLoop:false,

            search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
            showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            showExport: true,                     //是否显示导出
            exportDataType: "basic",              //basic', 'all', 'selected'.
            singleSelect: true, //开启单选,想要获取被选中的行数据必须要有该参数
            idField: 'Name', 			//指定主键

            //得到查询的参数(oTableInitO())
            queryParams: function (params) {
                //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                var query_params = {
                    rows: params.limit,                         //页面大小
                    page: (params.offset / params.limit) + 1,   //页码
                    sort: params.sort,      //排序列名
                    sortOrder: params.order, //排位命令（desc，asc）

                    //查询框中的参数传递给后台
                    search_kw: $('#search-keyword').val(), // 请求时向服务端传递的参数
                };
                return query_params;
            },

            //四个参数field, row, oldValue, $el分别对应着当前列的名称、当前行数据对象、更新前的值、编辑的当前单元格的jQuery对象。
            onEditableSave: function (field, row, oldValue, $el) {
                $.ajax({
                    type: "post",
                    url: "/Edit",
                    data: { strJson: JSON.stringify(row) },
                    success: function (data, status) {
                        if (status == "success") {
                            alert("编辑成功");
                        }
                    },
                    error: function () {
                        alert("Error");
                    },
                    complete: function () {

                    }

                });
            },
            rowStyle: function (row, index) {
                //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
                var strclass = "";
                if (index % 2 == 0) {
                    strclass = 'active';//还有一个active
                }
                else {
                    strclass = 'danger';
                }
                return { classes: strclass }
            },
            columns: [{
                checkbox: true,
                visible: true
            }, {
                field:'id',
                title:"商品编号",
                width: 50,
            },{
                field:'name',
                title:"商品名称",
                width: 50,
            },
                {
                    field: 'category',
                    title: '商品类别',
                    width: 120,
                    editable: {
                        type: 'select',
                        title: '商品类别',
                        editable: true,
                        source:[
                            {value:"101",text:"食品"},
                            {value:"102",text:"电器"},
                            {value:"103",text:"家居"},
                            {value:"104",text:"手机"},
                            {value:"105",text:"日用品"},
                        ]
                    },
                    formatter: function(value, row, index) {
                        var source = ["食品","电器","家居","手机","日用品"];
                        return source[row.id+100];
                    },//数据从另一个字段置换而来。
                }, {
                    field: 'place',
                    title: '商品产地',
                    width: 120
                }, {
                    field: 'price',
                    title: '商品价格',
                    width: 120
                }, {
                    field: 'date',
                    title: '商品生产日期',
                    width: 300
                },{
                    field: 'producer',
                    title: '商品生产商',
                    width: 300
                },{
                    field: 'operate',
                    title: '操作',
                    width: 120,
                    align: 'center',
                    valign: 'middle',
                    formatter: actionFormatter,
                }
            ]
        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit,   //页面大小
            offset: params.offset,  //页码
            goodsname: $("#txt_search_goodsname").val(),
            //查询框中的参数传递给后台
            //search_kw: $('#search-keyword').val(), // 请求时向服务端传递的参数
        };
        return temp;
    };

    return oTableInit;

};


//操作栏的格式化,value代表当前单元格中的值，row代表当前行数据，index表示当前行的下标
function actionFormatter(value, row, index) {
    var id = index;
    var data = JSON.stringify(row);
    var result = "";
    result += "<a href='javascript:;' class='btn btn-xs green' onclick=\"DataViewById('" + id + "', view='view')\" title='查看'><span class='glyphicon glyphicon-search'></span></a>";
    //{#result += "<a href='javascript:;' class='btn btn-xs blue' onclick=\"EditViewById('" + JSON.stringify(row) + "','" + id + "')\" title='编辑'><span class='glyphicon glyphicon-pencil'></span></a>";#}
    result += "<a href='javascript:;' class='btn btn-xs blue' onclick=\"EditViewById('" + row + "','" + id + "')\" title='编辑'><span class='glyphicon glyphicon-pencil'></span></a>";
    //{#result += "<a href='javascript:;' class='btn btn-xs blue' onclick=\"edit()\" title='编辑'><span class='glyphicon glyphicon-pencil'></span></a>";#}
    result += "<a href='javascript:;' class='btn btn-xs red' onclick=\"DeleteByIds('" + id + "','" + row + "')\" title='删除'><span class='glyphicon glyphicon-remove'></span></a>";
    return result;

}

//getData方法获取全部页面数据后，将data转为json对象，使用index当前行号作为下标获取对应数据
function EditViewById(row, index){
    var data = JSON.stringify($("#tb_goods").bootstrapTable('getData')); //获取全部数据
    var data_json = JSON.parse(data); //data_json和data_json2一样都是json对象
    //var data_json = eval("(" + data+ ")");
    console.log(data_json);
    //由于data2已经是对象了，alert不能显示对象只能显示为[object Object],需要控制台打印
    //{#alert(data_json[0]); #}
    //根据index找到对应行数据，填充数据到修改模态框
    $("#update_name").val(data_json[index].name);
    $("#update_name").attr("readonly","true");  // 删除禁止修改属性
    $("#update_category").val(data_json[index].category);
    $("#update_category").removeAttr("readonly");
    $("#update_place").val(data_json[index].place);
    $("#update_place").removeAttr("readonly");
    $("#update_price").val(data_json[index].price);
    $("#update_price").removeAttr("readonly");
    $("#update_date").val(data.json[index].data);
    $("#update_date").removeAttr("readonly");
    $("#update_producer").val(data.json[index].producer);
    $("#update_producer").removeAttr("readonly");



    $("#view_close").attr("class","btn btn-default");
    //$("view_submit").show();		// 显示提交按钮
    $("#view_submit").attr("style", "display:block;");

    //弹出修改/查看“部门信息”模态框
    $('#updateModal').modal('show');
}

// 查看部门信息
function DataViewById(index, title){
    var data = JSON.stringify($("#tb_goods").bootstrapTable('getData')); //获取全部数据
    var data_json = JSON.parse(data); //data_json和data_json2一样都是json对象
    //var data_json = eval("(" + data+ ")");
    console.log(data_json);
    //由于data2已经是对象了，alert不能显示对象只能显示为[object Object],需要控制台打印
    //{#alert(data_json[0]); #}
    //根据index找到对应行数据，填充数据到修改模态框
    $("#update_name").val(data_json[index].name);
    $("#update_name").attr("readonly","true");  // 删除禁止修改属性
    $("#update_category").val(data_json[index].category);
    $("#update_category").removeAttr("readonly");
    $("#update_place").val(data_json[index].place);
    $("#update_place").removeAttr("readonly");
    $("#update_price").val(data_json[index].price);
    $("#update_price").removeAttr("readonly");
    $("#update_date").val(data.json[index].data);
    $("#update_date").removeAttr("readonly");
    $("#update_producer").val(data.json[index].producer);
    $("#update_producer").removeAttr("readonly");

    $("#updateModalLabel").html("查看商品信息");
    //$(":submit").attr("style", "display:none;");
    //$("#view_submit").attr("style", "display:none;");
    $("#view_submit").hide();			//隐藏提交按钮
    $("#view_close").attr("class","btn btn-primary");

    //弹出修改/查看“部门信息”模态框
    $('#updateModal').modal('show');
}

// 删除选中行的数据
function DeleteByIds(index, row){
    var data = JSON.stringify($("#tb_goods").bootstrapTable('getData')); //获取全部数据
    var data_json = JSON.parse(data); //data_json和data_json2一样都是json对象
    //var data_json = eval("(" + data+ ")");
    console.log(data_json);

    //由于data_json已经是对象了，alert不能显示对象只能显示为[object Object],需要控制台打印
    var str_data_json = JSON.stringify(data_json[index])
    $.ajax({
        type: "post",
        url: "/Edit.jsp",
        data: JSON.stringify(data_json[index]),
        success: function (data, status) {
            if (status == "success") {
                alert("删除成功!");
            }
        },
        error: function () {
            alert("Error");
        },
        complete: function () {

        }

    });

}

// 搜索查询按钮触发事件
$(function() {
    $("#search-button").click(function () {
        $('#tb_departments').bootstrapTable(('refresh')); // 很重要的一步，刷新url！
        $('#search-keyword').val('')
    })
})

// 选择时间日期
$(function () {
    $('#datetime_picker').datetimepicker({
        format: 'YYYY-MM-DD',
        locale: moment.locale('zh-cn')
    });
});
var ButtonInit = function () {
    var oInit = new Object();
    var postdata = {};

    oInit.Init = function () {
        //初始化页面上面的按钮事件
    };

    return oInit;
};

