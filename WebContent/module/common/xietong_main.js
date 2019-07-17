Ext.BLANK_IMAGE_URL = 'images/public/s.gif"';
Ext.QuickTips.init();

Ext.namespace('Xietong');

Xietong.app = function(){};

var changeSkin = new Ext.form.ComboBox({
	allowBlank:false,
    mode: 'local',
    readOnly:true,
    width:100,
    triggerAction:'all',
    value:'galdaka',
	store:new Ext.data.SimpleStore({
		fields:['value','text'],
		data:[
		    ['galdaka','默认'],
			['gray','灰色'],
			['darkgray','深灰'],
			['black','黑色'],
			['slate','蓝灰'],
			['purple','紫色'],
			['darkgray','深灰'],
			['olive','橄榄绿']
		]
	}),
	valueField: 'value',
    displayField: 'text',
    listeners:{
    	'change':function(){
    		Ext.util.CSS.swapStyleSheet('theme','js/ext-2.0/resources/css/xtheme-' + this.getValue() + '.css');
    	}
    }
});


Ext.extend(Xietong.app,Ext.util.Observable,{
	//顶端
	header:new Ext.BoxComponent({
		region:'north',
		el:'north',
		height:30,
		margins:'5'
	}),
	
	//低端
	footer: new Ext.BoxComponent({
		region:'south',
		el:'south',
		height:25
	}),
	
	//实例菜单树形
	menuTree:new Ext.tree.TreePanel({
		title:'功能菜单',
		region:'west',
		id:'extExample-tree',
		enableDD:false,
		autoScroll:true, 
		containerScroll: true,
		split:true,
        width: 180,
        minSize: 175,
        maxSize: 300,
		rootVisible:true,
        collapsible: true,
		loader:new Ext.tree.TreeLoader({  
		   	dataUrl:'module/common/getJsonTree.do'  
        }),
		tools:[{
			id:'refresh',
			handler:function(){
				var tree = Ext.getCmp('extExample-tree');
				tree.root.reload();
			}
		}],
		tbar:new Ext.Toolbar({
			frame:true,
			border:false,
			items:['皮肤:',changeSkin]
		}),
		bbar:new Ext.Toolbar({
			frame:true,
			border:false,
			items:[new Ext.Button({
				text:'修改密码',
				iconCls:'icon-edit',
				handler:function(){
					Ext.MessageBox.prompt('修改密码','请输入新密码：',function(btn,text){
						if(btn !='ok') return;
						if(text.trim() == '' || text.length < 5 || text.length > 20) {
							Ext.MessageBox.alert("错误","密码不能为空，且长度在5~20之间");
							return;
						}
						Ext.Ajax.request({
						    url: 'module/common/changePassword.do',
						    success: function(response){ Ext.MessageBox.alert("信息",response.responseText); },
						    failure: function(){ Ext.MessageBox.alert("错误","与后台连接错误"); },
						    params: { password: text }
						 });
					});
				}
			})]
		})
	}),
	menuRoot: new Ext.tree.AsyncTreeNode({
		text:'协同管理系统',
		draggable:false,
		id:'source'
	}),
	
	main:new Ext.TabPanel({   
		region:'center',   
		enableTabScroll:true,   
		activeTab:0, 
		margins:'0 5 5 0',
		items:[dakaPanel]   
	}),  
	
	init:function(){
		this.menuTree.setRootNode(this.menuRoot);
		this.menuRoot.expand();
		this.menuTree.on('click',this.menuClickAction,this);
		this.main.on('tabchange', this.changeTab, this);
		
		var myView = new Ext.Viewport({
			layout:'border',
			border:false,
			items:[this.header,this.main,this.footer,this.menuTree]
		});
		this.loadMask = new Ext.LoadMask(this.main.body,{msg:"页面加载中……"}); 

	},
	menuClickAction:function(node){
		if(!node.isLeaf()){   
            return false;   
        }
		var tabId = 'tab-' + node.id;
		var tabTitle = node.text;
		var tablink = node.attributes.link;
		var tabJsArray = node.attributes.jsArray; 
		var tab = this.main.getComponent(tabId); 
		
		if(!tab){
            tab = this.main.add(new Ext.Panel({id:tabId,title:tabTitle,autoScroll:true,layout: 'fit',border:false,closable:true}));  	
            
			this.main.setActiveTab(tab);
			var mm = this.main;
			var loadmask = this.loadMask;
			var model; 

			loadmask.show();
			tab.load({
				url: tablink, 
				callback:function(a,b,c){
					var jsStr = ""; 
					var num = tabJsArray.length;
					for(var i=0;i<num;i++){
						 var tabjs = tabJsArray[i].js; 
						 var currentIndex = i; 
						 Ext.Ajax.request({   
							method:'POST',//为了不丢失js文件内容，所以要选择post的方式加载js文件
							url: tabJsArray[i].js,   
							scope: this,   
							success: function(response){
								jsStr+=response.responseText; 
								if(currentIndex==num-1){ 
									this[node.id] = eval(jsStr);   
									model = new this[node.id]();
									loadmask.hide(); 
								}
								tab.on('destroy',function(){
									model.destroy();
								});
							},
							failure:function(response){
								Ext.Msg.show({
									title: "错误信息",
									msg:"您没有权限",
									buttons:Ext.MessageBox.OK,
									icon: Ext.MessageBox.ERROR,
									fn:function(){ mm.remove(tab); }
								});
								loadmask.hide(); 
							}
						});
					}
				},
				discardUrl: false,
				nocache: true,
				text: "",
				timeout: 3000,//超时时间30ms
				scripts: true
			});
        }else{
            this.main.setActiveTab(tab);
        }
	},
	changeTab:function(tab,newtab){
        var nodeId = newtab.id.replace('tab-','');
        var node = this.menuTree.getNodeById(nodeId);
        if(node){
            this.menuTree.getSelectionModel().select(node);
        }else{
            this.menuTree.getSelectionModel().clearSelections();
        }  
	}

});


/**
 * 加载外部js文件
 */
function $import(url){
	var scripts=document.getElementsByTagName("script");
	for(var i=0;i<scripts.length;i++){
		if(scripts[i].src && scripts[i].src.indexOf(url)!=-1){
			if(scripts[i].readyState=="loaded" || scripts[i].readyState=="complete") return; //已经加载过了 不需要再次加载
		}
	}
	var async = true;
	var xmlhttp = window.XMLHttpRequest ? new XMLHttpRequest : new ActiveXObject('Msxml2.XMLHTTP');
	xmlhttp.open("GET", url, false);
	xmlhttp.onreadystatechange = function(){
	   if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
		    if(window.execScript) {// eval in global scope for IE
		    	window.execScript(xmlhttp.responseText);
		    } else {// 关键：用call来解决作用域问题 fo FF
		    	eval.call(window, xmlhttp.responseText);
		    }
	   }
	}
	xmlhttp.send(null);
}

Ext.onReady(function(){
    var xietong = new Xietong.app();
	xietong.init();
	
});