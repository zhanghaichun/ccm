 	/*
	*****************************************
	Author : Lea Smart
	Source : www.totallysmartit.com
	Date : 7/3/2001
	DHTML Calendar
	Version 1.2
	
	******************************************
	*/
	var timeoutDelay = 1500; // milliseconds, set to 0 for the calendar to never auto disappear
	var g_startDay = 0;// 0=sunday, 1=monday
	
	// preload images
	var imgUp = new Image(8,12);
	var imgDown = new Image(8,12);
	var DOWN_SRC;
	var UP_SRC;
   
	UP_SRC = "include/javascript/Calendar/up.gif";
	DOWN_SRC = "include/javascript/Calendar/down.gif";
	imgUp.src = UP_SRC;
	imgDown.src = DOWN_SRC;
	// used by timeout auto hide functions
	var timeoutId = false;
	
	// the now standard browser sniffer class
	function Browser(){
	  this.dom = document.getElementById?1:0;
	  this.ie4 = (document.all && !this.dom)?1:0;
	  this.ns4 = (document.layers && !this.dom)?1:0;
	  this.ns6 = (this.dom && !document.all)?1:0;
	  this.ie5 = (this.dom && document.all)?1:0;
	  this.ok = this.dom || this.ie4 || this.ns4;
	  this.platform = navigator.platform;
	}
	var browser = new Browser();
		
	// dom browsers require this written to the HEAD section
	
	if (browser.dom || browser.ie4){
	    document.writeln('<style>');
		document.writeln('#container {');
		document.writeln('position : absolute;');
		document.writeln('z-index : 1000;');
		document.writeln('left : 100px;');
		document.writeln('top : 100px;');
		//document.writeln('width : 124px;');
		document.writeln('width : 254px;');
		//browser.platform=='Win32'?height=140:height=145;
		browser.platform=='Win32'?height=150:height=150;
		document.writeln('height : ' + height +'px;');
		//document.writeln('clip:rect(0px 124px ' + height + 'px 0px);');
		document.writeln('clip:rect(0px 314px ' + height + 'px 0px);');
		//document.writeln('overflow : hidden;');
		document.writeln('visibility : hidden;');
		document.writeln('background-color : #ffffff');
		document.writeln('}');
		document.writeln('</style>')
		document.write('<div id="container"');
		if (timeoutDelay) document.write(' onmouseout="calendarTimeout();" onmouseover="if (timeoutId) clearTimeout(timeoutId);"');
		document.write('></div>');
	}
	
	var g_Calendar;  // global to hold the calendar reference, set by constructor
	var g_daysOfWeek = new Array("Su","Mo","Tu","We","Th","Fr","Sa");
	var g_months = new Array("January","February","March","April","May","June","July","August","September","October","November","December");

	function calendarTimeout(){
	  if (browser.ie4 || browser.ie5){
	    if (window.event.srcElement && window.event.srcElement.name!='month') timeoutId=setTimeout('g_Calendar.hide();',timeoutDelay);
	  }
	  if (browser.ns6 || browser.ns4){
	    timeoutId=setTimeout('g_Calendar.hide();',timeoutDelay);
	  }
	}
	
	// constructor for calendar class
	function Calendar(){
	  g_Calendar = this;
	  // some constants needed throughout the program
	  this.daysOfWeek = g_daysOfWeek;
	  this.months = g_months;
	  this.daysInMonth = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
	  
	  if (browser.ns4){
	    var tmpLayer = new Layer(127);
		if (timeoutDelay){
		  tmpLayer.captureEvents(Event.MOUSEOVER | Event.MOUSEOUT);
		  tmpLayer.onmouseover = function(event) { if (timeoutId) clearTimeout(timeoutId); };
		  tmpLayer.onmouseout = function(event) { timeoutId=setTimeout('g_Calendar.hide()',timeoutDelay);};
		}
	    tmpLayer.x = 100;
	    tmpLayer.y = 100;
	    tmpLayer.bgColor = "#ffffff";
	  }
	  if (browser.dom || browser.ie4){
		var tmpLayer = browser.dom?document.getElementById('container'):document.all.container;
	  }
	  this.containerLayer = tmpLayer;
	  if (browser.ns4 && browser.platform=='Win32') {
	    this.containerLayer.clip.height=134;
	    this.containerLayer.clip.width=127;
	  }

	}
	
 	Calendar.prototype.getFirstDOM = function() {
		var thedate = new Date();
		thedate.setDate(1);
		thedate.setMonth(this.month);
		thedate.setFullYear(this.year);
		return thedate.getDay();
	}

	Calendar.prototype.getDaysInMonth = function (){
	   if (this.month!=1) {
	   return this.daysInMonth[this.month]
	   }
	   else {
	     // is it a leap year
		    if (Date.isLeapYear(this.year)) {
			  return 29;
			}
		    else {
			  return 28;
			}
	   }
	}
	 
	Calendar.prototype.buildString = function(){
	  var tmpStr = '<form onSubmit="this.year.blur();return false;"><table width="100%" border="0" cellspacing="0" cellpadding="2" class="calBorderColor"><tr><td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="1" class="calBgColor">';
	  tmpStr += '<tr>';
	  tmpStr += '<td width="60%" class="cal" align="left">';
	  if (this.hasDropDown) {
	    tmpStr += '<select class="month" name="month" onchange="g_Calendar.selectChange();">';
		for (var i=0;i<this.months.length;i++){
	      tmpStr += '<option value="' + i + '"' 
		  if (i == this.month) tmpStr += ' selected';
		  tmpStr += '>' + this.months[i] + '</option>';
	    }
	    tmpStr += '</select>';
	  } else {
	    tmpStr += '<table border="0" cellspacing="0" cellpadding="0"><tr><td><a href="javascript: g_Calendar.changeMonth(-1);"><img name="calendar" src="'+DOWN_SRC+'" width="6" height="8" border="0" alt=""></a></td><td class="cal" width="100%" align="center">' + this.months[this.month] + '</td><td class="cal"><a href="javascript: g_Calendar.changeMonth(+1);"><img name="calendar" src="'+UP_SRC+'" width="8" height="6" border="0" alt=""></a></td></tr></table>';
	  }
	  tmpStr += '</td>';
	  /* observation : for some reason if the below event is changed to 'onChange' rather than 'onBlur' it totally crashes IE (4 and 5)!
	  */
	  tmpStr += '<td width="40%" align="right" class="cal">';
	  
	  if (this.hasDropDown) { 
	    tmpStr += '<input class="year" type="text" size="';
	    // get round NS4 win32 lenght of year input problem
	    (browser.ns4 && browser.platform=='Win32')?tmpStr += 1:tmpStr += 4;
	  tmpStr += '" name="year" maxlength="4" onBlur="g_Calendar.inputChange();" value="' + this.year + '">';
	  } else {
	  tmpStr += '<table border="0" cellspacing="0" cellpadding="0"><tr><td class="cal"><a href="javascript: g_Calendar.changeYear(-1);"><img name="calendar" src="'+DOWN_SRC+'" width="6" height="8" border="0" alt=""></a></td><td class="cal" width="100%" align="center">' + this.year + '</td><td class="cal"><a href="javascript: g_Calendar.changeYear(+1);"><img name="calendar" src="'+UP_SRC+'" width="8" height="6" border="0" alt=""></a></td></tr></table>'
	  }
	  tmpStr += '</td>';
	  tmpStr += '</tr>';
	  tmpStr += '</table>';
	  var iCount = 1;

	  var iFirstDOM = (7+this.getFirstDOM()-g_startDay)%7; // to prevent calling it in a loop

	  var iDaysInMonth = this.getDaysInMonth(); // to prevent calling it in a loop
	  
	  tmpStr += '<table width="100%" border="0" cellspacing="2" cellpadding="2" class="calBgColor">';
	  tmpStr += '<tr>';
	    for (var i=0;i<7;i++){
		  tmpStr += '<td  align="center" class="calDaysColor"><small><small>' + this.daysOfWeek[(g_startDay+i)%7] + '</small></small></td>';
		}
	  tmpStr += '</tr>';
	  var tmpFrom = parseInt('' + this.dateFromYear + this.dateFromMonth + this.dateFromDay,10);
	  var tmpTo = parseInt('' + this.dateToYear + this.dateToMonth + this.dateToDay,10);
	  var tmpCompare;
	  for (var j=1;j<=6;j++){
	     tmpStr += '<tr>';
	     for (var i=1;i<=7;i++){
		   tmpStr += '<td width="10" align="center" '
		   if ( (7*(j-1) + i)>=iFirstDOM+1  && iCount <= iDaysInMonth){
		     if (iCount==this.day && this.year==this.oYear && this.month==this.oMonth) tmpStr += 'class="calHighlightColor"';
			 else {
			    if (i==7-g_startDay || i==((7-g_startDay)%7)+1) tmpStr += 'class="calWeekend"';
				else tmpStr += 'class="cal"';
			 }
		     tmpStr += '>';
			 /* could create a date object here and compare that but probably more efficient to convert to a number
			   and compare number as numbers are primitives */
			 tmpCompare = parseInt('' + this.year + padZero(this.month) + padZero(iCount),10);
			 if (tmpCompare >= tmpFrom && tmpCompare <= tmpTo) {
			   tmpStr += '<a class="cal" href="javascript: g_Calendar.clickDay(' + iCount + ');">' + iCount + '</a>';
			 } else {
			   tmpStr += '<span class="disabled">' + iCount + '</span>';
			 }
			 iCount++;
		   } else {
		     if  (i==7-g_startDay || i==((7-g_startDay)%7)+1) tmpStr += 'class="calWeekend"'; else tmpStr +='class="cal"';
			 tmpStr += '>&nbsp;';
		   }
		   tmpStr += '</td>'
		 }
		 tmpStr += '</tr>'
	  }
	  tmpStr += '</table></td></tr></table></form>'
	  return tmpStr;
	}
	
	Calendar.prototype.selectChange = function(){
	  this.month = browser.ns6?this.containerLayer.ownerDocument.forms[0].month.selectedIndex:this.containerLayer.document.forms[0].month.selectedIndex;
	  this.writeString(this.buildString());
	}
	
	Calendar.prototype.inputChange = function(){
	  var tmp = browser.ns6?this.containerLayer.ownerDocument.forms[0].year:this.containerLayer.document.forms[0].year;
	  if (tmp.value >=1900 || tmp.value <=2100){
	    this.year = tmp.value;
	    this.writeString(this.buildString());
	  } else {
	    tmp.value = this.year;
	  }
	}
	Calendar.prototype.changeYear = function(incr){
	   (incr==1)?this.year++:this.year--;
	   this.writeString(this.buildString());
	}
	Calendar.prototype.changeMonth = function(incr){
	    if (this.month==11 && incr==1){
	      this.month = 0;
	  	  this.year++;
	    } else {
	      if (this.month==0 && incr==-1){
	        this.month = 11;
		    this.year--;
	      } else {
		    (incr==1)?this.month++:this.month--;
		  }
		}
		this.writeString(this.buildString());
	}
	
	Calendar.prototype.clickDay = function(day){
	   //var tmp = eval('document.' + this.target);
	   var tmp = document.getElementById(this.target);
	   tmp.value = this.formatDateAsString(day,this.month,this.year);
	    if (browser.ns4) this.containerLayer.hidden=true;
	    if (browser.dom || browser.ie4){
	      this.containerLayer.style.visibility='hidden';
	    }
	}
	Calendar.prototype.formatDateAsString = function(day, month, year){
	  var delim = eval('/\\' + this.dateDelim + '/g');
	   switch (this.dateFormat.replace(delim,"")){
	     case 'ddmmmyyyy': return padZero(day) + this.dateDelim + this.months[month].substr(0,3) + this.dateDelim + year;
		 case 'ddmmyyyy': return padZero(day) + this.dateDelim + padZero(month+1) + this.dateDelim + year;
		 case 'mmddyyyy': return padZero((month+1)) + this.dateDelim + padZero(day) + this.dateDelim + year;
		 case 'mmddyy': return padZero((month+1)) + this.dateDelim + padZero(day) + this.dateDelim + year.toString().substr(2,2);
	     case 'yyyymmdd': return year + this.dateDelim + padZero(month+1) + this.dateDelim + padZero(day);
		 default: alert('unsupported date format');
	   }
	}
	Calendar.prototype.writeString = function(str){
	  if (browser.ns4){
	    this.containerLayer.document.open();
	    this.containerLayer.document.write(str);
	    this.containerLayer.document.close();
	  } 
	  if (browser.dom || browser.ie4){
	    this.containerLayer.innerHTML = str;
	  }
	}
	
	Calendar.prototype.show = function(event, target, bHasDropDown, dateFormat, dateFrom, dateTo){
	// calendar can restrict choices between 2 dates, if however no restrictions
	// are made, let them choose any date between 1900 and 3000
	this.dateFrom = dateFrom || new Date(1900,0,1);
	this.dateFromDay = padZero(this.dateFrom.getDate());
	this.dateFromMonth = padZero(this.dateFrom.getMonth());
	this.dateFromYear = this.dateFrom.getFullYear();
	this.dateTo = dateTo || new Date(3000,0,1);
	this.dateToDay = padZero(this.dateTo.getDate());
	this.dateToMonth = padZero(this.dateTo.getMonth());
	this.dateToYear = this.dateTo.getFullYear();
	this.hasDropDown = bHasDropDown;
	this.dateFormat = dateFormat || 'dd-mmm-yyyy';
	switch (this.dateFormat){
	  case 'dd-mmm-yyyy':
	  case 'dd-mm-yyyy':
	  case 'yyyy-mm-dd':
	    this.dateDelim = '-';
		break;
	  case 'dd/mm/yyyy':
	  case 'mm/dd/yyyy':
	  case 'mm/dd/yy':
	  case 'dd/mmm/yyyy':
	  case 'yyyy/mm/dd':
	    this.dateDelim = '/';
		break;
	}
	
	  if (browser.ns4) {
	    if (!this.containerLayer.hidden) {
		  this.containerLayer.hidden=true;
		  return;
		}
	   }
	  if (browser.dom || browser.ie4){
	    if (this.containerLayer.style.visibility=='visible') {
		  this.containerLayer.style.visibility='hidden';
		  return;
		}  
	  }

	  if (browser.ie5 || browser.ie4){
	    var event = window.event;
	  }
	  if (browser.ns4){
	  	//***************  START OLD **************
		//this.containerLayer.x = event.x-135;
	    //this.containerLayer.y = event.y+15;
		//***************  END OLD **************
	    this.containerLayer.x = event.x+20;
	    this.containerLayer.y = event.y;
	  }
	  if (browser.ie5 || browser.ie4){
	    var obj = event.srcElement;
 	    x = 0;
  		while (obj.offsetParent != null) {
    		  x += obj.offsetLeft;
    		  obj = obj.offsetParent;
  		}
  		x += obj.offsetLeft;
	    y = 0;
		var obj = event.srcElement;
	    while (obj.offsetParent != null) {
    		  y += obj.offsetTop;
    		  obj = obj.offsetParent;
  		}
  		y += obj.offsetTop;
		
        this.containerLayer.style.left = x-105;
		if (event.y>0)this.containerLayer.style.top = y+20;
	  }
	  if (browser.ns6){
	    this.containerLayer.style.left = event.pageX-105;
		this.containerLayer.style.top = event.pageY+15;
	  }
	  this.target = target;
	  //var tmp = eval('document.' + this.target);
	  var tmp = null;//document.getElementById(this.target);
	  if (tmp && tmp.value && tmp.value.split(this.dateDelim).length==3 && tmp.value.indexOf('d')==-1){
	    var atmp = tmp.value.split(this.dateDelim)
		switch (this.dateFormat){
		 case 'dd-mmm-yyyy':
		 case 'dd/mmm/yyyy':
		   for (var i=0;i<this.months.length;i++){
		     if (atmp[1].toLowerCase()==this.months[i].substr(0,3).toLowerCase()){
		       this.month = this.oMonth = i;
			   break;
		     }
		   }
		   this.day = parseInt(atmp[0],10);
		   this.year = this.oYear = parseInt(atmp[2],10);
		   break;
		 case 'dd/mm/yyyy':
		 case 'dd-mm-yyyy':
		   this.month = this.oMonth = parseInt(atmp[1]-1,10); 
		   this.day = parseInt(atmp[0],10);
		   this.year = this.oYear = parseInt(atmp[2],10);
		   break;
		 case 'mm/dd/yyyy':
		 case 'mm-dd-yyyy':
		   this.month = this.oMonth = parseInt(atmp[0]-1,10);
		   this.day = parseInt(atmp[1],10);
		   this.year = this.oYear = parseInt(atmp[2],10);
		   break;
//Code added by Masroor, Dec 23, 2009
		 case 'yyyy/mm/dd':
		   this.year = this.oYear = parseInt(atmp[0],10);
		   this.month = this.oMonth = parseInt(atmp[1]-1,10);
		   this.day = parseInt(atmp[2],10);
		   
		   break;
		 case 'mm/dd/yy':
		   this.month = this.oMonth = parseInt(atmp[0]-1,10);
		   this.day = parseInt(atmp[1],10);
		   this.year = this.oYear = 2000 + parseInt(atmp[2],10);
		   break;
		 case 'yyyy-mm-dd':
		   this.month = this.oMonth = parseInt(atmp[1]-1,10);
		   this.day = parseInt(atmp[2],10);
		   this.year = this.oYear = parseInt(atmp[0],10);
		   break;
		}
	  } else { // no date set, default to today
	    var theDate = new Date();
	  	 this.year = this.oYear = theDate.getFullYear();
	     this.month = this.oMonth = theDate.getMonth();
	     this.day = this.oDay = theDate.getDate();
	  }
	  this.writeString(this.buildString());
	  
	  // and then show it!
	   if (browser.ns4) {
	   this.containerLayer.hidden = false;
	  }
	  if (browser.dom || browser.ie4){
	      this.containerLayer.style.visibility='visible';
	      	      this.firstShowup = true;
	  }
	}
	
	Calendar.prototype.hide = function(){
	  if (browser.ns4) this.containerLayer.hidden = true;
	  if (browser.dom || browser.ie4){
	    this.containerLayer.style.visibility='hidden';
	  }
	}
	
	function handleDocumentClick(e){
	  
		if (g_Calendar == null )
	    return;
		if (g_Calendar.containerLayer == null)
			return;
			if(g_Calendar.firstShowup){
				g_Calendar.firstShowup = false;
				return;
			}
	  if (browser.ie4 || browser.ie5) e = window.event;

	  if (browser.ns6){
	    var bTest = (e.pageX > parseInt(g_Calendar.containerLayer.style.left,10) && e.pageX <  (parseInt(g_Calendar.containerLayer.style.left,10)+125) && e.pageY < (parseInt(g_Calendar.containerLayer.style.top,10)+125) && e.pageY > parseInt(g_Calendar.containerLayer.style.top,10));
	    if (e.target.name!='imgCalendar' && e.target.name!='month'  && e.target.name!='year' && e.target.name!='calendar' && !bTest){
		  g_Calendar.hide(); 
		}
	  }
	  if (browser.ie4 || browser.ie5){
		// extra test to see if user clicked inside the calendar but not on a valid date, we don't want it to disappear in this case
	   var bTest = (e.x > parseInt(g_Calendar.containerLayer.style.left,10) && e.x <  (parseInt(g_Calendar.containerLayer.style.left,10)+125) && e.y < (parseInt(g_Calendar.containerLayer.style.top,10)+125) && e.y > parseInt(g_Calendar.containerLayer.style.top,10));
	    if (e.srcElement.name!='imgCalendar' && e.srcElement.name!='month' && e.srcElement.name!='year' && !bTest & typeof(e.srcElement)!='object'){
		  g_Calendar.hide(); 
		}
	  }
	  if (browser.ns4) g_Calendar.hide();
	}
	
	// utility function
	function padZero(num) {
	  return ((num <= 9) ? ("0" + num) : num);
	}
	  // Finally licked extending  native date object;
	  Date.isLeapYear = function(year){ if (year%4==0 && ((year%100!=0) || (year%400==0))) return true; else return false; }
	  Date.daysInYear = function(year){ if (Date.isLeapYear(year)) return 366; else return 365;}
	  var DAY = 1000*60*60*24;
	  Date.prototype.addDays = function(num){
		return new Date((num*DAY)+this.valueOf());
	  }	
	  
	 // events capturing, careful you don't override this by setting something in the onload event of 
	// the body tag
	window.onload=function(){ 
	  new Calendar(new Date());
	  if (browser.ns4){
	    if (typeof document.NSfix == 'undefined'){
		  document.NSfix = new Object();
	      document.NSfix.initWidth=window.innerWidth;
		  document.NSfix.initHeight=window.innerHeight;
		}
	  }
	}
	if (browser.ns4) window.onresize = function(){
	  if (document.NSfix.initWidth!=window.innerWidth || document.NSfix.initHeight!=window.innerHeight) window.location.reload(false);
	} // ns4 resize bug workaround
	window.document.onclick=handleDocumentClick;
    //window.onerror = function(msg,url,line){
//	  alert('******* an error has occurred ********' +
	//  '\n\nPlease check that' + 
	//  '\n\n1)You have not added any code to the body onload event,'
	//  +  '\nif you want to run something as well as the calendar initialisation'
	//  + '\ncode, add it to the onload event in the calendar library.'
	//  + '\n\n2)You have set the parameters correctly in the g_Calendar.show() method '
	//  + '\n\nSee www.totallysmartit.com\\examples\\calendar\\simple.asp for examples'
	//  + '\n\n------------------------------------------------------'
	//  + '\nError details'
	//  + '\nText:' + msg + '\nurl:' + url + '\nline:' + line);
	//}
