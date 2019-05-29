/*
 * Auchor Wei.Su
 */
YAHOO.util.Event.onDOMReady(function(){

    YAHOO.util.Dom.get("form0_oldPassword").value = "";
    YAHOO.util.Dom.get("form0_newPassword").value = "";
    YAHOO.util.Dom.get("form0_confirmPassword").value = "";
    
    url = CONTEXT_PATH + "toGetUserPictures.action";
    div = YAHOO.util.Dom.get("user_pictures_tr");
    div_h = YAHOO.util.Dom.get("user_pictures_tr_h");
    imgAction = "showUserPicture.action?pictureId=";
    du = CONTEXT_PATH + "toDeleteUserPicture.action";
    upd1 = YAHOO.util.Dom.get("uploadImg_tr_1");
    upd2 = YAHOO.util.Dom.get("uploadImg_tr_2");
    upd3 = YAHOO.util.Dom.get("uploadImg_tr_3");
    
    
    //From the page after the rendering
    var renderUpload = function(len){
        if (len >= 5) {
            upd1.style.display = "none";
            upd2.style.display = "none";
            upd3.style.display = "block";
        } else {
            upd1.style.display = "block";
            upd2.style.display = "block";
            upd3.style.display = "none";
        }
    };
    //User generated content photo
    var buildToUserImgDiv = function(data){
        var d = jQuery(div);
        var dh = jQuery(div_h);
        d.empty();
        dh.empty();
        var html = "";
        var tmp = "";
        for (var i = 0; i < data.length; i++) {
            if (i < data.length - 1) {
                tmp = 'width="20%"';
            } else {
                tmp = "";
            }
            html = '<td align="left" ' + tmp + '><img id="picture_id_' + data[i] + '" style="border:2px solid #ccc;" width="200" height="200" src="' + imgAction + data[i] + '"/></td>';
            d.append(html);
            html = '<td style="padding-left:76px;" align="left" ' + tmp + '><img style="margin-top:5px;" src="include/images/reject16.png" onclick="deleteImage(\'' + data[i] + '\')"/></td>';
            dh.append(html);
        }
    };
    //Load the user has pictures
    loadUserImage = function(){
        var callback = {
            success: function(o){
				if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
					window.location.reload();
					return;
				}
                var data = eval("(" + o.responseText + ")");
                if (data.error) {
                    alert("Error: " + data.error);
                    return;
                }
                buildToUserImgDiv(data);
                renderUpload(data.length);
            },
            failure: showError
        };
        document.getElementById('__up_load_text_myprofile').value = "";
        document.getElementById('up_load_text_myprofile_clear').value = "";
        YAHOO.util.Connect.asyncRequest('POST', url, callback);
    };
    
    deleteImage = function(i){
        var callback = {
            success: function(o){
				if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
					window.location.reload();
					return;
				}
                var data = eval("(" + o.responseText + ")");
                if (data.error) {
                    alert("Error: " + data.error);
                    return;
                }
                hideLoadingModalLayer();
                loadUserImage(data);
            },
            failure: showError
        };
        showLoadingModalLayer();
        YAHOO.util.Connect.asyncRequest('POST', du, callback, "pictureId=" + i);
    };
    //Add upload error message
    addErrorMessage = function(a){
        document.getElementById("showUploadErrorDiv").innerHTML = a;
    };
    //Modify the error message displayed password
    showChangePwdMassage = function(a){
        document.getElementById("showChangePwdMassageDiv").innerHTML = a;
        document.getElementById("showChangePwdErrorDiv").innerHTML = '';
    };
    
    showChangePwdError = function(a){
        document.getElementById("showChangePwdMassageDiv").innerHTML = '';
        document.getElementById("showChangePwdErrorDiv").innerHTML = a;
    };
    //Verify for0 content
    validataForm0 = function(){
        if (!FIC_checkForm('form0')) return false;
        var form0 = document.forms.form0;
        if (form0.confirmPassword.value != form0.newPassword.value) {
            alert('Passwords do not match. Re-enter password');
            form0.newPassword.value = '';
            form0.confirmPassword.value = '';
            form0.newPassword.focus();
            return false;
        }
        showLoadingModalLayer();
        return true;
    };
    //Reset the user to select box
    resetUserSelect = function(index){
        var ops = document.getElementById('form1_profileVO_delegateToUserId').options;
        for (var i = 0; i < ops.length; i++) {
            if (ops[i].value == index) {
                ops.selected = true;
                break;
            }
        }
    };
    
    loadUserImage();
});
