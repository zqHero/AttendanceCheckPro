Ext.namespace('Xietong.tips');

Xietong.tips.TipsStore = Ext.extend(Ext.data.Store, {
	
	proxy: new Ext.data.HttpProxy({
		url:'module/common/listTips.do'
	}),
	reader:new Ext.data.JsonReader({
		root:'root',
		totalProperty:'totalCount'
	},[
		{name:'id'},
		{name:'tipName'}
	])
});

Xietong.tips.TipsComboBox = Ext.extend(Ext.form.ComboBox, {
	store:new Xietong.tips.TipsStore({autoLoad:true}),
	fieldLabel:'系统提示',
	name:'systemTips',
	hiddenName:'systemTips',
	anchor:'90%',
	valueField:'id',
	displayField:'tipName',
	mode:'local',
	editable: false,
	triggerAction: 'all', 
	allowBlank:false, 
	emptyText :'--请选择--'
});