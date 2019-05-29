(function(){
	if(typeof SANINCO != 'object'){ SANINCO={}; } 
	
	SANINCO.ifAllGranted = function(as){
		var authoritie = (arguments.length == 1 ? as.split(",") : arguments);
		var r = true;
		for(var p=0;p<authoritie.length;p++){
			if(SANINCO.authorities.indexOf(authoritie[p]) == -1){
				r = false;
				break;
			}
		}
		return r;
	};
	
	SANINCO.ifAnyGranted = function(as){
		var authoritie = (arguments.length == 1 ? as.split(",") : arguments);
		var r = false;
		for(var p=0;p<authoritie.length;p++){
			if(SANINCO.authorities.indexOf(authoritie[p]) != -1){
				r = true;
				break;
			}
		}
		return r;
	};
	
})();