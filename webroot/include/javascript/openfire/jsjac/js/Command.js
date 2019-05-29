
var Command = {
//	server: '192.168.1.71',
	server: 'uat.rogerstems.com',
//	server: 'tems.51jobcanada.com',
//	resource : 'Club',
	username : '',
	password : '1',
	ContextPath : '',
	sendTo : '',
	con : null,
//	httpPort:7070,
	httpPort:5223,
	httpsPort:7443,
//	httpPort:7072,
//	httpsPort:7445,
	
	/**
	 * 发送消息
	 */
	send: function (sendTo, message) {
         try {
             var oMsg = new JSJaCMessage();
             oMsg.setTo(new JSJaCJID(sendTo+"@"+Command.server));
             oMsg.setBody(message);
             Command.con.send(oMsg);
         } catch (e) {
             alert(e.message);
         }
     },
     
    /**
	 * 接收消息
	 */ 
	receive: function(message) {},
	
    /**
	 * 判断用户是否在线
	 */ 
    isUserOnline : function(userId, callback) {
    	var url = ContextPath + "/command.do?method=isUserOnLine&userId="+userId;
    	jQuery.get(url, function(data){
	        if (callback != null) {
	        	var result = {};
	        	result.userId = userId;
	        	result.onlineFlag = data;
		        callback(result);
    		}
		},"text");
    },
    
	init : function (){
	
		var oDbg;
        
        function handleIQ(oIQ) {
            Command.con.send(oIQ.errorReply(ERR_FEATURE_NOT_IMPLEMENTED));
        }

        function handleMessage(oJSJaCPacket) {
            //  oJSJaCPacket.getFromJID()
            // oJSJaCPacket.getBody().htmlEnc()
           // console.debug(oJSJaCPacket.getBody());
          //  alert(oJSJaCPacket.getBody().htmlEnc());
        	var properties = oJSJaCPacket.getChild("properties");
        	var obj = {};
        	for (var i=0; i<properties.childNodes.length; i++) {
        		var property = properties.childNodes.item(i);
        		if (property) {
	        		var name = property.getElementsByTagName("name")[0].innerHTML;
	        		var value = property.getElementsByTagName("value")[0].innerHTML;
	        		if ("channelId" == name) {
	        			obj.channelId = value;
	        		} else if ("msgText" == name) {
	        			obj.msgText = value;
	        		} else if ("privateFlag" == name) {
	        			obj.privateFlag = value;
	        		} else if ("referenceTypeId" == name) {
	        			obj.referenceTypeId = value;
	        		} else if ("referenceId" == name) {
	        			obj.referenceId = value;
	        		} else if ("referenceNumber" == name) {
	        			obj.referenceNumber = value;
	        		} else if ("messageId" == name) {
	        			obj.messageId = value;
	        		} else if ("sendTimestamp" == name) {
	        			obj.sendTimestamp = value;
	        		} else if ("channelTypeId" == name) {
	        			obj.channelTypeId = value;
	        		} else if ("fromUserId" == name) {
	        			obj.fromUserId = value;
	        		} else if ("fromUserName" == name) {
	        			obj.fromUserName = value;
	        		} else if ("channelName" == name) {
	        			obj.channelName = value;
	        		} else if ("referenceKey" == name) {
	        			obj.referenceKey = value;
	        		}else if ("attachmentFile" == name) {
	        			obj.attachmentFile = value;
	        		}
        		}
        	}
        	
            Command.receive(obj);
        }

        function handlePresence(oJSJaCPacket) {
        }

        function handleError(e) {
        	if (e.getAttribute('code') == 409) {
        		doLogin(false);	
        	}
         //   alert("An error occured:<br />" + ("Code: " + e.getAttribute('code') + "\nType: " + e.getAttribute('type') + "\nCondition: " + e.firstChild.nodeName).htmlEnc());
            if (Command.con.connected())
                Command.con.disconnect();
        }

        function handleStatusChanged(status) {
            oDbg.log("status changed: " + status);
        }

        function handleConnected() {
            Command.con.send(new JSJaCPresence());
        }

        function handleDisconnected() {
        }

        function handleIqVersion(iq) {
            Command.con.send(iq.reply([iq.buildNode('name', 'SPS'), iq.buildNode('version', JSJaC.Version), iq.buildNode('os', navigator.userAgent)]));
        }

        function handleIqTime(iq) {
            var now = new Date();
            Command.con.send(iq.reply([iq.buildNode('display', now.toLocaleString()), iq.buildNode('utc', now.jabberDate()), iq.buildNode('tz', now.toLocaleString().substring(now.toLocaleString().lastIndexOf(' ') + 1))]));
        }

        function doLogin(isRegister) {
            var oArgs = new Object();
                
            oDbg = new JSJaCConsoleLogger(3);
            // reset

            try {
                
                if (window.location.protocol !== "https:"){
                    httpbase = 'http://' + Command.server + ':'+ Command.httpPort +'/http-bind/';
                } else {
                    httpbase = 'https://' + Command.server + ':'+ Command.httpsPort +'/http-bind/';
                }
                // set up the connection
                Command.con = new JSJaCHttpBindingConnection({
                    oDbg: oDbg,
                    httpbase: httpbase,
                    timerval: 500
                });

                setupCon(Command.con);

                // setup args for connect method
                oArgs.domain = Command.server;
                oArgs.username = Command.username;
             //   oArgs.resource = Command.resource;
                oArgs.pass = Command.password;
                if (isRegister) 
                	oArgs.register = true;
                Command.con.connect(oArgs);
            } catch (e) {
                alert(e.toString());
            }
        }

		function listenLonginStatus() {
        	var intervalTime = 10*1000;	// 10秒钟
        	setInterval(function () {
        		if (!Command.con.connected()) {
        			doLogin(false);
        		}
        	},intervalTime);
        }
        
        function setupCon(oCon) {
            oCon.registerHandler('message', handleMessage);
            oCon.registerHandler('presence', handlePresence);
            oCon.registerHandler('iq', handleIQ);
            oCon.registerHandler('onconnect', handleConnected);
            oCon.registerHandler('onerror', handleError);
            oCon.registerHandler('status_changed', handleStatusChanged);
            oCon.registerHandler('ondisconnect', handleDisconnected);

           // oCon.registerIQGet('query', NS_VERSION, handleIqVersion);
           // oCon.registerIQGet('query', NS_TIME, handleIqTime);
        }

        doLogin(true);
        listenLonginStatus();
	},
	
	quit : function () {
            var p = new JSJaCPresence();
            p.setType("unavailable");
            Command.con.send(p);
            Command.con.disconnect();
        },


    onunload : function() {
            if ( typeof Command.con != 'undefined' && Command.con && Command.con.connected()) {
                // save backend type
                if (Command.con._hold)// must be binding
                    (new JSJaCCookie('btype', 'binding')).write();
                else
                    (new JSJaCCookie('btype', 'polling')).write();
                if (Command.con.suspend) {
                    Command.con.suspend();
                }
                alert("onunload");
            }
        }
};
