<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>

    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    
<link rel="stylesheet" type="text/css" href="include/javascript/yui/build/slider.css" />
<LINK href="include/javascript/yui2/fonts-min.css" type=text/css rel=stylesheet>
<link rel="stylesheet" type="text/css" href="include/javascript/yui/assets/container.css" />
<link rel="stylesheet" type="text/css" href="include/javascript/yui/build/colorpicker.css" />

<script type="text/javascript" src="include/javascript/yui/build/yahoo-dom-event.js"></script>
<SCRIPT src="include/javascript/yui2/dragdrop-min.js" type=text/javascript></SCRIPT>
<script type="text/javascript" src="include/javascript/yui/build/animation-min.js"></script>
<script type="text/javascript" src="include/javascript/yui/build/slider-min.js"></script>
<SCRIPT src="include/javascript/yui2/element-min.js" type=text/javascript></SCRIPT>
<script type="text/javascript" src="include/javascript/yui/build/colorpicker-min.js"></script>

<link rel="stylesheet" type="text/css" href="include/javascript/yui/build/colorpickerBug.css" />

</head>

<body class="yui-skin-sam">

<!--BEGIN SOURCE CODE FOR EXAMPLE  -->

<div id="yui-picker-panel">
  <div class="yui-picker" id="yui-picker"></div>
</div>

<script type="text/javascript">
//Using an anonymous function keeps our variables
//out of the global namespace.
(function() {
    var Event = YAHOO.util.Event,
        picker;

    // this is how to override some or all of the
    // element ids used by the control
    var ids = YAHOO.lang.merge(
        YAHOO.widget.ColorPicker.prototype.ID, {
            R: "custom_R",
            G: "custom_G",
            B: "custom_B"
        });

    // this is how to change the text generated
    // by the control
    var txt = YAHOO.lang.merge(
        YAHOO.widget.ColorPicker.prototype.TXT, {
            R: "Red",
            G: "Green",
            B: "Blue"
        });
	
	//Having changed the default element ids, you can
	//instantiate your picker:
    Event.onDOMReady(function() {
            picker = new YAHOO.widget.ColorPicker("yui-picker-panel", {
                    showhsvcontrols: true,
                    showhexcontrols: true,
                    ids: ids,
                    txt: txt,
                    animate: false
                });
        });
})();
</script>

<!--END SOURCE CODE FOR EXAMPLE -->

</body>
</html>
