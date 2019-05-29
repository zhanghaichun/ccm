function ShowHidePanel(id) {
    var divId = "div" + id;
    var imgId = "img" + id;

    if (document.getElementById(divId).style.display == 'none') {
        document.getElementById(divId).style.display = 'block';
        if(typeof CONTEXT_PATH != 'undefined'){
        	document.getElementById(imgId).style.background = 'url('+CONTEXT_PATH+'include/images/close.png)';
        } else{
        	document.getElementById(imgId).style.background = 'url(include/images/close.png)';
        }
    }
    else {
        document.getElementById(divId).style.display = 'none';
        if(typeof CONTEXT_PATH != 'undefined'){
        	document.getElementById(imgId).style.background = 'url('+CONTEXT_PATH+'include/images/open.png)';
        } else{
        	document.getElementById(imgId).style.background = 'url(include/images/open.png)';
        }
    }
}
//////////////////add by sherman:

function hideLeftCol(id) {

    var url = window.location.href;
    var cookieKey = 'showLeftCol';

    // 针对 Rate Tab 进行特殊处理
    if (url.indexOf('displayRateViewSearch.action') !== -1) {

        cookieKey = 'showRateTabLeftCol';
    }

    if (document.getElementById(id).style.display == 'none') {

        document.getElementById(id).style.display = 'inline';
        
        Set_Cookie(cookieKey, 'true', 30, '/', '', '');

    } else {
        this.document.getElementById(id).style.display = 'none'; 
       
        Set_Cookie(cookieKey, 'false', 30, '/', '', '');
    }



    if(typeof CONTEXT_PATH != 'undefined'){
        document['HideHandle'].src = CONTEXT_PATH + 'include/images/hide.gif';
    } else{
        document['HideHandle'].src = 'include/images/hide.gif';
    }


}

//function showSubMenu(id) {
//    if (this.document.getElementById(id).style.display == 'none') {
 //       tbButtonMouseOver('HideHandle', '', '', 10, -25);
 //   }
//}

function printPage() {
    var url = location.href;
    var is_print = 'print=true';
    if (url.indexOf(is_print) > -1) {
        window.print();
    } else {
        return;
    }
}