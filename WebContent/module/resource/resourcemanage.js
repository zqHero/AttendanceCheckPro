

Ext.namespace('Xietong.resource');

Xietong.resource.ResourceManage = function(){
	this.init();
};

Ext.extend(Xietong.resource.ResourceManage,Ext.util.Observable,{
	init:function(){
		
		var roleStore=new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({
				url:'module/resource/listRole.do'
			}),
			reader:new Ext.data.JsonReader({
				root:'root',
				totalProperty:'totalCount'
			},[
				{name:'id'},
				{name:'description'}
			])
		});
		roleStore.setDefaultSort('id', 'desc');
		roleStore.load();
		
		
		var array = new Array();
		var array2 = new Array();
		
		
		var roleSm = new Ext.grid.CheckboxSelectionModel({
			header:'<div class="x-grid3-hd-checker"></div>'
		});
		
		var roleCm =  new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(),{
				header:'编号',
				dataIndex:'id',
				hidden:true,
				sortable:true
			},{
				header:'角色',
				dataIndex:'description'
			},
			roleSm
		]);
		
		var restoreBtn = new Ext.Button({
			text:'重置',
			iconCls:'icon-reset',
			handler:function(){
				roleSm.selectRows(array);
			}
		});
		
		var updateBtn = new Ext.Button({
			text:'保存权限修改',
			iconCls:'icon-edit',
			handler:function() {
				if(!sm.getSelected()) return;
				var records = roleSm.getSelections();
				var roles = '';
				for (var i = 0; i < records.length; i++) {
					if(i != 0) roles += ',';
				 	roles += records[i].get('id');
				}
				var resourceId = sm.getSelected().get('id');
				Ext.lib.Ajax.request(
					'POST',
					'module/resource/update.do',
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
					'roles=' + encodeURIComponent(roles) + '&resourceId=' + encodeURIComponent(resourceId)
				);
			}
		});
		
		var roleGrid = new Ext.grid.GridPanel({
			title:'所需具备的角色',
			iconCls:'icon-grid',
			disableSelection:true,
			cm:roleCm,
			sm:roleSm,
			store:roleStore,
			loadMask: {msg:'正在加载数据，请稍候......'},
			viewConfig:{ forceFit:true  },
			bbar:['->', restoreBtn,'-',updateBtn],
			listeners:{
				'cellclick':function(grid, rowIndex, columnIndex, e){
					if(columnIndex == 2) roleSm.selectRows(array2);
					
				},
				'cellmousedown':function(grid, rowIndex, columnIndex, e){
					if(columnIndex == 2) roleSm.selectRows(array2);
				}
			}
		});
		
		
		var ds=new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({
				url:'module/resource/list.do'
			}),
			reader:new Ext.data.JsonReader({
				root:'root',
				totalProperty:'totalCount'
			},[
				{name:'id'},
				{name:'value'},
				{name:'description'},
				{name:'roles'}
			])
		});
		ds.setDefaultSort('id', 'desc');
		
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
		var cm =  new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(),{ 
				header:'编号',
				dataIndex:'id',
				hidden:true,
				sortable:true
			},{
				header:'<div style="text-align:center">功能点</div>',
				dataIndex:'description'
			}
		]);
		
		var resourceGrid = new Ext.grid.GridPanel({
			cm:cm,
			sm:sm,
			store:ds,
			loadMask: {msg:'正在加载数据，请稍候......'},
			viewConfig:{ forceFit:true  }			
		});
		sm.on('rowselect',function(sm,rowIndex,record){
			var roles = record.get('roles');
			array = [];
			array2 = [];
			for (var i = 0; i < roles.length; i++) {
				var role = roles[i];
				var index = roleStore.find('id',role.id);
				if(index != -1) { array.push(index); array2.push(index); }
			}
			roleSm.selectRows(array);
			roleGrid.setTitle("'" + record.get('description') + "' 所需具备的角色");
		});
		
		var main = new Ext.Panel({
			renderTo:'daka-resourcemanage-main',
			iconCls:'icon-grid',
			width:Ext.get("daka-resourcemanage-main").getWidth(),
			height:Ext.get("daka-resourcemanage-main").getHeight(),
		    layout:'border',
		    border:false,
		    items: [{
		    	title:'',
		    	region:'center',
		    	border:false,
		    	layout:'fit',
		    	items:[resourceGrid]
		    },{
		    	title:'',
		    	region:'east',
		    	border:false,
		    	width:Ext.get("daka-resourcemanage-main").getWidth()*0.5,
		    	layout:'fit',
		    	items:[roleGrid]
		    }]
		});
		
		ds.on('beforeload', function() {//Ext.data.JsonStore读入数据之前的事件,store不需要在写baseParams,因为会覆盖掉. (每次调用都载入此函数,'load'则第一次不调用外,其余都调用).
			ds.baseParams = {
		 	};
		});
		
		ds.load({params:{
			start:0,
			limit:12
		}});
	},
	//-----------------模块销毁函数---------------------------
	destroy:function(){
	}
	
});
