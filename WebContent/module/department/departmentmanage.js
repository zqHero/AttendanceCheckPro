
$import('module/common/DepartmentComboBox.js');
$import('module/common/PersonManage.js');

Ext.namespace('Xietong.manage');


Xietong.manage.PersonManage = function(){
	this.init();
};

Ext.extend(Xietong.manage.PersonManage,Ext.util.Observable,{
	init:function(){
		
		var ds=new Xietong.department.DepartmentStore();
		ds.setDefaultSort('id', 'desc');
		
		var personStore = new Xietong.person.PersonStore();
		personStore.baseParams.departmentId = -1;
		
		var departmentExpander = new Ext.grid.RowExpander({
			tpl : new Ext.Template('<div style="margin-left:80px;width:90%;">'
									+ '<h4>概要描述:</h4><br>'
									+ '<hr><br>'
									+ '{departmentDesc}<br><br>'
								+ '</div>')
		});
		
		var sm = new Ext.grid.CheckboxSelectionModel({
			singleSelect : true,
			listeners:{
				'rowselect':function(sm, rowIndex, record){
					var departmentId = record.get('id');
					personStore.baseParams.departmentId = departmentId;
					personStore.reload();
				}
			}
		});
		var cm =  new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(),departmentExpander,{
				header:'编号',
				dataIndex:'id',
				width:60,
				hidden:true
			},{
				header:'名称',
				width:500,
				dataIndex:'departmentName'
			},
			sm
		]);
		
		var searchBtn = new Ext.Button({
			text:'查询',
			iconCls:'icon-search',
			handler:function() {
			}
		});
		
		var refreshBtn = new Ext.Button({
			text:'刷新',
			iconCls:'icon-refresh',
			handler:function() {
				ds.reload();
			}
		});
		
		var addDepartmentWindow = null;
		var addDepartmentPanel = new Ext.FormPanel({
			layout:'form',
			frame:true,
			labelAlign: 'right', 
			url:'module/department/save.do',
			defaultType:'textfield',
			items:[{
				fieldLabel:'名称',
				anchor: "90%",
				width:500,
				name:'departmentName',
				allowBlank: false
			},{
				fieldLabel:'描述',
				anchor: "90%",
				xtype:'htmleditor',
				enableSourceEdit :false,
				enableLists :false,
				name:'departmentDesc',
				width:500,
				allowBlank: false
			}],
			buttons:[{
                text: '添加',
                formBind: true,
                handler: function(){
                    if(addDepartmentPanel.getForm().isValid())
			        	addDepartmentPanel.getForm().submit({
			        		waitMsg:'保存中,请稍后...',
			        		success:function(form,action){
				        		addDepartmentPanel.getForm().reset();
				        		addDepartmentWindow.hide();
				        		Ext.Msg.alert('信息',action.result.msg);
				        		ds.reload();
			        		}
			        	});
                }
            }, {
                text: '取消',
                handler: function(){
                    addDepartmentPanel.form.reset();
					addDepartmentWindow.hide();
                }
            }]
		});
		
		var addBtn = new Ext.Button({
			text:'新增',
			iconCls:'icon-add',
			handler:function() {
				if(!addDepartmentWindow) {
					addDepartmentWindow = new Ext.Window({
						title:'添加新部门',
						modal:true,
					    closable:true,
					    closeAction:'hide',
						width:700,
						items:[addDepartmentPanel]
					});
				}
				addDepartmentWindow.show(Ext.get('addBtn'));
			}
		});
		
		var updateDepartmentWindow = null;
		var updateDepartmentPanel = new Ext.FormPanel({
			layout:'form',
			frame:true,
			labelAlign: 'right', 
			url:'module/department/update.do',
			defaultType:'textfield',
			items:[{
				inputType:'hidden',
				name:'departmentId'
			},{
				fieldLabel:'名称',
				anchor: "90%",
				width:500,
				name:'departmentName',
				allowBlank: false
			},{
				fieldLabel:'描述',
				anchor: "90%",
				xtype:'htmleditor',
				enableSourceEdit :false,
				enableLists :false,
				name:'departmentDesc',
				allowBlank: false
			}],
			buttons:[{
                text: '保存修改',
                formBind: true,
                handler: function(){
                    if(updateDepartmentPanel.getForm().isValid())
			        	updateDepartmentPanel.getForm().submit({
			        		waitMsg:'保存中,请稍后...',
			        		success:function(form,action){
				        		updateDepartmentPanel.getForm().reset();
				        		updateDepartmentWindow.hide();
				        		Ext.Msg.alert('信息',action.result.msg);
				        		ds.reload();
			        		}
			        	});
                }
            }, {
                text: '取消',
                handler: function(){
                    updateDepartmentPanel.form.reset();
					updateDepartmentWindow.hide(); 
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
				(updateDepartmentPanel.find('name','departmentId')[0]).setValue(sm.getSelected().get('id'));
				(updateDepartmentPanel.find('name','departmentName')[0]).setValue(sm.getSelected().get('departmentName'));
				(updateDepartmentPanel.find('name','departmentDesc')[0]).setValue(sm.getSelected().get('departmentDesc'));

				if(!updateDepartmentWindow) {
					updateDepartmentWindow = new Ext.Window({
						title:'修改部门信息',
						modal:true,
					    closable:true,
					    closeAction:'hide',
						width:700,
						items:[updateDepartmentPanel]
					});
				}
				updateDepartmentWindow.show(Ext.get('updateBtn'));
			} 
		});
		
		var deleteBtn = new Ext.Button({
			text:'删除',
			iconCls:'icon-delete',
			handler:function() {
				 var rows=departmentGrid.getSelectionModel().getSelections(); 
				 if(rows == '') {
				 	Ext.MessageBox.alert('信息','没有任何记录被选中！');
					return;
				 }
				 Ext.MessageBox.confirm("警告","确认删除所选记录？",function(e){
					if(e == 'yes') {
						 var department_id = '';
					     for (var i = 0; i < rows.length; i++) {
						 	if (i > 0) {
						 		department_id = department_id + ',' + rows[i].get('id');
						 	}
						 	else {
						 		department_id = department_id + rows[i].get('id');
						 	}
						 } 
						 Ext.lib.Ajax.request(
							'POST',
							'module/department/delete.do',
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
							'dids=' + encodeURIComponent(department_id)
						);
					} else {
						return;
					}
				});
			} 
		});
		
		var departmentGrid = new Ext.grid.GridPanel({
			clicksToEdit:1,
			cm:cm,
			plugins:departmentExpander,
			sm:sm,
			store:ds,
			loadMask: {msg:'正在加载数据，请稍候......'},
			viewConfig:{ forceFit:true  },
			tbar: [
				refreshBtn,'-',
				addBtn,'-',
				updateBtn,'-',
				deleteBtn,'->'
			]
		});
		
		var personCm = new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(),{
				header:'编号',
				dataIndex:'id',
				width:60,
				hidden:true
			},{
				header:'姓名',
				dataIndex:'realName'
			}
		]);
		var personGrid = new Ext.grid.GridPanel({
			title:'部门员工',
			cm:personCm,
			store:personStore,
			loadMask: {msg:'正在加载数据，请稍候......'},
			viewConfig:{ forceFit:true  },
			bbar: new Ext.PagingToolbar({
				pageSize:12,
				store:personStore,
				displayInfo:true,
				displayMsg:'第{0}到{1}条记录，共{2}条记录',
				emptyMsg:'没有记录'
			})
		});
		
		var mainPanel = new Ext.Panel({
			renderTo:'daka-departmentmanage-main',
			width:Ext.get("daka-departmentmanage-main").getWidth(),
			height:Ext.get("daka-departmentmanage-main").getHeight(),
			layout:'border',
			border:false,
			items:[{
				region:'center',
				layout:'fit',
				split:true,
				border:false,
				items:[departmentGrid]
			},{
				region:'east',
				layout:'fit',
				width:450,
				collapsible:true,
				split:true,
				border:false,
				items:[personGrid]
			}]
		});
		
		ds.load();
	},
	//-----------------模块销毁函数---------------------------
	destroy:function(){
	}

});
