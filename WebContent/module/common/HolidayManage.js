
Ext.namespace('Xietong.holiday');

Xietong.holiday.HolidayLogStore = Ext.extend(Ext.data.Store, {
	proxy: new Ext.data.HttpProxy({
		url:'module/holidaylog/list.do'
	}),
	reader:new Ext.data.JsonReader({
		root:'root',
		totalProperty:'totalCount'
	},[
		{name : 'id'}, 
		{name : 'userId'},
		{name : 'realName'}, 
		{name : 'holidayId'}, 
		{name : 'holidayName'}, 
		{name : 'startTime',type : 'date',dateFormat:'Y-m-d H:i'}, 
		{name : 'endTime',type : 'date',dateFormat:'Y-m-d H:i'}, 
		{name : 'reason'}
	])
});

Xietong.holiday.HolidayStore = Ext.extend(Ext.data.Store, {
	proxy: new Ext.data.HttpProxy({
		url:'module/common/listHoliday.do'
	}),
	reader:new Ext.data.ArrayReader({},[
		{name : 'holidayId'}, 
		{name :'holidayName'}
	])
});

Xietong.holiday.HolidayComboBox = Ext.extend(Ext.form.ComboBox, {
	fieldLabel:'类型',
	name:'holidayId',
	hiddenName:'holidayId',
	mode:'local',
    triggerAction: 'all',
	anchor:'90%',
	store: new Xietong.holiday.HolidayStore(),
	valueField:'holidayId',
	displayField:'holidayName',
	editable: false,
	allowBlank:false, 
	emptyText :'--请选择--'
});
