//////////////////////////////左栏折叠、展开控制//////////////////////////////////
var s = true;
function sw(){
	var left=document.getElementById("left");
	var right=document.getElementById("right");
	var midd=document.getElementById("midd");
	var xy = document.getElementById("xy");
	//var k=window.screen.availWidth;
	
	if (s){
		xy.src = "images/jt2.jpg";
		xy.alt = "展开左栏";
		left.style.display = "none";
		midd.style.left = 0;
		right.style.left = 7;
		right.style.width=window.screen.availWidth-17;
		}else{
		xy.src = "images/jt1.jpg";
		xy.alt = "隐藏左栏";
		left.style.display = "";
		midd.style.left = 344;
		right.style.left = 351;
		right.style.width=window.screen.availWidth-361;
		//s = false;
		}
		s = !s;
}
//////////////////////////////左栏折叠、展开控制结束////////////////////////////

