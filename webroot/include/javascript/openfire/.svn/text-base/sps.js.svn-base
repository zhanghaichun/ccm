(function (window, undefined) {
	var Saninco = {}
	window.Saninco = Saninco;
	var PopupWindow = function(innerDiv,options) {
		options = $.extend({
			isShowClose: true,
			popupCenterClass: 'popupCenter',
			isShowBackground: true
		}, options);
		var p = {
			container: null,
			popupCenter: null,
			popupClose: null,
			backgroundPopup:null,
			
			init: function() {
				
				p.container = $('<div style="width:100%;height:100%"></div>');
				p.popupCenter = $('<div class="' + options.popupCenterClass + '"></div>');

				p.container.append(p.popupCenter);
				if (options.isShowBackground) {
					p.backgroundPopup = $('<div class="backgroundPopup"></div>');
					p.container.append(p.backgroundPopup);
				}
				if (options.isShowClose) {
					p.popupClose = $('<a class="popupClose" >x</a>');
					p.popupClose.click(function() {
						p.hide();
					});
					p.popupCenter.append(p.popupClose);
					p.popupClose.css({
						"height": windowHeight
					});
				}
				p.popupCenter.append(innerDiv);
				
				$("body").append(p.container);
				
				var windowWidth = document.documentElement.clientWidth;
				var windowHeight = window.screen.availHeight;
				var popupHeight = p.popupCenter.height();
				var popupWidth = p.popupCenter.width();
				//centering
//	            if ($.browser.msie && ($.browser.version == "6.0") ) {
//	            	p.popupCenter.css({
//						"position": "absolute",
//						"top": windowHeight/2-popupHeight/2+$(window).scrollTop(),
//						"left": windowWidth/2-popupWidth/2
//					});
//				} else {
				p.popupCenter.css({
					"position": "fixed",
					"top": windowHeight/2-popupHeight/2,
					"left": windowWidth/2-popupWidth/2
				});
//				}
				
				//only need force for IE6
				
			},
		
			show: function() {
				if (options.isShowBackground) {
					p.backgroundPopup.css({
						"opacity": "0.7"
					});
					p.backgroundPopup.fadeIn("slow");
				}
				p.popupCenter.fadeIn("slow");
			},
			hide: function() {
				if (options.isShowBackground) {
					p.backgroundPopup.fadeOut("slow");
				}
				p.popupCenter.fadeOut("slow");
			}
		}
		
		this.show = p.show;
		
		this.hide = p.hide;
		
		return p.init.apply(p, arguments );
	}
	
	window.Saninco.PopupWindow = PopupWindow;
})(window);
window.Saninco.confirm = function(text) {
	var p = {
		init : function() {
			var html = $('<div class="tskbt"></div>');
			html.append($('<div class="contextm">'+text+'</div>'));
			var buttonContent = $('<div class="graybgbt"></div>');
			html.append(buttonContent);
			var buttonDiv = $('<div class="twobut"></div>');
			buttonContent.append(buttonDiv);
			var confirmBtn = $('<input class="pl" type="button" value="确定" />');
			buttonDiv.append(confirmBtn);
			var cancelBtn = $('<input class="pr" type="button" value="取消" />');
			buttonDiv.append(cancelBtn);
			buttonDiv.append($('<div class="clean"></div>'));
			var confirmWindow = new Saninco.PopupWindow(html,{isShowClose: true});
			confirmBtn.click(function(){
				confirmWindow.hide();
				p.confirmClick();
			});
			cancelBtn.click(function(){
				confirmWindow.hide();
				p.cancelClick();
			});
			confirmWindow.show();
		},
		confirmClick:function() {
			return true;
		},
		cancelClick:function() {
			return false;
		}
	}
	return p.init.apply(p, arguments );
};