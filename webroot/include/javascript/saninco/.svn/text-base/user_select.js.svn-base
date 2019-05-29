/**
 * @author Jian.Dong 
 */
(function(){
	if(typeof USERLIST != 'object'){USERLIST={};}
	var G = function(id){
		return document.getElementById(id);
	};
	var C = YAHOO.util.Connect;
	var S = showLoadingModalLayer;
	var H = hideLoadingModalLayer;
	
    var P = function(divId, c){
		var TO = this;
		this.did = divId;
		$("#"+divId).empty();
		this.paramUserStr = "";
		this.paramRoleStr = "";
		this.enableFlag = true;
		this.dataUri = "doGetUserAndRoleUri.action";
		this.width="320px";
        if (c) {
            for (var m in c) {
				if(typeof c[m] == 'function'){
					this[m] = c[m]();
				}else{
	                this[m] = c[m];
				}
            }
        }
		
		this.requestData = function(){
			var cb = {
				success: TO.renderPage,
				failure: showError
			}; 
			connection = C.asyncRequest('POST' , TO.dataUri , cb , null );
		};
		
		this.renderPage = function(o){
			var data = eval("("+o.responseText+")");
			delete o;
			var userRows = data.userList;
			var roleRows = data.roleList;
			var t1 = ''+
			'<div style="width:'+TO.width+';" class="inpu2">'+
				'<div id="'+TO.did+'_input" class="llin">'+
				'</div>'+
				'<span id="'+TO.did+'_plus" class="jiacule">+</span>'+
			'</div>'+
			
			'<div id="'+TO.did+'_select" style="width:'+TO.width+';visibility:hidden;" class="hjdnm">'+
				'<div class="tabt2">'+
					'<ul class="tabul1">'+
						'<li id="userListTab_'+TO.did+'" class="currentlis" style="cursor:pointer;">User</li>'+
						'<li id="roleListTab_'+TO.did+'" style="cursor:pointer;">Role</li>'+
					'</ul>'+
					'<div class="selall1" id="check_001_div_'+TO.did+'">'+
						'<input type="checkbox" id="check_001_'+TO.did+'" />Select All User'+
					'</div>'+
					'<div class="selall1" id="check_002_div_'+TO.did+'" style="display:none;">'+
					'<input type="checkbox" id="check_002_'+TO.did+'"/>Select All Role'+
					'</div>'+
					'<div class="clean"></div>'+
				'</div>'+
				'<ul id="userListPanel_'+TO.did+'" class="allname3" style="overflow-y:auto;max-height:100px;">';
			
			for(var i = 0; i<userRows.length; i++){
			 	var row = userRows[i];
			 	var t2 = '<li><input id="check_user_'+TO.did+'_'+row["id"]+'" type="checkbox" name="itemUser_'+TO.did+'" value="'+row["name"]+'" />'+row["name"]+'</li>';
			 	t1 += t2;
			}
			t1 += '<div class="clean"></div>'+'</ul>';
			
			var t3 = '<ul id="roleListPanel_'+TO.did+'" class="allname3" style="overflow-y:auto;max-height:100px;display:none;">';
			t1 += t3;
			
			for(var i = 0; i<roleRows.length; i++){
			 	var row = roleRows[i];
			 	var t4 = '<li><input id="check_role_'+TO.did+'_'+row["id"]+'" type="checkbox" name="itemRole_'+TO.did+'" value="'+row["name"]+'" />'+row["name"]+'</li>';
			 	t1 += t4;
			}
			t1 += '<div class="clean"></div>'+'</ul>'+'</div>';
			
			
			G(TO.did).innerHTML = t1;
			
			for(var s = 0; s<userRows.length; s++){
				var row = userRows[s];
			 	$("#check_user_"+TO.did+"_"+row['id']).click(function(e){
			 		if(G("check_001_"+TO.did).checked){
			 			$("#delete_all_user_"+TO.did).parent().remove();
			 			$("#check_001_"+TO.did).attr("checked","");
						var list = $("[name='itemUser_"+TO.did+"']");
						for(var w=0;w<list.length;w++){
							var item = list[w];
							if(item.checked){
								var item_num = item.id.replace("check_user_"+TO.did+"_","");
								var item_value = item.value;
								var a = '<span class="rmname">'+item_value+'<span id="delete_user_'+TO.did+'_'+item_num+'" class="c23"></span></span>';
					 			$("#"+TO.did+"_input").append(a);
					 			$("#delete_user_"+TO.did+"_"+item_num).click(function(e){
					 				var obj_d=e.target;
					 				var item_num = obj_d.id.replace("delete_user_"+TO.did+"_","");
					 				$("#"+obj_d.id).parent().remove();
					 				$("#check_user_"+TO.did+"_"+item_num).attr("checked",'');
					 				e.stopPropagation();
					 			});
							}
						}
					}else{
						var obj=e.target; 
				 		var name = obj.value;
				 		var num = obj.id.replace("check_user_"+TO.did+"_","");
						if(obj.checked){
				 			var a = '<span class="rmname">'+name+'<span id="delete_user_'+TO.did+'_'+num+'" class="c23"></span></span>';
				 			$("#"+TO.did+"_input").append(a);
				 			
				 			$("#delete_user_"+TO.did+"_"+num).click(function(e){
				 				var obj_d=e.target; 
				 				var item_num = obj_d.id.replace("delete_user_"+TO.did+"_","");
				 				$("#"+obj_d.id).parent().remove();
				 				$("#check_user_"+TO.did+"_"+item_num).attr("checked",'');
				 				e.stopPropagation();
				 			});
				 		}else{
				 			$("#delete_user_"+TO.did+"_"+num).parent().remove();
				 		}
					}
				});
			 	
			}
			
			for(var s = 0; s<roleRows.length; s++){
				var row = roleRows[s];
				$("#check_role_"+TO.did+"_"+row['id']).click(function(e){
					if(G("check_002_"+TO.did).checked){
			 			$("#delete_all_Role_"+TO.did).parent().remove();
			 			$("#check_002_"+TO.did).attr("checked","");
						var list = $("[name='itemRole_"+TO.did+"']");
						for(var w=0;w<list.length;w++){
							var item = list[w];
							if(item.checked){
								var item_num = item.id.replace("check_role_"+TO.did+"_","");
								var item_value = item.value;
								var a = '<span class="rmname">'+item_value+'<span id="delete_role_'+TO.did+'_'+item_num+'" class="c23"></span></span>';
					 			$("#"+TO.did+"_input").append(a);
					 			$("#delete_role_"+TO.did+"_"+item_num).click(function(e){
					 				var obj_d=e.target;
					 				var item_num = obj_d.id.replace("delete_role_"+TO.did+"_","");
					 				$("#"+obj_d.id).parent().remove();
					 				$("#check_role_"+TO.did+"_"+item_num).attr("checked",'');
					 				e.stopPropagation();
					 			});
							}
						}
					}else{
						var obj=e.target; 
						var name = obj.value;
						var num = obj.id.replace("check_role_"+TO.did+"_","");
						if(obj.checked){
							var a = '<span class="rmname">'+name+'<span id="delete_role_'+TO.did+'_'+num+'" class="c23"></span></span>';
							$("#"+TO.did+"_input").append(a);
							
							$("#delete_role_"+TO.did+"_"+num).click(function(e){
								var obj_d=e.target; 
								var item_num = obj_d.id.replace("delete_role_"+TO.did+"_","");
								$("#"+obj_d.id).parent().remove();
								$("#check_role_"+TO.did+"_"+item_num).attr("checked",'');
								e.stopPropagation();
							});
						}else{
							$("#delete_role_"+TO.did+"_"+num).parent().remove();
						}
					}
				});
				
			}
			
			$("#userListTab_"+TO.did).click(function(e){
				$("#userListTab_"+TO.did).addClass("currentlis");
				$("#roleListTab_"+TO.did).removeClass("currentlis");
				$("#roleListPanel_"+TO.did).hide();
				$("#check_002_div_"+TO.did).hide();
				$("#userListPanel_"+TO.did).show();
				$("#check_001_div_"+TO.did).show();
			});
			
			$("#roleListTab_"+TO.did).click(function(e){
				$("#roleListTab_"+TO.did).addClass("currentlis");
				$("#userListTab_"+TO.did).removeClass("currentlis");
				$("#userListPanel_"+TO.did).hide();
				$("#check_001_div_"+TO.did).hide();
				$("#roleListPanel_"+TO.did).show();
				$("#check_002_div_"+TO.did).show();
			});
			
			$("#check_001_"+TO.did).click(function(e){
				var obj_a=e.target; 
				if(obj_a.checked){
					
					var list = $("[name='itemUser_"+TO.did+"']");
					for(var i=0;i<list.length;i++){
						var item = list[i];
						if(item.checked){
							$("#delete_user_"+TO.did+"_"+item.id.replace("check_user_"+TO.did+"_","")).parent().remove();
						}
					}
					
					$("[name='itemUser_"+TO.did+"']").attr("checked",'true');
					var a = '<span class="rmname">Select All User<span id="delete_all_user_'+TO.did+'" class="c23"></span></span>';
					
		 			$("#"+TO.did+"_input").append(a);
		 			
		 			$("#delete_all_user_"+TO.did).click(function(e){
		 				var obj_d=e.target; 
		 				$("#delete_all_user_"+TO.did).parent().remove();
		 				$("[name='itemUser_"+TO.did+"']").attr("checked",'');
		 				$("#check_001_"+TO.did).attr("checked",'');
		 				e.stopPropagation();
		 			});
					
				}else{
					$("[name='itemUser_"+TO.did+"']").attr("checked",'');
					$("#delete_all_user_"+TO.did).parent().remove();
				}
			});
			
			$("#check_002_"+TO.did).click(function(e){
				var obj_a=e.target; 
				if(obj_a.checked){
					
					var list = $("[name='itemRole_"+TO.did+"']");
					for(var i=0;i<list.length;i++){
						var item = list[i];
						if(item.checked){
							$("#delete_role_"+TO.did+"_"+item.id.replace("check_role_"+TO.did+"_","")).parent().remove();
						}
					}
					
					$("[name='itemRole_"+TO.did+"']").attr("checked",'true');
					var a = '<span class="rmname">Select All Role<span id="delete_all_Role_'+TO.did+'" class="c23"></span></span>';
					
					$("#"+TO.did+"_input").append(a);
					
					$("#delete_all_Role_"+TO.did).click(function(e){
						var obj_d=e.target; 
						$("#delete_all_Role_"+TO.did).parent().remove();
						$("[name='itemRole_"+TO.did+"']").attr("checked",'');
						$("#check_002_"+TO.did).attr("checked",'');
						e.stopPropagation();
					});
					
				}else{
					$("[name='itemRole_"+TO.did+"']").attr("checked",'');
					$("#delete_all_Role_"+TO.did).parent().remove();
				}
			});
			
			$("#"+TO.did+"_input").click(function(e){
				if(!TO.enableFlag){
					e.stopPropagation();
	        		return ;
	        	}
				
				var top = $("#"+TO.did+"_input").position().top;
				
				$("#"+TO.did+"_select").position().top = top+19+"px";  
				
				var panel = G(TO.did+"_select");
	        	if(panel == null) return;
	        	if(panel.style.visibility == "hidden"){
	        		panel.style.visibility = "visible";
	        	}else{
	        		panel.style.visibility = "hidden";
	        	};
			});
			
			$("#"+TO.did+"_plus").click(function(e){
				if(!TO.enableFlag){
					e.stopPropagation();
	        		return ;
	        	}
				var panel = G(TO.did+"_select");
				if(panel == null) return;
				if(panel.style.visibility == "hidden"){
					panel.style.visibility = "visible";
				}else{
					panel.style.visibility = "hidden";
				};
			});
			
			
			$("#"+TO.did).hover( 
				function () { 
					G(TO.did+"_select").style.visibility = "hidden";
				}
			);
			
		};
		
		this.getSelectUserList = function(){
			var list = $("[name='itemUser_"+TO.did+"']");
			TO.paramUserStr="";
			if(!TO.enableFlag){
        		return TO.paramUserStr;
        	}
			for(var i=0;i<list.length;i++){
				var item = list[i];
				if(item.checked){
					TO.paramUserStr += item.id.replace("check_user_"+TO.did+"_","")+",";
				}
			}
			TO.paramUserStr=TO.paramUserStr.substring(0,TO.paramUserStr.lastIndexOf(','));
			return TO.paramUserStr;
		}
		
		this.getSelectRoleList = function(){
			var list = $("[name='itemRole_"+TO.did+"']");
			TO.paramRoleStr="";
			if(!TO.enableFlag){
        		return TO.paramRoleStr;
        	}
			for(var i=0;i<list.length;i++){
				var item = list[i];
				if(item.checked){
					TO.paramRoleStr += item.id.replace("check_role_"+TO.did+"_","")+",";
				}
			}
			TO.paramRoleStr=TO.paramRoleStr.substring(0,TO.paramRoleStr.lastIndexOf(','));
			return TO.paramRoleStr;
		}
		
		this.cleanSelect = function(){
			$("[name='itemUser_"+TO.did+"']").attr("checked",'');
			$("[name='itemRole_"+TO.did+"']").attr("checked",'');
			$("#check_001_"+TO.did).attr("checked",'');
			$("#check_002_"+TO.did).attr("checked",'');
			$("#"+TO.did+"_input").empty();
			this.enableFlag = true;
			
			$("#userListTab_"+TO.did).addClass("currentlis");
			$("#roleListTab_"+TO.did).removeClass("currentlis");
			$("#roleListPanel_"+TO.did).hide();
			$("#check_002_div_"+TO.did).hide();
			$("#check_001_div_"+TO.did).show();
			$("#userListPanel_"+TO.did).show();
		}
		
		this.requestData();
		
    };
    USERLIST.Page=P;
})();