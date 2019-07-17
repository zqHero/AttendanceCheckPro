// 自定义的button，添加了url属性
	Ext.namespace('Ext.ux.menu');
	Ext.ux.menu.UrlItem = Ext.extend(Ext.menu.Item, {  
        url : ''//动态面板的url，默认为空
	})
	