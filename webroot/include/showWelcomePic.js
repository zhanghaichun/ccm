// Auchor  : Chao.Liu 
// Date    : 2010/05/28 AM
// Explain : Control the welcome image show change


var searchUserLoginPicAction = "doGetUserLoginPicByUname.action"; // Get user Login Picture Id List
var imgAction = "showUserPicture.action?pictureId="; // Get ImgSrc

/**
 * Get document.getElementById()
 */
function get(id){
	return typeof (id) == "string" ? document.getElementById(id) : id;
}

function load(){
	var uname = Get_Cookie('ccmcname')
	if(uname == null){
		defaultPic(); // Show default Pic
	}else{
		get("_spring_security_remember_me").checked = "checked";
		get("j_username").value = uname;
		searchVisitedPic(uname);
	}
}
/**
 * Search User Login Pic Id List
 */
function searchVisitedPic(uname){
	var callback = {
		success: searchVisitedPicIsSuccess,
		failure: showError
	};
	var searchVisitedPicUrlParamer = "uname=" + uname;
	YAHOO.util.Connect.asyncRequest('POST' , searchUserLoginPicAction , callback , searchVisitedPicUrlParamer ); 
}
	
	var slidespeed=4000;
	var ie=document.all;
	var imageholder=new Array();
	var whichlink=0;
	var whichimage=0;
	var slideimages;
	var blenddelay;
function searchVisitedPicIsSuccess(o){ 
	var data = eval("("+o.responseText+")");
	if(data.isNull){
		defaultPic(); // Show default Pic
		return;
	}
	
	slideimages=new Array(data.length);
	
	for(var d in data){
		slideimages[d] = imgAction + data[d];
	}
	
	var slidelinks=new Array(slideimages.length);
	
	for (var i=0;i<slideimages.length;i++){
		imageholder[i]=new Image();
		imageholder[i].src=slideimages[i];
	}
	blenddelay=(ie)? document.images.slide.filters[0].duration*1000 : 0;
	
	slideit();
}

/**
 * Show default Pic
 */
function defaultPic(){
	slideimages=new Array(5);
	slideimages[0] = "include/slimages/1.jpg";
	slideimages[1] = "include/slimages/2.jpg";
	slideimages[2] = "include/slimages/3.jpg";
	slideimages[3] = "include/slimages/4.jpg";
	slideimages[4] = "include/slimages/5.jpg";
	
	var slidelinks=new Array(slideimages.length);
	
	for (var i=0;i<slideimages.length;i++){
		imageholder[i]=new Image();
		imageholder[i].src=slideimages[i];
	}
	blenddelay=(ie)? document.images.slide.filters[0].duration*1000 : 0;
	
	slideit();
}
/**
 *
 * Change Image
 */
function slideit(){
	
	get("showPic").style.display = "";
	
	if (!document.images) return;
	if (ie) document.images.slide.filters[0].apply();
	document.images.slide.src=imageholder[whichimage].src;
	if (ie) document.images.slide.filters[0].play();
	whichlink=whichimage;
	whichimage=(whichimage<slideimages.length-1)? whichimage+1 : 0;
	setTimeout("slideit()",slidespeed+blenddelay);
}


// Error Info
function showError(o){
	var msg = "Search User Login Picture Failure!";
	alert(msg); // Show Error message Info 
}
window.onload = load;

/**
 *
 * Save Or Delete User Cookie
 */
function saveOrDeleteCookie(){
	var isChecked = get("_spring_security_remember_me");
	var uname = get("j_username").value;
	if(isChecked.checked == true){
		saveCookie(uname);
	}else{
		deleteCookie();
	}
}
/**
 *
 * Save Uesr Cookie
 */
function saveCookie(uname){
	Set_Cookie("ccmcname",uname,"30","/","","")
}
/**
 *
 * Delete Uesr Cookie
 */
function deleteCookie(){
	Delete_Cookie("ccmcname","/","")
}
/**
 * Add By Chao.Liu to 10/07/28 AM
 * Validation TextBox
 */
function loginValidation(){
	var uname = get("j_username").value;
	var upass = get("j_password").value;
	if(uname == "" || upass == ""){
		get("errorInfo").innerHTML = "Please type username or password.";
		return false;
	}
	
	var loginAppUrl = window.location.protocol+"//" + window.location.host + "/ccmApp/j_spring_security_check";
	jQuery.ajax({url:loginAppUrl, 
		type:'post',
		async:false,
		data:{'j_username':uname,'j_password':upass},
		success:function(data){
			if ("LoginFailure" == data) {
				//alert(data);
			}
		}});
		
	/* Delete By Chao.Liu : This is Error.Please to amend;
	var re = /^(?=.*[A-Za-z])(?=.*[0-9])(?!.*[^A-Za-z0-9])(?!.*\s).{5,12}$/;
	if(!req.exec(uname)){
		document.getElementById("errorInfo").innerHTML = "Username must be 5-12 long with at least one alphabet and on number, without special characters.";
		return false;
	}
	  */
	return true;
}


