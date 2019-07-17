
Ext.namespace('Xietong.department');
Xietong.department = function(){}
Xietong.department.DepartmentStore = Ext.extend(Ext.data.Store, {
	proxy: new Ext.data.HttpProxy({
		url:'module/common/listDepartment.do'
	}),
	reader:new Ext.data.ArrayReader({},[
		{name:'id'},
		{name:'departmentName'},
		{name:'departmentDesc'}
	])
});

var Department = Ext.data.Record.create([
	{name : 'id',mapping : 'id',type : 'int'}, 
	{name : 'departmentName',	mapping : 'departmentName',type : 'string'}
]);

Xietong.department.DepartmentComboBox = Ext.extend(Ext.form.ComboBox, {
	fieldLabel:'部门',
	name:'departmentId',
	hiddenName:'departmentId',
	anchor:'90%',
	store: new Xietong.department.DepartmentStore(),
	valueField:'id',
	displayField:'departmentName',
	triggerAction : 'all',
	mode:'local',
	editable: false,
	triggerAction: 'all', 
	allowBlank:false, 
	emptyText :'--请选择--'
});

