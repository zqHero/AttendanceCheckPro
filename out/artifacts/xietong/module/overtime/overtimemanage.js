
$import('js/ext-2.0/custom/Datetime/Datetime.js');
$import('module/common/PersonManage.js');

Ext.namespace('Xietong.manage');


Xietong.manage.OverTimeManage = function(){
	this.init();
};

Ext.extend(Xietong.manage.OverTimeManage,Ext.util.Observable,{
	init:function(){
		
		var personTrigger = new Xietong.person.PersonTrigger({
			fieldLabel:'用户名',
			name:'userName',
			hiddenName:'userName'	
		}); 
		
		var ds= new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({
				url:'module/overtime/list.do'
			}),
			reader:new Ext.data.JsonReader({
				root:'root',
				totalProperty:'totalCount'
			},[
				{name : 'id'}, 
				{name : 'userId'},
				{name : 'userName'},  
				{name : 'startTime',type : 'date',dateFormat:'Y-m-d H:i:s'}, 
				{name : 'endTime',type : 'date',dateFormat:'Y-m-d H:i:s'}, 
				{name : 'reason'}
			])
		});
		ds.setDefaultSort('id', 'desc');
		
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
		var cm =  new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(),{
				header:'编号',
				dataIndex:'id',
				width:60,
				hidden:true,
				sortable:true
			},{
				header:'姓名',
				dataIndex:'userName',
				width:60,
				sortable:true
			},{
				header:'开始时间',
				dataIndex:'startTime',
				width:60,
				sortable:true,
				renderer:function(v) {
					if(v == null || v=='') return '';
					return v.format('Y-m-d H:i');
				}
			},{
				header:'结束时间',
				dataIndex:'endTime',
				width:60,
				sortable:true,
				renderer:function(v) {
					if(v == null || v=='') return '';
					return v.format('Y-m-d H:i');
				}
			},{
				header:'原因',
				dataIndex:'reason' 
			},
			sm
		]);
		
		var refreshBtn = new Ext.Button({
			text:'刷新',
			iconCls:'icon-refresh',
			handler:function() {
				ds.reload();
			}
		});
		
		var addOverTimeWindow = null;
		
		var addOverTimePanel = new Ext.FormPanel({
			layout:'form',
			frame:true,
			labelAlign: 'right', 
			url:'module/overtime/save.do',
			defaultType:'textfield',
			items:[ personTrigger, {
				fieldLabel:'开始时间',
				name:'startTime',
				xtype:'datetimefield',
				format:'Y-m-d H:i:s',
				allowBlank:false,
				listeners:{
					'blur':function() {
						addOverTimePanel.find('name','endTime')[0].minValue = this.getValue();
					}
				}
			},{
				fieldLabel:'结束时间',
				name:'endTime',
				xtype:'datetimefield',
				format:'Y-m-d H:i:s',
				allowBlank:false,
				listeners:{
					'focus':function(){
						this.minValue = addOverTimePanel.find('name','startTime')[0].getValue();
					}
				}
			},{
				fieldLabel:'原因',
				xtype:'textarea',
				name:'reason',
				anchor: "90%",
				width:150
			}],
			buttons:[{
                text: '保存',
                formBind: true,
                handler: function(){
                	var startTime = addOverTimePanel.find('name','startTime')[0].getValue();
                	var endTime = addOverTimePanel.find('name','endTime')[0].getValue();
                	if(startTime > endTime) {
                		Ext.MessageBox.show({
                			title:'错误',
						    msg: '开始时间不能大于结束时间！',
						    buttons: Ext.Msg.OK,
							icon: Ext.MessageBox.ERROR
                		});
                		return;
                	}
                    if(addOverTimePanel.getForm().isValid()) {
			        	addOverTimePanel.getForm().submit({
			        		waitMsg:'保存中,请稍后...',
			        		success:function(form,action){
				        		addOverTimePanel.getForm().reset();
				        		addOverTimeWindow.hide();
				        		Ext.Msg.alert('信息',action.result.msg);
				        		ds.reload();
			        		}
			        	});
                    }
                }
            }, {
                text: '取消',
                handler: function(){
                    addOverTimePanel.form.reset();
					addOverTimeWindow.hide();
                }
            }]
		});
		
		var addBtn = new Ext.Button({
			text:'新增',
			iconCls:'icon-add',
			handler:function() {
				if(!addOverTimeWindow) {
					addOverTimeWindow = new Ext.Window({
						title:'添加特殊情况',
						modal:true,
					    closeAction:'hide',
						width:500,
						items:[addOverTimePanel]
					});
				}
				addOverTimeWindow.show(Ext.get('addBtn'));
			}
		});
		
		var deleteBtn = new Ext.Button({
			text:'删除',
			iconCls:'icon-delete',
			handler:function() {
				 if(!sm.getSelected()) {Ext.MessageBox.alert('信息','没有任何记录被选中！'); return;}
				 Ext.MessageBox.confirm("警告","确认删除所选记录？",function(e){
					if(e == 'yes') {
						 Ext.Ajax.request({
							url:'module/overtime/delete.do',
							success:function(response) {
								Ext.MessageBox.alert("信息",response.responseText,function(){ ds.reload(); });
							},
							failure:function(){ Ext.MessageBox.alert("错误","与后台联系的时候出了问题"); },
							params:{overTimeId:sm.getSelected().get('id')}
						 });
					}
				});
			}
		});
		
		var mainGrid = new Ext.grid.EditorGridPanel({
			renderTo:'daka-overtimemanage-main',
			width:Ext.get("daka-overtimemanage-main").getWidth(),
			height:Ext.get("daka-overtimemanage-main").getHeight(),
			clicksToEdit:1,
			cm:cm,
			sm:sm,
			store:ds,
			loadMask: {msg:'正在加载数据，请稍候......'},
			viewConfig:{ forceFit:true  },
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
				deleteBtn,'-'
			]
		});
		
		
		ds.load({params:{
			start:0,
			limit:25
		}});
		
		
		
	},
	//-----------------模块销毁函数---------------------------
	destroy:function(){
	}

});
