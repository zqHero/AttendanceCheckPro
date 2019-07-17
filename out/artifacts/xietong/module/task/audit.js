
$import('module/common/DepartmentComboBox.js');
$import('module/common/ProjectComboBox.js');
$import('module/common/PersonManage.js');
$import('module/common/TaskManage.js');

Ext.namespace('Xietong.manage');

Xietong.manage.TaskManage = function(){
	this.init();
};

Ext.extend(Xietong.manage.TaskManage,Ext.util.Observable,{
	init:function(){
		
		//保存查询出来的数据	
		var ds = new Xietong.task.TaskStore();
		//复选框
		var sm = new Ext.grid.CheckboxSelectionModel({
			singleSelect:false,
			listeners:{
				'rowselect':function(sm,rowIndex, record){
					logPanel.setTitle("属于 '" + record.get('taskName') + "' 的操作记录");
					taskLogDs.load({
						params:{
							taskId:record.get("id"),
							start:0,
							limit:5
						}
					});	
				}
			}
		});
		
		//任务列表
		var expander = new Ext.grid.RowExpander({
        tpl : new Ext.Template( 
            '<p><b>描述:</b> {taskdescription}</p>'
        )
    	});
    	var personComboBox = new Xietong.person.PersonTrigger();
		var cm =  new Ext.grid.ColumnModel([
		    expander,
			new Ext.grid.RowNumberer(),{
				header:'编号',
				dataIndex:'id',
				width:60,
				hidden:true,
				sortable:true
			},{
				header:'任务名称',
				dataIndex:'taskname',
				width:60,
				sortable:true
			},{
				header:'负责人',
				dataIndex:'usersName',
				width:60,
				sortable:true
				//editable:false,
				//editor:personComboBox
			},{
				header:'所属项目',
				dataIndex:'projectName',
				width:60,
				sortable:true
			},{
				header:'任务类型',
				dataIndex:'tasktype',
				width:60,
				sortable:true
			},{
				header:'优先级',
				dataIndex:'priority',
				width:60,
				sortable:true
			},{
				header:'任务状态',
				dataIndex:'taskstate',
				width:60,
				sortable:true,
				editable:true,
				editor:new Ext.form.ComboBox({
					store : new Ext.data.SimpleStore({
						fields : ['value','text'],
						data : [['审核通过','审核通过'],['审核不通过','审核不通过']]
					}),
					valueField : 'value',
					displayField : 'text',
					triggerAction : 'all',
					mode:'local',
					editable: false,
					triggerAction: 'all'
				})
			},
			sm
		]);
		
		//刷新按钮
		var refreshBtn = new Ext.Button({
		text:'刷新',
		iconCls:'icon-refresh',
		handler:function() {
			ds.reload();
		}
		});
		
		//根据负责人查询按钮
		var searchPeronComboBox = new Xietong.person.PersonTrigger();		
		var searchBtn = new Ext.Button({
			text:'查询',
			iconCls:'icon-search',
			handler:function() {
				if(searchPeronComboBox.isValid()) {
					ds.proxy = new Ext.data.HttpProxy({
									url:'module/task/listAudit.do'
								});
					ds.baseParams.s_userName = searchPeronComboBox.getValue();
					ds.load({params:{
						s_userName:searchPeronComboBox.getValue(),
				 		start:0,
		                limit:12
					}});
				}
			}
		});
		
		//保存修改按钮
		var saveAuditUpdateBtn = new Ext.Button({
		text:'保存修改',
		iconCls:'icon-edit',
		handler:function() {
			//cm.setEditable(8,false);
			var modifiedRecords = ds.getModifiedRecords();
			if(modifiedRecords.length==0){
				Ext.Msg.alert('提示','没有任何修改！');
			}else{
				var taskstates = "";
				var ids = "";
				for(var i=0;i<modifiedRecords.length;i++){
					taskstates += modifiedRecords[i].get('taskstate');
					taskstates += ",";
					ids += modifiedRecords[i].get('id');
					ids += ",";
				}
				Ext.Ajax.request({
					url : 'module/task/audit.do',
					params : {
						taskstates : taskstates,
						ids : ids
					},
					success : function(response){
						Ext.Msg.alert('信息',response.responseText,function(){
							ds.reload();
						});
					},
					failure : function(response){
						Ext.Msg.alert('信息',response.responseText);
					}
				});
			}	
		}
		});
		
		//查询所有任务按钮
		var allTasksBtn = new Ext.Button({
			text:'所有任务',
			iconCls:'icon-search',
			handler:function() {
				ds.proxy = new Ext.data.HttpProxy({
									url:'module/task/listAudit.do'
								});
				ds.baseParams.s_userName = 'all';
				ds.load({params:{
					s_userName:'all',
			 		start:0,
	                limit:12
				}});
			}
		});
        //-------------------主界面（center）------------------------
		var mainPanel = new Ext.grid.EditorGridPanel({
			cm:cm,
			sm:sm,
			store:ds,
			plugins: expander,
			loadMask: {msg:'正在加载数据，请稍候......'},
			viewConfig:{ forceFit:true  },
			clicksToEdit: 1,
			bbar: new Ext.PagingToolbar({
				pageSize:12,
				store:ds,
				displayInfo:true,
				displayMsg:'显示第{0}到{1}条记录，共{2}条记录',
				emptyMsg:'没有记录'
			}),
			tbar: [
				refreshBtn,'-',
				saveAuditUpdateBtn,'-',
                '->',
                allTasksBtn,'-',
				'负责人:',
				searchPeronComboBox,
				searchBtn
			]
		});
		
        ds.proxy = new Ext.data.HttpProxy({
					   	url:'module/task/listAudit.do'
					   });
		ds.load({params:{
			s_userName:'all',
	 		start:0,
            limit:12
		}});
		
		//----------------------任务操作日志（south）------------------
		var taskLogCm = new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(),{
				header: '时间',
				dataIndex: 'date',
				width: 100
			},{
				header: '操作人',
				dataIndex: 'operatorName',
				width: 100
			},{
				header: '操作动作',
				dataIndex: 'action',
				width: 100
			},{
				header: '接收者',
				dataIndex: 'executorName',
				width: 100
			},{
				header: '备注',
				dataIndex: 'result',
				width: 100
			}
		]);
		
		var taskLogDs = new Xietong.task.TaskLogStore();
		var logPanel = new Ext.grid.GridPanel({
			cm: taskLogCm,
			store: taskLogDs,
			loadMask: {msg:'正在加载数据，请稍候......'},
			viewConfig:{ forceFit:true  },
			bbar: new Ext.PagingToolbar({
				pageSize:5,
				store:taskLogDs,
				displayInfo:true,
				displayMsg:'显示第{0}到{1}条记录，共{2}条记录',
				emptyMsg:'没有记录'
			})			
		});
		
		taskLogDs.load({params:{
			start:0,
			limit:5
		}});
		
		//---------------------总体显示-------------------------
		var borderPanel = new Ext.Panel({
			renderTo: 'task-audit-main',
			height: Ext.get("task-audit-main").getHeight(),
			width: Ext.get("task-audit-main").getWidth(),
			layout: 'border',
			items:[{
				region: 'center',
				layout: 'fit',
				border: false,
				items: [mainPanel]
			},{
				region: 'south',
				layout: 'fit',
				border: false,
				height: Ext.get("task-audit-main").getHeight()*0.2,
				items: [logPanel]
			}]
		});
	},
	//-----------------模块销毁函数---------------------------
	destroy:function(){
	}

});
