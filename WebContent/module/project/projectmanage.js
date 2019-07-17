$import('module/common/ProjectComboBox.js');
$import('module/common/DepartmentComboBox.js');
Ext.namespace('Xietong.manage');

Xietong.manage.TaskManage = function(){
	this.init();
};

Ext.extend(Xietong.manage.TaskManage,Ext.util.Observable,{
	init:function(){
	
		var sm = new Ext.grid.CheckboxSelectionModel();
		var ds = new Xietong.project.ProjectStore();
		var expander = new Ext.grid.RowExpander({
        tpl : new Ext.Template( 
            '<p><b>描述:</b> {projectdescription}</p>'
        )
    	});
    	
		var cm = new Ext.grid.ColumnModel([
			expander,
			new Ext.grid.RowNumberer(),{
				header:'编号',
				dataIndex:'id',
				width:60,
				hidden:true,
				sortable:true
			},{
				header:'项目名称',
				dataIndex:'projectname',
				width:60,
				sortable:true
			},sm
		]);
		
		var refreshBtn = new Ext.Button({
			text:'刷新',
			iconCls:'icon-refresh',
			handler:function() {
				ds.reload();
			}
		});
		
		var addProjectWindow = null;
		var addProjectPanel = new Ext.FormPanel({
			layout:'form',
			frame:true,
			labelAlign: 'right', 
			url:'module/project/save.do',
			defaultType:'textfield',
			items:[{
				fieldLabel:'项目名称',
				anchor: "90%",
				width:500,
				name:'projectname',
				allowBlank: false
			},{
				fieldLabel:'项目描述',
				anchor: "90%",
				xtype:'htmleditor',
				enableSourceEdit :false,
				enableLists :false,
				name:'projectdescription',
				width:500,
				allowBlank: false
			}],
			buttons:[{
                text: '添加',
                formBind: true,
                handler: function(){
                    if(addProjectPanel.getForm().isValid())
			        	addProjectPanel.getForm().submit({
			        		waitMsg:'保存中,请稍后...',
			        		success:function(form,action){
				        		addProjectPanel.getForm().reset();
				        		addProjectWindow.hide();
				        		Ext.Msg.alert('信息',action.result.msg);
				        		ds.reload();
			        		}
			        	});
                }
            }, {
                text: '取消',
                handler: function(){
                    addProjectPanel.form.reset();
					addProjectWindow.hide();
                }
            }]
		});
		
		var addBtn = new Ext.Button({
			text:'新增',
			iconCls:'icon-add',
			handler:function() {
				if(!addProjectWindow) {
					addProjectWindow = new Ext.Window({
						title:'添加新项目',
						modal:true,
					    closable:true,
					    closeAction:'hide',
						width:700,
						items:[addProjectPanel]
					});
				}
				addProjectWindow.show(Ext.get('addBtn'));
			}
		});
		
		var updateProjectWindow = null;
		var updateProjectPanel = new Ext.FormPanel({
			layout:'form',
			frame:true,
			labelAlign: 'right', 
			url:'module/project/update.do',
			defaultType:'textfield',
			items:[{
				inputType:'hidden',
				name:'id'
			},{
				fieldLabel:'名称',
				anchor: "90%",
				width:500,
				name:'projectname',
				allowBlank: false
			},{
				fieldLabel:'描述',
				anchor: "90%",
				xtype:'htmleditor',
				enableSourceEdit :false,
				enableLists :false,
				name:'projectdescription',
				allowBlank: false
			}],
			buttons:[{
                text: '保存修改',
                formBind: true,
                handler: function(){
                    if(updateProjectPanel.getForm().isValid())
			        	updateProjectPanel.getForm().submit({
			        		waitMsg:'保存中,请稍后...',
			        		success:function(form,action){
				        		updateProjectPanel.getForm().reset();
				        		updateProjectWindow.hide();
				        		Ext.Msg.alert('信息',action.result.msg);
				        		ds.reload();
			        		}
			        	});
                }
            }, {
                text:'取消',
                handler: function(){
                    updateProjectPanel.form.reset();
					updateProjectWindow.hide(); 
                }
            }]
		});
		
		var updateBtn = new Ext.Button({
			text:'修改',
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
				(updateProjectPanel.find('name','id')[0]).setValue(sm.getSelected().get('id'));
				(updateProjectPanel.find('name','projectname')[0]).setValue(sm.getSelected().get('projectname'));
				(updateProjectPanel.find('name','projectdescription')[0]).setValue(sm.getSelected().get('projectdescription'));

				if(!updateProjectWindow) {
					updateProjectWindow = new Ext.Window({
						title:'修改项目信息',
						modal:true,
					    closable:true,
					    closeAction:'hide',
						width:700,
						items:[updateProjectPanel]
					});
				}
				updateProjectWindow.show(Ext.get('updateBtn'));
			} 
		});
		
		var deleteBtn = new Ext.Button({
			text:'删除',
			iconCls:'icon-delete',
			handler:function() {
				 var rows=mainPanel.getSelectionModel().getSelections(); 
				 if(rows == '') {
				 	Ext.MessageBox.alert('信息','没有任何记录被选中！');
					return;
				 }
				 Ext.MessageBox.confirm("警告","确认删除所选记录？",function(e){
					if(e == 'yes') {
						 var project_id = '';
					     for (var i = 0; i < rows.length; i++) {
						 	if (i > 0) {
						 		project_id = project_id + ',' + rows[i].get('id');
						 	}
						 	else {
						 		project_id = project_id + rows[i].get('id');
						 	}
						 } 
						 Ext.lib.Ajax.request(
							'POST',
							'module/project/delete.do',
							{
								success:function(response) {
									Ext.MessageBox.alert("信息",response.responseText,function(){
										ds.reload();
									});
								},
								failure:function(){
									Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
								}
							},
							'dids=' + encodeURIComponent(project_id)
						);
					} else {
						return;
					}
				});
			} 
		});
		var mainPanel = new Ext.grid.GridPanel({
			renderTo:'projectmanage-main',
			height:Ext.get("projectmanage-main").getHeight(),
			width:Ext.get("projectmanage-main").getWidth(),
			cm:cm,
			sm:sm,
			store:ds,
			loadMask: {msg:'正在加载数据，请稍候......'},
			clicksToEdit: 1,
			viewConfig:{ forceFit:true  },
			plugins: expander,
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
				updateBtn,'-',
				deleteBtn,'->'
			]
		});
		
		ds.load({params:{
			start:0,
			limit:12,
			sort:'priority',
			dir:'DESC'
		}});
	},
	//-----------------模块销毁函数---------------------------
	destroy:function(){
	}

});