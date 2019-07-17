
$import('js/Ext.ux/Ext.ux.MonthPicker.js')
$import('module/common/DepartmentComboBox.js');
$import('module/common/TipsStore.js');
$import('module/common/PersonManage.js');


Ext.namespace('Xietong.daka');

Xietong.daka.DakaManage = function(){
	this.init();
};

Ext.extend(Xietong.daka.DakaManage,Ext.util.Observable,{
	init:function(){
		
		var a = this;
		var departmentComboBox = new Xietong.department.DepartmentComboBox({
			listeners:{
				'expand':function(combo){
					combo.store.insert(0,new Department({id:0,departmentName:'所有部门'}));
				},
				'collapse': function(combo) {
					combo.store.remove(combo.store.getAt(0));
				}
			}
		});
		departmentComboBox.store.load();
		
		var personTrigger = new Xietong.person.PersonTrigger({width:150});
		
		var monthField = new Ext.ux.MonthField({
			format:'Y-m',
			readOnly:true,
			allowBlank:false,
			value:new Date()
		});
		
		
		var tipsStore = new Xietong.tips.TipsStore();
		
		var monthDetailDs=new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({
				url:'module/daka/list.do'
			}),
			reader:new Ext.data.JsonReader({
				root:'root',
				totalProperty:'totalCount'
			},[
				{name:'id'},
				{name:'userid'},
				{name:'dakaDate'},
				{name:'username'},
				{name:'date1'},
				{name:'date2'},
				{name:'date3'},
				{name:'date4'},
				{name:'tip1'},
				{name:'tip2'},
				{name:'tip3'},
				{name:'tip4'}
			]),
			listeners:{
				'beforeload':function(){
					this.baseParams.month = monthField.getValue().format('Y-m') 
				}
			},
			sortInfo: {field: "dakaDate", direction: "ASC"} 
		});
		
		tipsStore.load({callback:function(){
			monthDetailDs.load({
				params:{
					start:0,
					departmentId:0
				}
			});
		}});
		
		var monthDetailSm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
		var monthDetailCm =  new Ext.grid.ColumnModel([{
				header:'编号',
				dataIndex:'id',
				hidden:true
			},{
				header:'员工编号',
				dataIndex:'userid',
				hidden:true
			},{
				header:'日期',
				dataIndex:'dakaDate',
				width:90,
				sortable:true
			},{
				header:'员工',
				dataIndex:'username',
				width:80,
				sortable:true
			},{
				header:'时间1',
				width:80,
				dataIndex:'date1',
				editor:new Ext.form.TimeField({format:'H:i:s',allowBlank:false})
			},{
				header:'系统提示',
				dataIndex:'tip1',
				width:70,
				renderer:function(v){
					var index = tipsStore.find('id',v);
					if(index == -1) return '--';
					var tip1 = tipsStore.getAt(index).get('tipName');
					if(v == 2 || v == 3 || v == 4) return "<font color='red'>" + tip1 + "</font>";
					if(v == 8 || v == 9 || v == 10) return "<font color='blue'>" + tip1 + "</font>";
					return tip1;
				},
				editor:new Xietong.tips.TipsComboBox()
			},{
				header:'时间2',
				width:80,
				dataIndex:'date2',
				editor:new Ext.form.TimeField({format:'H:i:s',allowBlank:false})
			},{
				header:'系统提示',
				dataIndex:'tip2',
				width:70,
				renderer:function(v){
					var index = tipsStore.find('id',v);
					if(index == -1) return '--';
					var tip2 = tipsStore.getAt(index).get('tipName');
					if(v == 5) return "<font color='red'>" + tip2 + "</font>";
					if(v == 8 || v == 9 || v == 10) return "<font color='blue'>" + tip2 + "</font>";
					return tip2;
				},
				editor:new Xietong.tips.TipsComboBox()
			},{
				header:'时间3',
				width:80,
				dataIndex:'date3',
				editor:new Ext.form.TimeField({format:'H:i:s',allowBlank:false})
			},{
				header:'系统提示',
				dataIndex:'tip3',
				width:70,
				renderer:function(v){
					var index = tipsStore.find('id',v);
					if(index == -1) return '--';
					var tip3 = tipsStore.getAt(index).get('tipName');
					if(v == 2 || v == 3 || v == 4) return "<font color='red'>" + tip3 + "</font>";
					if(v == 8 || v == 9 || v == 10) return "<font color='blue'>" + tip3 + "</font>";
					return tip3;
				},
				editor:new Xietong.tips.TipsComboBox()
			},{
				header:'时间4',
				width:80,
				dataIndex:'date4',
				editor:new Ext.form.TimeField({format:'H:i:s',allowBlank:false})
			},{
				header:'系统提示',
				dataIndex:'tip4',
				width:70,
				renderer:function(v){
					var index = tipsStore.find('id',v);
					if(index == -1) return '--';
					var tip4 = tipsStore.getAt(index).get('tipName');
					if(v == 5) return "<font color='red'>" + tip4 + "</font>";
					if(v == 8 || v == 9 || v == 10) return "<font color='blue'>" + tip4 + "</font>";
					return tip4;
				},
				editor:new Xietong.tips.TipsComboBox()
			},
			monthDetailSm
		]);

		var monthDetailRefreshBtn = new Ext.Button({
			text:'刷新',
			iconCls:'icon-refresh',
			handler:function() {
				monthDetailDs.reload();
			}
		});
		
		var saveUpdateBtn_monthDetail = new Ext.Button({
			text:'保存修改',
			iconCls:'icon-edit',
			handler:function() {
				var m = monthDetailDs.modified.slice(0);
				if(m.length == 0) {
					Ext.Msg.show({
						title: "警告",
						msg:"没有任何记录被修改！",
						buttons:Ext.MessageBox.OK,
						icon: Ext.MessageBox.WARNING
					});
					return;
				}
				var jsonArray = [];
				Ext.each(m,function(item){
					jsonArray.push(item.data);
				});
				Ext.lib.Ajax.request('POST','module/daka/updateDakaLog.do', //Ajax提交保存修改
					{
						success:function(response) {
							Ext.Msg.show({
								title: "信息",
								msg:response.responseText,
								buttons:Ext.MessageBox.OK,
								icon: Ext.MessageBox.INFO,
								fn:function(){
									monthDetailDs.modified = []; /* 避免重复提交 */
									monthDetailDs.reload();
									/* 检查是否要重新统计 */
									checkStatistic();
								}
							});
						},
						failure:function(){
							Ext.Msg.show({
								title: "错误",
								msg:"与后台联系的时候出了问题，请联系管理员！",
								buttons:Ext.MessageBox.OK,
								icon: Ext.MessageBox.ERROR
							});
						}
					},
					'data=' + encodeURIComponent(Ext.encode(jsonArray))
				);
			}
		});
		
		var searchBtn_dept = new Ext.Button({
			text:'按部门查询',
			iconCls:'icon-search',
			handler:function() {
				monthDetailDs.baseParams.departmentId = departmentComboBox.getValue();
				monthDetailDs.baseParams.userName = null;
				monthDetailDs.reload();
			}
		});
		
		var searchBtn_user = new Ext.Button({
			text:'按员工查询',
			iconCls:'icon-search',
			handler:function() {
				if(!personTrigger.isValid()) return;
				monthDetailDs.baseParams.departmentId = null;
				monthDetailDs.baseParams.userName = personTrigger.getValue();
				monthDetailDs.reload();
			}
		});
		
		var deleteBtn = new Ext.Button({
			text:'删除',
			iconCls:'icon-delete',
			handler:function() {
				if(!monthDetailSm.getSelected()) Ext.MessageBox.alert("警告","没有选中任何一项");
				if(!monthDetailSm.getSelected().get('id')){ Ext.MessageBox.alert("警告","没有打卡记录，无需删除。"); return;} 
				Ext.Ajax.request({
					url:'module/daka/delete.do',
					success: function(response){Ext.MessageBox.alert("信息",response.responseText,function(){monthDetailDs.reload()});},
				    failure: function(response){Ext.MessageBox.alert("错误",response.responseText);},
				    params: { dakaLogId: monthDetailSm.getSelected().get('id') }
				});
			}
		});
		
		var monthDetailGrid = new Ext.grid.EditorGridPanel({
			renderTo:'daka-dakamanage-main',
			height:Ext.get("daka-dakamanage-main").getHeight(),
			width:Ext.get("daka-dakamanage-main").getWidth(),
			cm:monthDetailCm,
			sm:monthDetailSm,
			store:monthDetailDs,
			stripeRows :true,
			trackMouseOver:true,
			clicksToEdit:1,
			loadMask: {msg:'正在加载数据，请稍候......'},
			viewConfig:{ forceFit:true  },
			tbar:[
				monthDetailRefreshBtn, '-',
				saveUpdateBtn_monthDetail,'-',
				deleteBtn,'-',
				'月份:',monthField,
				new Ext.Button({
					text:'查询',
					iconCls:'icon-search',
					handler:function(){
						monthDetailDs.baseParams.departmentId = 0;
						monthDetailDs.baseParams.userName = null;
						monthDetailDs.reload();
					}
				}),'-',
				'->'
			],
			bbar:[
				departmentComboBox,searchBtn_dept,'-','&nbsp;&nbsp;&nbsp;',
				personTrigger,searchBtn_user,'-'
			]
		});
		
				
		function checkStatistic() {
			Ext.lib.Ajax.request('POST','module/daka/hasStatistic.do', 
			{
				success:function(response) {
					if(response.responseText == 'true'){
						Ext.Msg.show({
							title: "问题",
							msg:"该月份数据已经统计过，是否要重新统计？",
							buttons:Ext.MessageBox.YESNO,
							icon: Ext.MessageBox.QUESTION,
							fn:function(e){
								if(e == 'no'){
									return;
								} else {
									Ext.Ajax.request({
								    	url: 'module/daka/statistic.do',
								   		success: function(response){
								   			if(response.responseText == 'true') { 
								   				Ext.Msg.show({title: "信息",
													msg:"统计成功",
													buttons:Ext.MessageBox.OK,
													icon: Ext.MessageBox.INFO
												});
								   			} else {
												Ext.Msg.show({
													title: "信息",
													msg:"统计失败，请重新统计",
													buttons:Ext.MessageBox.OK,
													icon: Ext.MessageBox.ERROR
												});
								   			}
								   		},
								   		failure: function(){ 
								   			Ext.Msg.show({
												title: "错误",
												msg:"与后台联系的时候出了问题，请联系管理员！",
												buttons:Ext.MessageBox.OK,
												icon: Ext.MessageBox.ERROR
											});
								   		},
								   		params: { month: monthField.getValue().format('Y-m') }
									});
								}
							}
						});
					} else if(response.responseText != 'false') {
						Ext.Msg.show({
							title: "错误",
							msg:response.responseText,
							buttons:Ext.MessageBox.OK,
							icon: Ext.MessageBox.ERROR
						});
					}
				},
				failure:function(){
					Ext.Msg.show({
						title: "错误",
						msg:"与后台联系的时候出了问题，请联系管理员！",
						buttons:Ext.MessageBox.OK,
						icon: Ext.MessageBox.ERROR
					});
				}
			},
			'month=' + monthField.getValue().format('Y-m')
		);
		}
		
	},
	
	//-----------------模块销毁函数---------------------------
	destroy:function(){
		
	}

});
