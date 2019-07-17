Ext.namespace('Xietong.project');

Xietong.project.ProjectStore = Ext.extend(Ext.data.Store, {
	proxy: new Ext.data.HttpProxy({
		url:'module/common/list.do'
	}),
	reader:new Ext.data.JsonReader({
		root:'root',
		totalProperty:'totalCount'
	},[
		{name : 'id'}, 
		{name : 'projectname'}, 
		{name : 'projectdescription'}
	])
});

/*Xietong.project.ProjectComboBo = Ext.extend(Ext.form.ComboBox, {
	fieldLabel:'所属项目',
	name:'projectId',
	anchor:'90%',
	store: new Xietong.project.ProjectStore(),
	valueField:'id',
	hiddenName:'projectId',
	displayField:'projectname',
	triggerAction : 'all',
	mode:'local',
	editable: false,
	triggerAction: 'all',  
	emptyText :'--请选择--'
});*/


Xietong.project.ProjectComboBox = Ext.extend(Ext.form.ComboBox, {
	fieldLabel:'所属项目',
	name:'projectId',
	hiddenName:'projectId',
	anchor:'90%',
	store: new Xietong.project.ProjectStore(),
	editable: false,
	typeAhead: true,
	mode:'local',
	triggerAction : 'all',
    width:300,
	displayField:'projectname',
	valueField:'id',
	allowBlank:false, 
	emptyText :'--请选择项目名--'
});









