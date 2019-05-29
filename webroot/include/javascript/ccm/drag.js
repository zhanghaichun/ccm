
var params = {
	left: 0,
	top: 0,
	currentX: 0,
	currentY: 0,
	offsetHeight: 0,
	offsetWidth: 0,
	flag: false
};

//获取相关CSS属性
var getCss = function(o,key){
	return o.currentStyle? o.currentStyle[key] : document.defaultView.getComputedStyle(o,false)[key]; 	
};

//拖拽的实现
var startDrag = function(bar, target, callback){
	
	params.offsetWidth = target.offsetWidth;
	params.offsetHeight = target.offsetHeight;
	
	if(getCss(target, "left") !== "auto"){
		params.left = document.body.clientWidth/2 - params.offsetWidth/2;
	}
	if(getCss(target, "top") !== "auto"){
		params.top = document.body.clientHeight/2 - params.offsetHeight/2;
	}
	
	target.style.left = parseInt(params.left) + "px";
	target.style.top = parseInt(params.top) + "px";
	
	//o是移动对象
	bar.onmousedown = function(event){
		params.flag = true;
		if(!event){
			event = window.event;
			//防止IE文字选中
			bar.onselectstart = function(){
				return false;
			}  
		}
		var e = event;
		params.currentX = e.clientX;
		params.currentY = e.clientY;
	};
	document.onmouseup = function(){
		params.flag = false;	
		if(getCss(target, "left") !== "auto"){
			params.left = getCss(target, "left");
		}
		if(getCss(target, "top") !== "auto"){
			params.top = getCss(target, "top");
		}
	};
	document.onmousemove = function(event){
		var e = event ? event: window.event;
		if(params.flag){
			var nowX = e.clientX, nowY = e.clientY;
			var disX = nowX - params.currentX, disY = nowY - params.currentY;
			target.style.left = parseInt(params.left) + disX + "px";
			target.style.top = parseInt(params.top) + disY + "px";
			
			if(parseInt(target.style.left) <= 0){
				target.style.left = 0 + "px";
			}
			
			if(parseInt(target.style.top) <= 0){
				target.style.top = 0 + "px";
			}
			
			if(parseInt(target.style.left) >= document.body.clientWidth - params.offsetWidth){
				target.style.left = (document.body.clientWidth - params.offsetWidth) + "px";
			}
			
			if(parseInt(target.style.top) >= document.body.clientHeight - params.offsetHeight){
				target.style.top = (document.body.clientHeight - params.offsetHeight) + "px";
			}
			if (event.preventDefault) {
				event.preventDefault();
			}
			return false;
		}
		
		if (typeof callback == "function") {
			callback(parseInt(params.left) + disX, parseInt(params.top) + disY);
		}
	}	
};