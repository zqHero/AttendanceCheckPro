
Ext.namespace('Xietong.person');

Xietong.person.PersonStore = Ext.extend(Ext.data.Store, {
	proxy: new Ext.data.HttpProxy({
		url:'mocule/common/listUser.do'
	}),
	reader:new Ext.data.JsonReader({
		root:'root',
		totalProperty:'totalCount'
	},[
		{name : 'id'}, 
		{name : 'userName'}, 
		{name : 'password'}, 
		{name : 'realName'}, 
		{name : 'gender'}, 
		{name : 'birthday',type : 'date',dateFormat:'Y-m-d'}, 
		{name : 'school'}, 
		{name : 'specialty'}, 
		{name : 'departmentId'}, 
		{name : 'departmentName'}, 
		{name : 'superiorsId'},
		{name : 'superiorsName'}
	])
});

Xietong.person.PersonComboBox = Ext.extend(Ext.form.ComboBox, {
	fieldLabel:'上级',
	name:'superiorsId',
	anchor:'90%',
	store: new Xietong.person.PersonStore(),
	valueField:'id',
	displayField:'realName',
	triggerAction : 'all',
	mode:'local',
	editable: false,
	triggerAction: 'all', 
	allowBlank:false, 
	emptyText :'--请选择--'
});



