
Ext.namespace('Xietong.project');

Xietong.project.ProjectStore = Ext.extend(Ext.data.Store, {
	proxy: new Ext.data.HttpProxy({
		url:'listProject.do'
	}),
	reader:new Ext.data.JsonReader({
		root:'root',
		totalProperty:'totalCount'
	},[
		{name : 'id'}, 
		{name : 'projectname'}, 
		{name : 'projectdescription'}
	]),
	autoLoad:true
});

Xietong.project.TaskStore = Ext.extend(Ext.data.Store, {
	proxy: new Ext.data.HttpProxy({
		url:'listTask.do'
	}),
	reader:new Ext.data.JsonReader({
		root:'root',
		totalProperty:'totalCount'
	},[
		{name : 'id'}, 
		{name : 'taskname'}, 
		{name : 'usersId'},
		{name : 'usersName'},
		{name : 'projectId'},
		{name : 'projectName'},
		{name : 'tasktype'},
		{name : 'priority'},
		{name : 'flag'},
		{name : 'taskstate'},
		{name : 'taskdescription'},
		{name : 'superiorsId'},
		{name : 'superiorsName'}
	]),
	autoLoad:true
});

Xietong.project.ProjectComboBox = Ext.extend(Ext.form.ComboBox, {
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
});

Xietong.project.TaskTypeComboBox = Ext.extend(Ext.form.ComboBox, {
	fieldLabel:'任务类型',
	name:'tasktype',
	anchor:'90%',
	store:new Ext.data.SimpleStore({
		fields:['value','text'],
		data:[
			['开发任务','开发任务'],
			['测试任务','测试任务'],
			['修改任务','修改任务']
		]
	}),
	valueField:'value',
	displayField:'text',
	triggerAction : 'all',
	mode:'local',
	editable: false,
	triggerAction: 'all', 
	allowBlank:false, 
	emptyText :'--请选择--'
});

Xietong.project.TaskPriority = Ext.extend(Ext.form.ComboBox, {
	fieldLabel:'优先级',
	name:'priority',
	anchor:'90%',
	store:new Ext.data.SimpleStore({
		fields:['value','text'],
		data:[
			['高','高'],
			['中','中'],
			['低','低']
		]
	}),
	valueField:'value',
	displayField:'text',
	mode:'local',
	editable: false,
	triggerAction : 'all',
	emptyText :'--请选择--'
});







