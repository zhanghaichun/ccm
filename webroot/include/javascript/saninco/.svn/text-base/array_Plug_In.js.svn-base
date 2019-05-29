// Auchor Chao.Liu
$(function(){
	// Delete Option By Array Index
	Array.prototype.removeOf = function(n){
	  if(n<0 || n>this.length){
	    return this;
	  }
	  else{
	    return this.slice(0,n).concat(this.slice(n+1,this.length));
	  }
	}
	// Get Array Value Index
	Array.prototype.valueOf = function(str){
		var index = -1;
		for(var i=0; i<this.length; i++){
			if(this[i] == str){
				index = i;
			}
		}
	  	return index;
	}
	// Get Array Child Object Value Index
	Array.prototype.objectValueOf = function(pro,str){
		var index = -1;
		for(var i=0; i<this.length; i++){
			try{
				var ov = eval("this[i]."+pro);
				if(ov == str){
					index = i;
				}
			}catch(e){
				
			}
		}
	  	return index;
	}
});




