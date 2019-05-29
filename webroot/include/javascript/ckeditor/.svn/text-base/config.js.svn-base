/*
Copyright (c) 2003-2012, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

CKEDITOR.editorConfig = function( config )
{
	// Define changes to default configuration here. For example:
	var pathName = window.document.location.pathname;
	var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
	 config.language = 'en';
	// config.uiColor = '#AADC6E';
	config.filebrowserImageUploadUrl ="actions/wikiUploadImage";
	config.filebrowserFlashUploadUrl = projectName+'/wikiUploadFlash.action'; 
  
    //获取带"/"的项目名，如：/uimcardprj
    config.filebrowserImageUploadUrl = projectName+'/wikiUploadImage.action'; //固定路径
    config.filebrowserFlashBrowseUrl = projectName+'/wikiUploadFlash.action';
	
};
