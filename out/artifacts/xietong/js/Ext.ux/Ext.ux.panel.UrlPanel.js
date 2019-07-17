// 自定义的panel组建，添加了这个一个url属性js扩展函数
	Ext.namespace("Ext.ux.panel"); 
	Ext.ux.panel.UrlPanel = Ext.extend(Ext.Panel, {  
        url : '',//动态面板的url，默认为空
		needScripts:true,//在加载的页面中是否使用javascript，默认为true
		
		autoScroll:true,//是否自动加载滚动条，默认为true 

		reLoad:function(){//自定义的重新加载函数
			if(this.url!=''){
				this.getUpdater().update(this.url);
			}
		},
		
		initComponent : function() {  
			this.initUrlPanelConstruct();
			Ext.ux.panel.UrlPanel.superclass.initComponent.call(this);   
        },  
		initEvents : function() {  
	       	Ext.ux.panel.UrlPanel.superclass.initEvents.call(this);
		},
		
		//自定义的构造函数
		initUrlPanelConstruct:function(){
			//判断本身的url是否为空，如果不为空就自动调用autoLoad函数
			if(this.url!=''){
				this.autoLoad = {url:this.url, scripts:this.needScripts};
			}
		}
	})
	
	//Ext.reg('UrlPanel', Ext.ux.panel.UrlPanel);