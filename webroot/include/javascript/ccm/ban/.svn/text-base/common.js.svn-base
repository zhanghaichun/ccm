// @Auchor Chao.Liu
var common_Json = {
	getDOM : function (id){ // Get DOM
		return typeof (id) == "string" ? document.getElementById(id) : id;
	},
	clearForm_YUI : function (name){
		var input = common_Json.getDOM(name).getElementsByTagName("input");
		for(var i = 0; i<input.length; i++){
			if((new YAHOO.util.Element(input[i])).get("type")=="text")	input[i].value = "";
		}
		var select = common_Json.getDOM(name).getElementsByTagName("select");
		for(var i = 0; i<select.length; i++){
			select[i].selectedIndex = 0;
		}
		var textarea = common_Json.getDOM(name).getElementsByTagName("textarea");
		for(var i = 0; i<textarea.length; i++){
			textarea[i].value = "";
		}
	}
	
}
