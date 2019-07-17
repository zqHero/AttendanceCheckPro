$import('module/common/DepartmentComboBox.js');
$import('js/Ext.ux/Ext.ux.YearPicker.js');

Ext.namespace('Xietong.daka');

Xietong.daka.YearCollect = function(){
	this.init();
};

Ext.extend(Xietong.daka.YearCollect,Ext.util.Observable,{
	init:function(){
		
		var departmentCombo_year = new Xietong.department.DepartmentComboBox({
			listeners:{
				'expand':function(combo){
					combo.store.insert(0,new Department({id:0,departmentName:'所有部门'}));
				},
				'collapse': function(combo) {
					combo.store.remove(combo.store.getAt(0));
				}
			}
		});
		departmentCombo_year.store.load();
		
	/* ------------yearCollect----------- */
		var yearCollectDs=new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({
				url:'module/daka/yearcollect.do'
			}),
			reader:new Ext.data.JsonReader({
				root:'root',
				totalProperty:'totalCount'
			},[
				{name:'id'},
				{name:'userId'},
				{name:'realName'},
				{name:'workTime'},
				{name:'overTime'},
				{name:'businessTime'},
				{name:'sickTime'},
				{name:'vacationTime'},
				{name:'countOfLate'},
				{name:'countOfEO'},
				{name:'year'}
			]),
			listeners:{
				'beforeload':function(){
					this.baseParams.year = yearField.getValue().format('Y')
				}
			}
		});
		yearCollectDs.setDefaultSort('id', 'desc');
		
		var yearCollectSm = new Ext.grid.CheckboxSelectionModel();
		var yearCollectCm =  new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(),{
				header:'编号',
				dataIndex:'userId',
				hidden:true,
				sortable:true
			},{
				header:'时间',
				dataIndex:'year',
				width:100,
				renderer:function(v){return v+"年"}
			},{
				header:'员工姓名',
				dataIndex:'realName',
				width:100
			},{
				header:'工作时间(天)',
				width:200,
				dataIndex:'workTime',
				sortable:true,
				renderer:function(v){
					return (v/60/60/7.5).toFixed(2);
				}
			},{
				header:'加班时间(小时)',
				width:200,
				dataIndex:'overTime',
				sortable:true,
				renderer:function(v){
					return (v/60/60).toFixed(2);
				}
			},{
				header:'事假时间(小时)',
				width:200,
				dataIndex:'businessTime',
				sortable:true,
				renderer:function(v){
					return (v/60/60).toFixed(2);
				}
			},{
				header:'病假时间(小时)',
				width:200,
				dataIndex:'sickTime',
				sortable:true,
				renderer:function(v){
					return (v/60/60).toFixed(2);
				}
			},{
				header:'休假时间(小时)',
				width:200,
				dataIndex:'vacationTime',
				sortable:true,
				renderer:function(v){
					return (v/60/60).toFixed(2);
				}
			},{
				header:'迟到次数',
				width:200,
				dataIndex:'countOfLate',
				sortable:true
			},{
				header:'早退次数',
				width:200,
				dataIndex:'countOfEO',
				sortable:true
			},
			yearCollectSm
		]);
		
		var yearRefreshBtn = new Ext.Button({
			text:'刷新',
			iconCls:'icon-refresh',
			handler:function() {
				yearCollectDs.reload();
			}
		});
		
		var yearField = new Ext.ux.YearField({
			format:'Y',
			readOnly:true,
			allowBlank:false,
			value:new Date().add(Date.YEAR,-1)
		});
		
		
		var yearSearchBtn = new Ext.Button({
			text:'查询',
			iconCls:'icon-search',
			handler:function() {
				if(departmentCombo_year.isValid()) {
					yearCollectDs.reload({params:{
						start:0,
						limit:12,
						departmentId:departmentCombo_year.getValue()
					}});
				}
			}
		});
		
		var yearCollectGrid = new Ext.grid.GridPanel({
			renderTo:'daka-yearcollect-main',
			height:Ext.get("daka-yearcollect-main").getHeight(),
			width:Ext.get("daka-yearcollect-main").getWidth(),
			border:false,
			cm:yearCollectCm,
			sm:yearCollectSm,
			store:yearCollectDs,
			loadMask: {msg:'正在加载数据，请稍候......'},
			viewConfig:{ forceFit:true  },
			bbar: new Ext.PagingToolbar({
				pageSize:12,
				store:yearCollectDs,
				displayInfo:true,
				displayMsg:'显示第{0}到{1}条记录，共{2}条记录',
				emptyMsg:'没有记录'
			}),
			tbar: [
				yearRefreshBtn, '-',
				'年:',yearField,'-',
				'部门名称:',
				departmentCombo_year,'&nbsp;&nbsp;',
				yearSearchBtn
			],
			listeners:{
				cellClick:function(grid, rowIndex, columnIndex, e) {
					if(columnIndex==10) {
						var record = this.getStore().getAt(rowIndex); 
						yearDetailDs.load({
							params:{
								userid:record.get("userId"),
								start:0,
								limit:5
							}
						});
					}
			    }
			}
		});
		
		yearCollectDs.load({params:{
			start:0,
			limit:12
		}});
		
	},
	//-----------------模块销毁函数---------------------------
	destroy:function(){
	}

});
