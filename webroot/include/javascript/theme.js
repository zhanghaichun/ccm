changeUserTheme = function(n){
    var value = n.options[n.selectedIndex].value;
    
    YAHOO.util.Connect.asyncRequest('POST', "changeUserTheme.action", {
        success: function(o){
			if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
				window.location.reload();
				return;
			}
            var data = eval("(" + o.responseText + ")");
            if (data.error) {
                alert(data.error);
                return;
            }
//			var a = document.getElementsByTagName('link');
//            for (var i = 0; i<a.length; i++) {
//				if (a[i].title && a[i].title.indexOf("ccm_theme_link_20100716_")!=-1) {
//					a[i].disabled = !(a[i].title == "ccm_theme_link_20100716_"+value); 
//				}
//            }
            window.location.reload();
        },
        failure: showError
    }, "themeId=" + value);
}
