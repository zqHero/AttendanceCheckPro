$import('module/common/PersonManage.js'); 

Ext.namespace('Xietong.role');

Xietong.role.RoleManage = function(){
	this.init();
};

Ext.extend(Xietong.role.RoleManage,Ext.util.Observable,{
	init:function(){
		
		var ds=new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({
				url:'module/role/list.do'
			}),
			reader:new Ext.data.JsonReader({
				root:'root',
				totalProperty:'totalCount'
			},[
				{name:'id'},
				{name:'description'}
			]),
			autoLoad:true
		});
		ds.setDefaultSort('id', 'desc');
		
		var sm = new Ext.grid.CheckboxSelectionModel({
			singleSelect :true,
			listeners:{
				'rowselect':function(sm, rowIndex, record){
					userStore.baseParams.roleId = record.get('id');
					userStore.load();
					userGrid.setTitle("属于 '" + record.get('description') + "' 的用户"); 
				}
			}
		});
		
		var cm =  new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(),{
				dataIndex:'id',
				hidden:true,
				sortable:true
			},{
				header:'角色',
				dataIndex:'description'
			},
			sm
		]);
		
		var refreshBtn = new Ext.Button({
			text:'刷新',
			iconCls:'icon-refresh',
			handler:function(){ds.reload()}
		});
		
		var addBtn = new Ext.Button({
			text:'新增',
			iconCls:'icon-add',
			handler:function() {
				Ext.MessageBox.prompt("输入","请输入新增角色:",function(btn, text){
				    if (btn == 'ok'){
				        if(text.trim() == '') {Ext.MessageBox.alert('错误','角色名不能为空'); return;}
				        Ext.Ajax.request({
						   url: 'module/role/save.do',
						   success: function(response){Ext.MessageBox.alert('信息',response.responseText,function(){ds.reload();});},
						   failure: function(){Ext.MessageBox.alert('信息','与后台联系错误')},
						   params: { roleDesc: text }
						});
				    }
				});
			}
		});
		
		var deleteBtn = new Ext.Button({
			text:'删除',
			iconCls:'icon-delete',
			handler:function() {
				if(!sm.selections){Ext.MessageBox.alert('错误','没有选中任何一项'); return;}
		        Ext.Ajax.request({
				   url: 'module/role/delete.do',
				   success: function(response){Ext.MessageBox.alert('信息',response.responseText,function(){ds.reload()});},
				   failure: function(){Ext.MessageBox.alert('信息','与后台联系错误')},
				   params: { roleId: sm.getSelected().get('id') }
				});
			}
		});
		
		var roleGrid = new Ext.grid.GridPanel({
			cm:cm,
			sm:sm,
			store:ds,
			loadMask: {msg:'正在加载数据，请稍候......'},
			viewConfig:{ forceFit:true  },
			tbar: [refreshBtn,'-',addBtn,'-',deleteBtn]
		});
		
		
		var userStore=new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({ url:'module/role/listUsers.do' }),
			reader:new Ext.data.JsonReader({root:'root', totalProperty:'totalCount' },[{name:'id'},{name:'userName'}])
		});
		userStore.setDefaultSort('id', 'desc');
		var userSm = new Ext.grid.CheckboxSelectionModel({singleSelect :true});
		var userCm =  new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(),{
				dataIndex:'id',
				hidden:true,
				sortable:true
			},{
				header:'用户名',
				dataIndex:'userName'
			}
		]);
		
		var personTrigger = new Xietong.person.PersonTrigger({
			width:300
		});
		var addUserWindow = new Ext.Window({
			layout:'fit',
			title:'请输入一个已存在的用户名',
			closeAction:'hide',
			modal:true,
			width:400,
			items:[personTrigger],
			buttons:[new Ext.Button({
				text:'添加',
				handler:function(){
					if(personTrigger.isValid()) {
						Ext.Ajax.request({
						   url: 'module/role/update.do',
						   success: function(response){Ext.MessageBox.alert('信息',response.responseText,function(){userStore.reload()});},
						   failure: function(){Ext.MessageBox.alert('信息','与后台联系错误')},
						   params: { roleId: sm.getSelected().get('id'), userName:personTrigger.getValue(),method:'add' }
						});
						addUserWindow.hide();
					}
				}
			}),new Ext.Button({
				text:'取消',
				handler:function(){
					personTrigger.reset();
					addUserWindow.hide(addUserBtn)
				}
			})]
		});
		var addUserBtn = new Ext.Button({
			text:'添加用户',
			iconCls:'icon-add',
			handler:function(){
				if(sm.getSelected()) addUserWindow.show(this);
			}
		});
		
		var deleteUserBtn = new Ext.Button({
			text:'删除用户',
			iconCls:'icon-delete',
			handler:function(){
				 if(!sm.getSelected()) return;
				 if(!userSm.getSelected()) {Ext.MessageBox.alert('信息','没有任何用户被选中！'); return; }
				 Ext.MessageBox.confirm("警告","确认删除所选记录？",function(e){
					if(e == 'yes') {
						 Ext.Ajax.request({
							url : 'module/role/update.do',
							success:function(response) {
								Ext.MessageBox.alert("信息",response.responseText,function(){
									userStore.reload();
								});
							},
							failure:function(){
								Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
							},
							params:{roleId:sm.getSelected().get('id'), userId:userSm.getSelected().get('id'),method:'delete'}
						});
					} else {
						return;
					}
				});
			}
		});
		
		var userGrid = new Ext.grid.GridPanel({
			title:'属于角色的用户',
			iconCls:'icon-grid',
			cm:userCm,
			sm:userSm,
			store:userStore,
			loadMask: {msg:'正在加载数据，请稍候......'},
			viewConfig:{ forceFit:true  },
			tbar:[addUserBtn,'-',deleteUserBtn]
		});
		
		var mainGrid = new Ext.Panel({
			renderTo:'daka-rolemanage-main',
			width:Ext.get("daka-rolemanage-main").getWidth(),
			height:Ext.get("daka-rolemanage-main").getHeight(),
			layout:'border',
			border:false,
			items:[{
				region:'west',
				layout:'fit',
				border:false,
				width:Ext.get("daka-rolemanage-main").getWidth()*0.4,
				items:[roleGrid]
			},{
				region:'center',
				layout:'fit',
				border:false,
				items:[userGrid]
			}]
		});
		
	},
	//-----------------模块销毁函数---------------------------
	destroy:function(){
	}
	
});
