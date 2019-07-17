
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
			singleSelect:true,
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
		var fm = Ext.form, Ed = Ext.grid.GridEditor;
		var expander = new Ext.grid.RowExpander({
	        tpl : new Ext.Template( 
	            '<p><b>描述:</b> {taskdescription}</p>'
	        )  
    	});
        var projectComboBox = new Xietong.project.ProjectComboBox();
        
        
        projectComboBox.store.load({callback:function(){
			ds.load({params:{
				s_userName:'all',
		 		start:0,
	            limit:12
			}});
		}});
        
        
        var taskTypeComboBox = new Xietong.task.TaskTypeComboBox();
        var taskPriorityComboBox = new Xietong.task.TaskPriority();
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
				sortable:true,
				editable:true,
				editor:new Ed(new fm.TextField({
				   allowBlank: false
				}))
			},{
				header:'负责人',
				dataIndex:'usersName',
				width:60,
				sortable:true
			},{
				header:'所属项目',
				dataIndex:'projectId',
				width:60,
				sortable:true,
				editable:true,
				editor:projectComboBox,
				renderer:function(v) {
					
					var index = projectComboBox.store.find('id',v);
					return projectComboBox.store.getAt(index).get('projectname');
				}
			},{
				header:'任务类型',
				dataIndex:'tasktype',
				width:60,
				sortable:true,
				editable:true,
				editor:taskTypeComboBox
			},{
				header:'优先级',
				dataIndex:'priority',
				width:60,
				sortable:true,
				editable:true,
				editor:taskPriorityComboBox
			},{
				header:'任务状态',
				dataIndex:'taskstate',
				width:60,
				sortable:true,
				editable:false
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
		
		//添加任务
		var addTaskWindow = null;
		var users = new Xietong.person.PersonTrigger();
		users.fieldLabel = '负责人';
		var addTaskPanel = new Ext.FormPanel({
			layout:'form',
			frame:true,
			labelAlign: 'right', 
			url:'module/task/add.do',
			defaultType:'textfield',
			items:[{
				fieldLabel:'任务名称',
				anchor: "90%",
				name:'taskname',
				allowBlank: false,
				width:300
			},users,//负责人superiorsName
		    new Xietong.project.ProjectComboBox(),//所属项目projectId
		    new Xietong.task.TaskTypeComboBox(),//任务类型tasktype
		    new Xietong.task.TaskPriority(),//优先级priority
		    {
		    	fieldLabel:'描述',
				anchor: "90%",
				xtype:'htmleditor',
				enableSourceEdit :false,
				enableLists :false,
				name:'taskdescription',
				width:500
		    }
			],
			buttons:[{
                text: '添加',
                formBind: true,
                handler: function(){
                    if(addTaskPanel.getForm().isValid())
			        	addTaskPanel.getForm().submit({
			        		waitMsg:'保存中,请稍后...',
			        		success:function(form,action){
				        		addTaskPanel.getForm().reset();
				        		addTaskWindow.hide();
				        		Ext.Msg.alert('信息',action.result.msg);
				        		ds.reload();
			        		}
			        	});
			
                }
            }, {
                text: '取消',
                handler: function(){
                    addTaskPanel.form.reset();
					addTaskWindow.hide();
                }
            }]
		});
		
		var addBtn = new Ext.Button({
		text:'新增',
		iconCls:'icon-add',
		handler:function() {
			if(!addTaskWindow) {
				addTaskWindow = new Ext.Window({
					title:'提交任务',
					modal:true,
				    closable:true,
				    closeAction:'hide',
					width:700,
					items:[addTaskPanel]
				});
			}
			addTaskWindow.show(Ext.get('addBtn'));
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
					   	url:'module/task/listAll.do'
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
		
		//删除任务按钮
		var deleteBtn = new Ext.Button({
			iconCls:'icon-delete',
			text : '删除',
			handler : function(){
				if(!sm.hasSelection()){
					Ext.Msg.alert('信息','没有选中任何记录！');
					return;
				}else{
					Ext.MessageBox.confirm("警告","确认删除所选记录？",function(e){
					if(e == 'yes') {
						 var taskids = "";
					     for (var i = 0; i < sm.getSelections().length; i++) {  
						 	if (i > 0) {
						 		taskids = taskids + ',' + sm.getSelections()[i].get('id');
						 	}
						 	else {
						 		taskids = taskids + sm.getSelections()[i].get('id'); 
						 	}
						 }
						 Ext.Ajax.request({
							url : 'module/task/delete.do',
							params : {
								taskids : taskids
							},
							success : function(response){
								Ext.MessageBox.alert("信息",response.responseText,function(){
									ds.reload();
								});
							},
							failure : function(response){
								Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
							}
						 });
					} else {
						return;
					}
					});
				}
			}	
		});
		
		//修改详细描述
		var updateTaskWindow = null;
		var updateTaskPanel = new Ext.FormPanel({
			layout:'form',
			frame:true,
			labelAlign: 'right', 
			url:'module/task/update.do',
			defaultType:'textfield',
			items:[{
				inputType:'hidden',
				name:'id'
			},{
				fieldLabel:'描述',
				anchor: "90%",
				xtype:'htmleditor',
				enableSourceEdit :false,
				enableLists :false,
				name:'taskdescription',
				allowBlank: false
			}],
			buttons:[{
                text: '保存修改',
                formBind: true,
                handler: function(){
                    if(updateTaskPanel.getForm().isValid())
			        	updateTaskPanel.getForm().submit({
			        		waitMsg:'保存中,请稍后...',
			        		success:function(form,action){
				        		updateTaskPanel.getForm().reset();
				        		updateTaskWindow.hide();
				        		Ext.Msg.alert('信息',action.result.msg);
				        		ds.reload();
			        		}
			        	});
                }
            }, {
                text:'取消',
                handler: function(){
                    updateTaskPanel.form.reset();
					updateTaskWindow.hide(); 
                }
            }]
		});
		
		var updateBtn = new Ext.Button({
			text:'修改描述',
			iconCls:'icon-edit',
			handler:function() {
				if(!sm.getSelected()){
					Ext.Msg.show({
						title: "信息",
						msg:"请先选中一项",
						buttons:Ext.MessageBox.OK,
						icon: Ext.MessageBox.INFO
					});
					return;
				}
				(updateTaskPanel.find('name','id')[0]).setValue(sm.getSelected().get('id'));
				(updateTaskPanel.find('name','taskdescription')[0]).setValue(sm.getSelected().get('taskdescription'));
				if(!updateTaskWindow) {
					updateTaskWindow = new Ext.Window({
						title:'修改任务信息',
						modal:true,
					    closable:true,
					    closeAction:'hide',
						width:700,
						items:[updateTaskPanel]
					});
				}
				updateTaskWindow.show(Ext.get('updateBtn'));
			} 
		});
		
		//保存修改
		var saveUpdateBtn = new Ext.Button({
		text:'保存',
		iconCls:'icon-edit',
		handler:function() {
			var modifiedRecords = ds.getModifiedRecords();
			if(modifiedRecords.length==0){
				Ext.Msg.alert('提示','没有任何修改！');
			}else{
				var ids = "";
				var tasknames = "";
				var projectNames = "";
				var tasktypes = "";
				var prioritys = "";
				for(var i=0;i<modifiedRecords.length;i++){
					ids += modifiedRecords[i].get('id');
					ids += ",";
					tasknames += modifiedRecords[i].get('taskname');
					tasknames += ",";
					projectNames += modifiedRecords[i].get('projectName');
					projectNames += ",";
					tasktypes += modifiedRecords[i].get('tasktype');
					tasktypes += ",";
					prioritys += modifiedRecords[i].get('priority');
					prioritys += ",";
				}
				Ext.Ajax.request({
					url : 'module/task/update.do',
					params : {
						ids : ids,
						tasknames : tasknames,
						projectNames : projectNames,
						tasktypes : tasktypes,
						prioritys : prioritys
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
					   	url:'module/task/listAll.do'
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
				addBtn,'-',
				deleteBtn,'-',
				updateBtn,'-',
				saveUpdateBtn,'-',
                '->',
                allTasksBtn,'-',
				'负责人:',
				searchPeronComboBox,
				searchBtn
			]
		});
		
        ds.proxy = new Ext.data.HttpProxy({
					   	url:'module/task/listAll.do'
					   });
		
		

		//----------------------任务操作日志------------------
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
			renderTo: 'task-taskmanage-main',
			height: Ext.get("task-taskmanage-main").getHeight(),
			width: Ext.get("task-taskmanage-main").getWidth(),
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
				height: Ext.get("task-taskmanage-main").getHeight()*0.2,
				items: [logPanel]
			}]
		});
	},
	//-----------------模块销毁函数---------------------------
	destroy:function(){
	}

});
