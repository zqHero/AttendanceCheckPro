
Ext.namespace('Xietong.person');

Xietong.person.PersonStore = Ext.extend(Ext.data.Store, {
	proxy: new Ext.data.HttpProxy({
		url:'module/common/listUser.do'
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
	hiddenName:'superiorsId',
	anchor:'90%',
	store: new Xietong.person.PersonStore(),
	pageSize:12,
	width:300,
	valueField:'id',
	displayField:'realName',
	triggerAction : 'all',
	mode:'local',
	editable: false,
	triggerAction: 'all', 
	allowBlank:false, 
	emptyText :'--请选择--'
});

var patrn = /^[^`~!@#$%^&*()+=|\\\][\]\{\}:;'\,.<>/?]{1}[^`~!@$%^&()+=|\\\][\]\{\}:;'\,.<>?]{0,19}$/;//保证查询不含sql关键字

Xietong.person.PersonTrigger = Ext.extend(Ext.form.ComboBox, {
	fieldLabel:'上级姓名',
	name:'superiorsName',
	hiddenName:'superiorsName',
	anchor:'90%',
	store: new Xietong.person.PersonStore(),
	editable: true,
	typeAhead: true,
	mode:'remote',
	minChars:1,
    triggerAction: 'query',
    selectOnFocus:true,
    forceSelection:true,
    hideTrigger :true,
    width:300,
    regex:patrn,
	displayField:'userName',
	valueField:'userName',
	allowBlank:false, 
	emptyText :'--请输入用户名--',
	listeners:{
		'beforeQuery':function(e) {
			if(!patrn.exec(e.query))  e.cancel=true
		}
	}
});
