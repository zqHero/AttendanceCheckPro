Ext.namespace('Xietong.task');

Xietong.task.TaskStore = Ext.extend(Ext.data.Store, {
	proxy: new Ext.data.HttpProxy({
		url:'module/task/list.do'
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
		{name : 'taskdescription'}
	]),
	remoteSort:true
});

Xietong.task.TaskLogStore = Ext.extend(Ext.data.Store, {
	proxy: new Ext.data.HttpProxy({
		url:'module/task/listLog.do'
	}),
	reader:new Ext.data.JsonReader({
		root:'root',
		totalProperty:'totalCount'
	},[
		{name : 'id'}, 
		{name : 'taskId'}, 
		{name : 'taskName'},
		{name : 'date'},
		{name : 'operatorId'},
		{name : 'operatorName'},
		{name : 'action'},
		{name : 'executorId'},
		{name : 'executorName'},
		{name : 'result'}
	])
});

Xietong.task.TaskTypeComboBox = Ext.extend(Ext.form.ComboBox, {
	fieldLabel:'任务类型',
	name:'tasktype',
	anchor:'90%',
	store:new Ext.data.SimpleStore({
		fields:['value','text'],
		data:[
			['开发任务','开发任务'],
			['测试任务','测试任务'],
			['修改任务','修改任务'],
			['需求任务','需求任务'],
			['支持任务','支持任务'],
			['文档任务','文档任务'],
			['设计任务','设计任务'],
			['美工任务','美工任务']
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

Xietong.task.TaskPriority = Ext.extend(Ext.form.ComboBox, {
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