
Ext.namespace('Xietong.person');

Xietong.person.GenderComboBox = Ext.extend(Ext.form.ComboBox, {
	fieldLabel:'性别',
	name:'gender',
	allowBlank:false,
	blankText: '请选择......',
    mode: 'local',
    readOnly:true,
    triggerAction:'all',
    anchor:'90%',
	store:new Ext.data.SimpleStore({
		fields:['value','text'],
		data:[
			['男','男'],
			['女','女']
		]
	}),
	valueField: 'value',
    displayField: 'text'
});

