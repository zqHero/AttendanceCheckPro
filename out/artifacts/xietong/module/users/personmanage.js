
$import('module/common/DepartmentComboBox.js'); 
$import('module/common/GenderComboBox.js'); 
$import('module/common/PersonManage.js'); 
$import('js/Ext.ux/Ext.ux.CheckExistField.js'); 

Ext.namespace('Xietong.manage');


Xietong.manage.PersonManage = function(){
	this.init();
};

Ext.extend(Xietong.manage.PersonManage,Ext.util.Observable,{
	init:function(){
		
		var departmentComboBox = new Xietong.department.DepartmentComboBox();
		var personComboBox = new Xietong.person.PersonTrigger();
		departmentComboBox.store.load({callback:function(){
			ds.load({params:{
				start:0,
				limit:25
			}});
		}});
		var searchDepartmentComboBox = new Xietong.department.DepartmentComboBox({
			listeners:{
				'expand':function(combo){
					combo.store.insert(0,new Department({id:0,departmentName:'所有部门'}));
				},
				'collapse': function(combo) {
					combo.store.remove(combo.store.getAt(0));
				}
			}
		});
		
		var ds=new Xietong.person.PersonStore();
		ds.setDefaultSort('id', 'desc');
		
		var sm = new Ext.grid.CheckboxSelectionModel();
		
		var cm =  new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(),{
				header:'编号',
				dataIndex:'id',
				hidden:true,
				sortable:true
			},{
				header:'用户名',
				dataIndex:'userName',
				width:50,
				sortable:true
			},{
				header:'密码',
				dataIndex:'password',
				width:50,
				editor:new Ext.form.TextField({
					allowBlank:false,
					maxLength:20,
					maxLengthText:'该输入项的最大长度是{0}',
					minLength:5,
					minLengthText:'该输入项的最小长度是{0}'
				})
			},{
				header:'真实姓名',
				dataIndex:'realName',
				width:50,
				editor:new Ext.form.TextField({
					allowBlank:false
				})
			},{
				header:'性别',
				dataIndex:'gender',
				width:20,
				sortable:true,
				editor:new Xietong.person.GenderComboBox(),
				renderer:function(v) {
					if(v=='男') return "<img src='images/male.png'/>"+v;
					else if(v=='女') return "<img src='images/female.png'/>"+v;
				}
			},{
				header:'出生年月',
				dataIndex:'birthday',
				width:30,
				sortable:true,
				editor:new Ext.form.DateField({
					format:'Y-m-d',
					readOnly:true
				}),
				renderer:function(v) {
					if(v == null || v=='') return '';
					return v.format('Y-m-d');
				}
			},{
				header:'学校',
				dataIndex:'school',
				width:50,
				editor:new Ext.form.TextField({
					allowBlank:false
				})
			},{
				header:'专业',
				dataIndex:'specialty',
				width:50,
				editor:new Ext.form.TextField({
					allowBlank:false
				})
			},{
				header:'部门',
				dataIndex:'departmentId',
				width:50,
				editor:departmentComboBox,
				renderer:function(v) {
					var index = departmentComboBox.store.find('id',v);
					return departmentComboBox.store.getAt(index).get('departmentName');
				}
			},{
				header:'上级',
				dataIndex:'superiorsName',
				editor : personComboBox
			},
			sm
		]);
		
		var searchBtn = new Ext.Button({
			text:'查询',
			iconCls:'icon-search',
			handler:function() {
				if(searchDepartmentComboBox.isValid()) {
					ds.on('beforeload', function() {
						this.baseParams = {
					 		departmentId:searchDepartmentComboBox.getValue()
					 	};
					});
					ds.reload();
				}
			}
		});
		
		var refreshBtn = new Ext.Button({
			text:'刷新',
			iconCls:'icon-refresh',
			handler:function() {
				ds.reload();
			}
		});
		
		Ext.form.TextField
		
		var addPersonWindow = null;
		var addPersonPanel = new Ext.FormPanel({
			layout:'form',
			frame:true,
			labelAlign: 'right', 
			url:'module/users/save.do',
			defaultType:'textfield',
			items:[new Ext.ux.CheckExistField({
				checkUrl:'module/users/check.do',
				checkFailedText:'用户名 {0} 已存在',
				fieldLabel:'用户名',
				anchor: "90%",
				name:'userName',
				allowBlank: false
			}),{
				fieldLabel:'密码',
				name:'password',
				inputType : 'password',
				anchor: "90%",
				maxLength:20,
				maxLengthText:'该输入项的最大长度是{0}',
				minLength:5,
				minLengthText:'该输入项的最小长度是{0}',
				allowBlank: false
			},{
				fieldLabel:'真实姓名',
				name:'realName',
				anchor: "90%"
			},new Xietong.person.GenderComboBox(),{
				fieldLabel:'出生日期',
				name:'birthday',
				xtype:'datefield',
				readOnly:true,
				format:'Y-m-d',
				anchor: "90%"
			},{
				fieldLabel:'学校',
				name:'school',
				anchor: "90%"
			},{
				fieldLabel:'专业',
				name:'specialty',
				anchor: "90%"
			},new Xietong.department.DepartmentComboBox(),
			  new Xietong.person.PersonTrigger()
			],
			buttons:[{
                text: '添加',
                formBind: true,
                handler: function(){
                    if(addPersonPanel.getForm().isValid())
			        	addPersonPanel.getForm().submit({
			        		waitMsg:'保存中,请稍后...',
			        		success:function(form,action){
				        		addPersonPanel.getForm().reset();
				        		addPersonWindow.hide();
				        		Ext.Msg.alert('信息',action.result.msg);
				        		ds.reload();
			        		}
			        	});
			
                }
            }, {
                text: '取消',
                handler: function(){
                    addPersonPanel.form.reset();
					addPersonWindow.hide();
                }
            }]
		});
		
		var addBtn = new Ext.Button({
			text:'新增',
			iconCls:'icon-add',
			handler:function() {
				if(!addPersonWindow) {
					addPersonWindow = new Ext.Window({
						title:'添加新员工',
						modal:true,
					    closable:true,
					    closeAction:'hide',
						width:500,
						items:[addPersonPanel]
					});
				}
				addPersonWindow.show(Ext.get('addBtn'));
			}
		});
		
		var updateBtn = new Ext.Button({
			text:'保存修改',
			iconCls:'icon-edit',
			handler:function() {
				var m = ds.modified.slice(0);
				if(m.length == 0) {
					Ext.MessageBox.alert("信息","没有任何记录被修改");
					return;
				}
				var jsonArray = [];
				Ext.each(m,function(item){
					jsonArray.push(item.data);
				});
				Ext.lib.Ajax.request(
					'POST',
					'module/users/update.do',
					{
						success:function(response) {
							Ext.MessageBox.alert("信息",response.responseText,function(){
								ds.reload();
								ds.modified = []; 
							});
						},
						failure:function(){
							Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
						}
					},
					'data=' + 	encodeURIComponent(Ext.encode(jsonArray))
				);
			}
		});
		
		var deleteBtn = new Ext.Button({
			text:'删除',
			iconCls:'icon-delete',
			handler:function() {
				 var rows=mainGrid.getSelectionModel().getSelections(); 
				 if(rows == '') {
				 	Ext.MessageBox.alert('信息','没有任何记录被选中！');
					return;
				 }
				 Ext.MessageBox.confirm("警告","确认删除所选记录？",function(e){
					if(e == 'yes') {
						 var user_id = '';
					     for (var i = 0; i < rows.length; i++) {
						 	if (i > 0) {
						 		user_id = user_id + ',' + rows[i].get('id');
						 	}
						 	else {
						 		user_id = user_id + rows[i].get('id');
						 	}
						 } 
						 Ext.lib.Ajax.request(
							'POST',
							'module/users/delete.do',
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
							'uids=' + encodeURIComponent(user_id)
						);
					} else {
						return;
					}
				});
			}
		});
		
		var mainGrid = new Ext.grid.EditorGridPanel({
			renderTo:'daka-personmanage-main',
			width:Ext.get("daka-personmanage-main").getWidth(),
			height:Ext.get("daka-personmanage-main").getHeight(),
			clicksToEdit:1,
			stripeRows :true,
			trackMouseOver:true,
			enableColumnMove:false,
			enableColumnHide :false,
			monitorResize:true,
			cm:cm,
			sm:sm,
			store:ds,
			loadMask: {msg:'正在加载数据，请稍候......'},
			viewConfig:{ forceFit:true  },
			bbar: new Ext.PagingToolbar({
				pageSize:25,
				store:ds,
				displayInfo:true,
				displayMsg:'显示第{0}到{1}条记录，共{2}条记录',
				emptyMsg:'没有记录'
			}),
			tbar: [
				refreshBtn,'-',
				addBtn,'-',
				updateBtn,'-',
				deleteBtn,'->',
				'部门:',
				searchDepartmentComboBox,
				searchBtn
			]
		});
		
	},
	//-----------------模块销毁函数---------------------------
	destroy:function(){
	}

});
