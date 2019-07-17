$import('js/Ext.ux/Ext.ux.MonthPicker.js');
$import('module/common/TipsStore.js');

Ext.namespace('Xietong.daka');

Xietong.daka.MonthList = function(){
	this.init();
};

Ext.extend(Xietong.daka.MonthList,Ext.util.Observable,{
	init:function(){
		
		var tipStore = new Xietong.tips.TipsStore();
		
		var ds=new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({
				url:'module/common/monthlist.do'
			}),
			reader:new Ext.data.JsonReader({
				root:'root',
				totalProperty:'totalCount'
			},[
				{name:'id'},
				{name:'dakaDate'},
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
				'beforeload':function() {
					this.baseParams.month = monthField.getValue().format('Y-m');
					this.baseParams.limit = 31;
				}
			}
		});
		ds.setDefaultSort('dakaDate', 'asc');
		
		tipStore.load({callback:function(){
			ds.load({params:{
				start:0,
				month: monthField.getValue().format('Y-m')
			}});
		}});
		
		var cm =  new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(),{
				header:'编号',
				dataIndex:'id',
				hidden:true
			},{
				header:'日期',
				dataIndex:'dakaDate',
				width:100,
				sortable:true
			},{
				header:'时间1',
				width:100,
				dataIndex:'date1'
			},{
				header:'系统提示',
				dataIndex:'tip1',
				width:80,
				renderer:function(v){
					var index = tipStore.find('id',v);
					if(index == -1) return '--';
					var tip1 = tipStore.getAt(index).get('tipName');
					if(v == 2 || v == 3 || v == 4) return "<font color='red'>" + tip1 + "</font>";
					if(v == 8 || v == 9 || v == 10) return "<font color='blue'>" + tip1 + "</font>";
					return tip1;
				}
			},{
				header:'时间2',
				width:100,
				dataIndex:'date2'
			},{
				header:'系统提示',
				dataIndex:'tip2',
				width:80,
				renderer:function(v){
					var index = tipStore.find('id',v);
					if(index == -1) return '--';
					var tip2 = tipStore.getAt(index).get('tipName');
					if(v == 5) return "<font color='red'>" + tip2 + "</font>";
					if(v == 8 || v == 9 || v == 10) return "<font color='blue'>" + tip2 + "</font>";
					return tip2;
				}
			},{
				header:'时间3',
				width:100,
				dataIndex:'date3'
			},{
				header:'系统提示',
				dataIndex:'tip3',
				width:80,
				renderer:function(v){
					var index = tipStore.find('id',v);
					if(index == -1) return '--';
					var tip3 = tipStore.getAt(index).get('tipName');
					if(v == 2 || v == 3 || v == 4) return "<font color='red'>" + tip3 + "</font>";
					if(v == 8 || v == 9 || v == 10) return "<font color='blue'>" + tip3 + "</font>";
					return tip3;
				}
			},{
				header:'时间4',
				width:100,
				dataIndex:'date4'
			},{
				header:'系统提示',
				dataIndex:'tip4',
				width:80,
				renderer:function(v){
					var index = tipStore.find('id',v);
					if(index == -1) return '--';
					var tip4 = tipStore.getAt(index).get('tipName');
					if(v == 5) return "<font color='red'>" + tip4 + "</font>";
					if(v == 8 || v == 9 || v == 10) return "<font color='blue'>" + tip4 + "</font>";
					return tip4;
				}
			}
		]);
		
		var monthField = new Ext.ux.MonthField({
			format:'Y-m',
			readOnly:true,
			allowBlank:false, 
			value:new Date()
		});
		
		var searchBtn = new Ext.Button({
			text:'查询',
			iconCls:'icon-search',
			handler:function() {
				ds.load({params:{
					start:0,
					month: monthField.getValue().format('Y-m')
				}});
			}
		});
		
		
		var mainGrid = new Ext.grid.GridPanel({
			renderTo:'daka-monthlist-main',
			iconCls:'icon-grid',
			width:Ext.get("daka-monthlist-main").getWidth(),
			height:Ext.get("daka-monthlist-main").getHeight(),
			cm:cm,
			store:ds,
			stripeRows :true,
			loadMask: {msg:'正在加载数据，请稍候......'},
			viewConfig:{ forceFit:true  },
			tbar: [
				'选择年月',monthField,searchBtn
			]
		});
		
	},
	//-----------------模块销毁函数---------------------------
	destroy:function(){
	}

});
