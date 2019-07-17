Ext.namespace('Xietong');

var dakaBtn = new Ext.Button({
	text:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
	tooltip:'打卡上班或下班<br>一天只能打卡两次。',
	iconCls:'icon-daka',
	handler:function() {
		Ext.Ajax.request({
			url: 'module/common/daka.do',   
			scope: this,   
			success: function(response){
				Ext.Msg.show({
					title: "信息",
					msg:response.responseText,
					buttons:Ext.MessageBox.OK,
					icon: Ext.MessageBox.INFO
				});
			},
			failure:function(response){
				Ext.Msg.show({
					title: "错误",
					msg:"与后台链接错误",
					buttons:Ext.MessageBox.OK,
					icon: Ext.MessageBox.ERROR
				});
			}
		});
	}
});


var dakaPanel = new Ext.Panel({
	id:"workPing", 
	title:"打卡页面",
	layout:'border',
	border:false ,
	items :[{
		region: 'center',
		items:[dakaBtn]
	}]
});

var tt;
showTime();
function showTime() {
	Ext.lib.Ajax.request(
		'POST',
		'module/common/getCurrentTime.do',
		{
			success:function(response) {
				if(response.responseText.length > 40) { 
					Ext.MessageBox.alert("错误","请重新登陆！",function(){
						self.location='/xietong/index.jsp'; 
						window.clearTimeout(tt);
					});
					return;
				}
				dakaBtn.setText(response.responseText);
			},
			failure:function(){
				Ext.MessageBox.alert("错误","与后台联系的时候出了问题",function(){
					self.location='/xietong/index.jsp'; 
					window.clearTimeout(tt);
				});
			}
		}
	);
	tt = window.setTimeout("showTime()", 10000); 
}