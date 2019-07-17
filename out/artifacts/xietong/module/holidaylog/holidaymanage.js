
$import('js/ext-2.0/custom/Datetime/Datetime.js');
$import('module/common/PersonManage.js'); 
$import('module/common/HolidayManage.js');

Ext.namespace('Xietong.manage');


Xietong.manage.SpecialManage = function(){
	this.init();
};

Ext.extend(Xietong.manage.SpecialManage,Ext.util.Observable,{
	init:function(){
		
		var personTrigger = new Xietong.person.PersonTrigger({
			fieldLabel:'用户名',
			name:'userName',
			hiddenName:'userName'	
		}); 
		
		var holidayComboBox = new Xietong.holiday.HolidayComboBox();
		
		var ds=new Xietong.holiday.HolidayLogStore();
		ds.setDefaultSort('id', 'desc');
		
		holidayComboBox.store.load({callback:function(){
			ds.load({params:{
				start:0,
				limit:12
			}});
		}});
		
		
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
				dataIndex:'realName',
				width:60,
				sortable:true
			},{
				header:'类型',
				dataIndex:'holidayId',
				width:60,
				renderer:function(v) {
					var index = holidayComboBox.store.find('holidayId',v);
					return holidayComboBox.store.getAt(index).get('holidayName');
				}/*,
				editor:new Xietong.holiday.HolidayComboBox()*/
			},{
				header:'开始时间',
				dataIndex:'startTime',
				width:60,
				sortable:true,
				/*editor:new DatetimeField({
					readOnly:true
				}),*/
				renderer:function(v) {
					if(v == null || v=='') return '';
					return v.format('Y-m-d H:i');
				}
			},{
				header:'结束时间',
				dataIndex:'endTime',
				width:60,
				sortable:true,
				/*editor:new DatetimeField({
					readOnly:true
				}),*/
				renderer:function(v) {
					if(v == null || v=='') return '';
					return v.format('Y-m-d H:i');
				}
			},{
				header:'原因',
				dataIndex:'reason'/*,
				editor:new Ext.form.TextField({
					allowBlank:false
				})*/
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
		
		var addHolidayLogWindow = null;
		
		var addHolidayLogPanel = new Ext.FormPanel({
			layout:'form',
			frame:true,
			labelAlign: 'right', 
			url:'module/holidaylog/save.do',
			defaultType:'textfield',
			items:[ personTrigger, holidayComboBox, {
				fieldLabel:'开始时间',
				name:'startTime',
				xtype:'datetimefield',
				allowBlank:false,
				listeners:{
					'blur':function() {
						addHolidayLogPanel.find('name','endTime')[0].minValue = this.getValue();
					}
				}
			},{
				fieldLabel:'结束时间',
				name:'endTime',
				xtype:'datetimefield',
				allowBlank:false,
				listeners:{
					'focus':function(){
						this.minValue = addHolidayLogPanel.find('name','startTime')[0].getValue();
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
                	var startTime = addHolidayLogPanel.find('name','startTime')[0].getValue();
                	var endTime = addHolidayLogPanel.find('name','endTime')[0].getValue();
                	if(startTime > endTime) {
                		Ext.MessageBox.show({
                			title:'错误',
						    msg: '开始时间不能大于结束时间！',
						    buttons: Ext.Msg.OK,
							icon: Ext.MessageBox.ERROR
                		});
                		return;
                	}
                    if(addHolidayLogPanel.getForm().isValid()) {
			        	addHolidayLogPanel.getForm().submit({
			        		waitMsg:'保存中,请稍后...',
			        		success:function(form,action){
				        		addHolidayLogPanel.getForm().reset();
				        		addHolidayLogWindow.hide();
				        		Ext.Msg.alert('信息',action.result.msg);
				        		ds.reload();
			        		}
			        	});
                    }
                }
            }, {
                text: '取消',
                handler: function(){
                    addHolidayLogPanel.form.reset();
					addHolidayLogWindow.hide();
                }
            }]
		});
		
		var addBtn = new Ext.Button({
			text:'新增',
			iconCls:'icon-add',
			handler:function() {
				if(!addHolidayLogWindow) {
					addHolidayLogWindow = new Ext.Window({
						title:'添加特殊情况',
						modal:true,
					    closeAction:'hide',
						width:500,
						items:[addHolidayLogPanel]
					});
				}
				addHolidayLogWindow.show(Ext.get('addBtn'));
			}
		});
		
		var updateBtn = new Ext.Button({
			text:'保存修改',
			iconCls:'icon-edit',
			handler:function() {
				/*var m = ds.modified.slice(0);
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
					'module/holidaylog/update.do',
					{
						success:function(response) {
							Ext.MessageBox.alert("信息",response.responseText,function(){ ds.reload(); ds.modified = []; });
						},
						failure:function(){
							Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
						}
					},
					'data=' + encodeURIComponent(Ext.encode(jsonArray))
				);*/
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
							url:'module/holidaylog/delete.do',
							success:function(response) {
								Ext.MessageBox.alert("信息",response.responseText,function(){ ds.reload(); });
							},
							failure:function(){ Ext.MessageBox.alert("错误","与后台联系的时候出了问题"); },
							params:{id:sm.getSelected().get('id')}
						 });
					}
				});
			}
		});
		
		var mainGrid = new Ext.grid.EditorGridPanel({
			renderTo:'daka-specialmanage-main',
			width:Ext.get("daka-specialmanage-main").getWidth(),
			height:Ext.get("daka-specialmanage-main").getHeight(),
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
		
		
		
	},
	//-----------------模块销毁函数---------------------------
	destroy:function(){
	}

});
