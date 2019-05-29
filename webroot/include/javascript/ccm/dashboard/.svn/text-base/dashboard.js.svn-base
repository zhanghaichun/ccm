// @Auchor Chao.Liu


$(function() {
	dashboardPage = {
		initAttribute : function (){
		//$("#footerSaveDiv").css({width:"100%",height:"30px"});
		$("#footerSaveDiv").css({height:"30px"});
			$("#footerSaveDiv").corner();
			
			$("#loadingDiv").css("opacity", 0.3); 
			
			$(".ui-icon").css({cursor:"pointer"}); // Icon Cursor
			
			$(".portlet-header").corner("8px"); // Title Style
		},
		initBind : function (){
			$(".column").sortable({
				connectWith: '.column'
			});
		
			$(".portlet").addClass("ui-widget ui-widget-content ui-helper-clearfix ui-corner-all")
				.find(".portlet-header")
					.addClass("ui-widget-header ui-corner-all")
					.end()
				.find(".portlet-content");
			
			// Max or Min
			$(".ui-icon-minusthick").click(function() {
				$(this).toggleClass("ui-icon-minusthick").toggleClass("ui-icon-plusthick");
				$(this).parents(".portlet:first").find(".portlet-content").toggle();
			});
			
			$(".ui-icon-plusthick").parents(".portlet:first").find(".portlet-content").toggle();
			$(".ui-icon-plusthick").click(function() {
				$(this).toggleClass("ui-icon-plusthick").toggleClass("ui-icon-minusthick");
				$(this).parents(".portlet:first").find(".portlet-content").toggle();
			});
			
			/** Edit
			 *  Use Disable
				$(".ui-icon-gear").click(function() {
					var img = $(this).attr("id") + "IMG";
					var oc  = "oc" + $(this).attr("id").substring(4);
					alert("Function is temporarily not available!");
					alert($("#"+oc).attr("class"));
					alert($("#"+img).attr("src"));
				});
			*/
			
			$(".column").disableSelection();
		},
		savePostion : function (){
			$("#loadingDiv").width($("#pageContainer").width());
			$("#loadingDiv").height($("#pageContainer").height());
			
			var str = "";			
			var portlet = $(".portlet");
			for(var i=0; i<portlet.length; i++){
				if(i!=0)str+="#";
				var offset = $("#"+portlet[i].id).offset();
				str += portlet[i].id+":"+parseInt(offset.left)+":"+parseInt(offset.top)
			}
			
			$.ajax({
			   type: "POST",
			   url: "saveDashboardPosition.action",
			   dataType : "json",
			   data: "dvo.dashboardPosition="+str,
			   success: function(msg){ 
			   		$("#loadingDiv").width(0);
					$("#loadingDiv").height(0);
			   }
			});
		}
	}
	
	dashboardPage.initAttribute();
	dashboardPage.initBind();
	
	
	
	
});



































